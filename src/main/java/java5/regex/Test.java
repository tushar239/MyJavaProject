package java5.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tushar Chokshi @ 1/19/16.
 */
public class Test {
    public static void main(String[] args) {
        String str = "sfsdfdsfsafsd,<?XML version=\"1.0\" encoding=\"UTF-8\"?><Response xmlns=\"...\" > something </Response>,abcd";

        //String startTagPattern = "^<"+getResponseTag()+ ".*" + ">$";
        // you need a group of <Response .....>
        //String startTagPattern = "(.*)((<(?i)"+"("+getXmlDeclarationTag()+"|"+getResponseTag()+")"+ ".*" + ">).*"+"</(?i)"+getResponseTag()+">)"+"(.*)";
        //String startTagPattern = "(.*)((<(?i)("+getXmlDeclarationTag()+"|"+getResponseTag()+").*>).*</(?i)Response>)(.*)";
        //String startTagPattern = "(.*)((<(?i)("+getXmlDeclarationTag()+").*>).*</(?i)Response>)(.*)";
        String startTagPattern =  "(.*)(<\\?(?i)xml.*>.*</(?i)Response>)(.*)";
        Pattern pattern = Pattern.compile(startTagPattern);
        Matcher matcher = pattern.matcher(str);
        System.out.println(matcher.matches());

        if(matcher.matches()) {
            System.out.println("total groups: "+matcher.groupCount());
            for (int i = 0; i <= matcher.groupCount(); i++) {
                System.out.println("group "+i+ " :"+ matcher.group(i)+" ,Start Index:"+matcher.start(i)+" , Length:"+matcher.group(i).length()); // group(0) is entire expression.

            }
        }



        String data = "Testing... \n" +
                "Registrar: abc.whois.com\n" +
                "registrar: 123.whois.com\n" +
                "end testing";
        System.out.println(getRegistrar(data));


        System.out.println("-------------");

        String webIdWithLocale = "cblt-ms-gmps-en-us-north";
        String webIdWithoutLocale = "cblt-ms-hyun";

        //String pattern = "cblt-\\w+-\\w+-\\w+";
        //String pattern = "(cblt-.+-.+)(-.+-.+)";
        //String pat = "(?i)(cblt-.+-.+)(-.+){2}";
        //String pat = "(?i)(cblt-.+-.+)(-.+)+";
        //String pat = "((?i)(cblt-.+-.+){1}?)(?=(-.+)+)";
        String pat = "(((?i)(cblt-.+-.+)){1}?)((-.+)+)";
        pat = "((cblt-.+-.+)+?)-.+";// reluctant quantifier 'X+?'
        pat = "(cblt-.+-.+)(?=-.+)";// Look Ahead (?=X)
        pat = "(cblt-.+?-.+?)(-.+)";
        test(pat,webIdWithoutLocale);
        testGrouping(pat, webIdWithoutLocale);
        System.out.println(extractCapture(pat, webIdWithoutLocale));
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
    private static void testGrouping(String patternToExtract, String str) {
        Pattern pattern = Pattern.compile(patternToExtract);
        Matcher matcher = pattern.matcher(str);
        if(matcher.matches()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                System.out.println("i:"+i+", "+matcher.group(i)); // group(0) is entire expression.
            }
        }

    }
    public static void test(String patternStr, String strToMatch) {
        boolean match = Pattern.matches(patternStr, strToMatch);

        if(match) {
            //Pattern pattern = Pattern.compile(patternStr);
            //Matcher matcher = pattern.matcher(strToMatch);
            System.out.println(strToMatch + "      MATCHES      " + patternStr);
        }
        else {
            System.out.println(strToMatch + "      DOES NOT MATCH      " + patternStr);
        }
    }

    private static List<String> getRegistrar(String data){
        String REGISTRAR_PATTERN = "(?i)Registrar:\\s(.*)";

        List<String> result = new ArrayList<>();

        Pattern registrarPattern = Pattern.compile(REGISTRAR_PATTERN);

        Matcher matcher = registrarPattern.matcher(data);

        while (matcher.find()) {
            result.add(matcher.group(1));
        }

        return result;
    }

    public static String getResponseTag() {
        return "Response";
    }
    public static String getXmlDeclarationTag() {
        return "\\?xml";
    }
}
