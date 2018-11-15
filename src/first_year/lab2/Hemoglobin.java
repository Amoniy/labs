package first_year.lab2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;


public class Hemoglobin {
    public static void main(String[] args) throws Exception {
        String sourceFileName = "Hemoglobin.in";
        String destinationFileName = "Hemoglobin.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        FileWriter writer = new FileWriter("Hemoglobin.out", true);
        ArrayList<Integer> list = new ArrayList<Integer>();
        ArrayList<Integer> prefix = new ArrayList<Integer>();
        int n = Integer.parseInt(br.readLine());
        list.add(0);
        prefix.add(0);
        for (int i = 0; i < n; i++) {
            char[] str = br.readLine().toCharArray();
            if (str[0] == '+') {
                String num = "";
                for (int j = 1; j < str.length; j++) {
                    num += str[j];
                }
                list.add(Integer.parseInt(num));
                prefix.add(prefix.get(prefix.size() - 1) + list.get(list.size() - 1));
            } else if (str[0] == '-') {
                writer.write("" + list.get(list.size() - 1) + "\n");
                list.remove(list.size() - 1);
                prefix.remove(prefix.size() - 1);
            } else {
                String num = "";
                for (int j = 1; j < str.length; j++) {
                    num += str[j];
                }

                writer.write("" + (prefix.get(prefix.size() - 1) - prefix.get(prefix.size() - 1 - Integer.parseInt(num))) + "\n");
            }
        }

        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
