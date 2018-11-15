package first_year.dmlab2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class AntiGray {
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
        String destinationFileName = "AntiGray.out";
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("AntiGray.out", true);
        FastScanner in = new FastScanner(new File("AntiGray.in"));
        int n = in.nextInt();
        int[] powers = {1, 3, 9, 27, 81, 243, 729, 2187, 6561, 19683, 59049, 177147};
        int[][] array = new int[powers[n]][n];
        for (int k = 0; k < powers[n - 1]; k++) {//[3*k]
            int t = k;
            for (int i = n - 1; i >= 0; i--) {
                array[3 * k][i] = t % 3;
                t /= 3;
            }
        }
        for (int i = 1; i < powers[n]; i += 3) {
            for (int j = 0; j < n; j++) {
                array[i][j] = (array[i - 1][j] + 1) % 3;
                array[i + 1][j] = (array[i][j] + 1) % 3;
            }
        }


        for (int j = 0; j < powers[n]; j++) {
            for (int i = 0; i < n; i++) {
                writer.write("" + array[j][i]);
            }
            writer.write("\n");
        }


        writer.flush();
        fileOutputStream.close();
    }
}
