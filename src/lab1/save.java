package lab1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by Антон on 16.10.2016.
 */
public class save {

    static void mergeSortIterative(int[] a) {
        for (int i = 1; i < a.length; i *= 2) {
            for (int j = 0; j < a.length - i; j += 2 * i) {
                merge(a, j, j + i, minn(j + 2 * i, a.length));
            }
        }
    }

    static int minn(int a, int b) {
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

    public static int delta(int[] kep, int[] may, int[] sht, int[] bot, int k, int m, int s, int b) {
        int x = min(min(kep[k], may[m]), min(sht[s], bot[b]));
        int y = max(max(kep[k], may[m]), max(sht[s], bot[b]));
        int delt = y - x;
        return delt;
    }

    public static int min(int x, int y) {
        if (x > y) {
            return y;
        } else {
            return x;
        }
    }

    public static int max(int x, int y) {
        if (x > y) {
            return x;
        } else {
            return y;
        }
    }

    public static void main(String[] args) throws Exception {
        String sourceFileName = "C:\\java\\projects\\src\\labs.lab1\\style2.in";
        String destinationFileName = "C:\\java\\projects\\src\\labs.lab1\\style2.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        String split = "[ ]+";

        int a = Integer.parseInt(br.readLine());
        String[] ints1 = br.readLine().split(split);
        int[] kep = new int[a];
        for (int k = 0; k < a; k++) {
            kep[k] = Integer.parseInt(ints1[k]);
        }

        int q = Integer.parseInt(br.readLine());
        String[] ints2 = br.readLine().split(split);
        int[] may = new int[q];
        for (int m = 0; m < q; m++) {
            may[m] = Integer.parseInt(ints2[m]);
        }

        int c = Integer.parseInt(br.readLine());
        String[] ints3 = br.readLine().split(split);
        int[] sht = new int[c];
        for (int s = 0; s < c; s++) {
            sht[s] = Integer.parseInt(ints3[s]);
        }

        int d = Integer.parseInt(br.readLine());
        String[] ints4 = br.readLine().split(split);
        int[] bot = new int[d];
        for (int b = 0; b < d; b++) {
            bot[b] = Integer.parseInt(ints4[b]);
        }

        mergeSortIterative(kep);
        mergeSortIterative(may);
        mergeSortIterative(sht);
        mergeSortIterative(bot);

        int x = 0, y = 0, z = 0, p = 0, k = 0, m = 0, s = 0, b = 0;
        int min = delta(kep, may, sht, bot, 0, 0, 0, 0);
        while (k < a && m < q && s < c && b < d) {
            if (min > delta(kep, may, sht, bot, k, m, s, b)) {
                min = delta(kep, may, sht, bot, k, m, s, b);
                x = k;
                y = m;
                z = s;
                p = b;
                if (min == 0) {
                    break;
                }
            }
            if (kep[k] == min(min(kep[k], may[m]), min(sht[s], bot[b]))) {
                k++;
            } else if (may[m] == min(min(kep[k], may[m]), min(sht[s], bot[b]))) {
                m++;
            } else if (sht[s] == min(min(kep[k], may[m]), min(sht[s], bot[b]))) {
                s++;
            } else {
                b++;
            }
        }

        FileWriter writer = new FileWriter("C:\\java\\projects\\src\\labs.lab1\\style2.out", true);
        writer.write(kep[x] + " " + may[y] + " " + sht[z] + " " + bot[p]);
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
