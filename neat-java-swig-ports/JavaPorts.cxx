/* File : example.cxx */

#include "JavaPorts.h"

#include <iostream>

/*JavaPorts::JavaPorts() {
    std::cout << "STARTING CONSTRUCTOR...\n" << std::endl;
}
JavaPorts::~JavaPorts() {
    std::cout << "CALLING DESTRUCTOR...\n" << std::endl;
}*/


void JavaPorts::init(int numInputNodes, int numOutputNode, int numAIs) {
    NEATInterface.init(numInputNodes, numOutputNode, numAIs);
}

int JavaPorts::getGeneration() {
    return NEATInterface.generation;
}

void JavaPorts::saveGeneration(std::string filename) {
    NEATInterface.saveGeneration(filename);
}

void JavaPorts::loadGeneration(std::string filename, int generation) {
    NEATInterface.loadGeneration(filename, generation);
}

void JavaPorts::evolve() {
    NEATInterface.evolve();
}

void JavaPorts::setFitness(int index, int fitness) {
    NEATInterface.setFitness(index, fitness);
}

void JavaPorts::setBehavior(int index, std::vector<int> behaviors) {
    NEATInterface.setBehavior(index, behaviors);
}

void JavaPorts::setTargetSpecies(int numTargetSpecies) {

    NEATInterface.setTargetSpecies(numTargetSpecies);
}

void JavaPorts::setSurpriseEffect(float effect) {
    NEATInterface.setSurpriseEffect(effect);
}

void JavaPorts::mapElites() {
    NEATInterface.mapElites();
}

void JavaPorts::randomizePopulation(int minNodes, int maxNodes) {
    NEATInterface.randomizePopulation(minNodes, maxNodes);
}

void JavaPorts::randomizePopulationViaElites() {
    NEATInterface.randomizePopulationFromElites();
}

void JavaPorts::saveElites(std::string foldername) {
    NEATInterface.saveElites(foldername);
}

void JavaPorts::loadElites(std::string foldername) {
    NEATInterface.loadElites(foldername);
}

void JavaPorts::storeElites() {
    NEATInterface.storeElites();
}

void JavaPorts::calcNEATInput(int index, std::vector<float> inputs) {
    NEATInterface.calcInput(index, inputs);
}

std::vector<float> JavaPorts::getNEATOutput(int index) {
    return NEATInterface.getOutput(index);
}

void JavaPorts::calcNEATEliteInput(int index, std::vector<float> inputs) {
    NEATInterface.calcEliteInput(index, inputs);
}

std::vector<float> JavaPorts::getNEATEliteOutput(int index) {
    return NEATInterface.getEliteOutput(index);
}

void JavaPorts::insertEliteIntoGeneration(int eliteIndex, int aiIndex) {
    NEATInterface.insertEliteIntoGeneration(eliteIndex, aiIndex);
}

void JavaPorts::loadBestElite(std::string foldername, std::string filename) {
    NEATInterface.loadBestElite(filename, foldername);
}

void JavaPorts::saveBestElite(std::string foldername, std::string filename) {
    NEATInterface.saveBestElite(filename, foldername);
}

void JavaPorts::setMaxHiddenNodes(int numNodes) {
    NEATInterface.setMaxHiddenNodes(numNodes);
}

int JavaPorts::getNumElites() {
    return NEATInterface.getNumElites();
}
int JavaPorts::getNumElitesOfUniqueDimensionValue(int dimension) {
    return NEATInterface.getNumElitesOfUniqueDimensionValue(dimension);
}
int JavaPorts::getNumElitesOfDimensionWithValue(int dimension, int value) {
    return NEATInterface.getNumElitesOfDimensionWithValue(dimension, value);
}
int JavaPorts::getNumElitesOfDimensionWithinThreshold(int dimension, int low, int high) {
    return NEATInterface.getNumElitesOfDimensionWithinThreshold(dimension, low, high);
}
std::vector<int> JavaPorts::getBestEliteBehavior() {
    return NEATInterface.getBestEliteBehavior();
}
void JavaPorts::setMutationRates(float newNodeRate, float newLinkRate, float randomizeLinkRate, float mutateLinkRate, float enableDisableLinkRate, bool enableExtraMutationRate) {
    NEATInterface.setMutationRates(newNodeRate, newLinkRate, randomizeLinkRate, mutateLinkRate, enableDisableLinkRate, enableExtraMutationRate);
}

void JavaPorts::storeElitesInVector() {
    NEATInterface.storeElitesInVector();
}

void JavaPorts::changeEliteFitnessAndBehvaior(int index, int newFitness, std::vector<int> behaviors) {
    NEATInterface.changeEliteFitnessAndBehvaior(index, newFitness, behaviors);
}

void JavaPorts::refactorEliteMapping() {
    NEATInterface.refactorEliteMapping();
}