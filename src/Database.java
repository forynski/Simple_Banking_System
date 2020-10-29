package banking;

import org.sqlite.SQLiteDataSource;
import java.sql.*;

public class Database {
    String path;
    SQLiteDataSource dataSource = new SQLiteDataSource();

    public Database(String path) {
        this.path = path;
        dataSource.setUrl(this.path);
        this.getDatabase();
    }

    public String getDatabase() {

        try (Connection con = dataSource.getConnection()) {
            // Statement creation
            try (Statement statement = con.createStatement()) {
                // Statement execution
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                        "id INTEGER PRIMARY KEY," +
                        "number TEXT," +
                        "pin TEXT," +
                        "balance INTEGER DEFAULT 0)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return this.path;
    }

    public void addRowToDatabase(String number, String pin) {
        try (Connection con = dataSource.getConnection()) {
            // Statement creation
            try (Statement statement = con.createStatement()) {
                // Statement execution
                statement.executeUpdate("INSERT INTO card(number, pin) VALUES" +
                        "(\"" + number + "\",\"" + pin + "\")");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean cardExistInDatabase(String number, String pin) {
        boolean cardExist = false;
        try (Connection con = dataSource.getConnection()) {
            // Statement creation
            try (Statement statement = con.createStatement()) {
                try (ResultSet resultCard = statement.executeQuery("SELECT * FROM card")) {
                    while (resultCard.next()) {
                        String resultNumber = resultCard.getString("number");
                        String resultPin = resultCard.getString("pin");
                        if (resultNumber.equals(number) && resultPin.equals(pin)) {
                            cardExist = true;
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cardExist;
    }

    public boolean cardExistInDatabase(String number) {
        boolean cardExist = false;
        try (Connection con = dataSource.getConnection()) {
            // Statement creation
            try (Statement statement = con.createStatement()) {
                try (ResultSet resultCard = statement.executeQuery("SELECT * FROM card")) {
                    while (resultCard.next()) {
                        String resultNumber = resultCard.getString("number");
                        if (resultNumber.equals(number)) {
                            cardExist = true;
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cardExist;
    }

    public Integer getBalanse(String number) {
        String sql = "SELECT number, pin, balance FROM card";

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                String resultNumber = rs.getString("number");
                String resultBalance = rs.getString("balance");
                if (resultNumber.equals(number)) {
                    return Integer.parseInt(resultBalance);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    public void addIncome(Integer balance, String number){
        try (Connection con = dataSource.getConnection()) {
            // Statement creation
            try (Statement statement = con.createStatement()) {
                // Statement execution
                statement.executeUpdate("UPDATE card " +
                        " set balance= balance +" + balance +
                        " WHERE NUMBER = " + number);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String cardExistsError(String numberFrom, String numberTo) {

        if (numberFrom.equals(numberTo)) {
            return "You can't transfer money to the same account!";
        }
        if (Integer.parseInt(numberTo.substring(numberTo.length() - 1))
                != Account.luhnAlgLastNumber(numberTo.substring(0,numberTo.length()-1))){
            return "Probably you made mistake in the card number. Please try again!";
        }
        if (!cardExistInDatabase(numberTo)) {
            return "Such a card does not exist.";
        }
        return "Enter how much money you want to transfer:";
    }
    public void transfer(Integer balance, String numberFrom, String numberTo){
        if (getBalanse(numberFrom) < balance ) {
            System.out.println("Not enough money!");
            return;
        }
        try (Connection con = dataSource.getConnection()) {
            // Statement creation
            try (Statement statement = con.createStatement()) {
                // Statement execution
                statement.executeUpdate("UPDATE card " +
                        " set balance= balance -" + balance +
                        " WHERE NUMBER = " + numberFrom);
                statement.executeUpdate("UPDATE card " +
                        " set balance= balance +" + balance +
                        " WHERE NUMBER = " + numberTo);
                System.out.println("Success!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAccount(String number){
        try (Connection con = dataSource.getConnection()) {
            // Statement creation
            try (Statement statement = con.createStatement()) {
                // Statement execution
                statement.executeUpdate("DELETE from card " +
                        " WHERE NUMBER = " + number);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
