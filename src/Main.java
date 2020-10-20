package banking;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Account account = new Account();
        account.menu();
    }
}

class Account {
    Map<String, Map<String, Integer>> account = new HashMap<>();
    Scanner scanner = new Scanner(System.in);
    String acc;
    Integer pin;

    void menu() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");

        String key = scanner.nextLine();

        switch (key) {
            case "1":
                System.out.println("Your card has been created");
                System.out.println("Your card number:");
                System.out.println(create());
                System.out.println("Your card PIN:");
                System.out.format("%04d", account.get(acc).get("PIN"));
                System.out.println();
                menu();
                break;
            case "2":
                System.out.println("Enter your card number");
                acc = scanner.nextLine();
                System.out.println("Enter your PIN:");
                pin = Integer.parseInt(scanner.nextLine());
                if (account.containsKey(acc) && account.get(acc).get("PIN").equals(pin)) {
                    System.out.println("You have successfully logged in!");
                    balance();
                } else {
                    System.out.println("Wrong card number or PIN!");
                    menu();
                }
                break;
            case "0":
                exit();
                break;
            default:
                System.out.println("\nIncorrect option! Try again.\n");
                break;
        }
    }

    void balance() {
        System.out.println("1. balance");
        System.out.println("2. Log out");
        System.out.println("0. Exit");
        String key = scanner.nextLine();
        switch (key) {
            case "1":
                balanceShow();
                balance();
                break;
            case "2":
                System.out.println("You have successfully logged out!");
                menu();
                break;
            case "0":
                exit();
                break;
            default:
                System.out.println("\nIncorrect option! Try again.\n");
                break;
        }
    }

    public void balanceShow() {
        System.out.println(account.get(acc).get("BALANCE"));
    }

    public String create() {

        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        Map<String, Integer> info = new HashMap<>();
        // first not 0 digit
        sb.append(random.nextInt(9) + 1);
        for (int i = 0; i < 9; i++) {
            sb.append(random.nextInt(10));
        }

        acc = 400000 + sb.toString();
        StringBuilder pin = new StringBuilder();

        // pin
        for (int i = 0; i < 4; i++) {
            pin.append(random.nextInt(10));
        }
        info.put("PIN", Integer.parseInt(pin.toString()));
        info.put("BALANCE", 0);
        account.put(acc, info);
        return acc;
    }

    void exit() {
        System.out.println("\nBye!");
    }
}

