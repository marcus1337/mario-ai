
import statisticsCalculation as sc
import subprocess
from subprocess import Popen, PIPE
import time
import rl_algorithms
import os
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


experiment(rl_algorithms.ENV_MAP_NAME1, 10000, 5)
#experiment(rl_algorithms.ENV_MAP_NAME2, 101, 5)

