package algorithms.crackingcodinginterviewbook._4tree_and_graph.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Tushar Chokshi @ 11/12/15.
 */

// Youtube Video - https://www.youtube.com/watch?v=zLZhSSXAwxI
// or downloaded youtube video - "5 - Graph Traversals -- Depth first search (DFS) & Breadth First Search (BFS) Algorithms..mp4"

/*
   DFS
   Uses STACK and uses add, peek and pop actions. poll=peek+remove the element from stack.

   Chooses a random vertex from a graph and makes it a source vertex.
   It visits that source vertex and puts it in a stack.

   (Recursive call)
   If stack is not empty,
    It Peeks (not Polls) a vertex from a stack. It becomes a source vertex.
    It Finds all adjacent vertices of the source vertex and visits ONE of those which is not yet visited and put that ONE in a stack.
    If it finds that all adjacent vertices are visited, then it Pops a source vertex from a stack.


    There is a different way also using BackTracking
    Instead of using STACK, you can use back tracking mechanism by using comingFromVertex property of Vertex. Don't know how to do it at present ?????
 */

public class DepthFirstSearch {
    public static void main(String[] args) {
        {
            GraphWithAdjacencyList<Vertex> G = GraphWithAdjacencyList.createGraph(true);
            System.out.println("Created Graph: " + G.toString());
            if (G.size() > 0) {
                Vertex sourceVertex = G.iterator().next(); // select any random vertex in a graph

                System.out.println("DFS One Way");
                startDepthFirstSearch(G, sourceVertex, 1); // Better Way because it doesn't use a linkedlist for visitedVertices

            }
        }

        System.out.println();

        {
            GraphWithAdjacencyList<Vertex> G = GraphWithAdjacencyList.createGraph(true);
            System.out.println("Created Graph: " + G.toString());

            if (G.size() > 0) {
                Vertex sourceVertex = G.iterator().next(); // select any random vertex in a graph

                System.out.println("DFS Another Way");
                startDepthFirstSearchAnotherWay(G, sourceVertex, 1);

            }
        }
    }

    private static void startDepthFirstSearchAnotherWay(GraphWithAdjacencyList<Vertex> G, Vertex sourceVertex, int connectedGraphId) {
        Stack<Vertex> stack = new Stack<>();
        List<Vertex> visitedVertices = new ArrayList<>();

        stack.add(sourceVertex);
        visitVertex(sourceVertex, visitedVertices, sourceVertex, connectedGraphId);

        depthFirstSearchAnotherWay(G, stack, visitedVertices, connectedGraphId);

        System.out.println("Visited Vertices: " + visitedVertices.toString());
        System.out.println("Cycles Found: ");
        G.printCycles();
        System.out.println("Stack:" + stack);
    }
    private static void depthFirstSearchAnotherWay(GraphWithAdjacencyList<Vertex> G, Stack<Vertex> stack,  List<Vertex> visitedVertices, int connectedComponentId) {
        if (stack.isEmpty()) return; // exit condition

        Vertex sourceVertex = stack.peek(); // don't do stack.pop() here
        List<Vertex> adjacentVertices = new ArrayList<>(G.edgesFrom(sourceVertex));

        boolean allAdjacentVerticesVisited = true;
        for (Vertex adjacentVertex : adjacentVertices) {
            if (!adjacentVertex.isVisited()) {
                allAdjacentVerticesVisited = false;
                stack.add(adjacentVertex);
                visitVertex(adjacentVertex, visitedVertices, sourceVertex, connectedComponentId);
                break;
            } else { // if not visited yet
                // Conditions to find circuits https://www.youtube.com/watch?v=n_t0a_8H8VY
                addCyclesInGraph(G, stack, sourceVertex, adjacentVertex);
            }
        }
        if (allAdjacentVerticesVisited) stack.pop(); // pop a vertex from a stack, if all its adjacent vertices are visited already

        depthFirstSearchAnotherWay(G, stack, visitedVertices, connectedComponentId);

    }

    // Conditions to find circuits https://www.youtube.com/watch?v=n_t0a_8H8VY

    // It uses BackTracking methodology. Every being visited Vertex knows what is the source vertex from where it has been visited.
    // e.g. in a graph {0=[1], 1=[0, 2, 3], 2=[1, 3, 4], 3=[1, 2, 4], 4=[2, 3, 5], 5=[4]}
    // If you start traversing this graph from Vertex 0, then Vertex 1 knows that its comingFromVertex=0
    // Cycle is found in a graph during its DFS traversal, when you find adjacentVertex
    //      which is visited
    //      and
    //      its comingFromVertex != adjacentVertex
    //      and
    //      adjacentVertex is still inside stack

    // e.g. You went from 0 to 1. So, 1's comingFromVertex=0. So, when you find 1's adjacent vertices (0,2,3), in which 0 is already visited, but 0 is its comingFromVertex, so an edge between 1 and 0 can't create a cycle
    //      But when you go from 1 to 2 and 2 to 3 and then 3 to 1, it will create a cycle of (1,2,3) because 1 is not a comingFromVertex of 3.
    private static void addCyclesInGraph(GraphWithAdjacencyList<Vertex> G, Stack<Vertex> stack, Vertex comingFromVertex, Vertex adjacentVertex) {
        if(adjacentVertex.isVisited() && adjacentVertex != comingFromVertex.getComingFromVertex() && stack.contains(adjacentVertex)) {
            List<Vertex> cycleList = stack.subList(stack.indexOf(adjacentVertex), stack.indexOf(comingFromVertex) + 1);
            if(!G.containsCycle(cycleList)) {
                G.addCycle(new Cycle(cycleList));
            }
            //System.out.println("created cycle while trying to go from vertex: " + comingFromVertex + " to "+adjacentVertex+ " cycle path: "+stack.subList(stack.indexOf(adjacentVertex), stack.indexOf(comingFromVertex)+1));
        }
    }

    private static void visitVertex(Vertex vertex, List<Vertex> visitedVertices,  Vertex comingFromVertex, int connectedComponentId) {
        visitedVertices.add(vertex);
        vertex.setVisited(true);
        vertex.setComingFromVertex(comingFromVertex); // for BackTracking purpose to find out cycles in the graph.
        vertex.setConnectedComponentId(connectedComponentId);
    }





    public static void startDepthFirstSearch(GraphWithAdjacencyList<Vertex> G, Vertex rootVertex, int connectedGraphId) {
        Stack<Vertex> stack = new Stack<>();

        stack.add(rootVertex);
        Vertex comingFromVertex = null;
        visitVertex(rootVertex, comingFromVertex, connectedGraphId);

        depthFirstSearch(G, stack, connectedGraphId);

        System.out.println("Cycles Found: ");
        G.printCycles();
        System.out.println("Stack:" + stack);
    }
    private static void depthFirstSearch(GraphWithAdjacencyList<Vertex> G, Stack<Vertex> stack, int connectedComponentId) {
        if (stack.isEmpty()) return; // exit condition

        Vertex sourceVertex = stack.peek(); // don't do stack.pop() here
        List<Vertex> adjacentVertices = new ArrayList<>(G.edgesFrom(sourceVertex));

        boolean allAdjacentVerticesVisited = true;
        for (Vertex adjacentVertex : adjacentVertices) {
            if (!adjacentVertex.isVisited()) {
                allAdjacentVerticesVisited = false;
                stack.add(adjacentVertex);
                visitVertex(adjacentVertex, sourceVertex, connectedComponentId);
                break;
            } else { // if already visited
                addCyclesInGraph(G, stack, sourceVertex, adjacentVertex);
            }



        }
        if (allAdjacentVerticesVisited) stack.pop(); // pop a vertex from a stack, if all its adjacent vertices are visited already

        depthFirstSearch(G, stack, connectedComponentId);

    }


    private static void visitVertex(Vertex vertex, Vertex comingFromVertex, int connectedComponentId) {
        vertex.setVisited(true);
        vertex.setComingFromVertex(comingFromVertex);  // for BackTracking purpose to find out cycles in the graph.
        vertex.setConnectedComponentId(connectedComponentId);
        System.out.println("Visited Vertex id: " + vertex.getId() +" ,connected component id: "+connectedComponentId);
    }
}
