package com.knoldus.day1;

/**
 * Created by harmeet on 15/3/17.
 */
public class MultiThreading {

    private static Runnable worker1 = () -> System.out.println("Hi Folks I am "+Thread.currentThread().getName());
    private static Runnable worker2 = () -> System.out.println("Hi Folks I am "+Thread.currentThread().getName());
    private static Runnable worker3 = () -> System.out.println("Hi Folks I am "+Thread.currentThread().getName());

    public static void main(String... args) {
        new Thread(worker1).start();
        new Thread(worker2).start();
        new Thread(worker3).start();
        System.out.println("Hi Folks I am "+Thread.currentThread().getName());
    }
}
