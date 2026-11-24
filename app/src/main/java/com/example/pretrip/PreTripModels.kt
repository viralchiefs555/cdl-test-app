package com.example.pretrip

import com.example.data.models.Question

/**
 * Inspection zones covering the mandatory CDL Class A / Class B inspection points.
 */
enum class InspectionZone(
    val title: String,
    val subtitle: String,
    val iconDescription: String
) {
    ALL_FLUID_LEVELS(
        title = "All Fluid Levels",
        subtitle = "Engine oil, coolant & power steering fluid",
        iconDescription = "Fluids"
    ),
    STEERING_COMPONENTS(
        title = "Steering Components",
        subtitle = "Gear box, hoses, pitman arm, drag link & tie rod",
        iconDescription = "Steering"
    ),
    BELTS_AND_HOSES(
        title = "Belts & Hoses",
        subtitle = "Alternator, water pump, compressor belts & all hoses",
        iconDescription = "Belts & Hoses"
    ),
    FRONT_SUSPENSION(
        title = "Front Suspension",
        subtitle = "Leaf springs, U-bolts, mounts & shock absorber",
        iconDescription = "Suspension"
    ),
    FRONT_BRAKES_AND_AXLE(
        title = "Front Brakes & Steering Axle",
        subtitle = "Chamber, slack adjuster, drums, steer tire, rim & hub seal",
        iconDescription = "Brakes & Axle"
    ),
    ENGINE_COMPARTMENT(
        title = "Engine Components",
        subtitle = "Alternator, water pump, compressor mounts & frame",
        iconDescription = "Engine"
    ),
    COUPLING_WALKAROUND(
        title = "Coupling & Walkaround",
        subtitle = "5th wheel, kingpin, air lines & frame",
        iconDescription = "Coupling"
    ),
    IN_CAB_SAFETY(
        title = "In-Cab & Emergency",
        subtitle = "Safety gear, gauges, horns & wipers",
        iconDescription = "Cab"
    ),
    AIR_BRAKE_SYSTEM(
        title = "Air Brake Check (COLA)",
        subtitle = "4-Step dynamic leak & valve test",
        iconDescription = "Air Brakes"
    )
}

enum class ViewMode {
    VIEW_2D,
    VIEW_3D_PERSPECTIVE
}

enum class InspectionModalMode {
    NONE,
    QUIZ,
    VERBATIM_RECITATION
}

/**
 * Multiple choice question specific to the component being inspected.
 */
data class ComponentQuiz(
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

/**
 * Interactive inspection target marker.
 */
data class InspectionComponent(
    val id: String,
    val zone: InspectionZone,
    val name: String,
    val systemGroup: String, // e.g. "Steering", "Brakes", "Fluids"
    val xNormalized: Float,  // 0.0f - 1.0f relative position on inspection canvas
    val yNormalized: Float,  // 0.0f - 1.0f relative position on inspection canvas
    val zDepth: Float = 0.5f,// 0.0f - 1.0f depth layer for 3D perspective shift
    val fmcsaCriteria: String,
    val criticalFailurePoint: String,
    val isAutoFailIfMissed: Boolean = false,
    val mandatoryVerbatimPhrases: List<String>,
    val quiz: ComponentQuiz
)

/**
 * State for the interactive Air Brake Test simulation.
 */
enum class AirBrakeStep(
    val stepNumber: Int,
    val title: String,
    val instruction: String,
    val criteria: String
) {
    STEP_1_STATIC_LEAK(
        stepNumber = 1,
        title = "Static Pressure Leak Test",
        instruction = "Build air pressure to governor cut-out (120-140 PSI). Turn engine OFF, turn key ON. Release both parking brake valves. Do not touch foot brake for 1 full minute.",
        criteria = "Air loss must not exceed 2 PSI for single vehicles, or 3 PSI for combination vehicles in 1 minute."
    ),
    STEP_2_APPLIED_PRESSURE(
        stepNumber = 2,
        title = "Applied Pressure Leak Test",
        instruction = "Press and hold foot brake firmly down for 1 full minute. Listen for audible leaks and watch gauge.",
        criteria = "Air loss must not exceed 3 PSI for single vehicles, or 4 PSI for combination vehicles in 1 minute."
    ),
    STEP_3_LOW_AIR_WARNING(
        stepNumber = 3,
        title = "Low Air Warning Signal Test",
        instruction = "Fan (pump) the foot brake pedal repeatedly to bleed air pressure down to approximately 60 PSI.",
        criteria = "Low air warning light and buzzer/alarm must activate at or before 60 PSI."
    ),
    STEP_4_EMERGENCY_VALVE_POP(
        stepNumber = 4,
        title = "Emergency Spring Brake Pop-Out Test",
        instruction = "Continue pumping the foot brake pedal to drop air pressure further between 20 and 40 PSI.",
        criteria = "Both Tractor Protection Valve (yellow) and Trailer Supply Valve (red) must pop out automatically between 20-40 PSI."
    )
}

data class AirBrakeSimState(
    val currentPsi: Float = 125f,
    val isEngineRunning: Boolean = false,
    val isKeyOn: Boolean = true,
    val isFootBrakePressed: Boolean = false,
    val isTractorValvePopped: Boolean = false, // yellow diamond
    val isTrailerValvePopped: Boolean = false, // red octagon
    val isLowAirBuzzerActive: Boolean = false,
    val isLowAirLightActive: Boolean = false,
    val testStep: AirBrakeStep = AirBrakeStep.STEP_1_STATIC_LEAK,
    val step1Passed: Boolean = false,
    val step2Passed: Boolean = false,
    val step3Passed: Boolean = false,
    val step4Passed: Boolean = false,
    val timerSecondsRemaining: Int = 60,
    val isTimerRunning: Boolean = false,
    val autoFailTriggered: String? = null
)

/**
 * Three primary CDL Under-the-Hood fluid checks.
 */
enum class FluidType(
    val title: String,
    val subtitle: String,
    val iconEmoji: String
) {
    ENGINE_OIL(
        title = "Engine Oil Dipstick",
        subtitle = "Pull, wipe, re-insert & check safe level",
        iconEmoji = "🛢️"
    ),
    COOLANT_TANK(
        title = "Coolant Recovery Tank",
        subtitle = "Sight glass level, cap & leak inspection",
        iconEmoji = "💧"
    ),
    POWER_STEERING(
        title = "Power Steering Fluid",
        subtitle = "Reservoir level, hoses & no-leak check",
        iconEmoji = "⚙️"
    )
}

/**
 * The 5 sequential CDL steps for checking engine oil dipstick.
 */
enum class DipstickStep(
    val stepNumber: Int,
    val actionLabel: String,
    val instruction: String
) {
    IN_TUBE_INITIAL(
        stepNumber = 1,
        actionLabel = "1️⃣ Pull Out Dipstick",
        instruction = "Locate dipstick in engine block. Pull it out of the guide tube."
    ),
    FIRST_PULL_DIRTY(
        stepNumber = 2,
        actionLabel = "2️⃣ Wipe with Clean Rag",
        instruction = "Wipe the dipstick blade completely clean of oil with a shop towel or rag."
    ),
    WIPED_CLEAN(
        stepNumber = 3,
        actionLabel = "3️⃣ Re-insert Fully into Tube",
        instruction = "Re-insert the clean dipstick all the way down into the guide tube until fully seated."
    ),
    REINSERTED(
        stepNumber = 4,
        actionLabel = "4️⃣ Pull Out Again to Check Level",
        instruction = "Pull the dipstick out a second time to take an accurate oil level reading."
    ),
    FINAL_PULL_READING(
        stepNumber = 5,
        actionLabel = "✅ Verify Safe Operating Level",
        instruction = "Check the oil film between the operating MIN/ADD and MAX/FULL crosshatch marks."
    ),
    VERIFIED_SAFE(
        stepNumber = 6,
        actionLabel = "🎉 Completed & Safe",
        instruction = "Engine oil level is verified in the safe operating zone. Re-seat dipstick."
    )
}

/**
 * State for the interactive All Fluid Levels simulation tab.
 */
data class FluidLevelsSimState(
    val activeFluidSubTab: FluidType = FluidType.ENGINE_OIL,

    // 1. Engine Oil Dipstick State
    val dipstickStep: DipstickStep = DipstickStep.IN_TUBE_INITIAL,
    val oilLevelPercent: Float = 0.75f, // Safe operating mark (0.25 = ADD, 0.85 = FULL)
    val dipstickCompleted: Boolean = false,

    // 2. Coolant Recovery Tank State
    val coolantLevelPercent: Float = 0.70f, // Between Cold and Hot marks
    val isCoolantSightGlassChecked: Boolean = false,
    val isCoolantCapTight: Boolean = false,
    val isCoolantHosesInspected: Boolean = false,
    val coolantCompleted: Boolean = false,

    // 3. Power Steering Fluid Reservoir State
    val powerSteeringLevelPercent: Float = 0.80f,
    val isPowerSteeringLevelChecked: Boolean = false,
    val isPowerSteeringHosesChecked: Boolean = false,
    val isPowerSteeringFittingsChecked: Boolean = false,
    val powerSteeringCompleted: Boolean = false
) {
    val allFluidsCompleted: Boolean
        get() = dipstickCompleted && coolantCompleted && powerSteeringCompleted
}

/**
 * Sub-tabs for the Belts & Hoses interactive simulation tab.
 */
enum class BeltAndHoseSubTab(
    val title: String,
    val subtitle: String,
    val iconEmoji: String
) {
    ENGINE_BELTS(
        title = "Engine Driven Belts",
        subtitle = "Alternator, Water Pump & Compressor Belts",
        iconEmoji = "🔄"
    ),
    ENGINE_HOSES(
        title = "Engine Hoses",
        subtitle = "Coolant, Heater & Oil Hoses",
        iconEmoji = "〰️"
    )
}

enum class BeltComponentType(
    val title: String,
    val description: String,
    val typicalDrive: String, // "Belt Driven" or "Gear Driven"
    val maxAllowedPlayInches: Float = 0.75f // 3/4 inch
) {
    ALTERNATOR_BELT(
        title = "Alternator Belt",
        description = "Drives alternator electrical charging system",
        typicalDrive = "Belt Driven"
    ),
    WATER_PUMP_BELT(
        title = "Water Pump Belt",
        description = "Drives water pump impeller to circulate coolant",
        typicalDrive = "Belt Driven (or Gear Driven)"
    ),
    AIR_COMPRESSOR_BELT(
        title = "Air Compressor Belt / Drive",
        description = "Pressurizes primary and secondary air brake tanks",
        typicalDrive = "Gear Driven (or Belt Driven)"
    )
}

data class BeltInspectionItemState(
    val isFrayingChecked: Boolean = false,
    val isCracksCutsChecked: Boolean = false,
    val isExcessiveWearChecked: Boolean = false,
    val measuredPlayInches: Float = 0.50f, // 0.50" deflection (Safe <= 0.75")
    val isPlayTested: Boolean = false,
    val isCompleted: Boolean = false
)

enum class HoseGroupType(
    val title: String,
    val subtitle: String,
    val fluidCarried: String
) {
    COOLANT_RADIATOR(
        title = "Coolant Radiator Hoses",
        subtitle = "Upper & lower large-diameter radiator hoses",
        fluidCarried = "Engine Coolant / Antifreeze"
    ),
    HEATER_CORE(
        title = "Heater Core Hoses",
        subtitle = "Inlet & outlet lines routed to cab heater box",
        fluidCarried = "Hot Coolant to Cab Heater"
    ),
    OIL_AND_TURBO(
        title = "Engine Oil & Turbo Lines",
        subtitle = "High pressure oil feed & turbo return lines",
        fluidCarried = "Pressurized Engine Lubricating Oil"
    )
}

data class HoseInspectionItemState(
    val isClampsTightChecked: Boolean = false,
    val isDryRotChecked: Boolean = false,
    val isBubblingChecked: Boolean = false,
    val isLeaksChecked: Boolean = false,
    val isCompleted: Boolean = false
)

data class BeltsAndHosesSimState(
    val activeSubTab: BeltAndHoseSubTab = BeltAndHoseSubTab.ENGINE_BELTS,
    val selectedBelt: BeltComponentType = BeltComponentType.ALTERNATOR_BELT,
    val selectedHose: HoseGroupType = HoseGroupType.COOLANT_RADIATOR,

    // Belts states
    val alternatorBelt: BeltInspectionItemState = BeltInspectionItemState(measuredPlayInches = 0.50f),
    val waterPumpBelt: BeltInspectionItemState = BeltInspectionItemState(measuredPlayInches = 0.55f),
    val airCompressorBelt: BeltInspectionItemState = BeltInspectionItemState(measuredPlayInches = 0.45f),

    // Hoses states
    val coolantHoses: HoseInspectionItemState = HoseInspectionItemState(),
    val heaterHoses: HoseInspectionItemState = HoseInspectionItemState(),
    val oilHoses: HoseInspectionItemState = HoseInspectionItemState()
) {
    fun getBeltState(belt: BeltComponentType): BeltInspectionItemState = when (belt) {
        BeltComponentType.ALTERNATOR_BELT -> alternatorBelt
        BeltComponentType.WATER_PUMP_BELT -> waterPumpBelt
        BeltComponentType.AIR_COMPRESSOR_BELT -> airCompressorBelt
    }

    fun getHoseState(hose: HoseGroupType): HoseInspectionItemState = when (hose) {
        HoseGroupType.COOLANT_RADIATOR -> coolantHoses
        HoseGroupType.HEATER_CORE -> heaterHoses
        HoseGroupType.OIL_AND_TURBO -> oilHoses
    }

    val allBeltsCompleted: Boolean
        get() = alternatorBelt.isCompleted && waterPumpBelt.isCompleted && airCompressorBelt.isCompleted

    val allHosesCompleted: Boolean
        get() = coolantHoses.isCompleted && heaterHoses.isCompleted && oilHoses.isCompleted

    val isAllCompleted: Boolean
        get() = allBeltsCompleted && allHosesCompleted
}

/**
 * Sub-tabs for the Steering Components interactive simulation tab.
 */
enum class SteeringSubTab(
    val title: String,
    val subtitle: String,
    val iconEmoji: String
) {
    GEAR_BOX_AND_HOSES(
        title = "Steering Gear Box & Hoses",
        subtitle = "Frame mount, box casting, hoses & fluid leaks",
        iconEmoji = "⚙️"
    ),
    STEERING_LINKAGE(
        title = "Pitman Arm, Drag Link & Tie Rod",
        subtitle = "Castle nuts, cotter pins & free play check",
        iconEmoji = "🔧"
    )
}

data class SteeringGearBoxSimState(
    val isFrameMountBoltsChecked: Boolean = false, // Securely mounted to frame with no loose/missing bolts or cracks
    val isGearBoxCastingChecked: Boolean = false,  // Box not cracked, damaged, or leaking
    val isHosesInspected: Boolean = false,         // Hoses must be leak-free, no cuts, bulges, rubs
    val isShaftPlayChecked: Boolean = false,       // Steering column & U-joints have no loose joints or excessive free play
    val isCompleted: Boolean = false
)

data class SteeringLinkageSimState(
    val isPitmanArmChecked: Boolean = false,       // Connected to sector shaft, castle nut & cotter pin present & tight
    val isDragLinkChecked: Boolean = false,        // Castle nuts & cotter pins tight, rod not bent or cracked
    val isTieRodChecked: Boolean = false,          // Tie rod straight, ends tight with castle nuts & cotter pins in place
    val isJointPlayTested: Boolean = false,        // Free play shake test: zero excessive play or loose joints
    val measuredFreePlayMm: Float = 0.5f,          // 0.5 mm = firm & safe (Max allowed is under 1/8" or 3mm)
    val isCompleted: Boolean = false
)

data class SteeringComponentsSimState(
    val activeSubTab: SteeringSubTab = SteeringSubTab.GEAR_BOX_AND_HOSES,
    val gearBoxState: SteeringGearBoxSimState = SteeringGearBoxSimState(),
    val linkageState: SteeringLinkageSimState = SteeringLinkageSimState()
) {
    val allGearBoxCompleted: Boolean
        get() = gearBoxState.isCompleted

    val allLinkageCompleted: Boolean
        get() = linkageState.isCompleted

    val isAllCompleted: Boolean
        get() = allGearBoxCompleted && allLinkageCompleted
}

/**
 * Sub-tabs for the Front Suspension interactive simulation tab.
 */
enum class SuspensionSubTab(
    val title: String,
    val subtitle: String,
    val iconEmoji: String
) {
    LEAF_SPRINGS(
        title = "Leaf Springs & Alignment",
        subtitle = "Cracks, breaks, shifting & missing leaves",
        iconEmoji = "〰️"
    ),
    U_BOLTS_AND_MOUNTS(
        title = "U-Bolts & Spring Mounts",
        subtitle = "Axle clamping, frame brackets & rust trails",
        iconEmoji = "🔩"
    ),
    SHOCK_ABSORBER(
        title = "Shock Absorber",
        subtitle = "Top/bottom mounting, oil leaks & rubber bushings",
        iconEmoji = "🛡️"
    )
}

data class LeafSpringsSimState(
    val isCracksBreaksChecked: Boolean = false,   // Inspect for cracks, breaks, or damaged main/helper leaves
    val isShiftingChecked: Boolean = false,       // Check for shifted leaves (scissoring out of alignment)
    val isMissingLeavesChecked: Boolean = false,  // Verify no missing leaves (out-of-service condition)
    val isCompleted: Boolean = false
)

data class UBoltMountsSimState(
    val isUBoltsTightChecked: Boolean = false,    // Pair of U-bolts securely clamping spring to axle, nuts tight
    val isRustTrailsChecked: Boolean = false,     // No rust powder trails or shiny metal rings indicating loose bolts
    val isSpringHangersChecked: Boolean = false,  // Front/rear spring mounts & hangers not cracked or broken
    val isBushingPinsChecked: Boolean = false,    // Shackle pins and bushings intact and greased
    val isCompleted: Boolean = false
)

data class ShockAbsorberSimState(
    val isTopMountChecked: Boolean = false,       // Securely mounted at top frame bracket with tight hardware
    val isBottomMountChecked: Boolean = false,    // Securely mounted at bottom axle bracket with tight hardware
    val isOilLeaksChecked: Boolean = false,       // Inspect piston tube for active hydraulic oil weeping/leaks
    val isRubberBushingsChecked: Boolean = false, // Rubber eyelet bushings intact, not split, rotted, or missing
    val isCompleted: Boolean = false
)

data class FrontSuspensionSimState(
    val activeSubTab: SuspensionSubTab = SuspensionSubTab.LEAF_SPRINGS,
    val leafSpringsState: LeafSpringsSimState = LeafSpringsSimState(),
    val uBoltMountsState: UBoltMountsSimState = UBoltMountsSimState(),
    val shockState: ShockAbsorberSimState = ShockAbsorberSimState()
) {
    val allLeafSpringsCompleted: Boolean
        get() = leafSpringsState.isCompleted

    val allUBoltsCompleted: Boolean
        get() = uBoltMountsState.isCompleted

    val allShockCompleted: Boolean
        get() = shockState.isCompleted

    val isAllCompleted: Boolean
        get() = allLeafSpringsCompleted && allUBoltsCompleted && allShockCompleted
}

/**
 * Sub-tabs for the Front Brakes & Steering Axle interactive simulation tab.
 */
enum class FrontBrakesSubTab(
    val title: String,
    val subtitle: String,
    val iconEmoji: String
) {
    CHAMBER_AND_SLACK_ADJUSTER(
        title = "Brake Chamber & Slack Adjuster",
        subtitle = "Chamber mounting, dents, leaks & 1-inch manual pushrod pull",
        iconEmoji = "🛑"
    ),
    DRUM_AND_LININGS(
        title = "Brake Drum & Linings",
        subtitle = "Cracks, oil contamination & 1/4\" minimum lining thickness",
        iconEmoji = "🥁"
    ),
    STEER_TIRE(
        title = "Steer Tire (4/32\" & ABC)",
        subtitle = "Tread gauge, ABC check, no recaps & air gauge reading",
        iconEmoji = "🛞"
    ),
    RIM_AND_HUB_SEAL(
        title = "Rim & Hub Oil Seal",
        subtitle = "No illegal welds, all lugs tight, zero rust streaks & oil level",
        iconEmoji = "⚙️"
    )
}

data class BrakeChamberSimState(
    val isChamberMountedTight: Boolean = false, // Securely mounted, mounting clamp tight
    val isNoDentsOrAirLeaks: Boolean = false,   // No dents, cracks, or audible air leaks
    val isPushrodAndClevisPinChecked: Boolean = false, // Pushrod straight, clevis & cotter pin in place
    val measuredPushrodTravelInches: Float = 0.75f, // Safe <= 1.0 inch
    val isManualPullTested: Boolean = false,    // Hand pull test with brakes released
    val isCompleted: Boolean = false
)

data class BrakeDrumLiningsSimState(
    val isDrumCracksChecked: Boolean = false,   // No cracks, dents, holes, or deep grooving
    val isOilGreaseContaminationChecked: Boolean = false, // Free of oil/grease contamination from hub seal
    val measuredLiningThicknessInches: Float = 0.35f, // Safe >= 0.25" (1/4 inch)
    val isLiningThicknessMeasured: Boolean = false, // Gauge measurement confirmed >= 1/4"
    val isCompleted: Boolean = false
)

data class SteerTireSimState(
    val measuredTreadDepth32nds: Int = 5,       // Safe >= 4/32"
    val isTreadDepthGaugeChecked: Boolean = false, // Checked with tread depth gauge across all major grooves
    val isAbcSidewallChecked: Boolean = false,  // Abrasions, Bulges, Cuts check passed
    val isExposedFabricChecked: Boolean = false,// Zero exposed cords or fabric
    val isRecapProhibitionChecked: Boolean = false, // Verified virgin rubber (no recaps/retreads on steer axle)
    val measuredPsi: Int = 105,                 // Manufacturer spec 100-110 PSI
    val isAirPressureGaugeChecked: Boolean = false, // Checked with tire pressure gauge
    val isValveCapPresent: Boolean = false,     // Valve stem cap present & tight
    val isCompleted: Boolean = false
)

data class RimHubSealSimState(
    val isRimWeldsAndCracksChecked: Boolean = false, // No cracks, bends, or illegal repair welds
    val isAllLugNutsPresentAndTight: Boolean = false, // All lug nuts present, torqued tight
    val isRustStreaksChecked: Boolean = false,   // No rust trails (steel) or white powder (aluminum)
    val isHubSightGlassLevelChecked: Boolean = false, // Hub oil level visible between Add & Full lines
    val isHubSealLeaksChecked: Boolean = false,  // Zero active leaks on wheel inner/outer faces
    val isCompleted: Boolean = false
)

data class FrontBrakesSimState(
    val activeSubTab: FrontBrakesSubTab = FrontBrakesSubTab.CHAMBER_AND_SLACK_ADJUSTER,
    val chamberState: BrakeChamberSimState = BrakeChamberSimState(),
    val drumLiningsState: BrakeDrumLiningsSimState = BrakeDrumLiningsSimState(),
    val steerTireState: SteerTireSimState = SteerTireSimState(),
    val rimHubState: RimHubSealSimState = RimHubSealSimState()
) {
    val allChamberCompleted: Boolean
        get() = chamberState.isCompleted

    val allDrumLiningsCompleted: Boolean
        get() = drumLiningsState.isCompleted

    val allSteerTireCompleted: Boolean
        get() = steerTireState.isCompleted

    val allRimHubCompleted: Boolean
        get() = rimHubState.isCompleted

    val isAllCompleted: Boolean
        get() = allChamberCompleted && allDrumLiningsCompleted && allSteerTireCompleted && allRimHubCompleted
}

/**
 * Entire Pre-Trip Inspection System UI State.
 */
data class PreTripInspectionState(
    val currentZone: InspectionZone = InspectionZone.ALL_FLUID_LEVELS,
    val viewMode: ViewMode = ViewMode.VIEW_2D,
    val components: List<InspectionComponent> = emptyList(),
    val selectedComponentId: String? = null,
    val modalMode: InspectionModalMode = InspectionModalMode.NONE,
    val inspectedComponentIds: Set<String> = emptySet(),
    val passedComponentIds: Set<String> = emptySet(),
    val failedComponentIds: Set<String> = emptySet(),
    val autoFailsEncountered: List<String> = emptyList(),
    val verbatimSelectedMap: Map<String, Set<Int>> = emptyMap(), // componentId -> set of checked indices
    val pointsScore: Int = 0,
    val airBrakeSim: AirBrakeSimState = AirBrakeSimState(),
    val fluidSim: FluidLevelsSimState = FluidLevelsSimState(),
    val beltsHosesSim: BeltsAndHosesSimState = BeltsAndHosesSimState(),
    val steeringSim: SteeringComponentsSimState = SteeringComponentsSimState(),
    val suspensionSim: FrontSuspensionSimState = FrontSuspensionSimState(),
    val frontBrakesSim: FrontBrakesSimState = FrontBrakesSimState(),
    val showSummaryDialog: Boolean = false
) {
    val selectedComponent: InspectionComponent?
        get() = components.find { it.id == selectedComponentId }

    val zoneComponents: List<InspectionComponent>
        get() = components.filter { it.zone == currentZone }

    val totalComponentsCount: Int
        get() = components.size

    val inspectedCount: Int
        get() = inspectedComponentIds.size

    val progressPercent: Int
        get() = if (totalComponentsCount > 0) (inspectedCount * 100) / totalComponentsCount else 0

    val scorePercent: Int
        get() = if (inspectedCount > 0) (passedComponentIds.size * 100) / totalComponentsCount else 0

    val isPassed: Boolean
        get() = autoFailsEncountered.isEmpty() && scorePercent >= 80
}
