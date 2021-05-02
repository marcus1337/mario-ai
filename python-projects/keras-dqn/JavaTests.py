
import jpype
import jpype.imports
from jpype.types import *
# Launch the JVM
jpype.startJVM(classpath = ['/JARS/*'])

from java.lang import String
from MarioPackage import GenerateLevel

#db = GenerateLevel()
#print(db.initTestMap("notchParam"))


jarray = JArray(jpype.java.lang.Boolean)(5)
jarray[1] = jpype.java.lang.Boolean(True)
print(jarray)
