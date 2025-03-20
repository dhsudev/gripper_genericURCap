JAVAC = javac
SOURCES = GripperApp.java GripperServer.java GripperUI.java GripperPanel.java
CLASSES = GripperApp.class GripperServer.class GripperUI.class GripperPanel.class
BIN_DIR = bin

all: $(BIN_DIR)/GripperApp.class

$(BIN_DIR)/GripperApp.class: $(SOURCES)
	$(JAVAC) -d $(BIN_DIR) $(SOURCES)



clean:
	rm -rf $(BIN_DIR)
