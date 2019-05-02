JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	./acotsp/*.java \
	./dss/*.java \
	./graphpckg/*.java \
	./tester/*.java 

default: classes jar

classes: $(CLASSES:.java=.class)

clean:
	$(RM) ./acotsp/*.class && $(RM) ./dss/*.class && $(RM) ./graphpckg/*.class && $(RM) ./tester/*.class
jar:
	jar cfm simulator.jar MANIFEST.MF ./acotsp/* ./dss/* ./graphpckg/* ./tester/*
