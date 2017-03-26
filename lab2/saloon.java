package labs.lab2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayDeque;

public class saloon {
    public static class Unit{
        int time;
        int outtime;
        public Unit(int time,int outtime){
            this.time=time;
            this.outtime=outtime;
        }
    }
    public static void main(String[] args) throws Exception
    {
        String sourceFileName = "saloon.in";
        String destinationFileName = "saloon.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        FileWriter writer = new FileWriter("saloon.out", true);
        String split="[ ]+";

        ArrayDeque<Unit> deck=new ArrayDeque<Unit>();
        int n=Integer.parseInt(br.readLine());
        String[] out=new String[n];

        for(int i=0;i<n;i++){
            String[] ints=br.readLine().split(split);
            int hour=Integer.parseInt(ints[0]);
            int minute=Integer.parseInt(ints[1]);
            int time=60*hour+minute;
            int imp=Integer.parseInt(ints[2]);
            if(deck.size()>0) {
                while (deck.peekFirst().outtime <= time) {
                    deck.removeFirst();
                    if (deck.size() == 0) {
                        break;
                    }
                }
            }
            if(imp<deck.size()){
                out[i]=""+hour+" "+minute+"\n";
            }
            else {
                int outtime;
                if(deck.size()>0) {
                    outtime=deck.peekLast().outtime + 20;
                    deck.addLast(new Unit(time, deck.peekLast().outtime + 20));
                }
                else {
                    deck.addLast(new Unit(time, time + 20));
                    outtime=time + 20;
                }
                out[i]=""+(outtime/60)+" "+(outtime%60)+"\n";
            }
        }
        for (int i=0;i<n;i++){
            writer.write(out[i]);
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
