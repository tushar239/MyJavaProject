package algorithms._5recursion_and_dynamic_programming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _8StringPermutationsWithDups {
    public static void main(String[] args) {
        List<String> perms = getPerms("aabbbbc");
        for (String perm : perms) {
            System.out.println(perm);
        }
    }

    private static List<String> getPerms(String s) {
        List<String> result = new ArrayList<>();
        Map<Character, Integer> map = buildFreqTable(s);
        createPerms(map, "", s.length(), result);
        return result;
    }

    private static void createPerms(Map<Character, Integer> map, String prefix, int remaining, List<String> result) {
        if (remaining == 0) {
            result.add(prefix);
            return;
        }
        for (Character c : map.keySet()) {
            int count = map.get(c);
            if (count > 0) {
                map.put(c, count - 1);
                createPerms(map, prefix + c, remaining - 1, result);
                map.put(c, count);
            }
        }
    }

    private static Map<Character, Integer> buildFreqTable(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }
        return map;
    }


}
