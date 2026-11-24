package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.CDLViewModel
import com.example.ui.theme.SafetyAmber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheatSheetScreen(
    viewModel: CDLViewModel,
    modifier: Modifier = Modifier
) {
    BackHandler {
        viewModel.navigateBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "CDL Must-Know Numbers",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.navigateBack() },
                        modifier = Modifier.testTag("cheat_sheet_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "💡 High-Yield DMV Exam Summary",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Over 75% of CDL written exam questions test these exact pressures, distances, and FMCSR regulatory limits.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            // Air Brake Pressures
            item {
                CheatSectionCard(
                    title = "Air Brake Key Pressures & Tests",
                    icon = Icons.Default.Build,
                    items = listOf(
                        "Governor Cut-Out Pressure" to "120 - 140 PSI (compressor stops pumping)",
                        "Governor Cut-In Pressure" to "~100 PSI (compressor resumes pumping)",
                        "Low Air Warning Alarm" to "Must trigger before falling below 60 PSI",
                        "Spring Brakes Automatic Apply" to "20 - 45 PSI (mechanical springs pop out)",
                        "Safety Relief Valve Pop-Off" to "150 PSI (protects against over-pressurization)",
                        "Dual System Operating Min." to "100 PSI before operating vehicle",
                        "Static Leakage (Engine Off, Released)" to "Max 2 PSI/min (Single) • 3 PSI/min (Combination)",
                        "Applied Leakage (Engine Off, Applied)" to "Max 3 PSI/min (Single) • 4 PSI/min (Combination)",
                        "Manual Slack Adjuster Travel" to "No more than 1 inch free stroke when pulled"
                    )
                )
            }

            // HazMat Classes & Distances
            item {
                CheatSectionCard(
                    title = "Hazardous Materials Rules & Distances",
                    icon = Icons.Default.LocalGasStation,
                    items = listOf(
                        "The 9 Hazard Classes" to "1: Explosives, 2: Gases, 3: Flammable Liquids, 4: Flammable Solids, 5: Oxidizers, 6: Poisons, 7: Radioactive, 8: Corrosives, 9: Misc",
                        "Placard Table 1 Materials" to "Placard for ANY quantity transported",
                        "Placard Table 2 Materials" to "Placard for 1,001+ lbs aggregate gross weight",
                        "Vehicle Placard Placement" to "All 4 sides: front, rear, left, and right",
                        "Railroad Crossing Stop" to "Stop 15 to 50 feet before the nearest rail",
                        "No Smoking Proximity" to "Never smoke within 25 feet of Class 3 or Div 2.1",
                        "Explosives Parking Rule" to "Never park within 300 feet of bridge, tunnel, or building",
                        "Emergency Phone Requirement" to "24-hour monitored technical response line",
                        "Shipping Papers Location" to "Within reach while seatbelted, or driver door pouch"
                    )
                )
            }

            // General Knowledge & FMCSR
            item {
                CheatSectionCard(
                    title = "General Knowledge FMCSR Standards",
                    icon = Icons.Default.Speed,
                    items = listOf(
                        "Following Distance Formula" to "1 sec per 10 ft (< 40 mph) + 1 sec over 40 mph",
                        "Highway Look-Ahead Distance" to "12 to 15 seconds ahead (about 1/4 mile)",
                        "Front Steering Tire Tread" to "At least 4/32 inch in every major groove",
                        "All Other Tire Tread" to "At least 2/32 inch in every major groove",
                        "Commercial Driver BAC Limit" to "0.04% BAC (Any alcohol = 24-hr out-of-service)",
                        "Cargo Inspection Schedule" to "Within first 50 miles, then every 3 hrs or 150 miles",
                        "Minimum Flatbed Tiedowns" to "At least 2 tiedowns regardless of cargo size",
                        "Steering Wheel Free Play" to "Max ~10 degrees or about 2 inches on 20-inch wheel"
                    )
                )
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun CheatSectionCard(
    title: String,
    icon: ImageVector,
    items: List<Pair<String, String>>
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            items.forEachIndexed { index, (label, value) ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = value,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (index < items.size - 1) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Surface(
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                        ) {}
                    }
                }
            }
        }
    }
}
