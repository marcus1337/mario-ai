
import statisticsCalculation as sc
import subprocess
from subprocess import Popen, PIPE
import time
import rl_algorithms
import os
import sys

def startJavaServer():
    command1 = Popen(['java','-jar', 'MarioAIFrameworkServer.jar'])
    #print(command1.stdout.readline())
    time.sleep(2)
    return command1



javaServer = startJavaServer()

try:
    print("starting...")
    rl_algorithms.setDirectoryToSavesFolder()
    rl_algorithms.NUM_STEPS = 101
    rl_algorithms.trainRLNetworks(5, rl_algorithms.ENV_MAP_NAME1)
    print(sc.getStatistics())
    os.chdir('..')
except:
    print("Unexpected error:", sys.exc_info()[0])
    javaServer.kill()
    raise

javaServer.kill()
