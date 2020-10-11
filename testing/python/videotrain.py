"""
@author: Chinmaya Basavanahally Venkatesh
@email: chinmaya.basvanahallyve@mavs.uta.edu
"""
import sys
import cv2
import numpy as np
import scipy
import PIL.Image
import math
import caffe
import time
from config_reader import config_reader
import util
import copy
import matplotlib
#%matplotlib inline
import pylab as plt
from detect_kp import find_kp
import csv

# count the arguments
arguments = len(sys.argv) - 1
if (arguments == 1):
    filename = sys.argv[1]

colors = [[255, 0, 0], [255, 85, 0], [255, 170, 0], [255, 255, 0], [170, 255, 0], [85, 255, 0], [0, 255, 0], \
          [0, 255, 85], [0, 255, 170], [0, 255, 255], [0, 170, 255], [0, 85, 255], [0, 0, 255], [85, 0, 255], \
          [170, 0, 255], [255, 0, 255], [255, 0, 170], [255, 0, 85]]

vidcap = cv2.VideoCapture('workoutrecognizerserver/' + filename)
param, model = config_reader()
p_left = []
p_right = []
caffe.set_mode_cpu()
net = caffe.Net(model['deployFile'], model['caffemodel'], caffe.TEST)
read_flag, image = vidcap.read()
vid_frames = []
i=0
f = open("./Key Points/" + filename + ".txt", "w+")
while (read_flag):
	resize = cv2.resize(image, (640, 480), interpolation = cv2.INTER_LINEAR) 
	vid_frames.append(resize)
	read_flag,image = vidcap.read()

for j in range(len(vid_frames)):
	multiplier = [x * model['boxsize'] / vid_frames[j].shape[0] for x in param['scale_search']]
	all_kp = find_kp(vid_frames[j], model, net, param, multiplier, p_left, p_right)
	print(all_kp)
	f.write(str(all_kp)+"\n")
	if j%70==0:
		f.close()
		cv2.waitKey(30000)
		i=i+1
		txtstr = "./Key Points/"+filename+str(i)+".txt"
		f = open(txtstr,"w+")
f.close()
