"""
@author: Reza Etemadi Idgahi
@email: reza.etemadiidgahi@mavs.uta.edu
"""
from django.shortcuts import render
from django.http import HttpResponse, JsonResponse
from .camera_controller import startCamera, stopCamera, getResult

def startcamera(request):
    startCamera()
    return HttpResponse("camera is started")

def stopcamera(request):
    stopCamera()
    return HttpResponse("camera is stopped")

def getresult(request):
	return JsonResponse(getResult())