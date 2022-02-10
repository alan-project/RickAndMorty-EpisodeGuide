package net.alanproject.domain.usecases.impl

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.text.TextUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import net.alanproject.domain.model.DomainVersionConfigModel
import net.alanproject.domain.repository.CartoonRepository
import net.alanproject.domain.usecases.GetVersionConfig
import timber.log.Timber
import javax.inject.Inject

class GetVersionConfigUsecase @Inject constructor(
    private val repository: CartoonRepository,
    @ApplicationContext val context: Context
) : GetVersionConfig {
    override fun getNewVersionConfig(): Pair<Boolean,Boolean> {
        val versionConfig:DomainVersionConfigModel = repository.getVersionConfig()
        Timber.d("versionConfig: $versionConfig")
        return checkForUpdate(versionConfig)
    }

    private fun checkForUpdate(versionConfig: DomainVersionConfigModel): Pair<Boolean,Boolean> {

        val currentVersion = getVersionName()

        val latestVersion = versionConfig.latestVersion ?: 0
        val forceUpdateRequest = versionConfig.forceUpdate ?: false
        val forceUpdateTarget = versionConfig.forceUpdateTarget ?: listOf(0) as List<*>
//        val forceUpdateResult = forceUpdateRequest && forceUpdateTarget.contains(currentVersion)
        val forceUpdateResult = forceUpdateRequest && currentVersion in forceUpdateTarget

        Timber.d("latestVersion: $latestVersion, forceUpdateResult: $forceUpdateResult, forceUpdateTarget: $forceUpdateTarget")

        if (latestVersion != 0 && currentVersion!=0 && latestVersion>currentVersion) {
            return true to forceUpdateResult
        }
        return false to false

    }

    private fun getVersionName(): Int {
        val versionCode: Long = try {
            val pInfo: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) pInfo.versionCode.toLong()
            else pInfo.longVersionCode
        } catch (e: PackageManager.NameNotFoundException) {
            Timber.d("getVersionName ex: $e")
            0L
        }
        return versionCode.toInt()
    }

    // I will use below code in case I compare versionName instead of versionCode
    private fun checkMandateVersionApplicable(
        latestVersion: String,
        currentVersion: String
    ): Boolean {
        return try {
            val latestVersionInt = latestVersion.toInt()
            val currentVersionInt = currentVersion.toInt()
            latestVersionInt > currentVersionInt
        } catch (ex: NumberFormatException) {
            Timber.d("ex: $ex")
            false
        }
    }

    private fun getAppVersionWithoutAlphaNumeric(result: String): String {
        var versionStr = ""
        versionStr = result.replace(".", "")
        return versionStr
    }
}