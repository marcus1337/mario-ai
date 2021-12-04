from os import listdir
from os.path import isfile, join
from EventCounting import EventCounter

class FileHandler:

    def __init__(self):
        self.eventTypes = ["GAP", "CLOSE-GAP", "STOMPABLE-ENEMY", "NONSTOMPABLE-ENEMY", "CLOSE-STOMPABLE-ENEMY", "CLOSE-NONSTOMPABLE-ENEMY"]
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

    def countEvents(self, files):
        event_counter = EventCounter(self.eventTypes)
        for i in range(0, 20):
            event_counter.count_events(files[i])
        return event_counter