package dmlab2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class part2sets2 {
    public static ArrayList<ArrayList<Integer>> partition(int v, int k) {
        ArrayList<ArrayList<Integer>> parttition = new ArrayList<>();
        ArrayList<Integer> array = new ArrayList<>();
        for (int i = 0; i < v - 1; i++) {
            array.add(1);
        }
        array.add(1);
        if (array.size() == k) {
            ArrayList<Integer> transit = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                transit.add(array.get(i));
            }
            parttition.add(transit);
        }
        while (true) {
            if (array.get(0) == v) {
                break;
            }
            array.set(array.size() - 2, array.get(array.size() - 2) + 1);
            array.set(array.size() - 1, array.get(array.size() - 1) - 1);
            if (array.get(array.size() - 2) < array.get(array.size() - 1)) {
                while (array.get(array.size() - 2) * 2 <= array.get(array.size() - 1)) {
                    array.add(array.size() - 1, array.get(array.size() - 2));
                    array.add(array.size() - 1, array.get(array.size() - 1) - array.get(array.size() - 2));
                    array.remove(array.size() - 1);
                }

            } else if (array.get(array.size() - 2) > array.get(array.size() - 1)) {
                array.set(array.size() - 2, array.get(array.size() - 2) + array.get(array.size() - 1));
                array.remove(array.size() - 1);
            }
            if (array.size() == k) {
                ArrayList<Integer> transit = new ArrayList<>();
                for (int i = 0; i < array.size(); i++) {
                    transit.add(array.get(i));
                }
                parttition.add(transit);
            }
        }
        return parttition;
    }

    public static ArrayList<ArrayList<Integer>> choose(int n, int k) {
        int[] array = new int[k + 1];
        ArrayList<ArrayList<Integer>> choose = new ArrayList<>();
        choose.add(new ArrayList<Integer>());
        for (int i = 0; i < k; i++) {
            array[i] = i + 1;
            choose.get(0).add(i + 1);
        }

        boolean marker = true;
        while (marker) {
            array[k] = n + 1;
            for (int i = k - 1; i >= -1; i--) {
                if (i == -1) {
                    marker = false;
                    break;
                }
                if ((array[i + 1] - array[i]) >= 2) {
                    array[i]++;
                    for (int j = i + 1; j < k; j++) {
                        array[j] = array[j - 1] + 1;
                    }
                    break;
                }

            }
            array[k] = n + 1;
            if (!marker) {
                break;
            }
            choose.add(new ArrayList<Integer>());
            for (int i = 0; i < k; i++) {
                choose.get(choose.size() - 1).add(array[i]);
            }
            array[k] = n + 1;
        }
        return choose;
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

    public static void bloodyfuck(ArrayList<ArrayList<Integer>> tempholder, int currentpart, HashSet<Integer> set, int i, int j, int size, FileWriter writer) throws Exception {

        tempholder.add(new ArrayList<Integer>());

        /*if(tempholder.size()>1){
            if(tempholder.get(tempholder.size()-1).size()==0 && tempholder.get(tempholder.size()-2).size()==0){
                //tempholder.remove(tempholder.size()-1);
            }
        }*/

        int show = fin.get(currentpart).get(i).size();
        for (int x = j; x < show; x++) {
            boolean flag = true;
            for (int q = 0; q < fin.get(currentpart).get(i).get(x).size(); q++) {
                if (set.contains(fin.get(currentpart).get(i).get(x).get(q))) {
                    flag = false;
                    break;
                }//проверили есть ли что-то из разбиения в сете
            }
            if (!flag) {/////////////////////////весь этот цикл от бул флаг до вызова бладифак это просто проход до конца разбиений. надо еще пройти вниз. это по х
                //skip
            } else {


                for (int q = 0; q < fin.get(currentpart).get(i).get(x).size(); q++) {
                    int element = fin.get(currentpart).get(i).get(x).get(q);

                    //answer += element + " ";//одно из подмножеств записано

                    tempholder.get(tempholder.size() - 1).add(element);

                    set.add(fin.get(currentpart).get(i).get(x).get(q));
                }
                //answer += "\n";


                if (i != fin.get(currentpart).size() - 1) {
                    int string = 0;
                    int stringsize = fin.get(currentpart).get(i + 1).get(0).size();
                    if (size == stringsize) {
                        string = x;
                    }
                    bloodyfuck(tempholder, currentpart, set, i + 1, string, stringsize, writer);
                } else {
                    for (int l = 0; l < tempholder.size(); l++) {
                        for (int m = 0; m < tempholder.get(l).size(); m++) {
                            //answer+=tempholder.get(l).get(m)+" ";
                            writer.write(tempholder.get(l).get(m) + " ");
                        }
                        //answer+="\n";
                        writer.write("\n");
                    }
                    //answer+="\n";//закончили конкретное разбиение на подмножества
                    //tempholder.remove(tempholder.size()-1);
                    writer.write("\n");
                }

                for (int q = 0; q < fin.get(currentpart).get(i).get(x).size(); q++) {//либо вывели последнее и удалили, либо не последнее и переходим к новому то есть старое почистить надо
                    set.remove(fin.get(currentpart).get(i).get(x).get(q));

                    //answer+=tempholder.get(tempholder.size()-1).get(q)+" ";
                    if (((i != fin.get(currentpart).size() - 1)) && (x != fin.get(currentpart).get(i).size() - 1) && tempholder.get(tempholder.size() - 1).size() != 0) {//и не в последней строке
                        tempholder.get(tempholder.size() - 1).remove(0);
                    }
                }
                if (((i != fin.get(currentpart).size() - 1)) && (x == fin.get(currentpart).get(i).size() - 1)) {
                    //tempholder.remove(tempholder.size()-1);
                }
            }
            if (x == show - 1) {
                tempholder.remove(tempholder.size() - 1);
            }
        }

    }

    public static ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> fin = new ArrayList<>();
    //public static String answer="";

    public static void main(String[] args) throws Exception {
        String destinationFileName = "part2sets.out";
        FileOutputStream fileOutputStream = new FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("part2sets.out", true);
        FastScanner in = new FastScanner(new File("part2sets.in"));
        int n = in.nextInt();
        int k = in.nextInt();
        ArrayList<ArrayList<Integer>> part = partition(n, k);

        /*
        ArrayList<ArrayList<Integer>> c=choose(n,k);
        for(int i=0;i<c.size();i++){
            for(int j=0;j<c.get(i).size();j++){
                writer.write(c.get(i).get(j)+" ");
            }
            writer.write("\n");
        }                      how to work with chooses
        */


        for (int x = 0; x < part.size(); x++) {//проход по разным разбиениям
            ArrayList<ArrayList<ArrayList<Integer>>> temp = new ArrayList<>();
            for (int y = 0; y < k; y++) {//проход по конкретному разбиению
                ArrayList<ArrayList<Integer>> c = choose(n, part.get(x).get(y));
                temp.add(c);
            }
            fin.add(temp);
        }


        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < fin.size(); i++) {
            bloodyfuck(new ArrayList<ArrayList<Integer>>(), i, set, 0, 0, fin.get(i).get(0).get(0).size(), writer);
        }
        //writer.write(answer);


        writer.flush();
        fileOutputStream.close();
    }
}
