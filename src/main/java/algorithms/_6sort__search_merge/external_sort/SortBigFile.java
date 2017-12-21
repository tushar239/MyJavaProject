package algorithms._6sort__search_merge.external_sort;

// p.g.403 of Cracking Coding Interview book

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
    Sorting a big file is called external sorting.

    We can use external deleteRootAndMergeItsLeftAndRight sort.
    http://www.ashishsharma.me/2011/08/external-merge-sort.html

    As shown below we need to read small chunks from a big file in char[] and sort them and create temp files for each sorted char[].
    After that you can deleteRootAndMergeItsLeftAndRight these sorted files using mergeSortFiles algorithm.

 */
public class SortBigFile {
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
