package labs.dmlab2;

import java.io.*;
import java.util.StringTokenizer;

public class part2num {
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

        long nextInt() {
            return Long.parseLong(next());
        }
        int nextInte() {
            return Integer.parseInt(next());
        }
    }
    static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        public void print(Object...objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0)
                    writer.print(' ');
                writer.print(objects[i]);
            }
        }

        public void printLine(Object...objects) {
            print(objects);
        }
        public void printLineln(Object...objects) {
            print(objects);
            writer.println();
        }

        public void close() {
            writer.close();
        }

        public void flush() {
            writer.flush();
        }

    }
    //C:\java\projects\src\labs\dmlab2\test
    public static void main(String[] args) throws Exception {
        String destinationFileName = "part2num.out";
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("part2num.out", true);
        String sourceFileName = "part2num.in";
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));



        int[] ans=new int[101];
        int len=0;
        int n=0;
        String str = "";
        while (true) {
            int c = br.read();
            if (c == -1 || c == 13) {

                ans[len+1]=Integer.parseInt(str);
                n+=ans[len+1];
                len++;
                break;
            }
            if (c == 43) {
                ans[len+1]=Integer.parseInt(str);
                str = "";
                n+=ans[len+1];
                len++;
            } else {
                str += (c - 48);
            }
        }

//filling d[][]
        long[][] d=new long[n+2][n+2];
        for(int i=1;i<n+2;i++){
            d[i][i]=1;
        }
        for(int i=2;i<n+2;i++){
            for(int j=i-1;j>0;j--){
                d[i][j]=d[i][j+1]+d[i-j][j];
            }
            d[i][0]=d[i][1];
        }


        long answer=0;
        ans[0]=1;
        int sum=n;
        n=0;
        for(int i=len;i>0;i--){
            n+=ans[i];
            answer+=d[n][ans[i-1]]-d[n][ans[i]];

        }





        writer.write(answer+"");
        writer.flush();

    }
}
