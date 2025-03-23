# Compiler
JAVAC = javac

# Source files
SRCS = Main.java Communicator.java

# Class files
CLASSES = $(SRCS:.java=.class)

# Default target
all: $(CLASSES)

# Compile source files to class files
%.class: %.java
	$(JAVAC) $<

# Clean up build files
clean:
	rm -f $(CLASSES)

# Run the executable
run: Main.class
	java Main

.PHONY: all clean run
