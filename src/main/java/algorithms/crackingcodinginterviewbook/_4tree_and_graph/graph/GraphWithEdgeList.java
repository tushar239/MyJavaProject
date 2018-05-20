package algorithms.crackingcodinginterviewbook._4tree_and_graph.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * @author Tushar Chokshi @ 11/15/15.
 */

// Taken from http://www.vogella.com/tutorials/JavaAlgorithmsDijkstra/article.html#shortestpath_problem
    // Here edges are represented as Edge List which has the worse time complexity.
    // Use Adjacency List for Sparse Graph and Adjacency Matrix for Dense Graph.

public class GraphWithEdgeList {
    private Set<Vertex> vertices = new HashSet<>();
    private Set<Edge> edges = new HashSet<>();

    public Iterator<Vertex> iterateVertices() {
        return vertices.iterator();
    }

    public Iterator<Edge> iterateEdges() {
        return edges.iterator();
    }


    public boolean addVertex(Vertex vertex) {
        /* If the vertex already exists, don't do anything. */
        if (vertices.contains(vertex))
            return false;
        vertices.add(vertex);
        return true;
    }

    public boolean vertexExists(Vertex vertex) {
        return vertices.contains(vertex);
    }

    public void addUnidirectionalEdge(Vertex from, Vertex to) {
        /* Confirm both endpoints exist. */
        if (!vertices.contains(from) || !vertices.contains(to))
            throw new NoSuchElementException("Both vertices must be in the graph.");

        edges.add(new Edge(from, to));
    }

    public void addBiDirectionalEdge(Vertex from, Vertex to) {
        addUnidirectionalEdge(from, to);

        if (!from.equals(to)) { // taking care of self-loops
            addUnidirectionalEdge(to, from);
        }
    }

    public List<Vertex> findVerticesHavingSelfLoops() {
        List<Vertex> vertices = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getFromVertex().equals(edge.getToVertex())) {
                vertices.add(edge.getFromVertex());
            }
        }
        return vertices;
    }

    public void removeEdge(Vertex from, Vertex to, boolean isBothSided) {
        /* Confirm both endpoints exist. */
        if (!vertices.contains(from) || !vertices.contains(to))
            throw new NoSuchElementException("Both vertices must be in the graph.");

        List<Edge> edgesToBeRemoved = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getFromVertex().equals(from) && edge.getToVertex().equals(to)) {
                edgesToBeRemoved.add(edge);
            }
            if (isBothSided) {
                if (edge.getFromVertex().equals(to) && edge.getToVertex().equals(from)) {
                    edgesToBeRemoved.add(edge);
                }
            }
        }

        for (Edge edgeToBeRemoved : edgesToBeRemoved) {
            edges.remove(edgeToBeRemoved);
        }

    }

    public boolean edgeExists(Vertex from, Vertex to) {
        /* Confirm both endpoints exist. */
        if (!vertices.contains(from) || !vertices.contains(to))
            throw new NoSuchElementException("Both vertices must be in the graph.");

        for (Edge edge : edges) {
            if (edge.getFromVertex().equals(from) && edge.getToVertex().equals(to)) {
                return true;
            }
        }
        return false;
    }

    public Set<Vertex> edgesFrom(Vertex from) {
        /* Check that the vertex exists. */

        Set<Vertex> vertices = new HashSet<>();
        for (Edge edge : edges) {
            if (edge.getFromVertex().equals(from)) {
                vertices.add(edge.getToVertex());
            }
        }
        return Collections.unmodifiableSet(vertices);
    }

    public boolean containsVertex(Vertex vertex) {
        return vertices.contains(vertex);
    }

    public Set<Vertex> getAllVerticies() {
        return vertices;
    }

    public int size() {
        return vertices.size();
    }

    public boolean isEmpty() {
        return vertices.isEmpty();
    }

    public String toString() {
        return edges.toString();
    }

    // This is a totally connected one graph
    public static GraphWithEdgeList createGraph() {
        GraphWithEdgeList G = new GraphWithEdgeList();
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

        G.addBiDirectionalEdge(vertex0, vertex1);
        G.addBiDirectionalEdge(vertex1, vertex2);
        G.addBiDirectionalEdge(vertex1, vertex3);
        G.addBiDirectionalEdge(vertex2, vertex3);
        G.addBiDirectionalEdge(vertex3, vertex4);
        G.addBiDirectionalEdge(vertex2, vertex4);
        G.addBiDirectionalEdge(vertex4, vertex5);

        //System.out.println(G.toString());

        return G;
    }
}
