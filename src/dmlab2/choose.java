package dmlab2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class choose {
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

    //C:\java\projects\src\labs\dmlab2\test
    public static void main(String[] args) throws Exception {
        String destinationFileName = "C:\\java\\projects\\src\\labs\\dmlab2\\test.out";
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("C:\\java\\projects\\src\\labs\\dmlab2\\test.out", true);
        FastScanner in = new FastScanner(new File("C:\\java\\projects\\src\\labs\\dmlab2\\test.in"));
        int n = in.nextInt();
        int k = in.nextInt();
        int[] array = new int[k + 1];

        for (int i = 0; i < k; i++) {
            array[i] = i + 1;
            writer.write("" + (i + 1) + " ");
        }
        writer.write("\n");

        boolean marker = true;
        while (marker) {
            array[k] = n + 1;
            for (int i = k - 1; i >= -1; i--) {
                if (i == -1) {
                    marker = false;
                    break;
                }
                if ((array[i + 1] - array[i]) >= 2) {
                    array[i]++;
                    for (int j = i + 1; j < k; j++) {
                        array[j] = array[j - 1] + 1;
                    }
                    break;
                }

            }
            array[k] = n + 1;
            if (!marker) {
                break;
            }
            for (int i = 0; i < k; i++) {
                writer.write("" + array[i] + " ");
            }
            array[k] = n + 1;
            writer.write("\n");
        }


        writer.flush();
        fileOutputStream.close();
    }
}
