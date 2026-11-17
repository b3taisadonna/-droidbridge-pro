package de.droidbridge.pro.adb.bridge

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

data class AdbExecutionResult(
    val command: String,
    val exitCode: Int,
    val output: String
)

class AdbMaintenanceEngine(private val context: Context) {

    private val bridgeMode = "shizuku"

    suspend fun executeShell(command: String): AdbExecutionResult = withContext(Dispatchers.IO) {
        try {
            val executable = when (bridgeMode) {
                "root_su" -> arrayOf("su", "-c", command)
                else -> arrayOf("sh", "-c", command)
            }
            val process = Runtime.getRuntime().exec(executable)
            val stdout = BufferedReader(InputStreamReader(process.inputStream)).readText()
            val stderr = BufferedReader(InputStreamReader(process.errorStream)).readText()
            val code = process.waitFor()
            AdbExecutionResult(
                command = command,
                exitCode = code,
                output = if (stdout.isNotBlank()) stdout.trim() else stderr.trim()
            )
        } catch (ex: Exception) {
            Log.e("DroidBridge", "ADB-Befehl fehlgeschlagen: $command", ex)
            AdbExecutionResult(command, -1, ex.message ?: "Unbekannter Fehler")
        }
    }

    suspend fun applyPerformanceProfile(): List<AdbExecutionResult> {
        val commands = listOf(
            "settings put global window_animation_scale 0.5",
            "settings put global transition_animation_scale 0.5",
            "settings put global animator_duration_scale 0.5",
            "settings put global cached_apps_freezer enabled",
            "cmd power set-adaptive-power-saver-enabled false"
        )
        return commands.map { executeShell(it) }
    }
}
