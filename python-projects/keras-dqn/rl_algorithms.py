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

def trainRL(agent, rlName, environment):
    agent.fit(environment, nb_steps=NUM_STEPS, visualize=False, verbose=2)
    agent.save_weights(rlName, overwrite=True)

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
    tmpDQN = DQNAgent(model=model, nb_actions=nb_actions, memory=memory, nb_steps_warmup=10,
                   enable_dueling_network=True, dueling_type='avg', enable_double_dqn=True, target_model_update=1e-2, policy=policy)
    tmpDQN.compile(Adam(lr=1e-3), metrics=['mae'])
    return tmpDQN

def getDuelDQNModel():
    model = Sequential()
    model.add(Flatten(input_shape=(1,) + (NUM_OBSERVATIONS,)))
    model.add(Dense(95, activation='relu'))
    model.add(Dense(95, activation='relu'))
    model.add(Dense(nb_actions, activation='linear'))
    #print(model.summary())
    memory = SequentialMemory(limit=50000, window_length=1)
    policy = BoltzmannQPolicy()
    tmpDQN = DQNAgent(model=model, nb_actions=nb_actions, memory=memory, nb_steps_warmup=10,
                   enable_dueling_network=True, dueling_type='avg', enable_double_dqn=True, target_model_update=1e-2, policy=policy)
    tmpDQN.compile(Adam(lr=1e-3), metrics=['mae'])
    return tmpDQN


def trainRLNetworks(numNetworks, mapType):
    env = getEnv(ENV_NAME_TRAINING)
    env.setMapType(mapType)
    for i in range(numNetworks):
        rlModel = getSmallDuelDQNModel()
        rlNetworkName = "duel_dqn_notchParam" + str(i+1)
        rlDataName = "dqnTrainingData" + str(i+1)
        sys.stdout = open(rlDataName + ".txt", 'w')
        trainRL(rlModel, rlNetworkName, env)

def testRLNetwork(networkNumber, mapType):
    env = getEnv(ENV_NAME_TESTING)
    env.setMapType(mapType)
    rlNetworkName = "duel_dqn_notchParam" + str(networkNumber)
    rlModel = getSmallDuelDQNModel()
    completionPercentage = 0.0
    completionRates = []
    for i in range(100):
        env.lvl = i+1
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
    rlNetworkName = "duel_dqn_notchParam" + str(networkNumber)
    rlModel = getDuelDQNModel()
    testRL(rlModel, rlNetworkName, True, env)
    print("Completion %:" + str(env.getCompletionPercentage()))

def setDirectoryToSavesFolder():
    currentDirectory = os.getcwd()
    os.chdir(currentDirectory + "\\saves")

def testRLNetworks(NUM_SAMPLES, ENV_MAP_NAME):
    for i in range(NUM_SAMPLES):
        testRLNetwork((i+1), ENV_MAP_NAME)

#setDirectoryToSavesFolder()

#trainRLNetworks(NUM_SAMPLES, ENV_MAP_NAME2)
#for i in range(NUM_SAMPLES):
#    testRLNetwork((i+1), ENV_MAP_NAME2)




#testRLNetworkVisually(1, ENV_MAP_NAME2) #For Video Recordings.
