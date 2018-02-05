package algorithms.crackingcodinginterviewbook._6sort__search_merge;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
    p.g.403 of Cracking Coding Interview book

    Sort Big File:
    Imagine you have a 20 GB file with one string per line.
    Explain how you would sort the file.

    Sorting a big file is called external sorting.

    When an interviewer gives a size limit of 20 GB, it should tell you something.
    In this case, it suggests that they don't want you to bring all the data into memory.

    So what do we do?
    We only bring part of the data into memory.

    We will divide the file into chunks, which are x MGs each, where x is the amount of memory we have available.
    Each chunk is sorted separately and then saved back to the file system.

    Once all the chunks are sorted, we merge the chunks, one by one. At the end, we have a fully sorted file.

    This algorithm is known as External Sort.


    http://www.ashishsharma.me/2011/08/external-merge-sort.html

 */
public class _6SortBigFile {
    public static void main(String[] args) throws IOException {
        File inputFile = new File("./MyJavaProject/src/algorithms/sortandsearch/binarysearchrelatedalgorithms/file.txt");
        System.out.println(inputFile.getCanonicalPath());
        File outputFile = new File("./MyJavaProject/src/algorithms/sortandsearch/binarysearchrelatedalgorithms/sortedfile.txt");

        /*byte[] bytes = new byte[1064];
        InputStream inputStream = new FileInputStream(inputFile);
        while(inputStream.read(bytes) != -1) {
            String str = new String(bytes);
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
        }*/

        Writer writer = new OutputStreamWriter(new FileOutputStream(outputFile));

        List<File> tempFiles = new ArrayList<>();

        //List<char[]> sortedCharArrays = new ArrayList<>();
        char[] buffer = new char[100];
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
        int count=0;
        while(bufferedReader.read(buffer) != -1) {
            Arrays.sort(buffer);
            //sortedCharArrays.add(buffer);

            /*{
                writer.write(buffer);
            }*/

            {
                File newtmpfile = File.createTempFile("sortInBatchTempFile"+count, "txt", new File("./MyJavaProject/src/algorithms/sortandsearch/binarysearchrelatedalgorithms/tempFiles"));
                count++;
                tempFiles.add(newtmpfile);
                Writer tempFileWriter = new OutputStreamWriter(new FileOutputStream(newtmpfile));
                tempFileWriter.write(buffer);
                tempFileWriter.close();
            }


        }

        bufferedReader.close();
        writer.close();

        /*for (File tempFile : tempFiles) {
            tempFile.delete();
        }*/

    }

    public static File sortAndSave(List<String> tmplist, Comparator<String> cmp) throws IOException  {
        Collections.sort(tmplist, cmp);  //
        File newtmpfile = File.createTempFile("sortInBatch", "flatfile");
        newtmpfile.deleteOnExit();
        BufferedWriter fbw = new BufferedWriter(new FileWriter(newtmpfile));
        try {
            for(String r : tmplist) {
                fbw.write(r);
                fbw.newLine();
            }
        } finally {
            fbw.close();
        }
        return newtmpfile;
    }

}
