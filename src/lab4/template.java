package lab4;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class template {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        char[] first = br.readLine().toCharArray();
        char[] second = br.readLine().toCharArray();
        boolean[][] d = new boolean[first.length + 1][second.length + 1];
        d[0][0] = true;
        for (int i = 1; i <= first.length; i++) {
            for (int j = 0; j <= second.length; j++) {
                if (j > 0) {
                    if (first[i - 1] == '*') {
                        d[i][j] = (d[i - 1][j] || d[i][j - 1]);
                    } else if (first[i - 1] == '?') {
                        d[i][j] = d[i - 1][j - 1];
                    } else {
                        d[i][j] = (d[i - 1][j - 1] && (first[i - 1] == second[j - 1]));
                    }
                } else {
                    if (first[i - 1] == '*') {
                        d[i][j] = d[i - 1][j];
                    }
                }
            }
        }
        if (d[first.length][second.length]) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}