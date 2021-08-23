JFLAGS = -g
JC = javac
JVM= java 
FILE= sample1.txt

.SUFFIXES: .java .class

.java.class:
        $(JC) $(JFLAGS) $*.java

CLASSES = \
        Parallelism.java \
        ParaConfig.java 
        
MAIN = Parallelism

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JVM) $(MAIN) $(FILE)
   
clean:
        $(RM) *.class