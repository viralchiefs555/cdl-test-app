package com.example.ui.screens

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.viewinterop.AndroidView
import java.io.ByteArrayInputStream

// The 3D page and model live in app/src/main/assets/pretrip3d/.
// They are served from a fake https origin so the page can fetch() the .glb model.
private const val ASSET_HOST = "appassets.androidplatform.net"
private const val START_URL = "https://$ASSET_HOST/assets/pretrip3d/index.html"

private fun mimeFor(path: String): String = when (path.substringAfterLast('.', "").lowercase()) {
    "html" -> "text/html"
    "js", "mjs" -> "text/javascript"
    "css" -> "text/css"
    "json" -> "application/json"
    "glb" -> "model/gltf-binary"
    "png" -> "image/png"
    "svg" -> "image/svg+xml"
    else -> "application/octet-stream"
}

/**
 * Full-screen 3D tractor-trailer with Find it and Test modes. The camera is moved only by the app.
 * three.js is bundled in assets/pretrip3d/three, so this works offline.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreTrip3DScreen(onBack: () -> Unit) {
    BackHandler(onBack = onBack)
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            TopAppBar(
                title = { Text("Pre-Trip Inspection") },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("pretrip3d_back_button")
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            factory = { context -> createInspectionWebView(context) },
            onRelease = { webView ->
                webView.stopLoading()
                webView.destroy()
            }
        )
    }
}

@SuppressLint("SetJavaScriptEnabled")
private fun createInspectionWebView(context: android.content.Context): WebView {
    val appAssets = context.applicationContext.assets
    return WebView(context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.allowFileAccess = false
        settings.allowContentAccess = false
        setBackgroundColor(Color.rgb(223, 228, 232))
        webChromeClient = object : WebChromeClient() {
            // Page errors show up in Logcat under the tag PreTrip3D.
            override fun onConsoleMessage(message: ConsoleMessage): Boolean {
                Log.d("PreTrip3D", "${message.message()} (${message.sourceId()}:${message.lineNumber()})")
                return true
            }
        }
        webViewClient = object : WebViewClient() {
            override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
                val url = request.url
                if (url.host != ASSET_HOST) return null // only web fonts come from the network
                val path = url.path?.removePrefix("/assets/") ?: return null
                return try {
                    val mime = mimeFor(path)
                    val encoding = if (mime.startsWith("text/")) "utf-8" else null
                    val headers = mapOf(
                        "Access-Control-Allow-Origin" to "*",
                        "Cache-Control" to "no-cache"
                    )
                    WebResourceResponse(mime, encoding, 200, "OK", headers, appAssets.open(path))
                } catch (e: Exception) {
                    WebResourceResponse(
                        "text/plain",
                        "utf-8",
                        404,
                        "Not Found",
                        emptyMap(),
                        ByteArrayInputStream(ByteArray(0))
                    )
                }
            }

            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean =
                request.url.host != ASSET_HOST // keep the WebView on the 3D page
        }
        loadUrl(START_URL)
    }
}
