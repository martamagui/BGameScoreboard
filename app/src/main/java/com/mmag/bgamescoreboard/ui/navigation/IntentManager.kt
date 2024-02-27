package com.mmag.bgamescoreboard.ui.navigation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings

object IntentManager {
    fun settingsIntent(activity: Activity): Intent {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.data = Uri.parse("package:" + activity.application.packageName)
        return intent
    }
}