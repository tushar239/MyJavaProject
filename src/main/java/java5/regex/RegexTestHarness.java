package java5.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tushar Chokshi @ 11/17/15.
 */

// https://docs.oracle.com/javase/tutorial/essential/regex/intro.html
// https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html

// GOOD - http://www.ocpsoft.org/opensource/guide-to-regular-expressions-in-java-part-1/#section-13
// Grouping - http://www.javamex.com/tutorials/regular_expressions/capturing_groups.shtml
//
// http://www.javaranch.com/journal/2003/04/RegexTutorial.htm
// Non-capturing groups (?:X)
// Look Ahead and Behind Constructs (?=X) , (?!X) , (?<=X) , (?<!X)
// flags -- (?idmsux-idmsux) , (?idmsux-idmsux:X) , Pattern.compile(String, int)
/*
A Pattern object is a compiled representation of a regular expression. The Pattern class provides no public constructors. To create a pattern, you must first invoke one of its public static compile methods, which will then return a Pattern object. These methods accept a regular expression as the first argument; the first few lessons of this trail will teach you the required syntax.
A Matcher object is the engine that interprets the pattern and performs match operations against an input string. Like the Pattern class, Matcher defines no public constructors. You obtain a Matcher object by invoking the matcher method on a Pattern object.
A PatternSyntaxException object is an unchecked exception that indicates a syntax error in a regular expression pattern.
*/

public class RegexTestHarness {

    public static void main(String[] args) {
        {
            System.out.println("1 -----------------------------");
            test("I lost my sablefish", new String[]{"I lost my sablefish"});
            System.out.println("2 -----------------------------");
            test("I lost my sablefish", new String[]{"I lost my sablefish hih hii"});
            System.out.println("3 -----------------------------");
            test("I lost my sablefish", new String[]{"I lost my sablefish"});
            System.out.println("4 -----------------------------");

            // “\w” says a word character(number/alphabet chars),
            // “+” says match one or more.
            test("I lost my \\w+", new String[]{"I lost my sablefish", "I lost my: sablefish", "I lost my 1"});
            System.out.println("5 -----------------------------");

            // [\w\s]*my means any word char OR a whitespace char 0 or more times
            // [\w\s]?my means any word char OR a whitespace char should appear 0 or 1 time before 'my'
            // "." means any character or whitespace. If you say .* means any char/whitespace 0 or more times. NOTE: \n is not considered as a character.
            // "X?" means X appears 0 or 1 time
            test("[\\w\\s]*my.?\\w+", new String[]{"I lost my sablefish", "$ lost my sablefish", "I lost my:sablefish", "I lost my 1"});
            test("[\\w\\s]?my.?\\w+", new String[]{"I lost my sablefish"});

            System.out.println("6 -----------------------------");
            // [] is used to or characters or numbers or ranges. To or strings, you need to use (str1|str2|...)
            // ^X - should start with X
            // X$ - should end with X
            test("^(I|you|hi)\\s*\\w*\\s*my$", new String[]{"I lost my", "Me lost my", "you lost hey hey my", "hi lost my", "I lost my wall"});

            System.out.println("7 -----------------------------");
            // X? - X should appear 0 or 1 time
            // X+ - X should appear 1 or more time
            // X* - X should appear 0 or more time
            // X{n} - X should appear exactly n times
            // X{0,n} - X should appear anywhere between 0 to n times
            // [0-9A-Za-z] - 0 to 9 OR A to Z OR a to z is acceptable
            test("^[0-9A-Za-z]{3}\\s*my$", new String[]{"IAO     my","I9O     my", "IA   my"});

            System.out.println("8 -----------------------------");
            // Look Ahead patterns
            // (?=X)
            // Matches "apple" or "cherry" where the following pattern
            // matches " chocolate".  " chocolate" is not a part of the
            // resulting match, it follows it.

            String pat = "(apple|cherry)(?= chocolate)";
            Pattern pattern = Pattern.compile(pat);
            String input = "Today's specials are apple chocolate pie and cherry chocolate and cherry banana pie.";
            boolean match = Pattern.matches(pat, input);  // false
            System.out.println("match: "+match);
            Matcher matcher = pattern.matcher(input);
            System.out.println(extractCapture(pat, input));// [apple, cherry]
            //test(pat, new String[]{input, "cherry chocolate", "apple candy"});

            // (?!X)
            // "(apple|cherry)(?! chocolate)"
            // Matches "apple" or "cherry" where the following pattern
            // does not match " chocolate".

            // Look Behind Patterns
            // (?<=X)
            // "(?<=fried )(bananas|clam)"
            // Matches "bananas" or "clam" if preceded by "fried ".
            // "fried " is not part of the resulting match, it precedes it.

            // (?<!X)
            // "(?<!fried )(bananas|clam)"
            // Matches "bananas" or "clam" if not preceded by "fried ".

            System.out.println("9 -----------------------------");
            // ?: - NON-capturing parenthesis - (cblt) won't be considered as a group
            // (?i) - ignore case  (?i)(cblt)
            test("((?i)(?:cblt)-.+-.+)-.+-.+", new String[] {"cblt-ms-gmps-en-us", "CBLT-ms-gmps-en-us"});

        }
        System.out.println("--------------------------------");
        System.out.println("Extract Capture Example");
        {
            String patternToExtract = "\\w\\s+my";
            System.out.println(extractCapture(patternToExtract, "I my jsdkfsd hi U         my"));
        }
        System.out.println("--------------------------------");
        System.out.println("Grouping Example");
        /*
        https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html#cg
        http://www.javamex.com/tutorials/regular_expressions/capturing_groups.shtm

        Capturing groups are numbered by counting their opening parentheses from left to right. In the expression ((A)(B(C))), for example, there are 5 such groups:

            0   (<<entire expression>>) - implicit brackets that covers entire expression
            1  ((A)(B(C)))
            2  (A)
            3  (B(C))
            4  (C)

         Group 0 always stands for the entire expression. Even if you have () covering ur expression, still there is one extra implicit () exist.
          So your actual expression is (((A)(B(C))))

          In many cases, you need to use () even though, you don't want to create a group e.g. "(title|subtitle)":.*
          In this expression you want to say "title":abcd or "subtitle":abcd both matches. But this expression will also create a group because u have used ().
          Creating a group unnecessarily is expensive and overhead for java. so you can use ?: to tell java not to create a group e.g. "(?:title|subtitle)":.*


         Capturing groups are so named because, during a match, each subsequence of the input sequence that matches such a group is saved. The captured subsequence may be used later in the expression, via a back reference, and may also be retrieved from the matcher once the match operation is complete.

         Capturing groups are an extremely useful feature of regular expression matching that allow us to query the Matcher to find out what the part of the string was that matched against a particular part of the regular expression.

         "\d{2}-\d{2}-\d{4}" is a reg exp for date format dd-mm-yyyy
         if you want to extract day, month and year out of it, you can group them accordingly using () and later on you can extract them using matcher.group(group number)
         "(\\d{2})-(\\d{2})-(\\d{4})"

         group 0 is always entire expression.

        */
        testGrouping("(\\d{2})-(\\d{2})-(\\d{4})", "09-23-1979");
        /*
        O/P:
        09-23-1979  - group 0
        09          - group 1
        23          - group 2
        1979        - group 3
         */

        System.out.println("--------------------------------");
        /*
            http://stackoverflow.com/questions/1139171/what-is-the-difference-between-greedy-and-reluctant-regular-expression-quant
            http://stackoverflow.com/questions/5319840/greedy-vs-reluctant-vs-possessive-quantifiers

            Greedy Quantifiers
            *?     Match 0 or more times, not greedily
            +?     Match 1 or more times, not greedily
            ??     Match 0 or 1 time, not greedily
            {n}?   Match exactly n times, not greedily
            {n,}?  Match at least n times, not greedily
            {n,m}? Match at least n but not more than m times, not greedily

            Reluctant Quantifiers
            *+     Match 0 or more times and give nothing back
            ++     Match 1 or more times and give nothing back
            ?+     Match 0 or 1 time and give nothing back
            {n}+   Match exactly n times and give nothing back (redundant)
            {n,}+  Match at least n times and give nothing back
            {n,m}+ Match at least n but not more than m times and give nothing back
        */
        System.out.println("Greedy and Reluctant Quantifiers Example");

        testGreedyAndReluctantQuantifiers();
    }
    private static void testGreedyAndReluctantQuantifiers() {

        String str = "The red fox jumped over the red fence";
        String pat = "(.*)red(.*)";
        //String pat = "(.*)red(.*)red(.*)";

        Pattern pattern = Pattern.compile(pat);
        Matcher matcher = pattern.matcher(str);
        System.out.println("Matches? : "+matcher.matches());

        testGrouping(pat, str);
        //group 0: The red fox jumped over the red fence
        //group 1: The red fox jumped over the
        //group 2:  fence

        pat = "(.*?)red(.*)";
        testGrouping(pat, str);
        //group 0: The red fox jumped over the red fence
        //group 1: The
        //grpop 2:  fox jumped over the red fence

    }

    private static void testGrouping(String patternToExtract, String str) {
        Pattern pattern = Pattern.compile(patternToExtract);
        Matcher matcher = pattern.matcher(str);
        if(matcher.matches()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                System.out.println(i +":"+ matcher.group(i)); // group(0) is entire expression.
            }
        }

    }
    private static List<String> extractCapture(String patternToExtract, String str) {
        List<String> capturedStrings = new ArrayList<>();

        Pattern pattern = Pattern.compile(patternToExtract);
        Matcher matcher = pattern.matcher(str);

        while(matcher.find()) {// Attempts to find the next subsequence of the input sequence in str that matches the pattern.
            // matcher.group() is same as matcher.group(0) returning 0th group, so entire expression
            capturedStrings.add(matcher.group());// matcher.group() returns an input sequence matched by a previous match (here matcher.find())
        }
        return capturedStrings;

    }

    public static void test(String patternStr, String[] strsToMatch) {
        for (String str : strsToMatch) {
            boolean match = Pattern.matches(patternStr, str);

            if(match)
                System.out.println(str +"      MATCHES      "+ patternStr);
            else
                System.out.println(str +"      DOES NOT MATCH      "+ patternStr);
        }
    }
    public static void testRegex(String[] args) {


        Pattern pattern =
                Pattern.compile(args[0]);

        Matcher matcher =
                pattern.matcher(args[1]);

        boolean found = false;
        while (matcher.find()) {
            System.out.println(String.format("I found the text" +
                            " \"%s\" starting at " +
                            "index %d and ending at index %d.%n",
                    matcher.group(),
                    matcher.start(),
                    matcher.end()));

            found = true;
        }
        if (!found) {
            System.out.println("No match found.%n");
        }
    }
}



