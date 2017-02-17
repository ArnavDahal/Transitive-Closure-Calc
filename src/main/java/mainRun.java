import com.scitools.understand.*;
import org.jgrapht.GraphMapping;
import org.jgrapht.alg.TransitiveClosure;
import org.jgrapht.alg.isomorphism.VF2SubgraphIsomorphismInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.io.*;
import java.util.Iterator;
import java.util.Set;

public class mainRun {


    // This program will take 2 input UDB files and compute Transitive closure and Isomorphism
    public static void main(String[] args) {

        // DB path strings
        String oldDBPath = "UDB\\old.udb";
        String newDBPath = "UDB\\new.udb";

        //Gets input
        try {
            BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Path for Older DB: ");
            oldDBPath = bufferReader.readLine();
            System.out.println("Path for newer DB: ");
             newDBPath = bufferReader.readLine();
        }
            catch (Exception ex)
            {
                System.out.println("Error on input: " + ex);
            }

        // Defaulter
        if (oldDBPath.equals("") || newDBPath.equals(""))
        {
            oldDBPath = "UDB\\old.udb";
            newDBPath = "UDB\\new.udb";
        }
        // Creates the graphs we need
        SimpleDirectedGraph<String, DefaultEdge> newDBCall;
        SimpleDirectedGraph<String, DefaultEdge> oldDBCall;
        SimpleDirectedGraph<String, DefaultEdge> newDBDepend;
        SimpleDirectedGraph<String, DefaultEdge> oldDBDepend;


        // Runs the grapher
        newDBCall = getCallGraph(newDBPath, "callby", null);
        oldDBCall = getCallGraph(oldDBPath, "callby", null);
        oldDBDepend = getCallGraph(oldDBPath, null, null);
        newDBDepend = getCallGraph(newDBPath, null, null);


        // Sees if isomorphism exists between the graphs
        isoExists(oldDBCall, newDBCall, "Call Graphs");
        isoExists(oldDBDepend, newDBDepend, "Dependency Graphs");

        // Saves the graphs to a text file
        System.out.println("Saving graphs to text files");
        saveToFile(newDBCall, "newDBCall.txt");
        saveToFile(oldDBCall, "oldDBCall.txt");
        saveToFile(newDBDepend, "newDBDepend.txt");
        saveToFile(oldDBDepend, "oldDBDepend.txt");

        /*
        // Running transitive closure causes long stalls and crashes
        // Computes the transitive closure
        System.out.println("Computing Transitive closure");

        System.out.println("\t Old Database Dependency");
        //TransitiveClosure.INSTANCE.closeSimpleDirectedGraph(oldDBDepend);

        System.out.println("\t New Database Dependency");
        TransitiveClosure.INSTANCE.closeSimpleDirectedGraph(newDBDepend);

        saveToFile(newDBDepend, "newDBDependClosed.txt");
        saveToFile(newDBDepend, "newDBDependClosed.txt");
        */

        System.out.println("\t Old Database Call");
        TransitiveClosure.INSTANCE.closeSimpleDirectedGraph(oldDBCall);

        System.out.println("\t New Database Call");
        TransitiveClosure.INSTANCE.closeSimpleDirectedGraph(newDBCall);

        System.out.println("Finished Transitive Closure");

        // Saves the closed graphs to text files
        System.out.println("Saving closed graphs to text files");
        saveToFile(newDBCall, "newDBCallClosed.txt");
        saveToFile(oldDBCall, "oldDBCallClosed.txt");


    }


    // Checks if Isomorphism exists
    public static void isoExists(SimpleDirectedGraph<String, DefaultEdge> gOld,
                                 SimpleDirectedGraph<String, DefaultEdge> gNew,
                                 String type) {
        VF2SubgraphIsomorphismInspector<String, DefaultEdge> vf =
                new VF2SubgraphIsomorphismInspector<>(gOld, gNew);
//        Iterator<GraphMapping<String, DefaultEdge>> iter = vf.getMappings();

        System.out.println("Isomorphism exists for " + type + ": " + vf.isomorphismExists());

    }

    // Saves graphs to files
    public static void saveToFile(SimpleDirectedGraph<String, DefaultEdge> g, String fileName) {

        // Print stream for files
        PrintStream out;
        try {
            out = new PrintStream(new FileOutputStream(fileName));

            Set<DefaultEdge> edges = g.edgeSet();

            // Prints line by line to a files
            for (DefaultEdge e : edges) {
                out.print(String.format("\"%s\" -> \"%s\"\n", g.getEdgeSource(e), g.getEdgeTarget(e)));


            }
            // Closes the stream
            out.close();
        } catch (FileNotFoundException e) {

        } finally {
        }
    }


    // Returns a simpleDirected graph that has had vertexes and edges added.
    public static SimpleDirectedGraph<String, DefaultEdge> getCallGraph(String inputPath, String param1, String param2) {

        // Graph used to return
        SimpleDirectedGraph<String, DefaultEdge> g;
        g = new SimpleDirectedGraph<String, DefaultEdge>(
                DefaultEdge.class);
        String called; // What method/class was called
        String calledBy; // What called the method/class

        try {
            //Open the Understand Database
            Database db = Understand.open(inputPath);

            // Get a list of all functions and methods
            Entity[] mEnts = db.ents("method ~unknown ~unresolved, class ~unknown ~unresolved");

            for (Entity e : mEnts) {

                called = null;

                // Sets the caller to the entity name
                called = e.name();

                // If the vertex isnt already in the graph
                if (!g.containsVertex(called))
                    g.addVertex(called);



                Reference[] callRefs = e.refs(param1, param2, true);

                for (Reference pRef : callRefs) {

                    calledBy = null;
                    Entity pEnt = pRef.ent();

                    // Sets the calledby to the ref name
                    calledBy = pEnt.name();

                    // If the vertex isnt already in the graph
                    if (!g.containsVertex(calledBy))
                        g.addVertex(calledBy);

                    try {
                        // Adds the edge
                        g.addEdge(calledBy, called);

                    } catch (Exception ex) {

                    }


                }
            }
        } catch (UnderstandException e) {
            System.out.println("Failed opening Database:" + e.getMessage());
            System.exit(0);
        }
        return g;
    }


}