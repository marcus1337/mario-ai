/* File : example.cxx */

#include "JavaPorts.h"

void JavaPorts::init(int numTrees, int maxOtherInteriors, int maxUnorderedInteriors,
    int maxDecorators, int maxActions, int maxConditions) {
    if (aiType == BT) {
        BTInterface.init(numTrees, maxOtherInteriors, maxUnorderedInteriors,
            maxDecorators, maxActions, maxConditions);
    }
}

void JavaPorts::init(int numInputNodes, int numOutputNode, int numAIs) {
    if (aiType == NEAT) {
        NEATInterface.init(numInputNodes, numOutputNode, numAIs);
    }
}

int JavaPorts::getGeneration() {
    if (aiType == NEAT) {
        return NEATInterface.generation;
    }
    if (aiType == BT) {
        return BTInterface.generation;
    }
    return -1;
}

std::string JavaPorts::getTreeString(int index) {
    if (aiType == BT) {
        return BTInterface.getTreeString(index);
    }

    return "";
}

std::string JavaPorts::getEliteTreeString(int index) {
    if (aiType == BT) {
        return BTInterface.getEliteTreeString(index);
    }

    return "";
}

void JavaPorts::saveGeneration(std::string filename) {
    if (aiType == BT) {
        BTInterface.saveGeneration(filename);
    }
    if (aiType == NEAT) {
        NEATInterface.saveGeneration(filename);
    }
}

void JavaPorts::loadGeneration(std::string filename, int generation) {
    if (aiType == BT) {
        BTInterface.loadGeneration(filename, generation);
    }
    if (aiType == NEAT) {
        NEATInterface.loadGeneration(filename, generation);
    }
}

void JavaPorts::evolve() {
    if (aiType == BT) {
        BTInterface.evolve();
    }
    if (aiType == NEAT) {
        NEATInterface.evolve();
    }
}

void JavaPorts::setFitness(int index, int fitness) {
    if (aiType == BT) {
        BTInterface.setFitness(index, fitness);
    }
    if (aiType == NEAT) {
        NEATInterface.setFitness(index, fitness);
    }
}

void JavaPorts::setBehavior(int index, std::vector<int> behaviors) {
    if (aiType == BT) {
        BTInterface.setBehavior(index, behaviors);
    }
    if (aiType == NEAT) {
        NEATInterface.setBehavior(index, behaviors);
    }
}

void JavaPorts::setTargetSpecies(int numTargetSpecies) {
    if (aiType == BT) {
        BTInterface.setTargetSpecies(numTargetSpecies);
    }
    if (aiType == NEAT) {
        NEATInterface.setTargetSpecies(numTargetSpecies);
    }
}

void JavaPorts::setSurpriseEffect(float effect) {
    if (aiType == BT) {
        BTInterface.setSurpriseEffect(effect);
    }
    if (aiType == NEAT) {
        NEATInterface.setSurpriseEffect(effect);
    }
}

JavaPorts::JavaPorts(AIType _aitype) : aiType(_aitype) {

}

void JavaPorts::mapElites() {
    if (aiType == BT) {
        BTInterface.mapElites();
    }
    if (aiType == NEAT) {
        NEATInterface.mapElites();
    }
}

void JavaPorts::randomizeBTPopulation(int minNodes, int maxNodes) {
    if (aiType == BT) {
        BTInterface.randomizePopulation(minNodes, maxNodes);
    }
    if (aiType == NEAT) {
        NEATInterface.randomizePopulation(minNodes, maxNodes);
    }
}

void JavaPorts::randomizePopulationFromElites() {
    if (aiType == BT) {
        BTInterface.randomizePopulationFromElites();
    }
    if (aiType == NEAT) {
        NEATInterface.randomizePopulationFromElites();
    }
}

void JavaPorts::saveElites(std::string foldername) {
    if (aiType == BT) {
        BTInterface.saveElites(foldername);
    }
    if (aiType == NEAT) {
        NEATInterface.saveElites(foldername);
    }
}

void JavaPorts::loadElites(std::string foldername) {
    if (aiType == BT) {
        BTInterface.loadElites(foldername);
    }
    if (aiType == NEAT) {
        NEATInterface.loadElites(foldername);
    }
}

void JavaPorts::storeElites() {
    if (aiType == BT) {
        BTInterface.storeElites();
    }
    if (aiType == NEAT) {
        NEATInterface.storeElites();
    }
}