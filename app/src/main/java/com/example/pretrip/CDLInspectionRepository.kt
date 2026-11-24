package com.example.pretrip

/**
 * Repository containing standard FMCSA CDL Pre-Trip Inspection points,
 * verbatim examiner recitation criteria, and critical safety quiz items.
 */
object CDLInspectionRepository {

    /**
     * Loads interactive markers across all mandatory CDL Class A / Class B inspection zones.
     */
    fun loadInspectionMarkers(): List<InspectionComponent> {
        return listOf(
            // ==========================================
            // ==========================================
            // 0. ALL FLUID LEVELS (UNDER HOOD)
            // ==========================================
            InspectionComponent(
                id = "dipstick",
                zone = InspectionZone.ALL_FLUID_LEVELS,
                name = "Engine Oil Dipstick",
                systemGroup = "Fluids & Level Check",
                xNormalized = 0.33f,
                yNormalized = 0.21f,
                zDepth = 0.3f,
                fmcsaCriteria = "Pull out dipstick, wipe with clean rag, re-insert fully into tube, and pull out again. Ensure oil level is between operating min/max marks in the safe crosshatch zone.",
                criticalFailurePoint = "Engine oil level below ADD mark or milky white indicating coolant contamination.",
                isAutoFailIfMissed = false,
                mandatoryVerbatimPhrases = listOf(
                    "Pull out dipstick, wipe clean with rag",
                    "Re-insert dipstick fully into tube",
                    "Pull out again to check oil level",
                    "Ensure oil level is between operating MIN and MAX marks"
                ),
                quiz = ComponentQuiz(
                    question = "What is the mandatory 4-step sequence for checking engine oil during a CDL pre-trip inspection?",
                    options = listOf(
                        "Check with engine idling, pull once, read gauge",
                        "Pull out, wipe clean, re-insert fully, pull out again to verify level between MIN and MAX",
                        "Add one gallon of 15W-40 before pulling dipstick",
                        "Only inspect the oil pressure gauge on the dashboard"
                    ),
                    correctIndex = 1,
                    explanation = "The examiner requires you to pull out the dipstick, wipe it clean, re-insert it fully, pull it out again, and confirm the level is in the safe zone between MIN/ADD and MAX/FULL."
                )
            ),
            InspectionComponent(
                id = "coolant_recovery_tank",
                zone = InspectionZone.ALL_FLUID_LEVELS,
                name = "Coolant Recovery Tank",
                systemGroup = "Fluids & Level Check",
                xNormalized = 0.52f,
                yNormalized = 0.18f,
                zDepth = 0.4f,
                fmcsaCriteria = "Check fluid level through the sight glass or translucent reservoir (ensure between Cold and Hot fill lines); ensure no leaks, and radiator cap is tight with gasket intact.",
                criticalFailurePoint = "Empty coolant reservoir or pressure cap missing/leaking causing rapid engine overheating.",
                isAutoFailIfMissed = false,
                mandatoryVerbatimPhrases = listOf(
                    "Check coolant level through sight glass or reservoir",
                    "Level is in safe operating range between Cold Full and Hot Full",
                    "No coolant leaks from hoses, clamps, or reservoir seams",
                    "Pressure cap is tightly secured and sealed"
                ),
                quiz = ComponentQuiz(
                    question = "How must you verify the engine coolant level on a semi-truck with a recovery tank?",
                    options = listOf(
                        "Remove the hot radiator cap immediately with bare hands",
                        "Check through the sight glass or translucent reservoir between Cold and Hot marks; verify cap is tight and no leaks",
                        "Fill tank until it overflows onto the ground",
                        "Turn on high heater fans to check temperature"
                    ),
                    correctIndex = 1,
                    explanation = "Check coolant through the reservoir sight glass or translucent plastic without removing the hot cap. Verify level is safe, cap is tight, and hoses are not leaking."
                )
            ),
            InspectionComponent(
                id = "power-steering-reservoir",
                zone = InspectionZone.ALL_FLUID_LEVELS,
                name = "Power Steering Fluid Reservoir",
                systemGroup = "Fluids & Level Check",
                xNormalized = 0.22f,
                yNormalized = 0.26f,
                zDepth = 0.3f,
                fmcsaCriteria = "Check dipstick or sight glass for proper fluid level (above minimum/safe range); verify hoses have no cuts, bulges, abrasions, or leaks.",
                criticalFailurePoint = "Power steering fluid empty or burst hose causing total steering loss.",
                isAutoFailIfMissed = true,
                mandatoryVerbatimPhrases = listOf(
                    "Check fluid level on dipstick or sight glass above MIN line",
                    "Reservoir securely mounted and cap is tight",
                    "Hoses have no cuts, bulges, or abrasions",
                    "No power steering fluid leaks from hoses or fittings"
                ),
                quiz = ComponentQuiz(
                    question = "What must you check regarding the power steering reservoir hoses during the pre-trip inspection?",
                    options = listOf(
                        "Hoses must be painted red",
                        "Verify hoses have no cuts, bulges, abrasions, or leaks, and clamps are tight",
                        "Hoses must be disconnected to inspect internal fluid",
                        "They must have at least 2 inches of deflection"
                    ),
                    correctIndex = 1,
                    explanation = "Verify that power steering hoses are securely clamped with no cuts, bulges, abrasions, or fluid leaks."
                )
            ),
            InspectionComponent(
                id = "eng_fluid_leaks",
                zone = InspectionZone.ALL_FLUID_LEVELS,
                name = "Undercarriage Puddles & Fluid Leaks",
                systemGroup = "Fluids & Level Check",
                xNormalized = 0.20f,
                yNormalized = 0.85f,
                zDepth = 0.2f,
                fmcsaCriteria = "Look underneath the engine and transmission for fresh puddles of coolant, oil, or fuel. Check for wet spots on ground or components.",
                criticalFailurePoint = "Active fuel or major brake/oil leak on the ground is an immediate out-of-service condition.",
                isAutoFailIfMissed = true,
                mandatoryVerbatimPhrases = listOf(
                    "No puddles or drips underneath the vehicle",
                    "No fluid leaks on engine block or hoses",
                    "No active fuel or oil dripping"
                ),
                quiz = ComponentQuiz(
                    question = "What indicates a critical fluid leak beneath the engine compartment during inspection?",
                    options = listOf(
                        "Small condensation puddle from the A/C compressor",
                        "Active puddle of oil, coolant, or fuel dripping on ground",
                        "Dust accumulation on the oil pan",
                        "Dry grease around the motor mounts"
                    ),
                    correctIndex = 1,
                    explanation = "Any active drip or puddle of fuel, oil, or coolant indicates a leak that must be identified and corrected before operating."
                )
            ),

            // ==========================================
            // 1. STEERING COMPONENTS
            // ==========================================
            InspectionComponent(
                id = "steering_gear_box",
                zone = InspectionZone.STEERING_COMPONENTS,
                name = "Steering Gear Box & Hoses",
                systemGroup = "Steering Components",
                xNormalized = 0.20f,
                yNormalized = 0.52f,
                zDepth = 0.4f,
                fmcsaCriteria = "Steering gear box securely mounted to frame rail with no loose or missing bolts, no cracks in casting or frame. Power steering hoses must have tight connections with zero cuts, bulges, abrasions, or fluid leaks.",
                criticalFailurePoint = "Loose or missing frame mounting bolts, cracked gear box casing, or burst steering hose causing immediate total loss of vehicle steering.",
                isAutoFailIfMissed = true,
                mandatoryVerbatimPhrases = listOf(
                    "Steering gear box securely mounted to the frame",
                    "No loose or missing mounting bolts",
                    "No cracks in gear box casting or frame rail",
                    "Power steering hoses have no cuts, bulges, or leaks",
                    "All hose fittings are tight and leak-free"
                ),
                quiz = ComponentQuiz(
                    question = "What are the critical inspection requirements for the steering gear box and hoses?",
                    options = listOf(
                        "Must be lubricated with engine oil on the outside casing",
                        "Securely mounted to frame with no loose/missing bolts or cracks; hoses must be leak-free with tight fittings",
                        "Must have at least 1 inch of loose play on the frame",
                        "Hoses must be wrapped in reflective electrical tape"
                    ),
                    correctIndex = 1,
                    explanation = "Under FMCSA CDL testing standards, the steering gear box must be securely mounted to the frame rail with no loose or missing bolts, no cracks, and power steering hoses must be leak-free with no cuts or bulges."
                )
            ),
            InspectionComponent(
                id = "steering_linkage",
                zone = InspectionZone.STEERING_COMPONENTS,
                name = "Pitman Arm, Drag Link, & Tie Rod",
                systemGroup = "Steering Components",
                xNormalized = 0.22f,
                yNormalized = 0.68f,
                zDepth = 0.4f,
                fmcsaCriteria = "Check that connecting links, arms, and rods (Pitman arm, drag link, tie rod) are not cracked, bent, or broken. Check that castle nuts and cotter pins are present and tight. Ensure no excessive play or loose joints.",
                criticalFailurePoint = "Missing cotter pin, loose castle nut, or separated ball joint resulting in catastrophic steering disconnect while driving.",
                isAutoFailIfMissed = true,
                mandatoryVerbatimPhrases = listOf(
                    "Pitman arm, drag link, and tie rod not cracked, bent, or broken",
                    "Castle nuts and cotter pins are present and tight",
                    "No excessive play or loose joints in ball sockets",
                    "Grease boots intact with no tears or missing clips"
                ),
                quiz = ComponentQuiz(
                    question = "What fastener hardware is mandatory on the Pitman arm, drag link, and tie rod connections?",
                    options = listOf(
                        "Plastic zip ties and safety wire",
                        "Castle nuts and cotter pins must be present and tight",
                        "Standard nylon locknuts without pins",
                        "Quick-release hitch pins"
                    ),
                    correctIndex = 1,
                    explanation = "All steering linkage joints (Pitman arm, drag link ends, tie rod ends) require castle nuts torqued tight and locked with cotter pins to prevent steering separation."
                )
            ),

            // ==========================================
            // 2. ENGINE DRIVEN BELTS & HOSES
            // ==========================================
            InspectionComponent(
                id = "belt_alternator",
                zone = InspectionZone.BELTS_AND_HOSES,
                name = "Alternator Belt",
                systemGroup = "Engine Driven Belts",
                xNormalized = 0.68f,
                yNormalized = 0.25f,
                zDepth = 0.6f,
                fmcsaCriteria = "Check alternator drive belt for fraying, cracks, cuts, or excessive wear. Push belt at midway point; deflection/play must not exceed 1/2 to 3/4 inch. Verbally state whether belt-driven or gear-driven.",
                criticalFailurePoint = "Belt frayed down to internal cords or having more than 3/4 inch of loose slack causing alternator charging failure.",
                isAutoFailIfMissed = false,
                mandatoryVerbatimPhrases = listOf(
                    "Check for fraying, cracks, cuts, or excessive wear",
                    "Push belt at center; play does not exceed 3/4 inch",
                    "State to examiner that alternator is belt-driven"
                ),
                quiz = ComponentQuiz(
                    question = "When pushing on the alternator belt midway between pulleys, what is the maximum allowable play/deflection?",
                    options = listOf(
                        "1 to 2 inches of deflection",
                        "No more than 1/2 to 3/4 inch of play",
                        "Must have at least 1.5 inches of loose movement",
                        "Zero movement (rigidly locked with zero give)"
                    ),
                    correctIndex = 1,
                    explanation = "Under FMCSA CDL testing criteria, belt play/deflection when pushed firmly at the midway point between pulleys must not exceed 1/2 to 3/4 inch."
                )
            ),
            InspectionComponent(
                id = "belt_water_pump",
                zone = InspectionZone.BELTS_AND_HOSES,
                name = "Water Pump Belt",
                systemGroup = "Engine Driven Belts",
                xNormalized = 0.50f,
                yNormalized = 0.35f,
                zDepth = 0.5f,
                fmcsaCriteria = "Check water pump drive belt for fraying, cracks, splits, or excessive wear. Deflection play must not exceed 1/2 to 3/4 inch. Explicitly state whether the water pump is belt-driven or gear-driven.",
                criticalFailurePoint = "Loose or snapped water pump belt stops coolant circulation and destroys engine within minutes.",
                isAutoFailIfMissed = false,
                mandatoryVerbatimPhrases = listOf(
                    "Check belt for fraying, cracks, cuts, or dry rotting",
                    "Verify belt tension has no more than 3/4 inch of deflection",
                    "Verbally declare whether water pump is belt-driven or gear-driven"
                ),
                quiz = ComponentQuiz(
                    question = "What must you check on the water pump belt, and what must you tell the CDL examiner?",
                    options = listOf(
                        "Check the belt color and tell the examiner the engine displacement",
                        "Check for fraying, cracks, cuts, wear, play not over 3/4 inch, and state whether belt or gear driven",
                        "Only check if the water pump is painted green",
                        "Loosen the tensioner bolt during the inspection"
                    ),
                    correctIndex = 1,
                    explanation = "You must inspect the belt for cracks, cuts, fraying, wear, ensure no more than 3/4 inch of play, and verbally declare to the examiner whether it is belt-driven or gear-driven."
                )
            ),
            InspectionComponent(
                id = "belt_air_compressor",
                zone = InspectionZone.BELTS_AND_HOSES,
                name = "Air Compressor Belt / Drive",
                systemGroup = "Engine Driven Belts",
                xNormalized = 0.62f,
                yNormalized = 0.40f,
                zDepth = 0.4f,
                fmcsaCriteria = "Check air compressor belt for fraying, cracks, cuts, oil contamination, or excessive wear. Play must not exceed 3/4 inch. If gear-driven, verify gear housing is securely mounted without leaks.",
                criticalFailurePoint = "Air compressor drive belt failure leaves truck unable to build air brake pressure.",
                isAutoFailIfMissed = true,
                mandatoryVerbatimPhrases = listOf(
                    "Check belt for fraying, cracks, cuts, or excessive wear",
                    "Ensure belt deflection does not exceed 3/4 inch",
                    "Identify if compressor is belt-driven or gear-driven"
                ),
                quiz = ComponentQuiz(
                    question = "Why is inspecting the air compressor drive belt or gear drive a critical pre-trip check?",
                    options = listOf(
                        "It controls the power windows and radio antenna",
                        "Without compressor drive, the system cannot build or maintain 120-140 PSI air brake pressure",
                        "It only charges the 12-volt battery bank",
                        "It provides extra engine horsepower for steep grades"
                    ),
                    correctIndex = 1,
                    explanation = "The air compressor generates all compressed air for service brakes and emergency spring brakes. Belt slippage or breakage leads to emergency brake lockup or brake failure."
                )
            ),
            InspectionComponent(
                id = "hose_coolant",
                zone = InspectionZone.BELTS_AND_HOSES,
                name = "Coolant Radiator Hoses",
                systemGroup = "Engine Hoses",
                xNormalized = 0.45f,
                yNormalized = 0.22f,
                zDepth = 0.4f,
                fmcsaCriteria = "Inspect upper and lower radiator hoses. Check all clamps are tight and securely seated. Verify zero signs of dry rot, cracking, bubbling, soft spots, or coolant leaks.",
                criticalFailurePoint = "Spongy swollen hose or loose radiator clamp blowing off under operating pressure.",
                isAutoFailIfMissed = false,
                mandatoryVerbatimPhrases = listOf(
                    "All hose clamps are tight and securely seated",
                    "Zero signs of dry rot, cracking, or cuts",
                    "No bubbling, swelling, or soft spots when squeezed",
                    "No coolant leaks or weeping at connections"
                ),
                quiz = ComponentQuiz(
                    question = "What indicates a radiator coolant hose is compromised and requires replacement?",
                    options = listOf(
                        "Firm rubber with clean black finish",
                        "Bubbling, soft spongy spots, dry rot cracking, or loose clamps",
                        "A stainless steel hose clamp fastened tightly",
                        "Normal operating warmth from hot coolant"
                    ),
                    correctIndex = 1,
                    explanation = "Bubbling, swelling, sponginess, dry rot cracking, or leaking clamps are telltale signs of internal hose cord degradation and imminent rupture."
                )
            ),
            InspectionComponent(
                id = "hose_heater",
                zone = InspectionZone.BELTS_AND_HOSES,
                name = "Heater Core Hoses",
                systemGroup = "Engine Hoses",
                xNormalized = 0.72f,
                yNormalized = 0.18f,
                zDepth = 0.5f,
                fmcsaCriteria = "Inspect heater core inlet and outlet hoses running to the cab firewall. Ensure clamps are tight, hoses have no cuts, dry rot, leaks, or bubbling, and routed away from hot manifolds.",
                criticalFailurePoint = "Ruptured heater hose sprays boiling coolant into engine compartment or into cab heater ducting.",
                isAutoFailIfMissed = false,
                mandatoryVerbatimPhrases = listOf(
                    "Heater hoses securely clamped at firewall and engine block",
                    "No dry rot, cracks, cuts, or rubbing against sharp edges",
                    "No bubbling, swelling, or soft spots",
                    "Zero coolant leaks"
                ),
                quiz = ComponentQuiz(
                    question = "When inspecting heater hoses during the CDL pre-trip, what routing condition should you look out for?",
                    options = listOf(
                        "Hoses must be tied directly onto the exhaust manifold",
                        "Hoses must be securely routed away from moving belts and hot exhaust parts, with tight clamps and no rubbing",
                        "Hoses must hang loose beneath the front steering axle",
                        "Hoses must have slack touching the radiator fan blades"
                    ),
                    correctIndex = 1,
                    explanation = "Heater hoses must be securely clamped, free of dry rot or bubbling, and safely routed away from hot turbo/exhaust parts and spinning belts."
                )
            ),
            InspectionComponent(
                id = "hose_oil",
                zone = InspectionZone.BELTS_AND_HOSES,
                name = "Engine Oil & Turbo Lines",
                systemGroup = "Engine Hoses",
                xNormalized = 0.58f,
                yNormalized = 0.48f,
                zDepth = 0.3f,
                fmcsaCriteria = "Inspect oil supply lines, turbocharger oil feed/return hoses, and breather tubes. Verify all fittings and clamps are tight, zero dry rot, zero bubbling, and zero active oil seepage.",
                criticalFailurePoint = "Pressurized turbo oil feed leak sprays oil onto hot exhaust, posing catastrophic fire hazard.",
                isAutoFailIfMissed = true,
                mandatoryVerbatimPhrases = listOf(
                    "Fittings and clamps are tight with no loose connections",
                    "No dry rot, cracking, cuts, or chafing against metal",
                    "No bubbling, swelling, or heat damage",
                    "Zero active engine oil or turbo oil leaks"
                ),
                quiz = ComponentQuiz(
                    question = "Why are oil hose leaks and bubbling especially dangerous in the engine compartment?",
                    options = listOf(
                        "They only cause minor windshield wiper streaks",
                        "Pressurized oil spraying onto hot exhaust manifolds or turbochargers creates an extreme fire hazard",
                        "Oil hoses have no effect on engine life",
                        "Bubbling hoses improve engine oil cooling efficiency"
                    ),
                    correctIndex = 1,
                    explanation = "Pressurized oil spraying from a failed or bubbling hose onto high-temperature turbo or exhaust components is a leading cause of commercial vehicle engine fires."
                )
            ),

            // ==========================================
            // 3. FRONT SUSPENSION
            // ==========================================
            InspectionComponent(
                id = "susp_leaf_springs",
                zone = InspectionZone.FRONT_SUSPENSION,
                name = "Leaf Springs & Alignment",
                systemGroup = "Front Suspension",
                xNormalized = 0.43f,
                yNormalized = 0.78f,
                zDepth = 0.4f,
                fmcsaCriteria = "Inspect leaf springs for cracks, breaks, shifting, or missing leaves. Leaves must be aligned with rebound clips tight. 1/4 or more of leaves broken or missing is an immediate out-of-service failure.",
                criticalFailurePoint = "Cracked main leaf, shifted leaves contacting tire sidewall, or 1/4+ of leaf pack broken or missing.",
                isAutoFailIfMissed = true,
                mandatoryVerbatimPhrases = listOf(
                    "Inspect leaf springs for cracks, breaks, or splits",
                    "No shifted leaves scissoring out of alignment",
                    "No missing leaves in the spring pack",
                    "Rebound alignment clips are securely in place"
                ),
                quiz = ComponentQuiz(
                    question = "Why is shifting of leaf springs out of alignment dangerous on the front steer axle?",
                    options = listOf(
                        "It only causes slight steering wheel squeak",
                        "Shifted leaves can strike tire sidewalls or severed brake air lines",
                        "It increases leaf spring flexibility for highway driving",
                        "Shifted leaves improve turning radius"
                    ),
                    correctIndex = 1,
                    explanation = "Shifted or scissored leaf springs can contact the inner sidewall of the front steer tires, causing rapid blowouts or severing brake hoses."
                )
            ),
            InspectionComponent(
                id = "susp_ubolts_mounts",
                zone = InspectionZone.FRONT_SUSPENSION,
                name = "U-Bolts & Spring Mounts",
                systemGroup = "Front Suspension",
                xNormalized = 0.48f,
                yNormalized = 0.82f,
                zDepth = 0.4f,
                fmcsaCriteria = "U-bolts securely clamp spring pack to axle; nuts are tight with no cracks or rust trails indicating looseness. Spring hangers and shackle brackets securely bolted to frame rail with no cracks or broken pins.",
                criticalFailurePoint = "Loose U-bolts, broken spring hanger, or rust powder trails indicating axle shift under braking.",
                isAutoFailIfMissed = true,
                mandatoryVerbatimPhrases = listOf(
                    "U-bolts securely bolted to the axle with no loose or missing nuts",
                    "No cracks in U-bolts, saddle plate, or spring hangers",
                    "No rust trails or shiny metal rings indicating looseness",
                    "Spring mounts and shackle pins securely attached to the frame"
                ),
                quiz = ComponentQuiz(
                    question = "What does reddish-brown rust trails or shiny metal around U-bolt nuts indicate during inspection?",
                    options = listOf(
                        "Proper high-temperature protective coating",
                        "The U-bolts are loose and rubbing against the mounting plate",
                        "Normal moisture condensation from overnight parking",
                        "The axle has recently been greased"
                    ),
                    correctIndex = 1,
                    explanation = "Rust trails radiating from U-bolt nuts or shiny metal rings indicate movement and looseness between the U-bolts and spring saddle."
                )
            ),
            InspectionComponent(
                id = "susp_shock_absorber",
                zone = InspectionZone.FRONT_SUSPENSION,
                name = "Front Shock Absorber",
                systemGroup = "Front Suspension",
                xNormalized = 0.52f,
                yNormalized = 0.65f,
                zDepth = 0.4f,
                fmcsaCriteria = "Shock absorber securely mounted at top frame bracket and bottom axle bracket. Inspect for hydraulic fluid/oil leaks and ensure rubber mounting eyelet bushings are not worn, split, or missing.",
                criticalFailurePoint = "Active hydraulic oil leak from seal or missing mounting bolt/shattered bushing.",
                isAutoFailIfMissed = false,
                mandatoryVerbatimPhrases = listOf(
                    "Shock absorber securely mounted at top and bottom",
                    "Mounting bolts and nuts are tight",
                    "No hydraulic oil leaks or weeping from shock cylinder",
                    "Rubber mounting bushings are not dry-rotted, split, or missing"
                ),
                quiz = ComponentQuiz(
                    question = "When inspecting the front shock absorber, what two critical conditions must you verify?",
                    options = listOf(
                        "That it is painted yellow and pressurized with nitrogen gas",
                        "Secure top and bottom mounting with tight hardware, and zero hydraulic oil leaks with intact rubber bushings",
                        "That the cylinder can be compressed easily by hand",
                        "That it is equipped with external cooling fins"
                    ),
                    correctIndex = 1,
                    explanation = "The shock absorber must be securely bolted top and bottom with intact rubber bushings, and the cylinder must have no hydraulic fluid leaks."
                )
            ),

            // ==========================================
            // 4. ENGINE COMPARTMENT (COMPONENTS)
            // ==========================================
            InspectionComponent(
                id = "water-pump",
                zone = InspectionZone.ENGINE_COMPARTMENT,
                name = "Water Pump",
                systemGroup = "Cooling System",
                xNormalized = 0.495f,
                yNormalized = 0.34f,
                zDepth = 0.5f,
                fmcsaCriteria = "Securely mounted, not leaking, belt or gear-driven (must identify whether belt or gear-driven to examiner).",
                criticalFailurePoint = "Coolant weeping from vent hole indicates imminent pump seal failure.",
                isAutoFailIfMissed = false,
                mandatoryVerbatimPhrases = listOf(
                    "Securely mounted with no missing bolts",
                    "Not cracked, bent, or broken",
                    "No coolant leaks",
                    "State whether it is belt-driven or gear-driven"
                ),
                quiz = ComponentQuiz(
                    question = "When inspecting the water pump for the CDL examiner, what specific detail must you verbally identify?",
                    options = listOf(
                        "The exact RPM it spins at highway speed",
                        "Whether it is belt-driven or gear-driven and not leaking",
                        "The horsepower rating of the impeller",
                        "The manufacturing date code"
                    ),
                    correctIndex = 1,
                    explanation = "Examiners require candidates to explicitly specify whether the water pump is belt-driven or gear-driven and confirm it is not leaking."
                )
            ),
            InspectionComponent(
                id = "alternator",
                zone = InspectionZone.ENGINE_COMPARTMENT,
                name = "Alternator",
                systemGroup = "Electrical",
                xNormalized = 0.685f,
                yNormalized = 0.23f,
                zDepth = 0.6f,
                fmcsaCriteria = "Securely mounted, wires not frayed or loose, belt or gear-driven.",
                criticalFailurePoint = "Loose mounting bolts or bare exposed copper wires causing fire risk.",
                isAutoFailIfMissed = false,
                mandatoryVerbatimPhrases = listOf(
                    "Securely mounted with all bolts tight",
                    "Not cracked, bent, or damaged",
                    "Electrical wiring is not frayed, burnt, or loose",
                    "State if belt-driven or gear-driven"
                ),
                quiz = ComponentQuiz(
                    question = "What is the primary visual check for the alternator wiring during the pre-trip inspection?",
                    options = listOf(
                        "Must be wrapped in aluminum heat tape",
                        "Wires must not be frayed, burnt, corroded, or loose",
                        "Wires must connect directly to the starter relay",
                        "Must have minimum 12-gauge solid copper wiring"
                    ),
                    correctIndex = 1,
                    explanation = "All electrical wiring to the alternator must be securely fastened with no frayed, exposed, or loose wires."
                )
            ),
            InspectionComponent(
                id = "air-compressor",
                zone = InspectionZone.ENGINE_COMPARTMENT,
                name = "Air Compressor & Lines",
                systemGroup = "Air System",
                xNormalized = 0.62f,
                yNormalized = 0.39f,
                zDepth = 0.4f,
                fmcsaCriteria = "Securely mounted, no audible air leaks, identify if gear or belt-driven.",
                criticalFailurePoint = "Audible hiss or loose air compressor bracket.",
                isAutoFailIfMissed = true,
                mandatoryVerbatimPhrases = listOf(
                    "Securely mounted to the engine",
                    "Not cracked, broken, or leaking air",
                    "Identify whether it is gear-driven or belt-driven",
                    "Lines are securely fastened without audible leaks"
                ),
                quiz = ComponentQuiz(
                    question = "If the air compressor is belt-driven, what is the maximum allowable belt deflection in the center?",
                    options = listOf(
                        "1 to 2 inches of play",
                        "1/2 to 3/4 inch of play at the center of the belt",
                        "Zero deflection allowed (must be rigid)",
                        "At least 1.5 inches of deflection"
                    ),
                    correctIndex = 1,
                    explanation = "Proper belt tension is typically 1/2 to 3/4 inch of play at the center of the belt span."
                )
            ),
            InspectionComponent(
                id = "steering-gear-box",
                zone = InspectionZone.ENGINE_COMPARTMENT,
                name = "Steering Gear Box",
                systemGroup = "Steering System",
                xNormalized = 0.195f,
                yNormalized = 0.55f,
                zDepth = 0.3f,
                fmcsaCriteria = "Check steering gear box for secure mounting to frame rail, no missing bolts, and no fluid leaks.",
                criticalFailurePoint = "Missing mounting bolts or steering gear box fluid leak.",
                isAutoFailIfMissed = true,
                mandatoryVerbatimPhrases = listOf(
                    "Steering box securely mounted to frame rail",
                    "No missing bolts, nuts, or cotter pins",
                    "No power steering fluid leaks from box or seals"
                ),
                quiz = ComponentQuiz(
                    question = "Why is a loose mounting bolt on the power steering gear box an automatic out-of-service failure?",
                    options = listOf(
                        "It creates minor engine vibration",
                        "It can cause complete loss of directional steering control",
                        "It reduces air brake pressure",
                        "It increases fuel consumption by 15%"
                    ),
                    correctIndex = 1,
                    explanation = "The power steering gear box connects directly to the steering shaft and pitman arm. Any loose mounting could sever steering control."
                )
            ),
            InspectionComponent(
                id = "pitman-arm",
                zone = InspectionZone.ENGINE_COMPARTMENT,
                name = "Pitman Arm",
                systemGroup = "Steering System",
                xNormalized = 0.192f,
                yNormalized = 0.64f,
                zDepth = 0.3f,
                fmcsaCriteria = "Inspect Pitman arm connecting steering gear box sector shaft to drag link. Check for cracks, bends, play, and secure castle nut with cotter pin.",
                criticalFailurePoint = "Missing cotter pin or loose castle nut on Pitman arm ball joint.",
                isAutoFailIfMissed = true,
                mandatoryVerbatimPhrases = listOf(
                    "Pitman arm not cracked, bent, or broken",
                    "Castle nuts and cotter pins securely in place",
                    "No excessive play or looseness in ball joint"
                ),
                quiz = ComponentQuiz(
                    question = "What must you verify where the Pitman arm attaches to the sector shaft and drag link?",
                    options = listOf(
                        "Rubber safety cover is glued",
                        "Castle nuts are tight and locked with cotter pins",
                        "Painted in reflective orange safety enamel",
                        "Minimum 2 inches of side play"
                    ),
                    correctIndex = 1,
                    explanation = "Castle nuts on the Pitman arm and steering linkage must be secured with cotter pins to prevent catastrophic detachment."
                )
            ),
            InspectionComponent(
                id = "drag-link",
                zone = InspectionZone.ENGINE_COMPARTMENT,
                name = "Drag Link & Steering Knuckle",
                systemGroup = "Steering System",
                xNormalized = 0.285f,
                yNormalized = 0.73f,
                zDepth = 0.3f,
                fmcsaCriteria = "Inspect drag link tube and ball joints connecting Pitman arm to steering knuckle. Check for straightness, cracks, secure clamps, and cotter pins.",
                criticalFailurePoint = "Bent drag link or missing cotter pin on steering knuckle arm.",
                isAutoFailIfMissed = true,
                mandatoryVerbatimPhrases = listOf(
                    "Drag link is straight, not bent, cracked, or welded",
                    "Ball sockets are greased and not worn or loose",
                    "Castle nuts and cotter pins are installed and tight"
                ),
                quiz = ComponentQuiz(
                    question = "What indicates a dangerously worn drag link socket during steering inspection?",
                    options = listOf(
                        "Fresh chassis grease around the rubber boot",
                        "Free play or movement in the ball joint socket when steering wheel is rocked",
                        "A black painted tube surface",
                        "Locking collar with torque stripe"
                    ),
                    correctIndex = 1,
                    explanation = "Any noticeable play or looseness in the drag link ball sockets indicates severe wear that can lead to steering failure."
                )
            ),
            InspectionComponent(
                id = "eng_belts_hoses",
                zone = InspectionZone.ENGINE_COMPARTMENT,
                name = "Belts & Hoses",
                systemGroup = "General Engine",
                xNormalized = 0.58f,
                yNormalized = 0.45f,
                zDepth = 0.5f,
                fmcsaCriteria = "Check all belts for snugness (no more than 1/2 to 3/4 inch deflection) and no cracks, frays, or splits. Check hoses for cuts, leaks, and secure clamps.",
                criticalFailurePoint = "Belt frayed through more than 50% or swollen cracked radiator hose.",
                isAutoFailIfMissed = false,
                mandatoryVerbatimPhrases = listOf(
                    "Belts have no more than 1/2 to 3/4 inch play",
                    "Belts are not frayed, cracked, or slipping",
                    "Hoses are securely clamped with no splits or coolant leaks"
                ),
                quiz = ComponentQuiz(
                    question = "What is the maximum allowable play when pressing the middle of an engine drive belt?",
                    options = listOf(
                        "1/8 inch",
                        "1/2 to 3/4 inch",
                        "1.5 inches",
                        "2 inches"
                    ),
                    correctIndex = 1,
                    explanation = "Standard FMCSA criteria dictates belt tension must allow no more than 1/2 to 3/4 inch play at the center of the belt."
                )
            ),

            // ==========================================
            // 4. FRONT BRAKES & STEERING AXLE (TAB 5)
            // ==========================================
            InspectionComponent(
                id = "brake-chamber",
                zone = InspectionZone.FRONT_BRAKES_AND_AXLE,
                name = "Brake Chamber & Slack Adjuster",
                systemGroup = "Front Brakes & Axle",
                xNormalized = 0.61f,
                yNormalized = 0.72f,
                zDepth = 0.5f,
                fmcsaCriteria = "Brake chamber securely mounted with mounting clamp tight, no dents, cracks, or air leaks. Slack adjuster and pushrod: clevis pin and cotter pin present and secure. With brakes released and wheels chocked, pull by hand—pushrod must not move more than 1 inch.",
                criticalFailurePoint = "Pushrod moves more than 1 inch by hand when brakes are released, cracked chamber, or active air leak.",
                isAutoFailIfMissed = true,
                mandatoryVerbatimPhrases = listOf(
                    "Brake chamber securely mounted with clamp tight and no dents or air leaks",
                    "Pushrod and slack adjuster straight, not bent, broken, or missing parts",
                    "Clevis pin and cotter pin are in place and secure",
                    "With brakes released and wheels chocked, pushrod moves no more than 1 inch when pulled by hand"
                ),
                quiz = ComponentQuiz(
                    question = "When checking manual slack adjuster travel by hand with brakes released and wheels chocked, what is the maximum pushrod movement allowed?",
                    options = listOf(
                        "No more than 1 inch (approximately 2.5 cm)",
                        "At least 2 to 3 inches of loose movement",
                        "Pushrod must not move at all (0 inches)",
                        "Up to 4 inches of pushrod stroke"
                    ),
                    correctIndex = 0,
                    explanation = "With the service brakes released and wheels chocked, pull the pushrod firmly by hand. It must move no more than about 1 inch (standard stroke chamber)."
                )
            ),
            InspectionComponent(
                id = "front_brake_linings",
                zone = InspectionZone.FRONT_BRAKES_AND_AXLE,
                name = "Brake Drum & Linings",
                systemGroup = "Front Brakes & Axle",
                xNormalized = 0.48f,
                yNormalized = 0.76f,
                zDepth = 0.5f,
                fmcsaCriteria = "Brake drums: no cracks, dents, holes, or deep heat grooving. Zero oil or grease contamination from hub seal. Brake linings: minimum thickness 1/4 inch, not dangerously thin or loose on brake shoes.",
                criticalFailurePoint = "Linings worn under 1/4 inch, cracked drum, or friction material contaminated with hub grease/oil.",
                isAutoFailIfMissed = true,
                mandatoryVerbatimPhrases = listOf(
                    "Brake drums have no cracks, dents, or deep grooves",
                    "No oil or grease contamination from inner hub seal onto drums or linings",
                    "Brake linings are not worn dangerously thin (minimum thickness 1/4 inch)",
                    "Friction linings are securely riveted or bonded to brake shoes"
                ),
                quiz = ComponentQuiz(
                    question = "What is the standard FMCSA minimum thickness for brake drum friction linings on a commercial vehicle?",
                    options = listOf(
                        "1/16 inch",
                        "1/4 inch (minimum thickness)",
                        "1/2 inch",
                        "3/4 inch"
                    ),
                    correctIndex = 1,
                    explanation = "Brake linings must not be dangerously thin; FMCSA criteria standard requires a minimum thickness of 1/4 inch."
                )
            ),
            InspectionComponent(
                id = "steer-tire",
                zone = InspectionZone.FRONT_BRAKES_AND_AXLE,
                name = "Steer Tire",
                systemGroup = "Front Brakes & Axle",
                xNormalized = 0.875f,
                yNormalized = 0.74f,
                zDepth = 0.2f,
                fmcsaCriteria = "Minimum 4/32” tread depth in every major groove across the surface. ABC Check: No Abrasions, Bulges, or Cuts on sidewalls or tread face. Zero exposed cords or fabric. No recapped/retreaded tires permitted on steer axle. Check cold inflation pressure with a tire pressure gauge; valve stem capped.",
                criticalFailurePoint = "Tread depth under 4/32\", recapped tire on steer axle, bulge/cut with exposed fabric, or flat tire.",
                isAutoFailIfMissed = true,
                mandatoryVerbatimPhrases = listOf(
                    "Minimum 4/32 inch tread depth in every major groove across the surface",
                    "ABC Check: No Abrasions, Bulges, or Cuts on tread or sidewalls",
                    "Zero exposed fabric or ply cords",
                    "Virgin rubber only; no recapped or retreaded tires on the steer axle",
                    "Check inflation pressure with a tire gauge; valve stem has tight cap and no leaks"
                ),
                quiz = ComponentQuiz(
                    question = "What are the two mandatory requirements for steer axle tires regarding tread depth and recaps?",
                    options = listOf(
                        "Minimum 2/32\" tread depth and recaps are allowed on steering axles",
                        "Minimum 4/32\" tread depth in every major groove and NO recaps/retreads allowed on steer axle",
                        "Minimum 3/32\" tread depth and must be siped",
                        "Minimum 6/32\" tread depth with dual valve stems"
                    ),
                    correctIndex = 1,
                    explanation = "Front steer tires require at least 4/32 inch tread depth in every major groove, and FMCSA strictly prohibits recapped or retreaded tires on the front steer axle."
                )
            ),
            InspectionComponent(
                id = "front_rim_hub_seal",
                zone = InspectionZone.FRONT_BRAKES_AND_AXLE,
                name = "Rim & Hub Oil Seal",
                systemGroup = "Front Brakes & Axle",
                xNormalized = 0.91f,
                yNormalized = 0.68f,
                zDepth = 0.2f,
                fmcsaCriteria = "Wheel rim: no cracks, bends, or illegal repair welds. Lug nuts: all 10 present, tight, with no rust streaks (rust indicates looseness on steel rims) or white powder (aluminum). Hub oil seal: sight glass clean with proper oil level visible between Add and Full marks; zero active leaks on inner or outer wheel seal.",
                criticalFailurePoint = "Cracked rim, illegal weld, loose/missing lug nuts, or leaking hub seal saturating brake linings.",
                isAutoFailIfMissed = true,
                mandatoryVerbatimPhrases = listOf(
                    "Rim has no cracks, bends, or illegal welds",
                    "All lug nuts are present and tight",
                    "No rust streaks or trails radiating from lug nuts indicating looseness",
                    "Hub oil seal has no leaks and proper oil level visible in sight glass",
                    "Hub cap rubber fill plug is intact and leak-free"
                ),
                quiz = ComponentQuiz(
                    question = "What does a rust streak radiating out from a lug nut on a steel wheel indicate to an inspector?",
                    options = listOf(
                        "The wheel was freshly washed with acid cleaner",
                        "The lug nut is loose and rocking against the wheel rim",
                        "The wheel bearing has overheated",
                        "The tire is overinflated"
                    ),
                    correctIndex = 1,
                    explanation = "Rust streaks (or shiny metal rings/powder) radiating from lug nuts indicate that the nut is loose and vibrating against the wheel rim."
                )
            ),

            // ==========================================
            // 3. COUPLING & WALKAROUND
            // ==========================================
            InspectionComponent(
                id = "coupling_glad_hands",
                zone = InspectionZone.COUPLING_WALKAROUND,
                name = "Glad Hands & Air / Electric Lines",
                systemGroup = "Coupling",
                xNormalized = 0.35f,
                yNormalized = 0.28f,
                zDepth = 0.3f,
                fmcsaCriteria = "Air lines securely connected with rubber grommets intact, no cuts or audible air leaks. Electric cord locked with safety latch. Lines suspended off catwalk (no dragging).",
                criticalFailurePoint = "Chafed or tangled air lines dragging against catwalk; damaged glad hand seal with audible air leak.",
                isAutoFailIfMissed = true,
                mandatoryVerbatimPhrases = listOf(
                    "Glad hand rubber seals/grommets are not cracked, dry-rotted, or leaking air",
                    "Electrical cable locked securely with safety latch",
                    "Air lines and electric cable not chafed, spliced, or dragging on catwalk"
                ),
                quiz = ComponentQuiz(
                    question = "What must you check on the glad hand connections between tractor and trailer?",
                    options = listOf(
                        "The rubber seals (grommets) are soft, free of cracks, and not leaking air",
                        "They must be lubricated with silicone grease",
                        "They must be painted blue and green",
                        "They must have pressure relief pop-off valves"
                    ),
                    correctIndex = 0,
                    explanation = "Glad hand rubber seals must be checked to ensure they are clean, free of cracks, dry rot, and not leaking air."
                )
            ),
            InspectionComponent(
                id = "coupling_fifth_wheel",
                zone = InspectionZone.COUPLING_WALKAROUND,
                name = "Fifth Wheel Skid Plate & Apron",
                systemGroup = "Coupling",
                xNormalized = 0.50f,
                yNormalized = 0.42f,
                zDepth = 0.5f,
                fmcsaCriteria = "Skid plate properly greased, securely mounted to frame. Check that there is NO space between the upper trailer apron and the fifth wheel skid plate.",
                criticalFailurePoint = "Gap between apron and fifth wheel (indicates high hitch).",
                isAutoFailIfMissed = true,
                mandatoryVerbatimPhrases = listOf(
                    "Fifth wheel skid plate is properly greased",
                    "No space or gap between trailer apron and skid plate",
                    "Pivot pins and brackets are secure with no missing pins"
                ),
                quiz = ComponentQuiz(
                    question = "What does daylight visible between the trailer apron and fifth wheel skid plate indicate?",
                    options = listOf(
                        "Proper air circulation under the fifth wheel",
                        "Dangerous high hitch—trailer may disconnect upon release",
                        "Tire over-inflation",
                        "The kingpin is too short"
                    ),
                    correctIndex = 1,
                    explanation = "There must be zero space between the trailer apron and skid plate. A gap means the kingpin may be resting on top of the jaws rather than locked."
                )
            ),
            InspectionComponent(
                id = "coupling_kingpin_jaws",
                zone = InspectionZone.COUPLING_WALKAROUND,
                name = "Kingpin & Locking Jaws",
                systemGroup = "Coupling",
                xNormalized = 0.52f,
                yNormalized = 0.55f,
                zDepth = 0.5f,
                fmcsaCriteria = "Look inside fifth wheel throat: locking jaws must be fully closed around the shank of the kingpin (not head). Release handle in locked position with safety latch engaged.",
                criticalFailurePoint = "Locking jaws not completely closed around kingpin shank.",
                isAutoFailIfMissed = true,
                mandatoryVerbatimPhrases = listOf(
                    "Look inside fifth wheel throat with flashlight",
                    "Locking jaws fully closed around the shank of the kingpin",
                    "Kingpin is not bent or cracked",
                    "Release lever is fully in and safety latch is engaged"
                ),
                quiz = ComponentQuiz(
                    question = "Where must the fifth wheel locking jaws close around the kingpin?",
                    options = listOf(
                        "Around the head of the kingpin",
                        "Around the shank (narrow portion) of the kingpin",
                        "Around the release handle pin",
                        "Around the base plate welds"
                    ),
                    correctIndex = 1,
                    explanation = "The locking jaws must close securely around the shank (narrow neck) of the kingpin, not the head, to ensure a solid lock."
                )
            ),
            InspectionComponent(
                id = "walk_fuel_tanks",
                zone = InspectionZone.COUPLING_WALKAROUND,
                name = "Fuel Tanks & Catwalk",
                systemGroup = "Walkaround",
                xNormalized = 0.28f,
                yNormalized = 0.65f,
                zDepth = 0.2f,
                fmcsaCriteria = "Fuel tanks securely mounted with straps intact and rubber gaskets in place. Fuel cap tight, not leaking. Catwalk clean and clear of loose items.",
                criticalFailurePoint = "Fuel leak from tank or cap.",
                isAutoFailIfMissed = false,
                mandatoryVerbatimPhrases = listOf(
                    "Fuel tank and mounting straps are securely mounted",
                    "Rubber cushions between tank and straps are intact",
                    "Fuel cap is on tight and gasket is not leaking",
                    "Catwalk and steps are solid, securely bolted, and clear of debris"
                ),
                quiz = ComponentQuiz(
                    question = "What must be verified regarding the fuel cap during the exterior walkaround?",
                    options = listOf(
                        "It must have a padlocked security clasp",
                        "It is on tight with seal/gasket intact and not leaking",
                        "It is painted reflective yellow",
                        "It has an automatic shutoff sensor"
                    ),
                    correctIndex = 1,
                    explanation = "Ensure fuel caps are on tight with the rubber gasket intact, with no signs of leaking fuel."
                )
            ),
            InspectionComponent(
                id = "walk_frame_crossmembers",
                zone = InspectionZone.COUPLING_WALKAROUND,
                name = "Frame, Crossmembers & Drive Shaft",
                systemGroup = "Chassis",
                xNormalized = 0.65f,
                yNormalized = 0.68f,
                zDepth = 0.6f,
                fmcsaCriteria = "Frame rails and crossmembers: no cracks, illegal welds, or bends. Drive shaft: straight, unbent, U-joints secure with no loose bolts.",
                criticalFailurePoint = "Cracked frame rail flange or illegal welding on heat-treated frame.",
                isAutoFailIfMissed = true,
                mandatoryVerbatimPhrases = listOf(
                    "Frame rails and crossmembers have no cracks, bends, or illegal holes/welds",
                    "Drive shaft is straight and free of foreign objects",
                    "Universal joints (U-joints) are securely mounted and not worn"
                ),
                quiz = ComponentQuiz(
                    question = "Are field welding repairs or drilling into top/bottom flanges of commercial vehicle frame rails permitted?",
                    options = listOf(
                        "Yes, as long as grade 8 bolts are used",
                        "No, welding or drilling into heat-treated frame flanges is illegal and weakens structure",
                        "Yes, permitted on crossmembers only",
                        "Only if inspected by state police within 48 hours"
                    ),
                    correctIndex = 1,
                    explanation = "Drilling holes into or welding on the top or bottom flanges of heat-treated truck frames is strictly prohibited under FMCSA safety rules."
                )
            ),

            // ==========================================
            // 4. IN-CAB & SAFETY EQUIPMENT
            // ==========================================
            InspectionComponent(
                id = "cab_seatbelt",
                zone = InspectionZone.IN_CAB_SAFETY,
                name = "Safety Belt",
                systemGroup = "In-Cab Safety",
                xNormalized = 0.25f,
                yNormalized = 0.45f,
                zDepth = 0.3f,
                fmcsaCriteria = "Securely mounted, belt latches and unlatches smoothly, webbing not frayed, ripped, or worn.",
                criticalFailurePoint = "Frayed seatbelt webbing or broken latch mechanism.",
                isAutoFailIfMissed = true,
                mandatoryVerbatimPhrases = listOf(
                    "Seatbelt is securely mounted to cab floor and pillar",
                    "Webbing is not frayed, ripped, or cut",
                    "Latches and unlatches properly, retracts smoothly"
                ),
                quiz = ComponentQuiz(
                    question = "What must you demonstrate to the examiner regarding the driver seatbelt?",
                    options = listOf(
                        "That it fits snugly, latches and releases securely, and has no frayed webbing",
                        "That it is made of bright orange nylon",
                        "That it has an audible alarm when unplugged",
                        "That it connects to the passenger seat anchor"
                    ),
                    correctIndex = 0,
                    explanation = "Examiners require candidates to physically latch and unlatch the seatbelt, demonstrating it holds firmly and the webbing is not frayed."
                )
            ),
            InspectionComponent(
                id = "cab_emergency_equipment",
                zone = InspectionZone.IN_CAB_SAFETY,
                name = "Emergency Safety Equipment",
                systemGroup = "Safety Gear",
                xNormalized = 0.70f,
                yNormalized = 0.78f,
                zDepth = 0.7f,
                fmcsaCriteria = "3 required items: (1) Fire extinguisher fully charged (gauge in green), securely mounted, rated 10 B:C minimum; (2) 3 red reflective triangles; (3) Spare electrical fuses (if circuit breakers not used).",
                criticalFailurePoint = "Missing fire extinguisher, discharged extinguisher, or missing reflective triangles.",
                isAutoFailIfMissed = true,
                mandatoryVerbatimPhrases = listOf(
                    "Fire extinguisher is fully charged, pin in place, rated 10 B:C or higher, securely mounted",
                    "Three red reflective triangles in good condition",
                    "Spare electrical fuses for all un-breakered circuits"
                ),
                quiz = ComponentQuiz(
                    question = "What three pieces of emergency equipment are legally required inside the cab of a commercial vehicle?",
                    options = listOf(
                        "First aid kit, road flares, and tow rope",
                        "Charged fire extinguisher (10 B:C), 3 reflective triangles, and spare electrical fuses",
                        "Jumper cables, flashlight, and tire chains",
                        "Hard hat, safety vest, and warning tape"
                    ),
                    correctIndex = 1,
                    explanation = "FMCSA § 393.95 mandates a properly rated charged fire extinguisher, 3 red reflective triangles, and spare electrical fuses."
                )
            ),
            InspectionComponent(
                id = "cab_gauges",
                zone = InspectionZone.IN_CAB_SAFETY,
                name = "Dashboard Gauges & Indicators",
                systemGroup = "In-Cab Instruments",
                xNormalized = 0.50f,
                yNormalized = 0.35f,
                zDepth = 0.5f,
                fmcsaCriteria = "Oil pressure gauge builds to normal operating range (20-60 PSI) within seconds of start. Temperature gauge rises smoothly (170-195°F). Voltmeter reads 13.5-14.5 volts with alternator charging.",
                criticalFailurePoint = "Oil pressure gauge not registering pressure after startup.",
                isAutoFailIfMissed = true,
                mandatoryVerbatimPhrases = listOf(
                    "Oil pressure gauge rises to normal operating range (20-60 PSI)",
                    "Water temperature gauge operates in normal range",
                    "Voltmeter shows alternator is charging (13.5 - 14.5 V)",
                    "Air pressure gauges build to governor cut-out (120-140 PSI)"
                ),
                quiz = ComponentQuiz(
                    question = "How quickly after engine start must oil pressure build to normal operating range?",
                    options = listOf(
                        "Within 3 to 5 minutes",
                        "Within seconds (typically 3 to 5 seconds, max 15 seconds)",
                        "Only after the truck reaches highway speed",
                        "Oil pressure is not monitored at idle"
                    ),
                    correctIndex = 1,
                    explanation = "Oil pressure must begin rising immediately upon startup; running an engine without oil pressure causes catastrophic engine failure."
                )
            ),
            InspectionComponent(
                id = "cab_horns_wipers",
                zone = InspectionZone.IN_CAB_SAFETY,
                name = "City / Air Horns, Wipers & Defroster",
                systemGroup = "In-Cab Controls",
                xNormalized = 0.42f,
                yNormalized = 0.22f,
                zDepth = 0.4f,
                fmcsaCriteria = "Test city (electric) horn. Test highway (air) horn. Check wiper arms have good spring tension, rubber blades not torn, windshield washer sprays, heater/defroster blows warm air.",
                criticalFailurePoint = "Neither horn operates, or defroster does not blow warm air on windshield.",
                isAutoFailIfMissed = false,
                mandatoryVerbatimPhrases = listOf(
                    "City electric horn operates loudly and clearly",
                    "Air highway horn operates properly",
                    "Windshield wipers and washers operate smoothly; blades are pliable and intact",
                    "Heater and defroster blow air strongly toward windshield"
                ),
                quiz = ComponentQuiz(
                    question = "Why must both the city horn and the air horn be demonstrated to the examiner?",
                    options = listOf(
                        "To verify the compressor governor pressure",
                        "To prove both electric city warning and high-volume highway warning devices function",
                        "To test the battery discharge rate",
                        "City horns are optional if air horns work"
                    ),
                    correctIndex = 1,
                    explanation = "Candidates must check that both horns work: the electric city horn for urban areas and the air horn for highway warning."
                )
            ),

            // ==========================================
            // 5. AIR BRAKE SYSTEM (COLA TEST)
            // ==========================================
            InspectionComponent(
                id = "air_brake_step1",
                zone = InspectionZone.AIR_BRAKE_SYSTEM,
                name = "Step 1: Static Leakage Test",
                systemGroup = "COLA Test",
                xNormalized = 0.25f,
                yNormalized = 0.35f,
                zDepth = 0.3f,
                fmcsaCriteria = "Build air pressure to governor cut-out (120-140 PSI). Shut off engine, turn key to ON position. Push in both parking valves (tractor and trailer). Release foot brake. Watch air gauges for 1 full minute.",
                criticalFailurePoint = "Air pressure loss exceeds 2 PSI in 1 minute (single) or 3 PSI (combination). Failure to time for 1 full minute is an automatic test fail.",
                isAutoFailIfMissed = true,
                mandatoryVerbatimPhrases = listOf(
                    "Engine shut off, key in ON position",
                    "Both parking brake valves pushed in to release brakes",
                    "Time for 1 full minute with foot off brake",
                    "Air loss must not exceed 2 PSI for single or 3 PSI for combination"
                ),
                quiz = ComponentQuiz(
                    question = "What is the maximum allowable air pressure loss during the 1-minute static leak test on a combination vehicle?",
                    options = listOf(
                        "No more than 2 PSI in 1 minute",
                        "No more than 3 PSI in 1 minute",
                        "No more than 5 PSI in 1 minute",
                        "10 PSI in 1 minute"
                    ),
                    correctIndex = 1,
                    explanation = "On a combination vehicle, air leakage with brakes released must not exceed 3 PSI in one minute."
                )
            ),
            InspectionComponent(
                id = "air_brake_step2",
                zone = InspectionZone.AIR_BRAKE_SYSTEM,
                name = "Step 2: Applied Pressure Leak Test",
                systemGroup = "COLA Test",
                xNormalized = 0.50f,
                yNormalized = 0.35f,
                zDepth = 0.5f,
                fmcsaCriteria = "With valves still pushed in, apply firm pressure to the service brake pedal (at least 90 PSI). Hold for 1 full minute after initial drop settles.",
                criticalFailurePoint = "Air pressure loss exceeds 3 PSI in 1 minute (single) or 4 PSI (combination).",
                isAutoFailIfMissed = true,
                mandatoryVerbatimPhrases = listOf(
                    "Apply firm foot brake pressure and hold",
                    "Time for 1 full minute after initial needle stabilization",
                    "Air loss must not exceed 3 PSI for single or 4 PSI for combination vehicle"
                ),
                quiz = ComponentQuiz(
                    question = "During the applied pressure leak test on a combination vehicle, what is the maximum allowable air loss in 1 minute?",
                    options = listOf(
                        "No more than 2 PSI",
                        "No more than 3 PSI",
                        "No more than 4 PSI in 1 minute",
                        "No more than 8 PSI"
                    ),
                    correctIndex = 2,
                    explanation = "With the service brake firmly applied, maximum allowable air loss for combination vehicles is 4 PSI in one minute (3 PSI for single vehicles)."
                )
            ),
            InspectionComponent(
                id = "air_brake_step3",
                zone = InspectionZone.AIR_BRAKE_SYSTEM,
                name = "Step 3: Low Air Warning Signal",
                systemGroup = "COLA Test",
                xNormalized = 0.75f,
                yNormalized = 0.35f,
                zDepth = 0.7f,
                fmcsaCriteria = "With the electrical key still ON, repeatedly fan (pump) the foot brake pedal to bleed air pressure down to ~60 PSI. Observe gauges.",
                criticalFailurePoint = "Low air warning light and buzzer fail to activate at or above 55-60 PSI.",
                isAutoFailIfMissed = true,
                mandatoryVerbatimPhrases = listOf(
                    "Fan foot brake pedal to bleed air down",
                    "Low air warning buzzer and visual red light must activate at or above 55 to 60 PSI",
                    "Verify both visual and audio warning signals work"
                ),
                quiz = ComponentQuiz(
                    question = "At what air pressure must the low air warning light and buzzer activate?",
                    options = listOf(
                        "At or before pressure drops below 55 - 60 PSI",
                        "At 90 PSI",
                        "At 20 PSI",
                        "Only when the engine stalls"
                    ),
                    correctIndex = 0,
                    explanation = "FMCSA regulations require low air warning devices (buzzer and light) to activate before pressure drops below 55-60 PSI (typically 60 PSI)."
                )
            ),
            InspectionComponent(
                id = "air_brake_step4",
                zone = InspectionZone.AIR_BRAKE_SYSTEM,
                name = "Step 4: Emergency Valve Pop-Out",
                systemGroup = "COLA Test",
                xNormalized = 0.50f,
                yNormalized = 0.65f,
                zDepth = 0.5f,
                fmcsaCriteria = "Continue fanning foot brake down. Between 20 and 40 PSI, the tractor protection valve (yellow diamond) and trailer supply valve (red octagon) must automatically pop out.",
                criticalFailurePoint = "Valves fail to pop out by 20 PSI, preventing emergency spring brake engagement.",
                isAutoFailIfMissed = true,
                mandatoryVerbatimPhrases = listOf(
                    "Continue fanning brake pedal to lower air pressure",
                    "Both parking brake valves must pop out between 20 and 40 PSI",
                    "Emergency spring brakes automatically engage"
                ),
                quiz = ComponentQuiz(
                    question = "At what air pressure range must the tractor and trailer parking brake valves pop out automatically?",
                    options = listOf(
                        "Between 80 and 100 PSI",
                        "Between 60 and 70 PSI",
                        "Between 20 and 40 PSI",
                        "Zero PSI only"
                    ),
                    correctIndex = 2,
                    explanation = "Between 20 and 40 PSI, the tractor protection valve and trailer supply valve must pop out, locking the emergency spring brakes."
                )
            )
        )
    }
}
