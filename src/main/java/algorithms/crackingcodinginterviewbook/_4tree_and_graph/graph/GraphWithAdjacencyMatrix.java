package algorithms.crackingcodinginterviewbook._4tree_and_graph.graph;

import java.util.ArrayList;
import java.util.List;

public class GraphWithAdjacencyMatrix {
    private final List<Vertex> vertices;
    private int[][] adjacencyMatrix;

    public GraphWithAdjacencyMatrix(List<Vertex> vertices)
    {
        this.vertices = vertices;
        adjacencyMatrix = new int[vertices.size()][vertices.size()];
    }

    public void makeEdge(Vertex from, Vertex to)
    {
        try
        {
            adjacencyMatrix[from.getId()][to.getId()] = 1;
        }
        catch (ArrayIndexOutOfBoundsException index)
        {
            System.out.println("The vertices does not exists");
        }
    }

    public int getEdge(Vertex from, Vertex to)
    {
        try
        {
            return adjacencyMatrix[from.getId()][to.getId()];
        }
        catch (ArrayIndexOutOfBoundsException index)
        {
            System.out.println("The vertices does not exists");
        }
        return -1;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }
    public static GraphWithAdjacencyMatrix createGraph() {
        Vertex tushar = new Vertex(0);
        Vertex miral = new Vertex(1);
        Vertex srikant = new Vertex(2);
        Vertex puja = new Vertex(3);
        Vertex madhu = new Vertex(4);
        Vertex anoop = new Vertex(5);
        Vertex rakesh = new Vertex(6);
        Vertex ronak = new Vertex(7);
        Vertex yogita = new Vertex(8);
        Vertex notConnected1 = new Vertex(9);
        Vertex notConnected2 = new Vertex(10);

        List<Vertex> vertices = new ArrayList<Vertex>() {{add(tushar); add(miral); add(srikant); add(puja); add(madhu); add(anoop); add(rakesh); add(ronak); add(yogita); add(notConnected1); add(notConnected2);}};

        GraphWithAdjacencyMatrix graph = new GraphWithAdjacencyMatrix(vertices);
        graph.makeEdge(tushar, miral);
        graph.makeEdge(tushar, srikant);
        graph.makeEdge(tushar, anoop);
        graph.makeEdge(tushar, madhu);
        graph.makeEdge(tushar, rakesh);
        graph.makeEdge(miral, srikant);
        graph.makeEdge(miral, tushar);
        graph.makeEdge(miral, puja);
        graph.makeEdge(miral, yogita);
        graph.makeEdge(srikant, tushar);
        graph.makeEdge(srikant, miral);
        graph.makeEdge(srikant, ronak);
        graph.makeEdge(yogita, puja);
        graph.makeEdge(notConnected1, notConnected2);

        return graph;
    }

    public void prettyPrintMatrix() {
        System.out.print("   ");
        for (Vertex vertex : vertices) {
            System.out.print(vertex.getId()+"   ");
        }
        System.out.println();
        System.out.print("   ");
        for (Vertex vertex : vertices) {
            System.out.print("---|");
        }
        System.out.println();
        for (int from = 0; from < adjacencyMatrix.length; from++) {
            for (Vertex vertex : vertices) {
                if(vertex.getId() == from) {
                    System.out.print(from+"|"+" ");
                    break;
                }
            }
            for (int to = 0; to < adjacencyMatrix[from].length; to++) {
                System.out.print(adjacencyMatrix[from][to]+"  "+"|");
            }
            System.out.println();
            System.out.println();
        }

    }


    public static void main(String[] args) {
        GraphWithAdjacencyMatrix graph = GraphWithAdjacencyMatrix.createGraph();
        graph.prettyPrintMatrix();
    }

    public int[] getEdges(Integer peeked) {
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if(i == peeked) {
                return adjacencyMatrix[i];
            }
        }
        return null;
    }

    public int[][] getMatrix() {
        return adjacencyMatrix;
    }
}
