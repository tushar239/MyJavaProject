package algorithms.graphmanipulation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
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
 */
public class BreathFirstSearchGrokkingAlgorithmBookWay {

    // Here, We are using a map to represent a graph. Friends are vertices in a graph and edges are defined by creating key-value pairs between friends.
    // You can't use this way of representing a graph, if you need an edge with some property (e.g. weight). You need to use GraphWithListOfEdges.
    private static Map<Friend, Friend[]> initialize() {
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

        {
            Map<Friend, Friend[]> graph = initialize();

            Queue queue = new LinkedBlockingQueue();

            // Add the node from where you want start searching
            queue.add(new Friend("Tushar"));

            List<Friend> visitedFriends = new LinkedList<>();
            Friend mangoSellerFriend = searchMangoSellerFriendOfYours(graph, queue, visitedFriends);

            System.out.println("Found closest Mango Seller is: "+ mangoSellerFriend);// Friend{name='Puja'}
        }

        {
            Map<Friend, Friend[]> graph = initialize();

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
            Map<Friend, Friend[]> graph = initialize();

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
            Map<Friend, Friend[]> graph = initialize();

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
                if (!visitedFriends.contains(friend)) {
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

    static class Friend { // vertex in graph
        private String name;
        private boolean isMangoSeller;
        private boolean visited; //most important variable

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

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        public boolean isVisited() {
            return visited;
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
