package banking;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("1. Create an account" + "\n" + "2. Log into account" + "\n" + "0. Exit");
        Scanner scanner = new Scanner(System.in);
        int menu = scanner.nextInt();

        switch (menu) {
            case 1:
                System.out.println("Creating an account");
                break;
            case 2:
                System.out.println("Logging into account");
                break;
            case 3:
                System.out.println("Exiting...");
                break;
        }
    }
}
