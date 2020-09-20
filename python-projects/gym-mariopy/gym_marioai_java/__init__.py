import logging
from gym.envs.registration import register

logger = logging.getLogger(__name__)

register(
    id='MarioAITraining-v0',
    entry_point='gym_marioai_java.envs:MarioAITraining1',
    max_episode_steps=1000,
    reward_threshold=10000.0,
    nondeterministic = True,
)

register(
    id='MarioAITest-v0',
    entry_point='gym_marioai_java.envs:MarioAITest1',
    max_episode_steps=1000,
    reward_threshold=10000.0,
    nondeterministic = True,
)