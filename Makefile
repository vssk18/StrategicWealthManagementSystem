# Makefile for Strategic Wealth Management System

# Compiler and flags
JAVAC = javac
JAVA = java
JAVAC_FLAGS = -d out -sourcepath src
JAVA_FLAGS = -cp out

# Source directories
SRC_DIR = src
OUT_DIR = out

# Main class
MAIN_CLASS = cli.StrategicWealthManagementSystem

# Source files
SOURCES = $(shell find $(SRC_DIR) -name "*.java")

.PHONY: all build run clean help

all: build

# Build the project
build:
	@echo "Building Strategic Wealth Management System..."
	@mkdir -p $(OUT_DIR)
	@$(JAVAC) $(JAVAC_FLAGS) $(SOURCES)
	@echo "✓ Build complete!"

# Run the application
run: build
	@echo "Starting application..."
	@$(JAVA) $(JAVA_FLAGS) $(MAIN_CLASS)

# Clean build artifacts
clean:
	@echo "Cleaning build artifacts..."
	@rm -rf $(OUT_DIR)
	@rm -f *.csv
	@echo "✓ Clean complete!"

# Quick demo with sample data
demo: build
	@echo "Running demo with sample portfolio..."
	@$(JAVA) $(JAVA_FLAGS) $(MAIN_CLASS)

# Compile and run in one step
quick: build run

# Display help information
help:
	@echo "Strategic Wealth Management System - Makefile Commands"
	@echo ""
	@echo "Available targets:"
	@echo "  make build   - Compile all Java source files"
	@echo "  make run     - Build and run the application"
	@echo "  make clean   - Remove compiled files and artifacts"
	@echo "  make demo    - Run with sample data"
	@echo "  make quick   - Build and run in one command"
	@echo "  make help    - Display this help message"
	@echo ""
	@echo "Examples:"
	@echo "  make         - Build the project (same as 'make build')"
	@echo "  make run     - Build and start the application"
	@echo "  make clean   - Clean up before a fresh build"

# Create JAR file (optional)
jar: build
	@echo "Creating JAR file..."
	@jar cfe StrategicWealthManagement.jar $(MAIN_CLASS) -C $(OUT_DIR) .
	@echo "✓ JAR file created: StrategicWealthManagement.jar"
	@echo "  Run with: java -jar StrategicWealthManagement.jar"
