import time
import random
import argparse
from experiment import MonsoonEnergyMeter, Writer, screen_brightness, unlock, get_device_model, stop_app, start_app
from experiment.android import start_instrumentation

parser = argparse.ArgumentParser(description='Hybrid UI Components Energy Experiment')
parser.add_argument('-m', '--meter', type=str, help='Energy metering approach: monsoon, batterystats, dummy', default="monsoon")
parser.add_argument('-f', '--filename', type=str, help='Filename of results', default="measurements.txt")
parser.add_argument('-c', '--count', type=int, help='Number of executions', default=1)
args = parser.parse_args()
device_model = get_device_model()
writer = Writer(args.filename)

meter = None
if args.meter == "monsoon":
    meter = MonsoonEnergyMeter()
meter.setup(4.3)


INTERACTIONS = {
    "scrolling": "ScrollDownListInteractionTest",
    "drawer": "OpenCloseDrawerInteractionTest",
    "sheet": "OpenCloseSheetInteractionTest",
    "dialog": "OpenCloseDialogInteractionTest"
}


def get_settings():
    return [
        ["at.stefanhuber.hui.elements", "drawer"],
        ["at.stefanhuber.hui.elements", "sheet"],
        ["at.stefanhuber.hui.elements", "dialog"],
        ["at.stefanhuber.hui.android.elements", "drawer"],
        ["at.stefanhuber.hui.android.elements", "sheet"],
        ["at.stefanhuber.hui.android.elements", "dialog"],
        ["at.stefanhuber.hui.capacitor.elements", "drawer"],
        ["at.stefanhuber.hui.capacitor.elements", "sheet"],
        ["at.stefanhuber.hui.capacitor.elements", "dialog"],
        ["at.stefanhuber.hui.scrolling1", "scrolling"],
        ["at.stefanhuber.hui.scrolling4", "scrolling"],
        ["at.stefanhuber.hui.scrolling10", "scrolling"],
        ["at.stefanhuber.hui.capacitor.scrolling", "scrolling"],
        ["at.stefanhuber.hui.android.scrolling", "scrolling"]
    ]


def get_randomized_settings():
    s = get_settings()
    random.shuffle(s)
    return s


def write_result(app, device_model, interaction, idle1, result, idle2):
    writer.write([
        app,
        device_model,
        interaction,
        str(idle1['energy']),
        str(idle1['duration']),
        str(result['energy']),
        str(result['duration']),
        str(idle2['energy']),
        str(idle2['duration']),
    ])


def execute_experiment(app, interaction):
    print("START: {}, {}".format(app, interaction))

    # stop/start app
    stop_app(app)
    start_app(app)
    time.sleep(2)

    # start idle 1
    meter.start()
    time.sleep(5)
    meter.stop()
    idle1 = meter.measurements()
    print("   idle 1 energy: {}, duration: {}".format(idle1['energy'], idle1['duration']))    
    time.sleep(2)

    # start interaction
    meter.start()
    start_instrumentation("at.stefanhuber.instrumentation", INTERACTIONS[interaction])
    meter.stop()
    result = meter.measurements()
    print("   interaction energy: {}, duration: {}".format(result['energy'], result['duration']))
    time.sleep(2)

    # start idle 2
    meter.start()
    time.sleep(5)
    meter.stop()
    idle2 = meter.measurements()
    print("   idle 2 energy: {}, duration: {}".format(idle2['energy'], idle2['duration']))
    time.sleep(2)

    # write results    
    write_result(app, device_model, interaction, idle1, result, idle2)


unlock()
unlock()
screen_brightness(0.3)


for i in range(args.count):
    settings = get_randomized_settings()
    for setting in settings:
        execute_experiment(*setting)
