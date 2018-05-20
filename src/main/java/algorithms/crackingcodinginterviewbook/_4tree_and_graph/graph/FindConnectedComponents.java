package algorithms.crackingcodinginterviewbook._4tree_and_graph.graph;

/**
 * @author Tushar Chokshi @ 11/15/15.
 */

// Coursera Video - "2 - 5 Connected Components (18-56.mp4)"

// There can be multiple connected graphs in one graph. If you want to find out which connected graph a particular vertex belongs to then you need to maintain connectedGraphId in each vertex.
// DFS/BFS traverses one connected graph at a time, so you need to call DFS/BFS multiple times for each connected graph.

// This algorithm is also useful to find out total# of partitioned graphs in one graph.
public class FindConnectedComponents {
    public static void main(String[] args) {
        DepthFirstSearch dfs = new DepthFirstSearch();
        GraphWithAdjacencyList<Vertex> G = GraphWithAdjacencyList.createGraphOf3ConnectedGraphs(true);

        int connectedGraphId = 1;

        System.out.println("Created Graph: " + G.toString());
        if (G.size() > 0) {
            Vertex sourceVertex = G.iterator().next(); // select any random vertex in a graph
            System.out.println("Total Partitioned Graphs in this Graph: "+ findTotalPartitionedGraphs(dfs, G, sourceVertex, connectedGraphId));
        }

    }

    private static int findTotalPartitionedGraphs(DepthFirstSearch dfs, GraphWithAdjacencyList<Vertex> G, Vertex sourceVertex, int connectedGraphId) {
        dfs.startDepthFirstSearch(G, sourceVertex, connectedGraphId);

        for (Vertex vertex : G.getAllVerticies()) {
            if(!vertex.isVisited()) {
                return findTotalPartitionedGraphs(dfs, G, vertex, ++connectedGraphId);
            }
        }
        return connectedGraphId;
    }
}
