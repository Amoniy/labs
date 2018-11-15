package second_year.turing;

import java.io.FileWriter;
import java.io.IOException;

public class Tandem {
    public static void main(String[] args) throws IOException {
        String destinationFileName = "tandem.out";
        FileWriter out = new FileWriter(destinationFileName);
        String[] rules = new String[]{
                "start: s",
                "accept: ac",
                "reject: rj",
                "blank: _",
                "s 0 -> forward 2 >",
                "s 1 -> forward 3 >",
                "forward 0 -> forward 0 >",
                "forward 1 -> forward 1 >",

                "forward _ -> backWithClear _ <",
                "forward 8 -> backWithClear 8 <",
                "forward 9 -> backWithClear 9 <", // (23)(01)middle(01)(89)

                "backWithClear 0 -> back 8 <",
                "backWithClear 1 -> back 9 <",
                "backWithClear 2 -> rj _ ^",
                "backWithClear 3 -> rj _ ^", // не нашли пары => строка нечетной длины
                "back 0 -> back 0 <",
                "back 1 -> back 1 <",

                "back 2 -> forwardWithClear 2 >",
                "back 3 -> forwardWithClear 3 >",
                "forwardWithClear 0 -> forward 2 >",
                "forwardWithClear 1 -> forward 3 >",
                "forwardWithClear 8 -> backToStart 8 <",
                "forwardWithClear 9 -> backToStart 9 <",
                "backToStart 2 -> backToStart 2 <",
                "backToStart 3 -> backToStart 3 <",

                "backToStart _ -> check _ >", // start

                "check 2 -> checkFwd0 4 >",
                "check 3 -> checkFwd1 5 >",

                "checkFwd1 2 -> checkFwd1 2 >",
                "checkFwd1 3 -> checkFwd1 3 >",
                "checkFwd1 6 -> checkFwd1 6 >",
                "checkFwd1 7 -> checkFwd1 7 >", // (45)(23)(67)(89)
                "checkFwd1 8 -> rj _ ^",
                "checkFwd1 9 -> checkBack 7 <",

                "checkFwd0 2 -> checkFwd0 2 >",
                "checkFwd0 3 -> checkFwd0 3 >",
                "checkFwd0 6 -> checkFwd0 6 >",
                "checkFwd0 7 -> checkFwd0 7 >",
                "checkFwd0 8 -> checkBack 6 <",
                "checkFwd0 9 -> rj _ ^",

                "checkBack 6 -> checkBack 7 <",
                "checkBack 7 -> checkBack 6 <",
                "checkBack 2 -> checkBack 2 <",
                "checkBack 3 -> checkBack 3 <",

                "checkBack 4 -> check 4 >",
                "checkBack 5 -> check 5 >",
                "check 6 -> ac _ ^",
                "check 7 -> ac _ ^",


        };
        for (int i = 0; i < rules.length; i++) {
            out.write(rules[i]);
            out.write("\n");
        }
        out.close();
    }
}