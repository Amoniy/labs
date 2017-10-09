package dmlab4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

/**
 * Created by Anton on 06.05.2017.
 */
public class test {
    private static class MyComp implements Comparator<Integer> {
        public int compare(Integer e1, Integer e2) {
            if (e1 > e2) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    public static void main(String[] args) {
        HashSet<ArrayList<Integer>> a = new HashSet<>();
        ArrayList<Integer> b = new ArrayList<>();
        b.add(1);
        b.add(2);
        a.add(b);
        b = new ArrayList<>();
        b.add(1);
        if (a.contains(b)) {
            System.out.println("+++");
        } else {
            System.out.println("---");
        }
        b.add(2);
        if (a.contains(b)) {
            System.out.println("+++");
        } else {
            System.out.println("---");
        }
        b = new ArrayList<>();
        b.add(2);
        b.add(1);
        b.sort(new MyComp());
        if (a.contains(b)) {
            System.out.println("+++");
        } else {
            System.out.println("---");
        }
    }
}
