#!/bin/bash
for i in {1..20}
do
    outfile="./decisionSaves/dqnDecisions_notchParam$i.txt"
    python ExperimentStarter.py $i >> $outfile
done


