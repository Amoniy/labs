package dmlab2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class gray {
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
        String destinationFileName = "gray.out";
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("gray.out", true);
        FastScanner in = new FastScanner(new File("gray.in"));
        int n = in.nextInt();

        int v = n;
        int[] powers = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072};
        int[][] array = new int[powers[v]][v];
        for (int i = 0; i < v; i++) {
            for (int j = 0; j < powers[v - i - 1]; j++) {
                array[j][i] = 0;
            }
            for (int j = powers[v - i - 1]; j < powers[v - i]; j++) {
                array[j][i] = 1;
            }
            int k = 1;
            for (int j = powers[v - i]; j < powers[v]; j++) {
                array[j][i] = array[j - k][i];
                k++;
                k++;
                k = k % (powers[v - i + 1]);
            }
        }

        for (int i = 0; i < powers[v]; i++) {
            for (int j = 0; j < v; j++) {
                writer.write("" + array[i][j]);
            }
            writer.write("\n");
        }


        writer.flush();
        fileOutputStream.close();
    }
}