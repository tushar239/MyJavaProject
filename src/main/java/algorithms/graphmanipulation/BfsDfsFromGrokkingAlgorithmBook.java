package algorithms.graphmanipulation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

/*
 * Grokking Algorithms book (Chapter 6)
 * Here, I am using Map to represent a graph.
 *
 * BFS (Breath First Search) algorithm is used to find
 * - a required vertex in the graph
 * - whether a vertex is connected to specified vertex
 * - distances or shortest distance from one vertex to another vertex in graph
 *
 * Time complexity of a graph is O(V+E).
 * If you search your entire network for a mango seller, that means you’ll follow each edge (remember, an edge is the arrow or connection from one person to another). So the running time is at least O(number of edges).
 * You also keep a queue of every person to search. Adding one person to the queue takes constant time: O(1). Doing this for every person will take O(number of people) total.
 * Breadth-first search takes O(number of people + number of edges), and it’s more commonly written as O(V+E) (V for number of vertices, E for number of edges).
 *
 *
 * BFS vs DFS
 * - BFS uses queue. DFS uses stack.
 * - BFS needs more memory compared to DFS.
 *   Because in BFS, you put all adjacent(connected) vertices of a vertex in a queue (Level First - vertices on the same level are pushed together in queue).
 *   DFS is a depth first search, so you go all the way far and till then you put only one-one vertex on stack and before you put next vertex of the same level on stack, you poll the last pushed vertex from stack. So, DFS needs less memory.
 * - If you don't have a space problem, use BFS because it is an ideal algorithm to find shortest distanct between two vertices.
 *   Use DFS only if you want to know Topological order of the vertices.
 *   Topological Order - https://www.youtube.com/watch?v=ddTC4Zovtbc  (Downloaded 'Topological Sort Graph Algorithm Using DFS.mp4')
 *
 * Sample Graph
 * Tushar  ->   Miral -> Puja
 *        \      |   ^
 *        \      v   |
 *         ->   Srikant -> Ronak
 *         \
 *         \
 *          ->  Anoop
 *          \
 *          \
 *           -> Madhu
 *           \
 *           \
 *            -> Rakesh
 *
 *
 *          NotConnectedPerson1 -> NotConnectedPerson2
 *
 *  Starting traversal from Vertex 'Tushar'
 *  BFS (Breadth First/Level First Search)
 *      start BFS by initializing a QUEUE with 'Tushar' in it.
 *      Now, POLL 'Tushar' and visit it, if not visited already.      NOTE: polling dequeues the element from queue
 *      Push Tushar's friends (Miral, Srikant, Anoop, Madhu, Rakesh) to queue, if they are not visited already.
 *      POLL 'Miral' and visit it, if not visited already.
 *      Push Miral's friends to queue. Queue - Srikant, Anoop, Madhu, Rakesh, Puja
 *      POLL Srikant and visit it, if not visited already.
 *      and continue till queue is empty
 *  Visited Nodes will be in level order [Tushar,Miral,Srikant,Anoop,Madhu,Rakesh,Puja,Ronak]
 *
 *  DFS (Depth First Search)
 *     start BFS by initializing a STACK with 'Tushar' in it.
 *     Now, PEEK (not POLL) 'Tushar' and visit it, if not visited already.
 *     Find Tushar's unvisited friend. If all friends are visited, then POLL 'Tushar' from stack, otherwise PUSH ANY ONE unvisited friend to Stack. Stack=[Miral,Tushar]
 *     PEEK 'Miral' and continue above process (Stack = [Srikant, Miral, Tushar])
 *     PEEK 'Srikant' and continue above process (Stack = [Ronak, Srikant, Miral, Tushar])
 *     PEEK 'Ronak' and continue above process. As 'Ronak' doesn't have any unvisited friends, POP it from stack. (Stack = [Srikant, Miral, Tushar])
 *     PEEK 'Srikant' and continue above process. As 'Srikant' doesn't have any unvisited friends, POP it from stack. (Stack = [Miral, Tushar])
 *     PEEK 'Miral' and continue above process. (Stack = [Puja, Miral, Tushar])
 *     .....                                    (Stack = [Miral, Tushar])
 *     .....                                    (Stack = [Tushar])
 *     .....                                    (Stack = [Anoop, Tushar])
 *     .....                                    (Stack = [Tushar])
 *     .....                                    (Stack = [Madhu, Tushar])
 *     .....                                    (Stack = [Tushar])
 *     .....                                    (Stack = [Rakesh, Tushar])
 *     .....                                    (Stack = [Tushar])
 *     .....                                    (Stack = [])
 */
public class BfsDfsFromGrokkingAlgorithmBook {

    // Here, We are using a map to represent a graph. Friends are vertices in a graph and edges are defined by creating key-value pairs between friends.
    // You can't use this way of representing a graph, if you need an edge with some property (e.g. weight). You need to use GraphWithListOfEdges.
    private static Map<Friend, Friend[]> initializeGraph() {
        Friend you = new Friend("Tushar");
        Friend miral = new Friend("Miral");
        Friend srikant = new Friend("Srikant");
        Friend puja = new Friend("Puja", true);
        Friend madhu = new Friend("Madhu");
        Friend anoop = new Friend("Anoop");
        Friend rakesh = new Friend("Rakesh");
        Friend ronak = new Friend("Ronak");

        Friend notConnectedPerson1 = new Friend("notConnectedPerson1");
        Friend notConnectedPerson2 = new Friend("notConnectedPerson2");

        // create a graph using hash table
        Map<Friend, Friend[]> graph = new HashMap<>();
        graph.put(you, new Friend[]{miral, srikant, anoop, madhu, rakesh});
        graph.put(miral, new Friend[]{srikant, you, puja});
        graph.put(srikant, new Friend[]{you, miral, ronak});

        graph.put(notConnectedPerson1, new Friend[]{notConnectedPerson2});

        return graph;
    }


    public static void main(String[] args) {
        System.out.println("Breadth First Search (BFS)............");
        {
            Map<Friend, Friend[]> graph = initializeGraph();

            Queue queue = new LinkedBlockingQueue();

            // Add the node from where you want start searching
            queue.add(new Friend("Tushar"));

            List<Friend> visitedFriends = new LinkedList<>();
            Friend mangoSellerFriend = searchMangoSellerFriendOfYours(graph, queue, visitedFriends);

            System.out.println("Found closest Mango Seller is: " + mangoSellerFriend);// Friend{name='Puja'}
        }

        {
            Map<Friend, Friend[]> graph = initializeGraph();

            Queue queue = new LinkedBlockingQueue();

            // Add the node from where you want start searching
            queue.add(new Friend("Tushar"));

            List<Friend> visitedFriends = new LinkedList<>();
            isThereAPathFromTusharToNotConnectedPerson1(graph, queue, visitedFriends);

            if (!visitedFriends.contains(new Friend("notConnectedPerson1"))) {
                System.out.println("notConnectedPerson1 is NOT connected to Tushar"); // this will be the output
            } else {
                System.out.println("notConnectedPerson1 is connected to Tushar");
            }
        }
        {
            Map<Friend, Friend[]> graph = initializeGraph();

            Queue queue = new LinkedBlockingQueue();

            // Add the node from where you want to find the distance to another node
            Friend you = new Friend("Tushar");
            queue.add(you);

            Map<Friend, Integer> friendDistanceMap = new HashMap<>();

            // distance from starting node to itself is 0
            friendDistanceMap.put(you, 0);

            Map<Friend, Integer> distances = findShortestDistances(graph, queue, friendDistanceMap);
            System.out.println("distances from Tushar to other friends: " + distances);// {Friend{name='Srikant'}=1, Friend{name='Anoop'}=1, Friend{name='Madhu'}=1, Friend{name='Rakesh'}=1, Friend{name='Tushar'}=0, Friend{name='Puja'}=2, Friend{name='Ronak'}=2, Friend{name='Miral'}=1}

        }
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

        System.out.println("Depth First Search (DFS)............");
        {

            Map<Friend, Friend[]> graph = initializeGraph();

            Stack<Friend> stack = new Stack<>();
            stack.push(new Friend("Tushar"));

            List<Friend> visitedFriends = new LinkedList<>();
            depthFirstSearch(graph, stack, visitedFriends);

            System.out.println("visitedFriends: "+ visitedFriends);// [Friend{name='Tushar'}, Friend{name='Miral'}, Friend{name='Srikant'}, Friend{name='Ronak'}, Friend{name='Puja'}, Friend{name='Anoop'}, Friend{name='Madhu'}, Friend{name='Rakesh'}]
        }
    }

    private static Friend searchMangoSellerFriendOfYours(Map<Friend, Friend[]> graph, Queue queue, List<Friend> visitedFriends) {
        if (queue.isEmpty()) return null;

        Friend me = (Friend) queue.poll();
        visitedFriends.add(me);

        if (me.isMangoSeller) return me;

        Friend[] friends = graph.get(me);

        // Add your closest friends to queue. This is needed to initiate the search.
        if (friends != null && friends.length > 0) {
            for (Friend friend : friends) {
                if (!visitedFriends.contains(friend)) { //MOST IMPORTANT condition. if you don't add it, it might go in infinite loop. you don't want visit the vertex in a graph that is already visited.
                    queue.add(friend);
                }
            }
        }

        return searchMangoSellerFriendOfYours(graph, queue, visitedFriends);
    }

    private static Friend[] isThereAPathFromTusharToNotConnectedPerson1(Map<Friend, Friend[]> graph, Queue queue, List<Friend> visitedFriends) {
        if (queue.isEmpty()) return null;

        Friend me = (Friend) queue.poll();
        visitedFriends.add(me);

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

    private static void depthFirstSearch(Map<Friend, Friend[]> graph, Stack<Friend> stack, List<Friend> visitedFriends) {
        if (stack.isEmpty()) return;

        Friend me = stack.peek();// At this point, just peek
        if(!visitedFriends.contains(me)) {
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
            stack.pop(); // pop me, if all friends are visited
        } else { // otherwise, push my unvisited friend to stack
            stack.push(notVisitedFriend);
        }

        depthFirstSearch(graph, stack, visitedFriends);

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
