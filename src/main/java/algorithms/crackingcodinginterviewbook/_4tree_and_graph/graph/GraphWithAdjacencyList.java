package algorithms.crackingcodinginterviewbook._4tree_and_graph.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

// Taken from http://www.keithschwarz.com/interesting/code/edmonds-matching/UndirectedGraph.java.html
// converted UnDirectedGraph from above example to UnDirected+Directed Graph

// This is wrong.
// It is not an Adjacency List. It is still Edge List representation.

@Deprecated
public final class GraphWithAdjacencyList<T> implements Iterable<T> {
    private boolean isItUndirected;

    /* A map from vertices in the graph to sets of outgoing edges.  Each
     * set of edges is represented by a map from edges to doubles.
     */
    private final Map<T, Set<T>> mGraph = new HashMap<T, Set<T>>(); // Adjacency List(Set)

    private List<Cycle> cycles = new ArrayList<>();

    public GraphWithAdjacencyList(boolean isItUndirected) {
        this.isItUndirected = isItUndirected;
    }
    /**
     * Adds a new vertex to the graph.  If the vertex already exists, this
     * function is a no-op.
     *
     * @param vertex The vertex to add.
     * @return Whether or not the vertex was added.
     */
    public boolean addVertex(T vertex) {
        /* If the vertex already exists, don't do anything. */
        if (mGraph.containsKey(vertex))
            return false;

        /* Otherwise, add the vertex with an empty set of outgoing edges. */
        mGraph.put(vertex, new HashSet<T>());
        return true;
    }

    /**
     * Given a vertex, returns whether that vertex exists in the graph.
     *
     * @param vertex vertex in question.
     * @return Whether that vertex eixsts in the graph.
     */
    public boolean vertexExists(T vertex) {
        return mGraph.containsKey(vertex);
    }

    /**
     * Given two vertices, adds an arc of that length between those vertices.  If
     * either endpoint does not exist in the graph, throws a
     * NoSuchElementException.
     *
     * @param from The first vertex.
     * @param to The second vertex.
     * @throws NoSuchElementException If either the start or destination vertices
     *                                do not exist.
     */
    public void addEdge(T from, T to) {
        /* Confirm both endpoints exist. */
        if (!mGraph.containsKey(from) || !mGraph.containsKey(to))
            throw new NoSuchElementException("Both vertices must be in the graph.");

        /* Add directed (edge to one direction) or undirected edge (edge in both directions). */
        mGraph.get(from).add(to);
        if(isItUndirected) {
            if(!from.equals(to)) { // taking care of self-loops
                addEdge(to, from);
            }
        }

    }
    /**
     * Removes the edge between the indicated endpoints from the graph.  If the
     * edge does not exist, this operation is a no-op.  If either endpoint does
     * not exist, this throws a NoSuchElementException.
     *
     * @param from The start vertex.
     * @param to The destination vertex.
     * @throws NoSuchElementException If either vertex is not in the graph.
     */
    public void removeEdge(T from, T to) {
        /* Confirm both endpoints exist. */
        if (!mGraph.containsKey(from) || !mGraph.containsKey(to))
            throw new NoSuchElementException("Both vertices must be in the graph.");

        /* Remove the edges from both adjacency lists. */
        mGraph.get(from).remove(to);
        if(isItUndirected) {
            if(!from.equals(to)) { // taking care of self-loops
                mGraph.get(to).remove(from);
            }
        }
    }

    /**
     * Given to endpoints, returns whether an edge exists between them.  If
     * either endpoint does not exist in the graph, throws a
     * NoSuchElementException.
     *
     * @param from The first endpoint.
     * @param to The second endpoint.
     * @return Whether an edge exists between the endpoints.
     * @throws NoSuchElementException If the endpoints are not vertices in the
     *                                graph.
     */
    public boolean edgeExists(T from, T to) {
        /* Confirm both endpoints exist. */
        if (!mGraph.containsKey(from) || !mGraph.containsKey(to))
            throw new NoSuchElementException("Both vertices must be in the graph.");

        /* Graph is symmetric, so we can just check either endpoint. */
        return mGraph.get(from).contains(to);
    }

    /**
     * Given a vertex in the graph, returns an immutable view of the edges
     * leaving that vertex.
     *
     * @param vertex The vertex whose edges should be queried.
     * @return An immutable view of the edges leaving that vertex.
     * @throws NoSuchElementException If the vertex does not exist.
     */
    public Set<T> edgesFrom(T vertex) {
        /* Check that the vertex exists. */
        Set<T> arcs = mGraph.get(vertex);
        if (arcs == null)
            throw new NoSuchElementException("Source vertex does not exist.");

        return Collections.unmodifiableSet(arcs);
    }

    /**
     * Returns whether a given vertex is contained in the graph.
     *
     * @param vertex vertex to test for inclusion.
     * @return Whether that vertex is contained in the graph.
     */
    public boolean containsVertex(T vertex) {
        return mGraph.containsKey(vertex);
    }

    /**
     * Returns an iterator that can traverse the vertices in the graph.
     *
     * @return An iterator that traverses the vertices in the graph.
     */
    public Iterator<T> iterator() {
        return mGraph.keySet().iterator();
    }
    public Set<T> getAllVerticies() {
        return mGraph.keySet();
    }
    /**
     * Returns the number of vertices in the graph.
     *
     * @return The number of vertices in the graph.
     */
    public int size() {
        return mGraph.size();
    }

    /**
     * Returns whether the graph is empty.
     *
     * @return Whether the graph is empty.
     */
    public boolean isEmpty() {
        return mGraph.isEmpty();
    }

    public void addCycle(Cycle cycle) {
        if(cycle != null && cycle.size() > 0) {
            if(!containsCycle(cycle.getVertices())) {
                cycles.add(cycle);
            }
        }
    }
    public boolean containsCycle(List<Vertex> vertices) {
        if(vertices != null && vertices.size() > 0) {
            for (Cycle cycle : cycles) {
                if(cycle.contains(vertices)) return true;
            }
        }
        return false;
    }


    public List<T> findVerticesHavingSelfLoops() {
        List<T> vertices = new ArrayList<>();

        Iterator<T> verticesIterator = iterator();
        while(verticesIterator.hasNext()) {
            T next = verticesIterator.next();
            if(next.equals(mGraph.get(next)) ) {
                vertices.add(next);
            }
        }

        return vertices;
    }

    /**
     * Returns a human-readable representation of the graph.
     *
     * @return A human-readable representation of the graph.
     */
    public String toString() {
        return mGraph.toString();
    }

    // This is a totally connected one graph
    public static GraphWithAdjacencyList createGraph(boolean isUnDirectedGraph) {
        GraphWithAdjacencyList<Vertex> G = new GraphWithAdjacencyList(isUnDirectedGraph);
        Vertex vertex0 = new Vertex(0);
        Vertex vertex1 = new Vertex(1);
        Vertex vertex2 = new Vertex(2);
        Vertex vertex3 = new Vertex(3);
        Vertex vertex4 = new Vertex(4);
        Vertex vertex5 = new Vertex(5);

        G.addVertex(vertex0);
        G.addVertex(vertex1);
        G.addVertex(vertex2);
        G.addVertex(vertex3);
        G.addVertex(vertex4);
        G.addVertex(vertex5);

        G.addEdge(vertex0, vertex1);
        G.addEdge(vertex1, vertex2);
        G.addEdge(vertex1, vertex3);
        G.addEdge(vertex2, vertex3);
        G.addEdge(vertex3, vertex4);
        G.addEdge(vertex2, vertex4);
        G.addEdge(vertex4, vertex5);

        //System.out.println(G.toString());

        return G;
    }

    // There are 3 connected graphs in this one grapsh
    public static GraphWithAdjacencyList createGraphOf3ConnectedGraphs(boolean isUnDirectedGraph) {
        // first graph
        GraphWithAdjacencyList<Vertex> G = createGraph(isUnDirectedGraph);

        // second graph
        Vertex vertex6 = new Vertex(6);
        Vertex vertex7 = new Vertex(7);
        Vertex vertex8 = new Vertex(8);
        G.addVertex(vertex6);
        G.addVertex(vertex7);
        G.addVertex(vertex8);

        G.addEdge(vertex6, vertex7);
        G.addEdge(vertex7, vertex8);
        G.addEdge(vertex8, vertex6);

        // third graph
        Vertex vertex9 = new Vertex(9);
        Vertex vertex10 = new Vertex(10);
        G.addVertex(vertex9);
        G.addVertex(vertex10);

        G.addEdge(vertex9, vertex10);

        return G;
    }

    public void printCycles() {
        for (Cycle cycle : cycles) {
            System.out.println(cycle.toString());
        }

    }
}