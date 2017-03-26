package labs.lab1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by Антон on 15.10.2016.
 */
public class order_statistics {

    static void quicksort(int[] a, int l, int r)
    {
        if (l<r-1)
        {
            int q = partition(a, l, r);
            quicksort(a, l, q);
            quicksort(a, q + 1, r);
        }
    }

    static int partition(int[] a, int l, int r)
    {
        int v = a[(l + r) / 2];
        int i = l;
        int j = r;
        while (i <= j)
        {
            while (a[i] < v)
            {
                i++;
            }
            while (a[j] > v)
            {
                j--;
            }
            if (i <=j)
            {
                swap(i, j,a);
                i++;
                j--;
            }
        }
        return j;
    }

    static void swap(int i, int j,int a[])
    {
        int x=a[i];
        a[i]=a[j];
        a[j]=x;
    }

    public static void main(String[] args) throws Exception
    {
        String sourceFileName = "kth.in";
        String destinationFileName = "kth.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        String split="[ ]+";
        String[] ints=br.readLine().split(split);
        int[] arr=new int[2];
        for (int i=0;i<2;i++){
            arr[i]=Integer.parseInt(ints[i]);
        }
        String[] ints2=br.readLine().split(split);
        int[] arr2=new int[5];
        for (int i=0;i<5;i++){
            arr2[i]=Integer.parseInt(ints2[i]);
        }

        int[] numbers=new int[arr[0]];
        numbers[0]=arr2[3];
        numbers[1]=arr2[4];
        for (int i=2;i<arr[0];i++){
            numbers[i]=numbers[i-2]*arr2[0]+numbers[i-1]*arr2[1]+arr2[2];
        }
        quicksort(numbers,0,numbers.length-1);

        int ans=numbers[arr[1]-1];
        FileWriter writer = new FileWriter("kth.out", true);
        writer.write(""+ans);
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
/*int[] numbers=new int[arr[0]];
        numbers[0]=arr2[3];
        numbers[1]=arr2[4];
        for (int i=2;i<arr[0];i++){
            numbers[i]=numbers[i-2]*arr2[0]+numbers[i-1]*arr2[1]+arr2[2];
        }
        quicksort(numbers,0,numbers.length-1);
        int[] numbers2=new int[arr[0]];
        int j=0;
        int i=1;
        numbers2[0]=numbers[0];
        while (i<arr[0]){
            if (numbers[i]!=numbers2[j]){
                j++;
                numbers2[j]=numbers[i];
            }
            i++;
        }

        int ans=numbers2[arr[1]-1];*/