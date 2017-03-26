package labs.lab3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class RSQ {
    static void set(int v, long x, long[] tree){
        tree[v]=x;
        if(v==0){
            return;
        }
        if(v%2==1){
            set((v-1)/2,(tree[v]+tree[v+1]),tree);
        }
        else {
            set((v-1)/2,(tree[v]+tree[v-1]),tree);
        }
    }
    static long sum(int v,int l,int r,int NEEDEDLEFT, int NEEDEDRIGHT,long[] tree){
        int x=0;
        if(l==r && l>=NEEDEDLEFT && r<=NEEDEDRIGHT){
            return tree[v];
        }

        if(NEEDEDLEFT<=l && NEEDEDRIGHT>=r){
            return tree[v];
        }

        if(NEEDEDLEFT>r || NEEDEDRIGHT<l){
            return 0;
        }

        if(NEEDEDLEFT>l || NEEDEDRIGHT<r){
            int m=(l+r)/2;
            return (sum(v*2+1,l,m,NEEDEDLEFT,NEEDEDRIGHT,tree)+sum(v*2+2,m+1,r,NEEDEDLEFT,NEEDEDRIGHT,tree));
        }
        return x;
    }
    static long[] build (long[] a, int v, int l, int r,long[] ret) {
        if (l == r)
            ret[v] = a[l];
        else {
            int m = (l + r) / 2;
            ret=build (a, v*2+1, l, m,ret);
            ret=build (a, v*2+2, m+1, r,ret);
            ret[v]=(ret[v*2+1]+ret[v*2+2]);
        }
        return ret;
    }
    //C:\java\projects\src\labs\lab3\test
    public static void main(String[] args)throws Exception {
        String sourceFileName = "rsq.in";
        String destinationFileName = "rsq.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        FileWriter writer = new FileWriter("rsq.out", true);
        String split="[ ]+";

        int n=Integer.parseInt(br.readLine());
        String[] temp=br.readLine().split(split);
        int x=1;
        for(int i=1;i<n*2;i*=2){
            x=i;
        }
        long[] array=new long[x];
        for(int i=0;i<n;i++){
            array[i]=Long.parseLong(temp[i]);
        }
        for(int i=n;i<x;i++){
            array[i]=0;
        }
        long[] ret=new long[2*x-1];
        long[] tree=build(array,0,0,x-1,ret);
        String str = br.readLine();

        try {
            while (true) {
                if (str.equals(null)) {
                    break;
                }
                String[] s = str.split(split);
                if (s[0].equals("sum")) {
                    int l = Integer.parseInt(s[1]);
                    int r = Integer.parseInt(s[2]);
                    writer.write(sum(0, x - 1, 2 * x - 2, l + x - 2, r + x - 2, tree)+"\n");
                } else {
                    int l = Integer.parseInt(s[1]);
                    int r = Integer.parseInt(s[2]);
                    set(l + x - 2, r, tree);
                }
                str = br.readLine();
            }
        }
        catch (Exception e){

        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
