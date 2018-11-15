package first_year.dmlab2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Subsets {
    static void mergeSortIterative(ArrayList<Integer> a) {
        for (int i = 1; i < a.size(); i *= 2) {
            for (int j = 0; j < a.size() - i; j += 2 * i) {
                merge(a, j, j + i, min(j + 2 * i, a.size()));
            }
        }
    }

    static int min(int a, int b) {
        return (a <= b) ? a : b;
    }

    static void merge(ArrayList<Integer> a, int left, int mid, int right) {
        int it1 = 0;
        int it2 = 0;
        int[] result = new int[right - left];
        while (left + it1 < mid && mid + it2 < right) {
            if (a.get(left + it1) < a.get(mid + it2)) {
                result[it1 + it2] = a.get(left + it1);
                it1 += 1;
            } else {
                result[it1 + it2] = a.get(mid + it2);
                it2 += 1;
            }
        }
        while (left + it1 < mid) {
            result[it1 + it2] = a.get(left + it1);
            it1 += 1;
        }
        while (mid + it2 < right) {
            result[it1 + it2] = a.get(mid + it2);
            it2 += 1;
        }
        for (int i = 0; i < it1 + it2; i++) {
            a.set(left + i, result[i]);
        }
    }

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

    public static ArrayList<ArrayList<Integer>> nextSetPartition(ArrayList<ArrayList<Integer>> a) {
        ArrayList<Integer> used = new ArrayList<>();
        boolean fl = false;
        for (int i = a.size() - 1; i >= 0; i--) {
            if ((used.size() != 0) && (used.get(used.size() - 1) > a.get(i).get(a.get(i).size() - 1))) {
                a.get(i).add(used.get(used.size() - 1));
                used.remove(used.size() - 1);
                break;
            }
            int j;
            for (j = a.get(i).size() - 1; j >= 0; j--) {
                if ((used.size() != 0) && (j != 0) && (used.get(used.size() - 1) > a.get(i).get(j))) {
                    a.get(i).set(j, used.get(used.size() - 1));
                    fl = true;
                    break;
                }
            }
            if (fl) break;
            used.add(a.get(i).get(j));
            a.get(j).remove(j);
        }
        mergeSortIterative(used);
        for (int i = 0; i < used.size() - 1; i++) {
            a.get(0).add((used.get(i)));
        }
        return a;
    }


    public static void main(String[] args) throws Exception {
        String destinationFileName = "Test.out";
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("Test.out", true);
        FastScanner in = new FastScanner(new File("Test.in"));
        int n = in.nextInt();
        int[] powers = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072};
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        ArrayList<Integer> used = new ArrayList<>();
        list.add(0, new ArrayList<Integer>());
        list.add(0, new ArrayList<Integer>());
        for (int i = 1; i <= n; i++) {
            list.get(1).add(i);
        }
        for (int i = 0; i < powers[n] - 1; i++) {
            nextSetPartition(list);
        }
        writer.flush();
        fileOutputStream.close();
    }
}
