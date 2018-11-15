package second_year.turing;

import java.io.FileWriter;
import java.io.IOException;

public class Multiplication {
    public static void main(String[] args) throws IOException {
        String destinationFileName = new Object() { }.getClass().getEnclosingClass()
                .getSimpleName().toLowerCase() + ".out";
        FileWriter out = new FileWriter(destinationFileName);
        String[] rules = new String[]{
                "start: mult",
                "accept: ac",
                "reject: rj",
                "blank: _", // (a)*(b--)=(c+=a)

                "mult 0 -> zeroAns _ >",
                "zeroAns 0 -> zeroAns _ >",
                "zeroAns 1 -> zeroAns _ >",
                "zeroAns * -> zeroAns _ >",
                "zeroAns _ -> ans 0 <",
                "ans _ -> ac _ >", // a = 0

                "mult 1 -> prepare 1 >",
                "prepare 0 -> prepare 0 >",
                "prepare 1 -> prepare 1 >",
                "prepare * -> prepare * >",
                "prepare _ -> prepareZeroAns = >",
                "prepareZeroAns _ -> prepareSecond 4 <", // a * b = 0 - initialization

                "prepareSecond = -> prepareSecond = <",
                "prepareSecond 0 -> prepareSecond 2 <",
                "prepareSecond 1 -> prepareSecond 3 <",
                "prepareSecond * -> moveToFirstMinus * >",

                "moveToFirstMinus 2 -> moveToFirstMinus 2 >",
                "moveToFirstMinus 3 -> moveToFirstMinus 3 >",
                "moveToFirstMinus = -> minus = <",

                // algorith: b--, c+=a
                // (01 89 if modified)*(23)=(45) (67 if modified)
                // (01)(89)*(23)=(67)(45)

                //
                "minus = -> minus = <", // skip
                "minus 4 -> minus 4 <", // skip
                "minus 5 -> minus 5 <", // skip
                "minus 2 -> minus 3 <", // carry minus
                "minus 3 -> takeBit 2 <", // actual minus
                "minus * -> break * >", // a * 22222 = c, c = ans

                //
                "takeBit 2 -> takeBit 2 <", // take current bit from a
                "takeBit 3 -> takeBit 3 <",
                "takeBit * -> takeBit * <",
                "takeBit 8 -> takeBit 8 <",
                "takeBit 9 -> takeBit 9 <",
                "takeBit 4 -> takeBit 4 <",
                "takeBit 5 -> takeBit 5 <",
                "takeBit 6 -> takeBit 6 <",
                "takeBit 7 -> takeBit 7 <",
                "takeBit = -> takeBit = <",
                "takeBit 0 -> plus0 8 >",
                "takeBit 1 -> plus1 9 >",
                "takeBit _ -> clean _ >",

                //
                "plus0 8 -> plus0 8 >",
                "plus0 9 -> plus0 9 >",
                "plus0 * -> plus0 * >",
                "plus0 2 -> plus0 2 >",
                "plus0 3 -> plus0 3 >",
                "plus0 = -> plus0 = >",
                "plus0 6 -> plus0 6 >",
                "plus0 7 -> plus0 7 >", // skip

                "plus0 _ -> takeBit 6 <",
                "plus0 4 -> takeBit 6 <",
                "plus0 5 -> takeBit 7 <", // +1

                "plus1 8 -> plus1 8 >",
                "plus1 9 -> plus1 9 >",
                "plus1 * -> plus1 * >",
                "plus1 2 -> plus1 2 >",
                "plus1 3 -> plus1 3 >",
                "plus1 = -> plus1 = >",
                "plus1 6 -> plus1 6 >",
                "plus1 7 -> plus1 7 >", // skip

                "plus1 4 -> takeBit 7 <",
                "plus1 _ -> takeBit 7 <",
                "plus1 5 -> plusCarry 6 >", // +1

                "plusCarry 4 -> takeBit 5 <",
                "plusCarry 5 -> plusCarry 4 >",
                "plusCarry _ -> takeBit 5 <", // add carry

                //
                "clean * -> clean * >", // skip
                "clean 2 -> clean 2 >",
                "clean 3 -> clean 3 >",
                "clean = -> clean = >",

                "clean 8 -> clean 0 >", // cleaning
                "clean 9 -> clean 1 >",
                "clean 6 -> clean 4 >",
                "clean 7 -> clean 5 >",

                "clean 4 -> minus 4 <", // finished cleaning
                "clean 5 -> minus 5 <",
                "clean _ -> minus _ <",

                // preparing ans
                "break = -> break = >",
                "break 2 -> break 2 >",
                "break 3 -> break 3 >",
                "break 4 -> break 0 >",
                "break 5 -> break 1 >",
                "break _ -> moveToStart _ <",

                "moveToStart 0 -> moveToStart 0 <",
                "moveToStart 1 -> moveToStart 1 <",
                "moveToStart = -> moveToStart = <",
                "moveToStart 2 -> moveToStart 2 <",
                "moveToStart 3 -> moveToStart 3 <",
                "moveToStart * -> moveToStart * <",
                "moveToStart _ -> clearForAns _ >",

                "clearForAns 0 -> clearForAns _ >",
                "clearForAns 1 -> clearForAns _ >",
                "clearForAns * -> clearForAns _ >",
                "clearForAns 2 -> clearForAns _ >",
                "clearForAns 3 -> clearForAns _ >",
                "clearForAns = -> reverse _ >",

                // reversing
                "reverse 0 -> forward 0 >",
                "reverse 1 -> forward 1 >",
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
                "toStart _ -> cleanReverse _ >",
                "cleanReverse 2 -> cleanReverse _ >",
                "cleanReverse 3 -> cleanReverse _ >",
                "cleanReverse 4 -> cleanReverse 0 >",
                "cleanReverse 5 -> cleanReverse 1 >",
                "cleanReverse _ -> backAns _ <",

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