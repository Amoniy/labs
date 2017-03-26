package labs.lab1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by Антон on 16.10.2016.
 */
public class priorityqueue {
    public static void main(String[] args) throws Exception
    {
        String sourceFileName = "C:\\java\\projects\\src\\labs.lab1\\priorityqueue.in";
        String destinationFileName = "C:\\java\\projects\\src\\labs.lab1\\priorityqueue.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        ArrayList<Integer> list = new ArrayList<Integer>();
        FileWriter writer = new FileWriter("C:\\java\\projects\\src\\labs.lab1\\priorityqueue.out", true);
        String s=br.readLine();
        String split="[ ]+";
        int count=0;
        try{
            while(true){
                if (s.equals(null)){
                    break;
                }
                char[] c=s.toCharArray();
                s="";
                if (c[0]=='p'){
                    for (int i=5;i<c.length;i++){
                        s+=c[i];
                    }
                    int x=Integer.parseInt(s);
                    list.add(x);
                }
                else if(c[0]=='e'){
                    if(list.size()>0) {
                        int min = list.get(0);
                        int ind = 0;
                        for (int i = 0; i < list.size(); i++) {
                            if (min >= list.get(i)) {
                                min = list.get(i);
                                ind = i;
                            }
                        }
                        if(count>0) {
                            writer.write("\n" + list.get(ind));
                        }
                        else {
                            writer.write(""+list.get(ind));
                            count++;
                        }
                        list.remove(ind);
                    }
                    else{
                        if (count>0) {
                            writer.write("\n*");
                        }
                        else {
                            writer.write("*");
                            count++;
                        }
                    }
                }
                else {
                    for(int i=13;i<c.length;i++){
                        s+=c[i];
                    }
                    String[] ints=s.split(split);
                    for (int i=0;i<ints.length;i++){
                        list.set(i,list.get(i)-Integer.parseInt(ints[i]));
                    }
                }
                s=br.readLine();
            }
        }
        catch (Exception e){
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
