package lab1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;


public class style2 {

    static void mergeSortIterative(int[] a) {
        for (int i = 1; i < a.length; i *= 2) {
            for (int j = 0; j < a.length - i; j += 2 * i) {
                merge(a, j, j + i, minn(j + 2 * i, a.length));
            }
        }
    }

    static int minn(int a, int b) {
        return (a <= b) ? a : b;
    }

    static void merge(int[] a, int left, int mid, int right) {
        int it1 = 0;
        int it2 = 0;
        int[] result = new int[right - left];
        while (left + it1 < mid && mid + it2 < right) {
            if (a[left + it1] < a[mid + it2]) {
                result[it1 + it2] = a[left + it1];
                it1 += 1;
            } else {
                result[it1 + it2] = a[mid + it2];
                it2 += 1;
            }
        }
        while (left + it1 < mid) {
            result[it1 + it2] = a[left + it1];
            it1 += 1;
        }
        while (mid + it2 < right) {
            result[it1 + it2] = a[mid + it2];
            it2 += 1;
        }
        for (int i = 0; i < it1 + it2; i++) {
            a[left + i] = result[i];
        }
    }

    public static int min(int x, int y) {
        if (x > y) {
            return y;
        } else {
            return x;
        }
    }

    public static int max(int x, int y) {
        if (x > y) {
            return x;
        } else {
            return y;
        }
    }

    public static void main(String[] args) throws Exception {
        String sourceFileName = "style2.in";
        String destinationFileName = "style2.out";
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(sourceFileName);
        java.io.FileOutputStream fileOutputStream = new java.io.FileOutputStream(destinationFileName);
        BufferedReader br = new BufferedReader(new FileReader(sourceFileName));
        String split = "[ ]+";

        int a = Integer.parseInt(br.readLine());
        String[] ints1 = br.readLine().split(split);
        int[] kep = new int[a];
        for (int k = 0; k < a; k++) {
            kep[k] = Integer.parseInt(ints1[k]);
        }

        int q = Integer.parseInt(br.readLine());
        String[] ints2 = br.readLine().split(split);
        int[] may = new int[q];
        for (int m = 0; m < q; m++) {
            may[m] = Integer.parseInt(ints2[m]);
        }

        int c = Integer.parseInt(br.readLine());
        String[] ints3 = br.readLine().split(split);
        int[] sht = new int[c];
        for (int s = 0; s < c; s++) {
            sht[s] = Integer.parseInt(ints3[s]);
        }

        int d = Integer.parseInt(br.readLine());
        String[] ints4 = br.readLine().split(split);
        int[] bot = new int[d];
        for (int b = 0; b < d; b++) {
            bot[b] = Integer.parseInt(ints4[b]);
        }

        mergeSortIterative(kep);
        mergeSortIterative(may);
        mergeSortIterative(sht);
        mergeSortIterative(bot);

        int x = 0, y = 0, z = 0, p = 0, k = 0, m = 0, s = 0, b = 0;
        int r = 0;
        if (kep[k] > may[m]) {
            if (kep[k] > sht[s]) {
                if (kep[k] > bot[b]) {
                    //y=kep[k];

                    if (may[m] < sht[s]) {
                        if (may[m] < bot[b]) {
                            //x=may[m];
                            r = kep[k] - may[m];
                        } else {
                            //x=bot[b];
                            r = kep[k] - bot[b];
                        }
                    } else {
                        if (sht[s] < bot[b]) {
                            //x=sht[s];
                            r = kep[k] - sht[s];
                        } else {
                            //x=bot[b];
                            r = kep[k] - bot[b];
                        }
                    }
                } else {
                    //y=bot[b];

                    if (sht[s] < may[m]) {
                        //x=sht[s];
                        r = bot[b] - sht[s];
                    } else {
                        //x=may[m];
                        r = bot[b] - may[m];
                    }
                }
            } else {
                if (sht[s] > bot[b]) {
                    // y=sht[s];

                    if (bot[b] < may[m]) {
                        //x=bot[b];
                        r = sht[s] - bot[b];
                    } else {
                        //x=may[m];
                        r = sht[s] - may[m];
                    }
                } else {
                    //y=bot[b];
                    //x=may[m];
                    r = bot[b] - may[m];
                }
            }
        } else {
            if (may[m] > sht[s]) {
                if (may[m] > bot[b]) {
                    //y=may[m];
                    if (kep[k] < sht[s]) {
                        if (kep[k] < bot[b]) {
                            //x=kep[k];
                            r = may[m] - kep[k];
                        } else {
                            //x=bot[b];
                            r = may[m] - bot[b];
                        }
                    }
                } else {
                    //y=bot[b];
                    if (kep[k] < sht[s]) {
                        //x=kep[k];
                        r = bot[b] - kep[k];
                    } else {
                        //x=sht[s];
                        r = bot[b] - sht[s];
                    }
                }
            } else {
                if (sht[s] > bot[b]) {
                    //y=sht[s];
                    if (kep[k] < bot[b]) {
                        //x=kep[k];
                        r = sht[s] - kep[k];
                    } else {
                        //x=bot[b];
                        r = sht[s] - bot[b];
                    }
                } else {
                    //y=bot[b];
                    //x=kep[k];
                    r = bot[b] - kep[k];
                }
            }
        }
        int min = r;
        while (k < a && m < q && s < c && b < d) {
            if (kep[k] > may[m]) {
                if (kep[k] > sht[s]) {
                    if (kep[k] > bot[b]) {
                        //y=kep[k];

                        if (may[m] < sht[s]) {
                            if (may[m] < bot[b]) {
                                //x=may[m];
                                r = kep[k] - may[m];
                            } else {
                                //x=bot[b];
                                r = kep[k] - bot[b];
                            }
                        } else {
                            if (sht[s] < bot[b]) {
                                //x=sht[s];
                                r = kep[k] - sht[s];
                            } else {
                                //x=bot[b];
                                r = kep[k] - bot[b];
                            }
                        }
                    } else {
                        //y=bot[b];

                        if (sht[s] < may[m]) {
                            //x=sht[s];
                            r = bot[b] - sht[s];
                        } else {
                            //x=may[m];
                            r = bot[b] - may[m];
                        }
                    }
                } else {
                    if (sht[s] > bot[b]) {
                        // y=sht[s];

                        if (bot[b] < may[m]) {
                            //x=bot[b];
                            r = sht[s] - bot[b];
                        } else {
                            //x=may[m];
                            r = sht[s] - may[m];
                        }
                    } else {
                        //y=bot[b];
                        //x=may[m];
                        r = bot[b] - may[m];
                    }
                }
            } else {
                if (may[m] > sht[s]) {
                    if (may[m] > bot[b]) {
                        //y=may[m];
                        if (kep[k] < sht[s]) {
                            if (kep[k] < bot[b]) {
                                //x=kep[k];
                                r = may[m] - kep[k];
                            } else {
                                //x=bot[b];
                                r = may[m] - bot[b];
                            }
                        }
                    } else {
                        //y=bot[b];
                        if (kep[k] < sht[s]) {
                            //x=kep[k];
                            r = bot[b] - kep[k];
                        } else {
                            //x=sht[s];
                            r = bot[b] - sht[s];
                        }
                    }
                } else {
                    if (sht[s] > bot[b]) {
                        //y=sht[s];
                        if (kep[k] < bot[b]) {
                            //x=kep[k];
                            r = sht[s] - kep[k];
                        } else {
                            //x=bot[b];
                            r = sht[s] - bot[b];
                        }
                    } else {
                        //y=bot[b];
                        //x=kep[k];
                        r = bot[b] - kep[k];
                    }
                }
            }
            if (min > r) {
                min = r;
                x = k;
                y = m;
                z = s;
                p = b;
                if (min == 0) {
                    break;
                }
            }
            if (kep[k] == min(min(kep[k], may[m]), min(sht[s], bot[b]))) {
                k++;
            } else if (may[m] == min(min(kep[k], may[m]), min(sht[s], bot[b]))) {
                m++;
            } else if (sht[s] == min(min(kep[k], may[m]), min(sht[s], bot[b]))) {
                s++;
            } else {
                b++;
            }
        }

        FileWriter writer = new FileWriter("style2.out", true);
        writer.write(kep[x] + " " + may[y] + " " + sht[z] + " " + bot[p]);
        writer.flush();
        fileInputStream.close();
        fileOutputStream.close();

    }
}
