package de.droidbridge.pro.adb.process

import de.droidbridge.pro.adb.bridge.AdbExecutionResult
import de.droidbridge.pro.adb.bridge.AdbMaintenanceEngine

class ProcessControlRepository(private val engine: AdbMaintenanceEngine) {

    private val restrictedPackages = listOf(
        "de.telekom.android.customercenter",
        "com.facebook.services"
    )

    suspend fun forceStopPackage(packageName: String): AdbExecutionResult {
        return engine.executeShell("am force-stop $packageName")
    }

    suspend fun setStandbyBucket(packageName: String, bucket: String): AdbExecutionResult {
        return engine.executeShell("am set-standby-bucket $packageName $bucket")
    }

    suspend fun blockBackgroundWakeups(packageName: String): List<AdbExecutionResult> {
        return listOf(
            engine.executeShell("cmd appops set $packageName RUN_IN_BACKGROUND ignore"),
            engine.executeShell("cmd appops set $packageName RUN_ANY_IN_BACKGROUND ignore"),
            engine.executeShell("cmd appops set $packageName WAKE_LOCK ignore")
        )
    }

    suspend fun enforceRestrictedPolicy(): List<AdbExecutionResult> {
        return restrictedPackages.flatMap { pkg ->
            listOf(
                forceStopPackage(pkg),
                setStandbyBucket(pkg, "restricted")
            )
        }
    }
}
