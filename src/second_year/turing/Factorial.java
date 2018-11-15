package second_year.turing;

import java.io.FileWriter;
import java.io.IOException;

public class Factorial {
    public static void main(String[] args) throws IOException {
        String destinationFileName = "factorial.out";
        FileWriter out = new FileWriter(destinationFileName);
        String[] rules = new String[]{
                "start: fact()",
                "accept: ac",
                "reject: rj",
                "blank: _",
                // a!:
                // a * (a-1) = c[1]
                // c[1] * (a-2) = c[2]
                // ...
                // c[n-1] * 0 = c[n]

                "fact() 1 -> checkFor1 1 >",
                "checkFor1 _ -> oneAns _ <",
                "oneAns 1 -> oneAns 1 <",
                "oneAns _ -> ac _ >", // 1!

                "checkFor1 0 -> mark* 0 >",
                "checkFor1 1 -> mark* 1 >",
                "mark* 0 -> mark* 0 >",
                "mark* 1 -> mark* 1 >",
                "mark* _ -> copyFirst * <",
                // (01)(89)*(23)=(67)(45)

                // copy a to b
                "copyFirst 0 -> copyFirst 0 <",
                "copyFirst 1 -> copyFirst 1 <",
                "copyFirst 2 -> copyFirst 2 <",
                "copyFirst 3 -> copyFirst 3 <",
                "copyFirst * -> copyFirst * <",

                "copyFirst _ -> takeFirstBit _ >",
                "copyFirst 8 -> takeFirstBit 0 >",
                "copyFirst 9 -> takeFirstBit 1 >", // (01)(8|9)(01)*(23)

                "takeFirstBit 0 -> carry0 8 >",
                "takeFirstBit 1 -> carry1 9 >",
                "takeFirstBit * -> mark= * >",

                "carry0 0 -> carry0 0 >",
                "carry0 1 -> carry0 1 >",
                "carry0 * -> carry0 * >",
                "carry0 2 -> carry0 2 >",
                "carry0 3 -> carry0 3 >",
                "carry0 _ -> copyFirst 2 <",

                "carry1 0 -> carry1 0 >",
                "carry1 1 -> carry1 1 >",
                "carry1 * -> carry1 * >",
                "carry1 2 -> carry1 2 >",
                "carry1 3 -> carry1 3 >",
                "carry1 _ -> copyFirst 3 <",

                "mark= * -> mark= * >",
                "mark= 2 -> mark= 2 >",
                "mark= 3 -> mark= 3 >",
                "mark= _ -> putZero = >",
                "putZero _ -> initialMinus 4 <",
                // end of copy a to b

                // initialization of b and save of b
                // b|a*b=c
                "initialMinus = -> initialMinus = <",
                "initialMinus 2 -> initialMinus 3 <",
                "initialMinus 3 -> mark| 2 <",

                "initialMinus * -> BREAK * <",

                // copy b
                "mark| 2 -> mark| 2 <",
                "mark| 3 -> mark| 3 <",
                "mark| * -> mark| * <",
                "mark| 0 -> mark| 0 <",
                "mark| 1 -> mark| 1 <",
                "mark| _ -> copySecond | >",

                "copySecond | -> copySecond | >",
                "copySecond 0 -> copySecond 0 >",
                "copySecond 1 -> copySecond 1 >",
                "copySecond * -> copySecond * >",
                "copySecond 2 -> copySecond 2 >",
                "copySecond 3 -> copySecond 3 >",
                "copySecond z -> takeSecondBit 2 <",
                "copySecond o -> takeSecondBit 3 <",
                "copySecond = -> takeSecondBit = <",

                "takeSecondBit 2 -> carry2 z <",
                "takeSecondBit 3 -> carry3 o <",

                "takeSecondBit * -> toMult * >",

                // (23)|(01)*(23)o/z(23)=4
                "carry2 2 -> carry2 2 <",
                "carry2 3 -> carry2 3 <",
                "carry2 * -> carry2 * <",
                "carry2 0 -> carry2 0 <",
                "carry2 1 -> carry2 1 <",
                "carry2 | -> carry2 | <",
                "carry2 _ -> copySecond 2 >",

                "carry3 2 -> carry3 2 <",
                "carry3 3 -> carry3 3 <",
                "carry3 * -> carry3 * <",
                "carry3 0 -> carry3 0 <",
                "carry3 1 -> carry3 1 <",
                "carry3 | -> carry3 | <",
                "carry3 _ -> copySecond 3 >",
                // end of copy b

                "toMult 2 -> toMult 2 >",
                "toMult 3 -> toMult 3 >",
                "toMult = -> toMult = >",
                "toMult 4 -> toMult m >",
                "toMult 5 -> toMult m >",
                "toMult _ -> cut _ <",
                "cut m -> cut _ <",
                "cut = -> putFirst4 = >",
                "putFirst4 _ -> mult 4 <",

                // multiplication
                // начинаем с правого конца второго множителя с команды минус

                "mult = -> minus = <", // TODO: check this line
                // (01 89 if modified)*(23)=(45) (67 if modified)
                // (01)(89)*(23)=(67)(45)

                //
                "minus = -> minus = <", // skip
                "minus 4 -> minus 4 <", // skip
                "minus 5 -> minus 5 <", // skip
                "minus 2 -> minus 3 <", // carry minus
                "minus 3 -> takeBit 2 <", // actual minus
                "minus * -> cleanThrees * >", // a * 22222 = c, c = ans

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
                "takeBit | -> clean | >",

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

                // factorial code below
                // b|a*b=c

                // b | a * b = c
                // b | a * (2) = c'
                // b | a * b = c'
                // _ * b = c'
                // _ c' * b = c'
                // _ c' * b-- = c'
                // b' | c' * b' = c'

                // (23)|(01)*(2)=(45)
                "cleanThrees 3 -> cleanThrees o >",
                "cleanThrees = -> copyToSecond = <",

                // (23)|(01)*(o)=(45)
                "copyToSecond o -> copyToSecond o <",
                "copyToSecond * -> copyToSecond * <",
                "copyToSecond 0 -> copyToSecond 0 <",
                "copyToSecond 1 -> copyToSecond 1 <",
                "copyToSecond | -> copyToSecond | <",
                "copyToSecond 2 -> copyToSecond 2 <",
                "copyToSecond 3 -> copyToSecond 3 <",
                "copyToSecond _ -> takeCopyBit _ >",
                "takeCopyBit 2 -> carry2Right _ >",
                "takeCopyBit 3 -> carry3Right _ >",
                //
                "takeCopyBit | -> checkToClearFirst _ >",
                "checkToClearFirst 0 -> checkToClearFirst 0 >",
                "checkToClearFirst 1 -> checkToClearFirst 1 >",
                "checkToClearFirst * -> checkToClearFirst * >",
                "checkToClearFirst 2 -> checkToClearFirst 2 >",
                "checkToClearFirst 3 -> moveToClearFirst 3 <",
                "checkToClearFirst = -> BREAK = >",
                "moveToClearFirst 2 -> moveToClearFirst 2 <",
                "moveToClearFirst 3 -> moveToClearFirst 3 <",
                "moveToClearFirst * -> moveToClearFirst * <",
                "moveToClearFirst 0 -> moveToClearFirst 0 <",
                "moveToClearFirst 1 -> moveToClearFirst 1 <",
                "moveToClearFirst _ -> clearFirst _ >",


                // (23)|(01)*(23)=(45)
                "carry2Right 2 -> carry2Right 2 >",
                "carry2Right 3 -> carry2Right 3 >",
                "carry2Right | -> carry2Right | >",
                "carry2Right 0 -> carry2Right 0 >",
                "carry2Right 1 -> carry2Right 1 >",
                "carry2Right * -> carry2Right * >",
                "carry2Right o -> copyToSecond 2 <",

                "carry3Right 2 -> carry3Right 2 >",
                "carry3Right 3 -> carry3Right 3 >",
                "carry3Right | -> carry3Right | >",
                "carry3Right 0 -> carry3Right 0 >",
                "carry3Right 1 -> carry3Right 1 >",
                "carry3Right * -> carry3Right * >",
                "carry3Right o -> copyToSecond 3 <",

                // *(23)=(45)
                "clearFirst 0 -> clearFirst _ >",
                "clearFirst 1 -> clearFirst _ >",
                "clearFirst * -> copyThird * >",

                // (01)*(23)=(zo)(45)
                "copyThird 0 -> copyThird 0 >",
                "copyThird 1 -> copyThird 1 >",
                "copyThird * -> copyThird * >",
                "copyThird 2 -> copyThird 2 >",
                "copyThird 3 -> copyThird 3 >",
                "copyThird = -> copyThird = >",
                "copyThird z -> copyThird z >",
                "copyThird o -> copyThird o >",
                "copyThird 4 -> carry4 z <",
                "copyThird 5 -> carry5 o <",
                "copyThird _ -> skipToEndOfSecondToInitialMinus _ <",

                "carry4 z -> carry4 z <",
                "carry4 o -> carry4 o <",
                "carry4 = -> carry4 = <",
                "carry4 2 -> carry4 2 <",
                "carry4 3 -> carry4 3 <",
                "carry4 * -> carry4 * <",
                "carry4 0 -> carry4 0 <",
                "carry4 1 -> carry4 1 <",
                "carry4 _ -> copyThird 0 >",

                "carry5 z -> carry5 z <",
                "carry5 o -> carry5 o <",
                "carry5 = -> carry5 = <",
                "carry5 2 -> carry5 2 <",
                "carry5 3 -> carry5 3 <",
                "carry5 * -> carry5 * <",
                "carry5 0 -> carry5 0 <",
                "carry5 1 -> carry5 1 <",
                "carry5 _ -> copyThird 1 >",

                // (01)*(23)=(zo)
                "skipToEndOfSecondToInitialMinus z -> skipToEndOfSecondToInitialMinus 4 <",
                "skipToEndOfSecondToInitialMinus o -> skipToEndOfSecondToInitialMinus 5 <",
                "skipToEndOfSecondToInitialMinus = -> initialMinus = <",
                // (23)|(01)*(23)=(01)

                //
                "BREAK 4 -> prepareToAns _ <",
                "prepareToAns * -> prepareToAns _ <",
                "prepareToAns 2 -> prepareToAns _ <",
                "prepareToAns = -> prepareToAns _ <",
                "prepareToAns 0 -> prepareToAns 0 <",
                "prepareToAns 1 -> prepareToAns 1 <",
                "prepareToAns _ -> ac _ >",

        };
        for (int i = 0; i < rules.length; i++) {
            out.write(rules[i]);
            out.write("\n");
        }
        out.close();
    }
}