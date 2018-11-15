package second_year.untitled_algo_labs;

public class TwoProcessors {
    public static void main(String[] args) {
        int firstTime = 14;
        int secondTime = 5;
        int firstDelay = 42;
        int secondDelay = 1;
        while (true) {
            int currentTime = 0;
            int lastFirstIn = 1;
            int lastSecondIn = 3;
            int currentSize = 0;
            int[] remainingTime = new int[2];
            int[] index = new int[2];
            int timeOfQuant = 0;
            int timeToChange = 0;
            while (true) {
                currentTime++;
                if (lastFirstIn == currentTime && lastSecondIn == currentTime) {
                    if (currentSize > 0) {
                        break;
                    }
                    remainingTime[0] = firstTime - 1;
                    timeOfQuant = 7;
                    remainingTime[1] = secondTime;
                    index[0] = 1;
                    index[1] = 2;
                    lastFirstIn += firstDelay;
                    lastSecondIn += secondDelay;
                    currentSize = 2;
                } else if (lastFirstIn == currentTime) {
                    if (currentSize == 0) {
                        remainingTime[0] = firstTime - 1;
                        timeOfQuant = 7;
                        index[0] = 1;
                        lastFirstIn += firstDelay;
                        currentSize = 1;
                    } else if (currentSize == 2) {
                        break;
                    } else {
                        if (index[0] == 1) {
                            break;
                        } else {
                            lastFirstIn += firstDelay;
                            if (timeToChange > 0) {
                                timeToChange--;
                                index[1] = 1;
                                currentSize = 2;
                                remainingTime[1] = firstTime;
                            } else {
                                index[1] = 1;
                                currentSize = 2;
                                remainingTime[1] = firstTime;
                                remainingTime[0]--;
                                timeOfQuant--;
                                if (remainingTime[0] == 0) {
                                    timeToChange = 3;
                                    currentSize = 1;
                                    index[0] = 1;
                                    remainingTime[0] = firstTime;
                                    timeOfQuant = 8;
                                } else if (timeOfQuant == 0) {
                                    timeToChange = 3;
                                    currentSize = 2;
                                    int tempIndex = index[0];
                                    int tempTime = remainingTime[0];
                                    index[0] = index[1];
                                    remainingTime[0] = remainingTime[1];
                                    index[0] = tempIndex;
                                    remainingTime[0] = tempTime;
                                    timeOfQuant = 8;
                                }
                            }
                        }
                    }
                } else if (lastSecondIn == currentTime) {
                    if (currentSize == 0) {
                        remainingTime[0] = secondTime - 1;
                        timeOfQuant = 7;
                        index[0] = 2;
                        lastSecondIn += secondDelay;
                        currentSize = 1;
                    } else if (currentSize == 2) {
                        break;
                    } else {
                        if (index[0] == 2) {
                            break;
                        } else {
                            lastSecondIn += secondDelay;
                            if (timeToChange > 0) {
                                timeToChange--;
                                index[1] = 2;
                                currentSize = 2;
                                remainingTime[1] = secondTime;
                            } else {
                                index[1] = 2;
                                currentSize = 2;
                                remainingTime[1] = secondTime;
                                remainingTime[0]--;
                                timeOfQuant--;
                                if (remainingTime[0] == 0) {
                                    timeToChange = 3;
                                    currentSize = 1;
                                    index[0] = 2;
                                    remainingTime[0] = secondTime;
                                    timeOfQuant = 8;
                                } else if (timeOfQuant == 0) {
                                    timeToChange = 3;
                                    currentSize = 2;
                                    int tempIndex = index[0];
                                    int tempTime = remainingTime[0];
                                    index[0] = index[1];
                                    remainingTime[0] = remainingTime[1];
                                    index[0] = tempIndex;
                                    remainingTime[0] = tempTime;
                                    timeOfQuant = 8;
                                }
                            }
                        }
                    }
                } else {
                    if (currentSize == 0) {
                        // nothing
                    } else if (currentSize == 1) {
                        if (timeToChange > 0) {
                            timeToChange--;
                        } else {
                            remainingTime[0]--;
                            timeOfQuant--;
                            if (remainingTime[0] == 0) {
                                currentSize = 0;
                                index[0] = 0;
                            } else if (timeOfQuant == 0) {
                                timeOfQuant = 8;
                            }
                        }
                    } else {
                        if (timeToChange > 0) {
                            timeToChange--;
                        } else {
                            remainingTime[0]--;
                            timeOfQuant--;
                            if (remainingTime[0] == 0) {
                                currentSize = 1;
                                index[0] = index[1];
                                remainingTime[0] = remainingTime[1];
                                index[1] = 0;
                                remainingTime[1] = 0;
                                timeToChange = 3;
                                timeOfQuant = 8;
                            } else if (timeOfQuant == 0) {
                                timeToChange = 3;
                                int tempIndex = index[0];
                                int tempTime = remainingTime[0];
                                index[0] = index[1];
                                remainingTime[0] = remainingTime[1];
                                index[0] = tempIndex;
                                remainingTime[0] = tempTime;
                                timeOfQuant = 8;
                            }
                        }
                    }
                }
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println(String.format("time = %d, time[0] =  %d, time[1] = %d, index[0] = %d, index[1] = %d, nextFirst = %d, nextSecond = %d, quant = %d, timeToChange = %d\n", currentTime, remainingTime[0], remainingTime[1], index[0], index[1], lastFirstIn, lastSecondIn, timeOfQuant, timeToChange));
            }
            System.out.println(secondDelay);// те которые выведет - не подходят
            secondDelay++;
        }
    }
}
