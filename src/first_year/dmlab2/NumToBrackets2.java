package first_year.dmlab2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;
import java.util.StringTokenizer;

public class NumToBrackets2 {
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

    public static void main(String[] args) throws Exception {
        String destinationFileName = "Test.out";
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("Test.out", true);
        FastScanner in = new FastScanner(new File("Test.in"));
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

        long[] powers = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576, 2097152, 4194304};
        int depth = 0;
        Stack<Character> stack = new Stack<>();

        long trans = 0;
        for (int i = 1; i <= 2 * n; i++) {
            //check for (
            boolean skip = false;
            if (2 * n - i - depth - 1 >= 0) {
                trans = catalan[2 * n - i + 1][depth + 2] * powers[(2 * n - i - depth - 1) / 2];//i->2n-i+1
            } else {
                trans = 0;
            }
            if (trans >= k) {// (
                ans += "(";
                stack.push('(');
                depth++;
                skip = true;
            }

            if (!skip) {// not (
                k -= trans;

                if ((stack.size() > 0) && (stack.peek() == '(') && (depth - 1 >= 0)) {
                    trans = catalan[2 * n - i + 1][depth] * powers[(2 * n - i - depth + 1) / 2];
                } else {
                    trans = 0;
                }
                if (trans >= k) {
                    ans += ")";
                    depth--;
                    stack.pop();
                    skip = true;
                }


                if (!skip) {// not )
                    k -= trans;
                    if (2 * n - i - depth - 1 >= 0) {
                        trans = catalan[2 * n - i + 1][depth + 2] * powers[(2 * n - i - depth - 1) / 2];//i->2n-i+1
                    } else {
                        trans = 0;
                    }
                    if (trans >= k) {
                        ans += '[';
                        stack.push('[');
                        depth++;
                        skip = true;
                    }

                    if (!skip) {// not [
                        k -= trans;
                        ans += "]";
                        stack.pop();
                        depth--;
                    }

                }
            }
        }
        writer.write(ans);
        writer.flush();
        fileOutputStream.close();
    }
}
