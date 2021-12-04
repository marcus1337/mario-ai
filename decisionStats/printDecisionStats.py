from os import listdir
from os.path import isfile, join
import re

eventTypes = ["GAP", "CLOSE-GAP", "STOMPABLE-ENEMY", "NONSTOMPABLE-ENEMY", "CLOSE-STOMPABLE-ENEMY", "CLOSE-NONSTOMPABLE-ENEMY"]

class Event:
    def __init__(self, event_type):
        self.event_type = event_type
        self.num_right = 0
        self.num_left = 0
        self.num_none = 0
        self.num_shoot = 0
        self.num_run = 0
        self.num_jump = 0
        self.num_event_occurances = 0

class EventCounter:
    def __init__(self, eventTypes):
        self.events = {}
        for eventType in eventTypes:
            self.events[eventType] = Event(eventType)
    
    def getParenthesisString(self, valueStr):
        return re.search(r'\((.*?)\)', valueStr).group(1)

    def isEvent(self, valueStr):
        return valueStr.startswith('EVENT')

    def getEventTypes(self, params):
        result = []
        for valueStr in params:
            if self.isEvent(valueStr):
                result.append(self.getParenthesisString(valueStr))
        return result

    def isActionTrue(self, valueAsStr):
        if valueAsStr == "false":
            return False
        return True

    def getActionValues(self, params):
        result = []
        for param in params:
            if not self.isEvent(param):
                value = self.isActionTrue(self.getParenthesisString(param))
                result.append(value)
        return result
    
    def addValuesToEvent(self, event, actions):
        action_values = [int(val) for val in actions]
        ev = self.events[event]
        ev.num_right += action_values[0]
        ev.num_left += action_values[1]
        ev.num_none += action_values[2]
        ev.num_shoot += action_values[3]
        ev.num_run += action_values[4]
        ev.num_jump += action_values[5]
        ev.num_event_occurances += 1

    def count_events(self, lines):
        for line in lines:
            params = line.split()
            actions = self.getActionValues(params)
            lineEvents = self.getEventTypes(params)
            for event in lineEvents:
                self.addValuesToEvent(event, actions)

class FileHandler:
    def __init__(self):
        self.dqn_medium_folder = "dqn//notchMedium//"
        self.dqn_param_folder = "dqn//notchParam//"
        self.neat_medium_folder = "neat//notchMedium//"
        self.neat_param_folder = "neat//notchParam//"

        self.neat_medium_lines = []
        self.neat_param_lines = []
        self.dqn_medium_lines = []
        self.dqn_param_lines = []

        self.loadFiles()

    def getFileNames(self,filepath):
        files = [f for f in listdir(filepath) if isfile(join(filepath, f))]
        return files

    def getFileNameByNum(self, filepath, fileNum):
        files = self.getFileNames(filepath)
        for file in files:
            if fileNum in file and ("1"+fileNum) not in file:
                return file

    def loadFilesLineLists(self, folder_path, line_list):
        for i in range(1, 21):
            filename = self.getFileNameByNum(folder_path, str(i))
            filename_and_path = folder_path + filename
            with open(filename_and_path) as f:
                line_list.append(f.read().splitlines())

    def loadFiles(self):
        self.loadFilesLineLists(self.dqn_medium_folder, self.dqn_medium_lines)
        self.loadFilesLineLists(self.dqn_param_folder, self.dqn_param_lines)
        self.loadFilesLineLists(self.neat_medium_folder, self.neat_medium_lines)
        self.loadFilesLineLists(self.neat_param_folder, self.neat_param_lines)

def countEvents(files):
    event_counter = EventCounter(eventTypes)
    for i in range(0, 20):
        event_counter.count_events(files[i])
    return event_counter

def addLjust(text, ljustValue, extraSpace):
    ljustValue += extraSpace
    text = text.ljust(ljustValue, ' ')
    return text, ljustValue

def addEventValue(text, ljustValue, value):
    text += str(value)
    text, ljustValue = addLjust(text, ljustValue, 10)
    return text, ljustValue

def getResultTitle():
    titleLjustValue = 0
    result = "EVENT"
    result, titleLjustValue = addLjust(result, titleLjustValue, 30)
    result += "Events"
    result, titleLjustValue = addLjust(result, titleLjustValue, 10)
    result += "Right"
    result, titleLjustValue = addLjust(result, titleLjustValue, 10)
    result += "Left"
    result, titleLjustValue = addLjust(result, titleLjustValue, 10)
    result += "None"
    result, titleLjustValue = addLjust(result, titleLjustValue, 10)
    result += "Shoot"
    result, titleLjustValue = addLjust(result, titleLjustValue, 10)
    result += "Run"
    result, titleLjustValue = addLjust(result, titleLjustValue, 10)
    result += "Jump"
    result, titleLjustValue = addLjust(result, titleLjustValue, 10)
    result += "\n"
    return result


def printResults(counter):

    result = getResultTitle()

    for key in counter.events:
        row = key
        ljustValue = 0
        values = counter.events[key]

        row, ljustValue = addLjust(row, ljustValue, 30)
        row, ljustValue = addEventValue(row, ljustValue, values.num_event_occurances)
        row, ljustValue = addEventValue(row, ljustValue, values.num_right)
        row, ljustValue = addEventValue(row, ljustValue, values.num_left)
        row, ljustValue = addEventValue(row, ljustValue, values.num_none)
        row, ljustValue = addEventValue(row, ljustValue, values.num_shoot)
        row, ljustValue = addEventValue(row, ljustValue, values.num_run)
        row, ljustValue = addEventValue(row, ljustValue, values.num_jump)
        result += row + "\n"
    
    print(result)


file_handler = FileHandler()
dqn_medium_counter = countEvents(file_handler.dqn_medium_lines)
dqn_param_counter = countEvents(file_handler.dqn_param_lines)
neat_medium_counter = countEvents(file_handler.neat_medium_lines)
neat_param_counter = countEvents(file_handler.neat_param_lines)

print("|-------------------DQN MEDIUM-------------------------|")
printResults(dqn_medium_counter)
print("|-------------------DQN PARAM--------------------------|")
printResults(dqn_param_counter)
print("|-------------------NEAT MEDIUM------------------------|")
printResults(neat_medium_counter)
print("|-------------------NEAT PARAM-------------------------|")
printResults(neat_param_counter)
print("|------------------------------------------------------|")


