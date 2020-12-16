package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        selectMode();
    }

    static void selectMode()throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter 1 for SubscribeTemperature, 2 for Subscribe or 3 for PublishTemperature");
        int input = scanner.nextInt();
        if (input == 1) {
            new SubscribeTemperature();
        }
        else if (input == 2){
            Subscribe.subscribeData();

        }else if (input == 3){
            PublishTemperature.publishData();
        }
        else {
            System.out.println("Please try again");
            selectMode();
        }
    }
}