"""
@author: Chinmaya Basavanahally Venkatesh
@email: chinmaya.basvanahallyve@mavs.uta.edu
"""
from threading import Thread
from .Cleaning_data import *
from .Converting_file import *
from .Workout_classification import *
import threading
import subprocess
import cv2
import sys
import os
from os import listdir
from os.path import isfile, join
from datetime import datetime
import copy

lock = threading.Lock()
session_lock = threading.Lock()
res = '480p'
exit = False
frames = []
keypointsPath = '../Key Points/'
sessionList = []

# Set resolution for the video capture
def change_res(cap, width, height):
    cap.set(3, width)
    cap.set(4, height)

# Standard Video Dimensions Sizes
STD_DIMENSIONS =  {
    "480p": (640, 480),
    "720p": (1280, 720),
    "1080p": (1920, 1080),
    "4k": (3840, 2160),
}


# grab resolution dimensions and set video capture to it.
def get_dims(cap, res='1080p'):
    width, height = STD_DIMENSIONS["480p"]
    if res in STD_DIMENSIONS:
        width,height = STD_DIMENSIONS[res]
    ## change the current caputre device
    ## to the resulting resolution
    change_res(cap, width, height)
    return width, height

VIDEO_TYPE = {
    'avi': cv2.VideoWriter_fourcc(*'XVID'),
    #'mp4': cv2.VideoWriter_fourcc(*'H264'),
    'mp4': cv2.VideoWriter_fourcc(*'XVID'),
}

def get_video_type(filename):
    filename, ext = os.path.splitext(filename)
    if ext in VIDEO_TYPE:
      return  VIDEO_TYPE[ext]
    return VIDEO_TYPE['avi']

def processVideo(startTimestamp, stopTimestamp, filename):
	session = {}
	startTimestampStr = startTimestamp.isoformat(sep='_', timespec='milliseconds')
	stopTimestampStr = stopTimestamp.isoformat(sep='_', timespec='milliseconds')
	session["startTimestamp"] = startTimestampStr
	session["stopTimestamp"] = stopTimestampStr
	#subprocess.run(["python", "videotrain.py", filename], cwd='../')
	print("openpose finished!")
	repList = []
	onlyfiles = [f for f in listdir(keypointsPath) if (isfile(join(keypointsPath, f)))]
	for file in onlyfiles:
		repDetail = {}
		df = fileConversion(join(keypointsPath, file))
		data = dataCleaning(df)
		acc_v, acc_t, a1, a2, a3, a4, a5 = workout(data)
		repDetail["acc_v"] = acc_v
		repDetail["acc_t"] = acc_t
		repDetail["a1"] = a1
		repDetail["a2"] = a2
		repDetail["a3"] = a3
		repDetail["a4"] = a4
		repDetail["a5"] = a5
		repList.append(repDetail)
		print(acc_v, acc_t, a1, a2, a3, a4, a5)
	session["repList"] = repList
	session_lock.acquire()
	sessionList.append(session)
	session_lock.release()

def runcamera():
	cv2.namedWindow("preview")
	vc = cv2.VideoCapture(0)
	if vc.isOpened(): # try to get the first frame
		startTimestamp = datetime.utcnow()
		startTimestampStr = startTimestamp.isoformat(sep='_', timespec='milliseconds')
		filename = (startTimestampStr + '.mp4').replace(":", "-")
		out = cv2.VideoWriter(filename, get_video_type(filename), 25, get_dims(vc, res))
		rval,frame = vc.read()
	else:
	    cv2.destroyWindow("preview")
	    return

	while rval:
		cv2.imshow("preview", frame)
		out.write(frame)
		rval, frame = vc.read()
		cv2.waitKey(20)
		global lock
		lock.acquire()
		global exit
		if exit == True: # exit on ESC
			exit = False
			lock.release()
			break
		else:
			lock.release()
	cv2.destroyWindow("preview")
	out.release()
	stopTimestamp = datetime.utcnow()
	processVideo(startTimestamp, stopTimestamp, filename)

def startCamera():
    t = Thread(target=runcamera, args=())
    t.start()

def stopCamera():
	global lock
	lock.acquire()
	global exit
	exit = True
	lock.release()

def getResult():
	result = {}
	session_lock.acquire()
	result["sessionList"] = copy.deepcopy(sessionList)
	sessionList.clear()
	session_lock.release()
	return result
