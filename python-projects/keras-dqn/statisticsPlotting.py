
import pandas as pd
import math
from matplotlib import pyplot as plt
from matplotlib.ticker import MaxNLocator
import numpy as np
import matplotlib.mlab as mlab
from scipy.stats import norm
from matplotlib.ticker import MultipleLocator

#round floats to closest whole number in order to simplify visualization (histogram plot of bins from 0 to 100)


scores_NEAT_notchMedium = [
    44.111968994140625,
    43.24018096923828,
    43.75408172607422,
    45.54356002807617,
    42.1792106628418,
    42.25156784057617,
    46.161678314208984,
    44.57902908325195,
    41.79842758178711,
    46.03965377807617,
    48.0965690612793,
    42.016387939453125,
    45.354774475097656,
    44.743282318115234,
    42.40510177612305,
    42.92262268066406]

scores_NEAT_notchParam = [
    29.347915649414062,
    30.2724666595459,
    16.511194229125977,
    29.273218154907227,
    24.741252899169922]

scores_DQN_notchMedium = [40.179911406, 37.01227583, 39.031984660000006, 37.26156925, 21.874435151000004, 26.196237106, 43.61040539, 41.81673040000002, 44.80799934999998, 41.113885471000025, 30.259957911, 43.98625213999998, 44.233354934000005, 34.42878024600001, 31.78959262199999, 38.03068688999999]
scores_DQN_notchParam = [2.6801077600000043, 9.308222170000008, 9.631616484000007, 10.485690985000007, 14.502794236000002]

scoresA = scores_NEAT_notchMedium
scoresB = scores_DQN_notchMedium

print(sorted(scoresA))
print(sorted(scoresB))

def makeSingleHistogram(scores):

    (mu, sigma) = norm.fit(scores)
    scores = [ int(int(elem)) for elem in scores ]
    ax = plt.figure().gca()
    ax.yaxis.set_major_locator(MaxNLocator(integer=True))
    #maxRange = min(max(scores)+3,101)
    #minRange = max(min(scores)-3,0)
    plt.xticks(np.arange(0, 101, 1))
    n, bins, patches = plt.hist(scores,bins=range(min(scores),max(scores)+2,1), range=[0,100], edgecolor='black', linewidth=1.5, density=True)
    plt.xlim([min(bins),max(bins)])
    y = norm.pdf( bins, mu, sigma)
    l = plt.plot(bins, y, 'r--', linewidth=1, label='$\mathcal{N}$ ' + f'$( \mu \\approx{ "{:.2f}".format(mu) } , \sigma \\approx { "{:.2f}".format(sigma) } )$')
    print(bins)

    plt.xlabel('Score')
    plt.ylabel('Density')
    plt.legend()
    ax.set_axisbelow(True)
    ax.yaxis.grid(color='gray', linestyle='dashed')
    ax.xaxis.grid(color='gray', linestyle='dashed')
    plt.tight_layout()
    plt.show()

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




#makeSingleHistogram(scores2)

makeTwoComparativeHistograms(scoresA, scoresB)