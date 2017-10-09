package lab2;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Stack;

public class stack_min {
    public static class Element {
        int prevmin;
        int value;

        public Element(int value, int prevmin) {
            this.value = value;
            this.prevmin = prevmin;
        }
    }

    public static void main(String[] args) throws Exception {
        String sourceFileName = "stack-min.in";
        String destinationFileName = "stack-min.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        FileWriter writer = new FileWriter("stack-min.out", true);
        Stack<Element> stack = new Stack();
        String split = "[ ]+";

        int n = Integer.parseInt(br.readLine());

        int[] arr = new int[2];
        String[] ints = br.readLine().split(split);
        for (int i = 0; i < 2; i++) {
            arr[i] = Integer.parseInt(ints[i]);
        }

        Element first = new Element(arr[1], arr[1]);
        int min = first.value;
        stack.push(first);

        for (int i = 0; i < n - 1; i++) {
            ints = br.readLine().split(split);
            for (int j = 0; j < ints.length; j++) {
                arr[j] = Integer.parseInt(ints[j]);
            }
            int operation = arr[0];

            if (operation == 1) {
                if (stack.size() != 0) {
                    stack.push(new Element(arr[1], min));
                    if (min > arr[1]) {
                        min = arr[1];
                    }
                } else {
                    first = new Element(arr[1], arr[1]);
                    min = first.value;
                    stack.push(first);
                }
            } else if (operation == 2) {
                min = stack.pop().prevmin;
            } else {
                writer.write("" + min + "\n");
            }
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
