package algorithms.crackingcodinginterviewbook._4tree_and_graph.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/*
 Grokking Algorithms book (Chapter 6)
 ------------------------------------

 BFS is enough to know the shortest path to X. Dijkstra's algorithm helps to know the fastest path to X.

 DAG means Directed Acyclic Graph

 REMEMBER:
 Dijkstra's algorithm works only for POSITIVELY WEIGHTED DIRECTED ACYCLIC graph.

 Undirected graph is same as two way directed(cycle) graph

 A----B    (Undirected Graph)
 is same as
 A ----> B   (Directed Graph both ways)
   <----
 This is called Cyclic Graph.

 To understand the reason read Chapter 7's 'Negative-weight edges' section of Grokking Algorithm book.
 As you don't process already processed vertex (to avoid cycle), you may end up with wrong cost for fastest path.

 To work with NEGATIVELY WEIGHTED GRAPH, you need to use BELLMAN-FORD's algorithm.
 */
public class _2DijkstraAlgorithmForPositivelyWeightedGraphGrokkingAlgorithmBook {
    private final static int INFINITE_COST = Integer.MAX_VALUE;

    /*
        1
    A -----> B -------
    |        |       |
    |        |4      |2
    |        |       |
    |        v       v
    |-------> C ---> D
        3        1

    Created a map
     [
        {
            A -> [
                    {B->1},
                    {C->3}
                 ]
        },
        {
            B -> [
                    {C->4},
                    {D->2}
                 ]
        },
        {
            C -> [
                    {D->1}
                 ]
        },
        {
            D -> [ ]
        }
     ]

    Problem: Find Fastest Path from A to D

        - Initialize vertex-cost-parent table for all vertices in the graph

        Vertex      Cost/weight/distance        Parent
        A           0                           null   --- initialize starting vertex's cost to 0
        B           INFINITE                    null   --- INFINITE value in java can be set as Integer.MAX_VALUE
        C           INFINITE                    null
        D           INFINITE                    null

        call findFastestPath(graph, vertex-cost-parent table, A) with startVertex=A

        void findFastestPath(graph, vertex-cost-parent table, startVertex)

            if(startVertex != null) {
                - Find friends of startVertex  (e.g. A's friends are C,B)

                - Inspect vertex-cost-parent table

                  If friend's parent is empty (friend is not processed yet)
                    update its parent and cost.
                    cost = parent's cost+friend's weight (important)
                    e.g. B's parent is A and its cost is A's cost+B's weight=0+1=1
                         C's parent is A and its cost is A's cost+C's weight=0+3=3

                    Vertex      Cost        Parent
                    A           0           null
                    B           1           A
                    C           3           A
                    D           INFINITE    null
                - Sort unprocessed friends by weight (B,C) ----------- VERY IMPORTANT. It processes a friend with lowest weighted edge first. GREEDY ALGORITHM concept.
                - Process unprocessed friends (B,C) one by one by using recursion
        }

        Final vertex-cost-parent table

            Vertex      Cost        Parent
            A           0           null
            B           1           A
            C           3           A
            D           3           B

       So, cost to reach from A to D is 3 (D's cost)
       fastest path is D<-B<-A
     */

    private static Map<Vertex, Set<VertexWeight>> initializeWeightedGraph() {
        Map<Vertex, Set<VertexWeight>> weightedGraph = new HashMap<>();

        Vertex vertexA = new Vertex("A");
        Vertex vertexB = new Vertex("B");
        Vertex vertexC = new Vertex("C");
        Vertex vertexD = new Vertex("D");

        weightedGraph.put(vertexA, new HashSet<VertexWeight>() {{
            add(new VertexWeight(vertexB, 1));
            add(new VertexWeight(vertexC, 3));
        }});

        weightedGraph.put(vertexB, new HashSet<VertexWeight>() {{
            add(new VertexWeight(vertexC, 4));
            add(new VertexWeight(vertexD, 2));
        }});

        weightedGraph.put(vertexC, new HashSet<VertexWeight>() {{
            add(new VertexWeight(vertexD, 1));
        }});

        weightedGraph.put(vertexD, new HashSet<>());

        return weightedGraph;
    }

    static class VertexWeight implements Comparable<VertexWeight> {
        private Vertex vertex;
        private Integer weight;

        public VertexWeight(Vertex vertex, Integer weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        public Vertex getVertex() {
            return vertex;
        }

        public void setVertex(Vertex vertex) {
            this.vertex = vertex;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            VertexWeight that = (VertexWeight) o;

            if (vertex != null ? !vertex.equals(that.vertex) : that.vertex != null) return false;
            return weight != null ? weight.equals(that.weight) : that.weight == null;
        }

        @Override
        public int hashCode() {
            int result = vertex != null ? vertex.hashCode() : 0;
            result = 31 * result + (weight != null ? weight.hashCode() : 0);
            return result;
        }

        @Override
        public int compareTo(VertexWeight vertexWeight) {
            if (this.weight > vertexWeight.weight) return 1;
            if (this.weight < vertexWeight.weight) return -1;
            return 0;
        }
    }

    public static void main(String[] args) {
        Map<Vertex, Set<VertexWeight>> graph = initializeWeightedGraph();

        Map<Vertex, VertexCostParent> vertexCostParentTable = new HashMap<>();
        // initialize a vertex-vs-cost table with initial value of cost as infinity
        for (Vertex vertex : graph.keySet()) {
            vertexCostParentTable.put(vertex, getInitialVertexCostParent(vertex));
        }

        /*
            vertex-cost-parent table

            Vertex      Cost        Parent
            A           0           null
            B           INFINITE    null
            C           INFINITE    null
            D           INFINITE    null
         */
        Vertex startVertex = new Vertex("A");
        vertexCostParentTable.get(startVertex).setCost(0);

        findFastestPathFromAToD(graph, vertexCostParentTable, startVertex);

        System.out.println(vertexCostParentTable);
        /*
        {
            Vertex{name='A'}=VertexCostParent{vertex=Vertex{name='A'}, cost=0, parent=null},
            Vertex{name='B'}=VertexCostParent{vertex=Vertex{name='B'}, cost=1, parent=Vertex{name='A'}},
            Vertex{name='C'}=VertexCostParent{vertex=Vertex{name='C'}, cost=3, parent=Vertex{name='A'}},
            Vertex{name='D'}=VertexCostParent{vertex=Vertex{name='D'}, cost=3, parent=Vertex{name='B'}}}
         */

        Vertex vertexD = new Vertex("D");
        VertexCostParent costAndParentOfD = vertexCostParentTable.get(vertexD);
        Integer costToReachFromAToD = costAndParentOfD.getCost();
        System.out.println("Cost to reach from A to D: " + costToReachFromAToD);

        System.out.print("Fastest path from A to D: ");

        System.out.print(vertexD.getName() + "<-");
        Vertex parent = costAndParentOfD.getParent();
        while (parent != null) {
            System.out.print(parent.getName() + "<-");
            parent = vertexCostParentTable.get(parent).getParent();
        }

        System.out.println();

    }

    protected static VertexCostParent getInitialVertexCostParent(Vertex vertex) {
        VertexCostParent vertexCostParent = new VertexCostParent(vertex);
        vertexCostParent.setCost(INFINITE_COST);
        vertexCostParent.setParent(null);
        return vertexCostParent;
    }

    private static void findFastestPathFromAToD(Map<Vertex, Set<VertexWeight>> graph, Map<Vertex, VertexCostParent> vertexCostParentTable, Vertex startVertex) {
        if (graph == null || (vertexCostParentTable == null || vertexCostParentTable.isEmpty()) || startVertex == null)
            return;

        Set<VertexWeight> friendsWithWeights = graph.get(startVertex);

        // process unprocessed friends one by one (set their cost and parent vertex in vertex-cost-parent table)
        Set<VertexWeight> unProcessedFriends = new HashSet<>();
        for (VertexWeight vertexWeight : friendsWithWeights) {

            Vertex friend = vertexWeight.getVertex();
            VertexCostParent costAndParentOfFriend = vertexCostParentTable.get(friend);

            // set cost and parent in vertex-cost-parent table for all non-processed friends
            if (costAndParentOfFriend.getParent() == null) { // friend is not yet processed
                unProcessedFriends.add(vertexWeight);
                // set cost
                Integer parentCost = vertexCostParentTable.get(startVertex).getCost();
                Integer friendWeight = vertexWeight.getWeight();
                costAndParentOfFriend.setCost(parentCost + friendWeight);
                // set parent
                costAndParentOfFriend.setParent(startVertex);
            }
        }

        // sort friends by weight
        SortedSet<VertexWeight> sortedUnFriendsByWeight = sortFriendsByWeight(unProcessedFriends);


        for (VertexWeight unProcessedFriend : sortedUnFriendsByWeight) {
            // Recursion
            findFastestPathFromAToD(graph, vertexCostParentTable, unProcessedFriend.getVertex());
        }
    }

    private static SortedSet<VertexWeight> sortFriendsByWeight(Set<VertexWeight> friendsWithWeights) {
        return new TreeSet<>(friendsWithWeights);
    }

    static class Vertex {
        private String name;

        public Vertex(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Vertex vertex = (Vertex) o;

            return name != null ? name.equals(vertex.name) : vertex.name == null;
        }

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    static class VertexCostParent {
        private Vertex vertex;
        private Integer cost;
        private Vertex parent;

        public VertexCostParent(Vertex vertex) {
            this.vertex = vertex;
        }

        public Vertex getVertex() {
            return vertex;
        }

        public void setVertex(Vertex vertex) {
            this.vertex = vertex;
        }

        public Integer getCost() {
            return cost;
        }

        public void setCost(Integer cost) {
            this.cost = cost;
        }

        public Vertex getParent() {
            return parent;
        }

        public void setParent(Vertex parent) {
            this.parent = parent;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            VertexCostParent that = (VertexCostParent) o;

            return vertex != null ? vertex.equals(that.vertex) : that.vertex == null;
        }

        @Override
        public int hashCode() {
            return vertex != null ? vertex.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "VertexCostParent{" +
                    "vertex=" + vertex +
                    ", cost=" + cost +
                    ", parent=" + parent +
                    '}';
        }
    }
}
