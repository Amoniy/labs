package labs.lab2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class hospital {
    public static void main(String[] args) throws Exception
    { String sourceFileName = "hospital.in";
        String destinationFileName = "hospital.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        FileWriter writer = new FileWriter("hospital.out", true);
        String split="[ ]+";
        ArrayList<Integer> list = new ArrayList<Integer>();
        int n=Integer.parseInt(br.readLine());
        for(int i=0;i<n;i++){
            String[] command=br.readLine().split(split);
            if (command[0].equals("+")){
                list.add(Integer.parseInt(command[1]));
            }
            else if (command[0].equals("*")){
                int c=0;
                if (list.size()%2==0){
                    c=list.size()/2;
                }
                else {
                    c=list.size()/2+1;
                }
                list.add(c,Integer.parseInt(command[1]));
            }
            else if (command[0].equals("-")){
                writer.write(""+list.get(0)+"\n");
                list.remove(0);
            }
        }

        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
