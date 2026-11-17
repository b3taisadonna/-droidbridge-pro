# DroidBridge Pro — Android App

Android-App für ADB-Wartung, Speicher-Optimierung, Auto-Backup und Ressourcen-Governance.

## Build

```bash
cd android-app
gradle assembleDebug
```

APK landet unter `app/build/outputs/apk/debug/app-debug.apk`.

## Features

- AdbMaintenanceEngine
- StorageOptimizerWorker
- AutoBackupWorker
- ResourceGovernorService
- ProcessControlRepository
