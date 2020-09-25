
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

# name = 'tree'
# detail = "Tree Pose stretches the thighs, groins, torso, and shoulders. It builds strength in the ankles and calves, and tones the abdominal muscles. The pose also helps to remedy flat feet and is therapeutic for sciatica."
# video_uri = 2131755015
# caution = "Due to the balancing nature of the posture, do not practice Tree Pose if you are currently experiencing headaches, insomnia, low blood pressure, or if you are lightheaded and/or dizzy. Those with high blood pressure should not raise their arms overhead in the pose. Always work within your own range of limits and abilities. If you have any medical concerns, talk with your doctor before practicing yoga."
# img_uri = 2131755014

# name.append()
# detail.append()
# video_uri.append()
# caution.append()
# img_uri.append()

name = []
detail = []
video_uri = []
caution = []
img_uri = []

#######################################################
#### add tree ####

name.append("Tree")
detail.append("Tree Pose stretches the thighs, groins, torso, and shoulders. It builds strength in the ankles and calves, and tones the abdominal muscles. The pose also helps to remedy flat feet and is therapeutic for sciatica.")
video_uri.append(2131755016)
caution.append("Due to the balancing nature of the posture, do not practice Tree Pose if you are currently experiencing headaches, insomnia, low blood pressure, or if you are lightheaded and/or dizzy. Those with high blood pressure should not raise their arms overhead in the pose. Always work within your own range of limits and abilities. If you have any medical concerns, talk with your doctor before practicing yoga.")
img_uri.append(2131755015)

######################################################
##### add cobra ######

name.append("Cobra")
detail.append("Cobra Pose is best known for its ability to increase the flexibility of the spine. It stretches the chest while strengthening the spine and shoulders. It also helps to open the lungs, which is therapeutic for asthma. This pose also stimulates the abdominal organs, improving digestion.")
video_uri.append(2131755009)
caution.append("Please do not practice Cobra if you have carpal tunnel syndrome, or a recent back or wrist injury. Women who are pregnant should avoid practicing this pose while on the floor, although they may practice it standing with their palms against a wall. Always work within your own range of limits and abilities. If you have any medical concerns, talk with your doctor before practicing yoga.")
img_uri.append(2131755014)


######################################################

######################################################
# write to file
# run in root directory

item_array = []
count = len(name)

for i in range(count):
    item_dict = {
        "name": name[i],
        "detail": detail[i],
        "video_uri": video_uri[i],
        "caution": caution[i],
        "img_uri": img_uri[i]
    }
    item_array.append(item_dict)

with open('app/src/main/res/raw/'+"pose_info"+'.json', 'w') as json_file:
    json.dump(item_array, json_file)
