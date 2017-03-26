package labs.dmlab2;

import java.io.*;
import java.util.StringTokenizer;

public class permutations {
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
        String destinationFileName = "permutations.out";
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("permutations.out", true);
        FastScanner in = new FastScanner(new File("permutations.in"));
        int n = in.nextInt();
        int[] array=new int[n];
        int[]factorials={0,1,2,6,24,120,720,5040,40320,362880};
        for(int i=0;i<n;i++){
            array[i]=i+1;
            writer.write(""+(i+1)+" ");
        }
        writer.write("\n");
        for(int i=1;i<factorials[n];i++){
            for(int j=n-1;j>0;j--){
                if(array[j-1]<array[j]){//нарушили убывание
                    int temp=array[j-1];
                    int minindex=0;
                    for(int w=n-1;w>=0;w--){
                        if(array[w]>temp){
                            minindex=w;
                            break;
                        }
                    }

                    for(int w=j;w<n;w++){
                        if(array[w]>temp&&array[w]<array[minindex]){
                            minindex=w;
                        }
                    }
                    array[j-1]=array[minindex];
                    array[minindex]=temp;
                    int[] trans=new int[n-j];
                    int q=0;
                    for(int k=j;k<n;k++){
                        trans[q]=array[k];
                        q++;
                    }
                    q=trans.length-1;
                    for(int k=j;k<n;k++){
                        array[k]=trans[q];
                        q--;
                    }
                    break;
                }

            }
            for(int z=0;z<n;z++){
                writer.write(""+array[z]+" ");
            }
            writer.write("\n");
        }
        writer.flush();
        fileOutputStream.close();
    }
}
