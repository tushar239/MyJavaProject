package algorithms.treeandgraph.graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

/*
 Grokking Algorithms book (Chapter 6)
 ------------------------------------

 What is Graph?
 Graphs are made up of nodes and edges. A node can be directly connected to many other nodes. Those nodes are called its neighbors.


 Directed Graph - relationship between vertices are mentioned using edge(s) with arrow(s).
 UnDirected Graph - there is an edge without arrow between two vertices.

 A ---> B (Directed Graph). Relationship between A and B is mentioned through an Edge between them. Edge can have name, value, weight etc. properties.
 Unidirectional graph is called a tree. A tree is a special type of graph, where no edges ever point back.
 As there is only one way street (you can go from A to B, but not from B to A), so it is ACYCLIC graph also.


 A----B    (Undirected Graph)

    is same as

 A ----> B   (Directed Graph both ways)
   <----
 This is called CYCLIC Graph.

 BFS (Breath First Search) algorithm is used to find
 - Is Vertex connected to graph that you are searching in?
 - Is there a path from node A to node B?
 - What is the shortest path from node A to node B? (Not the fastest path. For fastest path, you need to use Dijkstra's algorithm that works for weighted graphs)

 Time complexity of a graph is O(V+E).
  If you search your entire network for a mango seller, that means you’ll follow each edge (remember, an edge is the arrow or connection from one person to another). So the running time is at least O(number of edges).
  You also keep a queue of every person to search. Adding one person to the queue takes constant time: O(1). Doing this for every person will take O(number of people) total.
  Breadth-first search takes O(number of people + number of edges), and it’s more commonly written as O(V+E) (V for number of vertices, E for number of edges).

 BFS vs DFS
 - BFS uses queue. DFS uses stack.
 - BFS needs more memory compared to DFS.
   Because in BFS, you put all adjacent(connected) vertices of a vertex in a queue (Level First - vertices on the same level are pushed together in queue).
   DFS is a depth first search, so you go all the way far and till then you put only one-one vertex on stack and before you put next vertex of the same level on stack, you poll the last pushed vertex from stack. So, DFS needs less memory.
 - If you don't have a space problem, use BFS because it is an ideal algorithm to find shortest distanct between two vertices.
   Use DFS only if you want to know Topological order of the vertices.
   Topological Order - https://www.youtube.com/watch?v=ddTC4Zovtbc  (Downloaded 'Topological Sort Graph Algorithm Using DFS.mp4')
 - BFS works on both cyclic/acyclic and directed/undirected graphs, whereas DFS works only on directed acyclic graph, then only toplogical dependency makes sense.

 BFS                                                                        DFS
- uses queue                                                                - uses stack
- It does Level Ordering Traversal. Means vertices on same level            - it does Depth First Search. It means that it finds ANY ONE (not all) unvisited friend of a vertex and pushes to stack and visits it while PEEKing it from stack.
  in graph are visited and pushed to queue together
- works on directed/undirected cyclic/acyclic all types of graphs           - works on DIRECTED ACYCLIC graph only.
- used to know SHORTEST path from A to B (Not Fastest Path)                 - used to know TOPOLOGICAL Order of vertices
- vertices are visited when they are PUSHED to queue                        - vertex is visited when it is PEEKed from stack. vertex is POLLED when all its friends are visited.

What is Topological Ordering/Sorting?
 when you have packages in your project and one package depends on another, compiler needs to build dependent package first before dependee package. This is called topological sorting.
 In DFS, order of Popping of elements from stack will give you Topological Order.
 Topological order makes sense for 'Acyclic Graph'.


Dijkstra's Algorithm
- used to know FASTEST PATH from A to B.
- Works on graph with EDGES with WEIGHTS.
- It does not use Queue/Stack. It maintains a Vertex|Cost|Parent table.
- It works only on ACYCLIC DIRECTED Graph with POSITIVE WEIGHTS of edges.
- You can call this algorithm PARTIALLY GREEDY Algorithm also because it starts with Lowest cost edge (Locally Optimal Solution) without thinking of entire graph and at the end it reaches to final solution (Globally Optimal Solution).
  But unlike to Dijkstra's Algorithm, Greedy Algorithm may not find optimal solution at the end.
  Dijkstra's Algorithm always find MOST OPTIMAL FASTEST PATH at the end because it doesn't visit already visited vertices.

         2                              Vertex  Cost                     Parent
      A -----> B                           A     0                          -
      |         \ 10                       B     2                          A
      |         v                          C     4                          A
      |--------- C ---> D                  D     6 (it will never be 14)    C
            4       2
                                        Fastest Path will always be D<-C<-A. it will never be D<-C<-B<-A.


 Sample Graph

        ---------------
        |             | ------> Yogita
        |             | |          |
        v             | |          v
     Tushar  ->      Miral  ---> Puja
      |   ^   |      |   ^
      |   |   |      v   |
      |   |    ->   Srikant ---> Ronak
      |   |          |
      |   ------------
      |
      |
       ->  Anoop
      |
      |
       -> Madhu
      |
      |
       -> Rakesh


              NotConnectedPerson1 -> NotConnectedPerson2



    Two ways to create Graphs
     - Hash Table (e.g. BfsDfsFromGrokkingAlgorithmBook.java)
     - some Graph class with Vertices and Edges (e.g. GraphWithListOfEdges.java)
     - Matrix


                Tushar	Miral	Srikant	Anoop	Madhu	Rakesh	Yogita	Puja	Ronak	NotConnectedPerson1	NotConnectedPerson2
    Tushar	    1	      1	      1	      1	      1	      1
    Miral	    1	      1	      1				                   1	1
    Srikant	    1	      1	      1						                          1
    Anoop
    Madhu
    Rakesh
    Yogita		    						                            1
    Puja
    Ronak
    NotConnectedPerson1											                                                    1
    NotConnectedPerson2

    Matrix is created using 2-D array

    A[0][0] = [Tushar][Tushar]
    A[0][1] = [Tushar][Miral]
    and so on


BFs, DFS, Dijkstra's Algorithm

  Starting traversal from Vertex 'Tushar'
  BFS (Breadth First/Level First Search)
      start BFS by initializing a QUEUE with 'Tushar' and VisitedFriends with 'Tushar' in it.
      Now, POLL 'Tushar'    (NOTE: polling dequeues the element from queue)
      Push Tushar's friends (Miral, Srikant, Anoop, Madhu, Rakesh) to queue, if they are not visited already. IMPORTANT: Visit them before pushing them in queue.

      POLL 'Miral'
      Push Miral's friends to queue, if they are not yet visited. Queue - Srikant, Anoop, Madhu, Rakesh, Puja. Visit them before pushing to queue
      (See you haven't pushed Tushar and Srikant again to queue because they are already visited)

      POLL Srikant
      and continue till queue is empty
  Visited Vertices will be in level order (all friends pushed to queue together, so visited in that order)
  [Tushar, Miral, Srikant, Anoop, Madhu, Rakesh, Puja, Ronak]

  To find distance from Tushar to Puja, you maintain a map 'Friend to distance'.
  start with distanceMap=[{Tushar, 0}]
  As you find the friends of Tushar, calculate his friends' distance by Tushar's distance+1
  Likewise,
    [
        {Tushar, 0},
        {Miral, 1},
        {Srikant, 1},
        {Anoop, 1},
        {Madhu, 1},
        {Rakesh, 1},
        {Puja, 2},
        {Ronak, 2},
    ]
   So, distance from Tushar to Puja will be 2. This is the shortest distance.

   To print the path, you need to maintain 'parent' inside Friend object and populate 'parent' as you visit the friend object.


  DFS (Depth First Search)
     start BFS by initializing a STACK with 'Tushar' in it.

     Now, PEEK (not POP) 'Tushar' and visit it, if not visited already.
        Find Tushar's unvisited friend.
        If all friends are visited, then POP 'Tushar' from stack,
        otherwise PUSH ANY ONE unvisited friend to Stack. Stack=[Miral,Tushar]

     PEEK 'Miral' and continue above process (Stack = [Srikant, Miral, Tushar])

     PEEK 'Srikant' and continue above process (Stack = [Ronak, Srikant, Miral, Tushar])

     PEEK 'Ronak' and continue above process. As 'Ronak' doesn't have any unvisited friends, POP it from stack. (Stack = [Srikant, Miral, Tushar])

     PEEK 'Srikant' and continue above process. As 'Srikant' doesn't have any unvisited friends, POP it from stack. (Stack = [Miral, Tushar])

     PEEK 'Miral' and continue above process. (Stack = [Puja, Miral, Tushar])
     .....                                    (Stack = [Miral, Tushar])
     .....                                    (Stack = [Tushar])
     .....                                    (Stack = [Anoop, Tushar])
     .....                                    (Stack = [Tushar])
     .....                                    (Stack = [Madhu, Tushar])
     .....                                    (Stack = [Tushar])
     .....                                    (Stack = [Rakesh, Tushar])
     .....                                    (Stack = [Tushar])
     .....                                    (Stack = [])

     Visited Vertices will in 'one friend vertex at a time' order. One friend at a time will be pushed to stack.
     [Tushar, Miral, Srikant, Ronak, Puja, Anoop, Madhu, Rakesh]

     Friends in Topological Order: [Ronak, Srikant, Puja, Miral, Anoop, Madhu, Rakesh, Tushar]
     There can be many possibilities of topological order based on which unvisited friend is pushed to stack first.

  Dijkstra's Algorithm

    If you have a weight for edges between vertices, you need to use Dijkstra's Algorithm to find FASTEST path from one vertex to another (DijkstraAlgorithmForPositivelyWeightedGraphGrokkingAlgorithmBook.java)

 */
public class _1BfsDfsFromGrokkingAlgorithmBook {

    // Here, We are using a map to represent a graph. Friends are vertices in a graph and edges are defined by creating key-value pairs between friends.
    // If you have a weight for edges between vertices, you need to use Dijkstra's Algorithm to find FASTEST path from one vertex to another (DijkstraAlgorithmForPositivelyWeightedGraphGrokkingAlgorithmBook.java)
    private static Map<Friend, Friend[]> initializeGraph() {
        Friend you = new Friend("Tushar");
        Friend miral = new Friend("Miral");
        Friend srikant = new Friend("Srikant");
        Friend puja = new Friend("Puja", true);
        Friend madhu = new Friend("Madhu");
        Friend anoop = new Friend("Anoop");
        Friend rakesh = new Friend("Rakesh");
        Friend ronak = new Friend("Ronak");
        Friend yogita = new Friend("Yogita");

        Friend notConnectedPerson1 = new Friend("notConnectedPerson1");
        Friend notConnectedPerson2 = new Friend("notConnectedPerson2");

        // create a graph using hash table
        Map<Friend, Friend[]> graph = new HashMap<>();
        graph.put(you, new Friend[]{miral, srikant, anoop, madhu, rakesh});
        graph.put(miral, new Friend[]{srikant, you, puja, yogita});
        graph.put(srikant, new Friend[]{you, miral, ronak});
        graph.put(yogita, new Friend[]{puja});

        graph.put(notConnectedPerson1, new Friend[]{notConnectedPerson2});

        return graph;
    }


    public static void main(String[] args) {
        System.out.println("............Breadth First Search (BFS)............");
        System.out.println();
        System.out.println("Basic BFS example to find shortest path from Tushar to any Mango Seller .....");
        {
            Map<Friend, Friend[]> graph = initializeGraph();

            Friend mangoSellerFriend = searchMangoSellerFriendOfYours(graph);

            System.out.println("Found closest Mango Seller is: " + mangoSellerFriend);// Friend{name='Puja'}
        }
        System.out.println();
        System.out.println("Basic BFS example to find whether any node is a part of a graph.....");
        {
            Map<Friend, Friend[]> graph = initializeGraph();

            List<Friend> visitedFriends = isThereAPathFromTusharToNotConnectedPerson1(graph);

            if (!visitedFriends.contains(new Friend("notConnectedPerson1"))) {
                System.out.println("notConnectedPerson1 is NOT connected to Tushar"); // this will be the output
            } else {
                System.out.println("notConnectedPerson1 is connected to Tushar");
            }
        }
        System.out.println();
        System.out.println("BFS example to find shortest distances from Tushar to other nodes in graph .....");
        {
            Map<Friend, Friend[]> graph = initializeGraph();

            Map<Friend, Integer> distances = findShortestDistances(graph);
            System.out.println("distances from Tushar to other friends: " + distances);// {Friend{name='Srikant'}=1, Friend{name='Anoop'}=1, Friend{name='Madhu'}=1, Friend{name='Rakesh'}=1, Friend{name='Tushar'}=0, Friend{name='Puja'}=2, Friend{name='Ronak'}=2, Friend{name='Miral'}=1}
        }
        System.out.println();
        System.out.println("BFS example to find shortest distance from Tushar to Puja in a graph .....");
        {
            Map<Friend, Friend[]> graph = initializeGraph();

            Queue queue = new LinkedBlockingQueue();

            // Add the node from where you want to find the distance to another node
            Friend you = new Friend("Tushar");
            queue.add(you);

            Map<Friend, Integer> friendDistanceMap = new HashMap<>();

            // distance from starting node to itself is 0
            friendDistanceMap.put(you, 0);

            Integer distanceFromTusharToPuja = findShortestDistanceFromTusharToPuja(graph, queue, friendDistanceMap, new Friend("Puja"));
            System.out.println("Shortest distance from Tushar to Puja: " + distanceFromTusharToPuja);
        }
        System.out.println();
        System.out.println("............Depth First Search (DFS)............");
        System.out.println();
        {

            Map<Friend, Friend[]> graph = initializeGraph();

            Stack<Friend> stack = new Stack<>();
            stack.push(new Friend("Tushar"));

            List<Friend> visitedFriends = new LinkedList<>();
            List<Friend> friendsInTopologicalOrder = new LinkedList<>();
            depthFirstSearch(graph, stack, visitedFriends, friendsInTopologicalOrder);

            System.out.println("Visited Friends: " + visitedFriends);// [Friend{name='Tushar'}, Friend{name='Miral'}, Friend{name='Srikant'}, Friend{name='Ronak'}, Friend{name='Puja'}, Friend{name='Anoop'}, Friend{name='Madhu'}, Friend{name='Rakesh'}]
            System.out.println("Friends In Topological Order: " + friendsInTopologicalOrder); // [Friend{name='Ronak'}, Friend{name='Srikant'}, Friend{name='Puja'}, Friend{name='Miral'}, Friend{name='Anoop'}, Friend{name='Madhu'}, Friend{name='Rakesh'}, Friend{name='Tushar'}]
        }
        System.out.println();
    }
    private static Friend searchMangoSellerFriendOfYours(Map<Friend, Friend[]> graph) {
        Queue queue = new LinkedBlockingQueue();

        // Add the node from where you want start searching
        Friend you = new Friend("Tushar");
        queue.add(you);

        List<Friend> visitedFriends = new LinkedList<>();
        visitedFriends.add(you);

        return searchMangoSellerFriendOfYours(graph, queue, visitedFriends);

    }
    private static Friend searchMangoSellerFriendOfYours(Map<Friend, Friend[]> graph, Queue queue, List<Friend> visitedFriends) {
        if (queue.isEmpty()) return null;

        Friend me = (Friend) queue.poll();

        if (me.isMangoSeller) return me;

        Friend[] friends = graph.get(me);

        // Add your closest friends to queue. This is needed to initiate the search.
        if (friends != null && friends.length > 0) {
            for (Friend friend : friends) {
                if (!visitedFriends.contains(friend)) { //if not yet visited. MOST IMPORTANT condition. if you don't add it, it might go in infinite loop. you don't want visit the vertex in a graph that is already visited.
                    // Important: In BFS, you visit the vertices when you put them in queue. In DFS, you do that when you peek from the stack.
                    visitedFriends.add(friend); // visit a friend
                    queue.add(friend);
                }
            }
        }

        return searchMangoSellerFriendOfYours(graph, queue, visitedFriends);
    }
    private static List<Friend> isThereAPathFromTusharToNotConnectedPerson1(Map<Friend, Friend[]> graph) {
        Queue queue = new LinkedBlockingQueue();

        // Add the node from where you want start searching
        Friend you = new Friend("Tushar");
        queue.add(you);

        List<Friend> visitedFriends = new LinkedList<>();
        visitedFriends.add(you);

        isThereAPathFromTusharToNotConnectedPerson1(graph, queue, visitedFriends);

        return visitedFriends;

    }
    private static Friend[] isThereAPathFromTusharToNotConnectedPerson1(Map<Friend, Friend[]> graph, Queue queue, List<Friend> visitedFriends) {
        if (queue.isEmpty()) return null;

        Friend me = (Friend) queue.poll();
        if (!visitedFriends.contains(me)) {// if not yet visited
            visitedFriends.add(me);
        }

        Friend[] friends = graph.get(me);

        // Add your closest friends to queue. This is needed to initiate the search.
        if (friends != null && friends.length > 0) {
            for (Friend friend : friends) {
                if (!visitedFriends.contains(friend)) {
                    queue.add(friend);
                }
            }
        }

        return isThereAPathFromTusharToNotConnectedPerson1(graph, queue, visitedFriends);

    }

    private static Map<Friend, Integer> findShortestDistances(Map<Friend, Friend[]> graph) {
        Queue queue = new LinkedBlockingQueue();

        // Add the node from where you want to find the distance to another node
        Friend you = new Friend("Tushar");
        queue.add(you);

        Map<Friend, Integer> friendDistanceMap = new HashMap<>();
        // distance from starting node to itself is 0
        friendDistanceMap.put(you, 0);

        return findShortestDistances(graph, queue, friendDistanceMap);
    }

    // https://www.youtube.com/watch?v=0XgVhsMOcQM
    private static Map<Friend, Integer> findShortestDistances(Map<Friend, Friend[]> graph, Queue queue, Map<Friend, Integer> friendDistanceMap) {
        if (queue.isEmpty()) return friendDistanceMap;

        Friend me = (Friend) queue.poll();

        Friend[] friends = graph.get(me);

        // Add your closest friends to queue. This is needed to initiate the search.
        if (friends != null && friends.length > 0) {
            for (Friend friend : friends) {
                if (!friendDistanceMap.containsKey(friend)) {

                    // distance of my friend from me is +1. If you are using 'Edge' object, you can use edge.getWeight().
                    friendDistanceMap.put(friend, friendDistanceMap.get(me) + 1);

                    queue.add(friend);
                }

            }
        }

        return findShortestDistances(graph, queue, friendDistanceMap);
    }

    private static Integer findShortestDistanceFromTusharToPuja(Map<Friend, Friend[]> graph, Queue queue, Map<Friend, Integer> friendDistanceMap, Friend friendToFindDistanceTo) {
        if (queue.isEmpty()) return 0;

        Friend me = (Friend) queue.poll();

        Friend[] friends = graph.get(me);

        // Add your closest friends to queue. This is needed to initiate the search.
        if (friends != null && friends.length > 0) {
            for (Friend friend : friends) {
                if (!friendDistanceMap.containsKey(friend)) {
                    if (friend.equals(friendToFindDistanceTo)) {
                        return friendDistanceMap.get(me) + 1;
                    }
                    friendDistanceMap.put(friend, friendDistanceMap.get(me) + 1);
                    queue.add(friend);
                }

            }
        }

        return findShortestDistanceFromTusharToPuja(graph, queue, friendDistanceMap, friendToFindDistanceTo);
    }

    private static void depthFirstSearch(Map<Friend, Friend[]> graph, Stack<Friend> stack, List<Friend> visitedFriends, List<Friend> friendsInTopologicalOrder) {
        if (stack.isEmpty()) return;

        // Important: In BFS, vertex is visited while adding it to queue. In DFS, vertex is visited while peeking it from the stack.
        Friend me = stack.peek();// At this point, just peek
        if (!visitedFriends.contains(me)) {
            visitedFriends.add(me);
        }

        Friend[] friends = graph.get(me);

        Friend notVisitedFriend = null;

        if (friends != null) {
            for (Friend friend : friends) {
                if (!visitedFriends.contains(friend)) {
                    notVisitedFriend = friend;
                    break;
                }
            }
        }

        if (notVisitedFriend == null) {
            Friend poppedFriend = stack.pop();// pop me, if all friends are visited
            friendsInTopologicalOrder.add(poppedFriend); // order of popping is same as topological order
        } else { // otherwise, push my unvisited friend to stack
            stack.push(notVisitedFriend);
        }

        depthFirstSearch(graph, stack, visitedFriends, friendsInTopologicalOrder);

    }

    static class Friend { // vertex in graph
        private String name;
        private boolean isMangoSeller;

        public Friend(String name) {
            this.name = name;
        }

        public Friend(String name, boolean isMangoSeller) {
            this(name);
            this.isMangoSeller = isMangoSeller;
        }

        public String getName() {
            return name;
        }

        public boolean isMangoSeller() {
            return isMangoSeller;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Friend friend = (Friend) o;

            return name != null ? name.equals(friend.name) : friend.name == null;
        }

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "Friend{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
