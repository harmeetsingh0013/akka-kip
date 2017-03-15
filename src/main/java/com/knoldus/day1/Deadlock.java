package com.knoldus.day1;

/**
 * Created by Harmeet Singh(Taara) on 15/3/17.
 */
public class Deadlock {

    private static Object job = new Object();
    private static Object experience = new Object();

    private static Runnable worker1 = () -> {
        synchronized (job) {
            System.out.println("Worker1 Required Experience ... :(");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (experience) {
                System.out.println("Worker1 Start Job ... :D");
            }
        }
    };

    private static Runnable worker2 = () -> {
        synchronized (experience) {
            System.out.println("Worker2 Required Job ... :(");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (job) {
                System.out.println("Worker2 Start Job ... :D");
            }
        }
    };

    public static void main(String... args) throws InterruptedException {
        System.out.println("Deadlock use case ... ");
        new Thread(worker1).start();
        new Thread(worker2).start();
    }
}
