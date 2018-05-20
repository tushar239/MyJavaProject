package algorithms.crackingcodinginterviewbook._4tree_and_graph.graph;

/**
 * @author Tushar Chokshi @ 11/15/15.
 */
public class Edge {
    //private String id;
    private Vertex fromVertex;
    private Vertex toVertex;
    private Integer weight;

    public Edge(Vertex fromVertex, Vertex toVertex) {
        this.fromVertex = fromVertex;
        this.toVertex = toVertex;
    }

    public Edge(Vertex fromVertex, Vertex toVertex, Integer weight) {
        this(fromVertex, toVertex);
        this.weight = weight;
    }

    public Vertex getFromVertex() {
        return fromVertex;
    }

    public void setFromVertex(Vertex fromVertex) {
        this.fromVertex = fromVertex;
    }

    public Vertex getToVertex() {
        return toVertex;
    }

    public void setToVertex(Vertex toVertex) {
        this.toVertex = toVertex;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public boolean isWeightedEdge() {
        return weight != null;
    }

    @Override
    public String toString() {
        return fromVertex + "->" + toVertex;
    }
}
