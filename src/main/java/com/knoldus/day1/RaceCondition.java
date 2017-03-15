package com.knoldus.day1;

/**
 * Created by Harmeet Singh(Taara) on 15/3/17.
 */
public class RaceCondition {

    private static int balance = 20;

    private static Runnable worker1 = () -> {
        if(balance > 0 && balance == 20) {

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            balance -= 20;
        }
    };

    private static Runnable worker2 = () -> {
        if(balance > 0 && balance == 20) {

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            balance -= 20;
        }
    };

    public static void main(String... args) throws InterruptedException {
        System.out.println("Race condition use case ... ");

        System.out.println("Before Race condition Balance ... "+balance);

        new Thread(worker1).start();
        new Thread(worker2).start();

        Thread.sleep(20);
        System.out.println("After Race condition Balance ... "+balance);

        if(balance < 0) {
            System.out.println("Sorry you need to pay extra Penalty ... :(");
        }

    }
}
