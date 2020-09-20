/* File : example.h */

#include "BTCode/Coordinator.h"
#include "Coordinator.h"
#include <string>


enum AIType {
    BT,NEAT, BT_NEAT
};

class JavaPorts {
    BTE::Coordinator BTInterface;
    NTE::Coordinator NEATInterface;
    AIType aiType;
public:
    JavaPorts(AIType _aitype);

    void init(int numTrees, int maxOtherInteriors, int maxUnorderedInteriors,
        int maxDecorators, int maxActions, int maxConditions);

    void init(int numInputNodes, int numOutputNode, int numAIs);

    int getGeneration();

    std::string getTreeString(int index);
    std::string getEliteTreeString(int index);

    void saveGeneration(std::string filename);

    void loadGeneration(std::string filename, int generation);

    void evolve();

    void setFitness(int index, int fitness);
    void setBehavior(int index, std::vector<int> behaviors);
    void setTargetSpecies(int numTargetSpecies);
    void setSurpriseEffect(float effect);

    void mapElites();
    void randomizeBTPopulation(int minNodes, int maxNodes);
    void randomizePopulationFromElites();
    void saveElites(std::string foldername = "TREES_ELITE");
    void loadElites(std::string foldername = "TREES_ELITE");
    void storeElites();

};