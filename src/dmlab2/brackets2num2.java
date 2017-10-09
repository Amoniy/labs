package dmlab2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;
import java.util.StringTokenizer;

public class brackets2num2 {
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
        String destinationFileName = "brackets2num2.out";
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("brackets2num2.out", true);
        FastScanner in = new FastScanner(new File("brackets2num2.in"));
        int n = 20;
        char[] brackets = in.next().toCharArray();
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
        long[] powers = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576, 2097152, 4194304};
        int count = 0;
        n = brackets.length / 2;
        long k = 0;
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < 2 * n - 1; i++) {
            if (count == 2 * n - i) {
                break;
            }
            if (brackets[i] == '(') {
                count++;
                stack.push('(');
            } else if (brackets[i] == ')') {
                k += catalan[2 * n - i][count + 1 + 1] * powers[(2 * n - i - 1 - count - 1) / 2];
                count--;
                stack.pop();
            } else if (brackets[i] == '[') {
                k += catalan[2 * n - i][count + 1 + 1] * powers[(2 * n - i - 1 - count - 1) / 2];
                if (stack.size() > 0) {
                    if (stack.peek() == '(') {
                        k += catalan[2 * n - i][count - 1 + 1] * powers[(2 * n - i - 1 - count + 1) / 2];
                    }
                }
                stack.push('[');
                count++;
            } else {
                k += catalan[2 * n - i][count + 1 + 1] * powers[(2 * n - i - 1 - count - 1) / 2];
                if (stack.size() > 0) {
                    if (stack.peek() == '(') {
                        k += catalan[2 * n - i][count - 1 + 1] * powers[(2 * n - i - 1 - count + 1) / 2];
                    }
                }
                k += catalan[2 * n - i][count + 1 + 1] * powers[(2 * n - i - 1 - count - 1) / 2];
                count--;
                stack.pop();
            }
        }
        writer.write("" + k);
        writer.flush();
        fileOutputStream.close();
    }
}
