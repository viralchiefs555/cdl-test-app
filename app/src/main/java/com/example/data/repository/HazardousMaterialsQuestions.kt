package com.example.data.repository

import com.example.data.models.CDLCategory
import com.example.data.models.Question

/**
 * Master Question Pool for CDL Hazardous Materials (HazMat) Endorsement.
 * Total: 120 official questions across all 5 key domain categories:
 * - Hazard Classes & Identification
 * - Placards & Labeling
 * - Shipping Papers & Manifests
 * - Driving, Parking & Safe Operations
 * - Emergencies, Leaks & Security
 */
val hazardousMaterialsQuestions: List<Question> = listOf(
    Question(
        id = "hm_001",
        category = CDLCategory.HAZMAT,
        subtopic = "Hazard Classes & Identification",
        questionText = "How many main hazard classes are defined by the Department of Transportation (DOT)?",
        options = listOf(
            "6 hazard classes",
            "8 hazard classes",
            "9 hazard classes",
            "12 hazard classes",
        ),
        correctIndex = 2,
        explanation = "The DOT categorizes hazardous materials into 9 distinct numerical classes based on their primary hazard.",
        manualCitation = "FMCSA CDL Manual Section 9.2 - Hazard Classes Table"
    ),
    Question(
        id = "hm_002",
        category = CDLCategory.HAZMAT,
        subtopic = "Hazard Classes & Identification",
        questionText = "What primary hazard is represented by DOT Hazard Class 1?",
        options = listOf(
            "Gases",
            "Explosives",
            "Flammable liquids",
            "Corrosives",
        ),
        correctIndex = 1,
        explanation = "Class 1 materials are defined as explosives, which are further divided into divisions 1.1 through 1.6.",
        manualCitation = "FMCSA CDL Manual Section 9.2 - Hazard Classes Table"
    ),
    Question(
        id = "hm_003",
        category = CDLCategory.HAZMAT,
        subtopic = "Hazard Classes & Identification",
        questionText = "What primary hazard is represented by DOT Hazard Class 2?",
        options = listOf(
            "Flammable solids",
            "Gases",
            "Poisonous materials",
            "Radioactive materials",
        ),
        correctIndex = 1,
        explanation = "Class 2 includes flammable, non-flammable, and toxic/poisonous gases.",
        manualCitation = "FMCSA CDL Manual Section 9.2 - Hazard Classes Table"
    ),
    Question(
        id = "hm_004",
        category = CDLCategory.HAZMAT,
        subtopic = "Hazard Classes & Identification",
        questionText = "What primary hazard is represented by DOT Hazard Class 3?",
        options = listOf(
            "Flammable liquids",
            "Combustible solids",
            "Oxidizers",
            "Corrosive materials",
        ),
        correctIndex = 0,
        explanation = "Class 3 materials are flammable liquids (and combustible liquids meeting specific flash point criteria).",
        manualCitation = "FMCSA CDL Manual Section 9.2 - Hazard Classes Table"
    ),
    Question(
        id = "hm_005",
        category = CDLCategory.HAZMAT,
        subtopic = "Hazard Classes & Identification",
        questionText = "What primary hazard is represented by DOT Hazard Class 4?",
        options = listOf(
            "Flammable liquids",
            "Flammable solids",
            "Poisonous substances",
            "Radioactive substances",
        ),
        correctIndex = 1,
        explanation = "Class 4 includes flammable solids, spontaneously combustible materials, and dangerous-when-wet substances.",
        manualCitation = "FMCSA CDL Manual Section 9.2 - Hazard Classes Table"
    ),
    Question(
        id = "hm_006",
        category = CDLCategory.HAZMAT,
        subtopic = "Hazard Classes & Identification",
        questionText = "What primary hazard is represented by DOT Hazard Class 5?",
        options = listOf(
            "Oxidizers and Organic Peroxides",
            "Poisonous and Infectious substances",
            "Corrosive materials",
            "Miscellaneous dangerous goods",
        ),
        correctIndex = 0,
        explanation = "Class 5 materials are oxidizers (Division 5.1) and organic peroxides (Division 5.2).",
        manualCitation = "FMCSA CDL Manual Section 9.2 - Hazard Classes Table"
    ),
    Question(
        id = "hm_007",
        category = CDLCategory.HAZMAT,
        subtopic = "Hazard Classes & Identification",
        questionText = "What primary hazard is represented by DOT Hazard Class 6?",
        options = listOf(
            "Radioactive materials",
            "Poison (Toxic) and Infectious substances",
            "Corrosive substances",
            "Explosives",
        ),
        correctIndex = 1,
        explanation = "Class 6 covers toxic (poison) substances and infectious substances.",
        manualCitation = "FMCSA CDL Manual Section 9.2 - Hazard Classes Table"
    ),
    Question(
        id = "hm_008",
        category = CDLCategory.HAZMAT,
        subtopic = "Hazard Classes & Identification",
        questionText = "What primary hazard is represented by DOT Hazard Class 7?",
        options = listOf(
            "Corrosives",
            "Miscellaneous hazards",
            "Radioactive materials",
            "Gases",
        ),
        correctIndex = 2,
        explanation = "Class 7 covers radioactive materials requiring specialized packaging and transport controls.",
        manualCitation = "FMCSA CDL Manual Section 9.2 - Hazard Classes Table"
    ),
    Question(
        id = "hm_009",
        category = CDLCategory.HAZMAT,
        subtopic = "Hazard Classes & Identification",
        questionText = "What primary hazard is represented by DOT Hazard Class 8?",
        options = listOf(
            "Corrosive materials",
            "Oxidizing agents",
            "Flammable solids",
            "Explosive mixtures",
        ),
        correctIndex = 0,
        explanation = "Class 8 materials are corrosives, which can cause severe damage to human tissue or metals.",
        manualCitation = "FMCSA CDL Manual Section 9.2 - Hazard Classes Table"
    ),
    Question(
        id = "hm_010",
        category = CDLCategory.HAZMAT,
        subtopic = "Hazard Classes & Identification",
        questionText = "What primary hazard is represented by DOT Hazard Class 9?",
        options = listOf(
            "Dangerous when wet materials",
            "Spontaneously combustible materials",
            "Miscellaneous dangerous goods",
            "Infectious waste products",
        ),
        correctIndex = 2,
        explanation = "Class 9 materials present a hazard during transportation but do not meet the definition of classes 1 through 8.",
        manualCitation = "FMCSA CDL Manual Section 9.2 - Hazard Classes Table"
    ),
    Question(
        id = "hm_011",
        category = CDLCategory.HAZMAT,
        subtopic = "Hazard Classes & Identification",
        questionText = "What is a 'Forbidden' hazardous material according to DOT regulations?",
        options = listOf(
            "A material that can only be hauled on flatbed trailers",
            "A material that must never be transported on public highways",
            "A material requiring a police escort during transit",
            "A material that requires triple-walled steel packaging",
        ),
        correctIndex = 1,
        explanation = "Forbidden hazardous materials are considered too dangerous to transport under normal commercial conditions.",
        manualCitation = "FMCSA CDL Manual Section 9.2 - Hazard Classes Table"
    ),
    Question(
        id = "hm_012",
        category = CDLCategory.HAZMAT,
        subtopic = "Hazard Classes & Identification",
        questionText = "What is an identification number (UN or NA number) used for?",
        options = listOf(
            "To identify the vehicle's registration status",
            "To cross-reference specific hazardous chemicals in emergency response guides",
            "To track the driver's hours of service",
            "To calculate highway toll fees for heavy loads",
        ),
        correctIndex = 1,
        explanation = "4-digit UN or NA numbers uniquely identify chemical substances or groups of chemicals for emergency responders.",
        manualCitation = "FMCSA CDL Manual Section 9.2 - Hazard Classes Table"
    ),
    Question(
        id = "hm_013",
        category = CDLCategory.HAZMAT,
        subtopic = "Hazard Classes & Identification",
        questionText = "What do the letters 'UN' stand for in a hazard identification number like UN1203?",
        options = listOf(
            "Universal Notation",
            "United Nations",
            "Underwriter Network",
            "Uniform Numbering",
        ),
        correctIndex = 1,
        explanation = "UN identification numbers are international standards established by the United Nations.",
        manualCitation = "FMCSA CDL Manual Section 9.2 - Hazard Classes Table"
    ),
    Question(
        id = "hm_014",
        category = CDLCategory.HAZMAT,
        subtopic = "Hazard Classes & Identification",
        questionText = "What do the letters 'NA' stand for in a hazard identification number like NA1993?",
        options = listOf(
            "North American",
            "Non-Approved",
            "National Agency",
            "Natural Asset",
        ),
        correctIndex = 0,
        explanation = "NA numbers are North American identification numbers used for materials not recognized internationally under UN numbers.",
        manualCitation = "FMCSA CDL Manual Section 9.2 - Hazard Classes Table"
    ),
    Question(
        id = "hm_015",
        category = CDLCategory.HAZMAT,
        subtopic = "Hazard Classes & Identification",
        questionText = "What is a 'Reportable Quantity' (RQ)?",
        options = listOf(
            "The minimum weight required to placard a vehicle",
            "The quantity of hazardous material that must be reported to the federal government if spilled",
            "The total weight of cargo recorded on driver logbooks",
            "The maximum amount of fuel a vehicle can carry",
        ),
        correctIndex = 1,
        explanation = "An RQ designation means if that amount or more is released or spilled, the National Response Center must be notified.",
        manualCitation = "FMCSA CDL Manual Section 9.2 - Hazard Classes Table"
    ),
    Question(
        id = "hm_016",
        category = CDLCategory.HAZMAT,
        subtopic = "Hazard Classes & Identification",
        questionText = "Where does the hazard class number appear on a hazard label or placard?",
        options = listOf(
            "In the top apex corner",
            "In the center of the text field",
            "In the bottom corner of the diamond",
            "Along the outer left margin border",
        ),
        correctIndex = 2,
        explanation = "The numerical hazard class or division number is always displayed in the bottom apex corner of labels and placards.",
        manualCitation = "FMCSA CDL Manual Section 9.2 - Hazard Classes Table"
    ),
    Question(
        id = "hm_017",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "How many identical placards must be placed on a vehicle transporting hazardous materials that require placarding?",
        options = listOf(
            "2 placards (front and back)",
            "3 placards (sides and back)",
            "4 placards (front, back, and both sides)",
            "6 placards (all sides and trailer roof)",
        ),
        correctIndex = 2,
        explanation = "Placarded vehicles must display four identical placards: one on the front, one on the rear, and one on each side.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_018",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "Where must the front placard be mounted on a tractor-trailer combination?",
        options = listOf(
            "On the front bumper or front of the tractor cab",
            "On the front bulkhead wall of the trailer only",
            "Inside the driver side window glass",
            "On the tractor hood facing upward",
        ),
        correctIndex = 0,
        explanation = "The front placard must be clearly visible from the front, usually mounted on the tractor's front bumper or cab.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_019",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "What shape is a standard DOT hazardous material placard?",
        options = listOf(
            "Square set flat on a side",
            "Diamond-shaped (square on point)",
            "Octagonal red shape",
            "Circular yellow ring",
        ),
        correctIndex = 1,
        explanation = "All official hazardous materials placards are diamond-shaped (square-on-point) measuring at least 250mm (9.84 inches).",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_020",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "What is the minimum general weight threshold that triggers placarding for Table 2 hazardous materials?",
        options = listOf(
            "100 pounds total weight",
            "500 pounds total weight",
            "1,001 pounds total aggregate gross weight",
            "5,000 pounds liquid volume",
        ),
        correctIndex = 2,
        explanation = "Table 2 materials generally require placarding when the total aggregate gross weight reaches 1,001 pounds or more.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_021",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "When carrying ANY amount of Table 1 hazardous materials, when must you placard your vehicle?",
        options = listOf(
            "Only if travelling across state lines",
            "Only if the load exceeds 500 pounds",
            "Always, regardless of the amount or weight",
            "Only if requested by the shipper",
        ),
        correctIndex = 2,
        explanation = "Table 1 materials (such as explosives 1.1, poison gases, radioactive materials) require placarding in ANY quantity.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_022",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "What placard can be used for mixed loads containing two or more non-bulk Table 2 hazard classes?",
        options = listOf(
            "MIXED HAZARD placard",
            "DANGEROUS placard",
            "CAUTION placard",
            "GENERAL LOAD placard",
        ),
        correctIndex = 1,
        explanation = "A 'DANGEROUS' placard may be used for mixed loads of Table 2 materials totaling 1,001 lbs or more, under specific conditions.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_023",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "If a single hazard class in a mixed load weighs 2,205 pounds (1,000 kg) or more, what placard rule applies?",
        options = listOf(
            "You must use the specific placard for that class instead of or in addition to the Dangerous placard",
            "You must use the Dangerous placard exclusively",
            "You do not need any placard if it is on a flatbed",
            "You must apply double placards to the rear doors",
        ),
        correctIndex = 0,
        explanation = "When 2,205 lbs or more of a single Table 2 material is loaded at one facility, its specific hazard placard must be used.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_024",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "Who is responsible for providing the required placards to the driver?",
        options = listOf(
            "The DOT inspector at a weigh station",
            "The shipper supplying the hazardous materials load",
            "The vehicle manufacturer",
            "The local fire department emergency squad",
        ),
        correctIndex = 1,
        explanation = "The shipper is required to supply the correct placards to the carrier/driver before the load is moved.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_025",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "Who is responsible for placing the correct placards on the vehicle?",
        options = listOf(
            "The shipper only",
            "The driver / motor carrier",
            "The receiving dock clerk",
            "The state highway patrol officer",
        ),
        correctIndex = 1,
        explanation = "While the shipper supplies the placards, the driver/carrier is responsible for verifying and applying them correctly.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_026",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "How far away must a placard be readable during clear daytime conditions?",
        options = listOf(
            "At least 50 feet",
            "At least 100 feet",
            "At least 300 feet",
            "At least 500 feet",
        ),
        correctIndex = 2,
        explanation = "Placards must be kept clean, damage-free, and legible from a distance of at least 300 feet.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_027",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "Where should identification numbers (UN/NA numbers) be displayed on a bulk tank cargo vehicle?",
        options = listOf(
            "On orange panels or directly on the hazard placards",
            "Inside the driver door frame emblem",
            "On white square stickers on trailer mudflaps",
            "On small metal tags attached to battery cables",
        ),
        correctIndex = 0,
        explanation = "Bulk tanks must display 4-digit ID numbers on orange rectangular panels or inside white centers on placards.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_028",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "What is the difference between a hazard 'label' and a hazard 'placard'?",
        options = listOf(
            "Labels go on individual packages; placards go on vehicles/bulk containers",
            "Labels are circular; placards are square",
            "Labels are used for liquids; placards are used for gases",
            "Labels are orange; placards are always red",
        ),
        correctIndex = 0,
        explanation = "Hazard labels are smaller diamond stickers placed on individual boxes or drums; placards are larger and placed on vehicles.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_029",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "What color is a placard representing a Flammable Liquid (Class 3)?",
        options = listOf(
            "Solid Yellow",
            "Solid Red",
            "White with Black stripes",
            "Solid Green",
        ),
        correctIndex = 1,
        explanation = "Class 3 Flammable Liquid placards feature a solid red background with a flame symbol and white lettering/graphics.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_030",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "What color is a placard representing a Non-Flammable Gas (Division 2.2)?",
        options = listOf(
            "Solid Blue",
            "Solid Green",
            "Solid Orange",
            "Half Yellow and Half White",
        ),
        correctIndex = 1,
        explanation = "Division 2.2 Non-Flammable Gas placards feature a solid green background with a cylinder symbol.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_031",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "What color pattern is used for a Flammable Solid (Division 4.1) placard?",
        options = listOf(
            "Vertical Red and White stripes",
            "Horizontal Black and White stripes",
            "Checkerboard Yellow and Red",
            "Solid Orange with white border",
        ),
        correctIndex = 0,
        explanation = "Division 4.1 Flammable Solid placards feature vertical alternating red and white stripes.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_032",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "What color pattern is used for a Spontaneously Combustible (Division 4.2) placard?",
        options = listOf(
            "Top half white, bottom half red",
            "Top half red, bottom half white",
            "Solid yellow with black diagonal line",
            "Blue with white stars",
        ),
        correctIndex = 0,
        explanation = "Division 4.2 Spontaneously Combustible placards feature a top white half and a bottom red half.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_033",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "What color is an Oxidizer (Division 5.1) placard?",
        options = listOf(
            "Solid Yellow",
            "Solid White",
            "Solid Orange",
            "Solid Red",
        ),
        correctIndex = 0,
        explanation = "Division 5.1 Oxidizer placards feature a solid yellow background with a flaming ball symbol.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_034",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "What color pattern is used on a Corrosive (Class 8) placard?",
        options = listOf(
            "Top half white, bottom half black",
            "Top half black, bottom half white",
            "Solid purple background",
            "Red and blue diagonal splits",
        ),
        correctIndex = 0,
        explanation = "Class 8 Corrosive placards feature a white top half showing spilling test tubes and a black bottom half.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_035",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "What graphic symbol appears on a Poison/Toxic placard?",
        options = listOf(
            "Flaming torch",
            "Skull and crossbones",
            "Trefoil radio waves",
            "Exclamation mark inside circle",
        ),
        correctIndex = 1,
        explanation = "Toxic / Poison materials feature a prominent skull and crossbones symbol.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_036",
        category = CDLCategory.HAZMAT,
        subtopic = "Shipping Papers & Manifests",
        questionText = "Where must shipping papers be kept while the driver is sitting at the wheel with the seatbelt fastened?",
        options = listOf(
            "In a sealed envelope inside the sleeper berth",
            "Within immediate reach or mounted on the driver's door pouch",
            "Locked inside the glove compartment box",
            "Tucked under the rear trailer cargo door strap",
        ),
        correctIndex = 1,
        explanation = "While driving, shipping papers must be within immediate reach (restrained by seatbelt) or in the driver's door pocket.",
        manualCitation = "FMCSA CDL Manual Section 9.3.4 - Shipping Papers & Manifests"
    ),
    Question(
        id = "hm_037",
        category = CDLCategory.HAZMAT,
        subtopic = "Shipping Papers & Manifests",
        questionText = "Where must shipping papers be placed if the driver leaves the vehicle cab?",
        options = listOf(
            "On the driver's seat or in the driver's door pouch",
            "Locked in the passenger side toolbox",
            "Taped to the front windshield glass",
            "Carried into the truck stop restaurant at all times",
        ),
        correctIndex = 0,
        explanation = "When leaving the cab, papers must be clearly visible on the driver's seat or in the door pouch for emergency personnel.",
        manualCitation = "FMCSA CDL Manual Section 9.3.4 - Shipping Papers & Manifests"
    ),
    Question(
        id = "hm_038",
        category = CDLCategory.HAZMAT,
        subtopic = "Shipping Papers & Manifests",
        questionText = "In what order must basic shipping descriptions appear on hazardous materials shipping papers?",
        options = listOf(
            "Packing Group, Proper Shipping Name, Identification Number, Hazard Class",
            "Identification Number, Proper Shipping Name, Hazard Class, Packing Group (ISNH sequence)",
            "Proper Shipping Name, Hazard Class, Identification Number, Weight",
            "Hazard Class, Packing Group, Proper Shipping Name, Identification Number",
        ),
        correctIndex = 1,
        explanation = "DOT rules mandate the basic description sequence: ID Number (UN/NA), Proper Shipping Name, Hazard Class, Packing Group.",
        manualCitation = "FMCSA CDL Manual Section 9.3.4 - Shipping Papers & Manifests"
    ),
    Question(
        id = "hm_039",
        category = CDLCategory.HAZMAT,
        subtopic = "Shipping Papers & Manifests",
        questionText = "How must hazardous items be distinguished from non-hazardous items on shipping papers?",
        options = listOf(
            "Highlighted in red, or marked with an 'X' (or 'RQ') in a dedicated HazMat column",
            "Written in cursive handwriting style",
            "Printed on green colored paper cardstock",
            "Listed only on the back page appendix",
        ),
        correctIndex = 0,
        explanation = "HazMat items must be listed first, highlighted in a contrasting color, or identified with an 'X' or 'RQ' column entry.",
        manualCitation = "FMCSA CDL Manual Section 9.3.4 - Shipping Papers & Manifests"
    ),
    Question(
        id = "hm_040",
        category = CDLCategory.HAZMAT,
        subtopic = "Shipping Papers & Manifests",
        questionText = "What information must be included on shipping papers for emergency responders in case of an accident?",
        options = listOf(
            "The driver's home telephone number",
            "A 24-hour emergency response telephone number",
            "The mechanic's garage repair shop location",
            "The truck dealer's warranty phone hotline",
        ),
        correctIndex = 1,
        explanation = "Shipping papers must include a 24-hour emergency contact number that is monitored at all times while cargo is in transit.",
        manualCitation = "FMCSA CDL Manual Section 9.3.4 - Shipping Papers & Manifests"
    ),
    Question(
        id = "hm_041",
        category = CDLCategory.HAZMAT,
        subtopic = "Shipping Papers & Manifests",
        questionText = "What does a Packing Group designation (I, II, or III) indicate on shipping papers?",
        options = listOf(
            "The physical size of the cardboard shipping container",
            "The degree of danger presented by the hazardous material",
            "The stacking limit inside a box trailer",
            "The temperature needed to keep cargo stable",
        ),
        correctIndex = 1,
        explanation = "Packing Groups indicate danger level: PG I (Great Danger), PG II (Medium Danger), and PG III (Minor Danger).",
        manualCitation = "FMCSA CDL Manual Section 9.3.4 - Shipping Papers & Manifests"
    ),
    Question(
        id = "hm_042",
        category = CDLCategory.HAZMAT,
        subtopic = "Shipping Papers & Manifests",
        questionText = "Who must sign the shipper's certification statement on a hazardous materials shipping paper?",
        options = listOf(
            "The motor carrier driver receiving the load",
            "The shipper preparing the shipment",
            "The state highway patrol inspector",
            "The warehouse forklift driver",
        ),
        correctIndex = 1,
        explanation = "The shipper certifies that the shipment has been properly packaged, labeled, and described according to DOT rules.",
        manualCitation = "FMCSA CDL Manual Section 9.3.4 - Shipping Papers & Manifests"
    ),
    Question(
        id = "hm_043",
        category = CDLCategory.HAZMAT,
        subtopic = "Shipping Papers & Manifests",
        questionText = "How long must a motor carrier retain a copy of a hazardous material shipping paper (or electronic image)?",
        options = listOf(
            "At least 30 days",
            "At least 1 year (or 2 years for hazardous waste)",
            "At least 5 years minimum",
            "Indefinitely forever",
        ),
        correctIndex = 1,
        explanation = "DOT requires carriers to retain HazMat shipping papers for 1 year (or 3 years for hazardous waste shipments).",
        manualCitation = "FMCSA CDL Manual Section 9.3.4 - Shipping Papers & Manifests"
    ),
    Question(
        id = "hm_044",
        category = CDLCategory.HAZMAT,
        subtopic = "Shipping Papers & Manifests",
        questionText = "What document must accompany any hazardous waste shipment?",
        options = listOf(
            "Uniform Hazardous Waste Manifest",
            "State Department of Agriculture permit",
            "Commercial Customs Invoice",
            "Weight scale bill of lading only",
        ),
        correctIndex = 0,
        explanation = "Hazardous waste shipments require a specialized Uniform Hazardous Waste Manifest signed by generator, carrier, and recipient.",
        manualCitation = "FMCSA CDL Manual Section 9.3.4 - Shipping Papers & Manifests"
    ),
    Question(
        id = "hm_045",
        category = CDLCategory.HAZMAT,
        subtopic = "Driving, Parking & Safe Operations",
        questionText = "How far away must you park from an open fire if carrying Class 1 Explosives or Flammable liquids?",
        options = listOf(
            "At least 50 feet",
            "At least 100 feet",
            "At least 300 feet",
            "At least 500 feet",
        ),
        correctIndex = 2,
        explanation = "Never park a vehicle containing explosives or flammable materials within 300 feet of an open fire.",
        manualCitation = "FMCSA CDL Manual Section 9.6 - Driving & Parking Rules"
    ),
    Question(
        id = "hm_046",
        category = CDLCategory.HAZMAT,
        subtopic = "Driving, Parking & Safe Operations",
        questionText = "Where can you park a vehicle loaded with Division 1.1, 1.2, or 1.3 Explosives?",
        options = listOf(
            "On public street shoulders near diner parking",
            "On private property with the owner's knowledge or at a designated safe haven",
            "In any public truck stop parking lot space",
            "Underneath highway overpass bridges",
        ),
        correctIndex = 1,
        explanation = "Vehicles carrying Class 1 explosives must only be parked in safe havens or private property with explicit owner consent.",
        manualCitation = "FMCSA CDL Manual Section 9.6 - Driving & Parking Rules"
    ),
    Question(
        id = "hm_047",
        category = CDLCategory.HAZMAT,
        subtopic = "Driving, Parking & Safe Operations",
        questionText = "What is a 'Safe Haven'?",
        options = listOf(
            "An approved parking location designated by local authorities for parking vehicles carrying explosives",
            "Any weigh station parking facility",
            "A rest area equipped with state police guards",
            "A private farm driveway",
        ),
        correctIndex = 0,
        explanation = "A Safe Haven is a government-approved location for parking unattended vehicles carrying high explosives.",
        manualCitation = "FMCSA CDL Manual Section 9.6 - Driving & Parking Rules"
    ),
    Question(
        id = "hm_048",
        category = CDLCategory.HAZMAT,
        subtopic = "Driving, Parking & Safe Operations",
        questionText = "When can a vehicle carrying Division 1.1, 1.2, or 1.3 Explosives be left unattended?",
        options = listOf(
            "When parked in a designated safe haven or on the shipper's property",
            "Whenever the driver goes inside a diner for lunch",
            "When parked at any interstate rest area",
            "Never under any circumstances whatsoever",
        ),
        correctIndex = 0,
        explanation = "Explosives vehicles can only be left unattended if parked in a designated safe haven or secure facility.",
        manualCitation = "FMCSA CDL Manual Section 9.6 - Driving & Parking Rules"
    ),
    Question(
        id = "hm_049",
        category = CDLCategory.HAZMAT,
        subtopic = "Driving, Parking & Safe Operations",
        questionText = "How close to a railroad track must you stop a placarded hazardous materials vehicle?",
        options = listOf(
            "Stop within 10 to 25 feet",
            "Stop within 15 to 50 feet",
            "Stop within 50 to 100 feet",
            "You do not need to stop if no train is visible",
        ),
        correctIndex = 1,
        explanation = "Placarded vehicles must stop within 15 to 50 feet of the nearest rail before crossing railroad tracks.",
        manualCitation = "FMCSA CDL Manual Section 9.6 - Driving & Parking Rules"
    ),
    Question(
        id = "hm_050",
        category = CDLCategory.HAZMAT,
        subtopic = "Driving, Parking & Safe Operations",
        questionText = "When stopping at a railroad crossing in a placarded vehicle, what must you do before proceeding across?",
        options = listOf(
            "Look and listen in both directions, and shift gears while crossing",
            "Look and listen in both directions, and do NOT shift gears while crossing the tracks",
            "Sound horn 3 times and accelerate rapidly across",
            "Turn off all headlights and flash hazard lights",
        ),
        correctIndex = 1,
        explanation = "Look and listen for trains, ensure tracks are clear, and cross without shifting gears on the tracks.",
        manualCitation = "FMCSA CDL Manual Section 9.6 - Driving & Parking Rules"
    ),
    Question(
        id = "hm_051",
        category = CDLCategory.HAZMAT,
        subtopic = "Driving, Parking & Safe Operations",
        questionText = "When are you NOT required to stop at a railroad crossing even if carrying placards?",
        options = listOf(
            "When travelling at highway speed at night",
            "When the crossing is controlled by a police officer or 'Exempt' sign",
            "When transporting Class 3 Flammable Liquids",
            "When towing a flatbed trailer load",
        ),
        correctIndex = 1,
        explanation = "Stops are not required when directed by a traffic officer or at tracks marked with official 'Exempt' signs.",
        manualCitation = "FMCSA CDL Manual Section 9.6 - Driving & Parking Rules"
    ),
    Question(
        id = "hm_052",
        category = CDLCategory.HAZMAT,
        subtopic = "Driving, Parking & Safe Operations",
        questionText = "What type of fire extinguisher rating is required on a vehicle placarded for hazardous materials?",
        options = listOf(
            "At least 5 B:C rating",
            "At least 10 B:C rating",
            "At least 20 A:B rating",
            "At least 50 C rating",
        ),
        correctIndex = 1,
        explanation = "Placarded vehicles must be equipped with a fire extinguisher having a minimum Underwriters' Laboratories rating of 10 B:C.",
        manualCitation = "FMCSA CDL Manual Section 9.6 - Driving & Parking Rules"
    ),
    Question(
        id = "hm_053",
        category = CDLCategory.HAZMAT,
        subtopic = "Driving, Parking & Safe Operations",
        questionText = "How often must you inspect dual tires on a placarded vehicle during a trip?",
        options = listOf(
            "At the start of the trip and every 2 hours or 100 miles",
            "At the start of the trip and every 4 hours or 250 miles",
            "Only at the end of each driving shift",
            "Once every 500 miles",
        ),
        correctIndex = 0,
        explanation = "Dual tires on placarded vehicles must be checked at the start of a trip and every 2 hours or 100 miles (whichever comes first).",
        manualCitation = "FMCSA CDL Manual Section 9.6 - Driving & Parking Rules"
    ),
    Question(
        id = "hm_054",
        category = CDLCategory.HAZMAT,
        subtopic = "Driving, Parking & Safe Operations",
        questionText = "What must you do if you discover a flat or overheating dual tire on a placarded vehicle?",
        options = listOf(
            "Pour water over the tire and continue driving",
            "Stop immediately, remove the tire, and place it at a safe distance from the vehicle",
            "Drive slowly to the next state line",
            "Deflate the matching tire to balance axle height",
        ),
        correctIndex = 1,
        explanation = "Overheated or flat tires present severe fire risks; stop immediately, remove the tire, and isolate it safely.",
        manualCitation = "FMCSA CDL Manual Section 9.6 - Driving & Parking Rules"
    ),
    Question(
        id = "hm_055",
        category = CDLCategory.HAZMAT,
        subtopic = "Driving, Parking & Safe Operations",
        questionText = "Are smokers allowed to smoke within range of a vehicle placarded for Flammable Liquids or Gases?",
        options = listOf(
            "Smoking is permitted if 10 feet away",
            "No smoking within 25 feet of the vehicle or tank",
            "Smoking is permitted inside the cab with windows closed",
            "No smoking within 100 feet at all times",
        ),
        correctIndex = 1,
        explanation = "Never smoke or carry an open flame within 25 feet of a vehicle placarded for Class 3 (flammable liquids) or Class 2 (gases).",
        manualCitation = "FMCSA CDL Manual Section 9.6 - Driving & Parking Rules"
    ),
    Question(
        id = "hm_056",
        category = CDLCategory.HAZMAT,
        subtopic = "Driving, Parking & Safe Operations",
        questionText = "When loading or unloading flammable liquids, what must be done with the vehicle engine?",
        options = listOf(
            "Keep engine idling to run cargo discharge pumps",
            "Shut off engine unless engine power is necessary to operate discharge pumps",
            "Run engine at maximum governor RPM",
            "Disconnect battery cables entirely",
        ),
        correctIndex = 1,
        explanation = "Turn off vehicle engine while loading/unloading flammable liquids unless engine power drives cargo pumps.",
        manualCitation = "FMCSA CDL Manual Section 9.6 - Driving & Parking Rules"
    ),
    Question(
        id = "hm_057",
        category = CDLCategory.HAZMAT,
        subtopic = "Driving, Parking & Safe Operations",
        questionText = "What grounding procedure is required during transfer of Class 3 Flammable Liquids into tank containers?",
        options = listOf(
            "Ground tires with rubber wedges",
            "Bond cargo tank to fill pipe before opening flow valves",
            "Connect electric extension cord to trailer chassis",
            "Wipe fill nozzles with alcohol towels",
        ),
        correctIndex = 1,
        explanation = "Tank and piping must be electrically bonded to prevent static electricity sparks from igniting flammable vapors.",
        manualCitation = "FMCSA CDL Manual Section 9.6 - Driving & Parking Rules"
    ),
    Question(
        id = "hm_058",
        category = CDLCategory.HAZMAT,
        subtopic = "Driving, Parking & Safe Operations",
        questionText = "Can you carry Class 1 Explosives in a combination vehicle featuring a double trailer setup?",
        options = listOf(
            "Yes, anywhere in the combination",
            "No, Division 1.1 or 1.2 Explosives cannot be pulled in double/triple trailer combinations in most circumstances",
            "Yes, but only in the rear trailer",
            "Yes, if travel occurs during daylight hours only",
        ),
        correctIndex = 1,
        explanation = "Federal rules prohibit transporting Class 1.1, 1.2, or 1.3 explosives in double or triple trailer combinations.",
        manualCitation = "FMCSA CDL Manual Section 9.6 - Driving & Parking Rules"
    ),
    Question(
        id = "hm_059",
        category = CDLCategory.HAZMAT,
        subtopic = "Driving, Parking & Safe Operations",
        questionText = "What is required before driving a tank vehicle carrying hazardous liquid cargo?",
        options = listOf(
            "CDL Tanker endorsement (N) in addition to HazMat endorsement (H)",
            "Passenger endorsement (P)",
            "Double/Triple endorsement (T)",
            "School bus endorsement (S)",
        ),
        correctIndex = 0,
        explanation = "Drivers transporting bulk hazardous liquids in tank vehicles must hold both Tanker (N) and HazMat (H) endorsements (X endorsement).",
        manualCitation = "FMCSA CDL Manual Section 9.6 - Driving & Parking Rules"
    ),
    Question(
        id = "hm_060",
        category = CDLCategory.HAZMAT,
        subtopic = "Driving, Parking & Safe Operations",
        questionText = "What combination endorsement letter code represents both Tanker and HazMat endorsements on a CDL?",
        options = listOf(
            "Code H",
            "Code N",
            "Code X",
            "Code Z",
        ),
        correctIndex = 2,
        explanation = "The 'X' endorsement on a CDL license signifies that the driver holds both Tanker and Hazardous Materials endorsements.",
        manualCitation = "FMCSA CDL Manual Section 9.6 - Driving & Parking Rules"
    ),
    Question(
        id = "hm_061",
        category = CDLCategory.HAZMAT,
        subtopic = "Emergencies, Leaks & Security",
        questionText = "What is the primary guide book used by emergency personnel and drivers during a HazMat incident?",
        options = listOf(
            "DOT Emergency Response Guidebook (ERG)",
            "State Highway Patrol Traffic Manual",
            "National Trucking Association Directory",
            "Commercial Driver Handbook Appendix",
        ),
        correctIndex = 0,
        explanation = "The ERG provides critical initial response instructions indexed by chemical name and 4-digit UN/NA identification numbers.",
        manualCitation = "FMCSA CDL Manual Section 9.7 - Emergencies, Spills & Security"
    ),
    Question(
        id = "hm_062",
        category = CDLCategory.HAZMAT,
        subtopic = "Emergencies, Leaks & Security",
        questionText = "What should be your FIRST action if you discover a hazardous materials leak or spill while parked?",
        options = listOf(
            "Keep bystanders away, prevent open flames, and notify appropriate emergency authorities",
            "Drive vehicle quickly to nearest car wash bay",
            "Attempt to wash chemicals into roadside drainage ditches",
            "Cover leaking drum with heavy wool blankets",
        ),
        correctIndex = 0,
        explanation = "Isolate the area, keep people upwind and at a safe distance, eliminate ignition sources, and call emergency responders.",
        manualCitation = "FMCSA CDL Manual Section 9.7 - Emergencies, Spills & Security"
    ),
    Question(
        id = "hm_063",
        category = CDLCategory.HAZMAT,
        subtopic = "Emergencies, Leaks & Security",
        questionText = "Who must be notified immediately if a spill involves a Reportable Quantity (RQ) of hazardous material?",
        options = listOf(
            "National Response Center (NRC)",
            "Occupational Safety Health Bureau (OSHA)",
            "Federal Highway Administration (FHWA)",
            "Environmental Protection Agency (EPA) Regional Office",
        ),
        correctIndex = 0,
        explanation = "Spills meeting or exceeding an RQ threshold must be reported immediately to the National Response Center.",
        manualCitation = "FMCSA CDL Manual Section 9.7 - Emergencies, Spills & Security"
    ),
    Question(
        id = "hm_064",
        category = CDLCategory.HAZMAT,
        subtopic = "Emergencies, Leaks & Security",
        questionText = "What information must you provide when reporting a spill to the National Response Center?",
        options = listOf(
            "Location, material name, UN number, estimated quantity spilled, and injuries/hazards",
            "Vehicle VIN number and tire pressure readings",
            "Driver's social security number and home address",
            "Shipper's tax identification registration number",
        ),
        correctIndex = 0,
        explanation = "You must supply location details, chemical identification, UN number, release volume, and current safety conditions.",
        manualCitation = "FMCSA CDL Manual Section 9.7 - Emergencies, Spills & Security"
    ),
    Question(
        id = "hm_065",
        category = CDLCategory.HAZMAT,
        subtopic = "Emergencies, Leaks & Security",
        questionText = "What emergency warning signals should be used if a placarded vehicle breaks down on the highway at night?",
        options = listOf(
            "Burning road flares (fusees) near flammable tank valves",
            "Reflective triangles placed at appropriate distances behind and in front of vehicle",
            "Lit kerosene lanterns on top of cargo box",
            "Flashlight waved by driver sitting on bumper",
        ),
        correctIndex = 1,
        explanation = "Never use burning flares or open flames around flammable liquids or gases; use reflective triangles or electric signals.",
        manualCitation = "FMCSA CDL Manual Section 9.7 - Emergencies, Spills & Security"
    ),
    Question(
        id = "hm_066",
        category = CDLCategory.HAZMAT,
        subtopic = "Emergencies, Leaks & Security",
        questionText = "Why are burning flares (fusees) strictly prohibited around vehicles carrying Flammable Liquids or Gases?",
        options = listOf(
            "Flares attract insects to cargo doors",
            "Open flames from flares can ignite leaking flammable vapors and cause explosions",
            "Flares melt asphalt highway surfaces",
            "Flares obscure reflective diamond placards",
        ),
        correctIndex = 1,
        explanation = "Burning fusees introduce open flames that instantly ignite leaking flammable gas or liquid vapors.",
        manualCitation = "FMCSA CDL Manual Section 9.7 - Emergencies, Spills & Security"
    ),
    Question(
        id = "hm_067",
        category = CDLCategory.HAZMAT,
        subtopic = "Emergencies, Leaks & Security",
        questionText = "What is the background color of the Emergency Response Guidebook (ERG) pages indexing initial isolation distances?",
        options = listOf(
            "Yellow-bordered pages",
            "Blue-bordered pages",
            "Green-bordered pages",
            "Orange-bordered pages",
        ),
        correctIndex = 2,
        explanation = "Green-bordered pages in the ERG highlight initial isolation distances and protective action distances for toxic inhalation hazards.",
        manualCitation = "FMCSA CDL Manual Section 9.7 - Emergencies, Spills & Security"
    ),
    Question(
        id = "hm_068",
        category = CDLCategory.HAZMAT,
        subtopic = "Emergencies, Leaks & Security",
        questionText = "What information is listed on the Orange-bordered pages of the Emergency Response Guidebook (ERG)?",
        options = listOf(
            "Numerical index of UN numbers",
            "Alphabetical chemical list",
            "Safety guides detailing primary hazards, public safety, and emergency response steps",
            "Directory of state police telephone numbers",
        ),
        correctIndex = 2,
        explanation = "Orange ERG pages provide critical safety action guides covering health hazards, fire suppression, and first aid.",
        manualCitation = "FMCSA CDL Manual Section 9.7 - Emergencies, Spills & Security"
    ),
    Question(
        id = "hm_069",
        category = CDLCategory.HAZMAT,
        subtopic = "Emergencies, Leaks & Security",
        questionText = "What direction should you move relative to a chemical spill or vapor cloud?",
        options = listOf(
            "Downwind and uphill",
            "Upwind and uphill",
            "Downwind and downhill",
            "Crosswind toward low terrain areas",
        ),
        correctIndex = 1,
        explanation = "Always move upwind (away from blowing vapors) and uphill (above heavy flowing liquid or toxic gas clouds).",
        manualCitation = "FMCSA CDL Manual Section 9.7 - Emergencies, Spills & Security"
    ),
    Question(
        id = "hm_070",
        category = CDLCategory.HAZMAT,
        subtopic = "Emergencies, Leaks & Security",
        questionText = "What federal agency requires drivers hauling hazardous materials to undergo a Security Threat Assessment background check?",
        options = listOf(
            "Transportation Security Administration (TSA)",
            "Federal Aviation Administration (FAA)",
            "Federal Railroad Administration (FRA)",
            "Department of Labor (DOL)",
        ),
        correctIndex = 0,
        explanation = "TSA conducts fingerprint background checks and security threat assessments for CDL HazMat endorsement applicants.",
        manualCitation = "FMCSA CDL Manual Section 9.7 - Emergencies, Spills & Security"
    ),
    Question(
        id = "hm_071",
        category = CDLCategory.HAZMAT,
        subtopic = "Emergencies, Leaks & Security",
        questionText = "How often must a driver renew their TSA background check and HazMat endorsement?",
        options = listOf(
            "Every 2 years",
            "Every 5 years (or according to state CDL renewal cycles)",
            "Every 10 years",
            "HazMat endorsements never expire",
        ),
        correctIndex = 1,
        explanation = "Federal regulations mandate TSA security threat re-assessments every 5 years maximum.",
        manualCitation = "FMCSA CDL Manual Section 9.7 - Emergencies, Spills & Security"
    ),
    Question(
        id = "hm_072",
        category = CDLCategory.HAZMAT,
        subtopic = "Emergencies, Leaks & Security",
        questionText = "What must you do with your vehicle's shipping papers when leaving your truck unattended at a truck stop?",
        options = listOf(
            "Take shipping papers inside truck stop with you",
            "Leave papers clearly visible on driver's seat or in driver's door pouch",
            "Lock shipping papers inside sleeper berth wardrobe cabinet",
            "Leave papers taped inside rear trailer doors",
        ),
        correctIndex = 1,
        explanation = "If leaving cab unattended, papers must be placed in driver door pocket or directly on driver's seat.",
        manualCitation = "FMCSA CDL Manual Section 9.7 - Emergencies, Spills & Security"
    ),
    Question(
        id = "hm_073",
        category = CDLCategory.HAZMAT,
        subtopic = "Hazard Classes & Identification",
        questionText = "What division of Class 1 explosives represents a mass detonation hazard?",
        options = listOf(
            "Division 1.1",
            "Division 1.3",
            "Division 1.4",
            "Division 1.6",
        ),
        correctIndex = 0,
        explanation = "Division 1.1 explosives consist of mass explosion hazards affecting virtually the entire load instantaneously.",
        manualCitation = "FMCSA CDL Manual Section 9.2 - Hazard Classes Table"
    ),
    Question(
        id = "hm_074",
        category = CDLCategory.HAZMAT,
        subtopic = "Hazard Classes & Identification",
        questionText = "What division of Class 2 gases represents toxic or poisonous gas hazards?",
        options = listOf(
            "Division 2.1",
            "Division 2.2",
            "Division 2.3",
            "Division 2.4",
        ),
        correctIndex = 2,
        explanation = "Division 2.3 includes gases toxic/poisonous by inhalation.",
        manualCitation = "FMCSA CDL Manual Section 9.2 - Hazard Classes Table"
    ),
    Question(
        id = "hm_075",
        category = CDLCategory.HAZMAT,
        subtopic = "Hazard Classes & Identification",
        questionText = "What division of Class 4 flammable solids represents dangerous-when-wet materials?",
        options = listOf(
            "Division 4.1",
            "Division 4.2",
            "Division 4.3",
            "Division 4.4",
        ),
        correctIndex = 2,
        explanation = "Division 4.3 dangerous-when-wet solids emit flammable or toxic gases when contacting water.",
        manualCitation = "FMCSA CDL Manual Section 9.2 - Hazard Classes Table"
    ),
    Question(
        id = "hm_076",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "What placard symbol appears on a Division 2.1 Flammable Gas placard?",
        options = listOf(
            "Flame symbol",
            "Gas cylinder symbol",
            "Skull and crossbones",
            "Radiation trefoil",
        ),
        correctIndex = 0,
        explanation = "Division 2.1 Flammable Gas placards feature a red background with a prominent white or black flame symbol.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_077",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "When transporting Chlorine in bulk containers, what specific placard name is required?",
        options = listOf(
            "NON-FLAMMABLE GAS placard",
            "CHLORINE placard",
            "CORROSIVE placard",
            "HAZARDOUS AIR placard",
        ),
        correctIndex = 1,
        explanation = "Bulk shipments of Chlorine require specific 'CHLORINE' placards showing UN1017 identification.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_078",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "When transporting Anhydrous Ammonia in bulk containers, what specific placard display is required?",
        options = listOf(
            "ANHYDROUS AMMONIA placard (or Non-Flammable Gas with UN1005)",
            "OXYGEN placard",
            "POISON LIQUID placard",
            "DANGEROUS placard",
        ),
        correctIndex = 0,
        explanation = "Anhydrous Ammonia bulk tanks must display UN1005 identification on Non-Flammable Gas or Poison Inhalation Hazard placards.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_079",
        category = CDLCategory.HAZMAT,
        subtopic = "Shipping Papers & Manifests",
        questionText = "What does the letters 'RQ' printed next to a shipping paper description signify?",
        options = listOf(
            "Required Quantity",
            "Reportable Quantity",
            "Restricted Quota",
            "Rapid Quick-release",
        ),
        correctIndex = 1,
        explanation = "'RQ' designates that a spill equal to or exceeding specified package weight limits must be reported to the federal government.",
        manualCitation = "FMCSA CDL Manual Section 9.3.4 - Shipping Papers & Manifests"
    ),
    Question(
        id = "hm_080",
        category = CDLCategory.HAZMAT,
        subtopic = "Shipping Papers & Manifests",
        questionText = "If a shipment contains 100 pounds of non-hazardous material and 50 pounds of HazMat, how should total weight be listed?",
        options = listOf(
            "Combined as 150 lbs total without breakdown",
            "HazMat weight listed separately from non-hazardous material weight",
            "Listed as non-hazardous load only",
            "Rounded up to nearest 1,000 lbs",
        ),
        correctIndex = 1,
        explanation = "Hazardous material package weights must be explicitly stated separately from non-hazardous freight weights.",
        manualCitation = "FMCSA CDL Manual Section 9.3.4 - Shipping Papers & Manifests"
    ),
    Question(
        id = "hm_081",
        category = CDLCategory.HAZMAT,
        subtopic = "Driving, Parking & Safe Operations",
        questionText = "When hauling Class 1 Explosives, what must you do before taking your vehicle into a service garage for repairs?",
        options = listOf(
            "Remove all explosives from the vehicle before servicing",
            "Cover explosives with rubber tarps inside garage",
            "Park vehicle near garage furnace room",
            "Leave engine running while mechanics inspect chassis",
        ),
        correctIndex = 0,
        explanation = "Explosives must be unloaded prior to placing a transport vehicle into a repair garage facility.",
        manualCitation = "FMCSA CDL Manual Section 9.6 - Driving & Parking Rules"
    ),
    Question(
        id = "hm_082",
        category = CDLCategory.HAZMAT,
        subtopic = "Driving, Parking & Safe Operations",
        questionText = "Can you leave a vehicle loaded with placarded hazardous materials unattended on a public street in a residential area?",
        options = listOf(
            "Yes, if parking duration is under 1 hour",
            "No, never park placarded vehicles on public residential streets except for brief operational necessities",
            "Yes, provided parking lights remain on",
            "Yes, if driver sleeps inside cab sleeper berth",
        ),
        correctIndex = 1,
        explanation = "Placarded vehicles must not be parked on public streets in residential districts unless necessary for delivery operations.",
        manualCitation = "FMCSA CDL Manual Section 9.6 - Driving & Parking Rules"
    ),
    Question(
        id = "hm_083",
        category = CDLCategory.HAZMAT,
        subtopic = "Driving, Parking & Safe Operations",
        questionText = "What must you do if a cargo tank carrying flammable liquid develops a minor weld leak while on the road?",
        options = listOf(
            "Weld the crack immediately using torch kit",
            "Safely move vehicle away from populated areas and transfer cargo or repair leak safely",
            "Speed up to reach destination before tank empties",
            "Plug crack with wooden golf tees and duct tape",
        ),
        correctIndex = 1,
        explanation = "Isolate leaking cargo tanks away from population centers and arrange safe liquid transfer.",
        manualCitation = "FMCSA CDL Manual Section 9.6 - Driving & Parking Rules"
    ),
    Question(
        id = "hm_084",
        category = CDLCategory.HAZMAT,
        subtopic = "Emergencies, Leaks & Security",
        questionText = "What chemical organization provides 24-hour technical information regarding chemical spills via phone?",
        options = listOf(
            "CHEMTREC",
            "HAZMATNET",
            "CHEMSAFE",
            "DOTINFO",
        ),
        correctIndex = 0,
        explanation = "CHEMTREC (Chemical Transportation Emergency Center) provides 24/7 technical advice to emergency responders and drivers.",
        manualCitation = "FMCSA CDL Manual Section 9.7 - Emergencies, Spills & Security"
    ),
    Question(
        id = "hm_085",
        category = CDLCategory.HAZMAT,
        subtopic = "Emergencies, Leaks & Security",
        questionText = "If your vehicle breaks down while transporting placarded hazardous cargo, who must you contact first?",
        options = listOf(
            "Your motor carrier dispatcher / emergency contact",
            "Nearest news station desk",
            "Vehicle finance leasing agency",
            "Local union representative",
        ),
        correctIndex = 0,
        explanation = "Notify your carrier dispatcher immediately so emergency support and replacement transport can be coordinated.",
        manualCitation = "FMCSA CDL Manual Section 9.7 - Emergencies, Spills & Security"
    ),
    Question(
        id = "hm_086",
        category = CDLCategory.HAZMAT,
        subtopic = "Hazard Classes & Identification",
        questionText = "What is an Organic Peroxide (Division 5.2)?",
        options = listOf(
            "A severe thermal and chemical instability hazard that can react violently or catch fire",
            "A non-flammable gas used in air conditioning",
            "A heavy metal compound used in radio batteries",
            "A inert powder used to extinguish metal fires",
        ),
        correctIndex = 0,
        explanation = "Organic peroxides contain oxygen structures vulnerable to explosive decomposition or severe fire if heated.",
        manualCitation = "FMCSA CDL Manual Section 9.2 - Hazard Classes Table"
    ),
    Question(
        id = "hm_087",
        category = CDLCategory.HAZMAT,
        subtopic = "Hazard Classes & Identification",
        questionText = "What is a Cryogenic Liquid?",
        options = listOf(
            "A refrigerated liquefied gas kept at extremely low temperatures (-130°F or colder)",
            "A liquid heated above 500°F during transport",
            "A synthetic oil used in hydraulic transmissions",
            "A liquid toxic only when swallowed",
        ),
        correctIndex = 0,
        explanation = "Cryogenic liquids are super-cooled refrigerated gases kept extremely cold inside insulated double-walled tanks.",
        manualCitation = "FMCSA CDL Manual Section 9.2 - Hazard Classes Table"
    ),
    Question(
        id = "hm_088",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "Where must identification numbers be displayed on portable tanks containing hazardous materials?",
        options = listOf(
            "On two opposing sides of the portable tank container",
            "Inside bottom drainage valve handle",
            "Printed on driver helmet visor",
            "Stamped into wheel lug nuts",
        ),
        correctIndex = 0,
        explanation = "Portable tanks with capacity over 1,000 gallons must display ID numbers on two opposing sides.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_089",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "What placard display rule applies to cargo tanks carrying Elevated Temperature Materials (HOT)?",
        options = listOf(
            "Must display the word 'HOT' on white rectangular panels or placards alongside ID numbers",
            "Must display yellow caution flags on trailer corners",
            "Must display blue reflective light bar on roof",
            "Must feature steam pipe graphics on rear doors",
        ),
        correctIndex = 0,
        explanation = "Elevated temperature liquids (like liquid asphalt) require white 'HOT' panels or markings.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_090",
        category = CDLCategory.HAZMAT,
        subtopic = "Shipping Papers & Manifests",
        questionText = "What abbreviation indicates a material is a Poison Inhalation Hazard on shipping papers?",
        options = listOf(
            "PIH or Toxic Inhalation Hazard (TIH)",
            "POIS-GAS",
            "INH-HAZ",
            "TOX-AIR",
        ),
        correctIndex = 0,
        explanation = "Materials posing severe respiratory hazards must be explicitly identified as 'Poison Inhalation Hazard' or 'TIH'.",
        manualCitation = "FMCSA CDL Manual Section 9.3.4 - Shipping Papers & Manifests"
    ),
    Question(
        id = "hm_091",
        category = CDLCategory.HAZMAT,
        subtopic = "Driving, Parking & Safe Operations",
        questionText = "When loading radioactive materials, where must packages be stowed relative to the driver compartment?",
        options = listOf(
            "Directly behind driver cab wall",
            "At safe separation distances specified in DOT radiation distance tables",
            "Under passenger seat compartment",
            "Inside sleeping berth footwell",
        ),
        correctIndex = 1,
        explanation = "Radioactive packages emit radiation and must be placed at prescribed separation distances from driver and passengers.",
        manualCitation = "FMCSA CDL Manual Section 9.6 - Driving & Parking Rules"
    ),
    Question(
        id = "hm_092",
        category = CDLCategory.HAZMAT,
        subtopic = "Driving, Parking & Safe Operations",
        questionText = "Can you transport Division 1.1 Explosives in a truck with an automatic heater in the cargo space?",
        options = listOf(
            "Yes, if heater is set below 70°F",
            "No, cargo heaters must be disconnected or rendered inoperable when hauling explosives",
            "Yes, automatic heaters are mandatory",
            "Yes, if heater burns diesel fuel",
        ),
        correctIndex = 1,
        explanation = "Cargo heaters introduce ignition hazards and must be turned off or disconnected when hauling Class 1 explosives.",
        manualCitation = "FMCSA CDL Manual Section 9.6 - Driving & Parking Rules"
    ),
    Question(
        id = "hm_093",
        category = CDLCategory.HAZMAT,
        subtopic = "Driving, Parking & Safe Operations",
        questionText = "What must you do before loading or unloading explosives from a vehicle?",
        options = listOf(
            "Set parking brakes and chock wheels",
            "Keep engine running at fast idle",
            "Turn on internal cargo lights",
            "Drain air brake storage tanks completely",
        ),
        correctIndex = 0,
        explanation = "Vehicles must be safely parked with parking brakes set and wheels chocked before handling explosives.",
        manualCitation = "FMCSA CDL Manual Section 9.6 - Driving & Parking Rules"
    ),
    Question(
        id = "hm_094",
        category = CDLCategory.HAZMAT,
        subtopic = "Emergencies, Leaks & Security",
        questionText = "What action should you take if a tire fire starts on a vehicle hauling hazardous materials?",
        options = listOf(
            "Drive fast to blow fire out with wind",
            "Use fire extinguisher or water immediately, and separate tire from vehicle if safe",
            "Pour engine oil over tire carcass",
            "Lock cab doors and sleep inside",
        ),
        correctIndex = 1,
        explanation = "Tire fires release intense heat; fight tire fires aggressively with water/extinguisher and isolate tire away from HazMat load.",
        manualCitation = "FMCSA CDL Manual Section 9.7 - Emergencies, Spills & Security"
    ),
    Question(
        id = "hm_095",
        category = CDLCategory.HAZMAT,
        subtopic = "Emergencies, Leaks & Security",
        questionText = "What is a security plan requirement for motor carriers transporting high-risk hazardous materials?",
        options = listOf(
            "Written security measures covering personnel security, unauthorized access, and en-route security",
            "Installing bulletproof glass on tractor doors",
            "Hiring armed police guards for all trips",
            "Painting trailer box bright orange",
        ),
        correctIndex = 0,
        explanation = "Carriers moving significant HazMat volumes must maintain formal written security plans addressing security risks.",
        manualCitation = "FMCSA CDL Manual Section 9.7 - Emergencies, Spills & Security"
    ),
    Question(
        id = "hm_096",
        category = CDLCategory.HAZMAT,
        subtopic = "Hazard Classes & Identification",
        questionText = "What is an Oxidizer (Division 5.1)?",
        options = listOf(
            "A substance that yields oxygen readily, stimulating combustion of organic materials",
            "A liquid that corrodes iron metal surfaces",
            "A gas that smothers fires instantly",
            "An explosive powder trigger",
        ),
        correctIndex = 0,
        explanation = "Oxidizers supply oxygen to fires, causing combustible materials to burn fiercely.",
        manualCitation = "FMCSA CDL Manual Section 9.2 - Hazard Classes Table"
    ),
    Question(
        id = "hm_097",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "What background color is used on a Radioactive (Class 7) placard top half?",
        options = listOf(
            "Solid Yellow",
            "Solid White",
            "Solid Red",
            "Solid Green",
        ),
        correctIndex = 0,
        explanation = "Class 7 Radioactive placards feature a yellow top half with black radiation trefoil symbol and white bottom half.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_098",
        category = CDLCategory.HAZMAT,
        subtopic = "Shipping Papers & Manifests",
        questionText = "If shipping papers list 'UN1203, Gasoline, 3, PG II', what is the UN identification number?",
        options = listOf(
            "3",
            "PG II",
            "Gasoline",
            "UN1203",
        ),
        correctIndex = 3,
        explanation = "'UN1203' is the 4-digit identification number assigned to Gasoline.",
        manualCitation = "FMCSA CDL Manual Section 9.3.4 - Shipping Papers & Manifests"
    ),
    Question(
        id = "hm_099",
        category = CDLCategory.HAZMAT,
        subtopic = "Shipping Papers & Manifests",
        questionText = "If shipping papers list 'UN1203, Gasoline, 3, PG II', what is the Hazard Class?",
        options = listOf(
            "UN1203",
            "Gasoline",
            "3 (Flammable Liquid)",
            "PG II",
        ),
        correctIndex = 2,
        explanation = "The number '3' represents Hazard Class 3 (Flammable Liquid).",
        manualCitation = "FMCSA CDL Manual Section 9.3.4 - Shipping Papers & Manifests"
    ),
    Question(
        id = "hm_100",
        category = CDLCategory.HAZMAT,
        subtopic = "Driving, Parking & Safe Operations",
        questionText = "How far back from a fire must you place reflective triangles if stopped on a two-lane road with HazMat cargo?",
        options = listOf(
            "10 feet, 100 feet, and 200 feet facing traffic directions",
            "50 feet and 500 feet only",
            "5 feet behind trailer bumper",
            "Triangles are not needed if hazard lights are flashing",
        ),
        correctIndex = 0,
        explanation = "Standard emergency triangle placement applies (10', 100', 200') to warn oncoming traffic safely.",
        manualCitation = "FMCSA CDL Manual Section 9.6 - Driving & Parking Rules"
    ),
    Question(
        id = "hm_101",
        category = CDLCategory.HAZMAT,
        subtopic = "Emergencies, Leaks & Security",
        questionText = "What is the primary danger of breathing vapors from Class 8 Corrosive liquids?",
        options = listOf(
            "Severe irritation or chemical burns to respiratory tract and lungs",
            "Immediate explosion inside nasal passages",
            "Frostbite on outer lips",
            "Drowsiness without chemical toxicity",
        ),
        correctIndex = 0,
        explanation = "Corrosive fumes cause destructive chemical burns to lung tissue and respiratory pathways.",
        manualCitation = "FMCSA CDL Manual Section 9.7 - Emergencies, Spills & Security"
    ),
    Question(
        id = "hm_102",
        category = CDLCategory.HAZMAT,
        subtopic = "Hazard Classes & Identification",
        questionText = "What division covers Infectious Substances in Class 6?",
        options = listOf(
            "Division 6.1",
            "Division 6.2",
            "Division 6.3",
            "Division 6.4",
        ),
        correctIndex = 1,
        explanation = "Division 6.2 covers infectious substances known or suspected to contain pathogens (viruses/bacteria).",
        manualCitation = "FMCSA CDL Manual Section 9.2 - Hazard Classes Table"
    ),
    Question(
        id = "hm_103",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "Can you use placards with minor tears or dirt covering numbers?",
        options = listOf(
            "Yes, if driver has extra paper copy",
            "No, placards must remain clean, undamaged, and completely legible",
            "Yes, during rainstorms only",
            "Yes, if mounted on rear doors only",
        ),
        correctIndex = 1,
        explanation = "Damaged, dirty, or unreadable placards must be replaced immediately.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_104",
        category = CDLCategory.HAZMAT,
        subtopic = "Shipping Papers & Manifests",
        questionText = "What does a shipper certify when signing the shipping papers?",
        options = listOf(
            "That the driver is legally licensed to haul HazMat",
            "That the shipment is properly named, classified, packaged, marked, and labeled in accordance with DOT rules",
            "That the truck engine passed emissions testing",
            "That highway tolls have been paid in advance",
        ),
        correctIndex = 1,
        explanation = "The shipper certification confirms full compliance with federal packaging and labeling laws.",
        manualCitation = "FMCSA CDL Manual Section 9.3.4 - Shipping Papers & Manifests"
    ),
    Question(
        id = "hm_105",
        category = CDLCategory.HAZMAT,
        subtopic = "Driving, Parking & Safe Operations",
        questionText = "When carrying placarded explosives, what must you do if you encounter a tunnel with HazMat restriction signs?",
        options = listOf(
            "Drive through quickly with headlights on high beam",
            "Obey local highway signs and use designated HazMat detour routes around restricted tunnels",
            "Honk horn continuously inside tunnel",
            "Turn off engine and coast through tunnel",
        ),
        correctIndex = 1,
        explanation = "Drivers must strictly follow local and state HazMat route restrictions around tunnels and bridges.",
        manualCitation = "FMCSA CDL Manual Section 9.6 - Driving & Parking Rules"
    ),
    Question(
        id = "hm_106",
        category = CDLCategory.HAZMAT,
        subtopic = "Emergencies, Leaks & Security",
        questionText = "In the Emergency Response Guidebook (ERG), how are chemicals indexed by their 4-digit UN/NA number?",
        options = listOf(
            "In the Yellow-bordered section",
            "In the Blue-bordered section",
            "In the Orange-bordered section",
            "In the Green-bordered section",
        ),
        correctIndex = 0,
        explanation = "The Yellow-indexed section of the ERG lists hazardous materials in numerical order by UN/NA identification numbers.",
        manualCitation = "FMCSA CDL Manual Section 9.7 - Emergencies, Spills & Security"
    ),
    Question(
        id = "hm_107",
        category = CDLCategory.HAZMAT,
        subtopic = "Emergencies, Leaks & Security",
        questionText = "In the Emergency Response Guidebook (ERG), how are chemicals indexed alphabetically by name?",
        options = listOf(
            "In the Yellow-bordered section",
            "In the Blue-bordered section",
            "In the Orange-bordered section",
            "In the Green-bordered section",
        ),
        correctIndex = 1,
        explanation = "The Blue-indexed section of the ERG lists hazardous materials alphabetically by proper shipping name.",
        manualCitation = "FMCSA CDL Manual Section 9.7 - Emergencies, Spills & Security"
    ),
    Question(
        id = "hm_108",
        category = CDLCategory.HAZMAT,
        subtopic = "Hazard Classes & Identification",
        questionText = "What is a Combustible Liquid?",
        options = listOf(
            "A liquid with a flash point at or above 100°F and below 200°F",
            "A liquid that freezes below 0°F",
            "A liquid that explodes when exposed to air",
            "A liquid that dissolves rubber tires",
        ),
        correctIndex = 0,
        explanation = "Combustible liquids have higher flash points (100°F–200°F) than Flammable liquids (below 100°F).",
        manualCitation = "FMCSA CDL Manual Section 9.2 - Hazard Classes Table"
    ),
    Question(
        id = "hm_109",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "What placard display rule applies when transporting empty bulk tank trailers that previously contained HazMat?",
        options = listOf(
            "Remove all placards immediately when liquid is emptied",
            "Must remain placarded until tank is cleaned and purged of all hazardous vapors",
            "Replace placards with 'EMPTY' paper banners",
            "Cover placards with duct tape",
        ),
        correctIndex = 1,
        explanation = "Empty bulk tanks must retain placards until thoroughly cleaned and purged of hazardous residues and vapors.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_110",
        category = CDLCategory.HAZMAT,
        subtopic = "Shipping Papers & Manifests",
        questionText = "What is the Proper Shipping Name for UN1075?",
        options = listOf(
            "Petroleum Gases, Liquefied (LPG / Propane)",
            "Sulfuric Acid",
            "Dynamite Explosives",
            "Sodium Cyanide",
        ),
        correctIndex = 0,
        explanation = "UN1075 is the official identification number for Liquefied Petroleum Gas (LPG / Propane).",
        manualCitation = "FMCSA CDL Manual Section 9.3.4 - Shipping Papers & Manifests"
    ),
    Question(
        id = "hm_111",
        category = CDLCategory.HAZMAT,
        subtopic = "Driving, Parking & Safe Operations",
        questionText = "Are passengers allowed to ride in a commercial motor vehicle transporting placarded hazardous materials?",
        options = listOf(
            "Anyone can ride if seatbelts are worn",
            "Only authorized individuals assigned by the motor carrier may ride",
            "Passengers are allowed on weekend trips only",
            "Up to 4 hitchhikers may be transported",
        ),
        correctIndex = 1,
        explanation = "Federal law prohibits carrying unauthorized passengers in vehicles transporting placarded hazardous materials.",
        manualCitation = "FMCSA CDL Manual Section 9.6 - Driving & Parking Rules"
    ),
    Question(
        id = "hm_112",
        category = CDLCategory.HAZMAT,
        subtopic = "Emergencies, Leaks & Security",
        questionText = "What should you do if your placarded HazMat vehicle is hijacked or targeted by suspicious persons?",
        options = listOf(
            "Notify law enforcement (911) and motor carrier dispatcher immediately",
            "Challenge suspicious individuals physically",
            "Drive vehicle into dark alleyways",
            "Remove placards and hide under trailer",
        ),
        correctIndex = 0,
        explanation = "Report suspicious activity, threats, or security breaches immediately to local 911 police and carrier security.",
        manualCitation = "FMCSA CDL Manual Section 9.7 - Emergencies, Spills & Security"
    ),
    Question(
        id = "hm_113",
        category = CDLCategory.HAZMAT,
        subtopic = "Hazard Classes & Identification",
        questionText = "What hazard division covers Poisonous Liquids or Solids (non-gas)?",
        options = listOf(
            "Division 6.1",
            "Division 2.3",
            "Division 4.2",
            "Division 8.0",
        ),
        correctIndex = 0,
        explanation = "Division 6.1 covers toxic / poisonous materials other than toxic gases.",
        manualCitation = "FMCSA CDL Manual Section 9.2 - Hazard Classes Table"
    ),
    Question(
        id = "hm_114",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "What graphic appears on an Explosives 1.1 placard?",
        options = listOf(
            "Exploding bomb graphic",
            "Flaming circle",
            "Skull graphic",
            "Gas cylinder",
        ),
        correctIndex = 0,
        explanation = "Class 1 explosives placards prominently display an exploding burst symbol.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_115",
        category = CDLCategory.HAZMAT,
        subtopic = "Shipping Papers & Manifests",
        questionText = "If shipping papers state '10 drums, UN1830, Sulfuric Acid, 8, PG II, Total Weight 4,000 lbs', what is the total weight of HazMat?",
        options = listOf(
            "10 lbs",
            "1,830 lbs",
            "4,000 lbs",
            "8,000 lbs",
        ),
        correctIndex = 2,
        explanation = "The shipping paper explicitly identifies total gross shipment weight as 4,000 lbs.",
        manualCitation = "FMCSA CDL Manual Section 9.3.4 - Shipping Papers & Manifests"
    ),
    Question(
        id = "hm_116",
        category = CDLCategory.HAZMAT,
        subtopic = "Driving, Parking & Safe Operations",
        questionText = "What distance must be maintained between two vehicles hauling Class 1 explosives when driving on open highways?",
        options = listOf(
            "At least 100 feet",
            "At least 300 feet",
            "At least 800 feet",
            "At least 1,000 feet",
        ),
        correctIndex = 1,
        explanation = "Maintain at least 300 feet separation distance between vehicles transporting explosives on open highways.",
        manualCitation = "FMCSA CDL Manual Section 9.6 - Driving & Parking Rules"
    ),
    Question(
        id = "hm_117",
        category = CDLCategory.HAZMAT,
        subtopic = "Emergencies, Leaks & Security",
        questionText = "What does an entry highlighting in GREEN inside ERG Yellow or Blue tables signify?",
        options = listOf(
            "The chemical is non-toxic",
            "The chemical is a Toxic Inhalation Hazard (TIH) requiring specific isolation distances in the Green section",
            "The chemical is safe to touch with bare hands",
            "The chemical fire can be extinguished with water",
        ),
        correctIndex = 1,
        explanation = "Green-highlighted chemicals in ERG tables are severe toxic inhalation hazards directing users to isolation tables.",
        manualCitation = "FMCSA CDL Manual Section 9.7 - Emergencies, Spills & Security"
    ),
    Question(
        id = "hm_118",
        category = CDLCategory.HAZMAT,
        subtopic = "Hazard Classes & Identification",
        questionText = "What is the definition of a Flash Point?",
        options = listOf(
            "The minimum temperature at which a liquid gives off sufficient vapor to form an ignitable mixture with air",
            "The temperature at which a liquid freezes solid",
            "The pressure at which a gas turns into liquid",
            "The weight of vapor compared to air",
        ),
        correctIndex = 0,
        explanation = "Flash point is the lowest temperature at which a liquid produces enough ignitable vapor to flash when exposed to a spark or flame.",
        manualCitation = "FMCSA CDL Manual Section 9.2 - Hazard Classes Table"
    ),
    Question(
        id = "hm_119",
        category = CDLCategory.HAZMAT,
        subtopic = "Placards & Labeling",
        questionText = "When can placards be placed on background surfaces that match the placard color?",
        options = listOf(
            "Anytime without restrictions",
            "Only if a solid contrasting border line surrounds the placard diamond",
            "Never under any circumstances",
            "Only on rear doors of white box trailers",
        ),
        correctIndex = 1,
        explanation = "Placards must stand out clearly; if background color matches placard edges, a contrasting border line must be used.",
        manualCitation = "FMCSA CDL Manual Section 9.3 - Placards & Labeling Rules"
    ),
    Question(
        id = "hm_120",
        category = CDLCategory.HAZMAT,
        subtopic = "Driving, Parking & Safe Operations",
        questionText = "Why is it vital for a driver transporting hazardous materials to understand DOT regulations thoroughly?",
        options = listOf(
            "To protect the driver, public safety, property, and the environment from hazardous material disasters",
            "To bypass weigh station scales without stopping",
            "To avoid paying fuel taxes at state borders",
            "To increase speed limits on rural highways",
        ),
        correctIndex = 0,
        explanation = "Strict compliance protects life, property, emergency responders, and the environment from catastrophic chemical accidents.",
        manualCitation = "FMCSA CDL Manual Section 9.6 - Driving & Parking Rules"
    ),
)
