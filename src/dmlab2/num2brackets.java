package dmlab2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class num2brackets {
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

    public static int[] generatenextbracket(int[] brackets) {
        int opens = 0;
        int closes = 0;
        for (int i = brackets.length - 1; i >= 0; i--) {
            if (brackets[i] == 1) {
                opens++;
                if (closes > opens) {
                    break;
                }
            } else {
                closes++;
            }
        }
        int statcloses = closes;
        int statopens = opens;
        brackets[brackets.length - statcloses - statopens] = -1;
        for (int i = brackets.length - statcloses - statopens + 1; i < brackets.length; i++) {
            if (opens > 0) {
                brackets[i] = 1;
                opens--;
            } else {
                brackets[i] = -1;
            }
        }
        return brackets;
    }

    //C:\java\projects\src\labs\dmlab2\test
    public static void main(String[] args) throws Exception {
        String destinationFileName = "num2brackets.out";
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("num2brackets.out", true);
        FastScanner in = new FastScanner(new File("num2brackets.in"));
        int n = (int) in.nextInt();
        long k = in.nextInt();
        k++;
        String ans = "";
        long[][] catalan = new long[2 * n + 3][2 * n + 3];
        for (int i = 0; i < 2 * n + 3; i++) {
            catalan[0][i] = 0;
            catalan[i][0] = 0;
            catalan[i][2 * n + 2] = 0;
        }
        catalan[1][1] = 1;
        for (int i = 2; i < 2 * n + 3; i++) {
            for (int j = 1; j < 2 * n + 2; j++) {
                catalan[i][j] = catalan[i - 1][j - 1] + catalan[i - 1][j + 1];
            }
        }
        int count = 1;
        for (int i = 2; i < 2 * n + 1; i++) {
            if (catalan[2 * n - i + 2][count + 1] >= k) {
                count++;
                ans += "(";
            } else {
                k -= catalan[2 * n - i + 2][count + 1];
                count--;
                ans += ")";
            }
        }
        ans += ")";
        writer.write(ans);
        writer.flush();
        fileOutputStream.close();
    }
}
