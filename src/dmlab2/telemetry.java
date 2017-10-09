package dmlab2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class telemetry {
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
        String destinationFileName = "telemetry.out";
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("telemetry.out", true);
        FastScanner in = new FastScanner(new File("telemetry.in"));
        int n = in.nextInt();
        int k = in.nextInt();
        int[] powers = new int[n + 1];
        int q = 1;
        for (int i = 0; i < n + 1; i++) {
            powers[i] = q;
            q *= k;
        }
        int[][] array = new int[powers[n]][n];
        for (int i = 0; i < n; i++) {
            for (int temp = 0; temp < k; temp++) {
                for (int z = 0; z < powers[i]; z++) {
                    array[z + powers[i] * temp][i] = temp;
                }
            }
            if (i < (n - 1)) {
                for (int temp = 0; temp < k; temp++) {
                    for (int z = 0; z < powers[i]; z++) {
                        array[z + powers[i + 1] + powers[i] * temp][i] = k - 1 - temp;
                    }
                }
            }
        }
        for (int i = 0; i < n - 1; i++) {
            for (int j = powers[i + 1] * 2; j < powers[n]; j++) {
                array[j][i] = array[j - powers[i + 1] * 2][i];

            }
        }


        for (int i = 0; i < powers[n]; i++) {
            for (int j = 0; j < n; j++) {
                writer.write("" + array[i][j]);
            }
            writer.write("\n");
        }


        writer.flush();
        fileOutputStream.close();
    }
}
