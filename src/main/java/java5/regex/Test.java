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
