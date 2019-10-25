JFLAGS = -g
JC = javac
JVM = java
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
		CommonUtilities.java \
		SequenceGenerator.java \
		SequencePartitioner.java \
		SequenceAssembler.java


GENERATOR = SequenceGenerator
PARTITIONER = SequencePartitioner
ASSEMBLER = SequenceAssembler

default: classes

classes: $(CLASSES:.java=.class)

hw1-1: $(GENERATOR).class
			$(JVM) $(GENERATOR)

hw1-2: $(PARTITIONER).class
			$(JVM) $(PARTITIONER)

hw1-3: $(ASSEMBLER).class
			$(JVM) $(ASSEMBLER)

clean:
	$(RM) *.class