package banking;

import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("1. Create an account" + "\n" + "2. Log into account" + "\n" + "0. Exit");
        Scanner scanner = new Scanner(System.in);
        int menu = scanner.nextInt();

        switch (menu) {
            case 1:
                long generateCardNumber = (long)(Math.random()*100000_00000L + 400000_0000000000L);
                System.out.println("Your card has been created");
                System.out.println("Your card number: " + "\n" + generateCardNumber);

                break;
            case 2:
                System.out.println("Logging into account");
                break;
            case 0:
                System.out.println("Exiting...");
                break;
        }

//        long generateCardNumber = (long)(Math.random()*100000_00000L + 400000_0000000000L);
//        System.out.println(generateCardNumber);


    }
}
