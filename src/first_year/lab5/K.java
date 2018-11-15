package first_year.lab5;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class K {
    static int[][] w;
    static int[] used;
    static int k;
    static int[] p;
    static int C = (int) Math.pow(10, 9);

    static int[] topSort(int n) {
        p = new int[n + 1];
        used = new int[n + 1];
        k = 1;
        for (int i = 1; i <= n; i++)
            used[i] = 0;
        for (int i = 1; i <= n; i++) {
            if (used[i] == 0) {
                dfs(i);
            }
        }

        int[] p2 = new int[n + 1];
        for (int i = n; i >= 1; i--)
            p2[n - i + 1] = p[i];
        return p2;
    }

    static void dfs(int u) {
        used[u] = 1;
        for (int i = 1; i <= used.length - 1; i++) {
            if (w[u][i] != C && used[i] == 0) {
                dfs(i);
            }
        }
        p[k] = u;
        k++;
    }

    public static void main(String[] args) throws IOException {
        String sourceFileName = "Test.in";
        String destinationFileName = "Test.out";
        Scanner sc = new Scanner(new File(sourceFileName));
        PrintWriter pr = new PrintWriter(destinationFileName);

        int n = sc.nextInt();
        int m = sc.nextInt();
        int s = sc.nextInt();
        int t = sc.nextInt();
        w = new int[n + 1][n + 1];


        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                w[i][j] = C;
            }
        }
        for (int i = 1; i <= m; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int we = sc.nextInt();
            w[a][b] = we;
        }

        int[] d = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            d[i] = C;
        }

        d[s] = 0;

        p = topSort(n);

        int[] weights = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            weights[i] = C;
        }

        boolean marker = false;
        int start = -1;
        for (int i = 1; i <= n; i++) {
            if (p[i] == t) {//нашли конец
                marker = true;
                break;
            } else if (p[i] == s) {//нашли старт
                start = i;
                weights[s] = 0;
                break;
            }
        }
        if (!marker) {//если первым нашли старт
            for (int i = start; i <= n; i++) {
                if (weights[p[i]] != C) {
                    for (int j = 1; j <= n; j++) {
                        if (w[p[i]][j] != C) {
                            weights[j] = Math.min(weights[j], weights[p[i]] + w[p[i]][j]);
                        }
                    }
                }
            }
            if (weights[t] != C) {
                pr.println(weights[t]);
            } else {
                pr.println("Unreachable");
            }
        } else {
            pr.println("Unreachable");
        }

        for (int i = 1; i <= n; i++)
            System.out.println(p[i]);
        pr.close();
    }
}