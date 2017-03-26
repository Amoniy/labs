package labs.lab2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Stack;

public class postfix {
    public static void main(String[] args) throws Exception
    {
        String sourceFileName = "postfix.in";
        String destinationFileName = "postfix.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        String split="[ ]+";
        String[] items=br.readLine().split(split);
        Stack stack=new Stack();
        int ans;
        for(int i=0;i<items.length;i++){
            if(items[i].equals("-")){
                int second=Integer.parseInt((String)stack.pop());
                ans=Integer.parseInt((String)stack.pop())-second;
                stack.push(ans+"");
            }
            else if(items[i].equals("+")){
                int second=Integer.parseInt((String)stack.pop());
                ans=Integer.parseInt((String)stack.pop())+second;
                stack.push(ans+"");
            }
            else if(items[i].equals("*")){
                int second=Integer.parseInt((String)stack.pop());
                ans=Integer.parseInt((String)stack.pop())*second;
                stack.push(ans+"");
            }
            else {
                stack.push(items[i]);
            }
        }

        FileWriter writer = new FileWriter("postfix.out", true);
        String x=(String)stack.pop();
        writer.write(x);
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
