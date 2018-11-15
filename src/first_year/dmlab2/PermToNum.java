package first_year.dmlab2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.StringTokenizer;

public class PermToNum {
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
        String destinationFileName = "PermToNum.out";
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("PermToNum.out", true);
        FastScanner in = new FastScanner(new File("PermToNum.in"));
        int n = in.nextInt();
        int[] perm = new int[n];
        for (int i = 0; i < n; i++) {
            perm[i] = in.nextInt();
        }

        long[] factorials = new long[19];
        factorials[0] = 1;
        factorials[1] = 1;
        for (int i = 2; i < 19; i++) {
            factorials[i] = factorials[i - 1] * i;
        }
        HashSet<Integer> newset = new HashSet<>();

        long ans = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < perm[i]; j++) {
                if (!newset.contains(j)) {
                    ans += factorials[n - i - 1];
                }
            }
            newset.add(perm[i]);
        }

        writer.write(ans + "");
        writer.flush();
        fileOutputStream.close();
    }
}
