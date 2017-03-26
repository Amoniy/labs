package labs.dmlab2;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class vectors {
    static int[] powers = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072};
    static Set<String> set=new HashSet<>();
    static HashMap<Integer,String> map=new HashMap();
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
    public static int[] getnew(int[] array){
        for(int i=1;i<array.length;i++){
            array[i-1]=array[i];
        }
        if(array[array.length-2]==1){
            array[array.length-1]=0;
            String temp = "";
            for (int i = 0; i < array.length; i++) {
                temp += array[i];
            }
            set.add(temp);

            int value=0;
            for(int i=0;i<array.length;i++){
                value+=array[i]*powers[array.length-1-i];
            }
            map.put(value,temp);
        }
        else {
            array[array.length - 1] = 1;
            String temp = "";
            for (int i = 0; i < array.length - 1; i++) {
                temp += array[i];
            }
            String attemptone = temp + "1";
            String attemptzero = temp + "0";
            if (set.contains(attemptone)) {
                array[array.length - 1] = 0;
                set.add(attemptzero);
                int value=0;
                for(int i=0;i<array.length;i++){
                    value+=array[i]*powers[array.length-1-i];
                }
                map.put(value,attemptzero);
            } else {
                array[array.length - 1] = 1;
                set.add(attemptone);
                int value=0;
                for(int i=0;i<array.length;i++){
                    value+=array[i]*powers[array.length-1-i];
                }
                map.put(value,attemptone);
            }


        }
        return array;
    }
    //C:\java\projects\src\labs\dmlab2\test
    public static void main(String[] args) throws Exception {
        String destinationFileName = "vectors.out";
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("vectors.out", true);
        FastScanner in = new FastScanner(new File("vectors.in"));
        int n = in.nextInt();

        int[] array=new int[n];
        String temp="";
        for (int i=0;i<n;i++){
            array[i]=0;
            temp+="0";
        }
        set.add(temp);
        map.put(0,temp);
        for(int i=1;i<powers[n];i++){
            array=getnew(array);
        }
        writer.write(""+map.size());
        writer.write("\n");
        for(int i=0;i<powers[n];i++){
            if(map.containsKey(i)){
                writer.write(map.get(i));
                writer.write("\n");
            }
        }

        writer.flush();
        fileOutputStream.close();
    }
}
