package labs.dmlab2;

import java.io.*;
import java.util.HashSet;
import java.util.StringTokenizer;

public class choose2num {
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
    static long[][] C=new long[31][31];
    //C:\java\projects\src\labs\dmlab2\test
    public static void main(String[] args) throws Exception {
        String destinationFileName = "choose2num.out";
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("choose2num.out", true);
        FastScanner in = new FastScanner(new File("choose2num.in"));
        int n =in.nextInt();
        int k =in.nextInt();

        int[] perm=new int[k+1];
        perm[0]=0;
        for(int i=1;i<=k;i++){
            perm[i]=in.nextInt();
        }
        for(int i=0;i<31;i++){
            C[i][0]=1;
        }
        for(int i=1;i<31;i++){
            for(int j=1;j<=i;j++){
                int temp=1;
                int q2=2;
                if(i-j>j){
                    for(int q=i-j+1;q<=i;q++){
                        temp*=q;
                        if(q2<=j && temp%q2==0){
                            temp/=q2;
                            q2++;
                        }
                    }
                }
                else {
                    for(int q=j+1;q<=i;q++){
                        temp*=q;
                        if(q2<=i-j && temp%q2==0){
                            temp/=q2;
                            q2++;
                        }
                    }
                }
                C[i][j]=temp;
            }
        }
        C[30][15]=155117520;
        int ans=0;
        for(int i=0;i<k;i++){
            for(int j=perm[i]+1;j<perm[i+1];j++){
                ans+=C[n-j][k-i-1];
            }
        }

        writer.write(ans+"");
        writer.flush();
        fileOutputStream.close();
    }
}
