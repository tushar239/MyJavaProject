package algorithms.crackingcodinginterviewbook._1stringmanipulations;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Tushar Chokshi
 * @date 12/5/17
 */
public class Temp {
    public static void main(String[] args) throws ParseException {
        int[] numbers = new int[]{1,2,3,4,5};

        int count = 0;
        for (int number : numbers) {
            for (int i : numbers) {
                count++;
            }
        }
        System.out.println(count);
        
        count = 0;
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j <= i; j++) {
                count++;
            }
        }
        System.out.println(count);

        String dateInStringFormat = getStandardDateFormat().format(new Date());
        System.out.println(dateInStringFormat);

        Date parsedDate = getStandardDateFormat().parse(dateInStringFormat);
        System.out.println(parsedDate);
    }

    private static final String ISO_8601_COMPATIBLE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    private static SimpleDateFormat standardDateFormat;


    private static DateFormat getStandardDateFormat() {
        standardDateFormat = new SimpleDateFormat(ISO_8601_COMPATIBLE_DATE_FORMAT);
        standardDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        return standardDateFormat;
    }
}
