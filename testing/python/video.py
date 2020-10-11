"""
@author: Chinmaya Basavanahally Venkatesh
@email: chinmaya.basvanahallyve@mavs.uta.edu
"""
import numpy as np
import cv2
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

colors = [[255, 0, 0], [255, 85, 0], [255, 170, 0], [255, 255, 0], [170, 255, 0], [85, 255, 0], [0, 255, 0], \
          [0, 255, 85], [0, 255, 170], [0, 255, 255], [0, 170, 255], [0, 85, 255], [0, 0, 255], [85, 0, 255], \
          [170, 0, 255], [255, 0, 255], [255, 0, 170], [255, 0, 85]]
cap = cv2.VideoCapture(0)
param, model = config_reader()
print cap.isOpened()
p_left = []
p_right = []
caffe.set_mode_cpu()
net = caffe.Net(model['deployFile'], model['caffemodel'], caffe.TEST)
while(cap.isOpened()):  # check !
    # capture frame-by-frame
    ret, frame = cap.read()

    if ret: # check ! (some webcam's need a "warmup")
        # our operation on frame come here
        multiplier = [x * model['boxsize'] / frame.shape[0] for x in param['scale_search']]
        print "True ret"
        all_kp = find_kp(frame, model, net, param, multiplier, p_left, p_right)
        print(all_kp)
        while len(all_kp)>0:
			for (x,y,conf,jo) in all_kp[0]:
				cv2.line(frame,(x,y),(x,y),colors[jo],3)
			all_kp = all_kp[1:]
	cv2.imshow('frame',frame)
    if cv2.waitKey(6000) & 0xFF == ord('q'):
        break

# When everything done, release the capture
cap.release()
cv2.destroyAllWindows()
