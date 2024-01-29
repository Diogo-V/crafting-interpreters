SRC_DIR := src
OUT_DIR := out
EXAMPLES_DIR := examples

MAIN = $(SRC_DIR).Lox
EXAMPLE = ex1.lox

scanner:
	javac -d $(OUT_DIR)/ $(SRC_DIR)/*.java
	java -cp $(OUT_DIR) $(MAIN) $(EXAMPLES_DIR)/$(EXAMPLE)

clean:
	rm $(OUT_DIR)/*.class

