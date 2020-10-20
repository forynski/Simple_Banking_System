package banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    static List<Account> accountList = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Create an account");
            System.out.println("2. Log into account");
            System.out.println("0. Exit");

            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 0) {
                System.out.println("Exiting...");
                System.exit(0);
            }
            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    if (logIn()) {
                        authenticatedUserMenu();
                    }
            }
        }
    }

    private static void authenticatedUserMenu() {
        System.out.println("You have successfully logged in!");
        System.out.println();
        loop:
        while (true) {
            System.out.println("1. Balance");
            System.out.println("2. Log out");
            System.out.println("0. Exit");
            int choice = Integer.parseInt(scanner.nextLine());
            if (choice == 0) {
                System.out.println("Bye!");
                System.exit(0);
            }
            switch (choice) {
                case 1:
                    printBalance();
                    break;
                case 2:
                    logOut();
                    break loop;
            }
        }
    }

    private static void logOut() {
        System.out.println();
        System.out.println("You have successfully logged out!");
        System.out.println();
    }

    private static void printBalance() {
        Double balance = 0.0;
        System.out.println();
        System.out.println("Balance: " + balance);
        System.out.println();
    }

    private static boolean logIn() {
        System.out.println();
        System.out.println("Enter your card number:");
        String loginCardNumber = scanner.nextLine();
        Optional<Account> filteredAccount = accountList.stream()
                .filter(account -> account.getCardNumber().equals(loginCardNumber))
                .findFirst();
        if (filteredAccount.isEmpty()) {
            System.out.println();
            System.out.println("Wrong card number or PIN!");
            System.out.println();
            return false;
        } else {
            System.out.println("Enter your PIN!");
            String loginPin = scanner.nextLine();
            if (filteredAccount.get().getPin().equals(loginPin)) {
                System.out.println();
                return true;
            } else {
                System.out.println();
                System.out.println("Wrong card number or PIN!");
                System.out.println();
            }
        }
        return false;
    }

    private static void createAccount() {
        System.out.println();
        int IIN = 400000;
        int accountNum = ThreadLocalRandom.current().nextInt(100_000_000, 1_000_000_000);
        int checksum = 1;
        String cardNum = String.valueOf(IIN) + accountNum + checksum;
        String pin = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 10000));
        Account createdAccount = new Account(cardNum, pin);
        accountList.add(createdAccount);
        System.out.println("Your card has been created");
        System.out.println("Your card number: \n" + createdAccount.getCardNumber());
        System.out.println("Your card PIN: \n" + createdAccount.getPin());
        System.out.println();
    }
}
