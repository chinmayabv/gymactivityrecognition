"""
@author: Chinmaya Basavanahally Venkatesh
@email: chinmaya.basvanahallyve@mavs.uta.edu
"""
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

def runOnFrames(vid_frames):
	print("hi")
	param, model = config_reader()
	p_left = []
	p_right = []
	caffe.set_mode_cpu()
	net = caffe.Net(model['deployFile'], model['caffemodel'], caffe.TEST)
	read_flag, image = vidcap.read()
	f = open("./Key Points/new_File.txt","w+")
	for i in range(len(vid_frames)):
		resize = cv2.resize(image, (640, 480), interpolation = cv2.INTER_LINEAR) 
		vid_frames.append(resize)

	for j in range(len(vid_frames)):

		multiplier = [x * model['boxsize'] / vid_frames[j].shape[0] for x in param['scale_search']]
		all_kp = find_kp(vid_frames[j], model, net, param, multiplier, p_left, p_right)
		print(all_kp)
		f.write(str(all_kp)+"\n")

	f.close()
