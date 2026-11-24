package com.example.data.repository

import com.example.data.models.CDLCategory
import com.example.data.models.Question

/**
 * Master Question Pool for CDL Combination Vehicles Endorsement.
 * Total: 120 official questions across all 4 key domain categories:
 * - Combination Vehicle Safety & Driving
 * - Combination Air Brake Systems
 * - Coupling & Uncoupling
 * - Inspecting Combination Vehicles
 */
val combinationVehiclesQuestions: List<Question> = listOf(
    Question(
        id = "cv_001",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "What is 'rollover risk' or the 'crack-the-whip' effect in combination vehicles?",
        options = listOf(
            "Tendency of rear trailers to swing wider and roll over during rapid lane shifts",
            "Vibration of tractor steer wheels caused by excessive payload over front axle",
            "Sudden engine stalling occurring when ascending steep mountain highway grades",
            "Lockup of trailer mechanical spring brakes under light service brake applications",
        ),
        correctIndex = 0,
        explanation = "Quick steering movements amplify as they travel back toward the rear trailer, increasing the risk of rolling over.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_002",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "Which trailer in a combination vehicle configuration is most likely to turn over during a sudden sharp movement?",
        options = listOf(
            "The lead trailer coupled directly to tractor fifth wheel",
            "The rear trailer of a double or triple combination setup",
            "The intermediate trailer pulled behind the first converter",
            "All coupled trailers share the exact same risk of rollover",
        ),
        correctIndex = 1,
        explanation = "Rearward amplification increases with each added trailer, making the last trailer the most prone to tipping over.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_003",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "What is off-tracking (or cheating) when turning a combination vehicle?",
        options = listOf(
            "Tendency of trailer tandem tires to slide laterally across wet or icy road surfaces",
            "Speedometer calibration error caused by differences in drive and trailer tire sizes",
            "Path of rear trailer wheels following closer to curb than tractor front steer wheels",
            "Rebound bounce of trailer chassis suspension when coming to a complete highway stop",
        ),
        correctIndex = 2,
        explanation = "Off-tracking occurs because rear wheels do not follow the exact track of the front wheels, swinging further inward around corners.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_004",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "Which combination vehicle setup will experience the GREATEST amount of off-tracking?",
        options = listOf(
            "A standard highway tractor pulling twin 28-foot double trailers",
            "A straight delivery truck towing a short 14-foot utility trailer",
            "An empty bobtail tractor operating with zero semitrailers attached",
            "A long wheelbase tractor pulling a single 53-foot semitrailer",
        ),
        correctIndex = 3,
        explanation = "Longer distances between the kingpin and trailer axles increase the degree of inward off-tracking.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_005",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "How should you steer when making a wide right turn with a long combination vehicle?",
        options = listOf(
            "Drive straight out into intersection before turning right, keeping trailer near curb",
            "Turn wide to the left into oncoming traffic lanes before beginning your right turn",
            "Keep the trailer rear wheels tight against the curb throughout the entire right turn",
            "Swing the tractor into the opposite traffic lane to the right before entering corner",
        ),
        correctIndex = 0,
        explanation = "Drive straight out into the intersection before turning right to prevent the trailer wheels from clipping the curb or street signs.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_006",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "What is a trailer jackknife?",
        options = listOf(
            "Tractor drive wheels lock and cab swings around toward trailer side panel",
            "Trailer wheels lock up and trailer slides sideways out of line with tractor",
            "Trailer kingpin uncouples completely from fifth wheel locking jaw assembly",
            "Trailer landing gear legs collapse downward onto highway while in motion",
        ),
        correctIndex = 1,
        explanation = "A trailer jackknife occurs when trailer drive or tandem wheels lock up, causing the trailer to slide sideways.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_007",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "What is a tractor jackknife?",
        options = listOf(
            "Trailer wheels lock and the rear trailer swings sideways across adjacent lanes",
            "Trailer landing gear strikes railroad track crossing while crossing at angle",
            "Tractor drive wheels lock and rear of tractor swings around toward the trailer",
            "Fifth wheel slide locking pins shear off, allowing skid plate to slide back",
        ),
        correctIndex = 2,
        explanation = "Tractor jackknifes happen when the tractor drive wheels lose traction and lock, swinging the cab around toward the trailer.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_008",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "What is the best way to stop a trailer jackknife skid?",
        options = listOf(
            "Apply the trailer hand valve (trolley brake) with maximum pneumatic pressure",
            "Yank the red trailer supply knob out immediately to engage emergency springs",
            "Downshift transmission two gear steps and press hard on engine accelerator",
            "Release the service brakes so trailer wheels roll again and regain traction",
        ),
        correctIndex = 3,
        explanation = "Releasing the brakes unlocks the trailer wheels, allowing them to roll again and straighten out behind the tractor.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_009",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "Why should you NEVER use the trailer hand valve (trolley bar) to correct a trailer skid?",
        options = listOf(
            "Applying hand valve locks trailer wheels, which caused the skid in the first place",
            "Applying hand valve cuts off all electric power to trailer exterior signal lights",
            "Hand valve dumps all compressed air directly from tractor primary air reservoir",
            "Hand valve forces the tractor protection valve into emergency pop-out position",
        ),
        correctIndex = 0,
        explanation = "Using the trailer hand brake applies air pressure to the locked trailer wheels, worsening the skid rather than fixing it.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_010",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "What is a 'bobtail' tractor?",
        options = listOf(
            "A tractor pulling two short 28-foot pup trailer units",
            "A tractor operating on highway with no trailer attached",
            "A combination vehicle transporting hazardous class cargo",
            "A tandem axle tractor experiencing spring brake failure",
        ),
        correctIndex = 1,
        explanation = "A bobtail tractor is a standalone tractor unit driving without a semitrailer attached.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_011",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "How does braking distance of a bobtail tractor compare to a fully loaded combination vehicle?",
        options = listOf(
            "A bobtail stops in half the distance required by a fully loaded combination rig",
            "Both vehicles require the exact same stopping distance under all road conditions",
            "A bobtail can take longer to stop due to lack of drive axle weight for traction",
            "A bobtail stops instantly with zero brake lag because of its short wheel span",
        ),
        correctIndex = 2,
        explanation = "Empty bobtail tractors lack weight over the drive wheels, which can cause poor tire traction and longer stopping distances.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_012",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "What is the main function of the tractor protection valve?",
        options = listOf(
            "Regulates air compressor cycling speed during steep continuous downgrade runs",
            "Adjusts slack adjuster travel automatically across all combination brake shoes",
            "Prevents tractor drive wheels from spinning during sudden acceleration on ice",
            "Keeps tractor air supply safe if trailer breaks away or develops a severe leak",
        ),
        correctIndex = 3,
        explanation = "The tractor protection valve retains air in the tractor system if the trailer disconnects or experiences major pressure loss.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_013",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "How is the tractor protection valve controlled inside the cab?",
        options = listOf(
            "By the red 8-sided octagonal trailer air supply knob",
            "By the yellow 8-sided diamond parking brake knob",
            "By foot pedal application pressure on treadle valve",
            "By the blue electric engine compression brake switch",
        ),
        correctIndex = 0,
        explanation = "The red octagonal push-pull knob on the dash controls the trailer air supply valve and tractor protection system.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_014",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "What position should the trailer air supply control (red knob) be in when normal driving with a coupled trailer?",
        options = listOf(
            "Pulled OUT (emergency parking position)",
            "Pushed IN (normal air charging position)",
            "Turned halfway between in and out stops",
            "Unscrewed completely from dash mounting",
        ),
        correctIndex = 1,
        explanation = "Pushing the red knob IN supplies air to charge the trailer air tanks and release the trailer emergency spring brakes.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_015",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "What happens when you pull OUT the red trailer air supply control knob?",
        options = listOf(
            "Compressed air is pumped into trailer emergency lines at maximum PSI",
            "Tractor service brakes apply with 100 PSI pressure on all drive wheels",
            "Trailer air supply is shut off and trailer emergency spring brakes apply",
            "Air compressor governor cuts in automatically to fill primary reservoir",
        ),
        correctIndex = 2,
        explanation = "Pulling out the red knob cuts air supply to the trailer line, causing trailer emergency spring brakes to set.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_016",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "At what air pressure range should the trailer supply valve (red knob) automatically pop out?",
        options = listOf(
            "Between 50 and 65 PSI",
            "Between 70 and 85 PSI",
            "Between 5 and 15 PSI",
            "Between 20 and 45 PSI",
        ),
        correctIndex = 3,
        explanation = "If air pressure drops dangerously low (20–45 PSI), the trailer supply valve automatically pops out to set trailer emergency brakes.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_017",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "What is the purpose of the trailer hand valve (also known as trolley bar or Johnson bar)?",
        options = listOf(
            "To operate trailer service brakes independently from tractor service brakes",
            "To lock the tractor fifth wheel jaws securely around the trailer kingpin",
            "To bleed moisture and oil sludge automatically from trailer emergency tanks",
            "To adjust tire pressure evenly across all combination vehicle wheel hubs",
        ),
        correctIndex = 0,
        explanation = "The trailer hand valve controls trailer service brakes manually, independent of the tractor service brake pedal.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_018",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "Should the trailer hand valve be used for parking a combination vehicle?",
        options = listOf(
            "Yes, using hand valve preserves mechanical spring brake tension inside chambers",
            "No, never use hand valve for parking because air can leak away and release brakes",
            "Yes, hand valves provide much stronger holding force than parking spring brakes",
            "No, hand valves must only be used for parking when loaded with heavy liquid cargo",
        ),
        correctIndex = 1,
        explanation = "Never use the trailer hand valve for parking; service brake pressure will bleed off over time, releasing the brakes.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_019",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "What are trailer air line gladhands?",
        options = listOf(
            "Heavy steel safety latch hooks connecting trailer landing gear to tractor frame",
            "Hydraulic coupler valves linking power steering fluid lines to trailer dollies",
            "Interlocking coupling devices used to join tractor air hoses to trailer air hoses",
            "Electrical terminal pin plugs delivering 12-volt battery power to cargo lights",
        ),
        correctIndex = 2,
        explanation = "Gladhands are quick-connect metal couplers with rubber seals that join tractor and trailer air supply lines.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_020",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "What standard color coding is used for combination vehicle air lines and gladhands?",
        options = listOf(
            "Yellow for Service line (control) and Green for Emergency line (supply)",
            "Red for Service line (control) and Blue for Emergency line (supply)",
            "Black for Service line (control) and White for Emergency line (supply)",
            "Blue for Service line (control) and Red for Emergency line (supply)",
        ),
        correctIndex = 3,
        explanation = "Standard color coding marks the Service line (control line) in Blue and the Emergency line (supply line) in Red.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_021",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "What is the primary function of the blue Service line in a combination vehicle?",
        options = listOf(
            "Carries air pressure controlled by pedal or hand valve to apply trailer brakes",
            "Supplies constant tank pressure to charge trailer reservoirs and release springs",
            "Directs return air back from trailer foundation chambers to engine intake",
            "Carries electrical control signals to activate anti-lock brake modulators",
        ),
        correctIndex = 0,
        explanation = "The blue service line delivers control pressure signals to operate trailer service brakes when the driver steps on the pedal.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_022",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "What is the primary function of the red Emergency supply line in a combination vehicle?",
        options = listOf(
            "Carries modulated control air signals directly from the foot brake treadle valve",
            "Supplies air to fill trailer tanks and holds off trailer emergency spring brakes",
            "Directs hydraulic fluid pressure to the trailer converter dolly steering linkage",
            "Vents heated exhaust air from trailer foundation brake drums during downhill runs",
        ),
        correctIndex = 1,
        explanation = "The red emergency line charges trailer storage reservoirs and supplies pressure to hold spring brakes in the released position.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_023",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "What happens if you cross (swap) the service and emergency gladhand connections when coupling?",
        options = listOf(
            "Tractor engine will overheat immediately due to air compressor backpressure",
            "Tractor steer axle brakes will lock permanently in full emergency application",
            "Trailer emergency spring brakes will not release, or service brakes will fail",
            "Air compressor governor will purge all stored air out through safety valve",
        ),
        correctIndex = 2,
        explanation = "Swapping gladhand hoses prevents proper pressure distribution, leaving trailer spring brakes locked or service brakes non-functional.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_024",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "How can you test if air is flowing properly through a double or triple trailer combination system?",
        options = listOf(
            "Disconnect the blue service gladhand and listen for continuous high pressure air",
            "Apply the foot pedal for three minutes with the diesel engine running at idle",
            "Verify that tractor low air warning buzzer shuts off within fifteen seconds",
            "Open the shut-off valve at rear of the last trailer and listen for escaping air",
        ),
        correctIndex = 3,
        explanation = "Open the rear shut-off cock on the last trailer to verify air has traveled through all lines before closing the valve.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_025",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "What position must trailer line shut-off valves (cut-out cocks) be in at the rear of the LAST trailer in a combination?",
        options = listOf(
            "In the fully CLOSED position",
            "In the fully OPEN position",
            "Halfway open to vent lines",
            "Open with dummy plugs tied",
        ),
        correctIndex = 0,
        explanation = "Shut-off valves on the rear of the final trailer must be closed so compressed air does not escape into the air.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_026",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "What position must trailer line shut-off valves be in between trailers on a double trailer setup?",
        options = listOf(
            "In the fully CLOSED position",
            "In the fully OPEN position",
            "Halfway open to vent lines",
            "Closed with dummy couplers",
        ),
        correctIndex = 1,
        explanation = "Shut-off valves located between active trailers must remain open to allow continuous air passage to trailing units.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_027",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "What is the maximum allowable static air leakage rate for a combination vehicle (engine off, brakes released)?",
        options = listOf(
            "No more than 2 PSI in one minute",
            "No more than 4 PSI in one minute",
            "No more than 3 PSI in one minute",
            "No more than 5 PSI in one minute",
        ),
        correctIndex = 2,
        explanation = "For combination vehicles, static leakage (engine off, brakes released) must not exceed 3 PSI in one minute.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_028",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "What is the maximum allowable applied air leakage rate for a combination vehicle (engine off, foot pedal fully depressed)?",
        options = listOf(
            "No more than 3 PSI in one minute",
            "No more than 5 PSI in one minute",
            "No more than 6 PSI in one minute",
            "No more than 4 PSI in one minute",
        ),
        correctIndex = 3,
        explanation = "For combination vehicles under applied pressure (engine off, pedal depressed), leakage must not exceed 4 PSI per minute.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_029",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "Before backing under a trailer to couple, what critical height check must you perform?",
        options = listOf(
            "Trailer apron should be slightly lower than center of fifth wheel skid plate",
            "Trailer apron should be at least two inches higher than fifth wheel top plate",
            "Trailer landing gear feet must be suspended four inches off the ground level",
            "Trailer kingpin must be tilted upward at a thirty degree forward angle position",
        ),
        correctIndex = 0,
        explanation = "The trailer apron should contact the fifth wheel plate slightly below center so the tractor lifts the trailer slightly as it backs under.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_030",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "What happens if the trailer is positioned TOO HIGH before backing under it to couple?",
        options = listOf(
            "Fifth wheel locking jaws will lock prematurely around the kingpin collar",
            "Kingpin may slide over fifth wheel plate and strike or crush tractor cab",
            "Trailer emergency spring brakes will release and allow trailer to roll",
            "Landing gear legs will sink into pavement and snap cross bracing struts",
        ),
        correctIndex = 1,
        explanation = "If the trailer is too high, the kingpin can slide over the top of the fifth wheel plate and strike the back of the tractor cab.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_031",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "Where should the tractor be aligned when preparing to back under a trailer?",
        options = listOf(
            "Back swiftly until kingpin slams hard into the closed fifth wheel locking jaws",
            "Drive forward two feet to align fifth wheel ramps with trailer cargo doors",
            "Stop when fifth wheel plate touches trailer apron, then check trailer height",
            "Lower trailer landing gear until tractor drive tires lift completely off pavement",
        ),
        correctIndex = 2,
        explanation = "Position the tractor directly straight in front of the trailer to ensure smooth kingpin alignment with fifth wheel jaws.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_032",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "When backing the tractor under the trailer, when should you stop backing for the first time?",
        options = listOf(
            "Ensure that trailer cargo compartment is loaded to maximum registered capacity",
            "Verify that tractor drive axle differential lock is engaged in full lock mode",
            "Check that tractor low air pressure warning light remains lit on instrument panel",
            "Check that trailer kingpin is aligned squarely with fifth wheel throat opening",
        ),
        correctIndex = 3,
        explanation = "Stop when the fifth wheel contacts the trailer apron to check alignment and connect air lines before final coupling lock.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_033",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "Why should you connect air lines to the trailer BEFORE backing completely under the trailer kingpin lock?",
        options = listOf(
            "To charge trailer reservoirs and set emergency brakes so trailer cannot roll back",
            "To warm up the gladhand rubber seals before they lock into the fifth wheel throat",
            "To discharge all stored air from the tractor secondary reservoir before coupling",
            "To test the trailer clearance and marker lamps before backing under the kingpin",
        ),
        correctIndex = 0,
        explanation = "Connecting air supply allows you to charge trailer tanks and set trailer brakes, preventing the trailer from moving back.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_034",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "How do you know that the fifth wheel locking jaws have properly engaged around the kingpin?",
        options = listOf(
            "Release lever pulled out completely with safety catch hanging loose over bracket",
            "Locking jaws closed around shank, release handle locked, and zero gap between plates",
            "Trailer apron resting two inches above the fifth wheel skid plate pivot pins",
            "Red trailer air supply knob popping out automatically with an audible exhaust hiss",
        ),
        correctIndex = 1,
        explanation = "Check visually that fifth wheel jaws are closed around the kingpin, the safety latch is engaged, and no gap exists between plates.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_035",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "How do you perform a physical 'tug test' after coupling a tractor to a trailer?",
        options = listOf(
            "Release all brakes and accelerate rapidly to 15 MPH to check coupling grip",
            "Turn steering wheel sharply from lock to lock while monitoring fifth wheel jaws",
            "Keep trailer brakes locked, put tractor in low gear, and pull gently forward",
            "Apply foot brake pedal firmly and shift transmission into reverse at high idle",
        ),
        correctIndex = 2,
        explanation = "Perform a tug test by pulling forward gently in low gear against the locked trailer brakes to verify coupling security.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_036",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "When inspecting the fifth wheel coupling visually, how much space should be visible between the upper apron and lower fifth wheel plate?",
        options = listOf(
            "At least one-half inch gap for grease expansion",
            "Approximately one inch space for suspension bounce",
            "Two inches clearance to allow unrestricted turning",
            "Zero space (no gap between apron and fifth wheel plate)",
        ),
        correctIndex = 3,
        explanation = "There should be zero space between the trailer apron and lower fifth wheel plate; any gap indicates improper coupling.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_037",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "Which part of the kingpin should the fifth wheel locking jaws clamp around?",
        options = listOf(
            "Around the narrow shank (neck) of kingpin",
            "Around the wide lower head of kingpin base",
            "Around the flat upper collar of the apron",
            "Around the grease relief groove at kingpin tip",
        ),
        correctIndex = 0,
        explanation = "Fifth wheel jaws must lock firmly around the narrow neck (shank) of the trailer kingpin.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_038",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "When uncoupling a trailer, where should you park the combination vehicle?",
        options = listOf(
            "On a steep downhill grade so tractor pulls away from trailer freely",
            "On a firm, level surface capable of supporting landing gear weight",
            "On soft dirt or muddy ground where tires cannot slip during uncoupling",
            "Across multiple lanes of traffic to ensure adequate turnaround space",
        ),
        correctIndex = 1,
        explanation = "Park on hard, level pavement to prevent landing gear legs from sinking into soft surfaces or shifting.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_039",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "When uncoupling an UNLOADED trailer, how should landing gear be lowered?",
        options = listOf(
            "Crank landing gear down until feet are two inches above pavement before pulling away",
            "Lower landing gear only after the tractor has driven completely out from under trailer",
            "Lower landing gear until firm on ground, then crank extra turns to lift weight slightly",
            "Drop landing gear legs in high gear and let trailer crash down on fifth wheel ramps",
        ),
        correctIndex = 2,
        explanation = "Lower landing gear until it touches ground firmly, adding a couple extra turns to take weight off tractor suspension.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_040",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "Why should you place wooden pads underneath landing gear legs on soft asphalt or dirt ground?",
        options = listOf(
            "To reduce friction between landing gear pads and fifth wheel mounting brackets",
            "To insulate electrical landing gear motors from road salt corrosion and moisture",
            "To allow the trailer apron to tilt forward during rapid commercial uncoupling",
            "To prevent landing gear feet from sinking into soft ground under trailer weight",
        ),
        correctIndex = 3,
        explanation = "Placing wooden blocks or pads under landing gear distributes weight and prevents legs from sinking into soft surfaces.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_041",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "What step follows disconnecting air and electric lines during trailer uncoupling?",
        options = listOf(
            "Attach gladhands to dummy couplers or dead-end plugs on the back of tractor cab",
            "Hang air hoses over tractor fuel tank steps where road wind blows dust clean",
            "Tie gladhands tightly together with metal bailing wire around exhaust stack",
            "Leave air lines dangling loosely on tractor catwalk grating during transit",
        ),
        correctIndex = 0,
        explanation = "After uncoupling air hoses, store gladhands on dummy couplers to keep dirt, water, and debris out of seals.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_042",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "When uncoupling, how far should you pull the tractor forward before stopping to check trailer landing gear stability?",
        options = listOf(
            "Drive forward 50 feet immediately and accelerate to normal highway road speed",
            "Pull forward 8 to 12 inches so fifth wheel clears apron but remains under trailer",
            "Back tractor up two feet to compress landing gear legs firmly into pavement",
            "Pull tractor forward until drive axles clear the rear trailer bumper bar",
        ),
        correctIndex = 1,
        explanation = "Pulling forward slightly leaves fifth wheel frame under trailer nose as safety backup while checking landing gear support.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_043",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "What position should the fifth wheel release handle be in when successfully uncoupled?",
        options = listOf(
            "In the fully CLOSED position with safety catch locked down",
            "Halfway pulled out with the release handle chain dangling",
            "In the fully OPEN position with safety latch disengaged",
            "Removed completely from the fifth wheel side plate bracket",
        ),
        correctIndex = 2,
        explanation = "Pull fifth wheel release lever out into open position so jaws remain open for next coupling.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_044",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "What must be done with electrical cable when uncoupling tractor from trailer?",
        options = listOf(
            "Leave electrical cable plugged into trailer and pull tractor forward slowly",
            "Cut the seven-way electrical cord flush with the trailer front bulkhead wall",
            "Wrap electrical cable around the trailer kingpin to protect internal brass pins",
            "Unplug electrical cable, store on tractor dummy plug, and close socket cover",
        ),
        correctIndex = 3,
        explanation = "Unplug electrical cable, store securely on tractor, and close spring-loaded trailer receptacle cover.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_045",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Inspecting Combination Vehicles",
        questionText = "When inspecting trailer landing gear after coupling, what gear ratio crank handle position should be secured for travel?",
        options = listOf(
            "Fully raised in HIGH gear position with crank handle secured in bracket",
            "Lowered two inches off pavement with crank handle left hanging loose",
            "Locked in low gear ratio position with crank handle extended sideways",
            "Secured halfway up with safety chains wrapped around cross bracing rods",
        ),
        correctIndex = 0,
        explanation = "Landing gear must be fully cranked up in high gear and crank handle locked into its storage bracket.",
        manualCitation = "FMCSA CDL Manual Section 6.5 - Inspecting Combinations"
    ),
    Question(
        id = "cv_046",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Inspecting Combination Vehicles",
        questionText = "What damage can occur if landing gear is NOT fully raised before driving?",
        options = listOf(
            "Trailer cargo will shift forward and overload front steering axle tires",
            "Landing gear legs can strike railroad tracks or dips and rip off trailer frame",
            "Trailer kingpin will unlatch from fifth wheel jaws during highway cruising",
            "Tractor air compressor will over-pressurize and burst air tank relief valves",
        ),
        correctIndex = 1,
        explanation = "Partially lowered landing gear can strike ground obstacles, causing severe frame and gear damage.",
        manualCitation = "FMCSA CDL Manual Section 6.5 - Inspecting Combinations"
    ),
    Question(
        id = "cv_047",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Inspecting Combination Vehicles",
        questionText = "When inspecting the fifth wheel assembly, what lubricant should be applied to the skid plate surface?",
        options = listOf(
            "Light penetrating oil or liquid spray lubricant",
            "Standard motor engine oil drained from crankcase",
            "Heavy-duty fifth wheel grease (chassis grease)",
            "Clean soapy water mixed with rubbing alcohol",
        ),
        correctIndex = 2,
        explanation = "Keep fifth wheel skid plate properly greased with heavy duty chassis grease to prevent steering friction binding.",
        manualCitation = "FMCSA CDL Manual Section 6.5 - Inspecting Combinations"
    ),
    Question(
        id = "cv_048",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Inspecting Combination Vehicles",
        questionText = "What safety feature prevents the fifth wheel release lever from opening accidentally while driving?",
        options = listOf(
            "Pneumatic air pressure lock cylinder inside cab console",
            "Electronic solenoid pin wired to tractor brake pedal",
            "Hydraulic backup check valve on fifth wheel mounting",
            "Mechanical safety latch / lock arm over release lever",
        ),
        correctIndex = 3,
        explanation = "A mechanical safety catch drops over or locks the fifth wheel release handle to prevent unintentional opening.",
        manualCitation = "FMCSA CDL Manual Section 6.5 - Inspecting Combinations"
    ),
    Question(
        id = "cv_049",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Inspecting Combination Vehicles",
        questionText = "During a combination vehicle pre-trip inspection, what should you inspect on trailer air lines?",
        options = listOf(
            "Check that air lines are free of leaks, chafing, or kinks, and supported properly",
            "Ensure air hoses are painted the exact same color as trailer cargo box sides",
            "Verify that hose outer jackets are soaked in diesel fuel to maintain flexibility",
            "Measure hose length to ensure at least four feet of slack drags on catwalk",
        ),
        correctIndex = 0,
        explanation = "Air hoses must be free of damage, sealed properly, and suspended safely above tractor cat walk.",
        manualCitation = "FMCSA CDL Manual Section 6.5 - Inspecting Combinations"
    ),
    Question(
        id = "cv_050",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Inspecting Combination Vehicles",
        questionText = "What is the proper way to test trailer service brakes during a pre-trip inspection?",
        options = listOf(
            "Accelerate to 35 MPH on highway and pull red emergency knob out sharply",
            "Drive forward at 5 MPH and apply trailer hand valve (trolley bar) only",
            "Hold service brake pedal down while vehicle is parked in neutral gear",
            "Chock trailer wheels and measure pushrod travel distance with engine off",
        ),
        correctIndex = 1,
        explanation = "Roll forward slowly (~5 MPH) and pull trailer hand valve to test that trailer service brakes engage and stop vehicle.",
        manualCitation = "FMCSA CDL Manual Section 6.5 - Inspecting Combinations"
    ),
    Question(
        id = "cv_051",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Inspecting Combination Vehicles",
        questionText = "How do you test trailer parking / emergency brakes during a pre-trip inspection?",
        options = listOf(
            "Hold foot pedal down for two minutes with the engine running at idle",
            "Apply tractor parking brake, release trailer brakes, and rev the engine",
            "Charge trailer, pull red knob OUT, and gently pull forward in low gear",
            "Open trailer rear drain petcock while the vehicle rolls slowly backward",
        ),
        correctIndex = 2,
        explanation = "Set trailer emergency brakes by pulling red supply knob OUT, then attempt to pull forward gently to verify holding power.",
        manualCitation = "FMCSA CDL Manual Section 6.5 - Inspecting Combinations"
    ),
    Question(
        id = "cv_052",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "What causes a combination vehicle to 'snakeswap' or fishtail while travelling on high-speed highways?",
        options = listOf(
            "Using high quality radial tires instead of bias ply tires on trailer tandems",
            "Operating with fully pressurized trailer air tanks in mild sunny weather",
            "Keeping fifth wheel slide mechanism locked in the forward operating stop",
            "Improper cargo weight balance, loose trailer suspension, or excessive speed",
        ),
        correctIndex = 3,
        explanation = "Uneven cargo loading, high speed, and worn trailer suspensions lead to dangerous trailer swaying (fishtailing).",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_053",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "Where should the heaviest weight of cargo be loaded inside a trailer box?",
        options = listOf(
            "Centered low over the axles and along the trailer center line",
            "Stacked high against the trailer front bulkhead wall panel",
            "Loaded exclusively along the rear trailer doors near the bumper",
            "Placed on either the left or right side wall to improve banking",
        ),
        correctIndex = 0,
        explanation = "Position heavy cargo low and centered over axles to maintain vehicle stability and prevent rollover risks.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_054",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "What is the danger of loading cargo too heavily toward the REAR of a trailer?",
        options = listOf(
            "Overloads front steering axle, making manual steering wheel rotation heavy",
            "Lightens tractor drive axle traction, causing steering instability and swaying",
            "Causes trailer fifth wheel kingpin to lift and strike tractor cab roof",
            "Trips tractor protection valve automatically into emergency exhaust mode",
        ),
        correctIndex = 1,
        explanation = "Rear-heavy loading reduces drive axle weight, weakening traction, steering control, and trailer stability.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_055",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "What is the danger of loading cargo too heavily toward the FRONT nose of a trailer?",
        options = listOf(
            "Lightens trailer tandem axles, causing trailer spring brakes to drag on road",
            "Causes trailer cargo floor to sag and drag along highway pavement center",
            "Overloads tractor drive and steer axles, causing heavy steering and tire wear",
            "Forces trailer emergency gladhand coupler to disconnect from supply line",
        ),
        correctIndex = 2,
        explanation = "Front-heavy loading places excessive weight on fifth wheel, overloading tractor axles and tires.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_056",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "What is the function of the trailer air storage reservoir?",
        options = listOf(
            "Supplies fuel oil directly to trailer auxiliary refrigeration cooling engines",
            "Collects exhaust fumes from tractor stack to warm up trailer cargo compartment",
            "Pressurizes hydraulic fluid used to operate trailer automatic landing gear legs",
            "Stores compressed air from tractor to operate trailer service and emergency brakes",
        ),
        correctIndex = 3,
        explanation = "Trailer air tanks store compressed air delivered from tractor compressor to apply trailer brakes.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_057",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "Why must trailer air tanks be drained regularly if not equipped with automatic drain valves?",
        options = listOf(
            "To remove moisture and compressor oil sludge that can freeze and cause brake failure",
            "To relieve excess air pressure build-up before parking combination vehicle overnight",
            "To calibrate the electronic anti-lock brake wheel speed sensors on trailer axles",
            "To prevent the trailer emergency spring brakes from engaging during long descents",
        ),
        correctIndex = 0,
        explanation = "Draining removes accumulated moisture and compressor oil, preventing ice blockage and valve damage.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_058",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "If the red emergency line breaks or disconnects while driving a combination vehicle, what will happen?",
        options = listOf(
            "Tractor engine stalls immediately and steering wheel locks into rigid stop",
            "Trailer emergency spring brakes apply automatically and tractor valve closes",
            "Trailer service brakes release completely, allowing trailer to roll away",
            "Tractor primary air reservoir vents all pressure to open air atmosphere",
        ),
        correctIndex = 1,
        explanation = "Pressure loss in the emergency line trips the tractor protection valve and engages trailer emergency spring brakes.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_059",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "If the blue service line breaks or disconnects while driving, when will air loss occur?",
        options = listOf(
            "Continuously as soon as line disconnects, dumping all air in seconds",
            "Only when vehicle speed drops below 20 MPH on city highway routes",
            "Only when driver applies service brake pedal or trailer hand valve",
            "Whenever transmission shifts gears between low and high road ranges",
        ),
        correctIndex = 2,
        explanation = "The blue service line pressurizes when service brakes are applied; air escapes when pedal or hand valve is used.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_060",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "What component on a trailer converts air pressure signals into actual mechanical pushrod movement?",
        options = listOf(
            "Quick release valve",
            "Treadle foot valve",
            "Relay supply valve",
            "Trailer brake chamber",
        ),
        correctIndex = 3,
        explanation = "Trailer brake chambers convert pneumatic pressure into mechanical force to rotate slack adjusters and apply brakes.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_061",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "What should you check regarding fifth wheel positioning before coupling a trailer?",
        options = listOf(
            "Shifts cargo payload weight distribution between tractor drive and steer axles",
            "Changes the height of the trailer landing gear legs relative to ground pavement",
            "Adjusts pneumatic air pressure delivered to trailer foundation brake chambers",
            "Modulates engine horsepower output delivered to tractor tandem rear drive hubs",
        ),
        correctIndex = 0,
        explanation = "The fifth wheel plate must tilt down at the rear so the trailer apron slides up smoothly onto it with jaws open.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_062",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "What is the purpose of sliding fifth wheels on tractors?",
        options = listOf(
            "Takes weight off tractor front steer axle and transfers it onto trailer wheels",
            "Puts more weight onto tractor front steer axle and off rear drive tandem axles",
            "Increases trailer off-tracking swing while turning around sharp street corners",
            "Trips the tractor protection valve into emergency breakaway shutoff position",
        ),
        correctIndex = 1,
        explanation = "Sliding fifth wheels allow drivers to shift weight between steer and drive axles and adjust cab-to-trailer clearance.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_063",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "What must you check after adjusting a sliding fifth wheel to a new position?",
        options = listOf(
            "Pneumatic air pressure cylinders locking against the fifth wheel top plate",
            "Electronic solenoid clips wired to the tractor ignition switch console",
            "Heavy steel lock pins engaging both tractor frame rail slide bracket racks",
            "Hydraulic friction clamp bands wrapped around the pivot trunnion bushings",
        ),
        correctIndex = 2,
        explanation = "Confirm that all slider lock pins are fully extended into frame rail rack slots to secure the fifth wheel.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_064",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "When backing under a trailer to couple, what speed should be used?",
        options = listOf(
            "Accelerate swiftly in reverse to slam fifth wheel into kingpin",
            "Coast backward in neutral while riding the foot brake pedal",
            "Back in reverse high range with engine running at 1800 RPM",
            "Back under slowly in the lowest available transmission gear",
        ),
        correctIndex = 3,
        explanation = "Back slowly in low gear to avoid striking the trailer kingpin hard or damaging coupling components.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_065",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "What can happen if you couple a tractor to a trailer with the fifth wheel slider pins disengaged?",
        options = listOf(
            "Fifth wheel assembly can slam forward or backward along tractor frame rails",
            "Trailer kingpin will shear off completely from the upper apron mounting plate",
            "Tractor steer axle tires will lift completely off the highway pavement surface",
            "Trailer emergency spring brakes will engage and blow out all tandem drive tires",
        ),
        correctIndex = 0,
        explanation = "Disengaged slider pins allow the entire fifth wheel and trailer to slam forward or backward along the frame.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_066",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "What is the function of the kingpin on a trailer?",
        options = listOf(
            "A pivot bolt connecting tractor steering knuckle to the front steering axle",
            "A steel pin beneath trailer apron that locks into fifth wheel jaws to couple",
            "A release lever locking the sliding tandem carriage underneath trailer frame",
            "A safety hook linking trailer emergency breakaway chains to the tow bar eye",
        ),
        correctIndex = 1,
        explanation = "The kingpin is the main steel coupling pin welded under the trailer apron that locks into the fifth wheel jaws.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_067",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "Before backing under a trailer, why should you visually check the trailer kingpin?",
        options = listOf(
            "Ensure kingpin is painted the exact same color as trailer walls",
            "Verify kingpin is lubricated with water-soluble silicone spray",
            "Check for cracks, bends, excessive wear, or structural damage",
            "Measure kingpin diameter to ensure it exceeds four inches width",
        ),
        correctIndex = 2,
        explanation = "Inspect kingpin and upper apron plate for cracks, bends, wear, or loose mounting bolts before coupling.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_068",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Inspecting Combination Vehicles",
        questionText = "When inspecting a trailer during pre-trip, what should you look for on trailer dual tires?",
        options = listOf(
            "Matching brand names, identical wheel weights, and chrome lug nut covers present",
            "Retread seams aligned in same direction, zero air valve caps, and shiny sidewalls",
            "Even tire wear patterns with at least 6/32 inch tread depth in every major groove",
            "Proper pressure, at least 2/32 inch tread, no cuts/bulges, and space between duals",
        ),
        correctIndex = 3,
        explanation = "Inspect trailer tires for inflation pressure, minimal 2/32 inch tread depth, cuts/bulges, and clearance between duals.",
        manualCitation = "FMCSA CDL Manual Section 6.5 - Inspecting Combinations"
    ),
    Question(
        id = "cv_069",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Inspecting Combination Vehicles",
        questionText = "What minimum tread depth is required on trailer tires?",
        options = listOf(
            "At least 2/32 inch in major grooves",
            "At least 4/32 inch in major grooves",
            "At least 1/32 inch in major grooves",
            "At least 6/32 inch in major grooves",
        ),
        correctIndex = 0,
        explanation = "Trailer tires (and drive tires) require a minimum tread depth of 2/32 inch (steer tires require 4/32 inch).",
        manualCitation = "FMCSA CDL Manual Section 6.5 - Inspecting Combinations"
    ),
    Question(
        id = "cv_070",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Inspecting Combination Vehicles",
        questionText = "When inspecting trailer brake hardware, how much pushrod travel is generally allowed on manual slack adjusters?",
        options = listOf(
            "No more than 2 inches of pushrod travel",
            "No more than 1 inch of pushrod travel",
            "No more than 1/4 inch of pushrod travel",
            "No more than 3 inches of pushrod travel",
        ),
        correctIndex = 1,
        explanation = "Manual slack adjuster pushrod stroke should not exceed approximately 1 inch when pulled firmly by hand.",
        manualCitation = "FMCSA CDL Manual Section 6.5 - Inspecting Combinations"
    ),
    Question(
        id = "cv_071",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Inspecting Combination Vehicles",
        questionText = "What visual check must be made on trailer frame crossmembers during pre-trip inspection?",
        options = listOf(
            "Ensure crossmembers are painted with bright yellow safety stripes",
            "Verify that crossmembers are lubricated with heavy chassis grease",
            "Check that no crossmembers are missing, cracked, bent, or broken",
            "Check that wooden spacers are wedged between all crossmember beams",
        ),
        correctIndex = 2,
        explanation = "Inspect structural crossmembers under trailer box to ensure none are missing, cracked, broken, or sagged.",
        manualCitation = "FMCSA CDL Manual Section 6.5 - Inspecting Combinations"
    ),
    Question(
        id = "cv_072",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "When driving a combination vehicle, how much following distance should you maintain behind other vehicles at speeds under 40 MPH?",
        options = listOf(
            "Maintain 2 seconds for every 10 feet of vehicle length",
            "Maintain 1 second for every 20 feet of vehicle length",
            "Maintain a fixed 3-second gap regardless of rig length",
            "Maintain 1 second for every 10 feet of vehicle length",
        ),
        correctIndex = 3,
        explanation = "Maintain 1 second of following distance for every 10 feet of overall vehicle length at speeds below 40 MPH.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_073",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "How should following distance be adjusted when driving a 60-foot combination vehicle at speeds ABOVE 40 MPH?",
        options = listOf(
            "Add 1 extra second for high speed (total 7 seconds gap)",
            "Double the total following distance (total 12 seconds)",
            "Maintain the standard 6 seconds with no added adjustments",
            "Reduce following gap to 4 seconds to draft lead vehicles",
        ),
        correctIndex = 0,
        explanation = "At speeds over 40 MPH, calculate 1 second per 10 feet of vehicle length plus 1 additional second (60 feet = 6 + 1 = 7 seconds).",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_074",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "Why is backing a combination vehicle with a trailer considered hazardous?",
        options = listOf(
            "Combination vehicles lack reverse gears and must be towed into loading bays",
            "Trailer blinds rear view, and rear turns opposite from steering wheel turn",
            "Trailer emergency spring brakes lock up automatically whenever in reverse",
            "Fifth wheel locking jaws uncouple if tractor turns sharper than 45 degrees",
        ),
        correctIndex = 1,
        explanation = "Trailer backing visibility is restricted, and turning the steering wheel moves trailer rear in the opposite direction.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_075",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "When backing a combination vehicle with a trailer, which way should you turn the steering wheel to make the trailer turn to the LEFT?",
        options = listOf(
            "Turn top of steering wheel to the LEFT",
            "Hold steering wheel straight and firm",
            "Turn top of steering wheel to the RIGHT",
            "Pump steering wheel from side to side",
        ),
        correctIndex = 2,
        explanation = "To direct the rear of the trailer to the left while backing, turn the top of the steering wheel to the right.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_076",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "What is the safest way to position a combination vehicle when backing into an alley or loading dock?",
        options = listOf(
            "Back toward passenger side (blind-side backing)",
            "Back in a straight line with mirrors folded",
            "Back under guide of horn beeps exclusively",
            "Back toward driver side (sight-side backing)",
        ),
        correctIndex = 3,
        explanation = "Driver side (sight-side) backing allows you to see the trailer path out your side window clearly.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_077",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "What should you do if you MUST perform a blind-side (passenger side) back with a trailer?",
        options = listOf(
            "Use a spotter, pull forward to reposition, and get out and look (GOAL)",
            "Accelerate rapidly in reverse while relying on passenger side convex mirror",
            "Honk the horn continuously and back in one single continuous wheel cut",
            "Turn off headlights and reverse quickly before cross-traffic approaches",
        ),
        correctIndex = 0,
        explanation = "During blind-side maneuvers, stop frequently, get out and inspect surroundings, and utilize a helper spotter.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_078",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "What does a trailer converter dolly consist of?",
        options = listOf(
            "A hydraulic lift crane mounted behind tractor cab used to hoist heavy shipping crates",
            "An auxiliary axle assembly with fifth wheel, tow bar, and air lines for second trailer",
            "A motorized tow vehicle used to push stranded combination rigs out of snow banks",
            "A sliding tandem rail assembly welded underneath standard 53-foot dry van trailers",
        ),
        correctIndex = 1,
        explanation = "A converter dolly is an axle setup with its own fifth wheel and drawbar to couple a semitrailer into a double combination.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_079",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "Does a trailer converter dolly have its own air brake system?",
        options = listOf(
            "No, dollies rely completely on tractor drive axle braking force to stop",
            "No, dollies use hydraulic surge brakes activated by tongue trailer weight",
            "Yes, dollies feature air reservoirs, brake chambers, and emergency valves",
            "Yes, but dolly brakes are only functional when traveling in reverse gear",
        ),
        correctIndex = 2,
        explanation = "Converter dollies are equipped with complete air brake systems, including brake chambers and emergency spring brakes.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_080",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "What is the function of the trailer relay valve?",
        options = listOf(
            "Adjusts trailer brake balance automatically depending on highway cargo weight",
            "Dumps moisture and oil sludge continuously from trailer emergency air tanks",
            "Prevents trailer wheel lockup during hard braking on icy or wet road surfaces",
            "Speeds up brake timing by directing air directly from trailer tank to chambers",
        ),
        correctIndex = 3,
        explanation = "Relay valves use signal pressure from service lines to quickly supply air from nearby trailer tanks directly to brake chambers.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_081",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "When coupling a converter dolly to a lead trailer, what safety latch device secures the drawbar eye to the pintle hook?",
        options = listOf(
            "Pintle hook latch and mechanical safety catch / lock pin",
            "Magnetic safety latch and spring-loaded cotter ring",
            "Hydraulic locking collar and threaded retaining nut",
            "Rubber bungee strap looped around the tow bar eye",
        ),
        correctIndex = 0,
        explanation = "The pintle hook latch closes down over the drawbar eye and is locked with a metal safety keeper pin.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_082",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "What safety equipment must connect between a towing trailer frame and a converter dolly drawbar?",
        options = listOf(
            "Nylon cargo tie-down ratchet straps hooked to the bumper",
            "Heavy steel safety chains / cables crossed under the tow bar",
            "A single length of manila rope tied around pintle hook",
            "Flexible rubber stabilizer cords linked to landing gear",
        ),
        correctIndex = 1,
        explanation = "Crossed steel safety chains connect the towing vehicle frame to the converter dolly drawbar as emergency backup.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_083",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "When uncoupling a converter dolly from a trailer, what should be done with the dolly air hoses?",
        options = listOf(
            "Let hoses drag along road surface where pavement friction buffs the seals",
            "Tie air hoses tightly together around the dolly axle spindle assembly",
            "Hang hoses over tow bar and attach gladhands to protective dummy couplers",
            "Leave air lines connected to the lead trailer with cut-out valves open",
        ),
        correctIndex = 2,
        explanation = "Store dolly gladhands on protective dummy fittings and keep lines secured off the ground.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_084",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Inspecting Combination Vehicles",
        questionText = "When checking a trailer equipped with Anti-lock Brakes (ABS), where is the yellow ABS malfunction indicator light located on the trailer?",
        options = listOf(
            "Inside the trailer cargo box mounted to front nose interior bulkhead",
            "On right (passenger side) landing gear leg near the manual crank handle",
            "Underneath the trailer apron next to the kingpin grease relief groove",
            "On left (driver side) rear exterior of trailer near license plate or axle",
        ),
        correctIndex = 3,
        explanation = "Trailers display a yellow ABS malfunction light on the exterior left rear side to alert drivers during pre-trip checks.",
        manualCitation = "FMCSA CDL Manual Section 6.5 - Inspecting Combinations"
    ),
    Question(
        id = "cv_085",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Inspecting Combination Vehicles",
        questionText = "How do you test trailer ABS functionality during start-up?",
        options = listOf(
            "Turn key ON; yellow trailer ABS light illuminates and turns off after self-check",
            "Pump trailer hand valve five times and listen for audible ABS buzzer in trailer",
            "Drive at 25 MPH, brake hard to lock wheels, and observe trailer skid marks",
            "Disconnect blue service line and verify that yellow dashboard ABS lamp blinks",
        ),
        correctIndex = 0,
        explanation = "When powered, trailer ABS performs a self-test, lighting the yellow exterior indicator lamp briefly before turning it off.",
        manualCitation = "FMCSA CDL Manual Section 6.5 - Inspecting Combinations"
    ),
    Question(
        id = "cv_086",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "What happens if a trailer loses ABS function while driving?",
        options = listOf(
            "All trailer brakes fail completely and trailer wheels will freewheel unchecked",
            "ABS anti-lock protection is lost, but normal service braking remains functional",
            "Trailer emergency spring brakes lock instantly, bringing vehicle to sudden stop",
            "Tractor protection valve closes automatically, dumping all tractor air pressure",
        ),
        correctIndex = 1,
        explanation = "Loss of ABS deactivates anti-lock anti-skid control, but regular service brakes remain fully operational.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_087",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "How should you drive when pulling double or triple trailers in wet or icy weather?",
        options = listOf(
            "Accelerate to highway speed to stabilize trailing units and prevent swaying",
            "Use the trailer trolley hand valve frequently to keep trailer tires warm",
            "Reduce speed significantly, increase following gap, and avoid sharp movements",
            "Shift transmission into high gear and brake aggressively into all turns",
        ),
        correctIndex = 2,
        explanation = "Adverse weather amplifies skid hazards and rollover risk; reduce speed and steer smoothly to maintain control.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_088",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "What is the purpose of shut-off valves (cut-out cocks) on trailer air lines?",
        options = listOf(
            "Used to adjust service brake application timing between tractor and trailer",
            "Used to drain oil sludge and water moisture automatically from air tanks",
            "Used to bleed air pressure from suspension bellows during cargo loading",
            "Used to open or close air line supply channels between combination trailers",
        ),
        correctIndex = 3,
        explanation = "Cut-out cocks control air passage along the service and emergency lines running between connected trailers.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_089",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "When coupling a tractor to a trailer, what should you do immediately AFTER the fifth wheel jaws lock around the kingpin?",
        options = listOf(
            "Inspect jaws visually, connect air/electric lines, perform tug test, raise gear",
            "Raise landing gear completely before checking fifth wheel locking jaws visually",
            "Accelerate swiftly to 15 MPH to test fifth wheel locking jaw security on road",
            "Disconnect trailer emergency air line to confirm that spring brakes hold firm",
        ),
        correctIndex = 0,
        explanation = "Verify jaw closure visually, connect lines, test connection with a tug test, and fully crank up landing gear.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_090",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "Why should you NOT retract landing gear before connecting air lines and checking fifth wheel lock visually?",
        options = listOf(
            "Trailer landing gear legs will seize in raised position and cannot be lowered",
            "If fifth wheel failed to lock, trailer could drop onto drive tires and frame",
            "Tractor drive tires will spin out on pavement due to lack of tongue weight",
            "Air lines will stretch and snap off if landing gear is retracted prematurely",
        ),
        correctIndex = 1,
        explanation = "Leaving landing gear down until jaw lock is confirmed prevents the trailer from dropping onto tractor drive wheels if improper coupling occurred.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_091",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Inspecting Combination Vehicles",
        questionText = "When inspecting fifth wheel mounting bolts, what condition requires repair?",
        options = listOf(
            "Coast downhill in neutral gear while pumping the trailer hand trolley valve often",
            "Ride the foot brake pedal continuously with light pressure down the entire slope",
            "Use engine braking in proper gear, snubbing brakes when speed reaches safe limit",
            "Downshift transmission directly into reverse to lock all drive wheels into a stop",
        ),
        correctIndex = 2,
        explanation = "Inspect fifth wheel mounting brackets for loose, missing, or fractured bolts and cracked welds.",
        manualCitation = "FMCSA CDL Manual Section 6.5 - Inspecting Combinations"
    ),
    Question(
        id = "cv_092",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Inspecting Combination Vehicles",
        questionText = "What should you check on trailer doors and cargo securement during pre-trip inspection?",
        options = listOf(
            "Fan foot pedal rapidly down entire hill to cool brake linings with fresh air",
            "Apply trailer hand brake continuously while keeping foot off service pedal",
            "Hold service brake pedal down with exactly 30 PSI continuous application pressure",
            "Brake 5 MPH below safe speed, release completely, and repeat when speed rises",
        ),
        correctIndex = 3,
        explanation = "Trailer doors must be securely shut, latched, and sealed, with cargo blocked and tied down inside.",
        manualCitation = "FMCSA CDL Manual Section 6.5 - Inspecting Combinations"
    ),
    Question(
        id = "cv_093",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "What is the recommended method for slowing down a long combination vehicle on a long steep downhill grade?",
        options = listOf(
            "Turn key ON with engine OFF, fan brake pedal down until buzzer/light activate",
            "Run engine at idle, press foot pedal once firmly, and observe dashboard warning",
            "Set parking brakes, chock wheels, and listen for safety relief valve hissing",
            "Bleed air from trailer gladhands until tractor service line pressure reads zero",
        ),
        correctIndex = 0,
        explanation = "Select proper low gear and apply snubbing technique to prevent service brake overheating and fade.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_094",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "What causes severe brake fade on combination vehicles during mountain driving?",
        options = listOf(
            "Water moisture condensation freezing inside the trailer emergency supply hoses",
            "Excessive friction heat causing brake drums to expand and linings to lose grip",
            "Air compressor governor failing to maintain 120 PSI supply reservoir pressure",
            "Automatic slack adjusters loosening the pushrod stroke travel on trailer axles",
        ),
        correctIndex = 1,
        explanation = "Extreme friction heat causes brake shoes and drums to expand and lose friction effectiveness (brake fade).",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_095",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "What safety valve opens to vent excessive air pressure if the air compressor governor fails to cut out?",
        options = listOf(
            "Tractor protection valve on dashboard (pops at ~30 PSI)",
            "Automatic tank drain valve petcock (purges at ~90 PSI)",
            "Safety relief valve in supply tank (opens at ~150 PSI)",
            "Quick release valve on rear axle (exhausts at ~60 PSI)",
        ),
        correctIndex = 2,
        explanation = "The safety relief valve automatically blows off excess tank air at approximately 150 PSI to prevent system over-pressurization.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_096",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "What happens when you push IN the red trailer supply valve on the dashboard?",
        options = listOf(
            "Air pressure exhausts from trailer tanks to lock all trailer wheels for parking",
            "Tractor engine stalls out immediately and power steering hydraulic assist fails",
            "Trailer service brakes lock up into emergency anti-lock pulsating cycle mode",
            "Air pressure charges trailer tanks and releases trailer emergency spring brakes",
        ),
        correctIndex = 3,
        explanation = "Pushing IN the red knob charges the trailer air system and releases trailer parking/emergency spring brakes for driving.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_097",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "What check ensures that the trailer apron plate is sitting flat on the fifth wheel skid plate without tilting sideways?",
        options = listOf(
            "Visual check of contact surfaces from sides and rear showing no gap or tilt",
            "Measuring distance between trailer landing gear legs with folding steel ruler",
            "Listening for air leakage hissing sounds around the emergency gladhand coupler",
            "Checking that trailer cargo weight distribution matches shipping paper manifest",
        ),
        correctIndex = 0,
        explanation = "Visually verify from side and rear angles that the trailer apron rests flat against the fifth wheel plate without gaps.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_098",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "When uncoupling a trailer, when should wheel chocks be placed around trailer tires?",
        options = listOf(
            "Only after the tractor has driven completely out from under the trailer",
            "Immediately after parking vehicle, before lowering gear and uncoupling",
            "Whenever the trailer is coupled to tractor and parked on level pavement",
            "Right before cranking landing gear legs down in high gear ratio position",
        ),
        correctIndex = 1,
        explanation = "Chock trailer wheels immediately upon parking to prevent trailer rolling during uncoupling procedures.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_099",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Inspecting Combination Vehicles",
        questionText = "What should you check regarding trailer reflective tape (conspicuity treatment) during pre-trip inspection?",
        options = listOf(
            "Verify that reflective tape matches the tractor cab primary exterior paint color",
            "Check that reflective tape glows continuously in dark without headlights shining",
            "Ensure red-and-white reflective tape is clean, undamaged, and covers required areas",
            "Measure reflective tape thickness with micrometers to ensure minimum 1/4 inch gauge",
        ),
        correctIndex = 2,
        explanation = "Inspect DOT red-and-white retroreflective sheeting along trailer sides and rear to ensure high visibility.",
        manualCitation = "FMCSA CDL Manual Section 6.5 - Inspecting Combinations"
    ),
    Question(
        id = "cv_100",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Inspecting Combination Vehicles",
        questionText = "Where are trailer clearance lights and side marker reflectors required to be located?",
        options = listOf(
            "Amber lights/reflectors at rear, Red lights/reflectors at front and middle sides",
            "White lights/reflectors at rear, Blue lights/reflectors at front and middle sides",
            "Green lights/reflectors at rear, Yellow lights/reflectors along all trailer sides",
            "Red lights/reflectors at rear, Amber lights/reflectors at front and middle sides",
        ),
        correctIndex = 3,
        explanation = "Federal regulations require amber lights/reflectors on front/sides and red lights/reflectors at trailer rear.",
        manualCitation = "FMCSA CDL Manual Section 6.5 - Inspecting Combinations"
    ),
    Question(
        id = "cv_101",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "Why is it dangerous to travel alongside another vehicle in adjacent lanes while pulling a long trailer around sharp curves?",
        options = listOf(
            "Trailer off-tracking can cause rear wheels to cut into adjacent lanes and hit cars",
            "Trailer wind drag will create a vacuum that pulls neighboring cars under chassis",
            "Trailer air lines may disconnect from tractor due to excessive turning angles",
            "Fifth wheel slide bracket lock pins can shear off under cornering centrifugal force",
        ),
        correctIndex = 0,
        explanation = "Off-tracking causes trailer rear wheels to cut across inner lanes during turns, presenting collision risks to neighboring vehicles.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_102",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "What is the term for the extra distance required for compressed air to flow through long hoses to trailer brake chambers?",
        options = listOf(
            "Reaction distance (time for driver to move foot to pedal)",
            "Brake lag distance (time for air to travel to chambers)",
            "Perception distance (time for driver to recognize danger)",
            "Effective braking distance (time for shoes to stop drums)",
        ),
        correctIndex = 1,
        explanation = "Brake lag is the approximately 1/2 second delay required for compressed air to travel through lines to activate brakes.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_103",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "What total stopping distance component is added to air brake vehicles compared to hydraulic brake vehicles?",
        options = listOf(
            "Engine compression distance (about 50 feet delay)",
            "Hydraulic line delay distance (about 15 feet delay)",
            "Brake lag distance (about 32 feet delay at 55 MPH)",
            "Tire slip friction distance (about 40 feet delay)",
        ),
        correctIndex = 2,
        explanation = "Air brake systems add brake lag distance (about 32 feet at 55 MPH) to total stopping distance calculations.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_104",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "What is the function of dummy gladhand couplers mounted on the back of tractor cabs?",
        options = listOf(
            "Connect trailer service air line to the tractor primary fuel tank vent system",
            "Allow driver to bleed moisture and water condensation from air lines manually",
            "Lock the tractor fifth wheel release handle in place during highway cruising",
            "Provide sealed storage ports for gladhand hose ends when uncoupled to block dirt",
        ),
        correctIndex = 3,
        explanation = "Dummy couplers hold disconnected gladhands cleanly, protecting internal seals from contamination.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_105",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "When coupling, if the fifth wheel release handle does not push in or latch completely, what should you do?",
        options = listOf(
            "Verify jaws closed around kingpin shank and safety catch engaged; realign if needed",
            "Drive forward anyway because fifth wheel jaws lock automatically under highway speed",
            "Pound the release handle inward with a heavy steel hammer until latch snaps flush",
            "Tie the release handle securely to the tractor frame rail with nylon zip ties",
        ),
        correctIndex = 0,
        explanation = "Never drive with an unlatched release handle; inspect kingpin jaw engagement, disengage, and recouple properly.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_106",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "What is the danger of attempting to couple a tractor to a trailer when the fifth wheel plate is dry of grease?",
        options = listOf(
            "Causes the fifth wheel locking jaws to jam permanently in the open position",
            "Causes heavy friction, binding, steering resistance, and severe metal wear",
            "Causes trailer air lines to burst from excessive vibration across skid plate",
            "Causes trailer emergency spring brakes to drag continuously along the road",
        ),
        correctIndex = 1,
        explanation = "Lack of fifth wheel grease causes metal friction, making steering difficult and damaging plate surfaces.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_107",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Inspecting Combination Vehicles",
        questionText = "When inspecting trailer spring suspensions, what defects should you check for?",
        options = listOf(
            "Light surface dust accumulation on exterior spring steel leaf surface coatings",
            "Presence of manufacturer part number stencil marks along primary spring leaves",
            "Cracked, broken, shifted, or missing leaf springs, U-bolts, and torque arm mounts",
            "Minor surface paint fading on axle positioning brackets and shackle pin collars",
        ),
        correctIndex = 2,
        explanation = "Inspect trailer suspension for fractured spring leaves, loose U-bolts, broken torque arms, or misaligned axles.",
        manualCitation = "FMCSA CDL Manual Section 6.5 - Inspecting Combinations"
    ),
    Question(
        id = "cv_108",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Inspecting Combination Vehicles",
        questionText = "What defect in an air suspension bag requires immediate out-of-service trailer repair?",
        options = listOf(
            "Minor surface road dust accumulation on exterior rubber air spring bellows",
            "Manufacturer brand logo stamped on upper steel mounting bracket flange",
            "Presence of black rubber preservative coating on exterior suspension sleeve",
            "Audible air leak, ruptured air bag fabric, or deflated air spring assembly",
        ),
        correctIndex = 3,
        explanation = "Ruptured, leaking, or flat air spring bags compromise suspension leveling and mandate out-of-service repairs.",
        manualCitation = "FMCSA CDL Manual Section 6.5 - Inspecting Combinations"
    ),
    Question(
        id = "cv_109",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "Why should drivers avoid sudden, harsh braking when hauling empty or lightly loaded trailers?",
        options = listOf(
            "Light weight causes trailer wheels to lock easily, leading to jackknife skids",
            "Empty trailers create excessive air pressure that blows out foundation chambers",
            "Braking hard with empty trailers causes fifth wheel locking jaws to unlatch",
            "Light trailers cause tractor steer axle brakes to overheat and seize rigidly",
        ),
        correctIndex = 0,
        explanation = "Empty trailers lack downward tire load traction, causing wheels to lock up easily under sudden hard braking.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_110",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "When driving a combination vehicle, how far ahead should you scan the road path?",
        options = listOf(
            "At least 30 to 45 seconds ahead (about 1 mile on open highway)",
            "At least 12 to 15 seconds ahead (about 1/4 mile on open highway)",
            "Approximately 4 to 6 seconds ahead (about 200 feet ahead on road)",
            "Only as far ahead as the rear bumper of the vehicle immediately lead",
        ),
        correctIndex = 1,
        explanation = "Look 12–15 seconds ahead to anticipate traffic obstacles, lane changes, and stopping hazards early.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_111",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "What is the primary indicator that an air brake system leak test has FAILED on a combination vehicle?",
        options = listOf(
            "Pressure drop exceeds 1 PSI in 1 min (static) or 2 PSI in 1 min (applied)",
            "Pressure drop exceeds 5 PSI in 1 min (static) or 8 PSI in 1 min (applied)",
            "Pressure drop exceeds 3 PSI in 1 min (static) or 4 PSI in 1 min (applied)",
            "Low air warning buzzer activates within sixty seconds of turning key on",
        ),
        correctIndex = 2,
        explanation = "Air pressure loss exceeding 3 PSI/min (static) or 4 PSI/min (applied) indicates dangerous leaks requiring repair.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_112",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "What component prevents backflow of compressed air from trailer tanks into tractor air lines if tractor pressure drops?",
        options = listOf(
            "Quick release valves mounted on wheel hubs",
            "Safety relief valves set at 150 PSI limit",
            "Automatic drain petcocks on bottom of tanks",
            "One-way check valves inside the air system",
        ),
        correctIndex = 3,
        explanation = "One-way check valves retain stored trailer air pressure if supply line pressure drops.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_113",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "When coupling, what sound typically indicates that the fifth wheel locking jaws have closed around the kingpin?",
        options = listOf(
            "A loud metallic clunk or snap sound of jaws locking shut",
            "A loud hissing of air exhausting from safety relief valve",
            "A high-pitched electric buzzer sounding in the tractor cab",
            "A continuous rattling sound from the landing gear gearbox",
        ),
        correctIndex = 0,
        explanation = "Backing firm against the kingpin causes the spring-loaded fifth wheel jaws to snap shut with a distinct metallic clunk.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_114",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Coupling & Uncoupling",
        questionText = "What should you check regarding tractor-to-trailer clearance when turning sharply?",
        options = listOf(
            "Ensure that trailer clearance lights change color from red to amber in turns",
            "Verify sufficient clearance between tractor frame/tires and trailer landing gear",
            "Check that tractor low air pressure warning light blinks when turning wheel",
            "Verify that trailer air suspension bags deflate completely during sharp turns",
        ),
        correctIndex = 1,
        explanation = "Ensure ample clearance exists between trailer front corners/landing gear and tractor frame/cab during tight turns.",
        manualCitation = "FMCSA CDL Manual Section 6.4 - Coupling & Uncoupling"
    ),
    Question(
        id = "cv_115",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Inspecting Combination Vehicles",
        questionText = "When inspecting trailer kingpin area, what component supports the kingpin structure under the trailer nose?",
        options = listOf(
            "Trailer rear bumper assembly and ICC underride guard frame",
            "Tractor sliding fifth wheel carriage and base mounting rails",
            "Trailer upper coupler assembly / heavy steel apron plate",
            "Trailer landing gear cross-bracing struts and support shoes",
        ),
        correctIndex = 2,
        explanation = "The heavy steel upper coupler plate (apron) forms the reinforced structure supporting the kingpin.",
        manualCitation = "FMCSA CDL Manual Section 6.5 - Inspecting Combinations"
    ),
    Question(
        id = "cv_116",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Inspecting Combination Vehicles",
        questionText = "What pre-trip check verifies that trailer brake lights operate correctly?",
        options = listOf(
            "Turn on hazard flashers and measure battery voltage at tractor alternator studs",
            "Tap trailer tail lamp lenses with a tire thumper while the engine is running",
            "Rev engine to 1500 RPM and listen for stop light switch clicking in console",
            "Apply service brakes (or trolley valve) and visually confirm rear lamps light up",
        ),
        correctIndex = 3,
        explanation = "Press service brakes or latch trolley brake and walk to rear of trailer to verify illuminated stop lamps.",
        manualCitation = "FMCSA CDL Manual Section 6.5 - Inspecting Combinations"
    ),
    Question(
        id = "cv_117",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "What should you do if your trailer begins to sway dangerously due to heavy crosswinds?",
        options = listOf(
            "Steer smoothly, slow down gradually, and avoid sudden movements or harsh braking",
            "Accelerate to top speed immediately to pull trailer straight through wind gusts",
            "Apply the trailer hand trolley valve hard while counter-steering into the wind",
            "Yank red trailer emergency supply knob to lock trailer spring brakes on highway",
        ),
        correctIndex = 0,
        explanation = "Stabilize crosswind trailer sway by reducing speed gradually and steering smoothly without abrupt actions.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_118",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Vehicle Safety & Driving",
        questionText = "What is the primary danger of driving a bobtail tractor on wet, slippery roads?",
        options = listOf(
            "Bobtail tractors lack power steering assist, making turns impossible on wet roads",
            "Light drive axle weight causes quick wheel spin, hydroplaning, and long stops",
            "Air compressor stops functioning, causing immediate total loss of service brakes",
            "Tractor frame rails bend downward, dragging fuel tanks along wet highway surface",
        ),
        correctIndex = 1,
        explanation = "Bobtail tractors have very light rear axle weight, making drive tires prone to spinning, skidding, and long stopping distances.",
        manualCitation = "FMCSA CDL Manual Section 6.1 - Driving Combination Vehicles"
    ),
    Question(
        id = "cv_119",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Combination Air Brake Systems",
        questionText = "What happens when trailer spring brakes apply while vehicle is moving?",
        options = listOf(
            "Trailer wheels freewheel uncontrollably without any stopping resistance at all",
            "Compressed air bursts through chamber diaphragms, spraying high velocity air",
            "Heavy spring force pushes shoes against drums, bringing trailer to a sudden stop",
            "Tractor transmission drops into neutral and cuts electric ignition completely",
        ),
        correctIndex = 2,
        explanation = "Emergency spring brake application forces brake shoes against drums, bringing trailer wheels to an emergency stop.",
        manualCitation = "FMCSA CDL Manual Section 6.2 - Combination Air Brakes"
    ),
    Question(
        id = "cv_120",
        category = CDLCategory.COMBINATION_VEHICLES,
        subtopic = "Inspecting Combination Vehicles",
        questionText = "Why is a complete, systematic pre-trip inspection essential for combination vehicles?",
        options = listOf(
            "Verifies that combination vehicle diesel engine achieves maximum fuel economy",
            "Guarantees that trailer cargo payload weight matches the bill of lading manifest",
            "Allows commercial combination driver to bypass state highway weigh scales",
            "Ensures all coupling hardware, air lines, safety devices, and tires are safe",
        ),
        correctIndex = 3,
        explanation = "Thorough pre-trip inspections ensure full operational safety of heavy combination rig components before hitting the road.",
        manualCitation = "FMCSA CDL Manual Section 6.5 - Inspecting Combinations"
    ),
)
