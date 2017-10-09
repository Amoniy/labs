package lab3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

public class taxibus {
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

    static void mergeSortIterative(int[] a) {
        for (int i = 1; i < a.length; i *= 2) {
            for (int j = 0; j < a.length - i; j += 2 * i) {
                merge(a, j, j + i, min(j + 2 * i, a.length));
            }
        }
    }

    static int min(int a, int b) {
        return (a <= b) ? a : b;
    }

    static void merge(int[] a, int left, int mid, int right) {
        int it1 = 0;
        int it2 = 0;
        int[] result = new int[right - left];
        while (left + it1 < mid && mid + it2 < right) {
            if (a[left + it1] < a[mid + it2]) {
                result[it1 + it2] = a[left + it1];
                it1 += 1;
            } else {
                result[it1 + it2] = a[mid + it2];
                it2 += 1;
            }
        }
        while (left + it1 < mid) {
            result[it1 + it2] = a[left + it1];
            it1 += 1;
        }
        while (mid + it2 < right) {
            result[it1 + it2] = a[mid + it2];
            it2 += 1;
        }
        for (int i = 0; i < it1 + it2; i++) {
            a[left + i] = result[i];
        }
    }

    static void SuperMergeSortIterative(passenger[] a) {
        for (int i = 1; i < a.length; i *= 2) {
            for (int j = 0; j < a.length - i; j += 2 * i) {
                supermerge(a, j, j + i, min(j + 2 * i, a.length));
            }
        }
    }

    static void supermerge(passenger[] a, int left, int mid, int right) {
        int it1 = 0;
        int it2 = 0;
        passenger[] result = new passenger[right - left];
        while (left + it1 < mid && mid + it2 < right) {
            if (a[left + it1].in < a[mid + it2].in) {
                result[it1 + it2] = a[left + it1];

                it1 += 1;
            } else {
                result[it1 + it2] = a[mid + it2];
                it2 += 1;
            }
        }
        while (left + it1 < mid) {
            result[it1 + it2] = a[left + it1];
            it1 += 1;
        }
        while (mid + it2 < right) {
            result[it1 + it2] = a[mid + it2];
            it2 += 1;
        }
        for (int i = 0; i < it1 + it2; i++) {
            a[left + i] = result[i];
        }
    }

    public static int findseat(int x, int[] out2) {
        int l = 0;
        int r = out2.length - 1;
        while (true) {
            int m = (l + r) / 2;
            if (out2[m] == x) {
                return m + 1;
            } else if (out2[m] < x) {
                l = m + 1;
            } else {
                r = m;
            }
        }
    }

    public static void goin(int x, int[] adds) {
        int v = (adds.length) / 2 + x - 1;
        while (true) {
            if (v == 0) {
                adds[v] += 1;
                return;
            }
            if (v % 2 == 1) {
                adds[v] += 1;
                v = v / 2;
            } else {
                adds[v] += 1;
                v = (v - 1) / 2;
            }
        }
    }

    public static void goout(int x, int[] adds) {
        int v = (adds.length) / 2 + x - 1;
        while (true) {
            if (v == 0) {
                adds[v] -= 1;
                return;
            }
            if (v % 2 == 1) {
                adds[v] -= 1;
                v = v / 2;
            } else {
                adds[v] -= 1;
                v = (v - 1) / 2;
            }
        }
    }

    static int count(int x, int[] adds) {
        int l = adds.length / 2;
        int r = adds.length - 1;
        int cnt = 0;
        int v = 0;
        while (true) {
            if (v == x - 1 + adds.length / 2) {
                return cnt;
            }
            int m = (l + r) / 2;
            if (m < x - 1 + adds.length / 2) {
                cnt += adds[v * 2 + 1];
                v = v * 2 + 2;
                l = m + 1;

            } else {
                v = v * 2 + 1;
                r = m;
            }
        }
    }

    public static class passenger {
        int in;
        int out;
        int seat;

        public passenger(int in, int out) {
            this.in = in;
            this.out = out;
        }
    }

    public static void main(String[] args) throws Exception {
        String destinationFileName = "taxibus.out";
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("taxibus.out", true);
        FastScanner in = new FastScanner(new File("taxibus.in"));
        int n = in.nextInt();
        int x = 1;
        for (int i = 1; i < n; i *= 2) {
            x = i;
        }
        int[] out2 = new int[n];
        passenger[] innout = new passenger[n];
        passenger[] in2 = new passenger[n];
        for (int i = 0; i < n; i++) {
            int qin = in.nextInt();
            int qout = in.nextInt();
            in2[i] = new passenger(qin, qout);
            innout[i] = in2[i];
            out2[i] = qout;
        }
        mergeSortIterative(out2);

        int[] adds = new int[4 * x - 1];
        for (int i = 0; i < 4 * x - 1; i++) {
            adds[i] = 0;
        }

        int i = 0;
        int j = 0;
        long min = 0;
        int[] secondans = new int[n];
        for (int k = 0; k < n; k++) {
            int seat = findseat(in2[k].out, out2);
            in2[k].seat = seat;
            secondans[k] = seat;
        }
        SuperMergeSortIterative(innout);

        while (i < n) {
            if (innout[i].in < out2[j]) {
                int seat = innout[i].seat;
                int t = count(seat, adds);
                goin(seat, adds);
                min += t;
                i++;
            } else {
                int t = count(j + 1, adds);
                goout(j + 1, adds);
                min += t;
                j++;
            }


        }
        while (j < n) {
            int t = count(j + 1, adds);

            goout(j + 1, adds);
            min += t;
            j++;
        }
        writer.write(min + "\n");
        for (int k = 0; k < n; k++) {
            writer.write(secondans[k] + " ");
        }
        writer.flush();
        fileOutputStream.close();
    }
}

