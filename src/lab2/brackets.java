package lab2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Stack;

public class brackets {
    public static void main(String[] args) throws Exception {
        String sourceFileName = "C:\\java\\projects\\src\\labs.lab2\\brackets.in";
        String destinationFileName = "C:\\java\\projects\\src\\labs.lab2\\brackets.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        char[] bracks = br.readLine().toCharArray();
        Stack stack = new Stack();
        for (int i = 0; i < bracks.length; i++) {
            if (bracks[i] == '(' || bracks[i] == '{' || bracks[i] == '[' || stack.empty()) {
                stack.push(bracks[i]);
            } else {
                if (bracks[i] == ')' && stack.peek().equals('(')) {
                    stack.pop();
                } else if (bracks[i] == ']' && stack.peek().equals('[')) {
                    stack.pop();
                } else if (bracks[i] == '}' && stack.peek().equals('{')) {
                    stack.pop();
                } else {
                    stack.push(bracks[i]);
                }
            }
        }
        FileWriter writer = new FileWriter("C:\\java\\projects\\src\\labs.lab2\\brackets.out", true);
        if (stack.empty()) {
            writer.write("YES");
        } else {
            writer.write("NO");
        }
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
