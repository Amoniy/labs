package second_year.turing;

import java.io.FileWriter;
import java.io.IOException;

public class InfixLogic {

    public static void main(String[] args) throws IOException {
        String destinationFileName = new Object() { }.getClass().getEnclosingClass()
                .getSimpleName().toLowerCase() + ".out";
        FileWriter out = new FileWriter(destinationFileName);
        String[] rules = new String[]{
                "3",
                "S 0 _ _ -> S _ > 0 > _ ^",
                "S 1 _ _ -> S _ > 1 > _ ^",


                "S ( _ _ -> S _ > _ ^ ( >", // opening bracket


                "S o _ _ -> or o ^ _ ^ _ <", // or

                "or o _ ( -> pushOr o ^ _ ^ ( >",
                "or o _ o -> or o ^ o > _ <",
                "or o _ a -> or o ^ a > _ <",
                "or o _ _ -> S _ > _ ^ o >",

                "pushOr o _ _ -> S _ > _ ^ o >",


                "S a _ _ -> and a ^ _ ^ _ <", // and

                "and a _ ( -> pushAnd a ^ _ ^ ( >",
                "and a _ o -> pushAnd a ^ _ ^ o >",
                "and a _ a -> and a ^ a > _ <",
                "and a _ _ -> S _ > _ ^ a >",

                "pushAnd a _ _ -> S _ > _ ^ a >",


                "S ) _ _ -> close ) ^ _ ^ _ <", // closing bracket

                "close ) _ ( -> S _ > _ ^ _ ^",
                "close ) _ o -> close ) ^ o > _ <",
                "close ) _ a -> close ) ^ a > _ <",


                "S _ _ _ -> fin _ ^ _ ^ _ <", // end of expression

                "fin _ _ o -> fin _ ^ o > _ <",
                "fin _ _ a -> fin _ ^ a > _ <",
                "fin _ _ _ -> prepareToCount _ ^ _ < _ ^",

                "prepareToCount _ 0 _ -> prepareToCount _ ^ 0 < _ ^",
                "prepareToCount _ 1 _ -> prepareToCount _ ^ 1 < _ ^",
                "prepareToCount _ o _ -> prepareToCount _ ^ o < _ ^",
                "prepareToCount _ a _ -> prepareToCount _ ^ a < _ ^",
                "prepareToCount _ _ _ -> count _ ^ _ > _ ^",


                "count _ 0 _ -> count _ ^ _ > 0 >",
                "count _ 1 _ -> count _ ^ _ > 1 >",
                "count _ o _ -> countLast _ ^ o ^ _ <", // o = or
                "count _ a _ -> countLast _ ^ a ^ _ <", // a = and

                "countLast _ o 0 -> countLast0 _ ^ o ^ _ <",
                "countLast _ o 1 -> countLast1 _ ^ o ^ _ <",

                "countLast _ a 0 -> countLast0 _ ^ a ^ _ <",
                "countLast _ a 1 -> countLast1 _ ^ a ^ _ <",

                "countLast0 _ o 0 -> count _ ^ _ > 0 >",
                "countLast0 _ o 1 -> count _ ^ _ > 1 >",
                "countLast0 _ a 0 -> count _ ^ _ > 0 >",
                "countLast0 _ a 1 -> count _ ^ _ > 0 >",

                "countLast1 _ o 0 -> count _ ^ _ > 1 >",
                "countLast1 _ o 1 -> count _ ^ _ > 1 >",
                "countLast1 _ a 0 -> count _ ^ _ > 0 >",
                "countLast1 _ a 1 -> count _ ^ _ > 1 >",

                "count _ _ _ -> ans _ ^ _ ^ _ <",
                "ans _ _ 0 -> AC 0 ^ _ ^ _ ^",
                "ans _ _ 1 -> AC 1 ^ _ ^ _ ^",

        };
        for (int i = 0; i < rules.length; i++) {
            out.write(rules[i]);
            out.write("\n");
        }
        out.close();
    }
}
