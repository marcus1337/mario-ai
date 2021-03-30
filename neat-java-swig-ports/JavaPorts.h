/* File : example.h */

#include <string>
#include <vector>
#include "Coordinator.h"




#ifndef JAVAPORTS_H
#define JAVAPORTS_H


class JavaPorts {
   NTE::Coordinator NEATInterface;

public:

    //JavaPorts();
    //~JavaPorts();

    void init(int numInputNodes, int numOutputNode, int numAIs);

    int getGeneration();

    void saveGeneration(std::string filename);

    void loadGeneration(std::string filename, int generation);

    void evolve();

    void setFitness(int index, int fitness);
    void setBehavior(int index, std::vector<int> behaviors);
    void setTargetSpecies(int numTargetSpecies);
    void setSurpriseEffect(float effect);

    void mapElites();
    void randomizePopulation(int minNodes, int maxNodes);
    void randomizePopulationViaElites();
    void saveElites(std::string foldername = "NEATS_ELITE");
    void loadElites(std::string foldername = "NEATS_ELITE");
    void storeElites();

    void loadBestElite(std::string foldername, std::string filename);
    void saveBestElite(std::string foldername, std::string filename);

    void calcNEATInput(int index, std::vector<float> inputs);
    std::vector<float> getNEATOutput(int index);
    void calcNEATEliteInput(int index, std::vector<float> inputs);
    std::vector<float> getNEATEliteOutput(int index);


    void insertEliteIntoGeneration(int eliteIndex, int aiIndex);
    void setMaxHiddenNodes(int numNodes);

    int getNumElites();
    int getNumElitesOfUniqueDimensionValue(int dimension);
    int getNumElitesOfDimensionWithValue(int dimension, int value);
    int getNumElitesOfDimensionWithinThreshold(int dimension, int low, int high);
    std::vector<int> getBestEliteBehavior();
    void setMutationRates(float newNodeRate, float newLinkRate, float randomizeLinkRate, float mutateLinkRate, float enableDisableLinkRate, bool enableExtraMutationRate = false);


    void storeElitesInVector();
    void changeEliteFitnessAndBehvaior(int index, int newFitness, std::vector<int> behaviors);
    void refactorEliteMapping();

   // void resetRecurrentState(int aiIndex);
};

#endif // !JAVAPORTS_H