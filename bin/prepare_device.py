from experiment.android import install_apk, uninstall_apk

APPS_APKS = {
    "at.stefanhuber.instrumentation": "instrumentation.apk",
    "at.stefanhuber.hui.elements": "hui-elements.apk",
    "at.stefanhuber.hui.scrolling1": "hui-scrolling1.apk",
    "at.stefanhuber.hui.scrolling4": "hui-scrolling4.apk",
    "at.stefanhuber.hui.scrolling10": "hui-scrolling10.apk",
    "at.stefanhuber.hui.android.elements": "hui-android-elements.apk",
    "at.stefanhuber.hui.android.scrolling": "hui-android-scrolling.apk",
    "at.stefanhuber.hui.capacitor.elements": "hui-capacitor-elements.apk",
    "at.stefanhuber.hui.capacitor.scrolling": "hui-capacitor-scrolling.apk"
}

for app in APPS_APKS.keys():
    try:
        uninstall_apk(app)
    except:
        print("{} not uninstalled".format(app))
    install_apk("./data/apks/{}".format(APPS_APKS[app]))
