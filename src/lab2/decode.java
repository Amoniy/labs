package lab2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Stack;


public class decode {
    public static void main(String[] args) throws Exception {
        String sourceFileName = "decode.in";
        String destinationFileName = "decode.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        char[] bracks = br.readLine().toCharArray();
        Stack<Character> stack = new Stack();
        for (int i = 0; i < bracks.length; i++) {
            if (stack.empty()) {
                stack.push(bracks[i]);
            } else {
                if (stack.peek() == bracks[i]) {
                    stack.pop();
                } else {
                    stack.push(bracks[i]);
                }
            }
        }
        Stack stack2 = new Stack();
        FileWriter writer = new FileWriter("decode.out", true);
        while (stack.size() != 0) {
            stack2.push(stack.pop());
        }
        while (!stack2.empty()) {
            writer.write(String.valueOf(stack2.pop()));

        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
