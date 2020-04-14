all: wolfpub

.PHONY: all clean

wolfpub:
	mvn dependency:copy-dependencies package

docs: wolfpub
	mkdir -p documentation/html && mkdir -p documentation/man
	-cp target/generated-docs/*.1 documentation/man
	-cp target/generated-docs/*.html documentation/html

clean:
	mvn clean

