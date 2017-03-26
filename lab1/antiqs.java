package labs.lab1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class antiqs {
    public static void main(String[] args) throws Exception
    {
        String sourceFileName = "C:\\java\\projects\\src\\labs.lab1\\antiqs.in";
        String destinationFileName = "C:\\java\\projects\\src\\labs.lab1\\antiqs.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        int n=Integer.parseInt(br.readLine());
        int[] arr=new int[n];
        for (int i=0;i<n;i++){
            arr[i]=i+1;
        }
        for (int i=2;i<n;i++){
            int t=arr[i];
            arr[i]=arr[i/2];
            arr[i/2]=t;
        }

        FileWriter writer = new FileWriter("C:\\java\\projects\\src\\labs.lab1\\antiqs.out", true);
        for(int i=0;i<n;i++) {
            writer.write(arr[i]+" ");
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }

}