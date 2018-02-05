package algorithms.crackingcodinginterviewbook._6sort__search_merge;

/*
    p.g. 402 of Cracking Coding Interview book

    Given a sorted array of strings that is interspersed with empty strings, write an algorithm to find the location of a given string.
    array=["at","","","","ball","","","car","","","dad","',""];

    As soon as think of sorted array, Binary Search should come to mind.
    Here, the only caveat is array has empty strings, so if found middle element is "", then move middle to closet left or right element in array.

 */
public class _5SparseSearch {

    public static void main(String[] args) {
        String[] strs = {"at", "", "", "", "ball", "", "", "car", "", "", "dad", ""};

        int index = binarySearch(strs, 0, strs.length - 1, "ball");
        System.out.println(index);

        index = binarySearch(strs, 0, strs.length - 1, "sadfs");
        System.out.println(index);
    }

    private static int binarySearch(String[] strs, int start, int end, String strToFind) {
        if (strs == null || strs.length == 0) return -1;
        if (start > end) return -1;

        int mid = (start + end) / 2;

        System.out.println("original mid: " + mid);

        // if strs[mid] is "", then move mid to closest left/right non-blank index
        if (strs[mid].equals("")) {
            int leftOfMid = mid - 1;
            int rightOfMid = mid + 1;

            while (leftOfMid >= start && rightOfMid <= end) {
                if (!strs[leftOfMid].equals("")) {
                    mid = leftOfMid;
                    break;
                }
                if (!strs[rightOfMid].equals("")) {
                    mid = rightOfMid;
                    break;
                }
                leftOfMid--;
                rightOfMid++;
            }

            if(strs[mid].equals("")) {
                while (leftOfMid >= start) {
                    if (!strs[leftOfMid].equals("")) {
                        mid = leftOfMid;
                        break;
                    }
                    leftOfMid--;
                }

                while (rightOfMid <= end) {
                    if (!strs[rightOfMid].equals("")) {
                        mid = rightOfMid;
                        break;
                    }
                    rightOfMid++;
                }
            }

            System.out.println("updated mid: " + mid);
        }

        // Actual Binary Search starts from here
        if (strs[mid].equals(strToFind)) {
            return mid;
        }

        if (strToFind.compareTo(strs[mid]) == -1) {
            return binarySearch(strs, start, mid - 1, strToFind);
        }
        return binarySearch(strs, mid + 1, end, strToFind);
    }
}
