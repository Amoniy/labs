package first_year.lab2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Stack;

public class Brackets {
    public static void main(String[] args) throws Exception {
        String sourceFileName = "Brackets.in";
        String destinationFileName = "Brackets.out";
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
        FileWriter writer = new FileWriter("Brackets.out", true);
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
