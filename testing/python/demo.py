import cv2 as cv 
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

#test_image = '../sample_image/ski.jpg'
#test_image = '../sample_image/images(116).jpg'
test_image = 'Screenshot from 2019-11-05 00-52-01.png'
oriImg = cv.imread(test_image) # B,G,R order
f = plt.imshow(oriImg[:,:,[2,1,0]]) # reorder it before displaying
colors = [[255, 0, 0], [255, 85, 0], [255, 170, 0], [255, 255, 0], [170, 255, 0], [85, 255, 0], [0, 255, 0], \
          [0, 255, 85], [0, 255, 170], [0, 255, 255], [0, 170, 255], [0, 85, 255], [0, 0, 255], [85, 0, 255], \
          [170, 0, 255], [255, 0, 255], [255, 0, 170], [255, 0, 85]]
param, model = config_reader()


caffe.set_mode_cpu()
net = caffe.Net(model['deployFile'], model['caffemodel'], caffe.TEST)

img = cv.imread(test_image,cv.IMREAD_COLOR)
resize = cv.resize(oriImg, (640, 480), interpolation = cv.INTER_LINEAR) 
multiplier = [x * model['boxsize'] / resize.shape[0] for x in param['scale_search']]
p_left = []
p_right = [] 
print "before call"
all_kp = find_kp(resize, model, net, param, multiplier, p_left, p_right)
print(all_kp)
while len(all_kp)>0:
	for (x,y,conf,jo) in all_kp[0]:
		cv.line(resize,(x,y),(x,y),colors[jo],8)
	all_kp = all_kp[1:]


cv.imshow('image',resize)
cv.waitKey(0)
cv.destroyAllWindows()
