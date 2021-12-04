class ResultPrinter:
    def __init__(self):
        pass

    def addLjust(self, text, ljustValue, extraSpace):
        ljustValue += extraSpace
        text = text.ljust(ljustValue, ' ')
        return text, ljustValue

    def addEventValue(self, text, ljustValue, value):
        text += str(value)
        text, ljustValue = self.addLjust(text, ljustValue, 10)
        return text, ljustValue

    def getResultTitle(self):
        titleLjustValue = 0
        result = "EVENT"
        result, titleLjustValue = self.addLjust(result, titleLjustValue, 30)
        result += "Events"
        result, titleLjustValue = self.addLjust(result, titleLjustValue, 10)
        result += "Right"
        result, titleLjustValue = self.addLjust(result, titleLjustValue, 10)
        result += "Left"
        result, titleLjustValue = self.addLjust(result, titleLjustValue, 10)
        result += "None"
        result, titleLjustValue = self.addLjust(result, titleLjustValue, 10)
        result += "Shoot"
        result, titleLjustValue = self.addLjust(result, titleLjustValue, 10)
        result += "Run"
        result, titleLjustValue = self.addLjust(result, titleLjustValue, 10)
        result += "Jump"
        result, titleLjustValue = self.addLjust(result, titleLjustValue, 10)
        result += "\n"
        return result


    def printResults(self, counter, title):

        print("|-------------------" + title + "-------------------------|")

        result = self.getResultTitle()

        for key in counter.events:
            row = key
            ljustValue = 0
            values = counter.events[key]

            row, ljustValue = self.addLjust(row, ljustValue, 30)
            row, ljustValue = self.addEventValue(row, ljustValue, values.num_event_occurances)
            row, ljustValue = self.addEventValue(row, ljustValue, values.num_right)
            row, ljustValue = self.addEventValue(row, ljustValue, values.num_left)
            row, ljustValue = self.addEventValue(row, ljustValue, values.num_none)
            row, ljustValue = self.addEventValue(row, ljustValue, values.num_shoot)
            row, ljustValue = self.addEventValue(row, ljustValue, values.num_run)
            row, ljustValue = self.addEventValue(row, ljustValue, values.num_jump)
            result += row + "\n"
        
        print(result)