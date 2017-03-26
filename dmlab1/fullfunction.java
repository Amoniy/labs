package labs.dmlab1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class fullfunction {
    static boolean iszero(int[] args){
        if(args[0]==1){
            return false;
        }
        return true;
    }
    static boolean isone(int[] args){
        if(args[args.length-1]==0){
            return false;
        }
        return true;
    }
    static boolean isdual(int[] args){
        for(int i=0;i<args.length;i++){
            if(args[i]==args[args.length-1-i])
                return false;
        }
        return true;
    }
    static boolean ismonotonious(int[] args){
        for(int i=1;i<args.length/2;i*=2){
            int pointer=0;
            for(int k=0;k<args.length/(i*2);k++) {
                int sumleft = 0;
                int sumright = 0;
                for (int j = 0; j < i; j++) {
                    sumleft+=args[pointer+j];
                }
                pointer+=i;
                for (int j = 0; j < i; j++) {
                    sumright+=args[pointer+j];
                }
                pointer+=i;
                if (sumright<sumleft)
                    return false;
            }
        }
        if(args.length==2){
            if(args[0]==1 && args[1]==0)
                return false;
        }
        return true;
    }
    static boolean islinear(int[] args){
        ArrayList<Integer> deck=new ArrayList<Integer>();
        for(int i=0;i<args.length;i++){
            deck.add(args[i]);
        }
        int[] ans=new int[args.length];
        ans[0]=deck.get(0);
        for(int i=0;i<args.length-1;i++){
            int k=deck.size();
            for(int j=0;j<k-1;j++){
                int x=deck.get(deck.size()-1);
                if((x+deck.get(deck.size()-2))%2==1){
                    deck.add(0,1);
                    deck.remove(deck.size()-1);
                }
                else {
                    deck.add(0,0);
                    deck.remove(deck.size()-1);
                }
            }
            deck.remove(deck.size()-1);
            ans[i+1]=deck.get(0);
        }
        if(ans.length>2) {
            if (ans[3] == 1) {
                return false;
            }
        }
        int pointer=5;
        int sum=0;
        for(int i=4;i<args.length;i*=2){
            for(int j=0;j<i-1;j++) {
                sum += ans[pointer];
                pointer++;
            }
            pointer++;
        }
        if(sum>0)
            return false;
        return true;
    }
    public static void main(String[] args)throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n=Integer.parseInt(br.readLine());
        String split="[ ]+";
        int[] answer=new int[5];
        for(int i=0;i<5;i++){
            answer[i]=0;
        }
        int ansss=0;
        for (int i=0;i<n;i++){
            String[] temp=br.readLine().split(split);
            char[] chars=temp[1].toCharArray();
            int[] arguments=new int[chars.length];
            for(int j=0;j<chars.length;j++){
                arguments[j]=Integer.parseInt(""+chars[j]);
            }
            if (answer[0]==0){
                if (!iszero(arguments)){
                    answer[0]=1;
                    ansss++;
                }
            }
            if (answer[1]==0){
                if (!isone(arguments)){
                    answer[1]=1;
                    ansss++;
                }
            }
            if (answer[2]==0){
                if (!isdual(arguments)){
                    answer[2]=1;
                    ansss++;
                }
            }
            if (answer[3]==0){
                if (!ismonotonious(arguments)){
                    answer[3]=1;
                    ansss++;
                }
            }
            if (answer[4]==0){
                if (!islinear(arguments)){
                    answer[4]=1;
                    ansss++;
                }
            }
        }
        if(ansss==5){
            System.out.println("YES");
        }
        else {
            System.out.println("NO");
        }
    }
}
