JAVAC = javac
SOURCES = GripperApp.java GripperServer.java GripperUI.java GripperPanel.java
CLASSES = GripperApp.class GripperServer.class GripperUI.class GripperPanel.class
BIN_DIR = bin

all: run

run : build
	java -cp $(BIN_DIR) GripperApp

build: $(SOURCES)
	mkdir -p $(BIN_DIR)
	$(JAVAC) -d $(BIN_DIR) $(SOURCES)

clean:
	rm -rf $(BIN_DIR)

.PHONY: all run build clean
