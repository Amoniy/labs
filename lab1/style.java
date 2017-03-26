package labs.lab1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class style {
    public static int delta(int[] may,int[] sht, int i, int j){
        int delt;
        if(sht[j]>=may[i]){
            delt=sht[j]-may[i];
        }
        else{
            delt=may[i]-sht[j];
        }
        return delt;
    }
    public static void main(String [] args) throws Exception
    {
        String sourceFileName = "style.in";
        String destinationFileName = "style.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        int n=Integer.parseInt(br.readLine());
        String split="[ ]+";
        String[] ints=br.readLine().split(split);
        int[] may=new int[n];
        for (int i=0;i<n;i++){
            may[i]=Integer.parseInt(ints[i]);
        }
        int m=Integer.parseInt(br.readLine());
        String[] ints2=br.readLine().split(split);
        int[] sht=new int[m];
        for (int i=0;i<m;i++){
            sht[i]=Integer.parseInt(ints2[i]);
        }
        int x=0,i=0,j=0,y=0;
        int min=delta(may,sht,0,0);
        while(i<n && j<m){
            if (min>delta(may,sht,i,j)){
                min=delta(may,sht,i,j);
                x=i;
                y=j;
            }
            if(may[i]==sht[j]){
                x=i;
                y=j;
                break;
            }
            else if (may[i]<sht[j]){
                i++;
            }
            else if(may[i]>sht[j]){
                j++;
            }
        }


        FileWriter writer = new FileWriter("style.out", true);
        writer.write(may[x]+" "+sht[y]);
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
