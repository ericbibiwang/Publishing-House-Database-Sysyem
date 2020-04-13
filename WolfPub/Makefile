all: dependencies wolfpub

.PHONY: all dependencies

dependencies:
	mvn clean dependency:copy-dependencies

wolfpub: dependencies
	mvn package
