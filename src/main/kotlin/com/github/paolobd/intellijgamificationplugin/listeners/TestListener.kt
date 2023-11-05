package com.github.paolobd.intellijgamificationplugin.listeners

import com.github.paolobd.intellijgamificationplugin.communication.Server
import com.github.paolobd.intellijgamificationplugin.enums.DailyAchievement
import com.github.paolobd.intellijgamificationplugin.enums.GlobalAchievement
import com.github.paolobd.intellijgamificationplugin.enums.ProjectAchievement
import com.github.paolobd.intellijgamificationplugin.services.AchievementService
import com.github.paolobd.intellijgamificationplugin.services.ApplicationStatePersistence
import com.github.paolobd.intellijgamificationplugin.services.ProjectStatePersistence
import com.intellij.execution.testframework.sm.runner.SMTRunnerEventsListener
import com.intellij.execution.testframework.sm.runner.SMTestProxy
import com.intellij.openapi.project.Project
import java.time.LocalTime

class TestListener(private val project: Project) : SMTRunnerEventsListener {
    private lateinit var server: Server
    override fun onTestingStarted(testsRoot: SMTestProxy.SMRootTestProxy) {
        server = Server()
        server.start()
        ApplicationStatePersistence.getInstance().checkAndUpdateDailyGUI()
        println("Server started!")
    }

    override fun onTestingFinished(testsRoot: SMTestProxy.SMRootTestProxy) {
        server.stop()
        println("Server stopped!")
    }

    override fun onTestsCountInSuite(count: Int) {
    }

    override fun onTestStarted(test: SMTestProxy) {
    }

    override fun onTestFinished(test: SMTestProxy) {
        println("Test finished!")
        val eventList = Server.events
        println("Events: $eventList")
        val service = project.getService(AchievementService::class.java)

        if (eventList.isNotEmpty()) {
            AchievementService().addExp(null, false, GlobalAchievement.FIRST_SELENIUM.achievement, 1)

            val daily = ApplicationStatePersistence.getInstance().state.dailyAchievement

            val currentHour = LocalTime.now().hour

            if (currentHour in 6..8) {
                AchievementService().addExp(null, false, GlobalAchievement.EARLY_TEST.achievement, 1)

                val achEarly = DailyAchievement.DAILY_EARLY.achievement
                if (daily.state.id == achEarly.id) {
                    AchievementService().addExp(null, true, achEarly, 1)
                }

            } else if (currentHour >= 23 || currentHour <= 3) {
                AchievementService().addExp(null, false, GlobalAchievement.LATE_TEST.achievement, 1)

                val achLate = DailyAchievement.DAILY_LATE.achievement
                if (daily.state.id == achLate.id) {
                    AchievementService().addExp(null, true, achLate, 1)
                }
            }


            val projectState = ProjectStatePersistence.getInstance(project).state

            if (test.isPassed) {
                if (!projectState.testState.contains(test.name)) {
                    projectState.testState[test.name] = true
                    AchievementService().addExp(null, false, GlobalAchievement.NUM_TEST_PASSED.achievement, 1)
                    AchievementService().addExp(project, false, ProjectAchievement.NUM_TEST_PASSED.achievement, 1)
                    AchievementService().addExp(null, true, DailyAchievement.DAILY_TEST_PASSED.achievement, 1)
                } else {
                    if (projectState.testState[test.name] == false) {
                        projectState.testState[test.name] = true
                        AchievementService().addExp(null, false, GlobalAchievement.NUM_TEST_FIXED.achievement, 1)
                        AchievementService().addExp(project, false, ProjectAchievement.NUM_TEST_FIXED.achievement, 1)
                    }
                }
            } else {
                if (!projectState.testState.contains(test.name)) {
                    projectState.testState[test.name] = false
                    AchievementService().addExp(null, false, GlobalAchievement.FIRST_FAILED.achievement, 1)
                } else {
                    if (projectState.testState[test.name] == true) {
                        projectState.testState[test.name] = false
                    }
                }
            }
            service.analyzeEvents(project, eventList)
        }
    }

    override fun onTestFailed(test: SMTestProxy) {
    }

    override fun onTestIgnored(test: SMTestProxy) {
    }

    override fun onSuiteFinished(suite: SMTestProxy) {
    }

    override fun onSuiteStarted(suite: SMTestProxy) {
    }

    override fun onCustomProgressTestsCategory(categoryName: String?, testCount: Int) {
    }

    override fun onCustomProgressTestStarted() {
    }

    override fun onCustomProgressTestFailed() {
    }

    override fun onCustomProgressTestFinished() {
    }

    override fun onSuiteTreeNodeAdded(testProxy: SMTestProxy?) {
    }

    override fun onSuiteTreeStarted(suite: SMTestProxy?) {
    }

}