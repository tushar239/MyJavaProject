package StringExperiment;

import java.util.ArrayList;
import java.util.List;

public class StringExperiment {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // String in immutable in java. It means that value of the String can not be changed once it is assigned.
        // Changing the value will create a new String object.
        String str = "abc";
        addString(str);
        System.out.println(str); // O/P: abc, not abcdef
        
        List<String> list = new ArrayList<String>();
        list.add("abc");
        addInList(list);
        System.out.println(list);// O/P: [abc, def]

    }
    private static void addString(String str) {
        str += "def";
    }
    private static void addInList(List<String> list) {
        list.add("def");
    }
}
