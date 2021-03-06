package first_year.dmlab2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class AllVectors {
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

    public static void main(String[] args) throws Exception {
        String destinationFileName = "AllVectors.out";
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("AllVectors.out", true);
        FastScanner in = new FastScanner(new File("AllVectors.in"));
        int v = in.nextInt();
        int[] array = new int[v];
        int[] powers = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072};
        for (int i = 0; i < v; i++) {
            array[i] = 0;
        }
        for (int q = 0; q < v; q++) {
            writer.write("" + array[q]);
        }
        writer.write("\n");
        for (int i = 1; i < powers[v]; i++) {
            boolean k = true;
            for (int j = v - 1; j >= 0; j--) {
                if (k) {
                    if (array[j] == 0) {
                        array[j] = 1;
                        k = false;
                    } else {
                        array[j] = 0;
                    }
                } else {
                    array[j] = array[j];
                }
            }
            for (int q = 0; q < v; q++) {
                writer.write("" + array[q]);
            }
            writer.write("\n");
        }


        writer.flush();
        fileOutputStream.close();
    }
}
