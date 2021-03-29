
import sys
import os
import numpy as np
from pathlib import Path
from statistics import stdev


def getMeans(directory):
    means = []
    indexer = 1
    directory += "dqnResult"
    while (Path(directory + str(indexer) + ".txt").is_file()):
        result_file = directory + str(indexer) + ".txt"
        indexer = indexer + 1
        with open(result_file, "r") as file1:
            means.append(float(file1.readline())*100)
    return means

def getStatistics():
    result = ""
    directory = os.getcwd() + "\\saves\\"
    means = getMeans(directory)
    result += "NUMBER OF SAMPLES: " + str(len(means)) + "\n"
    result += "MEAN: " + str(np.mean(means)) + "\n"
    result += "STANDARD DEVIATION: " + str(stdev(means)) + "\n"
    result += "\n---RAW SCORE DATA---\n\n"
    result += str(means)
    return result
