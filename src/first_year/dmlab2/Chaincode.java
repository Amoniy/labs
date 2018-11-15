package first_year.dmlab2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Chaincode {
    static Set<String> set = new HashSet<String>();

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(File f) {
            try {
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }

    public static int[] getnew(int[] array) {
        for (int i = 1; i < array.length; i++) {
            array[i - 1] = array[i];
        }
        array[array.length - 1] = 1;
        String temp = "";
        for (int i = 0; i < array.length - 1; i++) {
            temp += array[i];
        }
        String attemptone = temp + "1";
        String attemptzero = temp + "0";
        if (set.contains(attemptone)) {
            array[array.length - 1] = 0;
            set.add(attemptzero);
        } else {
            array[array.length - 1] = 1;
            set.add(attemptone);
        }
        return array;
    }

    public static void main(String[] args) throws Exception {
        String destinationFileName = "Chaincode.out";
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("Chaincode.out", true);
        FastScanner in = new FastScanner(new File("Chaincode.in"));
        int n = in.nextInt();
        int[] powers = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072};
        int[] array = new int[n];
        String temp = "";
        for (int i = 0; i < n; i++) {
            array[i] = 0;
            temp += "0";
            writer.write("0");
        }
        set.add(temp);
        writer.write("\n");
        for (int i = 1; i < powers[n]; i++) {
            array = getnew(array);
            for (int j = 0; j < n; j++) {
                writer.write("" + array[j]);
            }
            writer.write("\n");
        }


        writer.flush();
        fileOutputStream.close();
    }
}
