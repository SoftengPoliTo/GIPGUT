package com.github.paolobd.intellijgamificationplugin.userInterface

import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType
import com.intellij.openapi.project.Project

class MyNotifier {

    companion object {
        fun notifyAchievement(project: Project?, content: String) {
            NotificationGroupManager.getInstance()
                .getNotificationGroup("Achievement Notification Group")
                .createNotification(content, NotificationType.INFORMATION)
                .notify(project)
        }
    }
}