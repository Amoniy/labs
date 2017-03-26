package labs.lab1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class trains {
    public static class Lot{
        public double v;
        public double t;

        public Lot(double v, double t) {
            this.v = v;
            this.t = t;
        }
    }
    public static double getLength(Lot[] trace,double time){
        double res = 0;
        double t = 0;
        for (int i = 0; i < trace.length;i++){
            if (t + trace[i].t > time){
                res += trace[i].v * (time - t);
                break;
            }
            t+= trace[i].t;
            res += trace[i].t * trace[i].v;
        }
        return res;
    }
    public static boolean checkTime(Lot[] trace,double time,double l){
        double sumTime = 0;
        for (int i = 0; i < trace.length; i++){
            sumTime+=trace[i].t;
        }
        double now = 0;
        double min = 10e9;
        for (int i = 0; i< trace.length;i++){
            now += trace[i].t;
            if (now>=time){
                double s1 = getLength (trace,now) - getLength (trace,now-time);
                double s2 = getLength (trace,now+time) - getLength (trace,now);
                if (now + time >sumTime){
                    s2 = l;
                }
                min = Math.min (min,Math.min (s1,s2));
            }
        }
        double temp = getLength (trace,time);
        if (temp <l){
            return false;
        }
        if (min >= l) {
            return true;
        }else{
            return false;
        }
    }
    public static double getTime(Lot[] trace,double len){
        double sum = 0;
        for (int i = 0; i < trace.length; i++){
            sum +=trace[i].t;
        }
        double l = 0;
        double r = sum;
        while (r-l>0.00001){
            double m =(l+r)/2;
            if (checkTime (trace,m,len)){
                r = m;
            }else{
                l = m;
            }
        }
        return r;
    }
    public static void main(String[] args) throws IOException{
        String sourceFileName = "C:\\java\\projects\\src\\labs.lab1\\trains.in";
        String destinationFileName = "C:\\java\\projects\\src\\labs.lab1\\trains.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        String split="[ ]+";
        FileWriter writer = new FileWriter("C:\\java\\projects\\src\\labs.lab1\\trains.out", true);
        int l=Integer.parseInt(br.readLine());
        int n=Integer.parseInt(br.readLine());
        //double[] t=new double[n];
        //double[] v=new double[n];
        double[] arr=new double[2];

        /*for(int i=0;i<n;i++) {
            String[] ints = br.readLine().split(split);
            arr[0]=Integer.parseInt(ints[0]);
            arr[1]=Integer.parseInt(ints[1]);
            t[i]=arr[0];
            v[i]=arr[1];
        }*/

        Lot[] trace = new Lot[n];
        for (int i = 0;i < n; i++){
            String[] ints = br.readLine().split(split);
            arr[0]=Double.parseDouble(ints[0]);
            arr[1]=Double.parseDouble(ints[1]);
            double t = arr[0];
            double v = arr[1];
            trace[i] = new Lot(v,t);
        }

        double time = getTime (trace,l);
        //out.write (String.format (Locale.ENGLISH,"%.3f",time));
        writer.write(String.format(Locale.ENGLISH,"%.3f",time));
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
