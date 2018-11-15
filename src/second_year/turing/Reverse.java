package second_year.turing;

import java.io.FileWriter;
import java.io.IOException;

public class Reverse {
    public static void main(String[] args) throws IOException {
        String destinationFileName = "reverse.out";
        FileWriter out = new FileWriter(destinationFileName);
        String[] rules = new String[]{
                "start: s",
                "accept: ac",
                "reject: rj",
                "blank: _",
                "s _ -> ac _ <",
                "s 0 -> forward 0 >",
                "s 1 -> forward 1 >",
                "forward 0 -> forward 0 >",
                "forward 1 -> forward 1 >",
                "forward _ -> clearForward _ <",
                "clearForward 0 -> forward0 2 >",
                "clearForward 1 -> forward1 3 >",

                "forward0 2 -> forward0 2 >",
                "forward0 3 -> forward0 3 >",
                "forward0 4 -> forward0 4 >",
                "forward0 5 -> forward0 5 >",
                "forward0 _ -> toStart 4 <",

                "forward1 2 -> forward1 2 >",
                "forward1 3 -> forward1 3 >",
                "forward1 4 -> forward1 4 >",
                "forward1 5 -> forward1 5 >",
                "forward1 _ -> toStart 5 <",

                "toStart 2 -> toStart 2 <",
                "toStart 3 -> toStart 3 <",
                "toStart 4 -> toStart 4 <",
                "toStart 5 -> toStart 5 <",

                "toStart 0 -> forward0 2 >",
                "toStart 1 -> forward1 3 >",
                "toStart _ -> clean _ >",
                "clean 2 -> clean _ >",
                "clean 3 -> clean _ >",
                "clean 4 -> clean 0 >",
                "clean 5 -> clean 1 >",
                "clean _ -> backAns _ <",

                "backAns 0 -> backAns 0 <",
                "backAns 1 -> backAns 1 <",
                "backAns _ -> ac _ >",


        };
        for (int i = 0; i < rules.length; i++) {
            out.write(rules[i]);
            out.write("\n");
        }
        out.close();
    }
}