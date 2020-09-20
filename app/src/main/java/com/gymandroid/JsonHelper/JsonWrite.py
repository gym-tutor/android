
import json

#######################################################
#### version 1 ####

# name = input("Pose Name: ")
# detail = input("Pose Detail: ")
# video_uri = input("Video Uri: ")
# caution = input("Pose Caution: ")
# img_uri = input("Img Uri: ")

#######################################################
##### version 2 ######

name = 'tree'
detail = "Tree Pose stretches the thighs, groins, torso, and shoulders. It builds strength in the ankles and calves, and tones the abdominal muscles. The pose also helps to remedy flat feet and is therapeutic for sciatica."
video_uri = 2131755015
caution = "Due to the balancing nature of the posture, do not practice Tree Pose if you are currently experiencing headaches, insomnia, low blood pressure, or if you are lightheaded and/or dizzy. Those with high blood pressure should not raise their arms overhead in the pose. Always work within your own range of limits and abilities. If you have any medical concerns, talk with your doctor before practicing yoga."
img_uri = 2131755014

######################################################
# write to file
# run in root directory

item_dict = {
    "name": name,
    "detail": detail,
    "video_uri": video_uri,
    "caution": caution,
    "img_uri": img_uri
}

with open('app/src/main/java/com/gymandroid/ui/exercise/dummy/'+name+'.json', 'w') as json_file:
    json.dump(item_dict, json_file)
