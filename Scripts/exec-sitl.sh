#!/bin/bash
#Author: Jesimar da Silva Arantes
#Date: 19/10/2017

LAT=-22.00587424417797  #-22.005640
LNG=-47.89874454308930  #-47.932474
ALT=870
ANGLE=70

dronekit-sitl copter-3.3 --home=$LAT,$LNG,$ALT,$ANGLE
