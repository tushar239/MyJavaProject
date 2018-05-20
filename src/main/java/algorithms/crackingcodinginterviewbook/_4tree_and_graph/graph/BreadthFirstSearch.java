package algorithms.crackingcodinginterviewbook._4tree_and_graph.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Tushar Chokshi @ 11/14/15.
 */

// Youtube Video - https://www.youtube.com/watch?v=zLZhSSXAwxI
// or downloaded youtube video - "5 - Graph Traversals -- Depth first search (DFS) & Breadth First Search (BFS) Algorithms..mp4"

/*
   BFS
   Uses QUEUE and uses add and poll actions (not peek).

   Chooses a random vertex from a graph and makes it a source vertex.
   It visits that source vertex and puts it in a queue.

   (Recursive call)
   If queue is not empty,
    It Polls a vertex from a queue. It becomes a source vertex.
    It Finds all adjacent vertices of the source vertex and visits ALL of those which are not yet visited and puts ALL of them in a queue.
*/
public class BreadthFirstSearch {

    public static void main(String[] args) {
        System.out.println("One approach by using a linkedlist of Visited Vertices");
        startBreadthFirstSearch();

        System.out.println();

        System.out.println("Another approach by using a marking a vertex as visited (BETTER Approach)");
        startBreadthFirstSearchAnotherApproach(1); // Better approach - use this
    }

    private static void startBreadthFirstSearch() {
       GraphWithEdgeList G = GraphWithEdgeList.createGraph();
        System.out.println("Created Graph: " + G.toString());

        if (G.size() > 0) {
            List<Vertex> visitedVertices = new ArrayList<>();
            Queue<Vertex> queue = new LinkedBlockingQueue<>();

            Vertex someVertex = G.iterateVertices().next(); // select any random vertex in a graph
            queue.add(someVertex);
            visitVertex(visitedVertices, someVertex);
            breadthFirstSearch(G, visitedVertices, queue);

            System.out.println("Visited Vertices:" + visitedVertices);
            System.out.println("Queue:" + queue);
        }
    }

    private static void breadthFirstSearch(GraphWithEdgeList G, List<Vertex> visitedVertices, Queue<Vertex> queue) {
        if (queue.isEmpty()) return; // exit condition

        List<Vertex> adjacentVertices = new ArrayList<>(G.edgesFrom(queue.poll()));
        for (Vertex adjacentVertex : adjacentVertices) {
            if(visitedVertices.contains(adjacentVertex)) continue; // using contains on linkedlist will force to iterate over the linkedlist that adds up some traversal time, instead we can use another approach of marking a vertex as visited
            queue.add(adjacentVertex);
            visitVertex(visitedVertices, adjacentVertex);
        }

        breadthFirstSearch(G, visitedVertices, queue);
    }

    private static void visitVertex(List<Vertex> visitedVertices, Vertex someVertex) {
        visitedVertices.add(someVertex);
    }



    // Another Approach (Better Approach)
    public static void startBreadthFirstSearchAnotherApproach(int connectedComponentId) {
        GraphWithEdgeList G = GraphWithEdgeList.createGraph();
        System.out.println("Created Graph: " + G.toString());

        if (G.size() > 0) {

            Queue<Vertex> queue = new LinkedBlockingQueue<>();

            Vertex someVertex = G.iterateVertices().next(); // select any random vertex in a graph
            queue.add(someVertex);
            visitVertex(someVertex, connectedComponentId);
            breadthFirstSearchAnotherApproach(G, queue, connectedComponentId);

            System.out.println("Queue:" + queue);
        }
    }

    private static void breadthFirstSearchAnotherApproach(GraphWithEdgeList G, Queue<Vertex> queue, int connectedComponentId) {
        if (queue.isEmpty()) return; // exit condition

        Vertex sourceVertex = queue.poll();
        List<Vertex> adjacentVertices = new ArrayList<>(G.edgesFrom(sourceVertex));
        for (Vertex adjacentVertex : adjacentVertices) {
            if(adjacentVertex.isVisited()) continue;
            queue.add(adjacentVertex);
            visitVertex(adjacentVertex, connectedComponentId);
        }

        breadthFirstSearchAnotherApproach(G, queue, connectedComponentId);
    }

    private static void visitVertex(Vertex vertex, int connectedComponentId) {
        vertex.setVisited(true);
        vertex.setConnectedComponentId(connectedComponentId);
        System.out.println("Visited Vertex:" + vertex.getId());
    }

}
