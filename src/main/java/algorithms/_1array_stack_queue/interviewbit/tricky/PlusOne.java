package algorithms._1array_stack_queue.interviewbit.tricky;

/*
you add one to the given number. Number is given in the form of an array.
e.g. int[] A = {1,2,3}
Answer should be {1,2,4}

This is tricky. You need to ask questions to interviewer.

    what about {9,9,9}?
    answer should be {1,0,0,0} or {0,0,0}?
    Interviewer will say {1,0,0,0}.


    what about {0,1,2,3}?
    answer should be {0,1,2,4} or {1,2,4}?
    Interviewer will say {1,2,4}.


As you see in below code, I tried harder approach first.
taking string of input
e.g. int A[]={0,1,2,3}

    strOfInput = "0123"
    converted it to long =123  ------ I failed here. I assumed that array cannot have a number greater than long.
    added 1 to 123=124
    Without converting 124 to string, i wanted to store it in result array.
        to do that i tried 124/10 = 12.4 double value
                           new Double(12.4).logValue() = 12
                           12.4 - 12 = 0.4
                           .4 * 10 = 4
                           added 4 to last index of result array.

                           then
                           12/10 = 1.2 double value ------ I failed here because somehow it gave 1.19999999999
                           12/10 = 1 long value
                           1.2 - 1 = 0.19999999999 ----- I failed here too. I was expecting 0.2 (CAUTION FOR FUTURE)


I tried another approach

    strOfInput = "0123"
    converted it to long =123  ------ I failed here. I assumed that array cannot have a number greater than long.
    added 1 to 123=124
    converted 124 to string "124"
    converted string to charArray
    char[] plusOneChars = "124".toCharArray(); ['1','2','4']

    int B[] = new int[lengthOfInputNumber("123")+1];//4

    int j = B.length - 1;
    for (int i = plusOneChars.length - 1; i >= 0; i--) {
        B[j] = plusOneChars[i];  // I failed here. To convert char '1','2','4' to int, it took its ascii value. like 50,51,53 etc.
        j--;
    }

Finally I tried easiest and right approach as shown below.
 */
public class PlusOne {

    public static void main(String[] args) {
        PlusOne plusOne = new PlusOne();
        int[] A = {1, 2, 3};
        //int[] A = {9,9,9};
        //int[] A = {2, 5, 6, 8, 6, 1, 2, 4, 5};
        //int[] A = {0, 3, 7, 6, 4, 0, 5, 5, 5};
        //int[] A = {9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9};
        int[] result = plusOne.plusOne(A);
        for (int finalNum : result) {
            System.out.print(finalNum + ",");
        }

    }

    public int[] plusOne(int[] A) {
        if (A == null || A.length == 0) return A;

        int result[] = new int[A.length + 1];

        int valueToAdd = 1;

        // adding 1 to last element. if answer is > 9, then it means that result's last element should be 0 and 1 should be carry forwarded to second last element.
        // if answer is  <=0, then nothing special needs to be done.
        // and so on...
        // In this case, 999+1 will result in 1000. So, you need one additional apace in resulting array result.
        for (int i = A.length - 1; i >= 0; i--) {

            int addedValueResult = A[i] + valueToAdd;

            if (addedValueResult > 9) {
                result[i + 1] = 0;

            } else {
                result[i + 1] = addedValueResult;
                valueToAdd = 0;
            }
        }

        // taking care of 999+1=1000 case
        if (valueToAdd == 1) {
            result[0] = 1;
            return result;
        }

        // as per requirement, result array should not have leading 0s, so truncating them by creating a new array from it.
        // e.g. 00000999+1 should return 1000 and not 00001000
        int numberOfLeading0s = 0;
        for (int i : result) {
            if (i == 0) numberOfLeading0s++;
            else break;
        }

        int resultWithoutLeading0s[] = new int[result.length - numberOfLeading0s];

        int j = result.length - 1;
        for (int i = resultWithoutLeading0s.length - 1; i >= 0; i--) {
            resultWithoutLeading0s[i] = result[j];
            j--;
        }
        return resultWithoutLeading0s;

        /*if(valueToAdd == 1) {
            result[0] =1;
            return result;
        } else {
            int resultWithoutLeading0s[] = new int[result.length-1];
            for (int i = 1; i < result.length; i++) {
                resultWithoutLeading0s[i-1]=result[i];
            }
            return resultWithoutLeading0s;
        }*/



/*

        StringBuilder sb = new StringBuilder();
        for (int num : A) {
            sb.append(num);
        }
        Long inputNumber = Long.valueOf(sb.toString());//999

        long plusOneResult = inputNumber + 1;//1000

        int lengthOfInputNumber = inputNumber.toString().length();//3

        int result[] = new int[lengthOfInputNumber];

        String plusOneString = String.valueOf(plusOneResult);

        char[] plusOneChars = plusOneString.toCharArray();

        int j = plusOneChars.length - 1;
        for (int i = result.length - 1; i >= 0; i--) {
            result[i] = Integer.valueOf("" + plusOneChars[j--]);
        }

        return result;
        */

        /*StringBuilder sb = new StringBuilder();
        for (int num : A) {
            sb.append(num);
        }
        Long aLong = Long.valueOf(sb.toString());//999

        long plusOneResult = aLong + 1;//1000

        int lengthOfInputNumber = aLong.toString().length();//3

        double divisionDoubleResult = plusOneResult / Math.pow(10, lengthOfInputNumber);//1

        long divisionLongResult = Math.round(new Double(divisionDoubleResult));//1

        double finalResult = (divisionDoubleResult - divisionLongResult) * Math.pow(10, lengthOfInputNumber);

        double num = finalResult;


        int result[] = new int[lengthOfInputNumber];
        int startIndex = result.length - 1;

        while (!(num < 1)) {
            double doubleNum = num / 10;// 12.4

            //long longNum = new Double(doubleNum).longValue();//12 ---- 12/10=1.99999999999
            long longNum = (long)doubleNum;

            //long arrayElement = new Double((doubleNum - longNum) * 10).longValue();//4
            long arrayElement = (long)((doubleNum - longNum) * 10);

            result[startIndex] = new Long(arrayElement).intValue();
            startIndex--;

            num = longNum;// 12

        }

        return result;*/
    }
}
