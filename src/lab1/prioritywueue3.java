package lab1;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class prioritywueue3 {

    static void siftDown(int i, ArrayList<Integer[]> a) {
        while (2 * i + 1 < a.size()) {     // heapSize — количество элементов в куче
            int left = 2 * i + 1;            // left — левый сын
            int right = 2 * i + 2;            // right — правый сын
            int j = left;
            if (right < a.size() && a.get(right)[0] < a.get(left)[0]) {
                j = right;
            }
            if (a.get(i)[0] <= a.get(j)[0]) {
                break;
            }
            Integer[] t = a.get(i);
            a.set(i, a.get(j));
            a.set(j, t);
            i = j;
        }
    }

    static void siftUp(int i, ArrayList<Integer[]> a) {
        /*while (a.get(i)[0] < a.get((i-1)/2)[0]) {    // i == 0 — мы в корне
            Integer[] t=a.get(i);
            a.set(i,a.get((i-1)/2));
            a.set((i-1)/2,t);
            i = (i - 1) / 2;
        }*/

        while (i > 0 && a.get((i - 1) / 2)[0] > a.get(i)[0]) {
            Integer[] t = a.get(i);
            a.set(i, a.get((i - 1) / 2));
            a.set((i - 1) / 2, t);
            i = (i - 1) / 2;
        }

    }

    static int extractMin(ArrayList<Integer[]> a) {
        Integer min = a.get(0)[0];
        a.set(0, a.get(a.size() - 1));
        a.remove(a.size() - 1);
        siftDown(0, a);
        return min;
    }

    static void insert(int key, ArrayList<Integer[]> a, int count) {
        Integer[] x = {key, count};
        a.add(x);
        siftUp(a.size() - 1, a);
    }


    public static void main(String[] args) throws Exception {
        String sourceFileName = "priorityqueue.in";
        String destinationFileName = "priorityqueue.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        ArrayList<Integer[]> list = new ArrayList<Integer[]>();
        FileWriter writer = new FileWriter("priorityqueue.out", true);
        String s = br.readLine();
        String split = "[ ]+";
        int count = 1;
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
                    insert(x, list, count);
                } else if (c[0] == 'e') {
                    if (list.size() > 0) {
                        writer.write(extractMin(list) + "\n");
                    } else {
                        writer.write("*\n");
                    }
                } else {
                    for (int i = 13; i < c.length; i++) {
                        s += c[i];
                    }
                    String[] ints = s.split(split);
                    int[] g = new int[2];
                    g[0] = Integer.parseInt(ints[0]);
                    g[1] = Integer.parseInt(ints[1]);
                    for (int u = 0; u < list.size(); u++) {
                        if (list.get(u)[1] == g[0]) {
                            Integer[] r = new Integer[2];
                            r[0] = g[1];
                            r[1] = u;
                            list.set(u, r);
                            siftUp(u, list);
                            siftDown(u, list);

                            break;
                        }
                    }
                }
                s = br.readLine();
                count++;
            }
        } catch (Exception e) {
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }

}
