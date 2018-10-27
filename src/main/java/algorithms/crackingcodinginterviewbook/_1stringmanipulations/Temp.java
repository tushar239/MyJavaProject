package algorithms.crackingcodinginterviewbook._1stringmanipulations;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.mariuszgromada.math.mxparser.Constant;
import org.mariuszgromada.math.mxparser.Expression;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Tushar Chokshi
 * @date 12/5/17
 */
public class Temp {
    public static void main(String[] args) throws ParseException {
        int[] numbers = new int[]{1, 2, 3, 4, 5};

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

        Constant T = new Constant("T = 1");
        Constant F = new Constant("F = 0");
        Expression expression = new Expression(("T || (F || (F && T))"), T, F);
        System.out.println(expression.getExpressionString() + " = " + expression.calculate());

        String[] array = {"New", "2018", "Chevrolet", "Camaro", "LX"};

        List<String> set = printSubSequencesRecursively(array, 0, array.length - 1);
        System.out.println(set.size());
        System.out.println(set);


        String[] arrayOfVehCriteria = {"category", "year", "make", "model", "trim"};
        List<String> vehCriteriaSubSequences = printSubSequencesRecursively(arrayOfVehCriteria, 0, arrayOfVehCriteria.length - 1);
        System.out.println(vehCriteriaSubSequences.size());
        System.out.println(vehCriteriaSubSequences);


       /* String[] optionCodes = {"op1", "op2", "op3"};
        Set<String> withOptionCodes = new HashSet<>();
        for (String optionCode : optionCodes) {
            withOptionCodes.add(optionCode);
            for (String s : set) {
                withOptionCodes.add(s+"$"+optionCode);
            }
        }

        Set<String> finalSet = new HashSet<>();
        finalSet.addAll(set);
        finalSet.addAll(withOptionCodes);

        System.out.println(finalSet);*/

       List<Integer> list1 = Lists.newArrayList(1,2,3);
        List<Integer> list2 = Lists.newArrayList(3,4);
        Collection<Integer> disjunction = CollectionUtils.disjunction(list1, list2);// [1,2,4]
        System.out.println(disjunction);

        DateTime dt = new DateTime();
        DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
        String str = fmt.print(dt);
        System.out.println(str);

        List<String> list = new LinkedList<>();

        Set<String> set1 = list.stream().map(s -> s + ",").collect(Collectors.toSet());
        if(set1 != null) {
            System.out.println("set is not null");
        }
        System.out.println(set1);

        List<Integer> result = list1.stream().filter(ele -> ele != 2).collect(Collectors.toList());
        System.out.println(result);
    }

    private static final String ISO_8601_COMPATIBLE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    private static SimpleDateFormat standardDateFormat;


    private static DateFormat getStandardDateFormat() {
        standardDateFormat = new SimpleDateFormat(ISO_8601_COMPATIBLE_DATE_FORMAT);
//        standardDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        return standardDateFormat;
    }
    private static List<String> printSubSequencesRecursively(String[] strs, int start, int end) {
        if (strs.length == 0) return new LinkedList<>();

        if (start == end) {
            List<String> set = new LinkedList<>();
            set.add(strs[start]);
            return set;
        }

        String first = strs[start];
        List<String> topLevelSubSeqsSet = new LinkedList<>();
        topLevelSubSeqsSet.add(first);

        List<String> subSeqsFromRemainingArray = printSubSequencesRecursively(strs, start + 1, end);
        topLevelSubSeqsSet.addAll(subSeqsFromRemainingArray);

        for (String subseq : subSeqsFromRemainingArray) {
            topLevelSubSeqsSet.add(first + "$"+subseq);
        }

        return topLevelSubSeqsSet;

    }

}
