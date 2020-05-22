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

void JavaPorts::saveGeneration(std::string filename) {
    if (aiType == BT) {
        BTInterface.saveGeneration(filename);
    }
    if (aiType == NEAT) {
        NEATInterface.save(0, filename);
    }
}

void JavaPorts::loadGeneration(std::string filename, int generation) {
    if (aiType == BT) {
        BTInterface.loadGeneration(filename, generation);
    }
    if (aiType == NEAT) {
        NEATInterface.load(filename, 0);
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
}

void JavaPorts::setTargetSpecies(int numTargetSpecies) {
    if (aiType == BT) {
        BTInterface.setTargetSpecies(numTargetSpecies);
    }
}

JavaPorts::JavaPorts(AIType _aitype) : aiType(_aitype) {

}