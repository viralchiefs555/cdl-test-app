package com.example

import com.example.data.models.CDLCategory
import com.example.data.repository.CDLQuestionRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CDLTest {

    @Test
    fun testPassingScoreStandardIs80Percent() {
        assertEquals(80, CDLCategory.GENERAL_KNOWLEDGE.passingScorePercent)
        assertEquals(80, CDLCategory.AIR_BRAKES.passingScorePercent)
        assertEquals(80, CDLCategory.HAZMAT.passingScorePercent)

        // General Knowledge: 50 questions, 40 to pass (80%)
        val gkTotal = 50
        val gkPassing = 40
        assertTrue((gkPassing.toFloat() / gkTotal * 100).toInt() >= CDLCategory.GENERAL_KNOWLEDGE.passingScorePercent)
        assertFalse(((gkPassing - 1).toFloat() / gkTotal * 100).toInt() >= CDLCategory.GENERAL_KNOWLEDGE.passingScorePercent)

        // Air Brakes: 25 questions, 20 to pass (80%)
        val abTotal = 25
        val abPassing = 20
        assertTrue((abPassing.toFloat() / abTotal * 100).toInt() >= CDLCategory.AIR_BRAKES.passingScorePercent)
        assertFalse(((abPassing - 1).toFloat() / abTotal * 100).toInt() >= CDLCategory.AIR_BRAKES.passingScorePercent)

        // HazMat: 30 questions, 24 to pass (80%)
        val hmTotal = 30
        val hmPassing = 24
        assertTrue((hmPassing.toFloat() / hmTotal * 100).toInt() >= CDLCategory.HAZMAT.passingScorePercent)
        assertFalse(((hmPassing - 1).toFloat() / hmTotal * 100).toInt() >= CDLCategory.HAZMAT.passingScorePercent)
    }

    @Test
    fun testQuestionsHaveValidAnswersAndCitations() {
        val allQuestions = CDLQuestionRepository.questions
        assertTrue(allQuestions.isNotEmpty())

        for (q in allQuestions) {
            assertTrue("Options must have at least 3 choices", q.options.size >= 3)
            assertTrue("Correct index must be within range", q.correctIndex in q.options.indices)
            assertNotNull("Citation must be present", q.manualCitation)
            assertTrue("Citation must not be empty", q.manualCitation.isNotBlank())
            assertTrue("Explanation must not be empty", q.explanation.isNotBlank())
        }
    }

    @Test
    fun testCategoryQuestionsRetrieval() {
        val gk = CDLQuestionRepository.getQuestionsByCategory(CDLCategory.GENERAL_KNOWLEDGE)
        val ab = CDLQuestionRepository.getQuestionsByCategory(CDLCategory.AIR_BRAKES)
        val hm = CDLQuestionRepository.getQuestionsByCategory(CDLCategory.HAZMAT)

        assertTrue(gk.isNotEmpty())
        assertTrue(ab.isNotEmpty())
        assertTrue(hm.isNotEmpty())

        assertTrue(gk.all { it.category == CDLCategory.GENERAL_KNOWLEDGE })
        assertTrue(ab.all { it.category == CDLCategory.AIR_BRAKES })
        assertTrue(hm.all { it.category == CDLCategory.HAZMAT })
    }

    @Test
    fun testGeneralKnowledgeFiftyQuestionsIntegrity() {
        val gkQuestions = CDLQuestionRepository.getQuestionsByCategory(CDLCategory.GENERAL_KNOWLEDGE)
        assertEquals(50, gkQuestions.size)

        gkQuestions.forEachIndexed { index, q ->
            assertTrue("Question ${index + 1} has valid options", q.options.size >= 4)
            assertTrue("Question ${index + 1} correctIndex in bounds", q.correctIndex in q.options.indices)
            assertTrue("Question ${index + 1} questionText not blank", q.questionText.isNotBlank())
            assertTrue("Question ${index + 1} explanation not blank", q.explanation.isNotBlank())
        }

        // Verify key phases are represented
        val subtopics = gkQuestions.map { it.subtopic }.toSet()
        assertTrue(subtopics.any { it.contains("Vehicle Inspection") })
        assertTrue(subtopics.any { it.contains("Control & Vehicle Handling") })
        assertTrue(subtopics.any { it.contains("Mountain Driving") })
        assertTrue(subtopics.any { it.contains("Emergencies, Skids") })
        assertTrue(subtopics.any { it.contains("Railway Crossings") })
        assertTrue(subtopics.any { it.contains("Cargo Handling") })
        assertTrue(subtopics.any { it.contains("Hazardous Materials") })
    }

    @Test
    fun testAirBrakesOneHundredTwentyQuestionsIntegrity() {
        val abQuestions = CDLQuestionRepository.getQuestionsByCategory(CDLCategory.AIR_BRAKES)
        assertEquals(120, abQuestions.size)

        abQuestions.forEachIndexed { index, q ->
            assertTrue("Question ${index + 1} has at least 4 options", q.options.size >= 4)
            assertTrue("Question ${index + 1} correctIndex in bounds", q.correctIndex in q.options.indices)
            assertTrue("Question ${index + 1} questionText not blank", q.questionText.isNotBlank())
            assertTrue("Question ${index + 1} explanation not blank", q.explanation.isNotBlank())
            assertTrue("Question ${index + 1} manualCitation not blank", q.manualCitation.isNotBlank())
        }

        val subtopics = abQuestions.map { it.subtopic }.toSet()
        assertTrue(subtopics.contains("Components & Mechanics"))
        assertTrue(subtopics.contains("Sub-Braking Systems"))
        assertTrue(subtopics.contains("Inspection & Leak Tests"))
        assertTrue(subtopics.contains("Safe Driving & Emergency Operations"))
    }

    @Test
    fun testHazardousMaterialsOneHundredTwentyQuestionsIntegrity() {
        val hmQuestions = CDLQuestionRepository.getQuestionsByCategory(CDLCategory.HAZMAT)
        assertEquals(120, hmQuestions.size)

        hmQuestions.forEachIndexed { index, q ->
            assertTrue("Question ${index + 1} has at least 4 options", q.options.size >= 4)
            assertTrue("Question ${index + 1} correctIndex in bounds", q.correctIndex in q.options.indices)
            assertTrue("Question ${index + 1} questionText not blank", q.questionText.isNotBlank())
            assertTrue("Question ${index + 1} explanation not blank", q.explanation.isNotBlank())
            assertTrue("Question ${index + 1} manualCitation not blank", q.manualCitation.isNotBlank())
        }

        val subtopics = hmQuestions.map { it.subtopic }.toSet()
        assertTrue(subtopics.contains("Hazard Classes & Identification"))
        assertTrue(subtopics.contains("Placards & Labeling"))
        assertTrue(subtopics.contains("Shipping Papers & Manifests"))
        assertTrue(subtopics.contains("Driving, Parking & Safe Operations"))
        assertTrue(subtopics.contains("Emergencies, Leaks & Security"))
    }

    @Test
    fun testCombinationVehiclesOneHundredTwentyQuestionsIntegrity() {
        val cvQuestions = CDLQuestionRepository.getQuestionsByCategory(CDLCategory.COMBINATION_VEHICLES)
        assertEquals(120, cvQuestions.size)

        cvQuestions.forEachIndexed { index, q ->
            assertTrue("Question ${index + 1} has at least 4 options", q.options.size >= 4)
            assertTrue("Question ${index + 1} correctIndex in bounds", q.correctIndex in q.options.indices)
            assertTrue("Question ${index + 1} questionText not blank", q.questionText.isNotBlank())
            assertTrue("Question ${index + 1} explanation not blank", q.explanation.isNotBlank())
            assertTrue("Question ${index + 1} manualCitation not blank", q.manualCitation.isNotBlank())
        }

        val subtopics = cvQuestions.map { it.subtopic }.toSet()
        assertTrue(subtopics.contains("Combination Vehicle Safety & Driving"))
        assertTrue(subtopics.contains("Combination Air Brake Systems"))
        assertTrue(subtopics.contains("Coupling & Uncoupling"))
        assertTrue(subtopics.contains("Inspecting Combination Vehicles"))
    }

    @Test
    fun testGeneralKnowledgeOptionLengthBalanceAndRandomization() {
        val gkQuestions = CDLQuestionRepository.getQuestionsByCategory(CDLCategory.GENERAL_KNOWLEDGE)
        assertEquals(50, gkQuestions.size)

        // Verify correct answer distribution across all 4 positions (0, 1, 2, 3)
        val indexCounts = gkQuestions.groupingBy { it.correctIndex }.eachCount()
        for (i in 0..3) {
            val count = indexCounts[i] ?: 0
            assertTrue("Position $i should have at least 10 questions, found $count", count >= 10)
        }

        // Verify that the correct option is not strictly longer than distractors in more than 30% of questions
        var correctIsStrictlyLongestCount = 0
        for (q in gkQuestions) {
            val correctLen = q.options[q.correctIndex].length
            val otherLens = q.options.filterIndexed { idx, _ -> idx != q.correctIndex }.map { it.length }
            if (correctLen > otherLens.max()) {
                correctIsStrictlyLongestCount++
            }
            // Length consistency: maximum length difference between any two options in a question is tight (under 20 chars)
            val minLen = q.options.minOf { it.length }
            val maxLen = q.options.maxOf { it.length }
            assertTrue(
                "Options in question ${q.id} should have consistent length (diff was ${maxLen - minLen})",
                maxLen - minLen <= 15
            )
        }

        assertTrue(
            "Correct answer is strictly longest in too many questions ($correctIsStrictlyLongestCount / 50)",
            correctIsStrictlyLongestCount <= 16
        )
    }

    @Test
    fun testAirBrakesOptionLengthBalanceAndRandomization() {
        val abQuestions = CDLQuestionRepository.getQuestionsByCategory(CDLCategory.AIR_BRAKES)
        assertEquals(120, abQuestions.size)

        // Verify correct answer distribution across all 4 positions (0, 1, 2, 3)
        val indexCounts = abQuestions.groupingBy { it.correctIndex }.eachCount()
        for (i in 0..3) {
            val count = indexCounts[i] ?: 0
            assertTrue("Position $i should have at least 25 questions, found $count", count >= 25)
        }

        var correctIsStrictlyLongestCount = 0
        for (q in abQuestions) {
            val correctLen = q.options[q.correctIndex].length
            val otherLens = q.options.filterIndexed { idx, _ -> idx != q.correctIndex }.map { it.length }
            if (correctLen > otherLens.max()) {
                correctIsStrictlyLongestCount++
            }
            // Length consistency: maximum length difference between any two options in a question is tight (under 15 chars)
            val minLen = q.options.minOf { it.length }
            val maxLen = q.options.maxOf { it.length }
            assertTrue(
                "Options in question ${q.id} should have consistent length (diff was ${maxLen - minLen})",
                maxLen - minLen <= 15
            )
        }

        assertTrue(
            "Correct answer is strictly longest in too many questions ($correctIsStrictlyLongestCount / 120)",
            correctIsStrictlyLongestCount <= 50
        )
    }

    @Test
    fun testCombinationVehiclesOptionLengthBalanceAndRandomization() {
        val cvQuestions = CDLQuestionRepository.getQuestionsByCategory(CDLCategory.COMBINATION_VEHICLES)
        assertEquals(120, cvQuestions.size)

        // Verify correct answer distribution across all 4 positions (0, 1, 2, 3)
        val indexCounts = cvQuestions.groupingBy { it.correctIndex }.eachCount()
        for (i in 0..3) {
            val count = indexCounts[i] ?: 0
            assertTrue("Position $i should have at least 25 questions, found $count", count >= 25)
        }

        var correctIsStrictlyLongestCount = 0
        for (q in cvQuestions) {
            val correctLen = q.options[q.correctIndex].length
            val otherLens = q.options.filterIndexed { idx, _ -> idx != q.correctIndex }.map { it.length }
            if (correctLen > otherLens.max()) {
                correctIsStrictlyLongestCount++
            }
            // Length consistency: maximum length difference between any two options in a question is tight (under 15 chars)
            val minLen = q.options.minOf { it.length }
            val maxLen = q.options.maxOf { it.length }
            assertTrue(
                "Options in question ${q.id} should have consistent length (diff was ${maxLen - minLen})",
                maxLen - minLen <= 15
            )
        }

        assertTrue(
            "Correct answer is strictly longest in too many questions ($correctIsStrictlyLongestCount / 120)",
            correctIsStrictlyLongestCount <= 50
        )
    }

    @Test
    fun testQuestionLengthChoiceRetrievalAndPassingMath() {
        val testLengths = listOf(10, 20, 50)
        val categories = listOf(
            CDLCategory.GENERAL_KNOWLEDGE,
            CDLCategory.AIR_BRAKES,
            CDLCategory.HAZMAT,
            CDLCategory.COMBINATION_VEHICLES
        )

        for (cat in categories) {
            for (len in testLengths) {
                val questions = CDLQuestionRepository.getQuestionsForExam(cat, len)
                assertEquals("Category ${cat.name} with requested length $len", len, questions.size)

                // 80% passing threshold math
                val passingScore = (len * 0.8f).toInt()
                val maxAllowedErrors = (len * 0.2f).toInt()

                // When exactly at passing threshold:
                val passPct = ((passingScore.toFloat() / len) * 100).toInt()
                assertTrue(passPct >= 80)

                // When 1 below passing threshold:
                val failScore = passingScore - 1
                val failPct = ((failScore.toFloat() / len) * 100).toInt()
                assertTrue(failPct < 80)

                // Error count check:
                assertEquals(len - passingScore, maxAllowedErrors)
            }
        }
    }

    @Test
    fun testAllFluidLevelsZoneAndComponents() {
        // First zone must be ALL_FLUID_LEVELS
        assertEquals(com.example.pretrip.InspectionZone.ALL_FLUID_LEVELS, com.example.pretrip.InspectionZone.values()[0])

        val allComponents = com.example.pretrip.CDLInspectionRepository.loadInspectionMarkers()
        val fluidComponents = allComponents.filter { it.zone == com.example.pretrip.InspectionZone.ALL_FLUID_LEVELS }
        assertEquals(4, fluidComponents.size)

        val ids = fluidComponents.map { it.id }
        assertTrue("Contains dipstick", ids.contains("dipstick"))
        assertTrue("Contains coolant recovery tank", ids.contains("coolant_recovery_tank"))
        assertTrue("Contains power steering reservoir", ids.contains("power-steering-reservoir"))
        assertTrue("Contains engine fluid leaks", ids.contains("eng_fluid_leaks"))

        // Engine oil dipstick has quiz and recitation
        val dipstick = fluidComponents.first { it.id == "dipstick" }
        assertNotNull(dipstick.quiz)
        assertTrue(dipstick.mandatoryVerbatimPhrases.isNotEmpty())

        // Coolant tank has quiz and recitation
        val coolant = fluidComponents.first { it.id == "coolant_recovery_tank" }
        assertNotNull(coolant.quiz)
        assertTrue(coolant.mandatoryVerbatimPhrases.isNotEmpty())

        // Power steering has quiz and recitation
        val ps = fluidComponents.first { it.id == "power-steering-reservoir" }
        assertNotNull(ps.quiz)
        assertTrue(ps.mandatoryVerbatimPhrases.isNotEmpty())
    }

    @Test
    fun testFluidLevelsDipstickSimulationFlow() {
        var state = com.example.pretrip.FluidLevelsSimState()
        assertEquals(com.example.pretrip.DipstickStep.IN_TUBE_INITIAL, state.dipstickStep)
        assertFalse(state.dipstickCompleted)

        // Step 1: Pull out
        val step1 = when (state.dipstickStep) {
            com.example.pretrip.DipstickStep.IN_TUBE_INITIAL -> com.example.pretrip.DipstickStep.FIRST_PULL_DIRTY
            else -> state.dipstickStep
        }
        state = state.copy(dipstickStep = step1)
        assertEquals(com.example.pretrip.DipstickStep.FIRST_PULL_DIRTY, state.dipstickStep)

        // Step 2: Wipe clean
        val step2 = when (state.dipstickStep) {
            com.example.pretrip.DipstickStep.FIRST_PULL_DIRTY -> com.example.pretrip.DipstickStep.WIPED_CLEAN
            else -> state.dipstickStep
        }
        state = state.copy(dipstickStep = step2)
        assertEquals(com.example.pretrip.DipstickStep.WIPED_CLEAN, state.dipstickStep)

        // Step 3: Re-insert
        val step3 = when (state.dipstickStep) {
            com.example.pretrip.DipstickStep.WIPED_CLEAN -> com.example.pretrip.DipstickStep.REINSERTED
            else -> state.dipstickStep
        }
        state = state.copy(dipstickStep = step3)
        assertEquals(com.example.pretrip.DipstickStep.REINSERTED, state.dipstickStep)

        // Step 4: Final pull
        val step4 = when (state.dipstickStep) {
            com.example.pretrip.DipstickStep.REINSERTED -> com.example.pretrip.DipstickStep.FINAL_PULL_READING
            else -> state.dipstickStep
        }
        state = state.copy(dipstickStep = step4)
        assertEquals(com.example.pretrip.DipstickStep.FINAL_PULL_READING, state.dipstickStep)

        // Step 5: Verify safe operating level
        val step5 = when (state.dipstickStep) {
            com.example.pretrip.DipstickStep.FINAL_PULL_READING -> com.example.pretrip.DipstickStep.VERIFIED_SAFE
            else -> state.dipstickStep
        }
        state = state.copy(dipstickStep = step5, dipstickCompleted = true)
        assertEquals(com.example.pretrip.DipstickStep.VERIFIED_SAFE, state.dipstickStep)
        assertTrue(state.dipstickCompleted)

        // Check Coolant & Power Steering completing full session
        state = state.copy(
            isCoolantSightGlassChecked = true,
            isCoolantCapTight = true,
            isCoolantHosesInspected = true,
            coolantCompleted = true,
            isPowerSteeringLevelChecked = true,
            isPowerSteeringHosesChecked = true,
            isPowerSteeringFittingsChecked = true,
            powerSteeringCompleted = true
        )
        assertTrue(state.allFluidsCompleted)
    }

    @Test
    fun testSteeringComponentsZoneAndComponents() {
        // Second zone must be STEERING_COMPONENTS
        assertEquals(com.example.pretrip.InspectionZone.STEERING_COMPONENTS, com.example.pretrip.InspectionZone.values()[1])

        val allComponents = com.example.pretrip.CDLInspectionRepository.loadInspectionMarkers()
        val steeringComponents = allComponents.filter { it.zone == com.example.pretrip.InspectionZone.STEERING_COMPONENTS }
        assertEquals(2, steeringComponents.size)

        val ids = steeringComponents.map { it.id }
        assertTrue("Contains steering gear box & hoses", ids.contains("steering_gear_box"))
        assertTrue("Contains Pitman arm, drag link, & tie rod", ids.contains("steering_linkage"))

        steeringComponents.forEach { component ->
            assertNotNull("${component.id} has quiz", component.quiz)
            assertTrue("${component.id} has options", component.quiz.options.size >= 4)
            assertTrue("${component.id} has verbatim phrases", component.mandatoryVerbatimPhrases.isNotEmpty())
            assertTrue("${component.id} has FMCSA criteria", component.fmcsaCriteria.isNotBlank())
        }
    }

    @Test
    fun testSteeringComponentsSimulationFlow() {
        var sim = com.example.pretrip.SteeringComponentsSimState()
        assertFalse(sim.allGearBoxCompleted)
        assertFalse(sim.allLinkageCompleted)
        assertFalse(sim.isAllCompleted)

        // Complete Steering Gear Box & Hoses
        val gearBoxDone = sim.gearBoxState.copy(
            isFrameMountBoltsChecked = true,
            isGearBoxCastingChecked = true,
            isHosesInspected = true,
            isShaftPlayChecked = true,
            isCompleted = true
        )
        sim = sim.copy(gearBoxState = gearBoxDone)
        assertTrue(sim.allGearBoxCompleted)
        assertFalse(sim.isAllCompleted)

        // Complete Linkages (Pitman arm, drag link, tie rod, joint play shake test)
        val linkageDone = sim.linkageState.copy(
            isPitmanArmChecked = true,
            isDragLinkChecked = true,
            isTieRodChecked = true,
            isJointPlayTested = true,
            measuredFreePlayMm = 0.5f,
            isCompleted = true
        )
        sim = sim.copy(linkageState = linkageDone)
        assertTrue(sim.allLinkageCompleted)
        assertTrue(sim.isAllCompleted)
    }

    @Test
    fun testBeltsAndHosesZoneAndComponents() {
        // Third zone is BELTS_AND_HOSES
        assertEquals(com.example.pretrip.InspectionZone.BELTS_AND_HOSES, com.example.pretrip.InspectionZone.values()[2])

        val allComponents = com.example.pretrip.CDLInspectionRepository.loadInspectionMarkers()
        val beltsHosesComponents = allComponents.filter { it.zone == com.example.pretrip.InspectionZone.BELTS_AND_HOSES }
        assertEquals(6, beltsHosesComponents.size)

        val ids = beltsHosesComponents.map { it.id }
        assertTrue("Contains alternator belt", ids.contains("belt_alternator"))
        assertTrue("Contains water pump belt", ids.contains("belt_water_pump"))
        assertTrue("Contains air compressor belt", ids.contains("belt_air_compressor"))
        assertTrue("Contains coolant hoses", ids.contains("hose_coolant"))
        assertTrue("Contains heater hoses", ids.contains("hose_heater"))
        assertTrue("Contains oil hoses", ids.contains("hose_oil"))

        // Verify quizzes and FMCSA verbatim criteria are populated
        beltsHosesComponents.forEach { component ->
            assertNotNull("${component.id} has quiz", component.quiz)
            assertTrue("${component.id} has options", component.quiz.options.size >= 4)
            assertTrue("${component.id} has verbatim phrases", component.mandatoryVerbatimPhrases.isNotEmpty())
            assertTrue("${component.id} has FMCSA criteria", component.fmcsaCriteria.isNotBlank())
        }
    }

    @Test
    fun testBeltsAndHosesSimulationFlow() {
        var sim = com.example.pretrip.BeltsAndHosesSimState()
        assertFalse(sim.allBeltsCompleted)
        assertFalse(sim.allHosesCompleted)
        assertFalse(sim.isAllCompleted)

        // Test belt deflection threshold (<= 0.75" is compliant)
        assertEquals(0.75f, com.example.pretrip.BeltComponentType.ALTERNATOR_BELT.maxAllowedPlayInches)
        assertEquals(0.75f, com.example.pretrip.BeltComponentType.WATER_PUMP_BELT.maxAllowedPlayInches)
        assertEquals(0.75f, com.example.pretrip.BeltComponentType.AIR_COMPRESSOR_BELT.maxAllowedPlayInches)

        // Complete Alternator belt
        val altDone = sim.alternatorBelt.copy(
            isFrayingChecked = true,
            isCracksCutsChecked = true,
            isExcessiveWearChecked = true,
            isPlayTested = true,
            measuredPlayInches = 0.50f,
            isCompleted = true
        )
        // Complete Water Pump belt
        val wpDone = sim.waterPumpBelt.copy(
            isFrayingChecked = true,
            isCracksCutsChecked = true,
            isExcessiveWearChecked = true,
            isPlayTested = true,
            measuredPlayInches = 0.60f,
            isCompleted = true
        )
        // Complete Compressor belt
        val acDone = sim.airCompressorBelt.copy(
            isFrayingChecked = true,
            isCracksCutsChecked = true,
            isExcessiveWearChecked = true,
            isPlayTested = true,
            measuredPlayInches = 0.45f,
            isCompleted = true
        )

        sim = sim.copy(
            alternatorBelt = altDone,
            waterPumpBelt = wpDone,
            airCompressorBelt = acDone
        )
        assertTrue(sim.allBeltsCompleted)
        assertFalse(sim.isAllCompleted)

        // Complete Hoses (coolant, heater, oil)
        val coolantDone = sim.coolantHoses.copy(
            isClampsTightChecked = true,
            isDryRotChecked = true,
            isBubblingChecked = true,
            isLeaksChecked = true,
            isCompleted = true
        )
        val heaterDone = sim.heaterHoses.copy(
            isClampsTightChecked = true,
            isDryRotChecked = true,
            isBubblingChecked = true,
            isLeaksChecked = true,
            isCompleted = true
        )
        val oilDone = sim.oilHoses.copy(
            isClampsTightChecked = true,
            isDryRotChecked = true,
            isBubblingChecked = true,
            isLeaksChecked = true,
            isCompleted = true
        )

        sim = sim.copy(
            coolantHoses = coolantDone,
            heaterHoses = heaterDone,
            oilHoses = oilDone
        )
        assertTrue(sim.allHosesCompleted)
        assertTrue(sim.isAllCompleted)
    }

    @Test
    fun testFrontSuspensionZoneAndComponents() {
        // Fourth zone must be FRONT_SUSPENSION
        assertEquals(com.example.pretrip.InspectionZone.FRONT_SUSPENSION, com.example.pretrip.InspectionZone.values()[3])

        val allComponents = com.example.pretrip.CDLInspectionRepository.loadInspectionMarkers()
        val suspensionComponents = allComponents.filter { it.zone == com.example.pretrip.InspectionZone.FRONT_SUSPENSION }
        assertEquals(3, suspensionComponents.size)

        val ids = suspensionComponents.map { it.id }
        assertTrue("Contains leaf springs", ids.contains("susp_leaf_springs"))
        assertTrue("Contains U-bolts & spring mounts", ids.contains("susp_ubolts_mounts"))
        assertTrue("Contains shock absorber", ids.contains("susp_shock_absorber"))

        suspensionComponents.forEach { component ->
            assertNotNull("${component.id} has quiz", component.quiz)
            assertTrue("${component.id} has at least 4 options", component.quiz.options.size >= 4)
            assertTrue("${component.id} has verbatim recitation phrases", component.mandatoryVerbatimPhrases.isNotEmpty())
            assertTrue("${component.id} has FMCSA criteria", component.fmcsaCriteria.isNotBlank())
        }
    }

    @Test
    fun testFrontSuspensionSimulationFlow() {
        var sim = com.example.pretrip.FrontSuspensionSimState()
        assertFalse(sim.allLeafSpringsCompleted)
        assertFalse(sim.allUBoltsCompleted)
        assertFalse(sim.allShockCompleted)
        assertFalse(sim.isAllCompleted)

        // Complete Leaf Springs checks
        val springsDone = sim.leafSpringsState.copy(
            isCracksBreaksChecked = true,
            isShiftingChecked = true,
            isMissingLeavesChecked = true,
            isCompleted = true
        )
        sim = sim.copy(leafSpringsState = springsDone)
        assertTrue(sim.allLeafSpringsCompleted)
        assertFalse(sim.isAllCompleted)

        // Complete U-Bolts & Spring Mounts checks
        val uboltsDone = sim.uBoltMountsState.copy(
            isUBoltsTightChecked = true,
            isRustTrailsChecked = true,
            isSpringHangersChecked = true,
            isBushingPinsChecked = true,
            isCompleted = true
        )
        sim = sim.copy(uBoltMountsState = uboltsDone)
        assertTrue(sim.allUBoltsCompleted)
        assertFalse(sim.isAllCompleted)

        // Complete Shock Absorber checks
        val shockDone = sim.shockState.copy(
            isTopMountChecked = true,
            isBottomMountChecked = true,
            isOilLeaksChecked = true,
            isRubberBushingsChecked = true,
            isCompleted = true
        )
        sim = sim.copy(shockState = shockDone)
        assertTrue(sim.allShockCompleted)
        assertTrue(sim.isAllCompleted)
    }

    @Test
    fun testFrontBrakesZoneAndComponents() {
        // Fifth zone must be FRONT_BRAKES_AND_AXLE
        assertEquals(com.example.pretrip.InspectionZone.FRONT_BRAKES_AND_AXLE, com.example.pretrip.InspectionZone.values()[4])

        val allComponents = com.example.pretrip.CDLInspectionRepository.loadInspectionMarkers()
        val frontBrakeComponents = allComponents.filter { it.zone == com.example.pretrip.InspectionZone.FRONT_BRAKES_AND_AXLE }
        assertEquals(4, frontBrakeComponents.size)

        val ids = frontBrakeComponents.map { it.id }
        assertTrue("Contains brake chamber & slack adjuster", ids.contains("brake-chamber"))
        assertTrue("Contains brake drum & linings", ids.contains("front_brake_linings"))
        assertTrue("Contains steer tire", ids.contains("steer-tire"))
        assertTrue("Contains rim & hub oil seal", ids.contains("front_rim_hub_seal"))

        frontBrakeComponents.forEach { component ->
            assertNotNull("${component.id} has quiz", component.quiz)
            assertTrue("${component.id} has at least 4 options", component.quiz.options.size >= 4)
            assertTrue("${component.id} has verbatim recitation phrases", component.mandatoryVerbatimPhrases.isNotEmpty())
            assertTrue("${component.id} has FMCSA criteria", component.fmcsaCriteria.isNotBlank())
        }
    }

    @Test
    fun testFrontBrakesSimulationFlow() {
        var sim = com.example.pretrip.FrontBrakesSimState()
        assertFalse(sim.allChamberCompleted)
        assertFalse(sim.allDrumLiningsCompleted)
        assertFalse(sim.allSteerTireCompleted)
        assertFalse(sim.allRimHubCompleted)
        assertFalse(sim.isAllCompleted)

        // 1. Chamber & Slack Adjuster
        val chamberDone = sim.chamberState.copy(
            isChamberMountedTight = true,
            isNoDentsOrAirLeaks = true,
            isPushrodAndClevisPinChecked = true,
            measuredPushrodTravelInches = 0.75f,
            isManualPullTested = true,
            isCompleted = true
        )
        sim = sim.copy(chamberState = chamberDone)
        assertTrue(sim.allChamberCompleted)
        assertFalse(sim.isAllCompleted)

        // 2. Drum & Linings (>= 1/4" thickness)
        val drumDone = sim.drumLiningsState.copy(
            isDrumCracksChecked = true,
            isOilGreaseContaminationChecked = true,
            measuredLiningThicknessInches = 0.35f,
            isLiningThicknessMeasured = true,
            isCompleted = true
        )
        sim = sim.copy(drumLiningsState = drumDone)
        assertTrue(sim.allDrumLiningsCompleted)
        assertFalse(sim.isAllCompleted)

        // 3. Steer Tire (>= 4/32" tread, ABC check, cold PSI)
        val tireDone = sim.steerTireState.copy(
            measuredTreadDepth32nds = 5,
            isTreadDepthGaugeChecked = true,
            isAbcSidewallChecked = true,
            isExposedFabricChecked = true,
            isRecapProhibitionChecked = true,
            measuredPsi = 105,
            isAirPressureGaugeChecked = true,
            isValveCapPresent = true,
            isCompleted = true
        )
        sim = sim.copy(steerTireState = tireDone)
        assertTrue(sim.allSteerTireCompleted)
        assertFalse(sim.isAllCompleted)

        // 4. Rim & Hub Oil Seal
        val rimDone = sim.rimHubState.copy(
            isRimWeldsAndCracksChecked = true,
            isAllLugNutsPresentAndTight = true,
            isRustStreaksChecked = true,
            isHubSightGlassLevelChecked = true,
            isHubSealLeaksChecked = true,
            isCompleted = true
        )
        sim = sim.copy(rimHubState = rimDone)
        assertTrue(sim.allRimHubCompleted)
        assertTrue(sim.isAllCompleted)
    }
}

