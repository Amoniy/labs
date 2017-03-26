package labs.lab1;
import java.io.*;

public class heap {
    public static void main(String[] args) throws Exception
    {
        String sourceFileName = "isheap.in";
        String destinationFileName = "isheap.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        int n=Integer.parseInt(br.readLine());
        String split="[ ]+";
        String[] ints=br.readLine().split(split);
        int[] arr=new int[n];
        for (int i=0;i<n;i++){
            arr[i]=Integer.parseInt(ints[i]);
        }
        int bp=0;
        for (int k=0;k<n/2-1;k++){
            if (k*2+2<n){
                if (arr[k]>arr[k*2+2]){
                    bp=1;
                    break;
                }
            }
            if (k*2+1<n){
                if (arr[k]>arr[k*2+1]){
                    bp=1;
                    break;
                }
            }
        }
        Byte[] ans={'Y','E','S','N','O'};
        int x=0, y=3;
        if(bp==1){
            x=3;
            y=5;
        }
        for (int j=x;j<y;j++)
        {
            fileOutputStream.write(ans[j]);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }
}
