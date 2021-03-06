package first_year.dmlab2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class SuperSubsets {
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
        String destinationFileName = "Test.out";
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("Test.out", true);
        FastScanner in = new FastScanner(new File("Test.in"));
        int n = in.nextInt();
        int[] powers = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072};
        String[] answer = new String[powers[n]];
        answer[powers[n] - 1] = "" + n;
        int num = n - 1;
        for (int i = powers[n] - 2; i > 0; i--) {
            for (int j = 0; j < powers[n - num] - 1/*так надо*/; j++) {
                answer[i] = "" + num + " " + answer[powers[n] - 1 - j];
                i--;
            }
            answer[i] = "" + num;

            num--;
        }
        writer.write("\n");
        for (int i = 1; i < powers[n]; i++) {
            writer.write(answer[i] + "\n");
        }
        writer.flush();
        fileOutputStream.close();
    }
}
