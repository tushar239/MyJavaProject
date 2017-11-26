package algorithms.graphmanipulation;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Tushar Chokshi @ 11/25/17.
 *         <p>
 *         Grokking Algorithms book (Chapter 6)
 *         <p>
 *         Here, I am using Map to represent a graph.
 */
public class BreathFirstSearchGrokkingAlgorithmBookWay {
    private static Map<Friend, Friend[]> friendsNetwork = new HashMap<>();

    private static final Friend you;

    static {
        you = new Friend("Tushar", false);
        Friend miral = new Friend("Miral", false);
        Friend srikant = new Friend("Srikant", false);
        Friend puja = new Friend("Puja", true);
        Friend madhu = new Friend("Madhu", false);
        Friend anoop = new Friend("Anoop", false);
        Friend rakesh = new Friend("Rakesh", false);
        Friend ronak = new Friend("Ronak", false);

        // create a graph using hash table
        friendsNetwork.put(you, new Friend[]{miral, srikant, anoop, madhu, rakesh});
        friendsNetwork.put(miral, new Friend[]{srikant, you, puja});
        friendsNetwork.put(srikant, new Friend[]{you, miral, ronak});
    }


    public static void main(String[] args) {

        {
            Queue queue = new LinkedBlockingQueue();

            // Add the node from where you want start searching
            queue.add(you);

            Friend mangoSellerFriend = searchMangoSellerFriendOfYours(queue);

            System.out.println(mangoSellerFriend);// Friend{name='Puja'}
        }
        {
            Queue queue = new LinkedBlockingQueue();

            // Add the node from where you want to find the distance to another node
            queue.add(you);

            Map<Friend, Integer> friendDistanceMap = new HashMap<>();

            // distance from starting node to itself is 0
            friendDistanceMap.put(you, 0);

            findShortestDistanceFromTusharToPuja(queue, friendDistanceMap);

            for (Friend friend : friendDistanceMap.keySet()) {
                if (friend.getName().equalsIgnoreCase("Puja")) {
                    System.out.println("Shortest distance from Tushar to Puja is " + friendDistanceMap.get(friend));//Shortest distance from Tushar to Puja is 2
                }
            }
        }
    }

    private static Friend searchMangoSellerFriendOfYours(Queue queue) {
        if (queue.isEmpty()) return null;

        Friend me = (Friend) queue.poll();
        me.setVisited(true);

        if (me.isMangoSeller) return me;

        Friend[] friends = friendsNetwork.get(me);

        // Add your closest friends to queue. This is needed to initiate the search.
        if (friends != null && friends.length > 0) {
            for (Friend friend : friends) {
                if (!friend.isVisited()) {
                    queue.add(friend);
                }
            }
        }

        return searchMangoSellerFriendOfYours(queue);
    }

    private static Map<Friend, Integer> findShortestDistanceFromTusharToPuja(Queue queue, Map<Friend, Integer> friendDistanceMap) {
        if (queue.isEmpty()) return friendDistanceMap;

        Friend me = (Friend) queue.poll();

        Friend[] friends = friendsNetwork.get(me);

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

        return findShortestDistanceFromTusharToPuja(queue, friendDistanceMap);

    }

    static class Friend { // vertex in graph
        private String name;
        private boolean isMangoSeller;
        private boolean visited; //most important variable

        public Friend(String name, boolean isMangoSeller) {
            this.name = name;
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
