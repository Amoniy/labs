package labs.lab1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class sorting {
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

    static void quicksort(int[] a, int l, int r) {
        if (l < r - 1) {
            int q = partition(a, l, r);
            quicksort(a, l, q);
            quicksort(a, q + 1, r);
        }
    }

    static int partition(int[] a, int l, int r) {
        int v = a[(l + r) / 2];
        int i = l;
        int j = r;
        while (i <= j) {
            while (a[i] < v) {
                i++;
            }
            while (a[j] > v) {
                j--;
            }
            if (i <= j) {
                swap(i, j, a);
                i++;
                j--;
            }
        }
        return j;
    }

    static void swap(int i, int j, int a[]) {
        int x = a[i];
        a[i] = a[j];
        a[j] = x;
    }

    public static void heapify(int[] array, int size, int pos) {
        while (2 * pos + 1 < size) {
            int t = 2 * pos + 1;
            if (2 * pos + 2 < size && array[2 * pos + 1] < array[2 * pos + 2]) {
                t = 2 * pos + 2;
            }
            if (array[pos] < array[t]) {
                swap(array, pos, t);
                pos = t;
            } else {
                break;
            }
        }
    }

    public static int[] heapMake(int[] array) {
        int n = array.length;
        for (int i = n - 1; i >= 0; i--) {
            heapify(array, n, i);
        }
        return array;
    }

    public static void sort(int[] array) {
        int n = array.length;
        heapMake(array);
        while (n > 0) {
            swap(array, 0, n - 1);
            n--;
            heapify(array, n, 0);
        }

    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) throws Exception {
        String sourceFileName = "sort.in";
        String destinationFileName = "sort.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        int n = Integer.parseInt(br.readLine());
        String split = "[ ]+";
        String[] ints = br.readLine().split(split);
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(ints[i]);
        }
        mergeSortIterative(arr);
        //sort(arr);
        //quicksort(arr,0, arr.length-1);


        FileWriter writer = new FileWriter("sort.out", true);
        for (int k = 0; k < arr.length - 1; k++) {
            writer.write(arr[k] + " ");
        }
        writer.write("" + arr[arr.length - 1]);
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
