import re
import json
import os
import shutil
import requests
import faker


IMAGE_FILE_NAME = re.compile("https?://i\.picsum\.photos/id/(\d+?)/.*")
FAKER = faker.Faker()


def clear_directory(path, mkdir=False):
    if os.path.exists(path):
        shutil.rmtree(path)
    if mkdir:
        os.mkdir(path)    


def get_image(width=200, heigth=200):        
    response = requests.get("https://picsum.photos/{}/{}".format(width, heigth))
    match = IMAGE_FILE_NAME.match(response.url)

    if match:
        filename = "{}-{}-{}.jpg".format(match.group(1), width, heigth)
        with open("./data/raw/demodata/{}".format(filename), "wb") as file:
            file.write(response.content)        
        return filename

    raise RuntimeError("image could not be downloaded")


def get_gallery_item():
    return {
        "title": FAKER.sentence(nb_words=3)[:-1],
        "count": FAKER.pyint(min_value=10),
        "image": get_image()
    }


def get_gallery_items(count=100):
    gallery = []
    for _ in range(count):
        gallery.append(get_gallery_item())

    with open("./data/raw/demodata/data.json", "w") as file:
        json.dump(gallery, file, indent=4)
    
    
def generate(count=5):
    clear_directory("./data/raw/demodata", True)
    get_gallery_items(count)


def copy():   

    # hybrid app
    clear_directory("./data/hui-capacitor-scrolling/public/data")    
    shutil.copytree("./data/raw/demodata", "./data/hui-capacitor-scrolling/public/data")

    # native app
    clear_directory("./data/apps/hui-android-scrolling/app/src/main/assets/data")    
    shutil.copytree("./data/raw/demodata", "./data/apps/hui-android-scrolling/app/src/main/assets/data")

    # hui elements 1 app
    clear_directory("./data/apps/hui-scrolling1/app/src/main/assets/data")    
    shutil.copytree("./data/raw/demodata", "./data/apps/hui-scrolling1/app/src/main/assets/data")

    # hui elements 4 app
    clear_directory("./data/apps/hui-scrolling4/app/src/main/assets/data")    
    shutil.copytree("./data/raw/demodata", "./data/apps/hui-scrolling4/app/src/main/assets/data")

    # hui elements 10 app
    clear_directory("./data/apps/hui-scrolling10/app/src/main/assets/data")    
    shutil.copytree("./data/raw/demodata", "./data/apps/hui-scrolling10/app/src/main/assets/data")
