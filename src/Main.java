package banking;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String path = "jdbc:sqlite:" + args[1];
        Database data = new Database(path);
        data.getDatabase();
        Account account = new Account();
        account.menu(data);
    }
}

class Account {
    Map<String, Map<String, String>> account = new HashMap<>();
    Scanner scanner = new Scanner(System.in);
    String acc;
    String pin;
    Integer income;
    void menu(Database data) {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");

        String key = scanner.nextLine();

        switch (key) {
            case "1":
                System.out.println("Your card has been created");
                System.out.println("Your card number:");
                acc = create();
                System.out.println(acc);
                System.out.println("Your card PIN:");
                pin = account.get(acc).get("PIN");
                System.out.format(pin);
                data.addRowToDatabase(acc, pin);
                System.out.println();
                menu(data);
                break;
            case "2":
                System.out.println("Enter your card number");
                acc = scanner.nextLine();
                System.out.println("Enter your PIN:");
                pin = scanner.nextLine();
                if (data.cardExistInDatabase(acc, pin)) {
                    System.out.println("You have successfully logged in!");
                    balance(data);
                } else {
                    System.out.println("Wrong card number or PIN!");
                    menu(data);
                }
                break;
            case "0":
                exit();
                break;
            default:
                System.out.println("\nIncorrect option! Try again.\n");
                menu(data);
        }
    }

    void balance(Database data) {
        System.out.println("1. Balance");
        System.out.println("2. Add income");
        System.out.println("3. Do transfer");
        System.out.println("4. Close account");
        System.out.println("5. Log out");
        System.out.println("0. Exit");
        String key = scanner.nextLine();
        switch (key) {
            case "1":
                System.out.println("Balance: " + data.getBalanse(acc));
                balance(data);
                break;
            case "2":
                System.out.println("Enter income:");
                income = Integer.parseInt(scanner.nextLine());
                data.addIncome(income,acc);
                System.out.println("Income was added!");
                balance(data);
            case "5":
                System.out.println("You have successfully logged out!");
                menu(data);
                break;
            case "3":
                System.out.println("Enter card number:");
                String numberTo = scanner.nextLine();
                System.out.println(data.cardExistsError(acc,numberTo));
                if (data.cardExistsError(acc,numberTo).equals("Enter how much money you want to transfer:")){
                    income = Integer.parseInt(scanner.nextLine());
                    data.transfer(income,acc,numberTo);
                };
                balance(data);
            case "4":
                data.deleteAccount(acc);
                menu(data);
            case "0":
                exit();
                break;
            default:
                System.out.println("\nIncorrect option! Try again.\n");
                break;
        }
    }

    public String create() {

        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        Map<String, String> info = new HashMap<>();
        // first not 0 digit
        sb.append(random.nextInt(9) + 1);
        for (int i = 0; i < 8; i++) {
            sb.append(random.nextInt(10));
        }

        acc = 400000 + sb.toString();
        acc = acc + luhnAlgLastNumber(acc);
        StringBuilder pin = new StringBuilder();

        // pin
        for (int i = 0; i < 4; i++) {
            pin.append(random.nextInt(10));
        }
        info.put("PIN", pin.toString());
        info.put("BALANCE", "0");
        account.put(acc, info);
        return acc;
    }

    public static int luhnAlgLastNumber(String acc) {
        int sum = 0;
        char ch;
        String s;
        int[] arr = new int[acc.length()];
        for (int i = 0; i < acc.length(); i++) {
            ch = acc.charAt(i);
            if (Character.isDigit(ch)) {
                arr[i] = Character.getNumericValue(ch);
            }
        }
        for (int i = 0; i < arr.length; i++) {
            if (i % 2 == 0) {
                arr[i] = arr[i] * 2;
            }
        }

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 9) {
                arr[i] = arr[i] - 9;
            }
        }

        for (int i : arr) {
            sum += i;
        }
        return sum % 10 == 0 ? 0 : 10 - sum % 10;
    }

    void exit() {
        System.out.println("\nBye!");
        System.exit(0);
    }
}
