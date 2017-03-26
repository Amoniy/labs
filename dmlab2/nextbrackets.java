package labs.dmlab2;

import java.io.*;
import java.util.StringTokenizer;

public class nextbrackets {
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

    public static int[] generatenextbracket(int[] brackets,int len){
        int opens=0;
        int closes=0;
        for(int i=len-1;i>=0;i--){
            if(brackets[i]==1){
                opens++;
                if(closes>opens){
                    break;
                }
            }
            else {
                closes++;
            }
        }
        int statcloses=closes;
        int statopens=opens;
        brackets[len-statcloses-statopens]=-1;
        for(int i=len-statcloses-statopens+1;i<len;i++){
            if(opens>0){
                brackets[i]=1;
                opens--;
            }
            else {
                brackets[i]=-1;
            }
        }
        return brackets;
    }
    //C:\java\projects\src\labs\dmlab2\test
    public static void main(String[] args) throws Exception {
        String sourceFileName = "nextbrackets.in";
        String destinationFileName = "nextbrackets.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        FileWriter writer = new FileWriter("nextbrackets.out", true);
        long[] catalan={1, 1, 2, 5, 14, 42, 132, 429, 1430, 4862, 16796, 58786, 208012};
        int[] brackets=new int[200000];
        int len=0;
        boolean flag=false;
        while (true){
            int c = br.read();
            if (c == -1 || c==13 ) {
                break;
            }
            if(c==40){
                c=1;
            }
            else {
                c=-1;
            }
            if(len>0){
                if(brackets[len-1]==1){
                    if(c!=-1){
                        flag=true;
                    }
                }
                else {
                    if(c!=1){
                        flag=true;
                    }
                }
            }
            brackets[len] = c;
            len++;
        }
        if(flag) {
            brackets = generatenextbracket(brackets, len);
            for (int j = 0; j < len; j++) {
                if (brackets[j] == 1) {
                    writer.write("(");
                } else {
                    writer.write(")");
                }
            }
        }
        else {
            writer.write("-");
        }

        writer.flush();
        fileOutputStream.close();
    }
}
