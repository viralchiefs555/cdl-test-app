package com.example.data.repository

import com.example.data.models.CDLCategory
import com.example.data.models.Question

/**
 * Master Question Pool for CDL Air Brakes Endorsement.
 * Total: 120 official questions across all 4 key domain categories:
 * - Components & Mechanics
 * - Sub-Braking Systems
 * - Inspection & Leak Tests
 * - Safe Driving & Emergency Operations
 */
val airBrakesQuestions: List<Question> = listOf(
    Question(
        id = "ab_001",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "At what air pressure level should the air compressor governor cut out?",
        options = listOf(
            "Around 120 to 130 PSI",
            "Around 100 to 110 PSI",
            "Around 80 to 90 PSI",
            "Around 145 to 155 PSI",
        ),
        correctIndex = 0,
        explanation = "When air pressure reaches approximately 120 to 130 PSI, the governor cuts out and stops the compressor from pumping air into the tanks.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_002",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "At what air pressure level should the air compressor governor cut in?",
        options = listOf(
            "Around 125 PSI",
            "Around 100 PSI",
            "Around 85 PSI",
            "Around 60 PSI",
        ),
        correctIndex = 1,
        explanation = "When tank pressure drops to approximately 100 PSI, the governor cuts in and allows the air compressor to resume pumping.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_003",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "Why must air tanks be drained regularly if they do not have automatic drains?",
        options = listOf(
            "To purge trapped air pressure and discharge excess tank exhaust vapors",
            "To relieve thermal expansion and protect compressor governor components",
            "To remove water and compressor oil that can freeze or damage brake valves",
            "To lower operating temperatures and stabilize pneumatic system pressures",
        ),
        correctIndex = 2,
        explanation = "Air storage tanks accumulate condensed water and compressor oil, which can freeze in cold weather and cause total brake failure.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_004",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What is the primary function of the alcohol evaporator in an air brake system?",
        options = listOf(
            "To cool compressed air before it enters the primary service reservoirs",
            "To purge oil residues and sludge accumulation from service brake valves",
            "To lubricate slack adjuster splines and camshaft bushings automatically",
            "To inject alcohol vapor into air lines to prevent ice formation in winter",
        ),
        correctIndex = 3,
        explanation = "An alcohol evaporator injects alcohol vapor into the air system to reduce the freezing point of moisture in cold weather.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_005",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "At approximately what pressure is the safety relief valve in an air brake tank set to open?",
        options = listOf(
            "At approximately 150 PSI",
            "At approximately 125 PSI",
            "At approximately 175 PSI",
            "At approximately 100 PSI",
        ),
        correctIndex = 0,
        explanation = "A safety relief valve protects the tank and air system from excessive pressure by blowing off air at 150 PSI.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_006",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "Which component turns the brake camshaft to push the brake shoes against the brake drum?",
        options = listOf(
            "Treadle valve",
            "Slack adjuster",
            "Air compressor",
            "Governor valve",
        ),
        correctIndex = 1,
        explanation = "The slack adjuster connects the brake chamber pushrod to the brake camshaft, rotating the S-cam when air enters the chamber.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_007",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "In an S-cam drum brake, what happens when you press the brake pedal?",
        options = listOf(
            "Air exhausts from chambers, allowing return springs to pull the brake shoes back",
            "Hydraulic pressure forces the caliper pistons outward against the brake rotor",
            "Air pushes the pushrod out, rotating the slack adjuster and S-cam to press shoes",
            "Heavy mechanical springs compress, locking the friction linings against drums",
        ),
        correctIndex = 2,
        explanation = "Pressing the pedal directs compressed air into the chamber, pushing out the pushrod and rotating the S-cam to press shoes against the drum.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_008",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What is the low air pressure warning requirement on all vehicles equipped with air brakes?",
        options = listOf(
            "An audible warning buzzer that sounds continuously at 80 PSI tank pressure",
            "An automatic engine shutdown circuit that cuts ignition power at 40 PSI air",
            "A dashboard warning light that remains illuminated whenever the engine idles",
            "A visible warning signal that activates before tank pressure drops below 60 PSI",
        ),
        correctIndex = 3,
        explanation = "Federal standards require a visual (and often audible) low air pressure signal to activate before pressure drops below 55–60 PSI.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_009",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Sub-Braking Systems",
        questionText = "What holds the parking brake in place when parking a vehicle equipped with spring brakes?",
        options = listOf(
            "Mechanical force from heavy steel springs",
            "Constant air pressure from the supply tank",
            "Hydraulic pressure locked inside the lines",
            "Electromagnetic coils inside the brake hub",
        ),
        correctIndex = 0,
        explanation = "Spring brakes rely on powerful mechanical springs that apply the brakes when air pressure is released from the chamber.",
        manualCitation = "FMCSA CDL Manual Section 5.2 - Dual Air & Sub-Brake Systems"
    ),
    Question(
        id = "ab_010",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Sub-Braking Systems",
        questionText = "At what air pressure range will the parking brake control valve (yellow diamond knob) usually pop out?",
        options = listOf(
            "Between 55 and 70 PSI",
            "Between 20 and 45 PSI",
            "Between 80 and 95 PSI",
            "Between 5 and 15 PSI",
        ),
        correctIndex = 1,
        explanation = "When main tank pressure drops down to 20–45 PSI, the spring brakes apply automatically as the yellow knob pops out.",
        manualCitation = "FMCSA CDL Manual Section 5.2 - Dual Air & Sub-Brake Systems"
    ),
    Question(
        id = "ab_011",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "What is the maximum allowable air leakage rate for a single vehicle with the engine off and brakes released?",
        options = listOf(
            "No more than 1 PSI in one minute",
            "No more than 3 PSI in one minute",
            "No more than 2 PSI in one minute",
            "No more than 4 PSI in one minute",
        ),
        correctIndex = 2,
        explanation = "For single vehicles with engine off and brakes released (static test), air loss must not exceed 2 PSI per minute.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_012",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "What is the maximum allowable air leakage rate for a single vehicle with engine off and service brakes fully applied?",
        options = listOf(
            "No more than 2 PSI in one minute",
            "No more than 4 PSI in one minute",
            "No more than 5 PSI in one minute",
            "No more than 3 PSI in one minute",
        ),
        correctIndex = 3,
        explanation = "For single vehicles with engine off and pedal fully depressed (applied test), air loss must not exceed 3 PSI per minute.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_013",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "What is the maximum allowable air leakage rate for a combination vehicle with engine off and brakes released?",
        options = listOf(
            "No more than 3 PSI in one minute",
            "No more than 2 PSI in one minute",
            "No more than 4 PSI in one minute",
            "No more than 5 PSI in one minute",
        ),
        correctIndex = 0,
        explanation = "For combination vehicles in a static state (engine off, brakes released), air loss must not exceed 3 PSI per minute.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_014",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "What is the maximum allowable air leakage rate for a combination vehicle with engine off and service brakes fully applied?",
        options = listOf(
            "No more than 3 PSI in one minute",
            "No more than 4 PSI in one minute",
            "No more than 5 PSI in one minute",
            "No more than 6 PSI in one minute",
        ),
        correctIndex = 1,
        explanation = "For combination vehicles under applied pressure (engine off, pedal fully pressed), air loss must not exceed 4 PSI per minute.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_015",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "How do you check manual slack adjusters during a pre-trip inspection?",
        options = listOf(
            "Set parking brakes, rev the engine, and check brake drum temperature levels",
            "Apply full foot pedal pressure and visually inspect chamber mounting studs",
            "Chock wheels, release parking brakes, and pull hard on each slack adjuster",
            "Engage spring brakes on a slight grade and measure trailer rolling distance",
        ),
        correctIndex = 2,
        explanation = "Slack adjusters must be pulled by hand with wheels chocked and parking brakes released to verify pushrod travel.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_016",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "When checking manual slack adjusters, how much pushrod travel indicates that adjustment is required?",
        options = listOf(
            "More than 2 inches of free movement",
            "More than 1/4 inch of free movement",
            "More than 3 inches of free movement",
            "More than 1 inch of free movement",
        ),
        correctIndex = 3,
        explanation = "If a manual slack adjuster moves more than approximately 1 inch where the pushrod attaches, it needs adjustment.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_017",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Safe Driving & Emergency Operations",
        questionText = "What is brake lag distance at 55 MPH for an air brake system?",
        options = listOf(
            "Approximately 32 feet of travel",
            "Approximately 15 feet of travel",
            "Approximately 50 feet of travel",
            "Approximately 65 feet of travel",
        ),
        correctIndex = 0,
        explanation = "Air takes roughly 1/2 second to travel through lines, adding approximately 32 feet of lag distance at 55 MPH.",
        manualCitation = "FMCSA CDL Manual Section 5.4 - Using Air Brakes"
    ),
    Question(
        id = "ab_018",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Safe Driving & Emergency Operations",
        questionText = "Total stopping distance for air brakes includes which of the following combined distances?",
        options = listOf(
            "Perception distance + Reaction distance + Engine brake distance + Stop distance",
            "Perception distance + Reaction distance + Brake lag distance + Braking distance",
            "Perception distance + Reaction distance + Following distance + Stopping distance",
            "Perception distance + Sight distance + Stopping distance + Vehicle skid distance",
        ),
        correctIndex = 1,
        explanation = "Air brake stopping distance adds brake lag distance to normal perception, reaction, and physical braking distances.",
        manualCitation = "FMCSA CDL Manual Section 5.4 - Using Air Brakes"
    ),
    Question(
        id = "ab_019",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Safe Driving & Emergency Operations",
        questionText = "What causes severe brake fade during long downhill mountain grades?",
        options = listOf(
            "Low air compressor output caused by worn governor valves and leaking fittings",
            "Moisture accumulation inside brake drums freezing during rapid deceleration",
            "Excessive heat generated by using brakes too much instead of engine braking",
            "Improper slack adjuster travel causing uneven friction contact across drums",
        ),
        correctIndex = 2,
        explanation = "Brake fade occurs when extreme heat causes friction linings to expand and lose stopping power.",
        manualCitation = "FMCSA CDL Manual Section 5.4 - Using Air Brakes"
    ),
    Question(
        id = "ab_020",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Safe Driving & Emergency Operations",
        questionText = "What is the proper technique for braking on a steep downgrade (snubbing technique)?",
        options = listOf(
            "Apply light continuous pedal pressure all the way down the entire downgrade",
            "Pump the brake pedal rapidly to keep air reservoirs charged and drums cool",
            "Use only the trailer trolley valve to control vehicle speed down the grade",
            "Brake 5 MPH below safe speed, release completely, and repeat at safe speed",
        ),
        correctIndex = 3,
        explanation = "Apply brakes firmly until speed drops 5 MPH below safe speed, release, let speed rise back to safe speed, and repeat.",
        manualCitation = "FMCSA CDL Manual Section 5.4 - Using Air Brakes"
    ),
    Question(
        id = "ab_021",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Safe Driving & Emergency Operations",
        questionText = "Why should you NOT apply the parking brake when the brakes are extremely hot?",
        options = listOf(
            "Extreme heat can warp or crack drums as they cool and contract around shoes",
            "Hot drums cause the mechanical parking springs to lose their internal tension",
            "Air pressure trapped in the spring chambers will rapidly overheat and rupture",
            "Hot friction linings will melt completely and weld the pushrod into position",
        ),
        correctIndex = 0,
        explanation = "Hot drums contract as they cool; setting parking spring brakes on hot drums can crack drums or damage pads.",
        manualCitation = "FMCSA CDL Manual Section 5.4 - Using Air Brakes"
    ),
    Question(
        id = "ab_022",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Safe Driving & Emergency Operations",
        questionText = "Why should you NOT apply the parking brake in freezing weather if the brakes are wet?",
        options = listOf(
            "Ice in the air supply lines will immediately burst the brake chambers",
            "Wet brake shoes can freeze solid to the drums, locking wheels in place",
            "Freezing moisture causes the parking brake control knob to pop inward",
            "Cold temperatures reduce steel spring tension, causing vehicle runaway",
        ),
        correctIndex = 1,
        explanation = "Wet brake shoes can freeze solid against drums in sub-freezing temperatures, locking the wheels.",
        manualCitation = "FMCSA CDL Manual Section 5.4 - Using Air Brakes"
    ),
    Question(
        id = "ab_023",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Safe Driving & Emergency Operations",
        questionText = "What is controlled braking during an emergency stopping situation?",
        options = listOf(
            "Pumping foot pedal rapidly to cycle air pressure and prevent heat buildup",
            "Pressing pedal fully until wheels lock, releasing, and re-applying firmly",
            "Applying brakes as hard as possible without locking wheels or losing control",
            "Using the trailer hand valve to steady the combination before foot braking",
        ),
        correctIndex = 2,
        explanation = "Controlled braking involves applying maximum pedal pressure without locking wheels or losing directional control.",
        manualCitation = "FMCSA CDL Manual Section 5.4 - Using Air Brakes"
    ),
    Question(
        id = "ab_024",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Safe Driving & Emergency Operations",
        questionText = "What is stab braking during an emergency stopping situation?",
        options = listOf(
            "Applying steady light pressure while downshifting rapidly through every gear",
            "Pulling the trailer hand control valve while steering into the direction of skid",
            "Holding service brake pedal halfway down while modulating the parking brake knob",
            "Applying brakes fully, releasing when wheels lock, and re-applying when rolling",
        ),
        correctIndex = 3,
        explanation = "Stab braking requires pressing brakes fully, releasing completely when wheels lock, and re-applying once wheels roll.",
        manualCitation = "FMCSA CDL Manual Section 5.4 - Using Air Brakes"
    ),
    Question(
        id = "ab_025",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Sub-Braking Systems",
        questionText = "What does a dual air brake system consist of?",
        options = listOf(
            "Two separate air systems that share a single set of controls and compressor",
            "Two distinct foot pedals operating front and rear brake circuits separately",
            "A primary mechanical cable system paired with secondary hydraulic chambers",
            "An air brake system combined with an auxiliary electric retarder on the axle",
        ),
        correctIndex = 0,
        explanation = "Dual air systems have primary and secondary circuits operating distinct axles, powered by one compressor and foot pedal.",
        manualCitation = "FMCSA CDL Manual Section 5.2 - Dual Air & Sub-Brake Systems"
    ),
    Question(
        id = "ab_026",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Sub-Braking Systems",
        questionText = "Before driving a vehicle with a dual air system, how much air pressure must be built in both primary and secondary systems?",
        options = listOf(
            "At least 120 PSI",
            "At least 100 PSI",
            "At least 60 PSI",
            "At least 80 PSI",
        ),
        correctIndex = 1,
        explanation = "Both primary and secondary air circuits must reach at least 100 PSI before driving the vehicle.",
        manualCitation = "FMCSA CDL Manual Section 5.2 - Dual Air & Sub-Brake Systems"
    ),
    Question(
        id = "ab_027",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What is the function of the supply pressure gauge?",
        options = listOf(
            "The amount of air pressure being delivered to the brake chambers during stops",
            "The differential pressure between the trailer supply line and tractor tanks",
            "The amount of air pressure stored in the primary and secondary reservoirs",
            "The instantaneous pressure generated by the air compressor piston strokes",
        ),
        correctIndex = 2,
        explanation = "Supply pressure gauges display the exact air pressure remaining in storage reservoirs.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_028",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What does an application pressure gauge show?",
        options = listOf(
            "The total pressure remaining in the primary and secondary storage reservoirs",
            "The pressure setting at which the safety relief valve opens on the wet tank",
            "The air pressure differential across the quick release and relay valve ports",
            "The amount of air pressure delivered to service brakes when pedal is pressed",
        ),
        correctIndex = 3,
        explanation = "Application pressure gauges indicate how much air pressure is being delivered to brake chambers during pedal use.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_029",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Safe Driving & Emergency Operations",
        questionText = "If the application pressure gauge indicates increasing pressure to maintain the same speed on a downgrade, what does this signal?",
        options = listOf(
            "Brakes are fading and losing stopping power due to excessive heat buildup",
            "Air compressor governor is failing to supply adequate air to the tanks",
            "Slack adjusters are automatically ratcheting into closer drum contact",
            "Treadle valve exhaust port is partially obstructed by moisture sludge",
        ),
        correctIndex = 0,
        explanation = "Needing more air pressure to maintain speed indicates fading brakes caused by overheating.",
        manualCitation = "FMCSA CDL Manual Section 5.4 - Using Air Brakes"
    ),
    Question(
        id = "ab_030",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Safe Driving & Emergency Operations",
        questionText = "What should you do if the low air pressure warning light and buzzer activate while driving?",
        options = listOf(
            "Continue driving at reduced speed to the nearest authorized repair facility",
            "Stop safely and park immediately while service brakes are still controllable",
            "Pump the brake pedal rapidly to boost reservoir pressure above cutoff level",
            "Downshift into low gear and apply the trailer trolley valve continuously",
        ),
        correctIndex = 1,
        explanation = "Safely pull off and stop immediately before air drops low enough to trigger emergency spring brake lockup.",
        manualCitation = "FMCSA CDL Manual Section 5.4 - Using Air Brakes"
    ),
    Question(
        id = "ab_031",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Sub-Braking Systems",
        questionText = "What happens if air pressure drops below 20 to 45 PSI while driving?",
        options = listOf(
            "Service brakes gain extra stopping power from secondary reservoir lines",
            "Treadle foot pedal locks down completely and disables all front braking",
            "Spring brakes apply automatically, locking wheels or stopping vehicle",
            "Air compressor clutch engages at high RPM to restore storage pressure",
        ),
        correctIndex = 2,
        explanation = "Below 20–45 PSI, air pressure can no longer hold back spring mechanisms, causing emergency spring brake engagement.",
        manualCitation = "FMCSA CDL Manual Section 5.2 - Dual Air & Sub-Brake Systems"
    ),
    Question(
        id = "ab_032",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "How do you test the air compressor governor cut-in and cut-out pressures?",
        options = listOf(
            "Idle engine until pressure reaches 150 PSI, then open tank drain petcock fully",
            "Set parking brakes, rev engine to governor limit, and time pressure drop rate",
            "Apply full service pedal pressure while checking the low pressure buzzer light",
            "Run engine to cut-out (~125 PSI), pump pedal until compressor kicks in (~100 PSI)",
        ),
        correctIndex = 3,
        explanation = "Verify cut-out when pressure stops rising (~120-130 PSI) and cut-in when pumping pedal drops pressure to ~100 PSI.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_033",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Sub-Braking Systems",
        questionText = "What is the purpose of the tractor protection valve in combination vehicles?",
        options = listOf(
            "Keeps tractor air supply safe if trailer breaks away or leaks severely",
            "Regulates air compressor cycling speed during heavy mountain descents",
            "Prevents tractor drive wheels from spinning on slick or icy road surfaces",
            "Directs high pressure emergency air into the steering axle brake chambers",
        ),
        correctIndex = 0,
        explanation = "Tractor protection valves close automatically if trailer separates or suffers massive air loss, protecting tractor air.",
        manualCitation = "FMCSA CDL Manual Section 5.2 - Dual Air & Sub-Brake Systems"
    ),
    Question(
        id = "ab_034",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What color is the trailer air supply control knob on the dashboard?",
        options = listOf(
            "Yellow, 8-sided (diamond) shape",
            "Red, 8-sided (octagonal) shape",
            "Blue, 4-sided (square) shape",
            "Green, round (circular) shape",
        ),
        correctIndex = 1,
        explanation = "The trailer air supply control knob is shaped like a red 8-sided octagon.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_035",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What color is the parking brake control knob on the dashboard?",
        options = listOf(
            "Red, octagonal 8-sided shape",
            "Blue, rectangular lever switch",
            "Yellow, diamond / 8-sided shape",
            "Black, round push-pull button",
        ),
        correctIndex = 2,
        explanation = "The main parking brake control valve is a yellow diamond/8-sided push-pull knob.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_036",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "How do you test the low air pressure warning signal during a pre-trip inspection?",
        options = listOf(
            "Run engine at idle, press foot pedal once firmly, and observe dashboard buzzer",
            "Apply parking brakes, chock wheels, and listen for pressure relief valve hiss",
            "Bleed air from trailer gladhands until tractor service line pressure equals zero",
            "Turn key ON with engine OFF, fan brake pedal until warning triggers above 60 PSI",
        ),
        correctIndex = 3,
        explanation = "Turn key ON with engine OFF, fan brake pedal to drop air, and verify warning activates before pressure reaches 60 PSI.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_037",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "How do you test that spring brakes apply automatically when air pressure drops?",
        options = listOf(
            "Fan foot pedal until pressure drops to 20–45 PSI and knobs pop out",
            "Apply hand trolley valve until emergency line releases air pressure",
            "Hold service brake pedal down for two minutes with the engine running",
            "Shut engine OFF and turn steering wheel fully from lock to lock position",
        ),
        correctIndex = 0,
        explanation = "Fan foot pedal down to 20–45 PSI range and confirm valve control knobs pop out automatically.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_038",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "How do you test parking brake holding power during a pre-trip inspection?",
        options = listOf(
            "Drive at 20 MPH, apply parking brake, and verify vehicle stopping rate",
            "Set parking brake, put truck in low gear, and gently pull against it",
            "Park on a steep incline, turn engine off, and release foot brake pedal",
            "Rev engine to 1500 RPM in neutral while monitoring reservoir pressure",
        ),
        correctIndex = 1,
        explanation = "Test parking brakes by engaging them, selecting low gear, and gently attempting to pull forward against the brakes.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_039",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "How do you test service brake effectiveness during a pre-trip inspection?",
        options = listOf(
            "Accelerate to 25 MPH on highway and execute a rapid emergency stop test",
            "Hold foot pedal down with vehicle parked and listen for exterior leaks",
            "Drive 5 MPH, press pedal firmly, and check for straight smooth stopping",
            "Apply trailer hand valve at cruising speed to check tractor alignment",
        ),
        correctIndex = 2,
        explanation = "Drive forward slowly (~5 MPH), press service brake pedal, and verify vehicle stops smoothly without pulling left or right.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_040",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "What is the rate of air pressure buildup required in dual air systems from 85 to 100 PSI?",
        options = listOf(
            "Within 90 seconds at operating engine RPM",
            "Within 15 seconds at operating engine RPM",
            "Within 2 minutes at operating engine RPM",
            "Within 45 seconds at operating engine RPM",
        ),
        correctIndex = 3,
        explanation = "In dual air systems, air pressure must build from 85 to 100 PSI within 45 seconds at operating engine RPM.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_041",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What happens when you press the brake pedal down in an air brake vehicle?",
        options = listOf(
            "Compressed air enters brake chambers to force shoes against drums or discs",
            "Return springs expand outward to push friction pads against rotating rotors",
            "Hydraulic brake fluid pressurizes internal slave cylinders on every axle",
            "Mechanical cables pull the slack adjuster arms directly toward frame rails",
        ),
        correctIndex = 0,
        explanation = "Depressing foot pedal opens treadle valve, directing stored compressed air into brake chambers to apply brakes.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_042",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Safe Driving & Emergency Operations",
        questionText = "What should you do if your service brakes fail while driving downhill?",
        options = listOf(
            "Shift transmission into reverse gear and pump the service brake foot pedal",
            "Steer into an escape ramp, runaway vehicle lane, or open field immediately",
            "Yank the yellow parking brake knob while counter-steering across traffic",
            "Turn engine ignition switch completely OFF to engage steering column lock",
        ),
        correctIndex = 1,
        explanation = "If service brakes fail on a downgrade, immediately steer into an escape ramp or safe runaway area.",
        manualCitation = "FMCSA CDL Manual Section 5.4 - Using Air Brakes"
    ),
    Question(
        id = "ab_043",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Safe Driving & Emergency Operations",
        questionText = "Why is it dangerous to push down the service brake pedal when spring brakes are fully applied?",
        options = listOf(
            "The sudden burst of air pressure will permanently rupture tire bead seats",
            "Trapped air will backflow into compressor and destroy intake reed valves",
            "Combining air and spring forces can damage or break brake parts (compounding)",
            "The foot pedal will seize in depressed position and burn out lining shoes",
        ),
        correctIndex = 2,
        explanation = "Compounding brake forces (air force + spring force simultaneously) can damage or break foundation brake components.",
        manualCitation = "FMCSA CDL Manual Section 5.4 - Using Air Brakes"
    ),
    Question(
        id = "ab_044",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Sub-Braking Systems",
        questionText = "Which brake system applies and releases brakes when driver presses foot pedal during normal driving?",
        options = listOf(
            "The parking brake system",
            "The emergency brake system",
            "The retarder brake system",
            "The service brake system",
        ),
        correctIndex = 3,
        explanation = "The service brake system operates during routine driving using the foot pedal valve.",
        manualCitation = "FMCSA CDL Manual Section 5.2 - Dual Air & Sub-Brake Systems"
    ),
    Question(
        id = "ab_045",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Sub-Braking Systems",
        questionText = "Which brake system applies and releases brakes when yellow cab control knob is pulled or pushed?",
        options = listOf(
            "The parking brake system",
            "The service brake system",
            "The foundation brake system",
            "The dynamic brake system",
        ),
        correctIndex = 0,
        explanation = "The parking brake system is controlled inside the cab using the yellow push-pull control knob.",
        manualCitation = "FMCSA CDL Manual Section 5.2 - Dual Air & Sub-Brake Systems"
    ),
    Question(
        id = "ab_046",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Sub-Braking Systems",
        questionText = "Which brake system uses parts of service and parking brake systems to stop vehicle in event of primary system failure?",
        options = listOf(
            "The auxiliary retarder system",
            "The emergency brake system",
            "The service brake system",
            "The foundation drum system",
        ),
        correctIndex = 1,
        explanation = "The emergency brake system utilizes spring brakes or remaining air to stop the vehicle if service brakes fail.",
        manualCitation = "FMCSA CDL Manual Section 5.2 - Dual Air & Sub-Brake Systems"
    ),
    Question(
        id = "ab_047",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "Where are spring brakes located on commercial vehicles?",
        options = listOf(
            "On front steering axle assemblies only",
            "On all axles including steering wheels",
            "On rear drive and trailer axles only",
            "Inside the transmission transfer case",
        ),
        correctIndex = 2,
        explanation = "Spring brake chambers are installed on rear drive and trailer axles, not steering axles.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_048",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What is the function of the check valve in air brake tanks?",
        options = listOf(
            "Directs high pressure exhaust air to atmosphere during governor cut-out cycle",
            "Balances application pressure equally between primary and secondary circuits",
            "Allows hot air to circulate back through compressor cooling jackets safely",
            "Prevents stored compressed air from leaking out if compressor supply line breaks",
        ),
        correctIndex = 3,
        explanation = "One-way check valves allow air into storage tanks but prevent air from backflowing if supply lines leak.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_049",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What type of brake system uses disc pads pressed against a rotating metal rotor?",
        options = listOf(
            "Air disc brake system",
            "S-cam drum brake system",
            "Wedge brake drum system",
            "Hydro-boost brake system",
        ),
        correctIndex = 0,
        explanation = "Air disc brakes use an air-operated caliper to clamp friction pads directly against a metal disc rotor.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_050",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "If your vehicle has automatic slack adjusters, when should you manually adjust them?",
        options = listOf(
            "Every morning before beginning your scheduled daily commercial driving run",
            "Only during installation, routine overhaul, or emergency maintenance repairs",
            "Whenever descending steep mountain grades with heavy gross vehicle cargo",
            "At every mandatory fuel stop to compensate for normal friction lining wear",
        ),
        correctIndex = 1,
        explanation = "Automatic slack adjusters adjust automatically; manual adjustment should only be done during repairs/maintenance.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_051",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What is the function of the supply tank (also called wet tank)?",
        options = listOf(
            "Secondary reservoir storing dry air dedicated exclusively to steering brakes",
            "Emergency reserve reservoir supplying air directly to break-away valves",
            "First storage tank receiving air from compressor where water and oil collect",
            "Auxiliary air tank operating pneumatic air suspension and cab leveling valves",
        ),
        correctIndex = 2,
        explanation = "The supply (wet) tank receives air directly from compressor, trapping initial condensed water and compressor oil.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_052",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Safe Driving & Emergency Operations",
        questionText = "What should you do if the low air pressure warning light stays on after engine start?",
        options = listOf(
            "Drive slowly in low gear while pumping the brake pedal to clear the sensor",
            "Rev the engine in neutral until the safety relief valve opens at 150 PSI",
            "Pull the red trailer supply knob and drive using tractor brakes exclusively",
            "Do not move the vehicle until air pressure builds up and the warning turns off",
        ),
        correctIndex = 3,
        explanation = "Never move an air brake vehicle until air pressure reaches safe operating levels and warnings shut off.",
        manualCitation = "FMCSA CDL Manual Section 5.4 - Using Air Brakes"
    ),
    Question(
        id = "ab_053",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "Which of the following is true about front wheel limiting valves on older trucks?",
        options = listOf(
            "They cut front brake pressure in half on slippery roads when switch is set",
            "They double front brake pressure automatically whenever roads become wet",
            "They disconnect rear drive axle brakes during light empty vehicle driving",
            "They prevent the steering axle wheels from turning during sudden stops",
        ),
        correctIndex = 0,
        explanation = "Older vehicles had manual limiting valves reducing front brake application pressure by half in slippery conditions.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_054",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "Modern air brake vehicles have automatic front wheel limiting controls that work by doing what?",
        options = listOf(
            "Cutting off front brake air completely unless vehicle road speed exceeds 45 MPH",
            "Reducing front brake force on light pedal pressure, giving full force on heavy braking",
            "Shifting braking force entirely to trailer axles whenever anti-lock brakes engage",
            "Locking steering wheel firmly into position during sudden emergency brake stops",
        ),
        correctIndex = 1,
        explanation = "Automatic limiting controls reduce front brake pressure during light braking, providing full pressure during hard stops.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_055",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Safe Driving & Emergency Operations",
        questionText = "What is the main danger of fanning (pumping) the brake pedal repeatedly on air brake vehicles?",
        options = listOf(
            "It overheats the treadle foot valve and causes the return springs to snap off",
            "It forces moisture backward through the air dryer into compressor cylinder head",
            "It wastes compressed air faster than compressor replaces it, causing brake loss",
            "It causes automatic slack adjusters to over-tighten and drag drums continuously",
        ),
        correctIndex = 2,
        explanation = "Fanning foot pedal exhausts compressed air rapidly, depleting storage tanks faster than compressor can rebuild air.",
        manualCitation = "FMCSA CDL Manual Section 5.4 - Using Air Brakes"
    ),
    Question(
        id = "ab_056",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "How do you check for physical brake lining thickness during a visual pre-trip inspection?",
        options = listOf(
            "Remove the wheel assembly completely and measure brake drum outer diameter with calipers",
            "Tap brake chamber canisters with a tire thumper and listen for loose lining rivets",
            "Apply full parking brake pressure and measure pushrod angle relative to slack adjuster",
            "Check through inspection holes that friction material is not dangerously thin or oiled",
        ),
        correctIndex = 3,
        explanation = "Inspect drum lining or disc pad thickness through chamber/drum inspection slots for wear and contamination.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_057",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "What should you do if brake linings are contaminated with oil or grease?",
        options = listOf(
            "Have the vehicle serviced and contaminated linings replaced or cleaned immediately",
            "Drive vehicle at high speed and brake hard repeatedly to burn off oil residues",
            "Spray starting fluid or brake cleaner directly into inspection slots while idling",
            "Adjust the manual slack adjuster tighter to compensate for slippery friction pads",
        ),
        correctIndex = 0,
        explanation = "Oil or grease on friction linings reduces braking efficiency severely; contaminated linings must be serviced/replaced.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_058",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "In an S-cam brake, what component holds the brake shoes against the S-cam roller when brakes are released?",
        options = listOf(
            "Chamber pushrod clevis pins",
            "Heavy brake shoe return springs",
            "Manual slack adjuster pawls",
            "Camshaft spider anchor bolts",
        ),
        correctIndex = 1,
        explanation = "Return springs pull brake shoes away from drum surface back against S-cam when air pressure is released.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_059",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "What is the primary cause of commercial vehicle brake adjustment changing over time?",
        options = listOf(
            "Thermal expansion of compressed air lines operating under hot summer weather",
            "Accumulation of road salt and dirt particles inside chamber diaphragm vents",
            "Normal friction wear of brake linings and drums through routine daily use",
            "Vibration from diesel engine crankshaft shaking slack adjuster worm gears",
        ),
        correctIndex = 2,
        explanation = "Friction wearing down brake linings and drums increases distance shoes must travel, changing brake adjustment.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_060",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Safe Driving & Emergency Operations",
        questionText = "What is the main advantage of Anti-lock Braking Systems (ABS)?",
        options = listOf(
            "Shortens vehicle stopping distance significantly on every type of road surface",
            "Eliminates the requirement for pre-trip air brake system inspection procedures",
            "Prevents brake fade completely when descending long steep mountain downgrades",
            "Helps driver maintain directional steering control during hard emergency braking",
        ),
        correctIndex = 3,
        explanation = "ABS prevents wheel lockup during hard braking, allowing driver to maintain steering control.",
        manualCitation = "FMCSA CDL Manual Section 5.4 - Using Air Brakes"
    ),
    Question(
        id = "ab_061",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "How do you know if your vehicle is equipped with Anti-lock Brakes (ABS)?",
        options = listOf(
            "A yellow ABS malfunction lamp on instrument panel that lights up on start",
            "A red flashing light on tractor rear mudflap that stays lit continuously",
            "An audible chime that sounds every time vehicle accelerates past 15 MPH",
            "A digital readout showing individual wheel speed sensors on dashboard",
        ),
        correctIndex = 0,
        explanation = "ABS-equipped vehicles feature a yellow dash warning lamp that illuminates briefly when ignition key is turned on.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_062",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Safe Driving & Emergency Operations",
        questionText = "When driving a vehicle equipped with ABS, how should you brake in an emergency situation?",
        options = listOf(
            "Fan the brake pedal rapidly to prevent wheel lockup on slick pavement",
            "Apply firm continuous pedal pressure as needed and steer around hazards",
            "Pull yellow parking brake knob while pumping the service foot treadle",
            "Downshift two gears and apply only the trailer trolley hand control bar",
        ),
        correctIndex = 1,
        explanation = "With ABS, apply firm continuous brake pedal force and steer normally; do not fan or pump brakes.",
        manualCitation = "FMCSA CDL Manual Section 5.4 - Using Air Brakes"
    ),
    Question(
        id = "ab_063",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Safe Driving & Emergency Operations",
        questionText = "If the yellow ABS warning light stays illuminated while driving, what does it mean?",
        options = listOf(
            "All service brakes are disabled completely and spring brakes will lock wheels",
            "Air compressor has stopped pumping and will drop reservoir pressure to zero",
            "ABS feature is inactive, but regular service braking remains fully functional",
            "Tractor protection valve has closed, shutting off trailer air supply lines",
        ),
        correctIndex = 2,
        explanation = "If ABS warning lamp stays on, anti-lock feature is inactive, but regular service brakes still function.",
        manualCitation = "FMCSA CDL Manual Section 5.4 - Using Air Brakes"
    ),
    Question(
        id = "ab_064",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What is the function of the brake chamber diaphragm?",
        options = listOf(
            "A steel return spring that retracts slack adjuster arm during releases",
            "A machined bronze bushing supporting rotating camshaft inside spider plate",
            "A check valve disk preventing compressed air from backflowing to tanks",
            "A flexible rubber disc inside chamber that moves pushrod when air enters",
        ),
        correctIndex = 3,
        explanation = "The diaphragm is a flexible rubber seal inside brake chamber that expands under air pressure to push pushrod out.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_065",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "Which component in an S-cam air brake system connects pushrod to camshaft?",
        options = listOf(
            "Slack adjuster",
            "Treadle valve",
            "Return spring",
            "Relay valve",
        ),
        correctIndex = 0,
        explanation = "The slack adjuster connects chamber pushrod to camshaft, converting pushrod linear motion into camshaft rotation.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_066",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "When checking brake drum condition, what visual flaw warrants out-of-service replacement?",
        options = listOf(
            "Light surface heat checking patterns across inner machined drum contact face",
            "Cracks extending more than half the width of friction area or any break",
            "Minor surface rust forming on exterior unpainted drum cooling rib flanges",
            "Dust accumulation from friction lining wear inside drum inspection windows",
        ),
        correctIndex = 1,
        explanation = "Drums with cracks extending over half the width of friction area, or structural cracks, are unsafe and out-of-service.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_067",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What happens if compressor oil accumulates excessively in air brake storage tanks?",
        options = listOf(
            "Oil increases air pressure in tanks, causing safety relief valve pop-off",
            "Oil prevents the alcohol evaporator from vaporizing alcohol in cold weather",
            "Oil deteriorates rubber seals, valves, and diaphragms throughout system",
            "Oil causes the air compressor governor cut-out pressure to drop to 60 PSI",
        ),
        correctIndex = 2,
        explanation = "Engine oil in air tanks deteriorates rubber diaphragms and valve seals throughout air brake system.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_068",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "Why do air brake systems require more maintenance than hydraulic brake systems?",
        options = listOf(
            "Air brake chambers operate at higher fluid pressures than hydraulic cylinders",
            "Air systems require daily replacement of foundation brake return springs",
            "Air brake disc rotors wear out twice as fast as hydraulic vehicle drums",
            "Air systems have more mechanical linkage parts and air traps moisture and oil",
        ),
        correctIndex = 3,
        explanation = "Compressed air inherently traps water/oil and utilizes external mechanical linkage needing routine checks.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_069",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What is the purpose of air tank drain valves?",
        options = listOf(
            "To drain accumulated moisture and oil sludge from bottom of storage tanks",
            "To bleed excess air pressure from tanks before parking vehicle overnight",
            "To test the accuracy of dashboard supply pressure gauges during pre-trip",
            "To inject lubricating oil into the air compressor cylinder head valves",
        ),
        correctIndex = 0,
        explanation = "Drain valves positioned at bottom of air tanks allow drivers to drain condensed water and oil sludge.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_070",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What are two types of air tank drain valves?",
        options = listOf(
            "Electronic solenoid valves and pneumatic vacuum operated check drain valves",
            "Manual drain valves (turn petcock or pull cable) and Automatic drain valves",
            "High pressure relief drain valves and low pressure bypass circulating valves",
            "Gravity check valves and mechanical spring loaded suction drain petcocks",
        ),
        correctIndex = 1,
        explanation = "Air tank drain valves are either manually operated (petcock or pull cable) or automatic.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_071",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "If an air brake tank features an automatic drain valve, is it also equipped with manual draining capability?",
        options = listOf(
            "No, automatic drain valves completely eliminate the need for manual petcocks",
            "No, manual draining an automatic valve will destroy the internal float seal",
            "Yes, automatic drains usually include manual drains for inspection and testing",
            "Yes, but manual draining is only permitted during annual commercial inspections",
        ),
        correctIndex = 2,
        explanation = "Automatic drains include manual drain capability so drivers can manually purge moisture during inspections.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_072",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Safe Driving & Emergency Operations",
        questionText = "What can happen if water freezes in the air brake lines during cold winter weather?",
        options = listOf(
            "Ice forces the safety relief valve to open and discharge all tank pressure",
            "Ice causes the air compressor governor cut-in pressure to rise to 150 PSI",
            "Ice freezes the engine crankshaft, preventing the vehicle from starting up",
            "Ice blocks air flow through valves, causing complete service brake failure",
        ),
        correctIndex = 3,
        explanation = "Ice in air lines blocks compressed air flow to brake chambers, preventing brake application or release.",
        manualCitation = "FMCSA CDL Manual Section 5.4 - Using Air Brakes"
    ),
    Question(
        id = "ab_073",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "How often should you check alcohol levels in an alcohol evaporator during cold weather?",
        options = listOf(
            "Every day during your daily commercial pre-trip vehicle inspection",
            "Once a month during routine commercial fleet maintenance service",
            "Only when the low air pressure warning buzzer sounds on dashboard",
            "Whenever ambient temperature drops below zero degrees Fahrenheit",
        ),
        correctIndex = 0,
        explanation = "Alcohol level in evaporator must be checked daily during winter pre-trip inspections and refilled as needed.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_074",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What type of alcohol should be used in an alcohol evaporator system?",
        options = listOf(
            "Standard rubbing alcohol purchased at retail pharmacies",
            "Approved methyl alcohol specified by vehicle manufacturer",
            "Ethylene glycol antifreeze mixed with distilled water",
            "Automotive windshield washer fluid containing ammonia",
        ),
        correctIndex = 1,
        explanation = "Use manufacturer-approved methyl alcohol to prevent damage to internal rubber seals and valves.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_075",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "If you fan the brake pedal rapidly while engine is idling, what will happen to supply pressure gauge?",
        options = listOf(
            "Supply tank pressure reading will rise rapidly toward safety relief limit",
            "Application gauge will lock at 120 PSI until engine RPM is increased",
            "Supply tank pressure reading will drop rapidly with each pedal pump",
            "Air compressor governor will permanently trip into bypass exhaust mode",
        ),
        correctIndex = 2,
        explanation = "Fanning foot pedal consumes stored compressed air faster than engine idling compressor can rebuild pressure.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_076",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What powers the air compressor in most commercial motor vehicles?",
        options = listOf(
            "An independent 12-volt heavy duty electric motor mounted to chassis",
            "Exhaust gas pressure spinning an auxiliary turbo-compressor impeller",
            "Hydraulic fluid flow pumped from the commercial power steering system",
            "Direct connection to vehicle diesel engine through gears or drive belts",
        ),
        correctIndex = 3,
        explanation = "Air compressors are driven directly by vehicle engine via timing gears or belt drive.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_077",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "Where does the air compressor get its lubrication supply?",
        options = listOf(
            "Directly from engine oil lubrication system or its own internal sump",
            "From pressurized air tank moisture collected in bottom of wet tank",
            "From synthetic brake fluid circulating through the foundation lines",
            "From fuel injection pump returning diesel fuel to the primary tank",
        ),
        correctIndex = 0,
        explanation = "Air compressors are lubricated by engine oil system or have self-contained oil supply.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_078",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What is the primary danger of a clogged air compressor intake filter?",
        options = listOf(
            "Excessive air enters tanks, forcing safety relief valve to open often",
            "Compressor cannot draw enough air, slowing down tank pressure recovery",
            "Compressor piston rings overheat and weld to cylinder wall castings",
            "Low pressure warning buzzer sounds continuously regardless of tank PSI",
        ),
        correctIndex = 1,
        explanation = "A dirty or clogged air filter starves compressor of air, preventing proper pressure buildup rates.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_079",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Sub-Braking Systems",
        questionText = "What component allows driver to apply trailer service brakes independently of tractor service brakes?",
        options = listOf(
            "Tractor protection valve push-pull control knob on the dashboard",
            "Manual slack adjuster adjusting bolt located on the brake chamber",
            "Trailer hand valve (also known as trolley valve or Johnson bar)",
            "Quick release valve mounted on the front steering axle crossmember",
        ),
        correctIndex = 2,
        explanation = "Trailer hand valve (trolley bar) controls trailer service brakes independently from tractor brakes.",
        manualCitation = "FMCSA CDL Manual Section 5.2 - Dual Air & Sub-Brake Systems"
    ),
    Question(
        id = "ab_080",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Sub-Braking Systems",
        questionText = "Should the trailer hand valve be used for parking a combination vehicle?",
        options = listOf(
            "Yes, using the hand valve preserves mechanical spring brake tension",
            "Yes, hand valves provide stronger clamping force than parking springs",
            "No, hand valves must only be used when parking on steep uphill grades",
            "No, never use trailer hand valve for parking because air can leak off",
        ),
        correctIndex = 3,
        explanation = "Never use hand valve for parking; if air pressure leaks down, trailer service brakes will release.",
        manualCitation = "FMCSA CDL Manual Section 5.2 - Dual Air & Sub-Brake Systems"
    ),
    Question(
        id = "ab_081",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Safe Driving & Emergency Operations",
        questionText = "Should the trailer hand valve be used to correct a jackknife skid while driving?",
        options = listOf(
            "No, applying trailer hand valve locks trailer wheels, worsening the skid",
            "Yes, hand valve applies trailer brakes to straighten combination rig",
            "Yes, hand valve prevents the tractor drive wheels from losing traction",
            "No, hand valve should only be applied if vehicle speed exceeds 55 MPH",
        ),
        correctIndex = 0,
        explanation = "Never use hand valve to stop skid; locking trailer wheels causes or worsens trailer jackknife skid.",
        manualCitation = "FMCSA CDL Manual Section 5.4 - Using Air Brakes"
    ),
    Question(
        id = "ab_082",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What are gladhands on a combination vehicle?",
        options = listOf(
            "Mounting brackets securing brake chambers to the heavy steering axle beam",
            "Coupling devices connecting tractor service and emergency lines to trailer",
            "Adjustable locking levers controlling manual slack adjuster pushrod travel",
            "Safety chains linking the tractor fifth wheel frame to trailer landing gear",
        ),
        correctIndex = 1,
        explanation = "Gladhands are interlocking pneumatic couplers connecting tractor air hoses to trailer air system.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_083",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What colors are standard for service and emergency air lines on combination vehicles?",
        options = listOf(
            "Yellow for Service line (control) and Green for Emergency line (supply)",
            "Red for Service line (control) and Blue for Emergency line (supply)",
            "Blue for Service line (control) and Red for Emergency line (supply)",
            "Black for Service line (control) and White for Emergency line (supply)",
        ),
        correctIndex = 2,
        explanation = "Blue is universal color code for service line (control); Red is color code for emergency line (supply).",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_084",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "When connecting gladhands, what should you inspect on rubber seals?",
        options = listOf(
            "Color of rubber seals and thickness of silicone lubricant applied to rims",
            "Brand name stamped on rubber face and manufacturing date code validity",
            "Diameter of rubber grommet hole matching the electric seven-way cord pin",
            "Cleanliness, absence of cracks or rot, and proper seating in coupler grooves",
        ),
        correctIndex = 3,
        explanation = "Gladhand rubber grommets must be clean, free of cracks or rot, and properly seated to prevent leaks.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_085",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "What happens if you cross (reverse) service and emergency gladhand connections when coupling a trailer?",
        options = listOf(
            "Trailer emergency spring brakes will not release, or service brakes will fail",
            "Air compressor will immediately shut down due to excessive backpressure build",
            "Primary air reservoir tank will purge all stored air out through safety valve",
            "Tractor steer axle brakes will lock permanently in full application position",
        ),
        correctIndex = 0,
        explanation = "Crossing gladhands prevents proper air flow; trailer spring brakes will not release or service brakes will fail.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_086",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "How should gladhands be stored when uncoupled from a trailer?",
        options = listOf(
            "Hanging loose below trailer apron where road vibration cleans the seals",
            "Coupled to dummy couplers or sealed with dead-end plugs to exclude dirt",
            "Tied tightly together with wire ties against the tractor catwalk grating",
            "Wrapped in shop rags and stored inside the tractor side battery compartment",
        ),
        correctIndex = 1,
        explanation = "Secure gladhands to dummy couplers or dead-end holders to protect seals from dirt, water, and debris.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_087",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What is the function of the shut-off valves (cut-out cocks) located at rear of trailers?",
        options = listOf(
            "To adjust service brake application timing between tractor and lead trailer",
            "To dump air pressure from trailer air suspension bags during cargo loading",
            "To open or close air lines passing through to towing dollies or rear trailers",
            "To bleed water condensation automatically from the trailer emergency tank",
        ),
        correctIndex = 2,
        explanation = "Shut-off valves at rear of trailers control air flow through air lines servicing towing dolly or second trailer.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_088",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "What position must rear trailer shut-off valves be in on the LAST trailer of a combination vehicle?",
        options = listOf(
            "In the OPEN position to maintain continuous air circulation in lines",
            "Halfway open to allow moisture drainage while driving on highway",
            "In the OPEN position with dummy couplers attached over the valve ports",
            "In the CLOSED position to prevent compressed air escaping to atmosphere",
        ),
        correctIndex = 3,
        explanation = "Rear shut-off valves on last trailer must be CLOSED so air does not escape into open atmosphere.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_089",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "How do you test that air flows to rear of trailer during pre-trip inspection?",
        options = listOf(
            "Open rear trailer shut-off valve, listen for escaping air, then close valve",
            "Check that tractor low pressure buzzer shuts off when red knob is pulled",
            "Press service foot pedal and verify that trailer stop lights illuminate",
            "Measure air compressor recovery time with trailer emergency line vented",
        ),
        correctIndex = 0,
        explanation = "Test air flow by charging trailer system, opening rear shut-off valve, listening for escaping air, and closing valve.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_090",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Safe Driving & Emergency Operations",
        questionText = "What happens if service line disconnects while driving combination vehicle?",
        options = listOf(
            "Tractor engine stalls immediately and power steering hydraulic assist is lost",
            "Air escapes when brakes are applied, dropping pressure until emergency stops engage",
            "Trailer emergency spring brakes lock instantly without touching brake pedal",
            "Tractor service brakes lock up while trailer wheels continue rolling freely",
        ),
        correctIndex = 1,
        explanation = "If service line breaks, air escapes when brakes are applied, causing emergency system activation as pressure drops.",
        manualCitation = "FMCSA CDL Manual Section 5.4 - Using Air Brakes"
    ),
    Question(
        id = "ab_091",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Safe Driving & Emergency Operations",
        questionText = "What happens if emergency line disconnects while driving combination vehicle?",
        options = listOf(
            "Tractor engine stalls immediately and power steering hydraulic assist fails",
            "Tractor steer axle brakes apply with full pressure and lock front steering",
            "Trailer air escapes, and trailer emergency spring brakes apply automatically",
            "Air compressor governor shuts down and vents all primary storage reservoirs",
        ),
        correctIndex = 2,
        explanation = "Loss of emergency line pressure causes tractor protection valve to close and trailer emergency spring brakes to set.",
        manualCitation = "FMCSA CDL Manual Section 5.4 - Using Air Brakes"
    ),
    Question(
        id = "ab_092",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "How do you test trailer service brakes during pre-trip inspection?",
        options = listOf(
            "Set parking brakes and rev engine to 1500 RPM while watching pressure gauges",
            "Chock trailer wheels and observe pushrod travel distance with engine idling",
            "Disconnect service gladhand coupler and listen for audible air discharge",
            "Pull forward at 5 MPH and apply trailer hand valve to test holding power",
        ),
        correctIndex = 3,
        explanation = "Test trailer service brakes by rolling forward slowly (~5 MPH) and pulling trailer hand valve to check stopping power.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_093",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "How do you test trailer emergency brakes during pre-trip inspection?",
        options = listOf(
            "Charge trailer, pull red knob OUT, and gently pull forward in low gear",
            "Hold service brake pedal down for two minutes with the engine idling",
            "Apply tractor parking brake, release trailer brakes, and rev the engine",
            "Open the trailer rear drain petcock while the vehicle rolls backward",
        ),
        correctIndex = 0,
        explanation = "Charge trailer, pull red trailer supply valve OUT to set emergency brakes, and pull forward gently to test holding power.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_094",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "Why is brake adjustment more critical on air brake vehicles than hydraulic vehicles?",
        options = listOf(
            "Air lines will burst under pressure if pushrods travel beyond manufacturer limits",
            "Pushrods have limited stroke; if out of adjustment, braking force drops severely",
            "Air compressors will overheat rapidly trying to supply excessive chamber volume",
            "Brake drums will crack immediately when cold air enters the chamber diaphragms",
        ),
        correctIndex = 1,
        explanation = "Chamber pushrods have strict maximum stroke limits. Beyond that limit, air pressure cannot push shoes hard against drum.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_095",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "Which component is designed to release air pressure quickly from brake chambers when pedal is released?",
        options = listOf(
            "Tractor protection valve",
            "Automatic drain valve",
            "Quick release valve",
            "One-way check valve",
        ),
        correctIndex = 2,
        explanation = "Quick release valves allow compressed air to exhaust directly out of nearby chambers rather than returning back through pedal valve.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_096",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What is the function of a relay valve on long wheelbase vehicles and trailers?",
        options = listOf(
            "Regulates air compressor governor cut-in pressure during long downhill descents",
            "Dumps moisture and oil sludge automatically from primary and secondary reservoirs",
            "Prevents trailer emergency spring brakes from applying during hard stopping events",
            "Connects main reservoir to rear chambers to speed up application and release times",
        ),
        correctIndex = 3,
        explanation = "Relay valves use long-distance control air signals to direct main reservoir air straight into rear/trailer brake chambers quickly.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_097",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "If your air compressor develops a leak and loses air supply, what keeps air inside primary and secondary tanks?",
        options = listOf(
            "One-way check valves at reservoir tank inlets",
            "Quick release valves mounted at each chamber",
            "Pressure relief valves set at 150 PSI limit",
            "Automatic drain valves on bottom of reservoirs",
        ),
        correctIndex = 0,
        explanation = "One-way check valves at air tank inlets prevent stored air from leaking back through ruptured compressor lines.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_098",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "What should you check regarding air hoses during a pre-trip under-vehicle inspection?",
        options = listOf(
            "Verify that hose inner diameter matches the engine fuel delivery line",
            "Inspect hoses for cuts, abrasions, leaks, bulges, or frame rubbing wear",
            "Ensure hoses are painted the exact same color as vehicle chassis rails",
            "Check that all air hoses are wrapped with high temperature exhaust tape",
        ),
        correctIndex = 1,
        explanation = "Check air hoses and lines for leaks, cuts, cracks, bulges, and rubbing wear against chassis frame parts.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_099",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Safe Driving & Emergency Operations",
        questionText = "What is the result of applying service brakes repeatedly without allowing air compressor time to rebuild pressure?",
        options = listOf(
            "Compressor governor cut-out pressure increases automatically to protect valves",
            "Treadle foot pedal locks down permanently and prevents further brake activation",
            "Pressure drops until low air warning sounds, and eventually spring brakes engage",
            "Brake chamber diaphragms overheat and rupture from high velocity air currents",
        ),
        correctIndex = 2,
        explanation = "Repeated brake applications deplete tank air; continuous pumping leads to low air alarms and eventual spring brake lockup.",
        manualCitation = "FMCSA CDL Manual Section 5.4 - Using Air Brakes"
    ),
    Question(
        id = "ab_100",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Sub-Braking Systems",
        questionText = "Which axle's brakes are typically applied by primary air system in dual air system setup?",
        options = listOf(
            "The front steering axle brakes",
            "The trailer auxiliary dollies",
            "The transmission parking drum",
            "The rear drive axle assemblies",
        ),
        correctIndex = 3,
        explanation = "In most dual air systems, primary circuit operates rear drive axle service brakes.",
        manualCitation = "FMCSA CDL Manual Section 5.2 - Dual Air & Sub-Brake Systems"
    ),
    Question(
        id = "ab_101",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Sub-Braking Systems",
        questionText = "Which axle's brakes are typically operated by secondary air system in dual air setup?",
        options = listOf(
            "The front steering axle brakes",
            "The rear drive axle assemblies",
            "The trailer emergency lines",
            "The cab air suspension bags",
        ),
        correctIndex = 0,
        explanation = "In dual air systems, secondary air circuit typically operates front steering axle service brakes.",
        manualCitation = "FMCSA CDL Manual Section 5.2 - Dual Air & Sub-Brake Systems"
    ),
    Question(
        id = "ab_102",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Sub-Braking Systems",
        questionText = "If primary system loses air in dual air setup, what provides stopping capability for vehicle?",
        options = listOf(
            "Vehicle loses all braking immediately and relies solely on transmission gears",
            "Secondary circuit continues to operate front steering brakes safely to a stop",
            "Tractor protection valve opens completely to vent all trailer air pressure",
            "Air compressor clutch disengages automatically to prevent engine stall out",
        ),
        correctIndex = 1,
        explanation = "If one dual system loses pressure, remaining intact system provides emergency stopping capability.",
        manualCitation = "FMCSA CDL Manual Section 5.2 - Dual Air & Sub-Brake Systems"
    ),
    Question(
        id = "ab_103",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What is the function of the air dryer in an air brake system?",
        options = listOf(
            "Cools down hot engine oil before it circulates into the compressor crankcase",
            "Maintains constant 120 PSI application pressure to front steering brake shoes",
            "Removes water moisture and oil vapor before air enters the storage reservoirs",
            "Injects alcohol vapor continuously into primary and secondary storage tanks",
        ),
        correctIndex = 2,
        explanation = "The air dryer collects and removes moisture and oil desiccant contaminants before air reaches storage reservoirs.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_104",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What happens during air dryer purge cycle when governor cut-out pressure is reached?",
        options = listOf(
            "Safety relief valve blows off 150 PSI air through the cab floorboard vents",
            "Low pressure warning buzzer sounds for five seconds on the instrument panel",
            "Tractor protection valve pops out to disconnect trailer service air supply",
            "Air dryer purges collected water, oil, and air with an audible exhaust sound",
        ),
        correctIndex = 3,
        explanation = "At governor cut-out, air dryer purges trapped water and oil to open air with a loud 'pssshh' exhaust sound.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_105",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What is S-cam brake stroke?",
        options = listOf(
            "The distance pushrod travels out from chamber when service brakes are applied",
            "The total thickness of friction lining remaining on the curved brake shoes",
            "The angle between the slack adjuster arm and foundation camshaft bracket",
            "The diameter of the machined internal friction surface inside the drum",
        ),
        correctIndex = 0,
        explanation = "Brake stroke is exact distance chamber pushrod moves outward when air pressure applies brakes.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_106",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Safe Driving & Emergency Operations",
        questionText = "Why is proper adjustment of S-cam brakes vital on long downgrades?",
        options = listOf(
            "Hot drums contract tightly around shoes, locking wheels into continuous skid",
            "Hot drums expand, increasing stroke needed; if excessive, brakes fail completely",
            "Heat destroys chamber diaphragms, causing immediate total loss of air supply",
            "Excessive temperature causes the slack adjuster worm gear to reverse rotation",
        ),
        correctIndex = 1,
        explanation = "Hot drums expand, increasing stroke needed. If pushrod stroke is already excessive, chamber bottoms out causing total brake failure.",
        manualCitation = "FMCSA CDL Manual Section 5.4 - Using Air Brakes"
    ),
    Question(
        id = "ab_107",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What is purpose of parking brake spring release tool (caging bolt)?",
        options = listOf(
            "Used to adjust manual slack adjuster free play during routine pre-trip tests",
            "Used to lock parking brakes into full engagement when parked on steep inclines",
            "Used to mechanically compress spring inside chamber to release brakes for towing",
            "Used to calibrate low air pressure warning buzzer sensors on the dashboard",
        ),
        correctIndex = 2,
        explanation = "Caging bolts compress heavy parking springs inside chambers so disabled vehicles with no air pressure can be safely towed.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_108",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Safe Driving & Emergency Operations",
        questionText = "Should you drive a vehicle on public roads with spring brakes caged?",
        options = listOf(
            "Yes, caged springs are required whenever operating combination vehicles empty",
            "Yes, but you must restrict vehicle speed to 35 MPH and use emergency flashers",
            "No, unless driving on designated interstate highways with oversized cargo permits",
            "No, never drive on public roads with caged springs because you have no parking brakes",
        ),
        correctIndex = 3,
        explanation = "Never drive with caged springs on public roads; caged springs eliminate emergency and parking braking.",
        manualCitation = "FMCSA CDL Manual Section 5.4 - Using Air Brakes"
    ),
    Question(
        id = "ab_109",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What type of brake system uses wedge mechanism driven by chamber pushrod to force shoes outward?",
        options = listOf(
            "Wedge brake drum systems",
            "S-cam drum brake systems",
            "Air disc brake assemblies",
            "Hydro-boost drum systems",
        ),
        correctIndex = 0,
        explanation = "Wedge brakes force a wedge directly between brake shoes, pushing shoes directly against inside drum surface.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_110",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "How do you perform static (engine off, brakes released) leak test on single vehicle?",
        options = listOf(
            "Idle engine, apply parking brakes, and listen for audible leaks around chambers",
            "Shut engine OFF, release parking brakes, and monitor gauge for 1 minute (max 2 PSI)",
            "Hold foot pedal down with engine running and check compressor recovery time rate",
            "Open tank drain valves completely and verify that safety relief valve closes",
        ),
        correctIndex = 1,
        explanation = "Fully charge system, turn off engine, release parking brakes, and monitor initial static leakage rate over 1 minute.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_111",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "How do you perform applied (engine off, pedal depressed) leak test on single vehicle?",
        options = listOf(
            "Engine idling, parking brakes set, apply pedal firmly and watch application gauge",
            "Accelerate to 15 MPH, apply brakes hard, and measure total stopping distance",
            "Engine OFF, brakes released, hold pedal down for 1 minute and check gauge (max 3 PSI)",
            "Pump pedal rapidly with key ON and verify that low air warning alarm activates",
        ),
        correctIndex = 2,
        explanation = "With engine off and brakes released, hold foot pedal firmly down for 1 minute and verify gauge pressure drop.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_112",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "When inspecting air brake lines, what condition requires immediate vehicle out-of-service repair?",
        options = listOf(
            "Light surface dirt or road dust accumulation on exterior rubber hose jacket",
            "Minor surface paint peeling on steel reservoir air tank mounting brackets",
            "Manufacturer date stamp on flexible air hose partially faded from weather",
            "Audible air leak or hose severely chafed through outer reinforcement fabric layer",
        ),
        correctIndex = 3,
        explanation = "Audible air leaks or hoses chafed into structural fabric layer pose high failure risks and mandate out-of-service repair.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_113",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What is purpose of stop light switch in air brake system?",
        options = listOf(
            "An air-operated electric switch that turns on rear brake lights during stops",
            "A dashboard indicator light warning the driver that service brakes are dragging",
            "A safety solenoid that cuts engine throttle power whenever foot pedal is applied",
            "A pressure sensor that activates the emergency trailer spring brake chambers",
        ),
        correctIndex = 0,
        explanation = "The stop light switch is an air-operated electric switch illuminating rear brake lights when air pressure enters service lines.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_114",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Safe Driving & Emergency Operations",
        questionText = "What should driver do before leaving vehicle unattended?",
        options = listOf(
            "Leave engine idling in neutral with trailer trolley hand valve set in position",
            "Set parking brake, turn off engine, chock wheels if on incline, and take key",
            "Put transmission in highest road gear and leave parking brake control released",
            "Chock front wheels, open wet tank drain valve, and leave ignition key in switch",
        ),
        correctIndex = 1,
        explanation = "Always set parking brakes, shut off engine, remove key, and chock wheels whenever leaving commercial vehicle.",
        manualCitation = "FMCSA CDL Manual Section 5.4 - Using Air Brakes"
    ),
    Question(
        id = "ab_115",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "Why do some air brake systems feature air tank heaters?",
        options = listOf(
            "To warm up compressed air before it enters cold foundation brake chambers",
            "To increase storage reservoir pressure automatically during winter starts",
            "To prevent automatic drain valve mechanisms and collected water from freezing",
            "To keep diesel fuel inside nearby fuel tanks from gelling in sub-zero weather",
        ),
        correctIndex = 2,
        explanation = "Electrical air tank heaters keep automatic drain valves and tank moisture ports from freezing solid in winter.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_116",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "If brake pedal feels spongy or soft when depressed on air brake truck, what does this indicate?",
        options = listOf(
            "Air storage tanks contain excessive pressure above governor cut-out limit",
            "One-way check valve on supply reservoir has stuck in the closed position",
            "Air compressor piston rings have worn out and stopped circulating oil",
            "Air brake treadle valve or brake mechanical linkage is binding or defective",
        ),
        correctIndex = 3,
        explanation = "Mechanical binding or defective internal treadle valves cause spongy or abnormal resistance in air foot pedals.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
    Question(
        id = "ab_117",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "What is purpose of foundation brakes on commercial motor vehicles?",
        options = listOf(
            "Mechanical wheel brake assemblies (drums/shoes or discs/pads) that stop vehicle",
            "Pneumatic control valves located inside the tractor cab dashboard console",
            "Steel air storage reservoirs mounted securely along vehicle chassis frame",
            "High pressure air lines linking engine compressor to primary supply tank",
        ),
        correctIndex = 0,
        explanation = "Foundation brakes are the actual physical brake hardware assemblies located inside wheel hubs.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_118",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Components & Mechanics",
        questionText = "Which of following is MOST common type of foundation brake used on heavy commercial trucks?",
        options = listOf(
            "Air disc caliper system",
            "S-cam drum brake system",
            "Wedge drum brake system",
            "Hydraulic assist system",
        ),
        correctIndex = 1,
        explanation = "S-cam drum brakes are by far the most prevalent type of foundation brake used on commercial heavy vehicles.",
        manualCitation = "FMCSA CDL Manual Section 5.1 - Components of Air Brake System"
    ),
    Question(
        id = "ab_119",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Safe Driving & Emergency Operations",
        questionText = "What should driver do if vehicle starts to skid during heavy braking?",
        options = listOf(
            "Press brake pedal down harder and pull the yellow parking brake control knob",
            "Shift transmission into neutral and apply the trailer trolley hand valve hard",
            "Release service brakes immediately to allow wheels to roll and regain steering",
            "Pump foot pedal as fast as possible while steering against direction of skid",
        ),
        correctIndex = 2,
        explanation = "If wheels lock and skid, immediately release service brakes so wheels can roll and regain steering control.",
        manualCitation = "FMCSA CDL Manual Section 5.4 - Using Air Brakes"
    ),
    Question(
        id = "ab_120",
        category = CDLCategory.AIR_BRAKES,
        subtopic = "Inspection & Leak Tests",
        questionText = "Why is it important to complete full CDL air brake pre-trip inspection before every trip?",
        options = listOf(
            "Verifies that vehicle diesel engine achieves maximum fuel economy on highway",
            "Guarantees that trailer cargo weight is distributed evenly between all axles",
            "Allows driver to bypass mandatory state commercial highway weigh stations",
            "Ensures all safety controls, pressure limits, leak rates, and parts are safe",
        ),
        correctIndex = 3,
        explanation = "Pre-trip inspections guarantee that air pressures, leakage rates, warning devices, and mechanical brakes are safe.",
        manualCitation = "FMCSA CDL Manual Section 5.3 - Inspecting Air Brake Systems"
    ),
)
