package lab1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by Антон on 16.10.2016.
 */
public class priorityqueue2 {
    static void siftDown(int i, ArrayList<Integer> a) {
        while (2 * i + 1 < a.size()) {     // heapSize — количество элементов в куче
            int left = 2 * i + 1;            // left — левый сын
            int right = 2 * i + 2;            // right — правый сын
            int j = left;
            if (right < a.size() && a.get(right) < a.get(left)) {
                j = right;
            }
            if (a.get(i) <= a.get(j)) {
                break;
            }
            int t = a.get(i);
            a.set(i, a.get(j));
            a.set(j, t);
            i = j;
        }
    }

    static void siftUp(int i, ArrayList<Integer> a) {
        while (a.get(i) < a.get((i - 1) / 2)) {    // i == 0 — мы в корне
            int t = a.get(i);
            a.set(i, a.get((i - 1) / 2));
            a.set((i - 1) / 2, t);
            i = (i - 1) / 2;
        }
    }

    static int extractMin(ArrayList<Integer> a) {
        int min = a.get(0);
        a.set(0, a.get(a.size() - 1));
        a.remove(a.size() - 1);
        siftDown(0, a);
        return min;
    }

    static void insert(int key, ArrayList<Integer> a) {
        a.add(key);
        siftUp(a.size() - 1, a);
    }

    public static void main(String[] args) throws Exception {
        String sourceFileName = "C:\\java\\projects\\src\\labs.lab1\\priorityqueue.in";
        String destinationFileName = "C:\\java\\projects\\src\\labs.lab1\\priorityqueue.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        ArrayList<Integer> list = new ArrayList<Integer>();
        FileWriter writer = new FileWriter("C:\\java\\projects\\src\\labs.lab1\\priorityqueue.out", true);
        String s = br.readLine();
        String split = "[ ]+";
        int count = 0;
        try {
            while (true) {
                if (s.equals(null)) {
                    break;
                }
                char[] c = s.toCharArray();
                s = "";
                if (c[0] == 'p') {
                    for (int i = 5; i < c.length; i++) {
                        s += c[i];
                    }
                    int x = Integer.parseInt(s);
                    insert(x, list);
                } else if (c[0] == 'e') {
                    if (list.size() > 0) {
                        if (count > 0) {
                            writer.write("\n" + extractMin(list));
                        } else {
                            writer.write("" + extractMin(list));
                            count++;
                        }

                    } else {
                        if (count > 0) {
                            writer.write("\n*");
                        } else {
                            writer.write("*");
                            count++;
                        }
                    }
                } else {
                    for (int i = 13; i < c.length; i++) {
                        s += c[i];
                    }
                    String[] ints = s.split(split);
                    for (int i = 0; i < ints.length; i++) {
                        list.set(i, list.get(i) - Integer.parseInt(ints[i]));
                    }
                }
                s = br.readLine();
            }
        } catch (Exception e) {
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
