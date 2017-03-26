package labs.dmlab2;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class part2sets {
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
        int size=a.size();
        for (int i = size - 1;i>= 0;i--) {
            if ((used.size() != 0) &&(used.get(used.size() - 1) > a.get(i).get(a.get(i).size() - 1))) {
                a.get(i).add(used.get(used.size() - 1));
                used.remove(used.size() - 1);
                break;
            }
            int x=0;
            for (int j = a.get(i).size() - 1;j>= 0;j--) {
                x=j;
                if ((used.size() != 0) && (j != 0)&&(used.get(used.size() - 1)> a.get(i).get(j))) {
                    int temp=a.get(i).get(j);
                    a.get(i).set(j,used.get(used.size() - 1));
                    used.set(used.size()-1,temp);
                    mergeSortIterative(used);
                    fl = true;

                    break;
                }
            }
            if (fl) {
                break;
            }
            used.add(a.get(i).get(x));
            a.get(i).remove(x);
            if(a.get(i).size()==0){
                a.remove(a.size()-1);
            }
        }
        mergeSortIterative(used);
        for (int i = 0;i<= used.size() - 1;i++) {
            a.add(new ArrayList<Integer>());
            a.get(a.size()-1).add(used.get(i));
        }
        return a;
    }
    static void mergeSortIterative(ArrayList<Integer> a)
    {
        for (int i = 1;i<a.size(); i *= 2)
        {
            for (int j = 0;j<a.size() - i; j += 2 * i)
            {
                merge(a, j, j + i, min(j + 2 * i, a.size()));
            }
        }
    }

    static int min(int a,int b)
    {
        return (a<=b) ? a : b;
    }

    static void merge(ArrayList<Integer> a, int left, int mid, int right)
    {
        int it1 = 0;
        int it2 = 0;
        int[] result=new int[right-left];
        while (left + it1 < mid && mid+it2 < right)
        {
            if (a.get(left + it1) < a.get(mid + it2))
            {
                result[it1 + it2] = a.get(left + it1);
                it1 += 1;
            }
            else
            {
                result[it1 + it2] = a.get(mid + it2);
                it2 += 1;
            }
        }
        while (left + it1 < mid)
        {
            result[it1 + it2] = a.get(left + it1);
            it1 += 1;
        }
        while (mid + it2 < right)
        {
            result[it1 + it2] = a.get(mid + it2);
            it2 += 1;
        }
        for (int i = 0;i< it1 +it2;i++)
        {
            a.set(left + i,result[i]);
        }
    }

    //C:\java\projects\src\labs\dmlab2\test
    public static void main(String[] args) throws Exception {
        String destinationFileName = "C:\\java\\projects\\src\\labs\\dmlab2\\test.out";
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("C:\\java\\projects\\src\\labs\\dmlab2\\test.out", true);
        FastScanner in = new FastScanner(new File("C:\\java\\projects\\src\\labs\\dmlab2\\test.in"));
        int n = in.nextInt();
        int k= in.nextInt();
        ArrayList<ArrayList<Integer>> list=new ArrayList<>();
        list.add(new ArrayList<Integer>());
        list.add(new ArrayList<Integer>());
        list.get(0).add(1);
        list.get(0).add(3);
        list.get(0).add(5);
        list.get(1).add(2);
        list.get(1).add(4);
        list=nextSetPartition(list);
        for(int i=0;i<list.size();i++){
            for(int j=0;j<list.get(i).size();j++){
                writer.write(list.get(i).get(j)+" ");
            }
            writer.write("\n");
        }













        /*
        ArrayList<ArrayList<Integer>> list=new ArrayList<>();
        for(int i=1;i<=n;i++){
            list.add(new ArrayList<Integer>());
            list.get(i-1).add(i);
        }
        int[] powers = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072};
        /*ArrayList<Integer> t=new ArrayList<>();
        t.add(7);
        t.add(3);
        t.add(5);
        t.add(1);
        t.add(9);
        t.add(2);
        mergeSortIterative(t);
        for(int i=0;i<t.size();i++){
            writer.write(t.get(i)+" ");
        }/////////////
        if(true) {
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < list.get(i).size(); j++) {
                    writer.write(list.get(i).get(j) + " ");
                }
                writer.write("\n");
            }
            writer.write("\n");
        }
        try {
            while (true) {
                list = nextSetPartition(list);
                if(true) {
                    for (int i = 0; i < list.size(); i++) {
                        for (int j = 0; j < list.get(i).size(); j++) {
                            writer.write(list.get(i).get(j) + " ");
                        }
                        writer.write("\n");
                    }
                    writer.write("\n");
                }
                /*if(list.size()==1){
                    throw new Exception();
                }//////*
            }
        }
        catch (Exception e){
            writer.write("last");
        }*/

        writer.flush();
        fileOutputStream.close();
    }
}
