package algorithms.graphmanipulation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Tushar Chokshi @ 11/25/17.
 *
 * Grokking Algorithms book (Chapter 6)
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

        // Add your closest friends to queue. This is needed to initiate the search.
        Queue queue = new LinkedBlockingQueue();

        Friend[] yourFriends = friendsNetwork.get(you);
        Arrays.stream(yourFriends).forEach(yourFriend -> queue.add(yourFriend));

        Friend mangoSellerFriend = searchMangoSellerFriendOfYours(queue);

        System.out.println(mangoSellerFriend);
    }

    private static Friend searchMangoSellerFriendOfYours(Queue queue) {
        if (queue.isEmpty()) return null;

        Friend friend = (Friend) queue.poll();

        if (!friend.isVisited()) {

            if (friend.isMangoSeller) return friend;

            friend.setVisited(true);

            Friend[] friends = friendsNetwork.get(friend);

            if(friends != null && friends.length > 0) {
                Arrays.stream(friends).forEach(f -> queue.add(f));
            }
        }

        return searchMangoSellerFriendOfYours(queue);
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
