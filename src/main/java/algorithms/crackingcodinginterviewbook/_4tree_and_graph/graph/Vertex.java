package algorithms.crackingcodinginterviewbook._4tree_and_graph.graph;

/**
 * @author Tushar Chokshi @ 11/13/15.
 */
public class Vertex {
    private int id;
    private boolean visited;
    private Integer connectedComponentId;
    private Vertex comingFromVertex;

    public Vertex(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Integer getConnectedComponentId() {
        return connectedComponentId;
    }

    public void setConnectedComponentId(Integer connectedComponentId) {
        this.connectedComponentId = connectedComponentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;

        return id == vertex.id;

    }

    public Vertex getComingFromVertex() {
        return comingFromVertex;
    }

    public void setComingFromVertex(Vertex comingFromVertex) {
        this.comingFromVertex = comingFromVertex;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return id +"";
    }
}
