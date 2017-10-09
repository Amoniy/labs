package dmlab2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class num2choose {
    static long[][] C = new long[31][31];

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

    public static ArrayList<Integer> num2choose(int n, int k, long m) {
        ArrayList<Integer> choose = new ArrayList<>();
        int next = 1;
        while (k > 0) {
            if (m < C[n - 1][k - 1]) {
                choose.add(next);
                k = k - 1;
            } else {
                m -= C[n - 1][k - 1];
            }
            n = n - 1;
            next = next + 1;
        }
        return choose;
    }

    //C:\java\projects\src\labs\dmlab2\test
    public static void main(String[] args) throws Exception {
        String destinationFileName = "num2choose.out";
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("num2choose.out", true);
        FastScanner in = new FastScanner(new File("num2choose.in"));
        int n = (int) in.nextInt();
        int k = (int) in.nextInt();
        long m = in.nextInt();
        String answer = "";
        for (int i = 0; i < 31; i++) {
            C[i][0] = 1;
        }
        for (int i = 1; i < 31; i++) {
            for (int j = 1; j <= i; j++) {
                int temp = 1;
                int q2 = 2;
                if (i - j > j) {
                    for (int q = i - j + 1; q <= i; q++) {
                        temp *= q;
                        if (q2 <= j && temp % q2 == 0) {
                            temp /= q2;
                            q2++;
                        }
                    }
                } else {
                    for (int q = j + 1; q <= i; q++) {
                        temp *= q;
                        if (q2 <= i - j && temp % q2 == 0) {
                            temp /= q2;
                            q2++;
                        }
                    }
                }
                C[i][j] = temp;
            }
        }
        C[30][15] = 155117520;
        ArrayList<Integer> list = num2choose(n, k, m);
        for (int i = 0; i < k; i++) {
            answer += list.get(i) + " ";
        }
        writer.write(answer);
        writer.flush();
        fileOutputStream.close();
    }
}
