// DroidBridge Pro — Manifest V3 Service Worker
const CLEANUP_ALARM = 'droidbridge_auto_cleanup_reminder';

chrome.runtime.onInstalled.addListener(async () => {
  const existing = await chrome.storage.local.get(['wirelessConfig', 'cleanupIntervalHours', 'customScripts']);
  if (!existing.wirelessConfig) {
    await chrome.storage.local.set({
      wirelessConfig: {
        ipAddress: "192.168.178.42",
        port: 5555,
        label: "Pixel 9 Pro · Heimnetz"
      },
      cleanupIntervalHours: 24,
      customScripts: [{"id":"script-doze-trim","name":"Tiefen-Doze & Speicher-Trim","category":"Wartung","commands":["adb shell dumpsys deviceidle force-idle","adb shell pm trim-caches 16G","adb shell sm fstrim","adb shell am kill-all"]},{"id":"script-ui-snappy","name":"UI-Reaktionszeit 0.25x Boost","category":"Display & UI","commands":["adb shell settings put global window_animation_scale 0.25","adb shell settings put global transition_animation_scale 0.25","adb shell settings put global animator_duration_scale 0.25"]},{"id":"script-network-dns","name":"Private DNS & WLAN-Stack Reset","category":"Netzwerk","commands":["adb shell settings put global private_dns_mode hostname","adb shell settings put global private_dns_specifier one.one.one.one","adb shell svc wifi disable","adb shell svc wifi enable"]},{"id":"script-battery-diag","name":"Akku- & Wakelock-Diagnose","category":"Diagnose","commands":["adb shell dumpsys battery | grep -E \"level|temperature|voltage\"","adb shell dumpsys power | grep \"Wake Locks\"","adb shell dumpsys alarm | head -n 25"]}]
    });
  }

  chrome.alarms.create(CLEANUP_ALARM, {
    periodInMinutes: 1440
  });
});

chrome.alarms.onAlarm.addListener((alarm) => {
  if (alarm.name === CLEANUP_ALARM) {
    chrome.notifications.create({
      type: 'basic',
      iconUrl: 'icons/icon128.png',
      title: 'DroidBridge Pro — Geplante Gerätewartung',
      message: 'Prüfen Sie die Bereinigungsvorschau für Caches und temporäre Dateien.'
    });
  }
});
