package second_year.turing;

import java.io.FileWriter;
import java.io.IOException;

public class Sorting {

    public static void main(String[] args) throws IOException {
        String destinationFileName = new Object() { }.getClass().getEnclosingClass()
                .getSimpleName().toLowerCase() + ".out";
        FileWriter out = new FileWriter(destinationFileName);
        String[] rules = new String[]{
                "3", // line, saved int, answer
                // | 1 1 0 | 1 0 | 1 1 | 1 1 1 | 1 |
                "S 0 _ _ -> S 0 < _ ^ _ ^",
                "S 1 _ _ -> S 1 < _ ^ _ ^",
                "S _ _ _ -> copy | > | > | >",


                "copy 1 _ _ -> copy 1 > 1 > _ ^",
                "copy 0 _ _ -> copy 0 > 0 > _ ^",
                "copy 1 _ _ -> copy 1 > 1 > _ ^",
                "copy | _ _ -> copy | > | > _ ^",
                "copy _ _ _ -> minus | < | < _ ^",


                "minus 0 0 _ -> minus 1 < 0 < _ ^",
                "minus 1 0 _ -> skipToNextMinus 0 < 0 < _ ^",

                "minus 0 1 _ -> minus 1 < 1 < _ ^",
                "minus 1 1 _ -> skipToNextMinus 0 < 1 < _ ^",

                "minus | | _ -> addToAns | > | > _ ^",
                "minus s s _ -> skipToNextMinus s < s < _ ^",
                "minus _ _ _ -> checkAllZero _ > _ > _ ^",


                "addToAns 1 0 _ -> addToAns s > s > 0 >", // 11111..111 after minus = 0
                "addToAns 1 1 _ -> addToAns s > s > 1 >",
                "addToAns | | _ -> skipToNextMinus | < | < | >",


                "skipToNextMinus 0 0 _ -> skipToNextMinus 0 < 0 < _ ^",
                "skipToNextMinus 0 1 _ -> skipToNextMinus 0 < 1 < _ ^",
                "skipToNextMinus 1 0 _ -> skipToNextMinus 1 < 0 < _ ^",
                "skipToNextMinus 1 1 _ -> skipToNextMinus 1 < 1 < _ ^",
                "skipToNextMinus s s _ -> skipToNextMinus s < s < _ ^",
                "skipToNextMinus | | _ -> minus | < | < _ ^",


                "checkAllZero 0 0 _ -> skipToEndToPlus 0 > 0 > _ ^",
                "checkAllZero 0 1 _ -> skipToEndToPlus 0 > 1 > _ ^",
                "checkAllZero 1 0 _ -> skipToEndToPlus 1 > 0 > _ ^",
                "checkAllZero 1 1 _ -> skipToEndToPlus 1 > 1 > _ ^",
                "checkAllZero s s _ -> checkAllZero s > s > _ ^",
                "checkAllZero | | _ -> checkAllZero | > | > _ ^",
                "checkAllZero _ _ _ -> clearLines _ < _ < _ <",


                "skipToEndToPlus 0 0 _ -> skipToEndToPlus 0 > 0 > _ ^",
                "skipToEndToPlus 0 1 _ -> skipToEndToPlus 0 > 1 > _ ^",
                "skipToEndToPlus 1 0 _ -> skipToEndToPlus 1 > 0 > _ ^",
                "skipToEndToPlus 1 1 _ -> skipToEndToPlus 1 > 1 > _ ^",
                "skipToEndToPlus s s _ -> skipToEndToPlus s > s > _ ^",
                "skipToEndToPlus | | _ -> skipToEndToPlus | > | > _ ^",
                "skipToEndToPlus _ _ _ -> skipToNextMinus _ < _ < _ ^",


                "clearLines | | | -> clearLines | < _ < _ <",
                "clearLines s s | -> clearLines | < _ < _ <",
                "clearLines s s 0 -> clearLines 0 < _ < _ <",
                "clearLines | | 0 -> clearLines 0 < _ < _ <",
                "clearLines s s 1 -> clearLines 1 < _ < _ <",
                "clearLines | | 1 -> clearLines 1 < _ < _ <",
                "clearLines _ _ _ -> clearFirst| _ > _ ^ _ ^",
                "clearFirst| | _ _ -> skipToEnd _ > _ ^ _ ^",
                "skipToEnd | _ _ -> skipToEnd | > _ ^ _ ^",
                "skipToEnd 0 _ _ -> skipToEnd 0 > _ ^ _ ^",
                "skipToEnd 1 _ _ -> skipToEnd 1 > _ ^ _ ^",
                "skipToEnd _ _ _ -> clearLast| _ < _ ^ _ ^",
                "clearLast| | _ _ -> skipToStart _ < _ ^ _ ^",
                "skipToStart | _ _ -> skipToStart | < _ ^ _ ^",
                "skipToStart 0 _ _ -> skipToStart 0 < _ ^ _ ^",
                "skipToStart 1 _ _ -> skipToStart 1 < _ ^ _ ^",
                "skipToStart _ _ _ -> AC _ > _ ^ _ ^",

        };
        for (int i = 0; i < rules.length; i++) {
            out.write(rules[i]);
            out.write("\n");
        }
        out.close();
    }
}
