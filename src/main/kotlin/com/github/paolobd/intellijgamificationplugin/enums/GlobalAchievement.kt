package com.github.paolobd.intellijgamificationplugin.enums

import com.github.paolobd.intellijgamificationplugin.dataClasses.Achievement

enum class GlobalAchievement(
    val achievement: Achievement
) {
    NUM_CLICKS(
        Achievement(
            "Night Owl",
            "Execute a test between 11 p.m and 3 a.m",
            "owl.svg",
            1,
            100
        )
    ),
    NUM_SITES(
        Achievement(
            "Slow Testing",
            "A test took more than 1 minute",
            "slow.svg",
            5,
            20
        )
    ),
    NUM_LOCATOR_ALL(
        Achievement(
            "Fast Testing",
            "A test took more than 1 second",
            "fast.svg",
            200,
            30
        )
    ),
    NUM_LOCATOR_ID(
        Achievement(
            "High Success!",
            "Number of tests passed",
            "success.svg",
            100,
            30
        )
    ),
    NUM_LOCATOR_XPATH(
        Achievement(
            "Failure",
            "Number of tests failed",
            "failed.svg",
            100,
            30
        )
    ),
    NUM_LOCATOR_CSS(
        Achievement(
            "More Testing!",
            "Executed a suite with more than 10 test",
            "test.svg",
            100,
            30
        )
    ),
}