package labs.dmlab1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class impossible {
    public static void main(String[] args)throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        String split = "[ ]+";
        String[] a=br.readLine().split(split);
        long s=Long.parseLong(br.readLine());
        long[] longs=new long[n];
        for (int i=0;i<n;i++){
            longs[i]=Long.parseLong(a[i]);
        }
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        int[][] matrix=new int[n+1][33];
        for(int i=0;i<=n;i++){
            matrix[i][32]=0;
        }
        String ans="";
        if (s==0){
            System.out.print("1&~1");
        }
        else {
            for(int i=0;i<32;i++){
                for(int j=0;j<n;j++){
                    matrix[j][i]=(int)(longs[j]%2);
                    longs[j]/=2;
                }
                matrix[n][i]=(int)(s%2);
                s/=2;//filled the matrix
            }

            for(int i=0;i<33;i++){
                String temp="";
                for(int j=0;j<n;j++){
                    temp+=matrix[j][i];
                }
                if(!map.containsKey(temp)){
                    map.put(temp,matrix[n][i]);
                }
                if(map.containsKey(temp)){
                    if (map.get(temp)!=matrix[n][i]){
                        ans="Impossible";
                        break;
                    }
                    else {
                        if(matrix[n][i]==1){
                            if(ans.equals("")) {//first bracket
                                String tempans = "";
                                for (int k = 0; k < n; k++) {
                                    if(matrix[k][i]==1){
                                        if(tempans.equals("")){//first xi
                                            tempans+="("+(k+1);
                                        }
                                        else {
                                            tempans+="&"+(k+1);
                                        }
                                    }
                                    else {
                                        if(tempans.equals("")){
                                            tempans+="(~"+(k+1);
                                        }
                                        else {
                                            tempans+="&~"+(k+1);
                                        }
                                    }
                                }
                                tempans+=")";
                                ans+=tempans;
                            }
                            else{
                                ans+="|";
                                String tempans = "";
                                for (int k = 0; k < n; k++) {
                                    if(matrix[k][i]==1){
                                        if(tempans.equals("")){
                                            tempans+="("+(k+1);
                                        }
                                        else {
                                            tempans+="&"+(k+1);
                                        }
                                    }
                                    else {
                                        if(tempans.equals("")){
                                            tempans+="(~"+(k+1);
                                        }
                                        else {
                                            tempans+="&~"+(k+1);
                                        }
                                    }
                                }
                                tempans+=")";
                                ans+=tempans;
                            }
                        }
                    }

                }
                if(ans.equals("Impossible")){
                    break;
                }
            }
        }
        System.out.print(ans);
    }
}
