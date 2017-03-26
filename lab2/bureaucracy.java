package labs.lab2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Queue;

public class bureaucracy {
    public static void main(String[] args) throws Exception
    {
        String sourceFileName = "bureaucracy.in";
        String destinationFileName = "bureaucracy.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        FileWriter writer = new FileWriter("bureaucracy.out", true);
        String split="[ ]+";
        Queue<Integer> list=new LinkedList<Integer>();
        String[] command=br.readLine().split(split);
        int n=Integer.parseInt(command[0]);
        int m=Integer.parseInt(command[1]);
        String[] units=br.readLine().split(split);
        for(int i=0;i<n;i++){
            list.add(Integer.parseInt(units[i]));
        }
        int count=0;
        while(list.size()!=0 && list.size()<=m) {
            int k=list.size();
            int possible = m / list.size();
            for (int i = 0; i < k; i++) {
                if (list.peek() <= possible) {
                    count += list.peek();
                    list.remove();
                } else {
                    count += possible;
                    int temp = list.peek();
                    list.remove();
                    list.add(temp - possible);
                }
            }
            m=m-count;
            count=0;
        }


            if (list.size() != 0) {
                for (int i = 0; i < m; i++) {
                    int temp = list.peek();
                    list.remove();
                    if (temp != 1) {
                        list.add(temp - 1);
                    }
                }
            }

        if(list.size()!=0) {
            writer.write("" + list.size() + "\n");
            int k=list.size();
            for (int i = 0; i < k; i++) {
                writer.write("" + list.remove() + " ");
            }
        }
        else {
            writer.write("" + -1);
        }


        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
