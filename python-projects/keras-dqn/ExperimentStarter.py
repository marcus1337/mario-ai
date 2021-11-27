
import os
os.environ['TF_XLA_FLAGS'] = '--tf_xla_enable_xla_devices'

import statisticsCalculation as sc
import subprocess
from subprocess import Popen, PIPE
import time
import rl_algorithms
import sys
from distutils.dir_util import copy_tree
import shutil

def writeToFile(fileName, text):
    f = open(fileName, "w")
    f.write(text)
    f.close()

def makeFolder(folderName):
    current_directory = os.getcwd()
    final_directory = os.path.join(current_directory, folderName)
    if not os.path.exists(final_directory):
        os.makedirs(final_directory)

def exitSavesAndRename(mapType, numSteps, numSamples):
    os.chdir('..')
    newFolderName = "Experiments_" + str(mapType) + "_" + str(numSteps) + "_" + str(numSamples)
    os.rename("saves", newFolderName)
    makeFolder("saves")

def experiment(mapType, numSteps, numSamples):
    print("starting...")
    rl_algorithms.setDirectoryToSavesFolder()

    rl_algorithms.NUM_STEPS = numSteps
    rl_algorithms.trainRLNetworks(numSamples, mapType)
    rl_algorithms.testRLNetworks(numSamples, mapType)
    writeToFile("statisticsInfo.txt", sc.getStatistics())

    exitSavesAndRename(mapType, numSteps, numSamples)

def testVisually(mapType, AI_ID):
    print("showing visually...")
    rl_algorithms.setDirectoryToSavesFolder()
    rl_algorithms.testRLNetworkVisually(AI_ID, mapType)

def testWithoutVisuals(mapType, AI_ID):
    print("Logging decisions to files...")
    rl_algorithms.setDirectoryToSavesFolder()
    rl_algorithms.testRLNetwork(AI_ID, mapType)

def removeNonEventsFromDecisionFiles():
    import os
    folderPath = "./decisionSaves"
    for filename in os.listdir(folderPath):
        filePathAndName = folderPath + "/" + filename
        lines = None
        with open(filePathAndName, "r") as f:
            lines = f.readlines()
        with open(filePathAndName, "w") as f:
            for line in lines:
                if line.startswith("EVENT"):
                    f.write(line)

##31 min 44 sec for 200000 steps or  1904 sec -- on average 105,0420168067227 steps per second.
##60 minutes == 378151,2605 steps   378151

#testWithoutVisuals(rl_algorithms.ENV_MAP_NAME1, int(sys.argv[1]))

#logDecisions(rl_algorithms.ENV_MAP_NAME2)
#testVisually(rl_algorithms.ENV_MAP_NAME2, 2)
#testBlind(rl_algorithms.ENV_MAP_NAME1, 2)

#experiment(rl_algorithms.ENV_MAP_NAME1, 378150, 3)
#experiment(rl_algorithms.ENV_MAP_NAME2, 378150, 3)

#rl_algorithms.setDirectoryToSavesFolder()
#rl_algorithms.NUM_STEPS = numSteps
#rl_algorithms.testRLNetworks(numSamples, mapType)
