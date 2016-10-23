package java5.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tushar Chokshi @ 11/17/15.
 */

// https://docs.oracle.com/javase/tutorial/essential/regex/intro.html
// https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
// http://www.ocpsoft.org/opensource/guide-to-regular-expressions-in-java-part-1/#section-13
/*
A Pattern object is a compiled representation of a regular expression. The Pattern class provides no public constructors. To create a pattern, you must first invoke one of its public static compile methods, which will then return a Pattern object. These methods accept a regular expression as the first argument; the first few lessons of this trail will teach you the required syntax.
A Matcher object is the engine that interprets the pattern and performs match operations against an input string. Like the Pattern class, Matcher defines no public constructors. You obtain a Matcher object by invoking the matcher method on a Pattern object.
A PatternSyntaxException object is an unchecked exception that indicates a syntax error in a regular expression pattern.

 */
public class PatternAndMatcher {
    public static void main(String[] args) {
        {
            Pattern p = Pattern.compile("a*b");
            Matcher m = p.matcher("aaaaab");
            boolean b = m.matches();
            System.out.println(b);
        }
        {
            boolean b = Pattern.matches("a*b", "aaaaab");
            System.out.println(b);
        }
    }

}
