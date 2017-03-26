package labs.dmlab2;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;

public class num2perm {
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

        long nextInt() {
            return Long.parseLong(next());
        }
    }

    //C:\java\projects\src\labs\dmlab2\test
    public static void main(String[] args) throws Exception {
        String destinationFileName = "num2perm.out";
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("num2perm.out", true);
        FastScanner in = new FastScanner(new File("num2perm.in"));
        int n =(int) in.nextInt();
        long k = in.nextInt();
        long[]factorials=new long[n+1];
        factorials[1]=1;
        for (int i=2;i<n+1;i++){
            factorials[i]=factorials[i-1]*i;
        }
        ArrayList<Integer> list=new ArrayList<>();
        for(int i=0;i<=n;i++){
            list.add(i);
        }
        String newanswer="";
        HashSet<Integer> newset= new HashSet<>();

        for(int i=1;i<n;i++){
            int minchislo=(int) (k/factorials[n-i]+1);
            k=k%factorials[n-i];
            for(int j=minchislo;j<=n;j++){
                if(!newset.contains(list.get(j))){
                    newset.add(list.get(j));
                    newanswer+=list.get(j)+" ";
                    list.remove(j);
                    break;
                }
            }
        }
        for(int i=1;i<=n;i++){
            if(!newset.contains(i)){
                newanswer+=i;
                break;
            }
        }
        writer.write(newanswer);
        writer.flush();
        fileOutputStream.close();
    }
}
