
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
    
    def get(self, index):
        if index == 0:
            return self.num_right
        if index == 1:
            return self.num_left
        if index == 2:
            return self.num_none
        if index == 3:
            return self.num_shoot
        if index == 4:
            return self.num_run
        if index == 5:
            return self.num_jump
        

class EventCounter:
    def __init__(self, eventTypes):
        self.events = {}
        for eventType in eventTypes:
            self.events[eventType] = Event(eventType)
    
    def getParenthesisString(self, valueStr):
        import re
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