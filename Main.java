import bank.Bank;

import java.util.Scanner;

public class Main extends Application {

    private static Scanner s = new Scanner(System.in);

    // Nettoie l'écran des prints précédents
    private static void flushScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {

        // Init
        Bank b = new Bank();

        /// Declaration before loop
        boolean endOfSession = false;
        String userInput;

        int balance;
        int decouvert;
        String name;

        // Loop
        while (!endOfSession) {

            // Menu display
            System.out.println("\n\nWhat operation do you want to do ?");
            System.out.println("0. See all accounts");
            System.out.println("1. Create a new account");
            System.out.println("2. Change balance on a given account");
            System.out.println("3. Block an account");
            System.out.println("q. Quit\n");

            // Getting primary input
            userInput = s.nextLine();

            // Processing user input
            switch (userInput) {
                case "q":
                    endOfSession = true;
                    b.closeDb();
                    break;
                case "0":
                    b.printAllAccounts();
                    break;


                case "1":
                    System.out.println("\nEnter name for the account : ");
                    name = s.nextLine();

                    System.out.println("\nEnter balance for the account : ");
                    balance = s.nextInt();
                    if(balance < 0){
                        System.out.print("You have to enter a positive balance");
                        break;
                    }

                    System.out.println("\nEnter decouvert for the account : ");
                    decouvert = s.nextInt();
                    if(decouvert > 0){
                        System.out.print("Your decouvert have to be negative");
                        break;
                    }

                    b.createNewAccount(name,balance,decouvert);
                    break;

                case "2":
                    System.out.println("\nEnter the name of the account you want change the balance: ");
                    name = s.nextLine();

                    System.out.println("\nEnter the new balance : ");
                    balance = s.nextInt();
                    if(balance < 0){
                        System.out.print("You have to enter a positive balance");
                        break;
                    }

                    b.changeBalanceByName(name,balance);
                    break;

                case "3":
                    System.out.println("\nEnter the name of the account you want block : ");
                    name = s.nextLine();

                    b.blockAccount(name);
                    break;
            }
        }
    }
}
