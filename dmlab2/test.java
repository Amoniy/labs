package labs.dmlab2;
import java.io.*;
import java.util.StringTokenizer;

public class test {
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
    }

    public static int[] generatenextbracket(int[] brackets){
        int opens=0;
        int closes=0;
        for(int i=brackets.length-1;i>=0;i--){
            if(brackets[i]==1){
                opens++;
                if(closes>opens){
                    break;
                }
            }
            else {
                closes++;
            }
        }
        int statcloses=closes;
        int statopens=opens;
        brackets[brackets.length-statcloses-statopens]=-1;
        for(int i=brackets.length-statcloses-statopens+1;i<brackets.length;i++){
            if(opens>0){
                brackets[i]=1;
                opens--;
            }
            else {
                brackets[i]=-1;
            }
        }
        return brackets;
    }
    //C:\java\projects\src\labs\dmlab2\test
    public static void main(String[] args) throws Exception {
        String destinationFileName = "C:\\java\\projects\\src\\labs\\dmlab2\\test.out";
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        FileWriter writer = new FileWriter("C:\\java\\projects\\src\\labs\\dmlab2\\test.out", true);
        FastScanner in = new FastScanner(new File("C:\\java\\projects\\src\\labs\\dmlab2\\test.in"));
        int n =(int)in.nextInt();
        long k=in.nextInt();
        int[] brackets=new int[2*n];
        for(int i=0;i<n;i++){
            brackets[i]=1;
        }
        for(int i=n;i<2*n;i++){
            brackets[i]=-1;
        }
        long[] catalan={1, 1, 2, 5, 14, 42, 132, 429, 1430, 4862, 16796, 58786, 208012, 742900, 2674440, 9694845, 35357670, 129644790, 477638700, 1767263190, 6564120420L, 24466267020L};
        long counter=0;
        for(long i=0;i<catalan[n];i++){
            //brackets=generatenextbracket(brackets);
            if(counter==k) {
                for (int j = 0; j < 2 * n; j++) {
                    if (brackets[j] == 1) {
                        writer.write("(");
                    } else {
                        writer.write(")");
                    }
                }
                writer.write("\n");
                break;
            }
            else{
                counter++;
                brackets=generatenextbracket(brackets);
            }
        }
        writer.flush();
        fileOutputStream.close();
    }
}