package com.github.paolobd.intellijgamificationplugin.dataClasses

import com.github.paolobd.intellijgamificationplugin.enums.GlobalAchievement

data class ApplicationState (
    var userState: UserState = UserState(),
    var globalAchievements: List<AchievementState> = GlobalAchievement.values().map{
        AchievementState(it.ordinal, 0, false)
    }
)