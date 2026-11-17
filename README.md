# DroidBridge Pro — Android APK & Chrome Extension

Dieses Repository enthält das vollständige Android-APK-Projekt (`android-app/`), die Chrome Extension (Manifest V3, `chrome-extension/`) sowie einen automatisierten GitHub-Actions-Release-Workflow.

## Direkte Download-Links (nach Push / Workflow-Lauf)

- **Android APK:** `https://github.com/b3taisadonna/-droidbridge-pro/releases/latest/download/DroidBridge-Pro-v2.4.0-pro.apk`
- **Chrome Extension (.zip):** `https://github.com/b3taisadonna/-droidbridge-pro/releases/latest/download/DroidBridge-Pro-Chrome-Extension-MV3.zip`

## Auf GitHub hochladen & Release automatisch bauen lassen

```bash
git init
git add .
git commit -m "Release DroidBridge Pro v2.4.0-pro"
git branch -M main
git remote add origin https://github.com/b3taisadonna/-droidbridge-pro.git
git push -u origin main
```

Sobald der Push abgeschlossen ist, kompiliert GitHub Actions automatisch die `.apk`-Datei und packt die Chrome Extension in das Release `v2.4.0-pro`.
