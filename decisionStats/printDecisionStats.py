from EventCounting import EventCounter
from FileHandling import FileHandler
from Printing import ResultPrinter

file_handler = FileHandler()
dqn_medium_counter = file_handler.countEvents(file_handler.dqn_medium_lines)
dqn_param_counter = file_handler.countEvents(file_handler.dqn_param_lines)
neat_medium_counter = file_handler.countEvents(file_handler.neat_medium_lines)
neat_param_counter = file_handler.countEvents(file_handler.neat_param_lines)

resultPrinter = ResultPrinter()
resultPrinter.printResults(dqn_medium_counter, "DQN MEDIUM")
resultPrinter.printResults(dqn_param_counter, "DQN PARAM")
resultPrinter.printResults(neat_medium_counter, "NEAT MEDIUM")
resultPrinter.printResults(neat_param_counter, "NEAT PARAM")


