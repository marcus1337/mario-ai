
import pandas as pd
import math
from matplotlib import pyplot as plt
from matplotlib.ticker import MaxNLocator
import numpy as np
import matplotlib.mlab as mlab
from scipy.stats import norm
from matplotlib.ticker import MultipleLocator

#round floats to closest whole number in order to simplify visualization (histogram plot of bins from 0 to 100)


scores_NEAT_notchMedium = []
scores_NEAT_notchParam = []

scores_DQN_notchMedium = []
scores_DQN_notchParam = []

scoresA = scores_NEAT_notchMedium
scoresB = scores_DQN_notchMedium

def makeTwoComparativeHistograms(scores1, scores2):

    (mu1, sigma1) = norm.fit(scores1)
    (mu2, sigma2) = norm.fit(scores2)
    scores1 = [int(elem) for elem in scores1]
    scores2 = [int(elem) for elem in scores2]

    ax = plt.figure().gca()
    plt.xticks(np.arange(0, 101, 1))

    n1, bins1, patches1 = plt.hist(scores1,bins=range(min(scores1),max(max(scores1),max(scores2))+2,1), range=[0,100],color='white', edgecolor='white', linewidth=1.5, density=False, alpha=1)
    n2, bins2, patches2 = plt.hist(scores2,bins=range(min(scores2),max(scores2)+2,1), range=[0,100], color='white' , edgecolor='white', linewidth=1.5, density=False, alpha=1)

    n1, bins1, patches1 = plt.hist(scores1,bins=range(min(scores1),max(max(scores1),max(scores2))+2,1), range=[0,100],color='red', edgecolor='darkred', linewidth=1.5, density=False, label="NEAT", alpha=0.5)
    n2, bins2, patches2 = plt.hist(scores2,bins=range(min(scores2),max(scores2)+2,1), range=[0,100], color='blue' , edgecolor='midnightblue', linewidth=1.5, density=False, label="RL",alpha=0.5)
    plt.xlim([min(min(bins1),min(bins2)),max(max(bins1),max(bins2))])

    ax.yaxis.set_major_locator(MaxNLocator(integer=True))

    plt.xlabel('Score')
    plt.ylabel('Occurrences')
    plt.legend()
    ax.set_axisbelow(True)
    ax.yaxis.grid(color='gray', linestyle='dashed')
    ax.xaxis.grid(color='gray', linestyle='dashed')
    plt.tight_layout()

    plt.show()

import pickle
import os
import seaborn as sns
from io import StringIO
import csv

def plotRLHistory(rlName, numFiles):
    print("Starting plot...")

    objects = []
    for x in range(numFiles):
        fileName = rlName + str(x+1) + ".history"
        with open(fileName, 'rb') as file_pi:
            objects.append(pickle.load(file_pi))

    print(objects)
    print("---")

    maxEpisodes = 999999999999999999999
    for x in range(numFiles):
        maxEpisodes = min(len(objects[x]["episode_reward"]), maxEpisodes)


    tmpCSV = "Test;Average Episode Reward;nb_episode_steps;nb_steps;Episodes\n"
    for x in range(numFiles):
        for y in range(maxEpisodes):
            tmpCSV += str(x+1) + ";" + str(objects[x]["episode_reward"][y]) + ";" + \
                      str(objects[x]["nb_episode_steps"][y]) + ";" + str(objects[x]["nb_steps"][y]) + ";"\
                      +str(y+1)  + "\n"
    TESTDATA = StringIO(tmpCSV)
    df = pd.read_csv(TESTDATA, sep=";")

    print(df)

    sns.set(rc={'axes.facecolor': 'gainsboro'})
    sns.lineplot(data=df, x="Episodes", y="Average Episode Reward", color='blue')

    plt.show()

import glob

def plotDQNHistory(mapType):
    currentDirectory = os.getcwd()
    os.chdir(currentDirectory + "\\saves")
    numFiles = len(glob.glob1(os.getcwd(),"*.history"))
    plotRLHistory("duel_dqn_" + mapType, numFiles)
    os.chdir('..')


def textToList(hashtags):
    return hashtags.strip('[]').replace('\'', '').replace(' ', '').split(',')

def plotNEATHistory(fileprefix, numFiles):
    scores = None
    for x in range(numFiles):
        fileName = fileprefix + str(x+1) + ".history"
        with open(fileName, 'r') as fp:
            line = fp.readline()
            scores = [int(s) for s in textToList(line)]
    print(scores)
    return

def plotNEATHistory(mapType):
    currentDirectory = os.getcwd()
    os.chdir(currentDirectory + "\\saves")
    numFiles = len(glob.glob1(os.getcwd(),"*.history"))
    plotNEATHistory("NEAT_" + mapType, numFiles)
    os.chdir('..')
    return


#plotDQNHistory("notchParam")
plotNEATHistory("notchParam")

#makeTwoComparativeHistograms(scoresA, scoresB)