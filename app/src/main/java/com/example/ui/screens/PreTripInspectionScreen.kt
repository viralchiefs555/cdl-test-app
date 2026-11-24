package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.pretrip.*
import com.example.ui.CDLViewModel
import com.example.ui.theme.FailingRed
import com.example.ui.theme.FailingRedContainer
import com.example.ui.theme.NavyDark
import com.example.ui.theme.PassingGreen
import com.example.ui.theme.PassingGreenContainer
import com.example.ui.theme.PrimaryBlue
import com.example.ui.theme.SafetyAmber
import kotlin.math.cos
import kotlin.math.sin

/**
 * Main Interactive CDL Pre-Trip Inspection System Screen.
 * Provides 2D and 3D perspective visual inspection, interactive hotspot markers,
 * Quiz & CDL Verbatim Recitation modes, and dynamic Air Brake (COLA) simulation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreTripInspectionScreen(
    viewModel: CDLViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.preTripState.collectAsState()

    BackHandler {
        viewModel.navigateBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "CDL Pre-Trip Inspection",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "FMCSA & DMV Standards Simulator",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.navigateBack() },
                        modifier = Modifier.testTag("pretrip_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    // 2D / 3D Perspective Toggle Button
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = if (state.viewMode == ViewMode.VIEW_3D_PERSPECTIVE)
                            MaterialTheme.colorScheme.primaryContainer
                        else
                            MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable { viewModel.togglePreTripViewMode() }
                            .testTag("toggle_view_mode_button")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ViewInAr,
                                contentDescription = "View Mode",
                                modifier = Modifier.size(16.dp),
                                tint = if (state.viewMode == ViewMode.VIEW_3D_PERSPECTIVE)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (state.viewMode == ViewMode.VIEW_3D_PERSPECTIVE) "3D Perspective" else "2D Layout",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Reset Button
                    IconButton(
                        onClick = { viewModel.resetPreTripSession() },
                        modifier = Modifier.testTag("reset_pretrip_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Reset Inspection",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Zone Selector Tabs
            InspectionZoneTabs(
                selectedZone = state.currentZone,
                onSelectZone = { viewModel.selectPreTripZone(it) }
            )

            // Real-Time HUD: Progress, Score & Auto-Fail Alerts
            InspectionStatusHUD(
                state = state,
                onShowSummary = { viewModel.togglePreTripSummary(true) }
            )

            // Content Area: Either Visual Canvas or Dynamic Air Brake Dashboard
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                when (state.currentZone) {
                    InspectionZone.AIR_BRAKE_SYSTEM -> {
                        AirBrakeDashboardView(
                            state = state,
                            onStepAction = { viewModel.performAirBrakeStepAction() },
                            onPumpBrake = { viewModel.pumpAirBrakePedal() },
                            onHoldBrake = { viewModel.holdAirBrakePedal() },
                            onToggleTractorValve = { viewModel.toggleTractorParkingValve() },
                            onToggleTrailerValve = { viewModel.toggleTrailerSupplyValve() },
                            onInspectStep = { componentId -> viewModel.selectComponentForInspection(componentId) }
                        )
                    }
                    InspectionZone.ALL_FLUID_LEVELS -> {
                        FluidLevelsDashboardView(
                            state = state,
                            onSelectFluidTab = { viewModel.selectFluidSubTab(it) },
                            onDipstickAction = { viewModel.performDipstickStepAction() },
                            onResetDipstick = { viewModel.resetDipstickSimulation() },
                            onCheckCoolantSightGlass = { viewModel.checkCoolantSightGlass() },
                            onCheckCoolantCap = { viewModel.checkCoolantCap() },
                            onCheckCoolantHoses = { viewModel.checkCoolantHoses() },
                            onCheckPowerSteeringLevel = { viewModel.checkPowerSteeringLevel() },
                            onCheckPowerSteeringHoses = { viewModel.checkPowerSteeringHoses() },
                            onCheckPowerSteeringFittings = { viewModel.checkPowerSteeringFittings() },
                            onInspectComponent = { componentId -> viewModel.selectComponentForInspection(componentId) }
                        )
                    }
                    InspectionZone.STEERING_COMPONENTS -> {
                        SteeringDashboardView(
                            state = state,
                            onSelectSubTab = { viewModel.selectSteeringSubTab(it) },
                            onCheckFrameMountBolts = { viewModel.checkSteeringFrameMountBolts() },
                            onCheckGearBoxCasting = { viewModel.checkSteeringGearBoxCasting() },
                            onCheckHoses = { viewModel.checkSteeringHoses() },
                            onCheckShaftPlay = { viewModel.checkSteeringShaftPlay() },
                            onCheckPitmanArm = { viewModel.checkPitmanArm() },
                            onCheckDragLink = { viewModel.checkDragLink() },
                            onCheckTieRod = { viewModel.checkTieRod() },
                            onTestJointPlay = { viewModel.testSteeringJointPlay(it) },
                            onReset = { viewModel.resetSteeringSession() },
                            onInspectComponent = { componentId -> viewModel.selectComponentForInspection(componentId) }
                        )
                    }
                    InspectionZone.BELTS_AND_HOSES -> {
                        BeltsAndHosesDashboardView(
                            state = state,
                            onSelectSubTab = { viewModel.selectBeltsHosesSubTab(it) },
                            onSelectBelt = { viewModel.selectBeltItem(it) },
                            onSelectHose = { viewModel.selectHoseItem(it) },
                            onCheckBeltFraying = { viewModel.checkBeltFraying(it) },
                            onCheckBeltCracksCuts = { viewModel.checkBeltCracksCuts(it) },
                            onCheckBeltWear = { viewModel.checkBeltWear(it) },
                            onTestBeltPlay = { belt, play -> viewModel.testBeltPlay(belt, play) },
                            onCheckHoseClamps = { viewModel.checkHoseClamps(it) },
                            onCheckHoseDryRot = { viewModel.checkHoseDryRot(it) },
                            onCheckHoseBubbling = { viewModel.checkHoseBubbling(it) },
                            onCheckHoseLeaks = { viewModel.checkHoseLeaks(it) },
                            onReset = { viewModel.resetBeltsHosesSession() },
                            onInspectComponent = { componentId -> viewModel.selectComponentForInspection(componentId) }
                        )
                    }
                    InspectionZone.FRONT_SUSPENSION -> {
                        FrontSuspensionDashboardView(
                            state = state,
                            onSelectSubTab = { viewModel.selectSuspensionSubTab(it) },
                            onCheckLeafSpringsCracks = { viewModel.checkLeafSpringsCracks() },
                            onCheckLeafSpringsShifting = { viewModel.checkLeafSpringsShifting() },
                            onCheckLeafSpringsMissing = { viewModel.checkLeafSpringsMissing() },
                            onCheckUBoltsTight = { viewModel.checkUBoltsTight() },
                            onCheckUBoltsRustTrails = { viewModel.checkUBoltsRustTrails() },
                            onCheckSpringHangers = { viewModel.checkSpringHangers() },
                            onCheckBushingPins = { viewModel.checkBushingPins() },
                            onCheckShockTopMount = { viewModel.checkShockTopMount() },
                            onCheckShockBottomMount = { viewModel.checkShockBottomMount() },
                            onCheckShockOilLeaks = { viewModel.checkShockOilLeaks() },
                            onCheckShockBushings = { viewModel.checkShockBushings() },
                            onReset = { viewModel.resetSuspensionSession() },
                            onInspectComponent = { componentId -> viewModel.selectComponentForInspection(componentId) }
                        )
                    }
                    InspectionZone.FRONT_BRAKES_AND_AXLE -> {
                        FrontBrakesAxleDashboardView(
                            state = state,
                            onSelectSubTab = { viewModel.selectFrontBrakesSubTab(it) },
                            onCheckChamberMounting = { viewModel.checkChamberMounting() },
                            onCheckChamberDentsLeaks = { viewModel.checkChamberDentsLeaks() },
                            onCheckPushrodClevisPin = { viewModel.checkPushrodClevisPin() },
                            onTestPushrodTravel = { viewModel.testPushrodTravel(it) },
                            onCheckDrumCracks = { viewModel.checkDrumCracks() },
                            onCheckOilGreaseContamination = { viewModel.checkOilGreaseContamination() },
                            onMeasureLiningThickness = { viewModel.measureLiningThickness(it) },
                            onMeasureTreadDepth = { viewModel.measureTreadDepth(it) },
                            onCheckAbcSidewall = { viewModel.checkAbcSidewall() },
                            onCheckExposedFabric = { viewModel.checkExposedFabric() },
                            onCheckRecapProhibition = { viewModel.checkRecapProhibition() },
                            onMeasureTirePressure = { viewModel.measureTirePressure(it) },
                            onCheckValveStemCap = { viewModel.checkValveStemCap() },
                            onCheckRimWeldsCracks = { viewModel.checkRimWeldsCracks() },
                            onCheckAllLugNuts = { viewModel.checkAllLugNuts() },
                            onCheckRustStreaks = { viewModel.checkRustStreaks() },
                            onCheckHubSightGlass = { viewModel.checkHubSightGlass() },
                            onCheckHubSealLeaks = { viewModel.checkHubSealLeaks() },
                            onReset = { viewModel.resetFrontBrakesSession() },
                            onInspectComponent = { componentId -> viewModel.selectComponentForInspection(componentId) }
                        )
                    }
                    else -> {
                        InteractiveInspectionCanvas(
                            state = state,
                            onMarkerClicked = { component ->
                                viewModel.selectComponentForInspection(component.id)
                            }
                        )
                    }
                }
            }
        }
    }

    // Modal / Drawer Dialog for Component Inspection (Quiz & Verbatim Check)
    if (state.modalMode != InspectionModalMode.NONE && state.selectedComponent != null) {
        ComponentInspectionModal(
            component = state.selectedComponent!!,
            state = state,
            onClose = { viewModel.closePreTripModal() },
            onSwitchMode = { newMode -> viewModel.setPreTripModalMode(newMode) },
            onSubmitQuiz = { answerIndex ->
                viewModel.submitPreTripQuizAnswer(answerIndex)
            },
            onToggleVerbatim = { phraseIndex ->
                viewModel.togglePreTripVerbatimCheck(phraseIndex)
            },
            onSubmitVerbatim = {
                viewModel.submitPreTripVerbatimRecitation()
            }
        )
    }

    // End-of-Test Summary Report Dialog
    if (state.showSummaryDialog) {
        PreTripSummaryReportDialog(
            state = state,
            onDismiss = { viewModel.togglePreTripSummary(false) },
            onRestart = {
                viewModel.resetPreTripSession()
                viewModel.togglePreTripSummary(false)
            }
        )
    }
}

/**
 * Inspection Zone Horizontal Tabs
 */
@Composable
fun InspectionZoneTabs(
    selectedZone: InspectionZone,
    onSelectZone: (InspectionZone) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = selectedZone.ordinal,
        edgePadding = 12.dp,
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxWidth()
    ) {
        InspectionZone.entries.forEach { zone ->
            val isSelected = zone == selectedZone
            Tab(
                selected = isSelected,
                onClick = { onSelectZone(zone) },
                text = {
                    Text(
                        text = zone.title,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            )
        }
    }
}

/**
 * Inspection Real-Time Status HUD displaying progress, points, and auto-fails.
 */
@Composable
fun InspectionStatusHUD(
    state: PreTripInspectionState,
    onShowSummary: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Progress
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Inspected: ",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${state.inspectedCount}/${state.totalComponentsCount} (${state.progressPercent}%)",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                // Auto-Fail Indicator
                if (state.autoFailsEncountered.isNotEmpty()) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = FailingRedContainer
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = FailingRed,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "AUTO-FAIL (${state.autoFailsEncountered.size})",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = FailingRed
                            )
                        }
                    }
                } else {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = PassingGreenContainer.copy(alpha = 0.6f)
                    ) {
                        Text(
                            text = "Score: ${state.scorePercent}%",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = PassingGreen,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                // Summary Button
                TextButton(
                    onClick = onShowSummary,
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                    modifier = Modifier.testTag("pretrip_summary_button")
                ) {
                    Text(
                        text = "Report 📋",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            LinearProgressIndicator(
                progress = { state.progressPercent / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = if (state.autoFailsEncountered.isEmpty()) MaterialTheme.colorScheme.primary else FailingRed,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }
}

/**
 * Interactive Visual Canvas rendering 2D/3D inspection hotspots over vehicle schematics.
 */
@Composable
fun InteractiveInspectionCanvas(
    state: PreTripInspectionState,
    onMarkerClicked: (InspectionComponent) -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "markerPulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.25f,
        animationSpec = infiniteRepeatable(
            animation = tween(1100, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(NavyDark)
    ) {
        val width = constraints.maxWidth.toFloat()
        val height = constraints.maxHeight.toFloat()

        // Background Technical Schematic Graphic
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawSchematicGrid(width, height)
            drawVehicleZoneSchematic(state.currentZone, state.viewMode, width, height)
        }

        // Hotspot Markers for Current Zone
        state.zoneComponents.forEach { component ->
            val isInspected = state.inspectedComponentIds.contains(component.id)
            val isPassed = state.passedComponentIds.contains(component.id)

            // 3D Perspective Shift Calculation
            val xPos = if (state.viewMode == ViewMode.VIEW_3D_PERSPECTIVE) {
                // Apply subtle perspective foreshortening based on zDepth
                val depthFactor = 0.7f + (component.zDepth * 0.3f)
                (width * 0.5f) + ((component.xNormalized * width) - (width * 0.5f)) * depthFactor
            } else {
                component.xNormalized * width
            }

            val yPos = if (state.viewMode == ViewMode.VIEW_3D_PERSPECTIVE) {
                (component.yNormalized * height) * 0.9f + (component.zDepth * height * 0.08f)
            } else {
                component.yNormalized * height
            }

            val markerColor = when {
                !isInspected -> SafetyAmber
                isPassed -> PassingGreen
                else -> FailingRed
            }

            Box(
                modifier = Modifier
                    .size(52.dp)
                    .align(Alignment.TopStart)
                    .padding(
                        start = (xPos - 26).coerceIn(4f, width - 56).dp,
                        top = (yPos - 26).coerceIn(4f, height - 56).dp
                    )
                    .clickable { onMarkerClicked(component) }
                    .testTag("marker_${component.id}"),
                contentAlignment = Alignment.Center
            ) {
                // Outer Pulse Ring for Uninspected
                if (!isInspected) {
                    Box(
                        modifier = Modifier
                            .size((34 * pulseScale).dp)
                            .clip(CircleShape)
                            .background(markerColor.copy(alpha = 0.35f))
                    )
                }

                // Core Marker Button
                Surface(
                    shape = CircleShape,
                    color = markerColor,
                    shadowElevation = 6.dp,
                    border = BorderStroke(2.dp, Color.White),
                    modifier = Modifier.size(32.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        when {
                            !isInspected -> {
                                Text(
                                    text = "!",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }
                            isPassed -> {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Passed",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            else -> {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Failed",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Component Label Tag
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(
                        start = (xPos - 45).coerceIn(4f, width - 110).dp,
                        top = (yPos + 22).coerceIn(4f, height - 28).dp
                    )
            ) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = Color.Black.copy(alpha = 0.75f),
                    border = BorderStroke(0.5.dp, markerColor.copy(alpha = 0.6f))
                ) {
                    Text(
                        text = component.name,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                }
            }
        }

        // Zone Hint & Instructions Pill
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.Black.copy(alpha = 0.65f),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp)
        ) {
            Text(
                text = "Tap any pulsing hotspot to inspect • ${state.viewMode.name.replace("VIEW_", "")} Active",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.85f),
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
            )
        }
    }
}

/**
 * Custom Canvas drawing for schematic grid and vehicle zone blueprints.
 */
private fun DrawScope.drawSchematicGrid(width: Float, height: Float) {
    val gridColor = Color(0xFF1E293B)
    val step = 40.dp.toPx()

    var x = 0f
    while (x < width) {
        drawLine(
            color = gridColor,
            start = Offset(x, 0f),
            end = Offset(x, height),
            strokeWidth = 1f
        )
        x += step
    }

    var y = 0f
    while (y < height) {
        drawLine(
            color = gridColor,
            start = Offset(0f, y),
            end = Offset(width, y),
            strokeWidth = 1f
        )
        y += step
    }
}

private fun DrawScope.drawVehicleZoneSchematic(
    zone: InspectionZone,
    viewMode: ViewMode,
    width: Float,
    height: Float
) {
    val cyanGlow = Color(0xFF38BDF8)
    val darkBlue = Color(0xFF0F172A)
    val strokeColor = cyanGlow.copy(alpha = 0.45f)

    when (zone) {
        InspectionZone.ALL_FLUID_LEVELS -> {
            // Handled by specialized FluidLevelsDashboardView
        }
        InspectionZone.STEERING_COMPONENTS -> {
            // Handled by specialized SteeringDashboardView
        }
        InspectionZone.BELTS_AND_HOSES -> {
            // Handled by specialized BeltsAndHosesDashboardView
        }
        InspectionZone.FRONT_SUSPENSION -> {
            // Handled by specialized FrontSuspensionDashboardView
        }
        InspectionZone.ENGINE_COMPARTMENT -> {
            // 1. Chassis C-Channel Frame Rail
            drawRect(
                color = darkBlue,
                topLeft = Offset(width * 0.05f, height * 0.50f),
                size = Size(width * 0.90f, height * 0.08f)
            )
            drawRect(
                color = strokeColor,
                topLeft = Offset(width * 0.05f, height * 0.50f),
                size = Size(width * 0.90f, height * 0.08f),
                style = Stroke(width = 2.5f)
            )

            // 2. Diesel Engine Block & Valve Cover
            drawRoundRect(
                color = Color(0xFF131D31),
                topLeft = Offset(width * 0.30f, height * 0.14f),
                size = Size(width * 0.44f, height * 0.35f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(16f, 16f)
            )
            drawRoundRect(
                color = strokeColor.copy(alpha = 0.6f),
                topLeft = Offset(width * 0.30f, height * 0.14f),
                size = Size(width * 0.44f, height * 0.35f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(16f, 16f),
                style = Stroke(width = 2f)
            )

            // Engine Head Ribs
            for (i in 1..4) {
                val lineY = height * (0.17f + i * 0.06f)
                drawLine(
                    color = Color(0xFF1E293B),
                    start = Offset(width * 0.32f, lineY),
                    end = Offset(width * 0.72f, lineY),
                    strokeWidth = 3f
                )
            }

            // 3. Alternator (Top Right)
            drawCircle(
                color = Color(0xFF1E293B),
                center = Offset(width * 0.685f, height * 0.23f),
                radius = width * 0.055f
            )
            drawCircle(
                color = cyanGlow.copy(alpha = 0.5f),
                center = Offset(width * 0.685f, height * 0.23f),
                radius = width * 0.055f,
                style = Stroke(width = 2f)
            )
            drawCircle(
                color = Color(0xFFF59E0B),
                center = Offset(width * 0.685f, height * 0.23f),
                radius = width * 0.022f
            )
            // Output Wire
            drawLine(
                color = Color(0xFFEF4444),
                start = Offset(width * 0.73f, height * 0.20f),
                end = Offset(width * 0.78f, height * 0.21f),
                strokeWidth = 3.5f,
                cap = StrokeCap.Round
            )

            // 4. Water Pump (Center Front)
            drawCircle(
                color = Color(0xFF1E293B),
                center = Offset(width * 0.495f, height * 0.34f),
                radius = width * 0.045f
            )
            drawCircle(
                color = cyanGlow.copy(alpha = 0.5f),
                center = Offset(width * 0.495f, height * 0.34f),
                radius = width * 0.045f,
                style = Stroke(width = 2f)
            )
            drawCircle(
                color = Color(0xFF64748B),
                center = Offset(width * 0.495f, height * 0.34f),
                radius = width * 0.018f
            )

            // 5. Power Steering Reservoir (Driver Side)
            drawRoundRect(
                color = Color(0xFF1E293B),
                topLeft = Offset(width * 0.17f, height * 0.19f),
                size = Size(width * 0.10f, height * 0.15f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(8f, 8f)
            )
            drawRoundRect(
                color = cyanGlow.copy(alpha = 0.5f),
                topLeft = Offset(width * 0.17f, height * 0.19f),
                size = Size(width * 0.10f, height * 0.15f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(8f, 8f),
                style = Stroke(width = 2f)
            )
            // Fluid Level Window
            drawRect(
                color = Color(0xFFEF4444).copy(alpha = 0.85f),
                topLeft = Offset(width * 0.19f, height * 0.25f),
                size = Size(width * 0.06f, height * 0.07f)
            )

            // 6. Engine Oil Dipstick (Bright Yellow Loop)
            drawLine(
                color = Color(0xFF94A3B8),
                start = Offset(width * 0.33f, height * 0.21f),
                end = Offset(width * 0.35f, height * 0.48f),
                strokeWidth = 3f,
                cap = StrokeCap.Round
            )
            drawCircle(
                color = Color(0xFFEAB308),
                center = Offset(width * 0.33f, height * 0.21f),
                radius = 12f
            )
            drawCircle(
                color = darkBlue,
                center = Offset(width * 0.33f, height * 0.21f),
                radius = 5f
            )

            // 7. Air Compressor (Finned Cylinder Head)
            drawRoundRect(
                color = Color(0xFF1E293B),
                topLeft = Offset(width * 0.58f, height * 0.32f),
                size = Size(width * 0.08f, height * 0.14f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(6f, 6f)
            )
            drawRoundRect(
                color = strokeColor.copy(alpha = 0.5f),
                topLeft = Offset(width * 0.58f, height * 0.32f),
                size = Size(width * 0.08f, height * 0.14f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(6f, 6f),
                style = Stroke(width = 2f)
            )
            // Compressor Cooling Fins
            for (f in 1..3) {
                val finY = height * (0.34f + f * 0.025f)
                drawLine(
                    color = Color(0xFF94A3B8),
                    start = Offset(width * 0.575f, finY),
                    end = Offset(width * 0.665f, finY),
                    strokeWidth = 2.5f
                )
            }

            // 8. Steering Gear Box (Bolted to Frame)
            drawRect(
                color = Color(0xFF1E293B),
                topLeft = Offset(width * 0.15f, height * 0.49f),
                size = Size(width * 0.09f, height * 0.12f)
            )
            drawRect(
                color = cyanGlow.copy(alpha = 0.5f),
                topLeft = Offset(width * 0.15f, height * 0.49f),
                size = Size(width * 0.09f, height * 0.12f),
                style = Stroke(width = 2f)
            )
            // Steering Shaft
            drawLine(
                color = Color(0xFF64748B),
                start = Offset(width * 0.14f, height * 0.42f),
                end = Offset(width * 0.17f, height * 0.49f),
                strokeWidth = 7f,
                cap = StrokeCap.Round
            )

            // 9. Pitman Arm & Drag Link
            drawLine(
                color = Color(0xFF64748B),
                start = Offset(width * 0.195f, height * 0.57f),
                end = Offset(width * 0.192f, height * 0.67f),
                strokeWidth = 7f,
                cap = StrokeCap.Round
            )
            drawLine(
                color = Color(0xFF94A3B8),
                start = Offset(width * 0.192f, height * 0.67f),
                end = Offset(width * 0.36f, height * 0.73f),
                strokeWidth = 6f,
                cap = StrokeCap.Round
            )
        }
        InspectionZone.FRONT_BRAKES_AND_AXLE -> {
            // 1. Heavy Front I-Beam Axle
            drawLine(
                color = Color(0xFF1E293B),
                start = Offset(width * 0.08f, height * 0.83f),
                end = Offset(width * 0.92f, height * 0.83f),
                strokeWidth = 14f,
                cap = StrokeCap.Round
            )
            drawLine(
                color = strokeColor.copy(alpha = 0.5f),
                start = Offset(width * 0.08f, height * 0.83f),
                end = Offset(width * 0.92f, height * 0.83f),
                strokeWidth = 2f
            )

            // 2. Leaf Springs & U-Bolts Pack (Center Left)
            val springPath = Path().apply {
                moveTo(width * 0.28f, height * 0.77f)
                quadraticTo(width * 0.43f, height * 0.82f, width * 0.58f, height * 0.77f)
            }
            drawPath(
                path = springPath,
                color = Color(0xFF64748B),
                style = Stroke(width = 9f, cap = StrokeCap.Round)
            )
            // Spring U-Bolts
            drawLine(
                color = Color(0xFFCBD5E1),
                start = Offset(width * 0.41f, height * 0.76f),
                end = Offset(width * 0.41f, height * 0.84f),
                strokeWidth = 4f
            )
            drawLine(
                color = Color(0xFFCBD5E1),
                start = Offset(width * 0.45f, height * 0.76f),
                end = Offset(width * 0.45f, height * 0.84f),
                strokeWidth = 4f
            )

            // 3. Brake Chamber & Slack Adjuster
            drawCircle(
                color = Color(0xFF1E293B),
                center = Offset(width * 0.61f, height * 0.72f),
                radius = width * 0.045f
            )
            drawCircle(
                color = cyanGlow.copy(alpha = 0.5f),
                center = Offset(width * 0.61f, height * 0.72f),
                radius = width * 0.045f,
                style = Stroke(width = 2f)
            )
            // Pushrod
            drawLine(
                color = Color(0xFF94A3B8),
                start = Offset(width * 0.57f, height * 0.73f),
                end = Offset(width * 0.51f, height * 0.75f),
                strokeWidth = 4f,
                cap = StrokeCap.Round
            )
            // Slack Adjuster Arm
            drawLine(
                color = Color(0xFF64748B),
                start = Offset(width * 0.51f, height * 0.75f),
                end = Offset(width * 0.52f, height * 0.81f),
                strokeWidth = 6f,
                cap = StrokeCap.Round
            )

            // 4. Steer Tire Assembly (Right Side)
            drawRoundRect(
                color = Color(0xFF171717),
                topLeft = Offset(width * 0.80f, height * 0.48f),
                size = Size(width * 0.16f, height * 0.48f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(18f, 18f)
            )
            drawRoundRect(
                color = strokeColor.copy(alpha = 0.7f),
                topLeft = Offset(width * 0.80f, height * 0.48f),
                size = Size(width * 0.16f, height * 0.48f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(18f, 18f),
                style = Stroke(width = 2.5f)
            )
            // Tread Rib Lines
            for (t in 1..3) {
                val tx = width * (0.80f + t * 0.04f)
                drawLine(
                    color = Color.Black,
                    start = Offset(tx, height * 0.50f),
                    end = Offset(tx, height * 0.94f),
                    strokeWidth = 3f
                )
            }
            // Wheel Rim & Hub Oil Seal Sight Glass
            drawCircle(
                color = Color(0xFF334155),
                center = Offset(width * 0.88f, height * 0.72f),
                radius = width * 0.04f
            )
            drawCircle(
                color = Color(0xFF0284C7),
                center = Offset(width * 0.88f, height * 0.72f),
                radius = width * 0.018f
            )
            drawCircle(
                color = Color(0xFFEF4444),
                center = Offset(width * 0.88f, height * 0.72f),
                radius = width * 0.007f
            )
        }
        InspectionZone.COUPLING_WALKAROUND -> {
            // Draw Tractor Frame Rails & 5th Wheel Horseshoe
            drawLine(
                color = strokeColor,
                start = Offset(width * 0.32f, height * 0.15f),
                end = Offset(width * 0.32f, height * 0.85f),
                strokeWidth = 5f
            )
            drawLine(
                color = strokeColor,
                start = Offset(width * 0.68f, height * 0.15f),
                end = Offset(width * 0.68f, height * 0.85f),
                strokeWidth = 5f
            )
            // 5th Wheel Plate Arc
            drawArc(
                color = strokeColor,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(width * 0.38f, height * 0.38f),
                size = Size(width * 0.24f, height * 0.22f),
                style = Stroke(width = 4f)
            )
        }
        InspectionZone.IN_CAB_SAFETY -> {
            // Draw Cab Windshield & Dashboard Console
            val windshieldPath = Path().apply {
                moveTo(width * 0.18f, height * 0.25f)
                lineTo(width * 0.82f, height * 0.25f)
                lineTo(width * 0.88f, height * 0.60f)
                lineTo(width * 0.12f, height * 0.60f)
                close()
            }
            drawPath(windshieldPath, color = strokeColor, style = Stroke(width = 3f))
            // Steering Wheel Circle
            drawCircle(
                color = strokeColor,
                center = Offset(width * 0.35f, height * 0.68f),
                radius = 42f,
                style = Stroke(width = 4f)
            )
        }
        InspectionZone.AIR_BRAKE_SYSTEM -> {
            // Handled by specialized AirBrakeDashboardView
        }
    }
}

/**
 * Dynamic Air Brake System Test Dashboard (`renderAirBrakeDashboard()`)
 * Simulates the official 4-step COLA air brake test with interactive dual-needle pressure gauge,
 * warning buzzer/light at 60 PSI, and automatic emergency valve pop-outs between 20-40 PSI.
 */
@Composable
fun AirBrakeDashboardView(
    state: PreTripInspectionState,
    onStepAction: () -> Unit,
    onPumpBrake: () -> Unit,
    onHoldBrake: () -> Unit,
    onToggleTractorValve: () -> Unit,
    onToggleTrailerValve: () -> Unit,
    onInspectStep: (String) -> Unit
) {
    val sim = state.airBrakeSim

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B132B))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Step Banner & FMCSA Criteria
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "COLA Test: Step ${sim.testStep.stepNumber} of 4",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = SafetyAmber.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = "CRITICAL AUTO-FAIL",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = SafetyAmber,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = sim.testStep.title,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = sim.testStep.instruction,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Standard: ${sim.testStep.criteria}",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // Circular Dual-Needle Air Pressure Gauge
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1C2541)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "AIR SYSTEM PRESSURE (PSI)",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.White.copy(alpha = 0.8f)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Draw Dual-Needle Gauge
                    Canvas(
                        modifier = Modifier
                            .size(190.dp)
                            .testTag("air_brake_gauge")
                    ) {
                        drawAirPressureGauge(sim.currentPsi, sim.isLowAirLightActive)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Digital Readout & Warning Indicators
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Digital PSI Readout
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${sim.currentPsi.toInt()} PSI",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (sim.currentPsi < 60f) FailingRed else Color.White
                            )
                            Text(
                                text = if (sim.currentPsi in 120f..140f) "Governor Cut-Out" else "Tank Reservoir",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.White.copy(alpha = 0.6f)
                            )
                        }

                        // Low Air Warning Indicator Lamp
                        Surface(
                            shape = CircleShape,
                            color = if (sim.isLowAirLightActive) FailingRed else Color(0xFF334155),
                            border = BorderStroke(2.dp, if (sim.isLowAirLightActive) Color.White else Color.Transparent),
                            modifier = Modifier.size(54.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        imageVector = Icons.Default.Warning,
                                        contentDescription = null,
                                        tint = if (sim.isLowAirLightActive) Color.White else Color.Gray,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        text = "LOW AIR",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (sim.isLowAirLightActive) Color.White else Color.Gray
                                    )
                                }
                            }
                        }

                        // Buzzer Alarm Status
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (sim.isLowAirBuzzerActive) FailingRed.copy(alpha = 0.2f) else Color.Transparent,
                            border = BorderStroke(1.dp, if (sim.isLowAirBuzzerActive) FailingRed else Color(0xFF475569))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.GraphicEq,
                                    contentDescription = null,
                                    tint = if (sim.isLowAirBuzzerActive) FailingRed else Color.Gray,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (sim.isLowAirBuzzerActive) "BUZZER ON" else "Buzzer Off",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (sim.isLowAirBuzzerActive) FailingRed else Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }

        // Valve Knobs: Tractor Protection & Trailer Supply
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Parking Brake Valve Knobs",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Must automatically pop out between 20-40 PSI during bleed down",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Yellow Diamond: Tractor Parking Valve
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable { onToggleTractorValve() }
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0xFFFBBF24),
                                shadowElevation = if (sim.isTractorValvePopped) 8.dp else 2.dp,
                                border = BorderStroke(2.dp, Color.Black),
                                modifier = Modifier.size(64.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = if (sim.isTractorValvePopped) "OUT\n(SET)" else "IN\n(OFF)",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.ExtraBold,
                                        textAlign = TextAlign.Center,
                                        color = Color.Black
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "TRACTOR (Yellow)",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Red Octagon: Trailer Air Supply Valve
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable { onToggleTrailerValve() }
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = Color(0xFFEF4444),
                                shadowElevation = if (sim.isTrailerValvePopped) 8.dp else 2.dp,
                                border = BorderStroke(2.dp, Color.Black),
                                modifier = Modifier.size(64.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = if (sim.isTrailerValvePopped) "OUT\n(SET)" else "IN\n(OFF)",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.ExtraBold,
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "TRAILER (Red)",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Interactive Pedal & Test Action Controls
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Examiner Test Controls",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = onHoldBrake,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .testTag("hold_applied_brake_button")
                        ) {
                            Icon(imageVector = Icons.Default.Speed, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Hold Foot Brake (Applied)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = onPumpBrake,
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B)),
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp)
                                .testTag("fan_bleed_brake_button")
                        ) {
                            Icon(imageVector = Icons.Default.Air, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Fan Brakes (Bleed Air)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Complete Current Step Button
                    Button(
                        onClick = onStepAction,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PassingGreen),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("verify_step_button")
                    ) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Verify & Advance Step ${sim.testStep.stepNumber}",
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Open Component Quiz & Verbatim Check for this step
                    OutlinedButton(
                        onClick = {
                            val stepId = "air_brake_step${sim.testStep.stepNumber}"
                            onInspectStep(stepId)
                        },
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(imageVector = Icons.Default.Quiz, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Open Verbatim Recitation for Step ${sim.testStep.stepNumber}")
                    }
                }
            }
        }
    }
}

/**
 * Custom Canvas Drawing for the Circular Dual-Needle Air Pressure Gauge.
 */
private fun DrawScope.drawAirPressureGauge(currentPsi: Float, isWarningActive: Boolean) {
    val center = Offset(size.width / 2f, size.height / 2f)
    val radius = size.width / 2.2f

    // Gauge Outer Rim
    drawCircle(
        color = Color(0xFF334155),
        radius = radius,
        center = center,
        style = Stroke(width = 8f)
    )

    // Danger Zone Arc (0 - 60 PSI)
    drawArc(
        color = FailingRed.copy(alpha = 0.8f),
        startAngle = 135f,
        sweepAngle = 60f,
        useCenter = false,
        topLeft = Offset(center.x - radius + 12f, center.y - radius + 12f),
        size = Size((radius - 12f) * 2f, (radius - 12f) * 2f),
        style = Stroke(width = 12f)
    )

    // Safe Operating Zone Arc (90 - 140 PSI)
    drawArc(
        color = PassingGreen.copy(alpha = 0.8f),
        startAngle = 225f,
        sweepAngle = 110f,
        useCenter = false,
        topLeft = Offset(center.x - radius + 12f, center.y - radius + 12f),
        size = Size((radius - 12f) * 2f, (radius - 12f) * 2f),
        style = Stroke(width = 12f)
    )

    // Tick Marks & Numbers (0, 30, 60, 90, 120, 150)
    val ticks = listOf(0, 30, 60, 90, 120, 150)
    ticks.forEach { psi ->
        val sweepAngle = (psi / 150f) * 270f
        val angleDeg = 135f + sweepAngle
        val angleRad = Math.toRadians(angleDeg.toDouble())

        val tickStart = Offset(
            x = (center.x + (radius - 22f) * cos(angleRad)).toFloat(),
            y = (center.y + (radius - 22f) * sin(angleRad)).toFloat()
        )
        val tickEnd = Offset(
            x = (center.x + (radius - 8f) * cos(angleRad)).toFloat(),
            y = (center.y + (radius - 8f) * sin(angleRad)).toFloat()
        )

        drawLine(
            color = Color.White.copy(alpha = 0.8f),
            start = tickStart,
            end = tickEnd,
            strokeWidth = 3f
        )
    }

    // Needle Angle: 0 PSI -> 135 deg, 150 PSI -> 405 (45) deg
    val needleAngleDeg = 135f + (currentPsi.coerceIn(0f, 150f) / 150f) * 270f
    val needleRad = Math.toRadians(needleAngleDeg.toDouble())

    // Primary Needle (Orange)
    val needleEnd = Offset(
        x = (center.x + (radius - 26f) * cos(needleRad)).toFloat(),
        y = (center.y + (radius - 26f) * sin(needleRad)).toFloat()
    )
    drawLine(
        color = Color(0xFFF97316),
        start = center,
        end = needleEnd,
        strokeWidth = 5f,
        cap = StrokeCap.Round
    )

    // Center Hub
    drawCircle(
        color = Color.White,
        radius = 12f,
        center = center
    )
    drawCircle(
        color = Color.Black,
        radius = 6f,
        center = center
    )
}

/**
 * Inspection Modal / Drawer supporting Quiz Mode and CDL Verbatim Recitation Check.
 */
@Composable
fun ComponentInspectionModal(
    component: InspectionComponent,
    state: PreTripInspectionState,
    onClose: () -> Unit,
    onSwitchMode: (InspectionModalMode) -> Unit,
    onSubmitQuiz: (Int) -> Unit,
    onToggleVerbatim: (Int) -> Unit,
    onSubmitVerbatim: () -> Unit
) {
    var selectedQuizOption by remember { mutableStateOf<Int?>(null) }
    var quizSubmitted by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onClose) {
        Surface(
            shape = RoundedCornerShape(18.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                // Modal Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = component.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "${component.zone.title} • ${component.systemGroup}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    IconButton(
                        onClick = onClose,
                        modifier = Modifier.testTag("close_modal_button")
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Mode Switcher (Quiz Mode vs Verbatim Check)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = state.modalMode == InspectionModalMode.QUIZ,
                        onClick = { onSwitchMode(InspectionModalMode.QUIZ) },
                        label = { Text("Quiz Mode 🧠") },
                        modifier = Modifier.weight(1f)
                    )
                    FilterChip(
                        selected = state.modalMode == InspectionModalMode.VERBATIM_RECITATION,
                        onClick = { onSwitchMode(InspectionModalMode.VERBATIM_RECITATION) },
                        label = { Text("Verbatim Check 🗣️") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // FMCSA Examiner Criteria Card
                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "FMCSA EXAMINER CRITERIA:",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = component.fmcsaCriteria,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Content based on Mode
                if (state.modalMode == InspectionModalMode.QUIZ) {
                    // QUIZ MODE CONTENT
                    Text(
                        text = component.quiz.question,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    component.quiz.options.forEachIndexed { index, optionText ->
                        val isSelected = selectedQuizOption == index
                        val isCorrect = index == component.quiz.correctIndex
                        val cardColor = when {
                            !quizSubmitted && isSelected -> MaterialTheme.colorScheme.primaryContainer
                            quizSubmitted && isCorrect -> PassingGreenContainer
                            quizSubmitted && isSelected && !isCorrect -> FailingRedContainer
                            else -> MaterialTheme.colorScheme.surface
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = cardColor,
                            border = BorderStroke(
                                width = 1.dp,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable(enabled = !quizSubmitted) {
                                    selectedQuizOption = index
                                }
                        ) {
                            Text(
                                text = "${('A' + index)}. $optionText",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if (!quizSubmitted) {
                        Button(
                            onClick = {
                                if (selectedQuizOption != null) {
                                    quizSubmitted = true
                                    onSubmitQuiz(selectedQuizOption!!)
                                }
                            },
                            enabled = selectedQuizOption != null,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .testTag("submit_quiz_button")
                        ) {
                            Text("Submit Inspection Check", fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Button(
                            onClick = onClose,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Continue Inspection")
                        }
                    }
                } else {
                    // VERBATIM RECITATION MODE
                    Text(
                        text = "Recite & check every required CDL phrase:",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    val checkedIndices = state.verbatimSelectedMap[component.id] ?: emptySet()

                    component.mandatoryVerbatimPhrases.forEachIndexed { index, phrase ->
                        val isChecked = checkedIndices.contains(index)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onToggleVerbatim(index) }
                                .padding(vertical = 4.dp)
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = { onToggleVerbatim(index) },
                                colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "\"$phrase\"",
                                style = MaterialTheme.typography.bodySmall,
                                color = if (isChecked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = onSubmitVerbatim,
                        enabled = checkedIndices.size == component.mandatoryVerbatimPhrases.size,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("submit_verbatim_button")
                    ) {
                        Text(
                            text = if (checkedIndices.size == component.mandatoryVerbatimPhrases.size)
                                "Complete Verbal Recitation ✓"
                            else
                                "Check All Required Phrases (${checkedIndices.size}/${component.mandatoryVerbatimPhrases.size})",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

/**
 * End-of-Test Summary Report Dialog showing PASS/FAIL standard (80% + Zero Auto-Fails).
 */
@Composable
fun PreTripSummaryReportDialog(
    state: PreTripInspectionState,
    onDismiss: () -> Unit,
    onRestart: () -> Unit
) {
    val isPassed = state.isPassed

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(18.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Result Banner
                Surface(
                    shape = CircleShape,
                    color = if (isPassed) PassingGreenContainer else FailingRedContainer,
                    modifier = Modifier.size(64.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = if (isPassed) Icons.Default.CheckCircle else Icons.Default.Warning,
                            contentDescription = null,
                            tint = if (isPassed) PassingGreen else FailingRed,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = if (isPassed) "CDL PRE-TRIP: PASSED!" else "CDL PRE-TRIP: FAILED",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (isPassed) PassingGreen else FailingRed
                )

                Text(
                    text = "Standard: 80% Minimum Score + Zero Auto-Fails",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Stats Grid
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Score", style = MaterialTheme.typography.labelSmall)
                        Text(
                            text = "${state.scorePercent}%",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Inspected", style = MaterialTheme.typography.labelSmall)
                        Text(
                            text = "${state.inspectedCount}/${state.totalComponentsCount}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Auto-Fails", style = MaterialTheme.typography.labelSmall)
                        Text(
                            text = "${state.autoFailsEncountered.size}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (state.autoFailsEncountered.isEmpty()) PassingGreen else FailingRed
                        )
                    }
                }

                // Critical Failures List (if any)
                if (state.autoFailsEncountered.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = FailingRedContainer.copy(alpha = 0.5f))
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "CRITICAL SAFETY FAILURES:",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = FailingRed
                            )
                            state.autoFailsEncountered.take(3).forEach { failMsg ->
                                Text(
                                    text = "• $failMsg",
                                    style = MaterialTheme.typography.bodySmall,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onRestart,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Restart")
                    }
                    Button(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Review")
                    }
                }
            }
        }
    }
}

/**
 * Interactive All Fluid Levels Dashboard (`renderFluidLevelsDashboard()`)
 * Simulates hands-on CDL inspection for:
 * 1. Engine Oil Dipstick (Pull out, wipe clean, re-insert, pull out again, verify between MIN/MAX)
 * 2. Coolant Recovery Tank (Sight glass level, cap tight, no leaks)
 * 3. Power Steering Fluid Reservoir (Dipstick/sight glass level, verify hoses have no cuts/bulges/leaks)
 */
@Composable
fun FluidLevelsDashboardView(
    state: PreTripInspectionState,
    onSelectFluidTab: (FluidType) -> Unit,
    onDipstickAction: () -> Unit,
    onResetDipstick: () -> Unit,
    onCheckCoolantSightGlass: () -> Unit,
    onCheckCoolantCap: () -> Unit,
    onCheckCoolantHoses: () -> Unit,
    onCheckPowerSteeringLevel: () -> Unit,
    onCheckPowerSteeringHoses: () -> Unit,
    onCheckPowerSteeringFittings: () -> Unit,
    onInspectComponent: (String) -> Unit
) {
    val sim = state.fluidSim

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B132B))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Fluid Sub-Tabs Selector
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    FluidType.entries.forEach { type ->
                        val isSelected = type == sim.activeFluidSubTab
                        val isCompleted = when (type) {
                            FluidType.ENGINE_OIL -> sim.dipstickCompleted
                            FluidType.COOLANT_TANK -> sim.coolantCompleted
                            FluidType.POWER_STEERING -> sim.powerSteeringCompleted
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                            border = BorderStroke(
                                1.dp,
                                if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onSelectFluidTab(type) }
                                .testTag("fluid_tab_${type.name}")
                        ) {
                            Column(
                                modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = type.iconEmoji,
                                        fontSize = 16.sp
                                    )
                                    if (isCompleted) {
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = "Completed",
                                            tint = PassingGreen,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = when (type) {
                                        FluidType.ENGINE_OIL -> "Engine Oil"
                                        FluidType.COOLANT_TANK -> "Coolant"
                                        FluidType.POWER_STEERING -> "Power Steering"
                                    },
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }

        // Active Fluid Inspection Content
        when (sim.activeFluidSubTab) {
            FluidType.ENGINE_OIL -> {
                item {
                    EngineOilDipstickSection(
                        sim = sim,
                        onDipstickAction = onDipstickAction,
                        onResetDipstick = onResetDipstick,
                        onInspectQuiz = { onInspectComponent("dipstick") }
                    )
                }
            }
            FluidType.COOLANT_TANK -> {
                item {
                    CoolantTankSection(
                        sim = sim,
                        onCheckSightGlass = onCheckCoolantSightGlass,
                        onCheckCap = onCheckCoolantCap,
                        onCheckHoses = onCheckCoolantHoses,
                        onInspectQuiz = { onInspectComponent("coolant_recovery_tank") }
                    )
                }
            }
            FluidType.POWER_STEERING -> {
                item {
                    PowerSteeringSection(
                        sim = sim,
                        onCheckLevel = onCheckPowerSteeringLevel,
                        onCheckHoses = onCheckPowerSteeringHoses,
                        onCheckFittings = onCheckPowerSteeringFittings,
                        onInspectQuiz = { onInspectComponent("power-steering-reservoir") }
                    )
                }
            }
        }
    }
}

/**
 * 1. Engine Oil Dipstick Interactive Simulator
 */
@Composable
private fun EngineOilDipstickSection(
    sim: FluidLevelsSimState,
    onDipstickAction: () -> Unit,
    onResetDipstick: () -> Unit,
    onInspectQuiz: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // FMCSA Examiner Instruction Banner
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.5.dp, PrimaryBlue.copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🛢️ Engine Oil Dipstick Check",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = if (sim.dipstickCompleted) PassingGreenContainer else PrimaryBlue.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = if (sim.dipstickCompleted) "VERIFIED PASS" else "STEP ${sim.dipstickStep.stepNumber} OF 5",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (sim.dipstickCompleted) PassingGreen else PrimaryBlue,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "CDL Standard Procedure:",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Pull out dipstick, wipe clean with rag, re-insert fully into tube, and pull out again. Ensure oil level is in the safe operating zone between MIN and MAX marks.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        // Interactive Dipstick Visual Graphic Canvas
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF131D31)),
            border = BorderStroke(1.dp, Color(0xFF334155)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "INTERACTIVE DIPSTICK SIMULATOR",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Custom Dipstick Graphic
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .testTag("dipstick_canvas")
                ) {
                    drawDipstickGraphic(sim.dipstickStep, sim.oilLevelPercent)
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Current Step Status Pill
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = when (sim.dipstickStep) {
                        DipstickStep.IN_TUBE_INITIAL -> Color(0xFF1E293B)
                        DipstickStep.FIRST_PULL_DIRTY -> Color(0xFF3B2F1E)
                        DipstickStep.WIPED_CLEAN -> Color(0xFF1E3A5F)
                        DipstickStep.REINSERTED -> Color(0xFF1E293B)
                        DipstickStep.FINAL_PULL_READING, DipstickStep.VERIFIED_SAFE -> PassingGreenContainer
                    },
                    border = BorderStroke(
                        1.dp,
                        if (sim.dipstickCompleted) PassingGreen else PrimaryBlue.copy(alpha = 0.5f)
                    )
                ) {
                    Text(
                        text = sim.dipstickStep.instruction,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = if (sim.dipstickCompleted) PassingGreen else Color.White,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Hands-on Action Buttons
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Main Interactive Step Button
                Button(
                    onClick = onDipstickAction,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("dipstick_action_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (sim.dipstickCompleted) PassingGreen else MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = if (sim.dipstickCompleted) Icons.Default.CheckCircle else Icons.Default.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (sim.dipstickCompleted) "Dipstick Level Verified (Tap to Repeat)" else sim.dipstickStep.actionLabel,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onResetDipstick,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("reset_dipstick_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Reset")
                    }

                    Button(
                        onClick = onInspectQuiz,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("dipstick_quiz_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Quiz,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("CDL Quiz")
                    }
                }
            }
        }
    }
}

/**
 * 2. Coolant Recovery Tank Interactive Simulator
 */
@Composable
private fun CoolantTankSection(
    sim: FluidLevelsSimState,
    onCheckSightGlass: () -> Unit,
    onCheckCap: () -> Unit,
    onCheckHoses: () -> Unit,
    onInspectQuiz: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // CDL Standard Instruction Banner
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.5.dp, Color(0xFF38BDF8).copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "💧 Coolant Recovery Tank",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0284C7)
                    )
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = if (sim.coolantCompleted) PassingGreenContainer else Color(0xFF0284C7).copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = if (sim.coolantCompleted) "ALL CHECKS PASSED" else "3 CHECKS REQUIRED",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (sim.coolantCompleted) PassingGreen else Color(0xFF0284C7),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "CDL Standard Procedure:",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Check fluid level through the sight glass or translucent reservoir (ensure between Cold and Hot fill lines); ensure no leaks, and radiator cap is tight with gasket intact.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        // Coolant Visual Graphic Canvas
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF131D31)),
            border = BorderStroke(1.dp, Color(0xFF334155)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "COOLANT RESERVOIR & CAP SCHEMATIC",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .testTag("coolant_canvas")
                ) {
                    drawCoolantTankGraphic(
                        levelPercent = sim.coolantLevelPercent,
                        isSightGlassChecked = sim.isCoolantSightGlassChecked,
                        isCapTight = sim.isCoolantCapTight,
                        isHosesInspected = sim.isCoolantHosesInspected
                    )
                }
            }
        }

        // 3 Hands-on Check Tasks
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Perform CDL Examiner Checklist:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )

                // Task 1: Sight Glass Level
                FluidCheckItemButton(
                    title = "1. Check Sight Glass / Reservoir Level",
                    subtitle = "Verify coolant is between COLD FULL & HOT FULL marks",
                    isChecked = sim.isCoolantSightGlassChecked,
                    onClick = onCheckSightGlass,
                    testTag = "check_coolant_sight_glass"
                )

                // Task 2: Pressure Cap
                FluidCheckItemButton(
                    title = "2. Check Radiator / Surge Tank Cap",
                    subtitle = "Verify cap is securely seated, tight, with intact gasket",
                    isChecked = sim.isCoolantCapTight,
                    onClick = onCheckCap,
                    testTag = "check_coolant_cap"
                )

                // Task 3: Hoses & Leaks
                FluidCheckItemButton(
                    title = "3. Inspect Hoses, Clamps & Seams",
                    subtitle = "Verify no cuts, splits, swelling, or active coolant drips",
                    isChecked = sim.isCoolantHosesInspected,
                    onClick = onCheckHoses,
                    testTag = "check_coolant_hoses"
                )

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = onInspectQuiz,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Icon(
                        imageVector = Icons.Default.Quiz,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Coolant System CDL Quiz")
                }
            }
        }
    }
}

/**
 * 3. Power Steering Fluid Reservoir Interactive Simulator
 */
@Composable
private fun PowerSteeringSection(
    sim: FluidLevelsSimState,
    onCheckLevel: () -> Unit,
    onCheckHoses: () -> Unit,
    onCheckFittings: () -> Unit,
    onInspectQuiz: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        // CDL Standard Instruction Banner
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = BorderStroke(1.5.dp, Color(0xFFF59E0B).copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "⚙️ Power Steering Reservoir",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD97706)
                    )
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = if (sim.powerSteeringCompleted) PassingGreenContainer else Color(0xFFD97706).copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = if (sim.powerSteeringCompleted) "ALL CHECKS PASSED" else "3 CHECKS REQUIRED",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (sim.powerSteeringCompleted) PassingGreen else Color(0xFFD97706),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "CDL Standard Procedure:",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Check dipstick or sight glass for proper level; verify hoses have no cuts, bulges, abrasions, or leaks.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        // Power Steering Visual Graphic Canvas
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF131D31)),
            border = BorderStroke(1.dp, Color(0xFF334155)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "POWER STEERING RESERVOIR & LINES",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.White.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .testTag("power_steering_canvas")
                ) {
                    drawPowerSteeringGraphic(
                        levelPercent = sim.powerSteeringLevelPercent,
                        isLevelChecked = sim.isPowerSteeringLevelChecked,
                        isHosesChecked = sim.isPowerSteeringHosesChecked,
                        isFittingsChecked = sim.isPowerSteeringFittingsChecked
                    )
                }
            }
        }

        // 3 Hands-on Check Tasks
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Perform CDL Examiner Checklist:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )

                // Task 1: Check Level
                FluidCheckItemButton(
                    title = "1. Check Level on Dipstick or Sight Glass",
                    subtitle = "Ensure fluid is above the MIN line in safe operating range",
                    isChecked = sim.isPowerSteeringLevelChecked,
                    onClick = onCheckLevel,
                    testTag = "check_ps_level"
                )

                // Task 2: Check Hoses
                FluidCheckItemButton(
                    title = "2. Inspect Hoses for Cuts, Bulges & Abrasions",
                    subtitle = "Verify high-pressure & return lines are securely routed",
                    isChecked = sim.isPowerSteeringHosesChecked,
                    onClick = onCheckHoses,
                    testTag = "check_ps_hoses"
                )

                // Task 3: Check Fittings & Leaks
                FluidCheckItemButton(
                    title = "3. Check Fittings & Steering Box for Leaks",
                    subtitle = "Ensure all hose clamps and threaded fittings are leak-free",
                    isChecked = sim.isPowerSteeringFittingsChecked,
                    onClick = onCheckFittings,
                    testTag = "check_ps_fittings"
                )

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = onInspectQuiz,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Icon(
                        imageVector = Icons.Default.Quiz,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Power Steering CDL Quiz")
                }
            }
        }
    }
}

/**
 * Reusable checklist row item with checkmark status
 */
@Composable
private fun FluidCheckItemButton(
    title: String,
    subtitle: String,
    isChecked: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = if (isChecked) PassingGreenContainer.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        border = BorderStroke(
            1.dp,
            if (isChecked) PassingGreen else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isChecked) PassingGreen else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Surface(
                shape = CircleShape,
                color = if (isChecked) PassingGreen else Color.Transparent,
                border = BorderStroke(2.dp, if (isChecked) PassingGreen else Color.Gray),
                modifier = Modifier.size(26.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    if (isChecked) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Checked",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Canvas Drawing for Engine Oil Dipstick Simulation
 */
private fun DrawScope.drawDipstickGraphic(step: DipstickStep, oilLevelPercent: Float) {
    val width = size.width
    val height = size.height

    val bladeY = height * 0.52f
    val bladeStart = width * 0.22f
    val bladeEnd = width * 0.90f
    val bladeLength = bladeEnd - bladeStart

    // Guide Tube Collar (Left)
    drawRect(
        color = Color(0xFF334155),
        topLeft = Offset(width * 0.12f, bladeY - 14f),
        size = Size(width * 0.10f, 28f)
    )
    drawRect(
        color = Color(0xFF64748B),
        topLeft = Offset(width * 0.12f, bladeY - 14f),
        size = Size(width * 0.10f, 28f),
        style = Stroke(width = 2f)
    )

    // Yellow Pull Loop Handle
    val handleCenter = Offset(width * 0.08f, bladeY)
    drawCircle(
        color = Color(0xFFEAB308),
        center = handleCenter,
        radius = 24f
    )
    drawCircle(
        color = Color(0xFF0F172A),
        center = handleCenter,
        radius = 11f
    )

    if (step == DipstickStep.IN_TUBE_INITIAL || step == DipstickStep.REINSERTED) {
        // Dipstick is inside tube: show tube with insertion animation note
        drawLine(
            color = Color(0xFF475569),
            start = Offset(width * 0.22f, bladeY),
            end = Offset(width * 0.88f, bladeY),
            strokeWidth = 14f,
            cap = StrokeCap.Round
        )
        // Stippled Tube Casing
        drawLine(
            color = Color(0xFF94A3B8),
            start = Offset(width * 0.22f, bladeY),
            end = Offset(width * 0.88f, bladeY),
            strokeWidth = 2f,
            cap = StrokeCap.Round
        )
        return
    }

    // Steel Dipstick Blade (Extended)
    drawLine(
        color = Color(0xFF94A3B8),
        start = Offset(bladeStart, bladeY),
        end = Offset(bladeEnd, bladeY),
        strokeWidth = 12f,
        cap = StrokeCap.Round
    )
    drawLine(
        color = Color(0xFFE2E8F0),
        start = Offset(bladeStart, bladeY - 2f),
        end = Offset(bladeEnd, bladeY - 2f),
        strokeWidth = 2f
    )

    // Engraved Safe Crosshatch Zone (between 60% and 88% of blade)
    val minMarkX = bladeStart + bladeLength * 0.55f
    val maxMarkX = bladeStart + bladeLength * 0.88f

    // Safe Zone Background
    drawRect(
        color = Color(0xFF64748B).copy(alpha = 0.4f),
        topLeft = Offset(minMarkX, bladeY - 5f),
        size = Size(maxMarkX - minMarkX, 10f)
    )

    // Engraved Hatches
    var hatchX = minMarkX
    while (hatchX <= maxMarkX) {
        drawLine(
            color = Color(0xFF334155),
            start = Offset(hatchX, bladeY - 5f),
            end = Offset(hatchX + 4f, bladeY + 5f),
            strokeWidth = 1.5f
        )
        hatchX += 8f
    }

    // MIN / ADD Notch
    drawLine(
        color = Color(0xFFEF4444),
        start = Offset(minMarkX, bladeY - 10f),
        end = Offset(minMarkX, bladeY + 10f),
        strokeWidth = 2.5f
    )

    // MAX / FULL Notch
    drawLine(
        color = Color(0xFF38BDF8),
        start = Offset(maxMarkX, bladeY - 10f),
        end = Offset(maxMarkX, bladeY + 10f),
        strokeWidth = 2.5f
    )

    // Oil Film Display Based on Step
    when (step) {
        DipstickStep.FIRST_PULL_DIRTY -> {
            // Dirty, smudged oil coating whole blade
            drawLine(
                color = Color(0xFF5A3810).copy(alpha = 0.85f),
                start = Offset(bladeStart + bladeLength * 0.35f, bladeY),
                end = Offset(bladeEnd - 4f, bladeY),
                strokeWidth = 10f,
                cap = StrokeCap.Round
            )
        }
        DipstickStep.WIPED_CLEAN -> {
            // Clean polished blade - no oil
        }
        DipstickStep.FINAL_PULL_READING, DipstickStep.VERIFIED_SAFE -> {
            // Crisp amber oil film up to safe 75% mark between MIN and MAX
            val oilEndX = minMarkX + (maxMarkX - minMarkX) * oilLevelPercent
            drawLine(
                color = Color(0xFFD97706).copy(alpha = 0.9f),
                start = Offset(bladeStart + bladeLength * 0.45f, bladeY),
                end = Offset(oilEndX, bladeY),
                strokeWidth = 10f,
                cap = StrokeCap.Round
            )
            // Oil Meniscus bead
            drawCircle(
                color = Color(0xFFFBBF24),
                center = Offset(oilEndX, bladeY),
                radius = 6f
            )
        }
        else -> {}
    }
}

/**
 * Canvas Drawing for Coolant Recovery Tank Simulation
 */
private fun DrawScope.drawCoolantTankGraphic(
    levelPercent: Float,
    isSightGlassChecked: Boolean,
    isCapTight: Boolean,
    isHosesInspected: Boolean
) {
    val width = size.width
    val height = size.height

    // Main Translucent Tank Reservoir (Center)
    val tankLeft = width * 0.32f
    val tankTop = height * 0.22f
    val tankWidth = width * 0.36f
    val tankHeight = height * 0.65f

    drawRoundRect(
        color = Color(0xFF1E293B),
        topLeft = Offset(tankLeft, tankTop),
        size = Size(tankWidth, tankHeight),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(16f, 16f)
    )

    // Coolant Fluid (Pink / Red Extended Life Coolant)
    val fluidTop = tankTop + tankHeight * (1f - levelPercent)
    val fluidHeight = tankHeight * levelPercent
    drawRoundRect(
        color = Color(0xFFEC4899).copy(alpha = 0.75f),
        topLeft = Offset(tankLeft + 4f, fluidTop),
        size = Size(tankWidth - 8f, fluidHeight - 4f),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(12f, 12f)
    )

    // Tank Outline
    drawRoundRect(
        color = if (isSightGlassChecked) PassingGreen else Color(0xFF38BDF8).copy(alpha = 0.6f),
        topLeft = Offset(tankLeft, tankTop),
        size = Size(tankWidth, tankHeight),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(16f, 16f),
        style = Stroke(width = if (isSightGlassChecked) 3f else 2f)
    )

    // Cold Full and Hot Full Level Guide Lines
    val hotLineY = tankTop + tankHeight * 0.25f
    val coldLineY = tankTop + tankHeight * 0.55f

    drawLine(
        color = Color.White.copy(alpha = 0.8f),
        start = Offset(tankLeft + 8f, hotLineY),
        end = Offset(tankLeft + tankWidth - 8f, hotLineY),
        strokeWidth = 2f
    )
    drawLine(
        color = Color.White.copy(alpha = 0.8f),
        start = Offset(tankLeft + 8f, coldLineY),
        end = Offset(tankLeft + tankWidth - 8f, coldLineY),
        strokeWidth = 2f
    )

    // Sight Glass Oval Window
    drawOval(
        color = Color(0xFF0F172A).copy(alpha = 0.8f),
        topLeft = Offset(tankLeft + tankWidth * 0.35f, tankTop + tankHeight * 0.30f),
        size = Size(tankWidth * 0.30f, tankHeight * 0.40f)
    )
    drawOval(
        color = if (isSightGlassChecked) PassingGreen else Color(0xFF38BDF8),
        topLeft = Offset(tankLeft + tankWidth * 0.35f, tankTop + tankHeight * 0.30f),
        size = Size(tankWidth * 0.30f, tankHeight * 0.40f),
        style = Stroke(width = 2f)
    )

    // Pressure Cap on Top
    val capLeft = tankLeft + tankWidth * 0.30f
    val capTop = height * 0.08f
    val capWidth = tankWidth * 0.40f
    val capHeight = height * 0.14f

    drawRoundRect(
        color = if (isCapTight) PassingGreen.copy(alpha = 0.3f) else Color(0xFF334155),
        topLeft = Offset(capLeft, capTop),
        size = Size(capWidth, capHeight),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(6f, 6f)
    )
    drawRoundRect(
        color = if (isCapTight) PassingGreen else Color(0xFFCBD5E1),
        topLeft = Offset(capLeft, capTop),
        size = Size(capWidth, capHeight),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(6f, 6f),
        style = Stroke(width = 2f)
    )

    // Radiator Hoses (Left Upper Hose & Right Lower Hose)
    drawLine(
        color = if (isHosesInspected) PassingGreen else Color(0xFF1E293B),
        start = Offset(tankLeft, tankTop + tankHeight * 0.30f),
        end = Offset(width * 0.10f, tankTop + tankHeight * 0.20f),
        strokeWidth = 14f,
        cap = StrokeCap.Round
    )
    drawLine(
        color = if (isHosesInspected) PassingGreen else Color(0xFF1E293B),
        start = Offset(tankLeft + tankWidth, tankTop + tankHeight * 0.70f),
        end = Offset(width * 0.90f, tankTop + tankHeight * 0.80f),
        strokeWidth = 16f,
        cap = StrokeCap.Round
    )
    // Hose Clamps
    drawRect(
        color = Color(0xFF94A3B8),
        topLeft = Offset(tankLeft - 10f, tankTop + tankHeight * 0.26f),
        size = Size(6f, 16f)
    )
    drawRect(
        color = Color(0xFF94A3B8),
        topLeft = Offset(tankLeft + tankWidth + 4f, tankTop + tankHeight * 0.66f),
        size = Size(6f, 18f)
    )
}

/**
 * Canvas Drawing for Power Steering Reservoir Simulation
 */
private fun DrawScope.drawPowerSteeringGraphic(
    levelPercent: Float,
    isLevelChecked: Boolean,
    isHosesChecked: Boolean,
    isFittingsChecked: Boolean
) {
    val width = size.width
    val height = size.height

    val canisterLeft = width * 0.35f
    val canisterTop = height * 0.20f
    val canisterWidth = width * 0.30f
    val canisterHeight = height * 0.65f

    // Canister Body
    drawRoundRect(
        color = Color(0xFF1E293B),
        topLeft = Offset(canisterLeft, canisterTop),
        size = Size(canisterWidth, canisterHeight),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(14f, 14f)
    )

    // Fluid Sight Gauge Window
    val gaugeLeft = canisterLeft + canisterWidth * 0.25f
    val gaugeTop = canisterTop + canisterHeight * 0.20f
    val gaugeWidth = canisterWidth * 0.50f
    val gaugeHeight = canisterHeight * 0.60f

    drawRect(
        color = Color(0xFF0F172A),
        topLeft = Offset(gaugeLeft, gaugeTop),
        size = Size(gaugeWidth, gaugeHeight)
    )

    // Hydraulic ATF Red Fluid
    val fluidTop = gaugeTop + gaugeHeight * (1f - levelPercent)
    val fluidHeight = gaugeHeight * levelPercent
    drawRect(
        color = Color(0xFFEF4444).copy(alpha = 0.85f),
        topLeft = Offset(gaugeLeft + 2f, fluidTop),
        size = Size(gaugeWidth - 4f, fluidHeight)
    )

    // Gauge Marks (ADD / FULL)
    drawLine(
        color = Color.White,
        start = Offset(gaugeLeft, gaugeTop + gaugeHeight * 0.30f),
        end = Offset(gaugeLeft + 12f, gaugeTop + gaugeHeight * 0.30f),
        strokeWidth = 2f
    )
    drawLine(
        color = Color(0xFFFCA5A5),
        start = Offset(gaugeLeft, gaugeTop + gaugeHeight * 0.75f),
        end = Offset(gaugeLeft + 12f, gaugeTop + gaugeHeight * 0.75f),
        strokeWidth = 2f
    )

    // Canister Outline
    drawRoundRect(
        color = if (isLevelChecked) PassingGreen else Color(0xFFF59E0B).copy(alpha = 0.6f),
        topLeft = Offset(canisterLeft, canisterTop),
        size = Size(canisterWidth, canisterHeight),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(14f, 14f),
        style = Stroke(width = if (isLevelChecked) 3f else 2f)
    )

    // Screw-on Cap Top
    val capLeft = canisterLeft + canisterWidth * 0.25f
    val capTop = height * 0.08f
    val capWidth = canisterWidth * 0.50f
    val capHeight = height * 0.12f

    drawRect(
        color = Color(0xFF0F172A),
        topLeft = Offset(capLeft, capTop),
        size = Size(capWidth, capHeight)
    )
    drawRect(
        color = Color(0xFFCBD5E1),
        topLeft = Offset(capLeft, capTop),
        size = Size(capWidth, capHeight),
        style = Stroke(width = 2f)
    )

    // High Pressure Hose to Steering Box
    drawLine(
        color = if (isHosesChecked) PassingGreen else Color(0xFF1E293B),
        start = Offset(canisterLeft + canisterWidth * 0.20f, canisterTop + canisterHeight),
        end = Offset(width * 0.18f, height * 0.95f),
        strokeWidth = 14f,
        cap = StrokeCap.Round
    )

    // Low Pressure Return Hose
    drawLine(
        color = if (isHosesChecked) PassingGreen else Color(0xFF1E293B),
        start = Offset(canisterLeft + canisterWidth * 0.80f, canisterTop + canisterHeight),
        end = Offset(width * 0.82f, height * 0.95f),
        strokeWidth = 10f,
        cap = StrokeCap.Round
    )

    // Fitting Clamps
    if (isFittingsChecked) {
        drawCircle(
            color = PassingGreen,
            center = Offset(canisterLeft + canisterWidth * 0.20f, canisterTop + canisterHeight),
            radius = 8f
        )
        drawCircle(
            color = PassingGreen,
            center = Offset(canisterLeft + canisterWidth * 0.80f, canisterTop + canisterHeight),
            radius = 7f
        )
    }
}

// ==========================================
// 2. BELTS & HOSES INTERACTIVE DASHBOARD
// ==========================================

/**
 * Dedicated Interactive Dashboard for Engine Driven Belts & Hoses inspection.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeltsAndHosesDashboardView(
    state: PreTripInspectionState,
    onSelectSubTab: (BeltAndHoseSubTab) -> Unit,
    onSelectBelt: (BeltComponentType) -> Unit,
    onSelectHose: (HoseGroupType) -> Unit,
    onCheckBeltFraying: (BeltComponentType) -> Unit,
    onCheckBeltCracksCuts: (BeltComponentType) -> Unit,
    onCheckBeltWear: (BeltComponentType) -> Unit,
    onTestBeltPlay: (BeltComponentType, Float) -> Unit,
    onCheckHoseClamps: (HoseGroupType) -> Unit,
    onCheckHoseDryRot: (HoseGroupType) -> Unit,
    onCheckHoseBubbling: (HoseGroupType) -> Unit,
    onCheckHoseLeaks: (HoseGroupType) -> Unit,
    onReset: () -> Unit,
    onInspectComponent: (String) -> Unit
) {
    val sim = state.beltsHosesSim

    // Calculate completion progress: 3 belts + 3 hoses = 6 items
    val beltsDoneCount = listOf(
        sim.alternatorBelt.isCompleted,
        sim.waterPumpBelt.isCompleted,
        sim.airCompressorBelt.isCompleted
    ).count { it }

    val hosesDoneCount = listOf(
        sim.coolantHoses.isCompleted,
        sim.heaterHoses.isCompleted,
        sim.oilHoses.isCompleted
    ).count { it }

    val totalCompleted = beltsDoneCount + hosesDoneCount
    val totalProgressPct = (totalCompleted * 100) / 6

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Dashboard Header Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "CDL PRE-TRIP • TAB 2",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                            if (sim.isAllCompleted) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = PassingGreen.copy(alpha = 0.2f)
                                ) {
                                    Text(
                                        text = "ALL VERIFIED",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = PassingGreen,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }

                        IconButton(
                            onClick = onReset,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Reset Belts and Hoses Session",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Text(
                        text = "Engine Driven Belts & Hoses",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = "Inspect Alternator, Water Pump & Air Compressor belts for condition and deflection (≤ 3/4\" play). Verify all coolant, heater, and oil hoses have tight clamps, zero dry rot, zero leaks, and zero bubbling.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Overall Progress Bar
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Progress: $totalCompleted of 6 components checked",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "$totalProgressPct%",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (sim.isAllCompleted) PassingGreen else MaterialTheme.colorScheme.primary
                            )
                        }

                        LinearProgressIndicator(
                            progress = { totalProgressPct / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp),
                            color = if (sim.isAllCompleted) PassingGreen else MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.surface
                        )
                    }
                }
            }
        }

        // Sub-Tab Switcher (Belts vs Hoses)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                BeltAndHoseSubTab.values().forEach { subTab ->
                    val isSelected = sim.activeSubTab == subTab
                    val isDone = when (subTab) {
                        BeltAndHoseSubTab.ENGINE_BELTS -> sim.allBeltsCompleted
                        BeltAndHoseSubTab.ENGINE_HOSES -> sim.allHosesCompleted
                    }

                    FilterChip(
                        selected = isSelected,
                        onClick = { onSelectSubTab(subTab) },
                        label = {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(subTab.iconEmoji)
                                Text(subTab.title, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
                                if (isDone) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = PassingGreen,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Active Section Content
        item {
            when (sim.activeSubTab) {
                BeltAndHoseSubTab.ENGINE_BELTS -> {
                    EngineDrivenBeltsSection(
                        sim = sim,
                        onSelectBelt = onSelectBelt,
                        onCheckFraying = { onCheckBeltFraying(sim.selectedBelt) },
                        onCheckCracksCuts = { onCheckBeltCracksCuts(sim.selectedBelt) },
                        onCheckWear = { onCheckBeltWear(sim.selectedBelt) },
                        onTestPlay = { play -> onTestBeltPlay(sim.selectedBelt, play) },
                        onInspectQuiz = {
                            val compId = when (sim.selectedBelt) {
                                BeltComponentType.ALTERNATOR_BELT -> "belt_alternator"
                                BeltComponentType.WATER_PUMP_BELT -> "belt_water_pump"
                                BeltComponentType.AIR_COMPRESSOR_BELT -> "belt_air_compressor"
                            }
                            onInspectComponent(compId)
                        }
                    )
                }
                BeltAndHoseSubTab.ENGINE_HOSES -> {
                    EngineHosesSection(
                        sim = sim,
                        onSelectHose = onSelectHose,
                        onCheckClamps = { onCheckHoseClamps(sim.selectedHose) },
                        onCheckDryRot = { onCheckHoseDryRot(sim.selectedHose) },
                        onCheckBubbling = { onCheckHoseBubbling(sim.selectedHose) },
                        onCheckLeaks = { onCheckHoseLeaks(sim.selectedHose) },
                        onInspectQuiz = {
                            val compId = when (sim.selectedHose) {
                                HoseGroupType.COOLANT_RADIATOR -> "hose_coolant"
                                HoseGroupType.HEATER_CORE -> "hose_heater"
                                HoseGroupType.OIL_AND_TURBO -> "hose_oil"
                            }
                            onInspectComponent(compId)
                        }
                    )
                }
            }
        }

        // Examiner Pro-Tips Card
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "CDL Examiner Scoring Rules",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Text(
                        text = "1. Belt Play Rule: Push on the belt midway between pulleys. Deflection must be between 1/2 and 3/4 of an inch. If play exceeds 3/4\", the belt must be tensioned or replaced.\n" +
                                "2. Drive Verbal Declaration: You MUST tell the examiner whether each accessory (alternator, water pump, air compressor) is BELT-DRIVEN or GEAR-DRIVEN.\n" +
                                "3. Hose Inspection: Squeeze hoses to test for internal core failure (bubbling/sponginess). Clamps must be tight with zero coolant, oil, or fuel seepage.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

/**
 * Interactive Belts Section (Alternator, Water Pump, Air Compressor)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EngineDrivenBeltsSection(
    sim: BeltsAndHosesSimState,
    onSelectBelt: (BeltComponentType) -> Unit,
    onCheckFraying: () -> Unit,
    onCheckCracksCuts: () -> Unit,
    onCheckWear: () -> Unit,
    onTestPlay: (Float) -> Unit,
    onInspectQuiz: () -> Unit
) {
    val currentBelt = sim.selectedBelt
    val beltState = sim.getBeltState(currentBelt)

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Belt Component Selector
        Text(
            text = "Select Belt to Inspect:",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            BeltComponentType.values().forEach { belt ->
                val isSelected = currentBelt == belt
                val isDone = sim.getBeltState(belt).isCompleted

                FilterChip(
                    selected = isSelected,
                    onClick = { onSelectBelt(belt) },
                    label = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(belt.title, style = MaterialTheme.typography.labelSmall)
                            if (isDone) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = PassingGreen,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Belt Pulley & Deflection Graphic Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0B132B)),
            border = BorderStroke(1.dp, Color(0xFF1E293B))
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = currentBelt.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = currentBelt.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF1E293B)
                    ) {
                        Text(
                            text = currentBelt.typicalDrive,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF38BDF8),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                // Interactive Belt & Pulley Canvas
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .testTag("belt_pulley_canvas")
                ) {
                    drawEngineBeltGraphic(
                        belt = currentBelt,
                        state = beltState
                    )
                }

                // Deflection Indicator Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF0F172A), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Measured Belt Deflection:",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFFCBD5E1)
                        )
                        Text(
                            text = "${beltState.measuredPlayInches}\" (${if (beltState.measuredPlayInches <= 0.75f) "Safe ≤ 3/4\"" else "EXCESSIVE"})",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (beltState.measuredPlayInches <= 0.75f) PassingGreen else FailingRed
                        )
                    }

                    if (beltState.isPlayTested) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Tested",
                            tint = PassingGreen,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        // Hands-on Inspection Tasks Card
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Perform CDL Examiner Checklist for ${currentBelt.title}:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )

                // Task 1: Check Fraying
                FluidCheckItemButton(
                    title = "1. Check for Fraying & Stripped Threads",
                    subtitle = "Verify belt edges are smooth with no loose cords or strands",
                    isChecked = beltState.isFrayingChecked,
                    onClick = onCheckFraying,
                    testTag = "check_fraying"
                )

                // Task 2: Check Cracks & Cuts
                FluidCheckItemButton(
                    title = "2. Check for Cracks, Splits & Cuts",
                    subtitle = "Inspect rubber ribs for dry rot or transverse cracks > 1/2\"",
                    isChecked = beltState.isCracksCutsChecked,
                    onClick = onCheckCracksCuts,
                    testTag = "check_cracks"
                )

                // Task 3: Check Excessive Wear / Glazing
                FluidCheckItemButton(
                    title = "3. Check for Excessive Wear & Glazing",
                    subtitle = "Ensure belt is not slick, burnt, shiny, or slipping on pulleys",
                    isChecked = beltState.isExcessiveWearChecked,
                    onClick = onCheckWear,
                    testTag = "check_wear"
                )

                // Task 4: Interactive Deflection / Play Push Test
                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (beltState.isPlayTested) PassingGreen.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "4. Push Belt at Center: Test Deflection Play",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                            if (beltState.isPlayTested) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = PassingGreen,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Text(
                            text = "CDL Criterion: Deflection must not exceed 1/2\" to 3/4\" of play when pushed firmly.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        // Quick interactive play buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf(
                                0.25f to "1/4\" (Tight)",
                                0.50f to "1/2\" (Ideal)",
                                0.75f to "3/4\" (Max Safe)",
                                1.00f to "1\" (Loose FAIL)"
                            ).forEach { (value, label) ->
                                val isChosen = beltState.measuredPlayInches == value && beltState.isPlayTested
                                Button(
                                    onClick = { onTestPlay(value) },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (isChosen) {
                                            if (value <= 0.75f) PassingGreen else FailingRed
                                        } else MaterialTheme.colorScheme.surface
                                    ),
                                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (isChosen) Color.White else MaterialTheme.colorScheme.onSurface,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Component Quiz & Recitation launcher button
                Button(
                    onClick = onInspectQuiz,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Icon(
                        imageVector = Icons.Default.Quiz,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Recite Examiner Criteria & Take Quiz")
                }
            }
        }
    }
}

/**
 * Interactive Hoses Section (Coolant, Heater, Oil Hoses)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EngineHosesSection(
    sim: BeltsAndHosesSimState,
    onSelectHose: (HoseGroupType) -> Unit,
    onCheckClamps: () -> Unit,
    onCheckDryRot: () -> Unit,
    onCheckBubbling: () -> Unit,
    onCheckLeaks: () -> Unit,
    onInspectQuiz: () -> Unit
) {
    val currentHose = sim.selectedHose
    val hoseState = sim.getHoseState(currentHose)

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Hose Group Selector
        Text(
            text = "Select Hose Group to Inspect:",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HoseGroupType.values().forEach { hose ->
                val isSelected = currentHose == hose
                val isDone = sim.getHoseState(hose).isCompleted

                FilterChip(
                    selected = isSelected,
                    onClick = { onSelectHose(hose) },
                    label = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(hose.title, style = MaterialTheme.typography.labelSmall)
                            if (isDone) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = PassingGreen,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Hose Graphic Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0B132B)),
            border = BorderStroke(1.dp, Color(0xFF1E293B))
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = currentHose.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = currentHose.subtitle,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF1E293B)
                    ) {
                        Text(
                            text = currentHose.fluidCarried,
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFFF59E0B),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                // Canvas showing hose section and clamps
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .testTag("hose_canvas")
                ) {
                    drawEngineHosesGraphic(
                        hose = currentHose,
                        state = hoseState
                    )
                }
            }
        }

        // Hands-on Inspection Tasks Card
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Perform CDL Examiner Checklist for ${currentHose.title}:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )

                // Task 1: Check Clamps Tight
                FluidCheckItemButton(
                    title = "1. Inspect Clamps: Tight & Securely Fastened",
                    subtitle = "Verify all screw / spring clamps are snug with zero loose connections",
                    isChecked = hoseState.isClampsTightChecked,
                    onClick = onCheckClamps,
                    testTag = "check_hose_clamps"
                )

                // Task 2: Check Dry Rot
                FluidCheckItemButton(
                    title = "2. Inspect Rubber: Zero Dry Rot or Weather Checking",
                    subtitle = "Confirm rubber is supple, not brittle, crazed, hardened, or cracked",
                    isChecked = hoseState.isDryRotChecked,
                    onClick = onCheckDryRot,
                    testTag = "check_hose_dryrot"
                )

                // Task 3: Check Bubbling & Soft Spots
                FluidCheckItemButton(
                    title = "3. Squeeze Test: Zero Bubbling, Swelling, or Soft Spots",
                    subtitle = "Squeeze hose along entire length; rubber must be firm, not spongy or swollen",
                    isChecked = hoseState.isBubblingChecked,
                    onClick = onCheckBubbling,
                    testTag = "check_hose_bubbling"
                )

                // Task 4: Check Leaks
                FluidCheckItemButton(
                    title = "4. Leak Inspection: Zero Fluid Weeping or Drips",
                    subtitle = "Ensure zero puddles, drips, or wet residue at junctions and connections",
                    isChecked = hoseState.isLeaksChecked,
                    onClick = onCheckLeaks,
                    testTag = "check_hose_leaks"
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Component Quiz & Recitation launcher button
                Button(
                    onClick = onInspectQuiz,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Icon(
                        imageVector = Icons.Default.Quiz,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Recite Examiner Criteria & Take Quiz")
                }
            }
        }
    }
}

/**
 * Canvas drawing for interactive Engine Belt and Pulleys.
 */
private fun DrawScope.drawEngineBeltGraphic(
    belt: BeltComponentType,
    state: BeltInspectionItemState
) {
    val width = size.width
    val height = size.height

    // Main Crankshaft Pulley (Bottom Center)
    val crankCenter = Offset(width * 0.45f, height * 0.72f)
    val crankRadius = 32f

    // Alternator / Accessory Pulley (Top Right)
    val altCenter = Offset(width * 0.75f, height * 0.32f)
    val altRadius = 24f

    // Water Pump Pulley (Top Left)
    val wpCenter = Offset(width * 0.20f, height * 0.35f)
    val wpRadius = 26f

    // Idler / Tensioner Pulley (Mid-Right)
    val idlerCenter = Offset(width * 0.52f, height * 0.25f)
    val idlerRadius = 18f

    val beltColor = if (state.isCompleted) PassingGreen else Color(0xFF1E293B)
    val strokeColor = if (state.isCompleted) PassingGreen else Color(0xFF64748B)

    // Draw pulleys
    listOf(
        crankCenter to crankRadius,
        altCenter to altRadius,
        wpCenter to wpRadius,
        idlerCenter to idlerRadius
    ).forEach { (center, radius) ->
        // Pulley body
        drawCircle(
            color = Color(0xFF0F172A),
            radius = radius,
            center = center
        )
        // Pulley rim
        drawCircle(
            color = strokeColor,
            radius = radius,
            center = center,
            style = Stroke(width = 3f)
        )
        // Center bolt
        drawCircle(
            color = Color(0xFF94A3B8),
            radius = 6f,
            center = center
        )
    }

    // Belt Routing Path between pulleys
    val beltPath = androidx.compose.ui.graphics.Path().apply {
        moveTo(wpCenter.x, wpCenter.y - wpRadius)
        lineTo(idlerCenter.x, idlerCenter.y - idlerRadius)
        lineTo(altCenter.x, altCenter.y - altRadius)
        lineTo(altCenter.x + altRadius, altCenter.y)
        lineTo(crankCenter.x + crankRadius, crankCenter.y)
        lineTo(crankCenter.x, crankCenter.y + crankRadius)
        lineTo(wpCenter.x - wpRadius, wpCenter.y)
        close()
    }

    drawPath(
        path = beltPath,
        color = beltColor,
        style = Stroke(width = 8f)
    )

    // Interactive Deflection Indicator (Push arrow in the center span)
    val pushSpanCenter = Offset((wpCenter.x + altCenter.x) / 2f, height * 0.20f)
    val playOffset = (state.measuredPlayInches / 0.75f).coerceIn(0.2f, 1.5f) * 16f

    // Push vector line
    drawLine(
        color = if (state.measuredPlayInches <= 0.75f) PassingGreen else FailingRed,
        start = pushSpanCenter,
        end = Offset(pushSpanCenter.x, pushSpanCenter.y + playOffset),
        strokeWidth = 4f,
        cap = StrokeCap.Round
    )

    // Arrowhead
    drawCircle(
        color = if (state.measuredPlayInches <= 0.75f) PassingGreen else FailingRed,
        radius = 5f,
        center = Offset(pushSpanCenter.x, pushSpanCenter.y + playOffset)
    )
}

/**
 * Canvas drawing for interactive Engine Hoses and Clamps.
 */
private fun DrawScope.drawEngineHosesGraphic(
    hose: HoseGroupType,
    state: HoseInspectionItemState
) {
    val width = size.width
    val height = size.height

    val hoseLeft = width * 0.15f
    val hoseRight = width * 0.85f
    val hoseY = height * 0.50f
    val hoseThickness = 28f

    val hoseColor = if (state.isCompleted) PassingGreen else Color(0xFF1E293B)

    // Hose Main Body
    drawLine(
        color = hoseColor,
        start = Offset(hoseLeft, hoseY),
        end = Offset(hoseRight, hoseY),
        strokeWidth = hoseThickness,
        cap = StrokeCap.Round
    )

    // Rubber Texture Ribbing lines
    val ribStep = (hoseRight - hoseLeft) / 8f
    for (i in 1..7) {
        val ribX = hoseLeft + i * ribStep
        drawLine(
            color = Color(0xFF334155),
            start = Offset(ribX, hoseY - hoseThickness / 2f),
            end = Offset(ribX, hoseY + hoseThickness / 2f),
            strokeWidth = 2f
        )
    }

    // Steel Hose Clamps on both ends
    val clampColor = if (state.isClampsTightChecked) PassingGreen else Color(0xFF94A3B8)

    // Left Clamp
    drawRoundRect(
        color = clampColor,
        topLeft = Offset(hoseLeft + 10f, hoseY - hoseThickness / 2f - 4f),
        size = Size(10f, hoseThickness + 8f),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(3f, 3f)
    )

    // Right Clamp
    drawRoundRect(
        color = clampColor,
        topLeft = Offset(hoseRight - 20f, hoseY - hoseThickness / 2f - 4f),
        size = Size(10f, hoseThickness + 8f),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(3f, 3f)
    )

    // Clamp Screws
    drawCircle(
        color = Color(0xFF0F172A),
        radius = 3f,
        center = Offset(hoseLeft + 15f, hoseY - hoseThickness / 2f - 1f)
    )
    drawCircle(
        color = Color(0xFF0F172A),
        radius = 3f,
        center = Offset(hoseRight - 15f, hoseY - hoseThickness / 2f - 1f)
    )
}

// ==========================================
// 3. STEERING COMPONENTS INTERACTIVE DASHBOARD
// ==========================================

/**
 * Dedicated Interactive Dashboard for Steering Components:
 * - Steering Gear Box & Hoses
 * - Pitman Arm, Drag Link & Tie Rod
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SteeringDashboardView(
    state: PreTripInspectionState,
    onSelectSubTab: (SteeringSubTab) -> Unit,
    onCheckFrameMountBolts: () -> Unit,
    onCheckGearBoxCasting: () -> Unit,
    onCheckHoses: () -> Unit,
    onCheckShaftPlay: () -> Unit,
    onCheckPitmanArm: () -> Unit,
    onCheckDragLink: () -> Unit,
    onCheckTieRod: () -> Unit,
    onTestJointPlay: (Float) -> Unit,
    onReset: () -> Unit,
    onInspectComponent: (String) -> Unit
) {
    val sim = state.steeringSim
    val totalDone = (if (sim.gearBoxState.isCompleted) 1 else 0) + (if (sim.linkageState.isCompleted) 1 else 0)
    val progressPct = (totalDone * 100) / 2

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Header Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "CDL PRE-TRIP • TAB 2",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                            if (sim.isAllCompleted) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = PassingGreen.copy(alpha = 0.2f)
                                ) {
                                    Text(
                                        text = "ALL VERIFIED",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = PassingGreen,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }

                        IconButton(
                            onClick = onReset,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Reset Steering Components Session",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Text(
                        text = "Steering Components",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = "Inspect Steering Gear Box & Hoses (securely mounted to frame with no loose/missing bolts or cracks, leak-free hoses) and Linkages (Pitman Arm, Drag Link, and Tie Rod with castle nuts and cotter pins present, tight, with zero excessive play).",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Overall Progress Bar
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Progress: $totalDone of 2 systems verified",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "$progressPct%",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (sim.isAllCompleted) PassingGreen else MaterialTheme.colorScheme.primary
                            )
                        }

                        LinearProgressIndicator(
                            progress = { progressPct / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp),
                            color = if (sim.isAllCompleted) PassingGreen else MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.surface
                        )
                    }
                }
            }
        }

        // Sub-Tab Switcher (Gear Box & Hoses vs Linkages)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SteeringSubTab.values().forEach { subTab ->
                    val isSelected = sim.activeSubTab == subTab
                    val isDone = when (subTab) {
                        SteeringSubTab.GEAR_BOX_AND_HOSES -> sim.gearBoxState.isCompleted
                        SteeringSubTab.STEERING_LINKAGE -> sim.linkageState.isCompleted
                    }

                    FilterChip(
                        selected = isSelected,
                        onClick = { onSelectSubTab(subTab) },
                        label = {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(subTab.iconEmoji)
                                Text(subTab.title, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
                                if (isDone) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = PassingGreen,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Active Section Content
        item {
            when (sim.activeSubTab) {
                SteeringSubTab.GEAR_BOX_AND_HOSES -> {
                    SteeringGearBoxSection(
                        state = sim.gearBoxState,
                        onCheckFrameMountBolts = onCheckFrameMountBolts,
                        onCheckGearBoxCasting = onCheckGearBoxCasting,
                        onCheckHoses = onCheckHoses,
                        onCheckShaftPlay = onCheckShaftPlay,
                        onInspectQuiz = { onInspectComponent("steering_gear_box") }
                    )
                }
                SteeringSubTab.STEERING_LINKAGE -> {
                    SteeringLinkageSection(
                        state = sim.linkageState,
                        onCheckPitmanArm = onCheckPitmanArm,
                        onCheckDragLink = onCheckDragLink,
                        onCheckTieRod = onCheckTieRod,
                        onTestJointPlay = onTestJointPlay,
                        onInspectQuiz = { onInspectComponent("steering_linkage") }
                    )
                }
            }
        }

        // Examiner Pro-Tips Card
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "CDL Examiner Steering Failure Criteria",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Text(
                        text = "1. Castle Nuts & Cotter Pins: A missing cotter pin or loose castle nut on the Pitman arm, drag link, or tie rod is an IMMEDIATE CDL AUTO-FAIL.\n" +
                                "2. Frame Mount: All steering gear box bolts must be tight with no missing bolts. Look for rust trails or shiny metal rings indicating loose bolts.\n" +
                                "3. Hoses & Leaks: Power steering lines must have zero cuts, bulges, or leaks. A burst hose causes catastrophic loss of steering assist.\n" +
                                "4. Zero Excessive Play: Steering linkage ball joints must be tight with zero loose play when shaken.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

/**
 * Interactive Steering Gear Box & Hoses Section
 */
@Composable
private fun SteeringGearBoxSection(
    state: SteeringGearBoxSimState,
    onCheckFrameMountBolts: () -> Unit,
    onCheckGearBoxCasting: () -> Unit,
    onCheckHoses: () -> Unit,
    onCheckShaftPlay: () -> Unit,
    onInspectQuiz: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Graphic Canvas Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0B132B)),
            border = BorderStroke(1.dp, Color(0xFF1E293B))
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Steering Gear Box & Power Steering Lines",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Mounted to Left Chassis Frame Rail",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (state.isCompleted) PassingGreen.copy(alpha = 0.2f) else Color(0xFF1E293B)
                    ) {
                        Text(
                            text = if (state.isCompleted) "PASSED" else "NEEDS INSPECTION",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (state.isCompleted) PassingGreen else SafetyAmber,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                // Canvas showing frame rail, gear box body, mounting bolts, and hoses
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .testTag("steering_gearbox_canvas")
                ) {
                    drawSteeringGearBoxGraphic(state = state)
                }
            }
        }

        // Hands-on Inspection Tasks Card
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Perform CDL Examiner Checklist for Steering Gear Box & Hoses:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )

                // Task 1: Frame Mount Bolts
                FluidCheckItemButton(
                    title = "1. Frame Mount: Secure with No Missing/Loose Bolts",
                    subtitle = "Verify all bolts are tight to frame rail; no cracks in frame or box casting",
                    isChecked = state.isFrameMountBoltsChecked,
                    onClick = onCheckFrameMountBolts,
                    testTag = "check_gearbox_bolts"
                )

                // Task 2: Gear Box Casting & Fluid Leaks
                FluidCheckItemButton(
                    title = "2. Gear Box Casting: Not Cracked & Zero Leaks",
                    subtitle = "Check sector shaft seal and housing for power steering fluid leaks or damage",
                    isChecked = state.isGearBoxCastingChecked,
                    onClick = onCheckGearBoxCasting,
                    testTag = "check_gearbox_casting"
                )

                // Task 3: Power Steering Hoses
                FluidCheckItemButton(
                    title = "3. Power Steering Hoses: Leak-Free & Tight Fittings",
                    subtitle = "Inspect high pressure and return hoses for no cuts, bulges, abrasions, or leaks",
                    isChecked = state.isHosesInspected,
                    onClick = onCheckHoses,
                    testTag = "check_gearbox_hoses"
                )

                // Task 4: Steering Shaft & Universal Joints
                FluidCheckItemButton(
                    title = "4. Steering Shaft & U-Joints: No Loose Joints or Slop",
                    subtitle = "Check universal joint and slip joint; no binding, cracks, or excessive play",
                    isChecked = state.isShaftPlayChecked,
                    onClick = onCheckShaftPlay,
                    testTag = "check_gearbox_shaft"
                )

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = onInspectQuiz,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Icon(
                        imageVector = Icons.Default.Quiz,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Recite Examiner Criteria & Take Quiz")
                }
            }
        }
    }
}

/**
 * Interactive Pitman Arm, Drag Link & Tie Rod Section
 */
@Composable
private fun SteeringLinkageSection(
    state: SteeringLinkageSimState,
    onCheckPitmanArm: () -> Unit,
    onCheckDragLink: () -> Unit,
    onCheckTieRod: () -> Unit,
    onTestJointPlay: (Float) -> Unit,
    onInspectQuiz: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Graphic Canvas Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0B132B)),
            border = BorderStroke(1.dp, Color(0xFF1E293B))
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Pitman Arm, Drag Link & Tie Rod Assembly",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Connecting Links, Arms & Ball Joints",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (state.isCompleted) PassingGreen.copy(alpha = 0.2f) else Color(0xFF1E293B)
                    ) {
                        Text(
                            text = if (state.isCompleted) "PASSED" else "NEEDS INSPECTION",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (state.isCompleted) PassingGreen else SafetyAmber,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                // Canvas showing Pitman Arm, Drag Link, and Tie Rod with Castle Nuts & Cotter Pins
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .testTag("steering_linkage_canvas")
                ) {
                    drawSteeringLinkageGraphic(state = state)
                }

                // Joint Free Play Status
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF0F172A), RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Joint Free Play Check:",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFFCBD5E1)
                        )
                        Text(
                            text = "${state.measuredFreePlayMm} mm (${if (state.measuredFreePlayMm <= 1.5f) "Firm & Safe: Zero Excessive Play" else "LOOSE JOINT - FAIL"})",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (state.measuredFreePlayMm <= 1.5f) PassingGreen else FailingRed
                        )
                    }

                    if (state.isJointPlayTested) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Tested",
                            tint = PassingGreen,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        // Hands-on Inspection Tasks Card
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Perform CDL Examiner Checklist for Steering Linkages:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )

                // Task 1: Pitman Arm
                FluidCheckItemButton(
                    title = "1. Pitman Arm: Castle Nut & Cotter Pin Tight",
                    subtitle = "Verify securely fastened to sector shaft; castle nut tight with cotter pin present and spread",
                    isChecked = state.isPitmanArmChecked,
                    onClick = onCheckPitmanArm,
                    testTag = "check_pitman_arm"
                )

                // Task 2: Drag Link
                FluidCheckItemButton(
                    title = "2. Drag Link: Not Bent/Cracked, Castle Nuts & Pins Present",
                    subtitle = "Connects Pitman arm to steering knuckle; ball sockets tight, rubber boots intact",
                    isChecked = state.isDragLinkChecked,
                    onClick = onCheckDragLink,
                    testTag = "check_drag_link"
                )

                // Task 3: Tie Rod & Tie Rod Ends
                FluidCheckItemButton(
                    title = "3. Tie Rod: Straight & Castle Nuts/Pins Tight",
                    subtitle = "Spans across front steering axle; rod not bent or cracked, ends tight with cotter pins in place",
                    isChecked = state.isTieRodChecked,
                    onClick = onCheckTieRod,
                    testTag = "check_tie_rod"
                )

                // Task 4: Free Play Shake Test
                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (state.isJointPlayTested) PassingGreen.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "4. Shake Test: Check for Excessive Play & Loose Joints",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                            if (state.isJointPlayTested) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = PassingGreen,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }

                        Text(
                            text = "CDL Criterion: Grasp drag link and tie rod firmly; must be zero noticeable looseness, slop, or vertical ball joint give.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        // Quick interactive play buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf(
                                0.2f to "0.2mm (Tight)",
                                0.5f to "0.5mm (Firm Safe)",
                                1.0f to "1.0mm (Normal)",
                                3.0f to "3.0mm (Slop FAIL)"
                            ).forEach { (value, label) ->
                                val isChosen = state.measuredFreePlayMm == value && state.isJointPlayTested
                                Button(
                                    onClick = { onTestJointPlay(value) },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (isChosen) {
                                            if (value <= 1.5f) PassingGreen else FailingRed
                                        } else MaterialTheme.colorScheme.surface
                                    ),
                                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = label,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (isChosen) Color.White else MaterialTheme.colorScheme.onSurface,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = onInspectQuiz,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Icon(
                        imageVector = Icons.Default.Quiz,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Recite Examiner Criteria & Take Quiz")
                }
            }
        }
    }
}

/**
 * Canvas drawing for Steering Gear Box, Frame Rail, and Hoses.
 */
private fun DrawScope.drawSteeringGearBoxGraphic(state: SteeringGearBoxSimState) {
    val width = size.width
    val height = size.height

    val frameColor = Color(0xFF1E293B)
    val boxColor = if (state.isGearBoxCastingChecked) PassingGreen.copy(alpha = 0.25f) else Color(0xFF131D31)
    val boxStroke = if (state.isGearBoxCastingChecked) PassingGreen else Color(0xFF38BDF8)
    val boltColor = if (state.isFrameMountBoltsChecked) PassingGreen else Color(0xFFCBD5E1)
    val hoseColor = if (state.isHosesInspected) PassingGreen else Color(0xFF0F172A)

    // 1. Frame Rail (Horizontal C-Channel at Top)
    drawRect(
        color = frameColor,
        topLeft = Offset(width * 0.05f, height * 0.15f),
        size = Size(width * 0.90f, height * 0.20f)
    )
    drawRect(
        color = Color(0xFF334155),
        topLeft = Offset(width * 0.05f, height * 0.15f),
        size = Size(width * 0.90f, height * 0.20f),
        style = Stroke(width = 2f)
    )

    // 2. Steering Gear Box Body (Cast iron housing bolted to frame)
    val boxLeft = width * 0.25f
    val boxTop = height * 0.25f
    val boxWidth = width * 0.35f
    val boxHeight = height * 0.55f

    drawRoundRect(
        color = boxColor,
        topLeft = Offset(boxLeft, boxTop),
        size = Size(boxWidth, boxHeight),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(10f, 10f)
    )
    drawRoundRect(
        color = boxStroke,
        topLeft = Offset(boxLeft, boxTop),
        size = Size(boxWidth, boxHeight),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(10f, 10f),
        style = Stroke(width = 3f)
    )

    // 3. Four Mounting Bolts to Frame
    val boltRadius = 6f
    listOf(
        Offset(boxLeft + 18f, boxTop + 14f),
        Offset(boxLeft + boxWidth - 18f, boxTop + 14f),
        Offset(boxLeft + 18f, boxTop + 36f),
        Offset(boxLeft + boxWidth - 18f, boxTop + 36f)
    ).forEach { boltCenter ->
        drawCircle(
            color = boltColor,
            radius = boltRadius,
            center = boltCenter
        )
        drawCircle(
            color = Color(0xFF0F172A),
            radius = 2.5f,
            center = boltCenter
        )
    }

    // 4. Steering Shaft & U-Joint entering top of gear box
    val shaftColor = if (state.isShaftPlayChecked) PassingGreen else Color(0xFF94A3B8)
    drawLine(
        color = shaftColor,
        start = Offset(boxLeft + boxWidth * 0.5f, 0f),
        end = Offset(boxLeft + boxWidth * 0.5f, boxTop),
        strokeWidth = 10f,
        cap = StrokeCap.Round
    )
    // U-Joint Knuckle
    drawCircle(
        color = shaftColor,
        radius = 8f,
        center = Offset(boxLeft + boxWidth * 0.5f, boxTop * 0.5f)
    )

    // 5. Power Steering Hydraulic Lines (Pressure & Return to pump)
    drawLine(
        color = hoseColor,
        start = Offset(boxLeft + boxWidth, boxTop + 30f),
        end = Offset(width * 0.88f, height * 0.65f),
        strokeWidth = 8f,
        cap = StrokeCap.Round
    )
    drawLine(
        color = hoseColor,
        start = Offset(boxLeft + boxWidth, boxTop + 50f),
        end = Offset(width * 0.88f, height * 0.80f),
        strokeWidth = 6f,
        cap = StrokeCap.Round
    )

    // Hose Fittings
    drawCircle(color = boltColor, radius = 5f, center = Offset(boxLeft + boxWidth, boxTop + 30f))
    drawCircle(color = boltColor, radius = 4f, center = Offset(boxLeft + boxWidth, boxTop + 50f))
}

/**
 * Canvas drawing for Pitman Arm, Drag Link, and Tie Rod.
 */
private fun DrawScope.drawSteeringLinkageGraphic(state: SteeringLinkageSimState) {
    val width = size.width
    val height = size.height

    val pitmanColor = if (state.isPitmanArmChecked) PassingGreen else Color(0xFF38BDF8)
    val dragLinkColor = if (state.isDragLinkChecked) PassingGreen else Color(0xFF94A3B8)
    val tieRodColor = if (state.isTieRodChecked) PassingGreen else Color(0xFF64748B)
    val pinColor = if (state.isPitmanArmChecked && state.isDragLinkChecked && state.isTieRodChecked) PassingGreen else Color(0xFFF59E0B)

    // 1. Sector Shaft & Pitman Arm (Top-Left descending down)
    val pitmanTop = Offset(width * 0.20f, height * 0.15f)
    val pitmanBottom = Offset(width * 0.28f, height * 0.58f)

    drawLine(
        color = pitmanColor,
        start = pitmanTop,
        end = pitmanBottom,
        strokeWidth = 14f,
        cap = StrokeCap.Round
    )
    // Sector Shaft Nut
    drawCircle(color = Color(0xFFCBD5E1), radius = 9f, center = pitmanTop)

    // Pitman Castle Nut & Cotter Pin at Drag Link junction
    drawCircle(color = pinColor, radius = 8f, center = pitmanBottom)
    drawCircle(color = Color(0xFF0F172A), radius = 3f, center = pitmanBottom)

    // 2. Drag Link (Diagonal bar from Pitman arm to steering knuckle)
    val knuckleJoint = Offset(width * 0.72f, height * 0.62f)

    drawLine(
        color = dragLinkColor,
        start = pitmanBottom,
        end = knuckleJoint,
        strokeWidth = 10f,
        cap = StrokeCap.Round
    )
    // Drag Link Knuckle Castle Nut & Cotter Pin
    drawCircle(color = pinColor, radius = 8f, center = knuckleJoint)
    drawCircle(color = Color(0xFF0F172A), radius = 3f, center = knuckleJoint)

    // 3. Tie Rod (Horizontal rod spanning across front axle at bottom)
    val tieRodY = height * 0.82f
    val tieRodLeft = Offset(width * 0.10f, tieRodY)
    val tieRodRight = Offset(width * 0.90f, tieRodY)

    drawLine(
        color = tieRodColor,
        start = tieRodLeft,
        end = tieRodRight,
        strokeWidth = 12f,
        cap = StrokeCap.Round
    )

    // Tie Rod Ends & Castle Nuts on both sides
    listOf(tieRodLeft, tieRodRight).forEach { endCenter ->
        drawCircle(color = pinColor, radius = 9f, center = endCenter)
        drawCircle(color = Color(0xFF0F172A), radius = 3.5f, center = endCenter)
    }

    // Free Play Shake indicator if tested
    if (state.isJointPlayTested) {
        val indicatorColor = if (state.measuredFreePlayMm <= 1.5f) PassingGreen else FailingRed
        drawLine(
            color = indicatorColor,
            start = Offset(width * 0.50f, tieRodY - 14f),
            end = Offset(width * 0.50f, tieRodY + 14f),
            strokeWidth = 3f,
            cap = StrokeCap.Round
        )
    }
}

// ==========================================
// 4. FRONT SUSPENSION INTERACTIVE DASHBOARD
// ==========================================

/**
 * Dedicated Interactive Dashboard for Front Suspension:
 * - Leaf Springs (cracks, breaks, shifting & missing leaves)
 * - U-Bolts & Spring Mounts (tight bolts, hangers & no rust trails)
 * - Shock Absorber (top/bottom mounting, oil leaks & rubber bushings)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FrontSuspensionDashboardView(
    state: PreTripInspectionState,
    onSelectSubTab: (SuspensionSubTab) -> Unit,
    onCheckLeafSpringsCracks: () -> Unit,
    onCheckLeafSpringsShifting: () -> Unit,
    onCheckLeafSpringsMissing: () -> Unit,
    onCheckUBoltsTight: () -> Unit,
    onCheckUBoltsRustTrails: () -> Unit,
    onCheckSpringHangers: () -> Unit,
    onCheckBushingPins: () -> Unit,
    onCheckShockTopMount: () -> Unit,
    onCheckShockBottomMount: () -> Unit,
    onCheckShockOilLeaks: () -> Unit,
    onCheckShockBushings: () -> Unit,
    onReset: () -> Unit,
    onInspectComponent: (String) -> Unit
) {
    val sim = state.suspensionSim
    val totalDone = (if (sim.leafSpringsState.isCompleted) 1 else 0) +
            (if (sim.uBoltMountsState.isCompleted) 1 else 0) +
            (if (sim.shockState.isCompleted) 1 else 0)
    val progressPct = (totalDone * 100) / 3

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Header Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "CDL PRE-TRIP • TAB 4",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                            if (sim.isAllCompleted) {
                                Surface(
                                    shape = RoundedCornerShape(6.dp),
                                    color = PassingGreen.copy(alpha = 0.2f)
                                ) {
                                    Text(
                                        text = "ALL VERIFIED",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = PassingGreen,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }

                        IconButton(
                            onClick = onReset,
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Reset Suspension Session",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Text(
                        text = "Front Suspension",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = "Inspect Leaf Springs for cracks, breaks, shifting, or missing leaves. Verify U-Bolts and Spring Mounts are securely bolted to frame with no rust trails. Confirm Shock Absorber is securely mounted top & bottom with no oil leaks or worn rubber bushings.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    // Overall Progress Bar
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Progress: $totalDone of 3 suspension components verified",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "$progressPct%",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (sim.isAllCompleted) PassingGreen else MaterialTheme.colorScheme.primary
                            )
                        }

                        LinearProgressIndicator(
                            progress = { progressPct / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp),
                            color = if (sim.isAllCompleted) PassingGreen else MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.surface
                        )
                    }
                }
            }
        }

        // Sub-Tab Switcher
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SuspensionSubTab.values().forEach { subTab ->
                    val isSelected = sim.activeSubTab == subTab
                    val isDone = when (subTab) {
                        SuspensionSubTab.LEAF_SPRINGS -> sim.leafSpringsState.isCompleted
                        SuspensionSubTab.U_BOLTS_AND_MOUNTS -> sim.uBoltMountsState.isCompleted
                        SuspensionSubTab.SHOCK_ABSORBER -> sim.shockState.isCompleted
                    }

                    FilterChip(
                        selected = isSelected,
                        onClick = { onSelectSubTab(subTab) },
                        label = {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(subTab.iconEmoji)
                                Text(
                                    subTab.title,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                                if (isDone) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = PassingGreen,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Active Section Content
        item {
            when (sim.activeSubTab) {
                SuspensionSubTab.LEAF_SPRINGS -> {
                    LeafSpringsSection(
                        state = sim.leafSpringsState,
                        onCheckCracks = onCheckLeafSpringsCracks,
                        onCheckShifting = onCheckLeafSpringsShifting,
                        onCheckMissing = onCheckLeafSpringsMissing,
                        onInspectQuiz = { onInspectComponent("susp_leaf_springs") }
                    )
                }
                SuspensionSubTab.U_BOLTS_AND_MOUNTS -> {
                    UBoltMountsSection(
                        state = sim.uBoltMountsState,
                        onCheckUBoltsTight = onCheckUBoltsTight,
                        onCheckRustTrails = onCheckUBoltsRustTrails,
                        onCheckSpringHangers = onCheckSpringHangers,
                        onCheckBushingPins = onCheckBushingPins,
                        onInspectQuiz = { onInspectComponent("susp_ubolts_mounts") }
                    )
                }
                SuspensionSubTab.SHOCK_ABSORBER -> {
                    ShockAbsorberSection(
                        state = sim.shockState,
                        onCheckTopMount = onCheckShockTopMount,
                        onCheckBottomMount = onCheckShockBottomMount,
                        onCheckOilLeaks = onCheckShockOilLeaks,
                        onCheckBushings = onCheckShockBushings,
                        onInspectQuiz = { onInspectComponent("susp_shock_absorber") }
                    )
                }
            }
        }

        // Examiner Pro-Tips Card
        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "CDL Examiner Suspension Failure Rules",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Text(
                        text = "1. Missing/Broken Leaves: If 1/4 (25%) or more of the leaves in any spring pack are broken or missing, the vehicle is placed OUT OF SERVICE immediately.\n" +
                                "2. Shifted Leaves: Leaves that have scissored or shifted out of alignment can strike the steer tire sidewalls or puncture air brake lines.\n" +
                                "3. U-Bolt Rust Trails: Reddish-brown powder or shiny metal rings around U-bolt nuts indicate the axle is loose from the springs.\n" +
                                "4. Shock Absorber: Must be securely bolted at top frame bracket and bottom axle bracket. Any wet fluid weeping/leaking or missing rubber eyelet bushings fails the inspection.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

/**
 * Interactive Leaf Springs Section
 */
@Composable
private fun LeafSpringsSection(
    state: LeafSpringsSimState,
    onCheckCracks: () -> Unit,
    onCheckShifting: () -> Unit,
    onCheckMissing: () -> Unit,
    onInspectQuiz: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Graphic Canvas Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0B132B)),
            border = BorderStroke(1.dp, Color(0xFF1E293B))
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Multi-Leaf Spring Pack & Rebound Clips",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Main Leaf, Secondary Leaves & Alignment Clips",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (state.isCompleted) PassingGreen.copy(alpha = 0.2f) else Color(0xFF1E293B)
                    ) {
                        Text(
                            text = if (state.isCompleted) "PASSED" else "NEEDS INSPECTION",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (state.isCompleted) PassingGreen else SafetyAmber,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                // Canvas showing multi-leaf spring pack
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .testTag("leaf_springs_canvas")
                ) {
                    drawLeafSpringsGraphic(state = state)
                }
            }
        }

        // Hands-on Inspection Tasks Card
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Perform CDL Examiner Checklist for Leaf Springs:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )

                // Task 1: Cracks & Breaks
                FluidCheckItemButton(
                    title = "1. Cracks & Breaks: Inspect Full Length of Leaves",
                    subtitle = "Verify main leaf and secondary leaves have zero cracks, splits, or fractures",
                    isChecked = state.isCracksBreaksChecked,
                    onClick = onCheckCracks,
                    testTag = "check_leaf_cracks"
                )

                // Task 2: Shifting & Alignment
                FluidCheckItemButton(
                    title = "2. Shifting & Alignment: Leaves Parallel with Clips Intact",
                    subtitle = "Check that leaves have not shifted or scissored sideways toward tire or frame",
                    isChecked = state.isShiftingChecked,
                    onClick = onCheckShifting,
                    testTag = "check_leaf_shifting"
                )

                // Task 3: Missing Leaves
                FluidCheckItemButton(
                    title = "3. Missing Leaves: Complete Pack with Zero Missing Parts",
                    subtitle = "Verify pack is complete; 1/4 or more broken/missing is immediate out-of-service",
                    isChecked = state.isMissingLeavesChecked,
                    onClick = onCheckMissing,
                    testTag = "check_leaf_missing"
                )

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = onInspectQuiz,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Icon(
                        imageVector = Icons.Default.Quiz,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Recite Examiner Criteria & Take Quiz")
                }
            }
        }
    }
}

/**
 * Interactive U-Bolts & Spring Mounts Section
 */
@Composable
private fun UBoltMountsSection(
    state: UBoltMountsSimState,
    onCheckUBoltsTight: () -> Unit,
    onCheckRustTrails: () -> Unit,
    onCheckSpringHangers: () -> Unit,
    onCheckBushingPins: () -> Unit,
    onInspectQuiz: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Graphic Canvas Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0B132B)),
            border = BorderStroke(1.dp, Color(0xFF1E293B))
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Dual U-Bolts, Saddle & Frame Spring Mounts",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Axle Clamping Hardware & Frame Hangers",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (state.isCompleted) PassingGreen.copy(alpha = 0.2f) else Color(0xFF1E293B)
                    ) {
                        Text(
                            text = if (state.isCompleted) "PASSED" else "NEEDS INSPECTION",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (state.isCompleted) PassingGreen else SafetyAmber,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                // Canvas showing U-bolts and spring mounts
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .testTag("ubolts_mounts_canvas")
                ) {
                    drawUBoltMountsGraphic(state = state)
                }
            }
        }

        // Hands-on Inspection Tasks Card
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Perform CDL Examiner Checklist for U-Bolts & Mounts:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )

                // Task 1: U-Bolts Tight
                FluidCheckItemButton(
                    title = "1. Dual U-Bolts: Securely Bolted with Nuts Tight",
                    subtitle = "Verify pair of U-bolts clamp leaf springs to axle securely with no loose/missing nuts",
                    isChecked = state.isUBoltsTightChecked,
                    onClick = onCheckUBoltsTight,
                    testTag = "check_ubolts_tight"
                )

                // Task 2: Rust Trails
                FluidCheckItemButton(
                    title = "2. Rust Trails: Zero Rust Powder or Shiny Metal Rings",
                    subtitle = "Confirm zero reddish-brown rust trails or shiny metal rings indicating loose bolts",
                    isChecked = state.isRustTrailsChecked,
                    onClick = onCheckRustTrails,
                    testTag = "check_ubolts_rust"
                )

                // Task 3: Spring Hanger Brackets
                FluidCheckItemButton(
                    title = "3. Spring Hangers: Securely Bolted to Frame with No Cracks",
                    subtitle = "Verify front and rear spring mounting hangers are undamaged with all bolts tight",
                    isChecked = state.isSpringHangersChecked,
                    onClick = onCheckSpringHangers,
                    testTag = "check_spring_hangers"
                )

                // Task 4: Bushing Pins
                FluidCheckItemButton(
                    title = "4. Shackle Pins & Bushings: Intact & Properly Greased",
                    subtitle = "Confirm shackle pins and rubber/bronze bushings are present with zero excessive wear",
                    isChecked = state.isBushingPinsChecked,
                    onClick = onCheckBushingPins,
                    testTag = "check_bushing_pins"
                )

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = onInspectQuiz,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Icon(
                        imageVector = Icons.Default.Quiz,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Recite Examiner Criteria & Take Quiz")
                }
            }
        }
    }
}

/**
 * Interactive Shock Absorber Section
 */
@Composable
private fun ShockAbsorberSection(
    state: ShockAbsorberSimState,
    onCheckTopMount: () -> Unit,
    onCheckBottomMount: () -> Unit,
    onCheckOilLeaks: () -> Unit,
    onCheckBushings: () -> Unit,
    onInspectQuiz: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // Graphic Canvas Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0B132B)),
            border = BorderStroke(1.dp, Color(0xFF1E293B))
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Front Telescopic Shock Absorber",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Frame & Axle Mounting, Oil Seal & Eyelet Bushings",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF94A3B8)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (state.isCompleted) PassingGreen.copy(alpha = 0.2f) else Color(0xFF1E293B)
                    ) {
                        Text(
                            text = if (state.isCompleted) "PASSED" else "NEEDS INSPECTION",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (state.isCompleted) PassingGreen else SafetyAmber,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                // Canvas showing telescopic shock absorber
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .testTag("shock_absorber_canvas")
                ) {
                    drawShockAbsorberGraphic(state = state)
                }
            }
        }

        // Hands-on Inspection Tasks Card
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Perform CDL Examiner Checklist for Shock Absorber:",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )

                // Task 1: Top Mount
                FluidCheckItemButton(
                    title = "1. Top Mounting: Securely Bolted to Frame Bracket",
                    subtitle = "Verify top bracket bolt and locknut are tight with zero play on frame",
                    isChecked = state.isTopMountChecked,
                    onClick = onCheckTopMount,
                    testTag = "check_shock_top"
                )

                // Task 2: Bottom Mount
                FluidCheckItemButton(
                    title = "2. Bottom Mounting: Securely Bolted to Axle Bracket",
                    subtitle = "Verify bottom bracket bolt and locknut are securely fastened to axle",
                    isChecked = state.isBottomMountChecked,
                    onClick = onCheckBottomMount,
                    testTag = "check_shock_bottom"
                )

                // Task 3: Zero Oil Leaks
                FluidCheckItemButton(
                    title = "3. Zero Oil Leaks: Cylinder Body & Seal Dry",
                    subtitle = "Inspect hydraulic shock casing for active fluid leaks, weeping, or residue",
                    isChecked = state.isOilLeaksChecked,
                    onClick = onCheckOilLeaks,
                    testTag = "check_shock_leaks"
                )

                // Task 4: Rubber Bushings
                FluidCheckItemButton(
                    title = "4. Rubber Bushings: Intact with Zero Rot or Splits",
                    subtitle = "Confirm top and bottom eyelet rubber bushings are not dry-rotted, split, or missing",
                    isChecked = state.isRubberBushingsChecked,
                    onClick = onCheckBushings,
                    testTag = "check_shock_bushings"
                )

                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = onInspectQuiz,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Icon(
                        imageVector = Icons.Default.Quiz,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Recite Examiner Criteria & Take Quiz")
                }
            }
        }
    }
}

/**
 * Canvas drawing for Leaf Springs and Rebound Clips.
 */
private fun DrawScope.drawLeafSpringsGraphic(state: LeafSpringsSimState) {
    val width = size.width
    val height = size.height

    val springColor = if (state.isCompleted) PassingGreen else Color(0xFF64748B)
    val clipColor = if (state.isShiftingChecked) PassingGreen else Color(0xFF38BDF8)
    val eyeColor = if (state.isCracksBreaksChecked) PassingGreen else Color(0xFF94A3B8)

    val midX = width * 0.50f
    val baseArchY = height * 0.60f

    // 1. Main Leaf (Longest top curved spring spanning hangers)
    val mainPath = androidx.compose.ui.graphics.Path().apply {
        moveTo(width * 0.15f, baseArchY - 20f)
        quadraticTo(midX, baseArchY + 15f, width * 0.85f, baseArchY - 20f)
    }
    drawPath(path = mainPath, color = springColor, style = Stroke(width = 8f, cap = StrokeCap.Round))

    // Spring Eyes at both ends
    drawCircle(color = eyeColor, radius = 9f, center = Offset(width * 0.15f, baseArchY - 20f))
    drawCircle(color = Color(0xFF0F172A), radius = 4f, center = Offset(width * 0.15f, baseArchY - 20f))
    drawCircle(color = eyeColor, radius = 9f, center = Offset(width * 0.85f, baseArchY - 20f))
    drawCircle(color = Color(0xFF0F172A), radius = 4f, center = Offset(width * 0.85f, baseArchY - 20f))

    // 2. Secondary Leaves (2nd, 3rd, 4th leaves stepped below)
    listOf(
        Triple(width * 0.22f, width * 0.78f, 7f),
        Triple(width * 0.30f, width * 0.70f, 6f),
        Triple(width * 0.38f, width * 0.62f, 5f)
    ).forEachIndexed { idx, (left, right, stroke) ->
        val yOffset = (idx + 1) * 7f
        val leafPath = androidx.compose.ui.graphics.Path().apply {
            moveTo(left, baseArchY - 14f + yOffset)
            quadraticTo(midX, baseArchY + 15f + yOffset, right, baseArchY - 14f + yOffset)
        }
        drawPath(path = leafPath, color = springColor, style = Stroke(width = stroke, cap = StrokeCap.Round))
    }

    // 3. Rebound Alignment Clips (Left & Right of center to keep leaves from shifting)
    listOf(width * 0.35f, width * 0.65f).forEach { clipX ->
        drawRoundRect(
            color = clipColor,
            topLeft = Offset(clipX - 4f, baseArchY - 2f),
            size = Size(8f, 32f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(2f, 2f)
        )
    }

    // 4. Steer Axle Center Saddle Block
    drawRect(
        color = Color(0xFF1E293B),
        topLeft = Offset(midX - 22f, baseArchY + 18f),
        size = Size(44f, 24f)
    )
}

/**
 * Canvas drawing for U-Bolts, Saddle, and Frame Spring Mounts.
 */
private fun DrawScope.drawUBoltMountsGraphic(state: UBoltMountsSimState) {
    val width = size.width
    val height = size.height

    val midX = width * 0.50f
    val uboltColor = if (state.isUBoltsTightChecked) PassingGreen else Color(0xFF94A3B8)
    val hangerColor = if (state.isSpringHangersChecked) PassingGreen else Color(0xFF64748B)
    val nutColor = if (state.isRustTrailsChecked) PassingGreen else Color(0xFFCBD5E1)

    // 1. Frame Rail (Top)
    drawRect(
        color = Color(0xFF1E293B),
        topLeft = Offset(width * 0.08f, height * 0.12f),
        size = Size(width * 0.84f, height * 0.18f)
    )

    // 2. Spring Hangers (Front & Rear Hangers bolted to frame)
    drawRect(
        color = hangerColor,
        topLeft = Offset(width * 0.15f, height * 0.28f),
        size = Size(26f, 34f)
    )
    drawCircle(
        color = if (state.isBushingPinsChecked) PassingGreen else Color(0xFFF59E0B),
        radius = 7f,
        center = Offset(width * 0.15f + 13f, height * 0.28f + 24f)
    )

    drawRect(
        color = hangerColor,
        topLeft = Offset(width * 0.80f, height * 0.28f),
        size = Size(26f, 34f)
    )
    drawCircle(
        color = if (state.isBushingPinsChecked) PassingGreen else Color(0xFFF59E0B),
        radius = 7f,
        center = Offset(width * 0.80f + 13f, height * 0.28f + 24f)
    )

    // 3. Round Axle Center Tube (Middle bottom)
    drawCircle(
        color = Color(0xFF0F172A),
        radius = 28f,
        center = Offset(midX, height * 0.68f)
    )
    drawCircle(
        color = Color(0xFF334155),
        radius = 28f,
        center = Offset(midX, height * 0.68f),
        style = Stroke(width = 3f)
    )

    // 4. Pair of Dual U-Bolts (Wrapping axle with threaded ends)
    listOf(midX - 14f, midX + 14f).forEach { uX ->
        val uPath = androidx.compose.ui.graphics.Path().apply {
            moveTo(uX - 8f, height * 0.42f)
            lineTo(uX - 8f, height * 0.70f)
            quadraticTo(uX, height * 0.82f, uX + 8f, height * 0.70f)
            lineTo(uX + 8f, height * 0.42f)
        }
        drawPath(path = uPath, color = uboltColor, style = Stroke(width = 5f, cap = StrokeCap.Round))

        // U-Bolt Nuts & Saddle Plate
        drawCircle(color = nutColor, radius = 5f, center = Offset(uX - 8f, height * 0.43f))
        drawCircle(color = nutColor, radius = 5f, center = Offset(uX + 8f, height * 0.43f))
    }
}

/**
 * Canvas drawing for Shock Absorber.
 */
private fun DrawScope.drawShockAbsorberGraphic(state: ShockAbsorberSimState) {
    val width = size.width
    val height = size.height

    val midX = width * 0.50f
    val topMountY = height * 0.18f
    val bottomMountY = height * 0.82f

    val shockBodyColor = if (state.isOilLeaksChecked) PassingGreen else Color(0xFF1E293B)
    val strokeColor = if (state.isOilLeaksChecked) PassingGreen else Color(0xFF38BDF8)
    val bushingColor = if (state.isRubberBushingsChecked) PassingGreen else Color(0xFF0F172A)
    val boltColor = if (state.isTopMountChecked && state.isBottomMountChecked) PassingGreen else Color(0xFFCBD5E1)

    // 1. Upper Frame Bracket & Bushing Eyelet
    drawRect(
        color = Color(0xFF334155),
        topLeft = Offset(midX - 16f, topMountY - 14f),
        size = Size(32f, 14f)
    )
    drawCircle(color = bushingColor, radius = 10f, center = Offset(midX, topMountY))
    drawCircle(color = boltColor, radius = 5f, center = Offset(midX, topMountY))

    // 2. Chrome Piston Shaft (Extending from top into shock tube)
    drawLine(
        color = Color(0xFFCBD5E1),
        start = Offset(midX, topMountY + 10f),
        end = Offset(midX, height * 0.45f),
        strokeWidth = 8f,
        cap = StrokeCap.Round
    )

    // 3. Lower Shock Outer Cylinder Body
    drawRoundRect(
        color = shockBodyColor,
        topLeft = Offset(midX - 14f, height * 0.42f),
        size = Size(28f, height * 0.34f),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(6f, 6f)
    )
    drawRoundRect(
        color = strokeColor,
        topLeft = Offset(midX - 14f, height * 0.42f),
        size = Size(28f, height * 0.34f),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(6f, 6f),
        style = Stroke(width = 2.5f)
    )

    // 4. Lower Axle Bracket & Bushing Eyelet
    drawCircle(color = bushingColor, radius = 10f, center = Offset(midX, bottomMountY))
    drawCircle(color = boltColor, radius = 5f, center = Offset(midX, bottomMountY))
    drawRect(
        color = Color(0xFF334155),
        topLeft = Offset(midX - 16f, bottomMountY + 8f),
        size = Size(32f, 14f)
    )
}




