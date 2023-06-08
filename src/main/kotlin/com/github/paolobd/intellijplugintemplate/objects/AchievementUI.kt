package com.github.paolobd.intellijplugintemplate.objects

import javax.swing.JLabel
import javax.swing.JProgressBar

class AchievementUI(val id: Int, private var progressBar: JProgressBar, private var progressLabel: JLabel) {
    fun updateProgress(currentExp: Int) {
        progressBar.value = currentExp
        progressLabel.text = "$currentExp/${progressBar.maximum}"
    }

    companion object {
        private var list: MutableList<AchievementUI> = mutableListOf()

        @JvmStatic
        fun getList(): MutableList<AchievementUI> = list
    }
}