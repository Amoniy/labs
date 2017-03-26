package labs.dmlab2;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class perm2num {
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

    //C:\java\projects\src\labs\dmlab2\test
    public static void main(String[] args) throws Exception {
        String destinationFileName = "perm2num.out";
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("perm2num.out", true);
        FastScanner in = new FastScanner(new File("perm2num.in"));
        int n =in.nextInt();
        int[] perm=new int[n];
        for(int i=0;i<n;i++){
            perm[i]=in.nextInt();
        }

        long[]factorials=new long[19];
        factorials[0]=1;
        factorials[1]=1;
        for (int i=2;i<19;i++){
            factorials[i]=factorials[i-1]*i;
        }
        HashSet<Integer> newset= new HashSet<>();

        long ans=0;

        for(int i=0;i<n;i++){
            for(int j=1;j<perm[i];j++){
                if (!newset.contains(j)){
                    ans+=factorials[n-i-1];
                }
            }
            newset.add(perm[i]);
        }

        writer.write(ans+"");
        writer.flush();
        fileOutputStream.close();
    }
}
