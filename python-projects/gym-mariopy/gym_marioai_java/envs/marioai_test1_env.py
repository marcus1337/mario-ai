import os, subprocess, time, signal
import gym
import numpy as np
from gym import error, spaces
from gym import utils
from gym.utils import seeding

import jpype
import jpype.imports
from jpype.types import *




import logging
logger = logging.getLogger(__name__)

class MarioAITest1(gym.Env, utils.EzPickle):
    metadata = {'render.modes': ['human']}

    def __init__(self):
        self.observation_space = spaces.Discrete(237)
        self.action_space = spaces.Discrete(5)
        self.status = "Running"

        if (jpype.isJVMStarted() is False):
            jpype.startJVM(classpath=['/JARS/*'])
        from MarioPackage import GenerateLevel
        self.Main = GenerateLevel()

        self.renderActive = False
        self.mapType = "notchParam"
        self.lvl = -1

    def setLvl(self, lvl):
        self.lvl = lvl


    def step(self, action):
        if (not isinstance(action, list)):
            action = int(action)
            tmpArr = np.zeros(5)
            bitStr = "{0:b}".format(action)
            while (len(bitStr) < 5):
                bitStr = '0' + bitStr
            for i in range(5):
                if (bitStr[i] == '1'):
                    tmpArr[i] = 1
            action = tmpArr

        jarray = JArray(bool)(5)
        for i in range(5):
            if (action[i] >= 0.5):
                jarray[i] = True
            else:
                jarray[i] = False
        self.Main.setActions(jarray)

        if (self.renderActive == True):
            self.Main.stepWithVisuals()
        else:
            self.Main.step()
        obsJava = self.Main.getDiscreteObservations()
        ob = np.array(obsJava)
        reward = self.Main.getReward()
        episode_over = self.Main.isDone()
        return ob, reward, episode_over, {}

    def reset(self):

        if (jpype.isJVMStarted() is False):
            jpype.startJVM(classpath=['/JARS/*'])
        from MarioPackage import GenerateLevel

        if(self.lvl == -1):
            self.Main = GenerateLevel()

        print("LVL: " + str(self.lvl) + " _ " + str(self.mapType))
        if(self.lvl == -1):
            self.Main.initTestMap(self.mapType)
        else:
            self.Main.initTestMap(self.mapType, self.lvl)
        obsJava = self.Main.getDiscreteObservations()
        ob = np.array(obsJava)
        episode_over = False
        return ob

    def render(self, mode='human'):
        if(self.renderActive is False):
            self.renderActive = True
            print("Rendering is done by step. Rendering status: " + str(self.renderActive))

    def close(self):
        pass

    def setSpecificMap(self, lvlNum):
        self.lvl = lvlNum
        self.Main.initTestMap(self.mapType, self.lvl)
        print("Testing map: " + str(lvlNum))

    def setMapType(self, mapType):
        self.mapType = mapType

    def getCompletionPercentage(self):
        return self.Main.getMapCompletionPercentage()
