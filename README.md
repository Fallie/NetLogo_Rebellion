

1. 	cd under netlogo_rebellion/ folder
	To compile run the following: 
	javac -cp lib/commons-collections4-4.1.jar:lib/commons-codec-1.10.jar:lib/poi-3.16.jar:. -sourcepath src -d out src/**/*.java
	javac -cp lib/commons-collections4-4.1.jar:lib/commons-codec-1.10.jar:lib/poi-3.16.jar:. -sourcepath src -d out src/*.java
	
2.	To run the program
	java -cp .:out:**/*.class:*.class:lib/commons-collections4-4.1.jar:lib/commons-codec-1.10.jar:lib/poi-3.16.jar Output 50 0.35 0.15 0.50 4 1 5 0 100