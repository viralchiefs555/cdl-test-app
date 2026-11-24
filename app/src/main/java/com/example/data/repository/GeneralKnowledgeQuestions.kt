package com.example.data.repository

import com.example.data.models.CDLCategory
import com.example.data.models.Question

/**
 * Top 50 curated questions for the CDL General Knowledge Exam.
 * All multiple-choice options have been balanced to consistent lengths
 * with randomized answer positions to prevent length cueing or formatting bias.
 */
val generalKnowledgeQuestions: List<Question> = listOf(
    // ================= PHASE 1: VEHICLE INSPECTION & PRE-TRIP =================
    Question(
        id = "gk_01",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 1: Vehicle Inspection & Pre-Trip",
        questionText = "What is the most important reason for doing a pre-trip inspection?",
        options = listOf(
            "Safety for yourself, your cargo, and other highway users.",
            "Protecting company equipment from avoidable roadside wear.",
            "Preventing costly commercial citations during DOT audits.",
            "Complying with mandatory state dispatch logging guidelines."
        ),
        correctIndex = 0,
        explanation = "Safety is the most important reason you inspect your vehicle: safety for yourself and for other road users. Federal and state laws also require it.",
        manualCitation = "FMCSA CDL Manual Section 2.1 - Vehicle Inspection"
    ),
    Question(
        id = "gk_02",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 1: Vehicle Inspection & Pre-Trip",
        questionText = "What does rust around wheel nuts usually indicate?",
        options = listOf(
            "The wheel rim was manufactured from inferior cast alloy.",
            "The tire has been operated under excessive tire pressure.",
            "The brake drum lining has worn past federal safety limits.",
            "The nuts are loose and need immediate torque verification."
        ),
        correctIndex = 3,
        explanation = "Rust around wheel nuts usually means they are loose—check their tightness immediately. A shiny streak around a nut also indicates movement.",
        manualCitation = "FMCSA CDL Manual Section 2.1.2 - Wheel and Rim Flaws"
    ),
    Question(
        id = "gk_03",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 1: Vehicle Inspection & Pre-Trip",
        questionText = "What is the minimum legal tire tread depth for front (steering) tires?",
        options = listOf(
            "2/32 of an inch in every major groove across the tread.",
            "3/32 of an inch in the center groove of both front tires.",
            "4/32 of an inch in every major groove across the tread.",
            "6/32 of an inch measured at the shallowest outer groove."
        ),
        correctIndex = 2,
        explanation = "You need at least 4/32-inch tread depth in every major groove on front steering tires. Fabric or cord must never show through.",
        manualCitation = "FMCSA CDL Manual Section 2.1.2 - Tire Problems"
    ),
    Question(
        id = "gk_04",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 1: Vehicle Inspection & Pre-Trip",
        questionText = "What is the minimum legal tire tread depth for other (non-steering) tires?",
        options = listOf(
            "4/32 of an inch in every major tread groove.",
            "1/32 of an inch across the center tread area.",
            "3/32 of an inch measured at the tire shoulder.",
            "2/32 of an inch in every major tread groove."
        ),
        correctIndex = 3,
        explanation = "You need at least 2/32-inch tread depth on all other non-steering tires in every major groove.",
        manualCitation = "FMCSA CDL Manual Section 2.1.2 - Tire Problems"
    ),
    Question(
        id = "gk_05",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 1: Vehicle Inspection & Pre-Trip",
        questionText = "Which of the following components are part of the steering system?",
        options = listOf(
            "Tie rod, spindle, and pitman arm.",
            "Leaf spring, torque rod, and shackle.",
            "Slack adjuster, brake chamber, and rod.",
            "Radius rod, torsion bar, and bushing."
        ),
        correctIndex = 0,
        explanation = "Key components of the commercial steering system include the steering wheel, steering shaft, gearbox, pitman arm, drag link, steering knuckle, spindle, and tie rod.",
        manualCitation = "FMCSA CDL Manual Section 2.1.2 - Steering System Defects"
    ),
    Question(
        id = "gk_06",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 1: Vehicle Inspection & Pre-Trip",
        questionText = "What should you check wheel bearing seals for during an inspection?",
        options = listOf(
            "Excessive internal pressure buildup.",
            "Noticeable fluid leaks or seepage.",
            "Proper electrical hub grounding.",
            "Abnormal rust on the outer cap."
        ),
        correctIndex = 1,
        explanation = "Wheel bearing and hub oil seals must be inspected for leaks. A leaking seal can cause bearing seizure, wheel separation, or fire.",
        manualCitation = "FMCSA CDL Manual Section 2.1.5 - Wheel Inspection"
    ),
    Question(
        id = "gk_07",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 1: Vehicle Inspection & Pre-Trip",
        questionText = "When checking the cooling system of a pressurized engine, what should you do?",
        options = listOf(
            "Remove the cap slowly while idling to inspect coolant circulation.",
            "Pour cold distilled water into the reservoir while engine is hot.",
            "Pressurize the surge tank with shop air to test pressure relief.",
            "Wait for the system to cool down before attempting to open the cap."
        ),
        correctIndex = 3,
        explanation = "Never remove the radiator cap or coolant reservoir cap on a pressurized cooling system while it is hot. Scalding coolant and steam can cause severe burns.",
        manualCitation = "FMCSA CDL Manual Section 2.1.5 - Engine Compartment"
    ),

    // ================= PHASE 2: BASIC CONTROL & VEHICLE HANDLING =================
    Question(
        id = "gk_08",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 2: Basic Control & Vehicle Handling",
        questionText = "How far ahead should you look while driving at highway speeds?",
        options = listOf(
            "4 to 6 seconds ahead (roughly half a city block).",
            "8 to 10 seconds ahead (about one eighth of a mile).",
            "20 to 25 seconds ahead (over half a mile distance).",
            "12 to 15 seconds ahead (about one quarter of a mile)."
        ),
        correctIndex = 3,
        explanation = "Good drivers look at least 12 to 15 seconds ahead. That equates to about a quarter of a mile ahead at highway speeds, or about one block ahead in the city.",
        manualCitation = "FMCSA CDL Manual Section 2.4.1 - Seeing Ahead"
    ),
    Question(
        id = "gk_09",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 2: Basic Control & Vehicle Handling",
        questionText = "What is the best way to check your following distance?",
        options = listOf(
            "Watch the vehicle ahead pass a landmark, then count the seconds until you pass it.",
            "Measure the pavement dashed stripes between your front bumper and their rear bumper.",
            "Use your trip odometer to track mile markers against traffic ahead on the highway.",
            "Check your rearview mirrors to calculate the distance between approaching vehicles."
        ),
        correctIndex = 0,
        explanation = "Pick a clear landmark ahead (shadow, signpost, or bridge). When the vehicle in front passes it, count 'one-thousand-and-one, one-thousand-and-two...' until you reach the same spot.",
        manualCitation = "FMCSA CDL Manual Section 2.7.1 - Following Distance"
    ),
    Question(
        id = "gk_10",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 2: Basic Control & Vehicle Handling",
        questionText = "What is the rule of thumb for following distance under 40 mph?",
        options = listOf(
            "One second for every 10 feet of vehicle length.",
            "Two seconds for every 10 feet of vehicle length.",
            "Three seconds total regardless of truck length.",
            "One second per axle configuration on the truck."
        ),
        correctIndex = 0,
        explanation = "The standard rule: allow 1 second for every 10 feet of vehicle length when driving under 40 mph. For speeds over 40 mph, add an additional 1 second.",
        manualCitation = "FMCSA CDL Manual Section 2.7.1 - Following Distance Formula"
    ),
    Question(
        id = "gk_11",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 2: Basic Control & Vehicle Handling",
        questionText = "What is \"hydroplaning\"?",
        options = listOf(
            "When cold rain rapidly contracts hot brake drums, reducing friction.",
            "When steering fluid aerates during heavy rain, stiffening the wheel.",
            "When tires lose contact with the road and ride on a film of water.",
            "When air brake lines freeze from moisture, causing drive-wheel lock."
        ),
        correctIndex = 2,
        explanation = "Hydroplaning occurs when water builds up under tires faster than tread grooves can channel it away. The tires float on a thin film of water with zero traction.",
        manualCitation = "FMCSA CDL Manual Section 2.6.2 - Hydroplaning"
    ),
    Question(
        id = "gk_12",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 2: Basic Control & Vehicle Handling",
        questionText = "What should you do first if your vehicle begins to hydroplane?",
        options = listOf(
            "Apply firm, steady service brake pressure to stop immediately.",
            "Release the accelerator and steer straight without hard braking.",
            "Counter-steer sharply back and forth to locate dry pavement.",
            "Downshift two gears and pull the trailer emergency air valve."
        ),
        correctIndex = 1,
        explanation = "If you start to hydroplane, do NOT brake hard. Release the accelerator, push in the clutch, and keep wheels pointed straight until tires regain contact.",
        manualCitation = "FMCSA CDL Manual Section 2.6.2 - Responding to Hydroplaning"
    ),
    Question(
        id = "gk_13",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 2: Basic Control & Vehicle Handling",
        questionText = "What is the proper action to take if you find yourself driving through thick fog?",
        options = listOf(
            "Turn on high beams and drive on the road shoulder at reduced speed.",
            "Follow closely behind another truck's taillights for guidance.",
            "Slow down, use low beams, and pull off into a safe area if needed.",
            "Maintain highway speed with four-way emergency flashers active."
        ),
        correctIndex = 2,
        explanation = "Best advice for thick fog: do not drive. If you must drive, slow down, use low beams and fog lights, never use high beams (which reflect glare back), and pull off into a safe rest area.",
        manualCitation = "FMCSA CDL Manual Section 2.13 - Driving in Fog"
    ),
    Question(
        id = "gk_14",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 2: Basic Control & Vehicle Handling",
        questionText = "When driving at night, why should you adjust your vehicle's speed?",
        options = listOf(
            "To keep engine operating temperature at an optimal efficiency level.",
            "To prevent tire pressure from dropping due to cold night pavement.",
            "To ensure you can come to a full stop within your headlight vision.",
            "To stay synchronized with the flow of passenger car traffic around."
        ),
        correctIndex = 2,
        explanation = "Never out-run your headlights. You must drive slow enough that your total stopping distance is less than the distance your headlights illuminate ahead.",
        manualCitation = "FMCSA CDL Manual Section 2.11 - Driving at Night"
    ),
    Question(
        id = "gk_15",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 2: Basic Control & Vehicle Handling",
        questionText = "When must you dim your high-beam headlights when approaching or following another vehicle?",
        options = listOf(
            "Within 1,000 feet of an oncoming vehicle and 500 feet of one you follow.",
            "Within 300 feet of an oncoming vehicle and 100 feet of one you follow.",
            "Within 500 feet of an oncoming vehicle and 200 feet of one you follow.",
            "Within 400 feet of an oncoming vehicle and 150 feet of one you follow."
        ),
        correctIndex = 2,
        explanation = "Dim your high beams within 500 feet of an oncoming vehicle and within 200 feet when following another vehicle from behind.",
        manualCitation = "FMCSA CDL Manual Section 2.11.3 - Headlight Dimming"
    ),
    Question(
        id = "gk_16",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 2: Basic Control & Vehicle Handling",
        questionText = "What is the correct term for a long vehicle's tendency to swing wide on turns?",
        options = listOf(
            "Jackknifing.",
            "Off-tracking.",
            "Oversteering.",
            "Hydroplaning."
        ),
        correctIndex = 1,
        explanation = "When a vehicle rounds a curve or corner, rear wheels follow a shorter path than front wheels. This is called off-tracking or 'cheating.' Longer vehicles off-track more.",
        manualCitation = "FMCSA CDL Manual Section 2.7.6 - Off-Tracking"
    ),
    Question(
        id = "gk_17",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 2: Basic Control & Vehicle Handling",
        questionText = "What should you do if you are being tailgated?",
        options = listOf(
            "Tap your brakes repeatedly to send a warning flash to the driver.",
            "Accelerate briskly to establish a wider safety cushion behind you.",
            "Activate rear halogen work lamps to discourage them from drafting.",
            "Increase following distance ahead and encourage them to pass safely."
        ),
        correctIndex = 3,
        explanation = "Do not make quick changes. Increase your following distance in front so you never have to brake suddenly, and stay in the right lane to encourage the tailgater to pass.",
        manualCitation = "FMCSA CDL Manual Section 2.7.2 - Space Behind"
    ),

    // ================= PHASE 3: MOUNTAIN DRIVING & DOWNGRADES =================
    Question(
        id = "gk_18",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 3: Mountain Driving & Downgrades",
        questionText = "What two factors determine your selection of a safe speed when going down a long, steep downgrade?",
        options = listOf(
            "Total gross weight of vehicle and cargo, plus the grade's steepness.",
            "Tire tread compound type, plus the number of axles on the trailer.",
            "Ambient air temperature, plus current fuel tank capacity levels.",
            "Highway speed limit for cars, plus opposing wind direction force."
        ),
        correctIndex = 0,
        explanation = "Safe speed on downgrades is determined primarily by total weight of vehicle and cargo, steepness and length of the grade, road conditions, and weather.",
        manualCitation = "FMCSA CDL Manual Section 2.16.1 - Safe Speed on Downgrades"
    ),
    Question(
        id = "gk_19",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 3: Mountain Driving & Downgrades",
        questionText = "Why should you be in the proper gear before starting down a steep hill?",
        options = listOf(
            "Transmission fluid will overheat if shifted on an active decline.",
            "Gravity makes downshifting difficult or impossible once underway.",
            "Trailer emergency air lines lose required pressure during shifts.",
            "Electronic stability control disengages when shifting on slopes."
        ),
        correctIndex = 1,
        explanation = "Shift into a low gear before starting downhill. If you try to downshift after speed has increased, you may be unable to get into any gear, resulting in a runaway truck.",
        manualCitation = "FMCSA CDL Manual Section 2.16.2 - Selecting the Proper Gear"
    ),
    Question(
        id = "gk_20",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 3: Mountain Driving & Downgrades",
        questionText = "What is \"snubbing\" when applied to braking on a downgrade?",
        options = listOf(
            "Riding the foot brake with light, continuous pedal pressure.",
            "Pumping the service brake rapidly every ten to fifteen seconds.",
            "Braking until 5 mph below safe speed, releasing, and repeating.",
            "Locking the trailer hand brake while keeping tractor rolling."
        ),
        correctIndex = 2,
        explanation = "Snubbing technique: Apply brakes firmly until speed drops to 5 mph below your safe speed, release completely. When speed increases back to safe speed, repeat.",
        manualCitation = "FMCSA CDL Manual Section 2.16.4 - Proper Braking Technique"
    ),
    Question(
        id = "gk_21",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 3: Mountain Driving & Downgrades",
        questionText = "True or False: Relying entirely on your service brakes is the best way to control vehicle speed down a mountain.",
        options = listOf(
            "True. Service brakes are engineered to dissipate all mountain heat.",
            "True. As long as foundation brake linings remain cool and dry.",
            "False. Service brakes should only be applied in final emergency.",
            "False. Engine braking and low gears must provide primary control."
        ),
        correctIndex = 3,
        explanation = "False. Service brakes alone cannot handle prolonged downgrade braking without overheating, fading, and failing completely. Engine braking in a low gear is mandatory.",
        manualCitation = "FMCSA CDL Manual Section 2.16.4 - Brake Fade"
    ),

    // ================= PHASE 4: EMERGENCIES, SKIDS & HAZARDS =================
    Question(
        id = "gk_22",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 4: Emergencies, Skids & Hazards",
        questionText = "What is a \"hazard\" while driving?",
        options = listOf(
            "Any road condition or other road user that is a potential danger.",
            "An active collision that has closed one or more lanes of travel.",
            "A stationary emergency service vehicle operating flashing lights.",
            "Any section of highway marked by orange work-zone advisory signs."
        ),
        correctIndex = 0,
        explanation = "A hazard is any road condition or other road user (driver, bicyclist, pedestrian) that is a possible danger. Spotting hazards early lets you prepare before an emergency happens.",
        manualCitation = "FMCSA CDL Manual Section 2.8.1 - Seeing Hazards"
    ),
    Question(
        id = "gk_23",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 4: Emergencies, Skids & Hazards",
        questionText = "What are the three steps of defensive driving when facing a hazard?",
        options = listOf(
            "Accelerate, Signal, and Maneuver.",
            "Identify, Predict, and Decide/Execute.",
            "Observe, Report, and Document.",
            "Brake, Steer, and Pull Over."
        ),
        correctIndex = 1,
        explanation = "Defensive driving requires you to Identify hazards, Predict how they could develop, and Decide & Execute your safety maneuver in time.",
        manualCitation = "FMCSA CDL Manual Section 2.8 - Hazard Awareness"
    ),
    Question(
        id = "gk_24",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 4: Emergencies, Skids & Hazards",
        questionText = "What is the best strategy if you are forced to drive off the road onto the shoulder to avoid a crash?",
        options = listOf(
            "Slam the brakes immediately and cut the steering wheel sharply back.",
            "Accelerate on the shoulder to pass the hazard before re-entering.",
            "Hold wheel firmly, avoid hard braking until slow, and steer back.",
            "Lock trailer brakes with the trolley valve to stop quickly on dirt."
        ),
        correctIndex = 2,
        explanation = "Avoid braking until speed has dropped to around 20 mph to prevent skids on loose shoulders. Keep one set of wheels on pavement if possible, and steer smoothly back when clear.",
        manualCitation = "FMCSA CDL Manual Section 2.17.2 - Leaving the Road"
    ),
    Question(
        id = "gk_25",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 4: Emergencies, Skids & Hazards",
        questionText = "If your vehicle has a front-wheel skid, what will happen?",
        options = listOf(
            "The rear trailer will swing out into adjacent traffic lanes.",
            "The vehicle will slide straight ahead regardless of steering.",
            "The drive wheels will begin spinning twice as fast as speed.",
            "The steering wheel will whip rapidly in the opposite direction."
        ),
        correctIndex = 1,
        explanation = "In a front-wheel skid, front tires slide forward and you lose all steering control. Turning the wheel has zero effect until wheels regain traction.",
        manualCitation = "FMCSA CDL Manual Section 2.19.1 - Front-Wheel Skids"
    ),
    Question(
        id = "gk_26",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 4: Emergencies, Skids & Hazards",
        questionText = "How do you correct a rear-wheel drive-wheel skid?",
        options = listOf(
            "Accelerate hard to pull the combination straight down the lane.",
            "Apply trailer hand brake firmly to stretch the combination out.",
            "Lock all service brakes and keep the steering wheel centered.",
            "Stop braking, turn into the skid direction, and counter-steer."
        ),
        correctIndex = 3,
        explanation = "To recover from a drive-wheel skid: stop braking, push in the clutch, turn the steering wheel in the direction you want the vehicle to go, and counter-steer.",
        manualCitation = "FMCSA CDL Manual Section 2.19.2 - Drive-Wheel Skids"
    ),
    Question(
        id = "gk_27",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 4: Emergencies, Skids & Hazards",
        questionText = "What is the correct procedure when you witness or come upon an accident scene?",
        options = listOf(
            "Protect the area with triangles, notify police, and assist safely.",
            "Pull damaged vehicles off the roadway before police arrive on site.",
            "Proceed past without stopping to prevent congestion in open lanes.",
            "Wave approaching traffic through the scene using a handheld flare."
        ),
        correctIndex = 0,
        explanation = "At an accident scene, protect the area first so another vehicle does not strike the wreckage. Notify police/EMS, and render first aid only if qualified.",
        manualCitation = "FMCSA CDL Manual Section 2.21 - Accident Procedures"
    ),
    Question(
        id = "gk_28",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 4: Emergencies, Skids & Hazards",
        questionText = "What should you do if your brakes fail while driving?",
        options = listOf(
            "Shift into neutral immediately and turn off the engine ignition.",
            "Steer into highway guardrails to dissipate momentum with friction.",
            "Sound horn continuously and coast in high gear until momentum ends.",
            "Downshift, pump brakes, use emergency brake, and seek escape ramp."
        ),
        correctIndex = 3,
        explanation = "If brakes fail: downshift to use engine braking, pump pedal to build pressure, apply parking brake gently, and steer for an escape ramp or open field.",
        manualCitation = "FMCSA CDL Manual Section 2.16.5 - Brake Failure"
    ),

    // ================= PHASE 5: RAILWAY CROSSINGS & SPECIAL CONDITIONS =================
    Question(
        id = "gk_29",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 5: Railway Crossings & Special Conditions",
        questionText = "How many seconds does it take for a normal commercial vehicle/tractor-trailer to completely clear a single railroad track?",
        options = listOf(
            "About 5 seconds.",
            "About 8 seconds.",
            "About 14 seconds.",
            "About 22 seconds."
        ),
        correctIndex = 2,
        explanation = "It takes a typical tractor-trailer combination unit at least 14 seconds to clear a single track, and more than 15 seconds to clear a double track.",
        manualCitation = "FMCSA CDL Manual Section 2.15.2 - Railroad Crossing Clearance"
    ),
    Question(
        id = "gk_30",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 5: Railway Crossings & Special Conditions",
        questionText = "True or False: You should always shift gears while crossing railroad tracks.",
        options = listOf(
            "True. Shifting to higher gears clears the crossing much faster.",
            "True. Downshifting provides essential torque across rough rails.",
            "False. Automatic transmissions may shift, but manuals must not.",
            "False. Never shift gears on tracks due to risk of stalling out."
        ),
        correctIndex = 3,
        explanation = "False. Never shift gears while crossing railroad tracks. Shifting introduces the risk of missing a gear and stalling directly on the rails in front of a train.",
        manualCitation = "FMCSA CDL Manual Section 2.15.2 - Crossing Tracks"
    ),
    Question(
        id = "gk_31",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 5: Railway Crossings & Special Conditions",
        questionText = "When approaching a bridge or overpass, what should you always be aware of?",
        options = listOf(
            "Your vehicle's total height and posted clearance limits.",
            "The maximum axle load limit of the bridge foundation.",
            "Whether opposing traffic lanes feature a toll plaza.",
            "The ambient temperature of the structural steel deck."
        ),
        correctIndex = 0,
        explanation = "Know your vehicle clearance! Repaving roads under bridges can decrease posted clearances, and packed snow or ice will also reduce clearance.",
        manualCitation = "FMCSA CDL Manual Section 2.7.5 - Space Overhead"
    ),
    Question(
        id = "gk_32",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 5: Railway Crossings & Special Conditions",
        questionText = "What is black ice?",
        options = listOf(
            "Melted asphalt tar that bleeds to the road surface in summer.",
            "A thin, transparent ice layer that looks wet and is slippery.",
            "Diesel soot and rubber dust concentrated on highway curves.",
            "Frozen road salt residue left behind by highway snowplows."
        ),
        correctIndex = 1,
        explanation = "Black ice is a thin, clear glaze of ice through which the dark road surface is visible. It looks merely wet, but provides almost zero traction.",
        manualCitation = "FMCSA CDL Manual Section 2.6.2 - Slippery Surfaces"
    ),

    // ================= PHASE 6: CARGO HANDLING, WEIGHT & DISTRIBUTION =================
    Question(
        id = "gk_33",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 6: Cargo Handling, Weight & Distribution",
        questionText = "What three things are commercial drivers primarily responsible for regarding cargo?",
        options = listOf(
            "Purchasing cargo insurance, unloading freight, and fuel taxing.",
            "Inspecting cargo, balancing weight limits, and securing freight.",
            "Weighing individual boxes, sealing doors, and dispatch filing.",
            "Verifying warehouse permits, checking pallets, and crating goods."
        ),
        correctIndex = 1,
        explanation = "Drivers are responsible for: 1) Inspecting the cargo, 2) Recognizing overloads and poorly balanced weight, and 3) Ensuring cargo is properly secured.",
        manualCitation = "FMCSA CDL Manual Section 3.1 - Driver Cargo Responsibility"
    ),
    Question(
        id = "gk_34",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 6: Cargo Handling, Weight & Distribution",
        questionText = "When must cargo be inspected during a trip?",
        options = listOf(
            "At state commercial weigh scale facilities and final customer delivery points.",
            "Within first 50 miles, then every 3 hours or 150 miles, and at duty changes.",
            "Every 100 highway miles or 2 driving hours, plus whenever refueling tractor.",
            "Once every 24 hours of operation or 500 highway miles, whichever occurs first."
        ),
        correctIndex = 1,
        explanation = "Inspect cargo securement within the first 50 miles of starting, after any change of duty status, and at least every 3 hours or 150 miles.",
        manualCitation = "FMCSA CDL Manual Section 3.2.1 - Inspecting Cargo"
    ),
    Question(
        id = "gk_35",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 6: Cargo Handling, Weight & Distribution",
        questionText = "What is the minimum number of tie-downs required for any piece of cargo, regardless of size?",
        options = listOf(
            "At least one tie-down.",
            "At least three tie-downs.",
            "At least two tie-downs.",
            "At least four tie-downs."
        ),
        correctIndex = 2,
        explanation = "No matter how small the cargo is, there must be at least two tiedowns holding it down to prevent shifting if one tiedown loosens.",
        manualCitation = "FMCSA CDL Manual Section 3.2.2 - Cargo Tiedowns"
    ),
    Question(
        id = "gk_36",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 6: Cargo Handling, Weight & Distribution",
        questionText = "As a rule of thumb, how many tie-downs are required relative to cargo length?",
        options = listOf(
            "At least one tie-down for every 5 feet of cargo.",
            "At least two tie-downs for every 5 feet of cargo.",
            "At least one tie-down for every 15 feet of cargo.",
            "At least one tie-down for every 10 feet of cargo."
        ),
        correctIndex = 3,
        explanation = "On flatbed trailers or open loads, use at least one tiedown for each 10 feet of cargo length, with a minimum of two tiedowns regardless of length.",
        manualCitation = "FMCSA CDL Manual Section 3.2.2 - Tiedown Spacing"
    ),
    Question(
        id = "gk_37",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 6: Cargo Handling, Weight & Distribution",
        questionText = "What is Gross Vehicle Weight Rating (GVWR)?",
        options = listOf(
            "The maximum total weight of a vehicle and payload specified by manufacturer.",
            "The actual empty weight of tractor and trailer equipped with fuel tanks.",
            "The maximum legal weight permitted on a single steering axle by state law.",
            "The combined weight of freight payload without the tractor and cab included."
        ),
        correctIndex = 0,
        explanation = "GVWR is the maximum permissible total weight of a single vehicle plus its load, as specified by the manufacturer on the safety certification label.",
        manualCitation = "FMCSA CDL Manual Section 3.1.2 - Weight Ratings"
    ),
    Question(
        id = "gk_38",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 6: Cargo Handling, Weight & Distribution",
        questionText = "What is the primary purpose of the \"Bridge Formula\"?",
        options = listOf(
            "To calculate waterway clearance under older suspension bridges.",
            "To prevent bridge overload by spacing weight across vehicle axles.",
            "To limit the total height of high-cube trailers under overpasses.",
            "To determine maximum legal travel speeds when crossing bridges."
        ),
        correctIndex = 1,
        explanation = "The Bridge Formula specifies maximum axle group weights based on distance between axles. Spreading weight over more distance protects bridges from damage.",
        manualCitation = "FMCSA CDL Manual Section 3.1.2 - Legal Weight Limits"
    ),
    Question(
        id = "gk_39",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 6: Cargo Handling, Weight & Distribution",
        questionText = "Why is cargo placement balance important?",
        options = listOf(
            "Imbalance causes steering difficulty or reduces drive-axle traction.",
            "Imbalance alters electrical voltage delivered to trailer brake lights.",
            "Imbalance causes refrigeration units to freeze cargo inconsistently.",
            "Imbalance determines whether oversized transport permits are needed."
        ),
        correctIndex = 0,
        explanation = "Improper cargo balance can overload the front axle (making steering hard and damaging tires) or underload it (causing steering wander and loss of traction).",
        manualCitation = "FMCSA CDL Manual Section 3.1.3 - Balance"
    ),

    // ================= PHASE 7: HAZARDOUS MATERIALS & MISCELLANEOUS SAFETY =================
    Question(
        id = "gk_40",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 7: Hazardous Materials & Miscellaneous Safety",
        questionText = "What is a hazardous materials placard?",
        options = listOf(
            "Rectangular white cab cards identifying carrier fuel tax accounts.",
            "Diamond-shaped signs on vehicles identifying cargo hazard classes.",
            "Metal chassis plates stamped with vehicle manufacturing dates.",
            "Reflective stickers on trailer mud flaps displaying weight limits."
        ),
        correctIndex = 1,
        explanation = "Placards are 10 3/4-inch diamond-shaped warning signs placed on the outside of a vehicle to warn emergency responders of the hazardous materials inside.",
        manualCitation = "FMCSA CDL Manual Section 9.3.5 - Placarding Requirements"
    ),
    Question(
        id = "gk_41",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 7: Hazardous Materials & Miscellaneous Safety",
        questionText = "If you are hauling hazardous materials and carrying a \"sealed load,\" what must match the shipping papers?",
        options = listOf(
            "The driver's passport number.",
            "The tractor's hub odometer.",
            "The placard numbers and rules.",
            "The trailer reefer temperature."
        ),
        correctIndex = 2,
        explanation = "Even if a trailer is pre-sealed by the shipper, the driver must verify that the placards on the trailer match the hazard classes and UN numbers on the shipping papers.",
        manualCitation = "FMCSA CDL Manual Section 9.3.2 - Shipping Paper Verification"
    ),
    Question(
        id = "gk_42",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 7: Hazardous Materials & Miscellaneous Safety",
        questionText = "What are emergency warning triangles required for, and when must they be set out?",
        options = listOf(
            "To mark legal parking spots at truck stops; set out within 30 minutes.",
            "To guide backing maneuvers into tight docks; set out before reversing.",
            "To reserve designated fuel island lanes; set out within 15 minutes.",
            "To warn traffic during breakdown stops; set out within 10 minutes."
        ),
        correctIndex = 3,
        explanation = "If stopped on a roadway or shoulder, you must turn on hazard flashers and deploy reflective emergency triangles within 10 minutes.",
        manualCitation = "FMCSA CDL Manual Section 2.5.2 - Placing Warning Devices"
    ),
    Question(
        id = "gk_43",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 7: Hazardous Materials & Miscellaneous Safety",
        questionText = "Where must emergency triangles be placed if you break down on a two-way or divided highway?",
        options = listOf(
            "10 feet behind (traffic side), 100 feet behind, and 100 feet ahead.",
            "10 feet, 100 feet, and 200 feet all behind toward oncoming traffic.",
            "50 feet, 100 feet, and 150 feet all ahead toward oncoming traffic.",
            "25 feet behind, 50 feet ahead, and mounted to the driver cab mirror."
        ),
        correctIndex = 0,
        explanation = "On a two-way road: place one triangle 10 feet behind (traffic side), one 100 feet behind on the shoulder/lane, and one 100 feet ahead toward oncoming traffic.",
        manualCitation = "FMCSA CDL Manual Section 2.5.2 - Warning Placement on Two-Way Roads"
    ),
    Question(
        id = "gk_44",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 7: Hazardous Materials & Miscellaneous Safety",
        questionText = "Where must warning devices be placed if you break down on a one-way or divided highway?",
        options = listOf(
            "10 feet behind, 100 feet behind, and 100 feet ahead of vehicle.",
            "50 feet, 100 feet, and 300 feet ahead of the broken down vehicle.",
            "At 100-foot equal intervals starting directly at front bumper.",
            "10 feet, 100 feet, and 200 feet behind toward approaching cars."
        ),
        correctIndex = 3,
        explanation = "On a one-way or divided highway with traffic approaching from one direction: place triangles at 10 feet, 100 feet, and 200 feet behind the vehicle.",
        manualCitation = "FMCSA CDL Manual Section 2.5.2 - Warning Placement on Divided Highways"
    ),
    Question(
        id = "gk_45",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 7: Hazardous Materials & Miscellaneous Safety",
        questionText = "What is the legal Blood Alcohol Concentration (BAC) limit for operating a commercial motor vehicle?",
        options = listOf(
            "0.08% or higher.",
            "0.02% or higher.",
            "0.04% or higher.",
            "0.05% or higher."
        ),
        correctIndex = 2,
        explanation = "The legal BAC limit for commercial drivers is 0.04% (half the standard 0.08% limit for non-commercial drivers). Any detectable alcohol puts you out of service for 24 hours.",
        manualCitation = "FMCSA CDL Manual Section 1.2.1 - Alcohol Limits"
    ),
    Question(
        id = "gk_46",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 7: Hazardous Materials & Miscellaneous Safety",
        questionText = "Can you be issued a CDL disqualification if you get a DUI in your personal passenger vehicle?",
        options = listOf(
            "No, personal car violations never affect commercial driving credentials.",
            "Yes, a first-time DUI in any vehicle causes a 1-year CDL disqualification.",
            "Only if the passenger vehicle was registered under your freight employer.",
            "No, but mandatory insurance surcharges will apply for thirty-six months."
        ),
        correctIndex = 1,
        explanation = "Yes. If convicted of DUI while driving ANY vehicle (even your personal car), you lose your CDL for at least 1 year for a first offense, and for life for a second offense.",
        manualCitation = "FMCSA CDL Manual Section 1.2.1 - Major Disqualifying Offenses"
    ),
    Question(
        id = "gk_47",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 7: Hazardous Materials & Miscellaneous Safety",
        questionText = "What does an Anti-lock Braking System (ABS) do?",
        options = listOf(
            "Cuts overall vehicle stopping distance by half on snowy or icy highways.",
            "Applies emergency brakes automatically when front sensors detect hazards.",
            "Prevents wheel lockup during hard braking so you maintain steering control.",
            "Replaces foundation drum brakes with electronically modulated retarders."
        ),
        correctIndex = 2,
        explanation = "ABS activates only when wheels are about to lock up. It modulates brake pressure to keep wheels rolling, allowing the driver to steer and maintain directional control.",
        manualCitation = "FMCSA CDL Manual Section 2.18 - Anti-Lock Braking Systems"
    ),
    Question(
        id = "gk_48",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 7: Hazardous Materials & Miscellaneous Safety",
        questionText = "How do you test hydraulic brakes for leaks?",
        options = listOf(
            "Drive at 20 mph and brake firmly without holding onto the steering wheel.",
            "Open the bleeder valve on the drive axle while an assistant holds pedal.",
            "Disconnect vacuum booster hose and listen for audible pneumatic hissing.",
            "Pump pedal 3 times, apply firm pressure for 5 seconds; pedal must not move."
        ),
        correctIndex = 3,
        explanation = "To test hydraulic brakes: pump pedal 3 times, then hold firm pressure for 5 seconds. The pedal should remain firm and not sink or move. If it sinks, there is a leak.",
        manualCitation = "FMCSA CDL Manual Section 2.1.5 - Testing Hydraulic Brakes"
    ),
    Question(
        id = "gk_49",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 7: Hazardous Materials & Miscellaneous Safety",
        questionText = "What is aggressive driving defined as, versus road rage?",
        options = listOf(
            "Aggressive driving is traffic offenses endangering others; road rage is assault.",
            "Aggressive driving involves weapons; road rage involves speeding past trucks.",
            "Aggressive driving happens in cities; road rage happens exclusively on freeways.",
            "Under FMCSA definitions, there is no distinction between the two behaviors."
        ),
        correctIndex = 0,
        explanation = "Aggressive driving is operating a vehicle selfishly or boldly without regard for rights or safety. Road rage is operating with intent to do physical harm or assault other drivers.",
        manualCitation = "FMCSA CDL Manual Section 2.9 - Aggressive Driving and Road Rage"
    ),
    Question(
        id = "gk_50",
        category = CDLCategory.GENERAL_KNOWLEDGE,
        subtopic = "Phase 7: Hazardous Materials & Miscellaneous Safety",
        questionText = "What is the best countermeasure against driver fatigue on a long trip?",
        options = listOf(
            "Drinking multiple high-caffeine energy beverages every two driving hours.",
            "Rolling cab windows down fully with audio systems set to maximum volume.",
            "Chewing crushed ice cubes continuously while maintaining highway speed.",
            "Getting adequate sleep beforehand and taking short breaks or a quick nap."
        ),
        correctIndex = 3,
        explanation = "Sleep is the only genuine remedy for fatigue. Get 7-8 hours before driving, stop for a 15-20 minute nap before drowsiness becomes acute, and take breaks every 2 hours or 100 miles.",
        manualCitation = "FMCSA CDL Manual Section 2.14 - Driving Drowsy"
    )
)
