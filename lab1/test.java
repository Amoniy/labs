package labs.lab1;
import java.io.*;
public class test {
    public static void main(String[] args) throws IOException {
        String sourceFileName = "C:\\java\\projects\\src\\labs.lab1\\antiqs.in";
        String destinationFileName = "C:\\java\\projects\\src\\labs.lab1\\antiqs.out";
        FileInputStream fileInputStream = new FileInputStream(sourceFileName);
        FileOutputStream fileOutputStream= new FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        String str = br.readLine();
        //str = str.substring(1,str.length());
        int n = Integer.parseInt(str);
        int[] array = new int[n];
        for (int i = 0; i<n;i++) array[i] = i+1;

        for (int i = 0; i< n; i++) {
            int t = array[i];
            array[i] = array[i/2];
            array[i/2] = t;
        }

        FileWriter writer = new FileWriter("C:\\java\\projects\\src\\labs.lab1\\antiqs.out",true);
        for (int k =0; k<n;k++){
            writer.write(array[k]+" ");
        }
        writer.flush();///////////////////
        fileInputStream.close();
        fileOutputStream.close();
    }
}
