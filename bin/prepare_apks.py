import os
import subprocess
import shutil

apk_sub_path = "app/build/outputs/apk/debug/app-debug.apk"
android_app_path = lambda name: "./data/apps/{}".format(name)
capacitor_app_path = lambda name: "./data/apps/{}/android".format(name)

APP_PATHS = {
    "hui-android-elements": android_app_path("hui-android-elements"),
    "hui-android-scrolling": android_app_path("hui-android-scrolling"),
    "hui-capacitor-elements": capacitor_app_path("hui-capacitor-elements"),
    "hui-capacitor-scrolling": capacitor_app_path("hui-capacitor-scrolling"),
    "hui-elements": android_app_path("hui-elements"),
    "hui-scrolling1": android_app_path("hui-scrolling1"),
    "hui-scrolling4": android_app_path("hui-scrolling4"),
    "hui-scrolling10": android_app_path("hui-scrolling10"),
    "instrumentation": android_app_path("instrumentation")
}
TARGET_DIRECTORY = "./data/apks"

for app in APP_PATHS.keys():
    if os.path.exists(APP_PATHS[app]):
        print("build project: {}".format(app))
        subprocess.check_output("(cd {}; ./gradlew build)".format(APP_PATHS[app]), shell=True)
        print("copy project: {}".format(app))                
        target = "{}/{}.apk".format(TARGET_DIRECTORY, app)
        if os.path.exists("{}/{}.apk".format(TARGET_DIRECTORY, app)):
            os.remove(target)
        shutil.copy("{}/{}".format(APP_PATHS[app], apk_sub_path), target)
