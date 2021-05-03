import numpy as np
import gym

from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, Activation, Flatten
from tensorflow.keras.optimizers import Adam

from rl.agents.dqn import DQNAgent
from rl.policy import BoltzmannQPolicy
from rl.memory import SequentialMemory
import gym_marioai_java

import sys
import os
import pickle

ENV_NAME_TRAINING = 'MarioAITraining-v0'
ENV_NAME_TESTING = 'MarioAITest-v0'
ENV_MAP_NAME1 = "notchParam"
ENV_MAP_NAME2 = "notchMedium"
NUM_OBSERVATIONS = 237
nb_actions = 32
#NUM_SAMPLES = 5
NUM_STEPS = 100

def getEnv(envName):
    envTmp = gym.make(envName)
    np.random.seed(123)
    envTmp.seed(123)
    print(envTmp.action_space)
    return envTmp

def getDQNModel():
    return getSmallDuelDQNModel()

def trainRL(agent, rlName, environment):
    history = agent.fit(environment, nb_steps=NUM_STEPS, visualize=False, verbose=0)
    agent.save_weights(rlName, overwrite=True)
    return history

def testRL(agent, rlName, isVisual, environment):
    agent.load_weights(rlName)
    agent.test(environment, nb_episodes=1, visualize=isVisual)

def getSmallDuelDQNModel():
    model = Sequential()
    model.add(Flatten(input_shape=(1,) + (NUM_OBSERVATIONS,)))
    model.add(Dense(170, activation='relu', use_bias=False))
    model.add(Dense(nb_actions, activation='linear', use_bias=False))
    #print(model.summary())
    memory = SequentialMemory(limit=50000, window_length=1)
    policy = BoltzmannQPolicy()
    tmpDQN = DQNAgent(model=model, nb_actions=nb_actions, memory=memory, nb_steps_warmup=1000,
                   enable_dueling_network=True, dueling_type='avg', enable_double_dqn=True, target_model_update=1e-2, policy=policy, batch_size=512*2)
    tmpDQN.compile(Adam(lr=0.01), metrics=['mae'])
    return tmpDQN

def trainRLNetworks(numNetworks, mapType):
    env = getEnv(ENV_NAME_TRAINING)
    env.setMapType(mapType)
    for i in range(numNetworks):
        rlModel = getDQNModel()

        rlNetworkName = "duel_dqn_" + mapType + str(i+1)
        rlHistoryFile = rlNetworkName + ".history"
        history = trainRL(rlModel, rlNetworkName, env)
        with open(rlHistoryFile, 'wb') as file_pi:
            pickle.dump(history.history, file_pi)



def testRLNetwork(networkNumber, mapType):
    env = getEnv(ENV_NAME_TESTING)
    env.setMapType(mapType)
    rlNetworkName = "duel_dqn_" + mapType + str(networkNumber)
    rlModel = getDQNModel()
    completionPercentage = 0.0
    completionRates = []
    for i in range(100):
        env.setLvl(i+1)
        testRL(rlModel, rlNetworkName, False, env)
        completionPercentage += env.getCompletionPercentage()
        completionRates.append(env.getCompletionPercentage())
    completionPercentage /= 100.0

    f = open("dqnResult" + str(networkNumber) + ".txt", "w")
    f.write(str(completionPercentage) + '\n')
    f.write(str(completionRates))
    f.close()


def testRLNetworkVisually(networkNumber, mapType):
    env = getEnv(ENV_NAME_TESTING)
    env.setMapType(mapType)
    rlNetworkName = "duel_dqn_" + mapType + str(networkNumber+1)
    rlModel = getDQNModel()
    testRL(rlModel, rlNetworkName, True, env)
    print("Completion %:" + str(env.getCompletionPercentage()))

def setDirectoryToSavesFolder():
    currentDirectory = os.getcwd()
    os.chdir(currentDirectory + "\\saves")

def testRLNetworks(NUM_SAMPLES, ENV_MAP_NAME):
    for i in range(NUM_SAMPLES):
        testRLNetwork((i+1), ENV_MAP_NAME)

#testRLNetworkVisually(2, ENV_MAP_NAME1) #For Video Recordings.
