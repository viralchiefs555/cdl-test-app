package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pretrip.*
import com.example.ui.theme.*

@Composable
fun FrontBrakesAxleDashboardView(
    state: PreTripInspectionState,
    onSelectSubTab: (FrontBrakesSubTab) -> Unit,
    onCheckChamberMounting: () -> Unit,
    onCheckChamberDentsLeaks: () -> Unit,
    onCheckPushrodClevisPin: () -> Unit,
    onTestPushrodTravel: (Float) -> Unit,
    onCheckDrumCracks: () -> Unit,
    onCheckOilGreaseContamination: () -> Unit,
    onMeasureLiningThickness: (Float) -> Unit,
    onMeasureTreadDepth: (Int) -> Unit,
    onCheckAbcSidewall: () -> Unit,
    onCheckExposedFabric: () -> Unit,
    onCheckRecapProhibition: () -> Unit,
    onMeasureTirePressure: (Int) -> Unit,
    onCheckValveStemCap: () -> Unit,
    onCheckRimWeldsCracks: () -> Unit,
    onCheckAllLugNuts: () -> Unit,
    onCheckRustStreaks: () -> Unit,
    onCheckHubSightGlass: () -> Unit,
    onCheckHubSealLeaks: () -> Unit,
    onReset: () -> Unit,
    onInspectComponent: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val sim = state.frontBrakesSim
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
            .testTag("front_brakes_dashboard"),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Header Card with FMCSA Criteria Summary & Progress
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ),
            shape = RoundedCornerShape(16.dp)
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
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "TAB 5: FRONT BRAKES & STEERING AXLE",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            letterSpacing = 1.2.sp
                        )
                        Text(
                            text = "FMCSA Steer Axle & Brake Standards",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Surface(
                        color = if (sim.isAllCompleted) PassingGreen.copy(alpha = 0.15f) else MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = if (sim.isAllCompleted) "ZONE PASSED" else "4 SUB-SYSTEMS",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (sim.isAllCompleted) PassingGreen else MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Text(
                    text = "Inspect brake chambers (no dents/leaks, pushrod <= 1\"), drums & linings (>= 1/4\" thick, no grease), steer tire (>= 4/32\" tread, ABC check, cold PSI), and rim & hub oil seal (all lugs, no rust streaks, proper oil level).",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Sub-system progress row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SubsystemBadge(
                        label = "Chamber",
                        isCompleted = sim.allChamberCompleted,
                        modifier = Modifier.weight(1f)
                    )
                    SubsystemBadge(
                        label = "Drums/Linings",
                        isCompleted = sim.allDrumLiningsCompleted,
                        modifier = Modifier.weight(1f)
                    )
                    SubsystemBadge(
                        label = "Steer Tire",
                        isCompleted = sim.allSteerTireCompleted,
                        modifier = Modifier.weight(1f)
                    )
                    SubsystemBadge(
                        label = "Rim & Hub",
                        isCompleted = sim.allRimHubCompleted,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // 2. Sub-Tab Selector Navigation
        ScrollableTabRow(
            selectedTabIndex = sim.activeSubTab.ordinal,
            edgePadding = 0.dp,
            divider = {},
            containerColor = Color.Transparent
        ) {
            FrontBrakesSubTab.values().forEach { subTab ->
                val isSelected = sim.activeSubTab == subTab
                val isCompleted = when (subTab) {
                    FrontBrakesSubTab.CHAMBER_AND_SLACK_ADJUSTER -> sim.allChamberCompleted
                    FrontBrakesSubTab.DRUM_AND_LININGS -> sim.allDrumLiningsCompleted
                    FrontBrakesSubTab.STEER_TIRE -> sim.allSteerTireCompleted
                    FrontBrakesSubTab.RIM_AND_HUB_SEAL -> sim.allRimHubCompleted
                }

                Tab(
                    selected = isSelected,
                    onClick = { onSelectSubTab(subTab) },
                    modifier = Modifier.testTag("front_brakes_tab_${subTab.name.lowercase()}")
                ) {
                    Surface(
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 6.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                        border = if (isSelected) BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary) else null
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(text = subTab.iconEmoji, fontSize = 16.sp)
                            Text(
                                text = when (subTab) {
                                    FrontBrakesSubTab.CHAMBER_AND_SLACK_ADJUSTER -> "Chamber & Slack"
                                    FrontBrakesSubTab.DRUM_AND_LININGS -> "Drum & Linings"
                                    FrontBrakesSubTab.STEER_TIRE -> "Steer Tire"
                                    FrontBrakesSubTab.RIM_AND_HUB_SEAL -> "Rim & Hub"
                                },
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                            )
                            if (isCompleted) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Completed",
                                    tint = PassingGreen,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // 3. Active Sub-Tab Content
        when (sim.activeSubTab) {
            FrontBrakesSubTab.CHAMBER_AND_SLACK_ADJUSTER -> {
                BrakeChamberSlackSection(
                    state = state,
                    onCheckChamberMounting = onCheckChamberMounting,
                    onCheckChamberDentsLeaks = onCheckChamberDentsLeaks,
                    onCheckPushrodClevisPin = onCheckPushrodClevisPin,
                    onTestPushrodTravel = onTestPushrodTravel,
                    onInspectComponent = onInspectComponent
                )
            }
            FrontBrakesSubTab.DRUM_AND_LININGS -> {
                BrakeDrumLiningsSection(
                    state = state,
                    onCheckDrumCracks = onCheckDrumCracks,
                    onCheckOilGreaseContamination = onCheckOilGreaseContamination,
                    onMeasureLiningThickness = onMeasureLiningThickness,
                    onInspectComponent = onInspectComponent
                )
            }
            FrontBrakesSubTab.STEER_TIRE -> {
                SteerTireSection(
                    state = state,
                    onMeasureTreadDepth = onMeasureTreadDepth,
                    onCheckAbcSidewall = onCheckAbcSidewall,
                    onCheckExposedFabric = onCheckExposedFabric,
                    onCheckRecapProhibition = onCheckRecapProhibition,
                    onMeasureTirePressure = onMeasureTirePressure,
                    onCheckValveStemCap = onCheckValveStemCap,
                    onInspectComponent = onInspectComponent
                )
            }
            FrontBrakesSubTab.RIM_AND_HUB_SEAL -> {
                RimHubSealSection(
                    state = state,
                    onCheckRimWeldsCracks = onCheckRimWeldsCracks,
                    onCheckAllLugNuts = onCheckAllLugNuts,
                    onCheckRustStreaks = onCheckRustStreaks,
                    onCheckHubSightGlass = onCheckHubSightGlass,
                    onCheckHubSealLeaks = onCheckHubSealLeaks,
                    onInspectComponent = onInspectComponent
                )
            }
        }

        // 4. Session Controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = onReset,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.outline
                )
            ) {
                Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Reset Front Brakes Tab")
            }

            if (sim.isAllCompleted) {
                Surface(
                    color = PassingGreen.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(Icons.Default.Verified, contentDescription = null, tint = PassingGreen)
                        Text(
                            text = "Tab 5 Complete (+60 pts)",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = PassingGreen
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SubsystemBadge(
    label: String,
    isCompleted: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        color = if (isCompleted) PassingGreen.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
        border = BorderStroke(
            1.dp,
            if (isCompleted) PassingGreen.copy(alpha = 0.5f) else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                contentDescription = null,
                tint = if (isCompleted) PassingGreen else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = if (isCompleted) FontWeight.Bold else FontWeight.Normal,
                color = if (isCompleted) PassingGreen else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// =========================================================================
// SECTION 1: BRAKE CHAMBER & SLACK ADJUSTER
// =========================================================================

@Composable
private fun BrakeChamberSlackSection(
    state: PreTripInspectionState,
    onCheckChamberMounting: () -> Unit,
    onCheckChamberDentsLeaks: () -> Unit,
    onCheckPushrodClevisPin: () -> Unit,
    onTestPushrodTravel: (Float) -> Unit,
    onInspectComponent: (String) -> Unit
) {
    val chamber = state.frontBrakesSim.chamberState

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Brake Chamber & Slack Adjuster Simulation",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Visual Graphic Canvas
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawBrakeChamberGraphic(
                        chamberState = chamber,
                        travelInches = chamber.measuredPushrodTravelInches
                    )
                }
            }

            // Interactive Manual Pushrod Hand Pull Test
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
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
                                text = "Pushrod Manual Pull Stroke (Brakes Released)",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "FMCSA Criteria: Must not move more than 1 inch by hand",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        val isViolating = chamber.measuredPushrodTravelInches > 1.0f
                        Surface(
                            color = if (isViolating) FailingRed.copy(alpha = 0.2f) else PassingGreen.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "${String.format("%.2f", chamber.measuredPushrodTravelInches)}\" travel",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (isViolating) FailingRed else PassingGreen
                            )
                        }
                    }

                    Slider(
                        value = chamber.measuredPushrodTravelInches,
                        onValueChange = { onTestPushrodTravel(it) },
                        valueRange = 0.25f..2.0f,
                        steps = 6,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("0.25\" (Tight)", style = MaterialTheme.typography.labelSmall)
                        Text("1.00\" (Legal Limit)", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        Text("2.00\" (Out of Service)", style = MaterialTheme.typography.labelSmall, color = FailingRed)
                    }

                    if (chamber.measuredPushrodTravelInches > 1.0f) {
                        Surface(
                            color = FailingRed.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, FailingRed.copy(alpha = 0.4f))
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(Icons.Default.Warning, contentDescription = null, tint = FailingRed)
                                Text(
                                    text = "OUT-OF-SERVICE DEFECT: Pushrod stroke exceeds 1 inch hand-pull limit! Brakes out of adjustment.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = FailingRed,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // Inspection Checkpoints
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                InspectionCheckItem(
                    title = "Chamber Securely Mounted",
                    subtitle = "Mounting bracket nuts torqued tight; clamp band secure with no dents or damage",
                    isChecked = chamber.isChamberMountedTight,
                    onClick = onCheckChamberMounting,
                    testTag = "check_chamber_mounting"
                )
                InspectionCheckItem(
                    title = "No Audible or Visual Air Leaks",
                    subtitle = "Supply airline intact; diaphragm chamber sealed with zero pressure loss",
                    isChecked = chamber.isNoDentsOrAirLeaks,
                    onClick = onCheckChamberDentsLeaks,
                    testTag = "check_chamber_leaks"
                )
                InspectionCheckItem(
                    title = "Pushrod & Clevis Pin Checked",
                    subtitle = "Pushrod straight with zero bends; clevis pin and cotter pin present & locked",
                    isChecked = chamber.isPushrodAndClevisPinChecked,
                    onClick = onCheckPushrodClevisPin,
                    testTag = "check_chamber_clevis"
                )
            }

            // Recitation & Quiz Actions
            InspectionComponentActionButtons(
                componentId = "brake-chamber",
                onInspectComponent = onInspectComponent
            )
        }
    }
}

private fun DrawScope.drawBrakeChamberGraphic(
    chamberState: BrakeChamberSimState,
    travelInches: Float
) {
    val w = size.width
    val h = size.height

    // Grid / CAD background lines
    drawLine(
        color = Color(0xFF1E293B),
        start = Offset(0f, h * 0.5f),
        end = Offset(w, h * 0.5f),
        strokeWidth = 1f
    )

    // 1. Air Brake Hose Entry (Top Left)
    val hosePath = Path().apply {
        moveTo(w * 0.08f, h * 0.15f)
        quadraticTo(w * 0.16f, h * 0.35f, w * 0.22f, h * 0.38f)
    }
    drawPath(
        path = hosePath,
        color = Color(0xFFEF4444),
        style = Stroke(width = 8f, cap = StrokeCap.Round)
    )

    // 2. Service Brake Chamber Canister
    val canisterColor = if (chamberState.isNoDentsOrAirLeaks) Color(0xFF334155) else Color(0xFF475569)
    drawRoundRect(
        color = canisterColor,
        topLeft = Offset(w * 0.18f, h * 0.25f),
        size = Size(w * 0.24f, h * 0.50f),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(16f, 16f)
    )

    // Heavy Clamp Ring around chamber
    drawRoundRect(
        color = if (chamberState.isChamberMountedTight) SafetyAmber else Color(0xFF64748B),
        topLeft = Offset(w * 0.28f, h * 0.22f),
        size = Size(w * 0.04f, h * 0.56f),
        cornerRadius = androidx.compose.ui.geometry.CornerRadius(6f, 6f)
    )

    // Mounting Bracket to Axle (Left side)
    drawLine(
        color = Color(0xFF94A3B8),
        start = Offset(w * 0.14f, h * 0.40f),
        end = Offset(w * 0.18f, h * 0.40f),
        strokeWidth = 12f,
        cap = StrokeCap.Round
    )
    drawLine(
        color = Color(0xFF94A3B8),
        start = Offset(w * 0.14f, h * 0.60f),
        end = Offset(w * 0.18f, h * 0.60f),
        strokeWidth = 12f,
        cap = StrokeCap.Round
    )

    // 3. Pushrod extending out to the right
    val maxStrokePixels = w * 0.12f
    val extensionFraction = (travelInches / 2.0f).coerceIn(0f, 1f)
    val pushrodEndX = w * 0.42f + (extensionFraction * maxStrokePixels) + w * 0.18f

    val pushrodColor = if (travelInches > 1.0f) FailingRed else Color(0xFFE2E8F0)

    // Pushrod shaft
    drawLine(
        color = pushrodColor,
        start = Offset(w * 0.42f, h * 0.50f),
        end = Offset(pushrodEndX, h * 0.50f),
        strokeWidth = 14f,
        cap = StrokeCap.Round
    )

    // Travel ruler measurement line
    drawLine(
        color = if (travelInches > 1.0f) FailingRed else PassingGreen,
        start = Offset(w * 0.42f, h * 0.72f),
        end = Offset(pushrodEndX, h * 0.72f),
        strokeWidth = 4f
    )
    drawLine(
        color = if (travelInches > 1.0f) FailingRed else PassingGreen,
        start = Offset(w * 0.42f, h * 0.68f),
        end = Offset(w * 0.42f, h * 0.76f),
        strokeWidth = 3f
    )
    drawLine(
        color = if (travelInches > 1.0f) FailingRed else PassingGreen,
        start = Offset(pushrodEndX, h * 0.68f),
        end = Offset(pushrodEndX, h * 0.76f),
        strokeWidth = 3f
    )

    // 4. Clevis & Pin
    drawCircle(
        color = if (chamberState.isPushrodAndClevisPinChecked) PassingGreen else SafetyAmber,
        radius = 12f,
        center = Offset(pushrodEndX, h * 0.50f)
    )

    // 5. Slack Adjuster Arm
    val slackArmEndY = h * 0.85f
    drawLine(
        color = Color(0xFF64748B),
        start = Offset(pushrodEndX, h * 0.50f),
        end = Offset(pushrodEndX + w * 0.05f, slackArmEndY),
        strokeWidth = 18f,
        cap = StrokeCap.Round
    )

    // S-Cam Spline Pivot Point (Bottom of slack adjuster)
    drawCircle(
        color = Color(0xFF0284C7),
        radius = 18f,
        center = Offset(pushrodEndX + w * 0.05f, slackArmEndY)
    )
}

// =========================================================================
// SECTION 2: BRAKE DRUM & LININGS
// =========================================================================

@Composable
private fun BrakeDrumLiningsSection(
    state: PreTripInspectionState,
    onCheckDrumCracks: () -> Unit,
    onCheckOilGreaseContamination: () -> Unit,
    onMeasureLiningThickness: (Float) -> Unit,
    onInspectComponent: (String) -> Unit
) {
    val drum = state.frontBrakesSim.drumLiningsState

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Brake Drum & Friction Linings Simulation",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Visual Cutaway Canvas
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawBrakeDrumGraphic(
                        drumState = drum,
                        thickness = drum.measuredLiningThicknessInches
                    )
                }
            }

            // Interactive Friction Lining Thickness Gauge
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
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
                                text = "Brake Lining Friction Thickness Gauge",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "FMCSA Criteria: Minimum 1/4 inch (0.25\") thickness",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        val isThin = drum.measuredLiningThicknessInches < 0.25f
                        Surface(
                            color = if (isThin) FailingRed.copy(alpha = 0.2f) else PassingGreen.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "${String.format("%.2f", drum.measuredLiningThicknessInches)}\" thick",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (isThin) FailingRed else PassingGreen
                            )
                        }
                    }

                    Slider(
                        value = drum.measuredLiningThicknessInches,
                        onValueChange = { onMeasureLiningThickness(it) },
                        valueRange = 0.10f..0.50f,
                        steps = 7,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("0.10\" (Dangerous)", style = MaterialTheme.typography.labelSmall, color = FailingRed)
                        Text("0.25\" (Min Standard)", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        Text("0.50\" (New Lining)", style = MaterialTheme.typography.labelSmall)
                    }

                    if (drum.measuredLiningThicknessInches < 0.25f) {
                        Surface(
                            color = FailingRed.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, FailingRed.copy(alpha = 0.4f))
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(Icons.Default.Warning, contentDescription = null, tint = FailingRed)
                                Text(
                                    text = "OUT-OF-SERVICE DEFECT: Friction lining under 1/4\" minimum thickness! Dangerously thin linings cause metal-on-metal brake failure.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = FailingRed,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // Checkpoints
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                InspectionCheckItem(
                    title = "Brake Drum Structural Integrity",
                    subtitle = "No cracks, dents, holes, or deep heat grooving across drum friction face",
                    isChecked = drum.isDrumCracksChecked,
                    onClick = onCheckDrumCracks,
                    testTag = "check_drum_cracks"
                )
                InspectionCheckItem(
                    title = "Zero Oil / Grease Contamination",
                    subtitle = "Inner hub oil seal leak-free; drums and linings free of oil, grease, or road debris",
                    isChecked = drum.isOilGreaseContaminationChecked,
                    onClick = onCheckOilGreaseContamination,
                    testTag = "check_drum_oil"
                )
            }

            // Recitation & Quiz Actions
            InspectionComponentActionButtons(
                componentId = "front_brake_linings",
                onInspectComponent = onInspectComponent
            )
        }
    }
}

private fun DrawScope.drawBrakeDrumGraphic(
    drumState: BrakeDrumLiningsSimState,
    thickness: Float
) {
    val w = size.width
    val h = size.height

    val center = Offset(w * 0.5f, h * 0.5f)
    val outerRadius = h * 0.42f
    val innerRadius = h * 0.35f

    // 1. Heavy Brake Drum Outer Ring
    drawCircle(
        color = Color(0xFF1E293B),
        radius = outerRadius,
        center = center,
        style = Stroke(width = 16f)
    )

    // Inner machined friction surface
    drawCircle(
        color = Color(0xFF475569),
        radius = innerRadius,
        center = center,
        style = Stroke(width = 6f)
    )

    // 2. Upper and Lower Brake Shoes with Linings
    val isThin = thickness < 0.25f
    val liningThicknessPx = (thickness / 0.50f * 20f).coerceIn(6f, 24f)
    val liningColor = if (isThin) FailingRed else Color(0xFFF59E0B)

    // Upper shoe arc (Friction lining)
    drawArc(
        color = liningColor,
        startAngle = 200f,
        sweepAngle = 140f,
        useCenter = false,
        topLeft = Offset(center.x - innerRadius + 4f, center.y - innerRadius + 4f),
        size = Size((innerRadius - 4f) * 2, (innerRadius - 4f) * 2),
        style = Stroke(width = liningThicknessPx, cap = StrokeCap.Round)
    )

    // Lower shoe arc (Friction lining)
    drawArc(
        color = liningColor,
        startAngle = 20f,
        sweepAngle = 140f,
        useCenter = false,
        topLeft = Offset(center.x - innerRadius + 4f, center.y - innerRadius + 4f),
        size = Size((innerRadius - 4f) * 2, (innerRadius - 4f) * 2),
        style = Stroke(width = liningThicknessPx, cap = StrokeCap.Round)
    )

    // 3. Central Axle Hub Spindle & S-Cam Roller
    drawCircle(
        color = Color(0xFF0F172A),
        radius = 32f,
        center = center
    )
    drawCircle(
        color = if (drumState.isOilGreaseContaminationChecked) PassingGreen else SafetyAmber,
        radius = 20f,
        center = center
    )

    // S-Cam Shaft Indicator (Left)
    drawLine(
        color = Color(0xFF0284C7),
        start = Offset(center.x - innerRadius * 0.8f, center.y - 12f),
        end = Offset(center.x - innerRadius * 0.8f, center.y + 12f),
        strokeWidth = 14f,
        cap = StrokeCap.Round
    )
}

// =========================================================================
// SECTION 3: STEER TIRE (4/32" & ABC CHECK)
// =========================================================================

@Composable
private fun SteerTireSection(
    state: PreTripInspectionState,
    onMeasureTreadDepth: (Int) -> Unit,
    onCheckAbcSidewall: () -> Unit,
    onCheckExposedFabric: () -> Unit,
    onCheckRecapProhibition: () -> Unit,
    onMeasureTirePressure: (Int) -> Unit,
    onCheckValveStemCap: () -> Unit,
    onInspectComponent: (String) -> Unit
) {
    val tire = state.frontBrakesSim.steerTireState

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Steer Tire (4/32\" & ABC Check) Simulation",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Visual Steer Tire Canvas
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawSteerTireGraphic(
                        tireState = tire,
                        treadDepth = tire.measuredTreadDepth32nds
                    )
                }
            }

            // Interactive Tread Depth Gauge
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
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
                                text = "Tread Depth Gauge (Every Major Groove)",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "FMCSA Criteria: Minimum 4/32\" on steer tires (2/32\" on others)",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        val isLowTread = tire.measuredTreadDepth32nds < 4
                        Surface(
                            color = if (isLowTread) FailingRed.copy(alpha = 0.2f) else PassingGreen.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "${tire.measuredTreadDepth32nds}/32\" depth",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (isLowTread) FailingRed else PassingGreen
                            )
                        }
                    }

                    Slider(
                        value = tire.measuredTreadDepth32nds.toFloat(),
                        onValueChange = { onMeasureTreadDepth(it.toInt()) },
                        valueRange = 2f..10f,
                        steps = 7,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("2/32\" (Failed Steer)", style = MaterialTheme.typography.labelSmall, color = FailingRed)
                        Text("4/32\" (Legal Steer Minimum)", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                        Text("10/32\" (New Tire)", style = MaterialTheme.typography.labelSmall)
                    }

                    if (tire.measuredTreadDepth32nds < 4) {
                        Surface(
                            color = FailingRed.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, FailingRed.copy(alpha = 0.4f))
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(Icons.Default.Warning, contentDescription = null, tint = FailingRed)
                                Text(
                                    text = "CRITICAL OUT-OF-SERVICE DEFECT: Tread depth less than 4/32 inch in major grooves on steer axle!",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = FailingRed,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // Tire Pressure Gauge Simulation
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
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
                                text = "Cold Inflation Pressure (Tire Pressure Gauge)",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Manufacturer spec: 100 - 110 PSI cold",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Surface(
                            color = PassingGreen.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = "${tire.measuredPsi} PSI",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = PassingGreen
                            )
                        }
                    }

                    Slider(
                        value = tire.measuredPsi.toFloat(),
                        onValueChange = { onMeasureTirePressure(it.toInt()) },
                        valueRange = 80f..120f,
                        steps = 7,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Checkpoints
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                InspectionCheckItem(
                    title = "ABC Check (Sidewalls & Tread)",
                    subtitle = "No Abrasions, Bulges, or Cuts on inner/outer sidewalls or tread face",
                    isChecked = tire.isAbcSidewallChecked,
                    onClick = onCheckAbcSidewall,
                    testTag = "check_tire_abc"
                )
                InspectionCheckItem(
                    title = "Zero Exposed Fabric / Ply Cords",
                    subtitle = "Casing completely sound; no exposed steel belts or body cord fabric",
                    isChecked = tire.isExposedFabricChecked,
                    onClick = onCheckExposedFabric,
                    testTag = "check_tire_fabric"
                )
                InspectionCheckItem(
                    title = "Virgin Rubber Only (NO Recaps / Retreads)",
                    subtitle = "FMCSA strictly prohibits recapped or retreaded tires on commercial steer axles",
                    isChecked = tire.isRecapProhibitionChecked,
                    onClick = onCheckRecapProhibition,
                    testTag = "check_tire_recaps"
                )
                InspectionCheckItem(
                    title = "Valve Stem Capped & Leak-Free",
                    subtitle = "Metal or rubber valve stem cap present, tight, not cracked, no audible hissing",
                    isChecked = tire.isValveCapPresent,
                    onClick = onCheckValveStemCap,
                    testTag = "check_tire_valve_cap"
                )
            }

            // Recitation & Quiz Actions
            InspectionComponentActionButtons(
                componentId = "steer-tire",
                onInspectComponent = onInspectComponent
            )
        }
    }
}

private fun DrawScope.drawSteerTireGraphic(
    tireState: SteerTireSimState,
    treadDepth: Int
) {
    val w = size.width
    val h = size.height

    val center = Offset(w * 0.42f, h * 0.5f)
    val outerRadius = h * 0.44f
    val innerRadius = h * 0.28f

    // 1. Steer Tire Rubber Outer Casing
    drawCircle(
        color = Color(0xFF0F172A),
        radius = outerRadius,
        center = center
    )
    drawCircle(
        color = Color(0xFF1E293B),
        radius = outerRadius,
        center = center,
        style = Stroke(width = 18f)
    )

    // Tread Grooves Pattern
    val grooveColor = if (treadDepth < 4) FailingRed else Color(0xFF38BDF8)
    for (i in 0 until 12) {
        val angle = i * 30f * (Math.PI / 180.0)
        val x1 = center.x + (outerRadius - 16f) * Math.cos(angle).toFloat()
        val y1 = center.y + (outerRadius - 16f) * Math.sin(angle).toFloat()
        val x2 = center.x + outerRadius * Math.cos(angle).toFloat()
        val y2 = center.y + outerRadius * Math.sin(angle).toFloat()

        drawLine(
            color = grooveColor,
            start = Offset(x1, y1),
            end = Offset(x2, y2),
            strokeWidth = 4f
        )
    }

    // Sidewall Ring
    drawCircle(
        color = if (tireState.isAbcSidewallChecked) PassingGreen else Color(0xFF475569),
        radius = (outerRadius + innerRadius) / 2f,
        center = center,
        style = Stroke(width = 2f)
    )

    // Inner Rim Bead
    drawCircle(
        color = Color(0xFF94A3B8),
        radius = innerRadius,
        center = center,
        style = Stroke(width = 8f)
    )

    // 2. Valve Stem (Right side of tire)
    val valvePos = Offset(center.x + innerRadius * 0.7f, center.y - innerRadius * 0.7f)
    drawLine(
        color = if (tireState.isValveCapPresent) PassingGreen else SafetyAmber,
        start = valvePos,
        end = Offset(valvePos.x + 16f, valvePos.y - 16f),
        strokeWidth = 6f,
        cap = StrokeCap.Round
    )
    drawCircle(
        color = if (tireState.isValveCapPresent) PassingGreen else SafetyAmber,
        radius = 5f,
        center = Offset(valvePos.x + 16f, valvePos.y - 16f)
    )

    // 3. Pressure Gauge Readout Badge (Right side of canvas)
    val gaugeCenter = Offset(w * 0.82f, h * 0.5f)
    drawCircle(
        color = Color(0xFF1E293B),
        radius = 48f,
        center = gaugeCenter
    )
    drawCircle(
        color = PassingGreen,
        radius = 44f,
        center = gaugeCenter,
        style = Stroke(width = 4f)
    )
    // Needle pointing to 105 PSI
    drawLine(
        color = FailingRed,
        start = gaugeCenter,
        end = Offset(gaugeCenter.x + 24f, gaugeCenter.y - 20f),
        strokeWidth = 4f,
        cap = StrokeCap.Round
    )
}

// =========================================================================
// SECTION 4: RIM & HUB OIL SEAL
// =========================================================================

@Composable
private fun RimHubSealSection(
    state: PreTripInspectionState,
    onCheckRimWeldsCracks: () -> Unit,
    onCheckAllLugNuts: () -> Unit,
    onCheckRustStreaks: () -> Unit,
    onCheckHubSightGlass: () -> Unit,
    onCheckHubSealLeaks: () -> Unit,
    onInspectComponent: (String) -> Unit
) {
    val rimHub = state.frontBrakesSim.rimHubState

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Rim & Hub Oil Seal Simulation",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Visual Rim & Hub Canvas
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A))
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawRimHubSealGraphic(rimHubState = rimHub)
                }
            }

            // Summary Information Box
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                    Column {
                        Text(
                            text = "CDL Examiner Key Indicators",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "• Rust streaks radiating from lug nuts indicate loose nuts rocking on steel rims.\n• White chalky powder indicates loose nuts on aluminum wheels.\n• Hub oil seal must be checked through sight glass with oil above minimum line.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Checkpoints
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                InspectionCheckItem(
                    title = "Wheel Rim & Illegal Welds Check",
                    subtitle = "Rim has no cracks, bends, or illegal repair welds; bead seat in proper alignment",
                    isChecked = rimHub.isRimWeldsAndCracksChecked,
                    onClick = onCheckRimWeldsCracks,
                    testTag = "check_rim_welds"
                )
                InspectionCheckItem(
                    title = "All 10 Lug Nuts Present & Torqued",
                    subtitle = "Zero missing lug nuts; all nuts seated tight with no bolt hole elongation or distortion",
                    isChecked = rimHub.isAllLugNutsPresentAndTight,
                    onClick = onCheckAllLugNuts,
                    testTag = "check_rim_lugs"
                )
                InspectionCheckItem(
                    title = "Zero Rust Streaks or Powder",
                    subtitle = "No reddish-brown rust trails (steel) or white powder (aluminum) indicating looseness",
                    isChecked = rimHub.isRustStreaksChecked,
                    onClick = onCheckRustStreaks,
                    testTag = "check_rim_rust_streaks"
                )
                InspectionCheckItem(
                    title = "Hub Sight Glass Oil Level Adequate",
                    subtitle = "Hub cap clean, oil visible between Add and Full marks; rubber fill plug tight",
                    isChecked = rimHub.isHubSightGlassLevelChecked,
                    onClick = onCheckHubSightGlass,
                    testTag = "check_hub_sight_glass"
                )
                InspectionCheckItem(
                    title = "Hub Oil Seal Leak-Free",
                    subtitle = "Zero active leaks on inner wheel seal (brake side) or outer hub cap gasket",
                    isChecked = rimHub.isHubSealLeaksChecked,
                    onClick = onCheckHubSealLeaks,
                    testTag = "check_hub_seal_leaks"
                )
            }

            // Recitation & Quiz Actions
            InspectionComponentActionButtons(
                componentId = "front_rim_hub_seal",
                onInspectComponent = onInspectComponent
            )
        }
    }
}

private fun DrawScope.drawRimHubSealGraphic(rimHubState: RimHubSealSimState) {
    val w = size.width
    val h = size.height
    val center = Offset(w * 0.5f, h * 0.5f)

    val rimRadius = h * 0.44f
    val boltCircleRadius = h * 0.30f
    val hubRadius = h * 0.16f

    // 1. Wheel Rim Outer Flange
    drawCircle(
        color = Color(0xFF64748B),
        radius = rimRadius,
        center = center,
        style = Stroke(width = 14f)
    )

    // Inner wheel disc face
    drawCircle(
        color = Color(0xFF1E293B),
        radius = rimRadius - 8f,
        center = center
    )

    // 2. Ten Heavy Lug Nuts in Circular Bolt Pattern
    for (i in 0 until 10) {
        val angle = i * 36f * (Math.PI / 180.0)
        val lugX = center.x + boltCircleRadius * Math.cos(angle).toFloat()
        val lugY = center.y + boltCircleRadius * Math.sin(angle).toFloat()

        // Rust streak radiating outward if not checked
        if (!rimHubState.isRustStreaksChecked && i % 3 == 0) {
            val rustEndX = lugX + 16f * Math.cos(angle).toFloat()
            val rustEndY = lugY + 16f * Math.sin(angle).toFloat()
            drawLine(
                color = SafetyAmber.copy(alpha = 0.8f),
                start = Offset(lugX, lugY),
                end = Offset(rustEndX, rustEndY),
                strokeWidth = 3f,
                cap = StrokeCap.Round
            )
        }

        // Lug nut body
        val lugColor = if (rimHubState.isAllLugNutsPresentAndTight) PassingGreen else Color(0xFFE2E8F0)
        drawCircle(
            color = lugColor,
            radius = 9f,
            center = Offset(lugX, lugY)
        )
        drawCircle(
            color = Color(0xFF0F172A),
            radius = 4f,
            center = Offset(lugX, lugY)
        )
    }

    // 3. Central Hub Cap with Oil Sight Glass
    drawCircle(
        color = Color(0xFF475569),
        radius = hubRadius,
        center = center
    )
    drawCircle(
        color = if (rimHubState.isHubSealLeaksChecked) PassingGreen else Color(0xFF94A3B8),
        radius = hubRadius,
        center = center,
        style = Stroke(width = 4f)
    )

    // Translucent sight glass with amber gear oil
    drawCircle(
        color = if (rimHubState.isHubSightGlassLevelChecked) Color(0xFFF59E0B).copy(alpha = 0.85f) else Color(0xFF334155),
        radius = hubRadius * 0.65f,
        center = center
    )

    // Sight Glass Add & Full lines
    drawLine(
        color = Color(0xFFFFFFFF),
        start = Offset(center.x - hubRadius * 0.4f, center.y - 4f),
        end = Offset(center.x + hubRadius * 0.4f, center.y - 4f),
        strokeWidth = 2f
    )
    drawLine(
        color = Color(0xFFFFFFFF),
        start = Offset(center.x - hubRadius * 0.4f, center.y + 4f),
        end = Offset(center.x + hubRadius * 0.4f, center.y + 4f),
        strokeWidth = 2f
    )

    // Central rubber fill plug
    drawCircle(
        color = Color(0xFF0F172A),
        radius = 7f,
        center = center
    )
}

// =========================================================================
// REUSABLE INSPECTION CHECKPOINT ITEM
// =========================================================================

@Composable
private fun InspectionCheckItem(
    title: String,
    subtitle: String,
    isChecked: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    val borderColor by animateColorAsState(
        if (isChecked) PassingGreen.copy(alpha = 0.6f) else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f),
        label = "borderColor"
    )
    val bgColor by animateColorAsState(
        if (isChecked) PassingGreen.copy(alpha = 0.08f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
        label = "bgColor"
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .testTag(testTag),
        color = bgColor,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = if (isChecked) PassingGreen else MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.size(28.dp),
                border = if (!isChecked) BorderStroke(1.5.dp, MaterialTheme.colorScheme.outline) else null
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

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isChecked) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
private fun InspectionComponentActionButtons(
    componentId: String,
    onInspectComponent: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedButton(
            onClick = { onInspectComponent(componentId) },
            modifier = Modifier.weight(1f)
        ) {
            Icon(Icons.Default.RecordVoiceOver, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Recite CDL Phrases")
        }

        Button(
            onClick = { onInspectComponent(componentId) },
            modifier = Modifier.weight(1f)
        ) {
            Icon(Icons.Default.Quiz, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Take Quiz")
        }
    }
}
