
1. Create the out dir to contain compailed files
	mkdir out

2. 	cd under netlogo_rebellion/ folder
	To compile run the following: 
	javac -cp lib/commons-collections4-4.1.jar:lib/commons-codec-1.10.jar:lib/poi-3.16.jar:. -sourcepath src -d out src/**/*.java
	javac -cp lib/commons-collections4-4.1.jar:lib/commons-codec-1.10.jar:lib/poi-3.16.jar:. -sourcepath src -d out src/*.java
	
3.	To run the program
	
 	java -cp .:out:**/*.class:*.class:lib/commons-collections4-4.1.jar:lib/commons-codec-1.10.jar:lib/poi-3.16.jar Output 50 0.50 0.04 0.80 30 true 7  100 false false false > output.txt

	The parameters: 
		int numOfPatches, 
		double initialAgentDensity, 
		double initialCopDensity,
    	double governmentLegitimacy,
        int maxJailTerm,
        boolean movement,
        int vision, 
        int ticks,
        boolean extension, with or without extension
        boolean graduallyChangeGov: whether the government legitimacy drops gradually
        boolean sharplyChangGov: whether the government legitimacy drops at once

    After execution, the output of the patches will be in the "output.txt". And the statistics of the agents will be in the file "Rebellion".


