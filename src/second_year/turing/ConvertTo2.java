package second_year.turing;

import java.io.FileWriter;
import java.io.IOException;

public class ConvertTo2 {
    public static void main(String[] args) throws IOException {
        String destinationFileName = "convertto2.out";
        FileWriter out = new FileWriter(destinationFileName);
        String[] rules = new String[]{
                "start: ss",
                "accept: ac",
                "reject: rj",
                "blank: _",

                "ss 0 -> secondForward 0 >",
                "ss 1 -> secondForward 1 >",
                "ss 2 -> secondForward 2 >",
                "secondForward 0 -> secondForward 0 >",
                "secondForward 1 -> secondForward 1 >",
                "secondForward 2 -> secondForward 2 >",
                "secondForward _ -> mark # >",
                "mark _ -> minus 3 <", // marked second number

                "minus 0 -> minus 2 <",
                "minus 1 -> plus 0 >",
                "minus 2 -> plus 1 >",
                "minus _ -> secondClean _ >", // --
                "minus 3 -> minus 3 <",
                "minus 4 -> minus 4 <",
                "minus # -> minus # <",


                "plus 0 -> plus 0 >",
                "plus 1 -> plus 1 >",
                "plus 2 -> plus 2 >",
                "plus # -> plus # >", // (012)#(34)
                "plus 3 -> minus 4 <",
                "plus 4 -> plus 3 >",
                "plus _ -> minus 4 <", // ++

                "secondClean # -> replace _ >",
                "secondClean 0 -> secondClean _ >",
                "secondClean 1 -> secondClean _ >",
                "secondClean 2 -> secondClean _ >",

                "replace 3 -> replace 0 >",
                "replace 4 -> replace 1 >",
                "replace _ -> back _ <",

                "back 0 -> back 0 <",
                "back 1 -> back 1 <",
                "back _ -> s _ >",

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