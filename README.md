Information:
	This program will compute Isomorphic and Transitive closures of graphs for two UDB files that have been fed in
	

Structure:
The program console will print out with graphs are isomorphic or not
The program will then save text files to the local directory of the program project for the structure of the graphs
The call graphs strucute is as:
Caller -> Called
	ex.
	Main.main -> Class B
	Where main called Class B.
For the Dependcy graphs
The first depends on the second

How to run:
	Requirements:
	Java
	IntelliJ IDE with SBT
	Scitools Understand JAR
	jgrpaht jar

	Step 1: Open the project in intellij
			Import it as a SBT project if you have to
			If the librarys arent working from SBT you have to manually
			add the libraries to your project.
			The JGraphT Jar is included in the project folder, for SciTools Understand you will have to find that yourself as it is licensed.
		
	Step 2. Run the program
			Feed the input for the absolute paths for the udb files
			To use the default files I included, just press enter twice with no input
	
	Step 3. Check the data
			The program may take some time to run depending on the size of the projects in the UDB files
			Go through the .txt files saved in the local directory of the project to see the graphs


Tests:
 I did not include any JUNIT tests in this as there is only 1 method that does any real work, and it returns a Graph.
 It is easier to to test that method by inputting the test udb file I made.
 I first suggest UNCOMMENTING lines 66 and 79 from mainRun.java in /src/main/java
 When prompted for a input path, for both put in:
	> UDB/tester.udb
 The isomorphism should come back true for both of them.
 And the .txt files that are saved in the local dir should be the same between old and new
 

 To test larger files, just press enter twice when prompted and it will test two versions of epoxy master:
 I suggest RECOMMMENTING (block comments /* and */) on lines 66 and 79 as larger projects tend to crash the Transitive closure call on the dependcy graphs with out of memory errors
 Old: https://github.com/airbnb/epoxy/tree/bc867ac1e50d4e2cc37caace407b403c14b9aa89
 New: https://github.com/airbnb/epoxy/tree/14d7592daf59f3fdf0d026928b8b5faf1daf5067


	