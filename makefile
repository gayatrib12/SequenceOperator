JFLAGS = -g
JC = javac
JVM= java
FILE=

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	SequenceGenerator.java \
	CommonUtilities.java
4
default: classes

classes: $(CLASSES:.java=.class)

run: SequenceGenerator.class
	$(JVM) SequenceGenerator

clean:
	$(RM) *.class