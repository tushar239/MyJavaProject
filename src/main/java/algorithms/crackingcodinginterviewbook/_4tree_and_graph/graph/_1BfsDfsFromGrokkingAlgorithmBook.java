package algorithms.crackingcodinginterviewbook._4tree_and_graph.graph;

import java.util.ArrayList;
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


 BiDirectional BFS Search
 pg 109 of Cracking Coding Interview book
 You can use BiDirectional BFS Search to find shortest path between two nodes.
 You need to start BFS from both nodes and stop when they intersect


 BFS vs DFS
 - BFS uses queue. DFS uses stack.
 - BFS needs more memory compared to DFS.
   Because in BFS, you put all adjacent(connected) vertices of a vertex in a queue (Level First - vertices on the same level are pushed together in queue).
   DFS is a depth first search, so you go all the way far and till then you put only one-one vertex on stack and before you put next vertex of the same level on stack, you poll the last pushed vertex from stack. So, DFS needs less memory.
 - If you don't have a space problem, use BFS because it is an ideal algorithm to find shortest distance between two vertices.
   Use DFS only if you want to know Topological order of the vertices.
   Topological Order - https://www.youtube.com/watch?v=ddTC4Zovtbc  (Downloaded 'Topological Sort Graph Algorithm Using DFS.mp4')
 - BFS works on both cyclic/acyclic and directed/undirected graphs, whereas DFS works only on directed acyclic graph, then only toplogical dependency makes sense.

 BFS                                                                        DFS
- uses queue                                                                - uses stack
- It does Level Ordering Traversal. Means vertices on same level            - it does Depth First Search. It means that it finds ANY ONE (not all) unvisited friend of a vertex and pushes to stack and visits it while PEEKing it from stack.
  in graph are visited and pushed to queue together
- works on directed/undirected cyclic/acyclic all types of graphs           - works on DIRECTED ACYCLIC graph only.
- used to know SHORTEST path from A to B (Not Fastest Path)                 - used to know TOPOLOGICAL Order of vertices
- vertices are visited when they are PUSHED to queue                        - vertex is visited when it is PUSED to stack. vertex is POLLED when all its friends are visited.
- In average case, BFS is faster than DFS in searching
  but space complexity is highre than DFS. See the explnation below.


Time complexity of a graph is O(V+E).  ------- I am kind of not agreed to this explanation
  If you search your entire network for a mango seller, that means you’ll follow each edge (remember, an edge is the arrow or connection from one person to another). So the running time is at least O(number of edges).
  You also keep a queue of every person to search. Adding one person to the queue takes constant time: O(1). Doing this for every person will reserve O(number of people) total.
  Breadth-first search takes O(number of people + number of edges), and it’s more commonly written as O(V+E) (V for number of vertices, E for number of edges).


Space and Time complexity of BFS and DFS
    b is number of branches
    d is the depth of a graph

                      o                         E^0

    o                 o                 o       E^1

 o  o  o        o   o   o           o   o   o   E^2

Assuming that every vertex has E number of edges and height(depth) of the graph is D

Time Complexity:

    In worst case, if the node that we are searching for is at the bottom right corner, then we need to visit E^0+E^1+E^2+....+E^D = E^D+1 - 1 = O(E^D) is the time complexity of BFS and DFS searches.
    Normally, BFS is faster than DFS because if you want to search a last node in second level, BFS will be faster because you visit the node level wise.
              In DFS, you visit entire depth first. So, search will be slower.

Space Complexity:

    In BFS, you will have around E^D nodes in a queue at a time.
    In DFS, you will have only D+1 nodes in a stack at a time.
    So, space complexity wise, DFS is better than BFS.


What is Topological Ordering/Sorting?
 when you have packages in your project and one package depends on another, compiler needs to build dependent package first before dependee package. This is called topological sorting.
 In DFS, order of Popping of elements from stack will give you Topological Order.
 Topological order makes sense for 'Acyclic Graph'.

 https://www.youtube.com/watch?v=Q9PIxaNGnig

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



    Three ways to create Graphs
     - Edge List (some Graph class with Vertices and Edges GraphWithEdgeList.java) ---- worst performance. do not use it.
     - Adjacency Matrix --- Good for Dense graph. Not good for Sparse graph.
     - Adjacency List --- Good for Sparse graph. it can be represented in HashMap (e.g. BfsDfsFromGrokkingAlgorithmBook.java)

    Normally, real life graphs are Sparse. So, Adjacency List fits the best.

    Graph with Adjacency List can be represented as

        Map<Vertex, LinkedList<Vertex>> or
        Map<Vertex, Set<Vertex>> or
        Map<Vertex, Vertex[]> or  --- I am using it for this class
        Map<Vertex, BST> --- for faster search of a specific neighbour

        If you want to show weight also, then
        Map<Vertex, LinkedList<VertexWeight>>


                Tushar	Miral	Srikant	Anoop	Madhu	Rakesh	Yogita	Puja	Ronak	NotConnectedPerson1	NotConnectedPerson2
    Tushar	     	      1	      1	      1	      1	      1
    Miral	    1	       	      1				                   1	1
    Srikant	    1	      1	       						                          1
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

    Adjacency Matrix is good for Dense graph where every node is connected to almost every node (Complete or Almost Complete Graph)
    It is not good for Sparse graph because many cells in matrix will be empty as shown above. So, whenever you need to find out Srikant's friends, you need to visit all cells in Srikant row to figure out which ones have 1 in them.

BFS, DFS, Dijkstra's Algorithm

  Starting traversal from Vertex 'Tushar'

  BFS (Breadth First/Level First Search)
  --------------------------------------
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

  BFS to find SHORTEST PATH from Tushar to Ronak
  ----------------------------------------------
  add visited friend in visited list with its parent. So, store an object like Object(Friend, parent of Friend) in visited list.

  visited list:
    (Tushar, parent=null)
    (Miral, parent=Tushar)
    (Srikant, parent=Tushar)
    (Anoop, parent=Tushar)
    (Madhu, parent=Tushar)
    (Rakesh, parent=Tushar)
    (Puja, parent=Miral)
    (Yogita, parent=Miral)
    (Ronak, parent=Srikant)

    As soon as you find Ronak, stop there and return. you can backtrack the path from Tushar to Ronak using parents.

  BFS to find SHORTEST DISTANCE from Tushar to Puja
  -------------------------------------------------
  you maintain a map 'Friend to distance'.
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
  ------------------------
     start BFS by initializing a STACK with 'Tushar' in it.

     Now, PEEK (not POP) 'Tushar'
        Find Tushar's unvisited friend.
        If all friends are visited, then POP 'Tushar' from stack,
        otherwise PUSH ANY ONE unvisited friend to Stack (Stack=[Miral,Tushar])

        visit a friend while PUSHING it to stack. Only when you need to visit the friends(vertices) in TOPOLOGICAL ORDER, you visit them while POLLING.

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

     IMPORTANT:
     order of popping the elements from the stack is same as TOPOLOGICAL ORDER.
     If you want to detect a cycle, when you get neighbours of a vertex, if anyone of them is already visited, then there is a cycle. (BuildOrder.java of Cracking Coding Interview book has this requirement)

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
        Map<Friend, Friend[]> graph = new HashMap<>(); // same as Adjacency List. If you want to show weight also between two friends, then use Map<Friend, Set<FriendWeight>> same as DijkstraAlgorithmForPositivelyWeightedGraphGrokkingAlgorithmBook.java
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
        System.out.println("BFS example to find shortest path from Tushar to Ronak.....");
        {
            Map<Friend, Friend[]> graph = initializeGraph();
            findShortestPath(graph);
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


        /************************************************ DFS **********************************/


        System.out.println();
        System.out.println("............Depth First Search (DFS)............");
        System.out.println();
        System.out.println("DFS using Adjacency List.....");
        System.out.println();
        {
            Map<Friend, Friend[]> graph = initializeGraph();

            Stack<Friend> stack = new Stack<>();
            stack.push(new Friend("Tushar"));

            List<Friend> visitedFriends = new LinkedList<>();
            List<Friend> friendsInTopologicalOrder = new LinkedList<>();
            depthFirstSearch_Recursive(graph, stack, visitedFriends, friendsInTopologicalOrder);

            System.out.println("Visited Friends: " + visitedFriends);// [Friend{name='Tushar'}, Friend{name='Miral'}, Friend{name='Srikant'}, Friend{name='Ronak'}, Friend{name='Puja'}, Friend{name='Anoop'}, Friend{name='Madhu'}, Friend{name='Rakesh'}]
            System.out.println("Friends In Topological Order: " + friendsInTopologicalOrder); // [Friend{name='Ronak'}, Friend{name='Srikant'}, Friend{name='Puja'}, Friend{name='Miral'}, Friend{name='Anoop'}, Friend{name='Madhu'}, Friend{name='Rakesh'}, Friend{name='Tushar'}]
        }

        System.out.println();
        System.out.println("DFS using Adjacency Matrix.....");
        System.out.println();
        System.out.println("Recursive approach...");
        {
            GraphWithAdjacencyMatrix graph = GraphWithAdjacencyMatrix.createGraph();
            graph.prettyPrintMatrix();

            List<Integer> visited = new ArrayList<>();
            List<Integer> verticesInToplogicalOrder = new ArrayList<>();

            Stack<Integer> stack = new Stack<>();
            int start = graph.getVertices().get(0).getId();
            stack.push(start);
            visited.add(start);

            dfs_Using_AdjacencyMatrix_Recursive(graph, stack, visited, verticesInToplogicalOrder);

            System.out.println("Visited Vertices: " + visited);
            System.out.println("Vertices In Topological Order: " + verticesInToplogicalOrder);

        }
        System.out.println("Iterative approach...");
        {
            GraphWithAdjacencyMatrix graph = GraphWithAdjacencyMatrix.createGraph();
            graph.prettyPrintMatrix();

            List<Integer> visited = new ArrayList<>();
            List<Integer> verticesInToplogicalOrder = new ArrayList<>();

            dfs_Using_AdjacencyMatrix_Iteratively(graph, 0, visited, verticesInToplogicalOrder);

            System.out.println("Visited Vertices: " + visited);
            System.out.println("Vertices In Topological Order: " + verticesInToplogicalOrder);

        }
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
                    // Important: In BFS and DFS, you visit the vertices when you put them in queue or stack. In DFS, when you need to find Topological order, you visit the vertex while polling from queue.
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

    private static void findShortestPath(Map<Friend, Friend[]> graph) {
        Queue<Friend> queue = new LinkedBlockingQueue();

        // Add the node from where you want to find the distance to another node
        Friend you = new Friend("Tushar");
        queue.add(you);

        List<QueueObject> visited = new ArrayList<>();
        visited.add(new QueueObject(you, null));

        Friend ronak = new Friend("Ronak");
        boolean ronakFound = findShortestPath(graph, queue, visited, ronak);
        if (ronakFound) {
            System.out.println("There is a path from " + you.getName() + " to " + ronak.getName());
            System.out.println("Iterate visited list by finding parents");
            for (QueueObject visitedObj : visited) {
                if(visitedObj.getParent() != null) {
                    System.out.println("("+visitedObj.getFriend().getName()+", parent="+visitedObj.getParent().getName()+")");
                }else {
                    System.out.println("("+visitedObj.getFriend().getName()+", parent="+null+")");
                }
            }
        } else {
            System.out.printf("There is no path from " + you.getName() + " to " + ronak.getName());
        }

    }

    private static boolean findShortestPath(Map<Friend, Friend[]> graph, Queue<Friend> queue, List<QueueObject> visited, Friend pathTo) {
        if (queue.isEmpty()) return false;

        Friend me = queue.poll();

        Friend[] friends = graph.get(me);

        // Add your closest friends to queue with its parent. This is needed to initiate the search.
        if (friends != null && friends.length > 0) {
            for (Friend friend : friends) {
                if (!visited.contains(new QueueObject(friend, me))) {
                    queue.add(friend);
                    visited.add(new QueueObject(friend, me));
                    if (friend.equals(pathTo)) {
                        return true;
                    }
                }
            }
        }

        return findShortestPath(graph, queue, visited, pathTo);
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

    private static void depthFirstSearch_Recursive(Map<Friend, Friend[]> graph, Stack<Friend> stack, List<Friend> visitedFriends, List<Friend> friendsInTopologicalOrder) {
        if (stack.isEmpty()) return;

        // Important: In BFS, vertex is visited while adding it to queue. In DFS, vertex is visited while peeking it from the stack.
        Friend me = stack.peek();// At this point, just peek

        // you can do this or visit a friend when it is pushed to stack
        /*if (!visitedFriends.contains(me)) {
            visitedFriends.add(me);
        }*/

        Friend[] friends = graph.get(me);

        Friend notVisitedFriend = null;

        if (friends != null) {
            for (Friend friend : friends) {
                // If you want to detect a cycle, find if any friend is already visited. If that's the case, then there is a cycle (BuildOrder.java of Cracking Coding Interview book has this requirement)
                if (!visitedFriends.contains(friend)) {
                    notVisitedFriend = friend;
                    break;
                }
            }
        }

        if (notVisitedFriend == null) {
            Friend poppedFriend = stack.pop();// pop me, if all friends are visited
            // Ideally, if there is a cycle (you find even a single friend that is already visited, then it means that there is a cycle)
            // In that case, topological order is not possible. Here, for the sake of convenience, we are not taking care of that scenario.
            friendsInTopologicalOrder.add(poppedFriend); // order of popping is same as topological order
        } else { // otherwise, push my unvisited friend to stack
            stack.push(notVisitedFriend);
            visitedFriends.add(notVisitedFriend);
        }

        depthFirstSearch_Recursive(graph, stack, visitedFriends, friendsInTopologicalOrder);

    }

    private static void dfs_Using_AdjacencyMatrix_Recursive(GraphWithAdjacencyMatrix graph, Stack<Integer> stack, List<Integer> visitedVertices, List<Integer> verticesInTopologicalOrder) {

        if (stack == null) return;
        if (graph == null) return;

        if (stack.isEmpty()) return;

        Integer from = stack.peek();
        // you can do this or visit a vertex when it is pushed to stack.
            /*if(!visitedVertices.contains(from)) {
                visitedVertices.add(from);
            }*/

        boolean allVisited = true;
        int[] edges = graph.getEdges(from);
        if (edges != null) {
            for (int to = 0; to < edges.length; to++) {
                if (edges[to] == 1 && !visitedVertices.contains(to)) {
                    stack.push(to);
                    visitedVertices.add(to);
                    allVisited = false;
                    break;
                }
            }
        }

        if (allVisited) {
            Integer poppedVertex = stack.pop();
            // Ideally, if there is a cycle (you find even a single neighbour that is already visited, then it means that there is a cycle in graph).
            // In that case, topological order is not possible. Here, for the sake of convenience, we are not taking care of that scenario.
            verticesInTopologicalOrder.add(poppedVertex);
        }

        dfs_Using_AdjacencyMatrix_Recursive(graph, stack, visitedVertices, verticesInTopologicalOrder);
    }

    private static void dfs_Using_AdjacencyMatrix_Iteratively(GraphWithAdjacencyMatrix graph, int start, List<Integer> visitedVertices, List<Integer> verticesInTopologicalOrder) {
        int[][] adjacency_matrix = graph.getMatrix();

        Stack<Integer> stack = new Stack<>();

        stack.push(start);
        visitedVertices.add(start);

        while (!stack.isEmpty()) {
            int peeked = stack.peek();

            // you can do this or visit a vertex when it is pushed to stack.
            /*if(!visitedVertices.contains(peeked)) {
                visitedVertices.add(peeked);
            }*/

            int[] neighbours = adjacency_matrix[peeked];

            boolean allVisited = true;
            for (int i = 0; i < neighbours.length; i++) {
                // if vertex is not yet visitedVertices and value in matrix between peeked element is neighbour is 1
                if (adjacency_matrix[peeked][i] == 1 && !visitedVertices.contains(i)) {
                    stack.push(i);
                    visitedVertices.add(i); // visit a vertex when it is pushed to stack
                    allVisited = false;

                    break; // add  only one non-visitedVertices neighbour to stack
                }
            }
            // pop peeked element, if all of its neighbours are already visitedVertices
            if (allVisited) {
                // Ideally, if there is a cycle (you find even a single neighbour that is already visited, then it means that there is a cycle in graph).
                // In that case, topological order is not possible. Here, for the sake of convenience, we are not taking care of that scenario.
                Integer poppedElement = stack.pop();
                verticesInTopologicalOrder.add(poppedElement);
            }
        }
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

    static class QueueObject {
        private final Friend friend;
        private final Friend parent;

        public QueueObject(Friend friend, Friend parent) {
            this.friend = friend;
            this.parent = parent;
        }

        public Friend getFriend() {
            return friend;
        }

        public Friend getParent() {
            return parent;
        }

        @Override
        public String toString() {
            String str = "{" +
                    "friend=" + friend.getName();
           /* if (parent != null) {
                str += ", parent=" + parent.getName();
            } else {
                str += ", parent=" + parent;
            }
            str += "}";*/

            return str;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            QueueObject that = (QueueObject) o;

            return friend != null ? friend.equals(that.friend) : that.friend == null;
        }

        @Override
        public int hashCode() {
            return friend != null ? friend.hashCode() : 0;
        }
    }

}
