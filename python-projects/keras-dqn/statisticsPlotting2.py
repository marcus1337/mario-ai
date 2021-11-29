
import pandas as pd
import math
import matplotlib
from matplotlib import pyplot as plt
from matplotlib.ticker import MaxNLocator
import numpy as np
import matplotlib.mlab as mlab
from scipy.stats import norm
from matplotlib.ticker import MultipleLocator

#round floats to closest whole number in order to simplify visualization (histogram plot of bins from 0 to 100)


scores_NEAT_notchMedium = [39.7831916809082,42.41106033325195,43.9289436340332,42.40529251098633,44.50016784667969,41.7943115234375,41.329776763916016,41.35967254638672,46.0434684753418,41.76780700683594,40.545230865478516,45.68148422241211,42.434173583984375,44.7648811340332,30.701753616333008,37.49359130859375,38.9331169128418,39.099910736083984,38.83076477050781,43.996437072753906]
scores_NEAT_notchParam = [30.746227264404297,32.181114196777344,37.936031341552734,26.50324249267578,32.23626708984375,29.328824996948242,25.410852432250977,21.823623657226562,28.580419540405273,32.01652526855469,23.019859313964844,29.963380813598633,31.12384796142578,33.12236404418945,29.158784866333008,19.222209930419922,33.523658752441406,34.71747589111328,29.961824417114258,25.197484970092773]

scores_DQN_notchMedium = [18.889004927128553, 11.296737249009311, 9.423973673954606, 15.920314423739908, 20.53727175667882, 10.484395910054445, 20.805544544011354, 14.665033113211393, 9.095462292432785, 14.931070054881273, 13.576312452554703, 17.468951921910048, 14.226172162219882, 17.72994949296117, 10.772729331627488, 15.208480859175324, 16.9295313693583, 15.350790936499834, 14.656963923946023, 11.318865528912283]
scores_DQN_notchParam = [0.1384957954287529, 18.44212294369936, 15.062286045402287, 13.542960034683347, 16.696380065754056, 12.743397416546943, 13.895943704061208, 0.9166112306993456, 0.4869926299434156, 14.888453096151352, 17.13074555620551, 8.096482559107244, 16.70411216467619, 16.568318512290716, 10.39915033429861, 17.09644588828087, 15.435812510550024, 18.42766347527504, 16.60084155201912, 15.61551194638014]

scoresA = scores_NEAT_notchMedium
scoresB = scores_DQN_notchMedium
#print(sorted(scoresA))
#print(sorted(scoresB))

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
import ast

import glob

def scaleValues(rewardList, multiplier):
    newList = []
    for x in range(len(rewardList)):
        reward = rewardList[x]
        reward = reward * multiplier
        newList.append(reward)
    return newList


def getAveragedList(rewardList, averageOverNum):
    newList = []
    for x in range(0,len(rewardList),averageOverNum):
        if x + averageOverNum > len(rewardList):
            return newList
        totalValue = 0
        for y in range(averageOverNum):
            totalValue += rewardList[x+y]
        newList.append(int(totalValue/averageOverNum))

    return newList

def getBestList(rewardList, bestOverNum):
    newList = []
    for x in range(0,len(rewardList),bestOverNum):
        if x + bestOverNum > len(rewardList):
            return newList
        bestValue = 0
        for y in range(bestOverNum):
            bestValue = max(rewardList[x+y], bestValue)
        newList.append(int(bestValue))

    return newList

def plotRLHistory(rlName, numFiles):
    print("Starting plot...")

    objects = []
    for x in range(numFiles):
        fileName = rlName + str(x+1) + ".history"
        with open(fileName, 'rb') as file_pi:
            objects.append(pickle.load(file_pi))

    for key in objects[0]:
        print(key)
    #print(objects[0]["nb_steps"])

    print("---")

    #for x in range(numFiles):
    #    objects[x]["episode_reward"] = [v*100.0 for v in objects[x]["episode_reward"]]

    for x in range(numFiles):
        objects[x]["episode_reward"] = scaleValues(objects[x]["episode_reward"], 10.0)

    #for x in range(numFiles):
    #    objects[x]["episode_reward"] = getAveragedList(objects[x]["episode_reward"], 5)

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
    ax = sns.lineplot(data=df, x="Episodes", y="Average Episode Reward", color='blue', linewidth=0.3)
    ax.set(ylabel='Score', xlabel='Episodes')
    plt.ylim(0,100)
    plt.xlim(0, maxEpisodes)

    plt.show()



def plotDQNHistory(mapType):
    currentDirectory = os.getcwd()
    os.chdir(currentDirectory + "/saves")
    numFiles = len(glob.glob1(os.getcwd(),"*.history"))
    plotRLHistory("duel_dqn_" + mapType, numFiles)
    os.chdir('..')

def plotNEATHistory2(fileprefix, numFiles):
    objects = []

    for x in range(numFiles):
        fileName = fileprefix + str(x+1) + ".history"
        with open(fileName, 'r') as fp:
            line = fp.readline()
            dataArray = ast.literal_eval(line)
            dataArray = [int(n) for n in dataArray]
            objects.append(dataArray)
            while line:
                line = fp.readline()

    for x in range(numFiles):
        objects[x] = scaleValues(objects[x], 0.1)

    for x in range(numFiles):
        objects[x] = getAveragedList(objects[x], 100)

    maxEpisodes = 999999999999999999999
    for x in range(numFiles):
        maxEpisodes = min(len(objects[x]), maxEpisodes)


    tmpCSV = "Test;Episode Reward;Episodes\n"
    for x in range(numFiles):
        for y in range(maxEpisodes):
            tmpCSV += str(x+1) + ";" + str(objects[x][y]) + ";" + \
                      str(y+1) + "\n"

    #print(tmpCSV)
    TESTDATA = StringIO(tmpCSV)
    print(TESTDATA)
    df = pd.read_csv(TESTDATA, sep=";")

    sns.set(rc={'axes.facecolor': 'gainsboro'})
    ax = sns.lineplot(data=df, x="Episodes", y="Episode Reward", color='red', linewidth=1.0)

    ax.set(ylabel='Score', xlabel='Episodes x 50')
    #ax.set(ylabel='Reward', xlabel='Episodes')
    plt.ylim(0,100)
    plt.xlim(0, maxEpisodes)
    
    plt.show()



def plotNEATHistory1(mapType):
    currentDirectory = os.getcwd()
    os.chdir(currentDirectory + "/saves")
    numFiles = len(glob.glob1(os.getcwd(),"*.history"))
    plotNEATHistory2("NEAT_" + mapType, numFiles)
    os.chdir('..')
    return

#plotDQNHistory("notchMedium")
plotNEATHistory1("notchParam")

#makeTwoComparativeHistograms(scoresA, scoresB)