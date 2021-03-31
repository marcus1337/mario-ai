
import pandas as pd
import math
from matplotlib import pyplot as plt
from matplotlib.ticker import MaxNLocator
import numpy as np
import matplotlib.mlab as mlab
from scipy.stats import norm
from matplotlib.ticker import MultipleLocator

#round floats to closest whole number in order to simplify visualization (histogram plot of bins from 0 to 100)


scoresA = [20,22,33,24,26,33,34,33,33,32,22,26,30]
scoresB = [24,26,34,34,33,32,35,36,37,35,34,25,33,27,30]

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

    n1, bins1, patches1 = plt.hist(scores1,bins=range(min(scores1),max(max(scores1),max(scores2))+2,1), range=[0,100],color='green', edgecolor='darkgreen', linewidth=1.5, density=False, label="NEAT", alpha=0.5)
    n2, bins2, patches2 = plt.hist(scores2,bins=range(min(scores2),max(scores2)+2,1), range=[0,100], color='blue' , edgecolor='midnightblue', linewidth=1.5, density=False, label="RL",alpha=0.5)
    plt.xlim([min(min(bins1),min(bins2)),max(max(bins1),max(bins2))])


    #y1 = norm.pdf( bins1, mu1, sigma1)
    #l1 = plt.plot(bins1, y1, 'r--', linewidth=1, label='$\mathcal{N}$ ' + f'$( \mu \\approx{ "{:.2f}".format(mu1) } , \sigma \\approx { "{:.2f}".format(sigma1) } )$')


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