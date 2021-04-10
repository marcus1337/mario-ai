
# gym_marioai_java

Python environment with the Open AI Gym interface. This package is used with another Java process that runs the game itself. This is more or less a python wrapper for the Java process such that it may be used by Python applications. In particular, this code is made to work with the Keras-rl2 library.

#How to use

Always start the Java process (server) before running this code (client), as it needs to connect via socket communication.

This package can either be run in test mode or training mode. In training mode there is no option to visually see what is happening. To activate visuals, run in test mode and explicitly activate visuals by calling the "render()" function. The training-mode environment is named "MarioAITraining-v0" and the test-mode environment "MarioAITest-v0".

There are two map types to chose from, "notchParam" and "notchMedium". The number of observations is 237, which consists of a simple boolean array with 237 entries. There are 32 possible actions to take in each step. A step runs for 5 in-game-frames.

When finished, close the Java process manually.

# Installation

```bash
cd <this directory>
pip install -e .
```
