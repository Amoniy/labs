package labs.dmlab2;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class partition {
    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        FastScanner(File f) {
            try {
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }

    //C:\java\projects\src\labs\dmlab2\test
    public static void main(String[] args) throws Exception {
        String destinationFileName = "C:\\java\\projects\\src\\labs\\dmlab2\\test.out";
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("C:\\java\\projects\\src\\labs\\dmlab2\\test.out", true);
        FastScanner in = new FastScanner(new File("C:\\java\\projects\\src\\labs\\dmlab2\\test.in"));
        int v = in.nextInt();
        ArrayList<Integer> array=new ArrayList<>();
        for (int i=0;i<v-1;i++){
            array.add(1);
            writer.write("1+");
        }
        array.add(1);
        writer.write("1\n");

        while (true){
            if (array.get(0)==v){
                break;
            }

            array.set(array.size()-2,array.get(array.size()-2)+1);
            array.set(array.size()-1,array.get(array.size()-1)-1);

            if(array.get(array.size()-2)<array.get(array.size()-1)){
                while (array.get(array.size()-2)*2<=array.get(array.size()-1)) {
                    array.add(array.size() - 1, array.get(array.size() - 2));
                    array.add(array.size() - 1, array.get(array.size() - 1) - array.get(array.size() - 2));
                    array.remove(array.size() - 1);
                }

            }
            else if(array.get(array.size()-2)>array.get(array.size()-1)){
                array.set(array.size()-2,array.get(array.size()-2)+array.get(array.size()-1));
                array.remove(array.size()-1);
            }

            for (int i=0;i<array.size()-1;i++){
                writer.write(array.get(i)+"+");
            }
            writer.write(array.get(array.size()-1)+"\n");
        }
        writer.flush();
        fileOutputStream.close();
    }
}
