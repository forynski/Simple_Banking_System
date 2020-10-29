package banking;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;


public class Main {

    static String fileName;

    public static void main(String[] args) {

        fileName = "card.s3db";

        createNewTable();
        Account account = new Account();
        account.menu();

    }

    private static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:" + fileName;

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS card (\n"
                + "	id INTEGER NOT NULL PRIMARY KEY,\n"
                + "	number TEXT,\n"
                + "	pin TEXT,\n"
                + " balance INTEGER DEFAULT 0"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static class Account {
        Map<String, Map<String, Integer>> account = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        String acc;
        Integer pin;
        Integer balance;

        void menu() {
            System.out.println("1. Create an account" + "\n" + "2. Log into account" + "\n" + "0. Exit");


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
            System.out.println("1. Balance");
            System.out.println("2. Add income");
            System.out.println("3. Do transfer");
            System.out.println("4. Close account");
            System.out.println("5. Log out");
            System.out.println("0. Exit");
            String key = scanner.nextLine();
            switch (key) {
                case "1":
                    balanceShow();
                    balance();
                    break;
                case "2":
                    balanceAdd();
                    balance();
                    break;
                case "3":
                    // do transfer
                case "4":
                    // close account
                case "5":
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

        public void balanceAdd() {
            System.out.println("Enter income: ");
            balance = Integer.parseInt(scanner.nextLine());


        }

        public String create() {

            Random random = new Random();
            StringBuilder sb = new StringBuilder();
            Map<String, Integer> info = new HashMap<>();
            // first digit is not 0
            sb.append(random.nextInt(9) + 1);
            for (int i = 0; i < 8; i++) {
                sb.append(random.nextInt(10));
            }

            acc = 400000 + sb.toString();
            StringBuilder pin = new StringBuilder();

            // Luhn algorithm
            int checksum = getChecksum();
            String lastDigit = String.valueOf(checksum);
            acc = 400000 + sb.toString() + checksum;


            // pin
            for (int i = 0; i < 4; i++) {
                pin.append(random.nextInt(10));
            }

            info.put("PIN", Integer.parseInt(pin.toString()));
            info.put("BALANCE", 0);
            account.put(acc, info);
            String url = "jdbc:sqlite:" + fileName;
            String sql = "INSERT INTO card (number, pin) VALUES(?,?)";

            try (Connection conn = DriverManager.getConnection(url);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // Setting parameters

                pstmt.setString(1, acc);
                pstmt.setInt(2, Integer.parseInt(pin.toString()));
                pstmt.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e.getMessage());

            }
            return acc;
        }

        private int getChecksum() {
            int sum = 0;
            for (int i = 0; i < acc.length(); i++) {
                int digit = Character.getNumericValue(acc.charAt(i));
                if (i % 2 == 0) {
                    digit *= 2;
                }
                if (digit > 9) {
                    digit -= 9;
                }
                sum += digit;
            }
            int checksum = 10 - sum % 10;
            if (checksum % 10 == 0) {
                checksum = 0;
            }
            return checksum;
        }

        void exit() {
            System.out.println("\nBye!");
            scanner.close();
        }
    }
}
