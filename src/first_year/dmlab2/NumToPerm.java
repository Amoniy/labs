package first_year.dmlab2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class NumToPerm {
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

        long nextInt() {
            return Long.parseLong(next());
        }
    }

    public static void main(String[] args) throws Exception {
        String destinationFileName = "NumToPerm.out";
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("NumToPerm.out", true);
        FastScanner in = new FastScanner(new File("NumToPerm.in"));
        int n = (int) in.nextInt();
        long k = in.nextInt();
        long[] factorials = new long[n + 1];
        factorials[1] = 1;
        for (int i = 2; i < n + 1; i++) {
            factorials[i] = factorials[i - 1] * i;
        }
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            list.add(i);
        }
        String newanswer = "";
        HashSet<Integer> newset = new HashSet<>();

        for (int i = 1; i < n; i++) {
            int minchislo = (int) (k / factorials[n - i] + 1);
            k = k % factorials[n - i];
            for (int j = minchislo; j <= n; j++) {
                if (!newset.contains(list.get(j))) {
                    newset.add(list.get(j));
                    newanswer += list.get(j) + " ";
                    list.remove(j);
                    break;
                }
            }
        }
        for (int i = 1; i <= n; i++) {
            if (!newset.contains(i)) {
                newanswer += i;
                break;
            }
        }
        writer.write(newanswer);
        writer.flush();
        fileOutputStream.close();
    }
}
