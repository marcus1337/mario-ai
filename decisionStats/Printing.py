class ResultPrinter:
    def __init__(self):
        self.ljustValue = 0
        self.tableTop = self.getTableTop()
        self.ljustValue = 0

    def addLjust(self, text, extraSpace):
        self.ljustValue += extraSpace
        text = text.ljust(self.ljustValue, ' ')
        return text

    def addEventValue(self, text, value):
        text += str(value)
        text = self.addLjust(text, 10)
        return text
    
    def addTableTopHeader(self, tableStr, header):
        tableStr += header
        return self.addLjust(tableStr, 10)

    def getTableTop(self):
        result = "EVENT"
        result = self.addLjust(result, 30)
        result = self.addTableTopHeader(result, "Events")
        result = self.addTableTopHeader(result, "Right")
        result = self.addTableTopHeader(result, "Left")
        result = self.addTableTopHeader(result, "None")
        result = self.addTableTopHeader(result, "Shoot")
        result = self.addTableTopHeader(result, "Run")
        result = self.addTableTopHeader(result, "Jump")
        result += "\n"
        self.ljustValue = 0
        return result

    def getTitle(self, title):
        result = "|"
        result = result.ljust(40, '-')
        result += title
        result = result.ljust(len(result) + 55 - len(title), '-')
        result += "|"
        return result

    def printResults(self, counter, title):

        result = self.getTitle(title) + "\n"
        result += self.tableTop

        for key in counter.events:
            self.ljustValue = 0
            row = key
            values = counter.events[key]

            row = self.addLjust(row, 30)
            num_events = values.num_event_occurances
            row = self.addEventValue(row, values.num_event_occurances)
            for i in range(0, 6):
                row = self.addEventValue(row, values.get(i))
            result += row + "\n"
        
        print(result)