package bank;


import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Bank {

    private ArrayList<Account> user = new ArrayList<>();
    private Integer i;
    
    private ResultSet query(String sql) {
        return query(sql, null);
    }

    private ResultSet query(String sql, Object o) {
    }

    /*
        Strings de connection à la base postgres
     */
   // private static final String JDBC_DRIVER = "org.postgresql.Driver";
   // private static final String DB_URL = "jdbc:postgresql://localhost:5439/postgres";
   // private static final String DB_USER = "postgres";

    /*
        Strings de connection à la base mysql, à décommenter et compléter avec votre nom de bdd et de user
     */
     private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
     private static final String DB_URL = "jdbc:mysql://localhost:3308/bank_db";
     private static final String DB_USER = "root";

    private static final String DB_PASS = "root";

    private static final String TABLE_NAME = "accounts";

    private Connection c;

    public Bank() {
        initDb();
    }

    private void initDb() {
        try {
            Class.forName(JDBC_DRIVER);
            c = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("Opened database successfully");

            // TODO Init DB

            query("CREATE TABLE accounts(name VARCHAR(255), balance FLOAT, decouvert INTEGER, block BOOLEAN DEFAULT 'false')");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void closeDb() {
        try {
            c.close();
        } catch (SQLException e) {
            System.out.println("Could not close the database : " + e);
        }
    }

    void dropAllTables() {
        try (Statement s = c.createStatement()) {
            s.executeUpdate(
                       "DROP SCHEMA public CASCADE;" +
                            "CREATE SCHEMA public;" +
                            "GRANT ALL ON SCHEMA public TO postgres;" +
                            "GRANT ALL ON SCHEMA public TO public;");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }


    public void createNewAccount(String name, int balance, int decouvert) {
        // TODO

        if(decouvert <= 0){
            Account newAccount = new Account();
            newAccount.setName(name);
            newAccount.setDecouvert(decouvert);
            newAccount.setBalance(balance);
            user.add(newAccount);
            try (Statement s = c.createStatement()){

                char status;
                if(newAccount.isBlock() == false){
                    status = 'f';
                }else{
                    status = 't';
                }

                s.executeUpdate("INSERT INTO " + TABLE_NAME + " (name, balance,decouvert,block) VALUES ("
                        + "'" + newAccount.getName() + "' , "
                        +  newAccount.getBalance() + " , "
                        +  newAccount.getDecouvert() + " ,"
                        + "'" + status + "');");
                System.out.println("Account created");
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }else{
            return;
        }
    }
    }


    public String printAllAccounts() {
        // TODO
        String AllAccounts = "";
        ResultSet q;

        q = query("SELECT name, balance, decouvert, block FROM accounts");
        String name = q.getString(1);
        int balance = q.getInt(2);
        int decouvert = q.getInt(3);
        boolean block = q.getBoolean(4);
        AllAccounts += new Account(name, balance, decouvert, block).toString() + "\n";

        return AllAccounts();
    }



    public void changeBalanceByName(String name, int balanceModifier) {
        // TODO
        ArrayList<Object> account = new ArrayList<Object>()
        account.add(name);
        account.add(balanceModifier);

        query("UPDATE accounts SET balance = balanceModifier WHERE name");
    }

    public void blockAccount(String name) {
        // TODO
            ArrayList<Object> account = new ArrayList<Object>();
            account.add(name);
            query("UPDATE accounts SET block = 'true' WHERE name = name");
    }

    // For testing purpose
    String getTableDump(){
        String query = "select * from " + TABLE_NAME;
        String res = "";

        try (PreparedStatement s = c.prepareStatement(query)) {
            ResultSet r = s.executeQuery();

            // Getting nb colmun from meta data
            int nbColumns = r.getMetaData().getColumnCount();

            // while there is a next row
            while (r.next()){
                String[] currentRow = new String[nbColumns];

                // For each column in the row
                for (int i = 1 ; i <= nbColumns ; i++) {
                    currentRow[i - 1] = r.getString(i);
                }
                res += Arrays.toString(currentRow);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return res;
    }
}

