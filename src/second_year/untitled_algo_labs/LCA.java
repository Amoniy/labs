package second_year.untitled_algo_labs;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class LCA {

    static final int INFINITY = Integer.MAX_VALUE;
    static boolean[] used;
    static ArrayList<Integer>[] paths;
    static int[] indexes;
    static int[] depths;
    static int[] vertices;
    static int index = 0;

    static void dfs(int vertice, int depth) {
        used[vertice] = true;
        indexes[vertice] = index;
        depths[index] = (depth);
        vertices[index] = (vertice);
        index++; // enter to vertice
        for (int i = 0; i < paths[vertice].size(); i++) {
            if (!used[paths[vertice].get(i)]) {
                dfs(paths[vertice].get(i), depth + 1);
                depths[index] = (depth);
                vertices[index] = (vertice);
                index++; // exit from son
            }
        }
    }

    static int min(int v, int l, int r, int neededLeft, int neededRight, int[] tree) {
        int x = INFINITY;
        if (l == r && l >= neededLeft && r <= neededRight) {
            return tree[v];
        }

        if (neededLeft <= l && neededRight >= r) {
            return tree[v];
        }

        if (neededLeft > r || neededRight < l) {
            return INFINITY;
        }

        if (neededLeft > l || neededRight < r) {
            int m = (l + r) / 2;
            return minimum(min(v * 2 + 1, l, m, neededLeft, neededRight, tree), min(v * 2 + 2, m + 1, r, neededLeft, neededRight, tree));
        }
        return x;
    }

    static int[] build(int[] array, int v, int l, int r, int[] ret) {
        if (l == r)
            ret[v] = array[l];
        else {
            int m = (l + r) / 2;
            ret = build(array, v * 2 + 1, l, m, ret);
            ret = build(array, v * 2 + 2, m + 1, r, ret);
            ret[v] = minimum(ret[v * 2 + 1], ret[v * 2 + 2]);
        }
        return ret;
    }

    static int minimum(int f, int s) {
        if (f == INFINITY) {
            return s;
        }
        if (s == INFINITY) {
            return f;
        }
        if (depths[f] < depths[s]) {
            return f;
        }
        return s;
    }

    public static void main(String[] args) throws Exception {
        String sourceFileName = "lca.in";
        String destinationFileName = "lca.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        String split = "[ ]+";
        FileWriter writer = new FileWriter("lca.out", true);
        int n = Integer.parseInt(br.readLine());
        paths = new ArrayList[n + 1];
        for (int i = 1; i < n + 1; i++) {
            paths[i] = new ArrayList<Integer>();
        }
        used = new boolean[n + 1];
        indexes = new int[n + 1];
        depths = new int[2 * n - 1];
        vertices = new int[2 * n - 1];
        for (int i = 2; i < n + 1; i++) {
            int parent = Integer.parseInt(br.readLine());
            paths[parent].add(i);
        }

        //build tree
        dfs(1, 0);
        int size = depths.length;
        int x = 1;
        for (int i = 1; i < size * 2; i *= 2) {
            x = i;
        }
        int[] array = new int[x];
        for (int i = 0; i < size; i++) {
            array[i] = i;
        }
        for (int i = size; i < x; i++) {
            array[i] = INFINITY;
        }
        int[] ret = new int[2 * x - 1];
        int[] tree = build(array, 0, 0, x - 1, ret);

        int m = Integer.parseInt(br.readLine());
        for (int i = 0; i < m; i++) {
            String[] leftRight = br.readLine().split(split);
            int l = Integer.parseInt(leftRight[0]);
            int r = Integer.parseInt(leftRight[1]);
            if (indexes[l] > indexes[r]) {
                int temp = l;
                l = r;
                r = temp;
            }
            l = indexes[l];
            r = indexes[r];
            writer.write(String.valueOf(vertices[(min(0, x - 1, 2 * x - 2, l + x - 1, r + x - 1, tree))]) + "\n");
        }

        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}