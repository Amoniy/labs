package labs.lab2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

public class formation {
    public static class Unit{
        int value;
        int left;
        int right;
        public Unit(int value,int left, int right){
            this.value=value;
            this.left=left;
            this.right=right;
        }
    }
    public static void main(String[] args) throws Exception
    {
        String sourceFileName = "formation.in";
        String destinationFileName = "formation.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        FileWriter writer = new FileWriter("C:\\java\\projects\\src\\labs.lab2\\brackets.out", true);
        String split="[ ]+";

        String[] ints=br.readLine().split(split);
        int n=Integer.parseInt(ints[0]);
        int m=Integer.parseInt(ints[1]);

        HashMap<Integer, Unit> formation = new HashMap<Integer, Unit>();

        formation.put(1,new Unit(1,0,0));
        formation.put(0,new Unit(0,0,0));

        for(int i=0;i<m;i++){
            String[] command=br.readLine().split(split);
            if(command[0].equals("left")){
                int I=Integer.parseInt(command[1]);
                int J=Integer.parseInt(command[2]);

                int right1=formation.get(J).right;
                int left1=formation.get(J).left;
                int left2=formation.get(formation.get(J).left).left;

                formation.remove(left1);
                formation.put(left1,new Unit(left1,left2,I));

                formation.remove(J);
                formation.put(J,new Unit(J,I,right1));
                formation.put(I,new Unit(I,left1,J));
            }
            else if(command[0].equals("right")){
                int I=Integer.parseInt(command[1]);
                int J=Integer.parseInt(command[2]);

                int right1=formation.get(J).right;
                int left1=formation.get(J).left;
                int right2=formation.get(formation.get(J).right).right;

                formation.remove(right1);
                formation.put(right1,new Unit(right1,I,right2));

                formation.remove(J);
                formation.put(J,new Unit(J,left1,I));
                formation.put(I,new Unit(I,J,right1));
            }
            else if(command[0].equals("leave")){
                int I=Integer.parseInt(command[1]);
                int left1=formation.get(formation.get(I).left).left;
                int right2=formation.get(formation.get(I).right).right;
                formation.put(formation.get(I).left,new Unit(formation.get(I).left,left1,formation.get(I).right));
                formation.put(formation.get(I).right,new Unit(formation.get(I).right,formation.get(I).left,right2));
                formation.remove(I);
            }
            else if(command[0].equals("name")){
                int I=Integer.parseInt(command[1]);
                writer.write(""+formation.get(I).left+" "+formation.get(I).right+"\n");
            }
        }

        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
