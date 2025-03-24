JAVAC = javac
BIN_DIR = bin
SRCS = Main.java Communicator.java
CLASSES = $(SRCS:%.java=$(BIN_DIR)/%.class)

all: $(BIN_DIR) $(CLASSES)

$(BIN_DIR):
	mkdir -p $(BIN_DIR)

$(BIN_DIR)/%.class: %.java
	$(JAVAC) -d $(BIN_DIR) $<

clean:
	rm -rf $(BIN_DIR)

run: all
	java -cp $(BIN_DIR) Main

.PHONY: all clean run