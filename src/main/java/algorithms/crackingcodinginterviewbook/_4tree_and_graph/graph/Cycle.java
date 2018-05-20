package algorithms.crackingcodinginterviewbook._4tree_and_graph.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tushar Chokshi @ 11/15/15.
 */
public class Cycle {
    private List<Vertex> vertices = new ArrayList<>();

    public Cycle(List<Vertex> vertices) {
        for (Vertex vertex : vertices) {
            this.vertices.add(vertex);
        }
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    public boolean equals(Cycle cycle) {
        if(cycle == null) return false;

        for (Vertex vertex : cycle.getVertices()) {
            if(!vertices.contains(vertex)) return false;
        }
        return true;
    }

    public boolean contains(List<Vertex> vertices) {
        if(vertices == null || vertices.size() == 0) return false;

        for (Vertex vertex : vertices) {
            if(!this.vertices.contains(vertex)) {
                return false;
            }
        }
        return true;
    }

    public int size() {
        if(vertices == null) return 0;
        return vertices.size();
    }

    @Override
    public String toString() {
        return vertices.toString();
    }
}
