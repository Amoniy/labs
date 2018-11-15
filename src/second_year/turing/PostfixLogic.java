package second_year.turing;

import java.io.FileWriter;
import java.io.IOException;

public class PostfixLogic {
    public static void main(String[] args) throws IOException {
        String destinationFileName = new Object() { }.getClass().getEnclosingClass()
                .getSimpleName().toLowerCase() + ".out";
        FileWriter out = new FileWriter(destinationFileName);
        String[] rules = new String[]{
                "2",
                "S 0 _ -> S _ > 0 >",
                "S 1 _ -> S _ > 1 >",
                "S o _ -> countLast o ^ _ <", // o = or
                "S a _ -> countLast a ^ _ <", // a = and

                "countLast o 0 -> countLast0 o ^ _ <",
                "countLast o 1 -> countLast1 o ^ _ <",

                "countLast a 0 -> countLast0 a ^ _ <",
                "countLast a 1 -> countLast1 a ^ _ <",

                "countLast0 o 0 -> S _ > 0 >",
                "countLast0 o 1 -> S _ > 1 >",
                "countLast0 a 0 -> S _ > 0 >",
                "countLast0 a 1 -> S _ > 0 >",

                "countLast1 o 0 -> S _ > 1 >",
                "countLast1 o 1 -> S _ > 1 >",
                "countLast1 a 0 -> S _ > 0 >",
                "countLast1 a 1 -> S _ > 1 >",

                "S _ _ -> ans _ ^ _ <",
                "ans _ 0 -> AC 0 ^ _ ^",
                "ans _ 1 -> AC 1 ^ _ ^",

        };
        for (int i = 0; i < rules.length; i++) {
            out.write(rules[i]);
            out.write("\n");
        }
        out.close();
    }
}
