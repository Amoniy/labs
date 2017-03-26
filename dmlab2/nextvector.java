package labs.dmlab2;

import java.io.*;
import java.util.StringTokenizer;

public class nextvector {


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
    public static void next(int[] bits,int len,FileWriter writer, java.io.FileOutputStream fileOutputStream)throws Exception{
        for(int i=len-1;i>=0;i--){
            if(bits[i]==0){
                bits[i]=1;
                for(int j=i+1;j<len;j++){
                    bits[j]=0;
                }
                break;
            }
        }
        //String ans="";
        for (int i=0;i<len;i++){
            //ans+=bits[i];
            writer.write((bits[i]+48));
        }
        //return ans;
    }
    public static void prev(int[] bits,int len,FileWriter writer, java.io.FileOutputStream fileOutputStream)throws Exception{
        for(int i=len-1;i>=0;i--){
            if(bits[i]==1){
                bits[i]=0;
                for(int j=i+1;j<len;j++){
                    bits[j]=1;
                }
                break;
            }
        }
        //String ans="";
        for (int i=0;i<len;i++){
            //ans+=bits[i];
            writer.write((bits[i]+48));
        }
        //return ans;
    }

    //C:\java\projects\src\labs\dmlab2\test
    public static void main(String[] args) throws Exception {
        String sourceFileName = "nextvector.in";
        String destinationFileName = "nextvector.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        FileWriter writer = new FileWriter("nextvector.out", true);
        int[] bits=new int[200000];
        int[] bits2=new int[200000];
        //int rz=0;
        //int ro=0;
        int len=0;
        boolean firstmark=false;
        boolean secondmark=false;
        while (true) {
            int c = br.read();
            if (c == -1 || c==13 ) {
                break;
            }
            c-=48;
            if(c==1){
                firstmark=true;
            }
            else {
                secondmark=true;
            }
            bits[len] = c;
            bits2[len] = c;
            len++;
        }

        if (!firstmark && secondmark){
            writer.write("-\n");
            next(bits,len,writer,fileOutputStream);
        }
        else if(!secondmark && firstmark){
            prev(bits,len,writer,fileOutputStream);
            writer.write("\n");
            writer.write("-");
        }
        else {
            prev(bits,len,writer,fileOutputStream);
            writer.write("\n");
            next(bits2,len,writer,fileOutputStream);
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }

}
