package first_year.lab5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class SpanTree {
    static void mergeSortIterative(int[][] a) {
        for (int i = 1; i < a[0].length; i *= 2) {
            for (int j = 0; j < a[0].length - i; j += 2 * i) {
                merge(a, j, j + i, min(j + 2 * i, a[0].length));
            }
        }
    }

    static int min(int a, int b) {
        return (a <= b) ? a : b;
    }

    static void merge(int[][] a, int left, int mid, int right) {
        int it1 = 0;
        int it2 = 0;
        int[][] result = new int[3][right - left];
        while (left + it1 < mid && mid + it2 < right) {
            if (a[2][left + it1] < a[2][mid + it2]) {
                result[0][it1 + it2] = a[0][left + it1];
                result[1][it1 + it2] = a[1][left + it1];
                result[2][it1 + it2] = a[2][left + it1];
                it1 += 1;
            } else {
                result[0][it1 + it2] = a[0][mid + it2];
                result[1][it1 + it2] = a[1][mid + it2];
                result[2][it1 + it2] = a[2][mid + it2];
                it2 += 1;
            }
        }
        while (left + it1 < mid) {
            result[0][it1 + it2] = a[0][left + it1];
            result[1][it1 + it2] = a[1][left + it1];
            result[2][it1 + it2] = a[2][left + it1];
            it1 += 1;
        }
        while (mid + it2 < right) {
            result[0][it1 + it2] = a[0][mid + it2];
            result[1][it1 + it2] = a[1][mid + it2];
            result[2][it1 + it2] = a[2][mid + it2];
            it2 += 1;
        }
        for (int i = 0; i < it1 + it2; i++) {
            a[0][left + i] = result[0][i];
            a[1][left + i] = result[1][i];
            a[2][left + i] = result[2][i];
        }
    }

    static void makeSet(int[] parent, int[] size, int v) {
        parent[v] = v;
        size[v] = 1;
    }

    static int findSet(int[] parent, int[] size, int v) {
        if (v == parent[v]) {
            return v;
        }
        return parent[v] = findSet(parent, size, parent[v]);
    }

    static void unionSets(int[] parent, int[] size, int a, int b) {
        a = findSet(parent, size, a);
        b = findSet(parent, size, b);
        if (a != b) {
            if (size[a] < size[b]) {
                parent[a] = b;
                size[b] += size[a];
            } else {
                parent[b] = a;
                size[a] += size[b];
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String sourceFileName = "Test.in";
        String destinationFileName = "Test.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        String split = "[ ]+";
        FileWriter writer = new FileWriter("Test.out", true);
        String[] input = br.readLine().split(split);
        int n = Integer.parseInt(input[0]);
        int m = Integer.parseInt(input[1]);
        int countOfComponents = n;
        int totalCost = 0;
        //int[] verticesComponents = new int[n + 1];
        int[] size = new int[n + 1];
        int[] parent = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            //verticesComponents[i] = i;
            makeSet(parent, size, i);
        }
        int[][] map = new int[3][m];
        for (int i = 0; i < m; i++) {
            input = br.readLine().split(split);
            map[0][i] = Integer.parseInt(input[0]);//a
            map[1][i] = Integer.parseInt(input[1]);//b
            map[2][i] = Integer.parseInt(input[2]);//w
        }

        mergeSortIterative(map);

        for (int i = 0; i < m; i++) {
            if (findSet(parent, size, map[0][i]) != findSet(parent, size, map[1][i])) {
                countOfComponents--;
                unionSets(parent, size, map[0][i], map[1][i]);
                totalCost += map[2][i];
            }
            if (countOfComponents == 0) {
                break;
            }
        }
        writer.write(totalCost + "");
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
