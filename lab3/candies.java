package labs.lab3;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

public class candies {
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
    static void mergeSortIterative(int[] a)
    {
        for (int i = 1;i<a.length; i *= 2)
        {
            for (int j = 0;j<a.length - i; j += 2 * i)
            {
                merge(a, j, j + i, min(j + 2 * i, a.length));
            }
        }
    }

    static int min(int a,int b)
    {
        return (a<=b) ? a : b;
    }

    static void merge(int[] a, int left, int mid, int right)
    {
        int it1 = 0;
        int it2 = 0;
        int[] result=new int[right-left];
        while (left + it1 < mid && mid+it2 < right)
        {
            if (a[left + it1] < a[mid + it2])
            {
                result[it1 + it2] = a[left + it1];
                it1 += 1;
            }
            else
            {
                result[it1 + it2] = a[mid + it2];
                it2 += 1;
            }
        }
        while (left + it1 < mid)
        {
            result[it1 + it2] = a[left + it1];
            it1 += 1;
        }
        while (mid + it2 < right)
        {
            result[it1 + it2] = a[mid + it2];
            it2 += 1;
        }
        for (int i = 0;i< it1 +it2;i++)
        {
            a[left + i] = result[i];
        }
    }

    static int infinity=2147483647;
    static String split="[ ]+";

    static void goup(int v,int[] tree){
        if(v==0){
            return;
        }
        if(v%2==1){//leftson
            if(tree[(v-1)/2]!=maximum(tree[v],tree[v+1])){
                tree[(v-1)/2]=maximum(tree[v],tree[v+1]);
                goup((v-1)/2,tree);
            }
        }
        else{
            if(tree[(v-1)/2]!=maximum(tree[v],tree[v-1])){
                tree[(v-1)/2]=maximum(tree[v],tree[v-1]);
                goup((v-1)/2,tree);
            }
        }
    }
    static void add(int v, int q, int l,int r,int NEEDEDLEFT,int NEEDEDRIGHT,int[]adds,int[] tree){
        if(NEEDEDLEFT>r || NEEDEDRIGHT<l){//does not cross
            return;
        }

        if (NEEDEDLEFT<=l && NEEDEDRIGHT>=r){//borders are equal or qurrent in needed
            adds[v]+=q;
            tree[v]+=q;
            goup(v,tree);
            return;
        }
        //if (l<=NEEDEDLEFT && r>=NEEDEDRIGHT) {//needed in current
        else{//needed in current or crosses
            if(adds[v]!=0){
                adds[2*v+1]+=adds[v];
                adds[2*v+2]+=adds[v];
                tree[2*v+1]+=adds[v];
                tree[2*v+2]+=adds[v];
                adds[v]=0;
                goup(2*v+1,tree);
                goup(2*v+2,tree);
            }
            int m=(l+r)/2;
            add(v*2+1,q,l,m,NEEDEDLEFT,NEEDEDRIGHT,adds,tree);
            add(v*2+2,q,m+1,r,NEEDEDLEFT,NEEDEDRIGHT,adds,tree);
        }
    }
    static int[] build (int[] a, int v, int l, int r,int[] ret) {
        if (l == r)
            ret[v] = a[l];
        else {
            int m = (l + r) / 2;
            ret=build (a, v*2+1, l, m,ret);
            ret=build (a, v*2+2, m+1, r,ret);
            ret[v]=maximum(ret[v*2+1],ret[v*2+2]);
        }
        return ret;
    }
    static int minimum(int f,int s){
        if(f<=s){
            return f;
        }
        return s;
    }
    static int maximum(int f,int s){
        if(f<=s){
            return s;
        }
        return f;
    }

    static int findlength(int x,int[] tree,int[] adds){
        if (tree[0]<x){
            return 0;
        }
        int v=0;
        while(v<tree.length/2){//godown
            if(adds[v]!=0){
                adds[v*2+1]+=adds[v];
                adds[v*2+2]+=adds[v];
                tree[v*2+1]+=adds[v];
                tree[v*2+2]+=adds[v];
                adds[v]=0;
            }
            if(tree[2*v+2]>=x){
                v=v*2+2;
            }
            else {
                v=v*2+1;
            }
        }//got into the rightest
        int len=v-tree.length/2+1;//answer
        add(0,-1,tree.length/2,tree.length-1,tree.length/2,v,adds,tree);
        return len;
    }
    //C:\java\projects\src\labs\lab3\test
    public static void main(String[] args) throws Exception {
        String destinationFileName = "candies.out";
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("candies.out", true);
        FastScanner in=new FastScanner(new File("candies.in"));
        int n=in.nextInt();
        int x=1;
        for(int i=1;i<n*2;i*=2){
            x=i;
        }
        int[] array=new int[n];
        for(int i=0;i<n;i++){
            array[i]=in.nextInt();
        }
        mergeSortIterative(array);
        int[] actboxes=new int[x];
        for (int i=0;i<n;i++){
            actboxes[i]=array[n-i-1];
        }
        for (int i=n;i<x;i++){
            actboxes[i]=0;
        }
        int[] adds=new int[2*x-1];
        for(int i=0;i<2*x-1;i++){
            adds[i]=0;
        }
        int m=in.nextInt();
        int[] ret=new int[2*x-1];
        int[] boxes=build(actboxes,0,0,x-1,ret);
        for (int i=0;i<m;i++){
            int guest=in.nextInt();
            writer.write(findlength(guest,boxes,adds)+"\n");
        }
        writer.flush();
        fileOutputStream.close();
    }
}