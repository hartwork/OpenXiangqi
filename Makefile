# Copyright (C) 2013 Sebastian Pipping <sebastian@pipping.org>
# Licensed under AGPL v3 or later

JAVA ?= java
JAVAC ?= javac
MKDIR_P ?= mkdir -p
LN_S ?= ln -s
JCFLAGS =

MAIN_CLASS = org.openxiangqi.ui.console.Main

# Build class path
empty =
space = $(empty) $(empty)
JAR_FILES = /usr/share/junit-4/lib/junit.jar
CLASS_PATH_BUILD = $(subst $(space),:,$(JAR_FILES))
CLASS_PATH_RUN = $(subst $(space),:,$(patsubst ./%,../%,$(JAR_FILES)))

SOURCE_BASE = ./src
BINARY_BASE = ./bin

JAVA_FILES = $(shell find $(SOURCE_BASE) -type f -name '*.java')
CLASS_FILES = $(patsubst $(SOURCE_BASE)/%,$(BINARY_BASE)/%,$(patsubst %.java,%.class,$(JAVA_FILES)))

all: build

$(BINARY_BASE):
	$(MKDIR_P) $@

define TEMPLATE
$(1): $(2) | $(BINARY_BASE)
	@echo "  JAVAC   $$@"
	@$$(JAVAC) $$(JCFLAGS) -classpath "$$(CLASS_PATH_BUILD)" -sourcepath "$$(SOURCE_BASE)" -d "$$(BINARY_BASE)" "$$<"
endef

# Make a rule for each .class/.java pair
$(foreach class_file,\
  $(CLASS_FILES),\
  $(eval \
    $(call TEMPLATE,\
      $(class_file),\
      $(patsubst %.class,\
        %.java,\
        $(patsubst $(BINARY_BASE)/%,\
          $(SOURCE_BASE)/%,\
          $(class_file)\
        )\
      )\
    )\
  )\
)

.PHONY: build
build: $(CLASS_FILES)

.PHONY: clean
clean:
	find "$(BINARY_BASE)" -type f -name '*.class' -delete

.PHONY: run
run: build
	$(JAVA) -classpath "$(CLASS_PATH_RUN):$(BINARY_BASE)" $(MAIN_CLASS)
