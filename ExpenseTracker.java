import java.io.*;
import java.util.*;

class Expense {
    String title;
    double amount;

    Expense(String title, double amount) {
        this.title = title;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return title + " - ₹" + amount;
    }
}

public class ExpenseTracker {
    private static final String FILE_NAME = "expenses.txt";
    private static ArrayList<Expense> expenses = new ArrayList<>();

    public static void main(String[] args) {
        loadExpenses();

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n===== Personal Expense Tracker =====");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addExpense(sc);
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    saveExpenses();
                    System.out.println("✅ Data saved. Goodbye!");
                    sc.close();
                    return;
                default:
                    System.out.println("❌ Invalid choice, try again.");
            }
        }
    }

    // Add a new expense
    private static void addExpense(Scanner sc) {
        System.out.print("Enter expense title: ");
        String title = sc.nextLine();
        System.out.print("Enter expense amount: ");
        double amount = sc.nextDouble();
        sc.nextLine(); // consume newline

        expenses.add(new Expense(title, amount));
        System.out.println("✅ Expense added successfully!");
    }

    // View all expenses
    private static void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded yet.");
            return;
        }

        double total = 0;
        System.out.println("\n===== Expense List =====");
        for (int i = 0; i < expenses.size(); i++) {
            System.out.println((i + 1) + ". " + expenses.get(i));
            total += expenses.get(i).amount;
        }
        System.out.println("-------------------------");
        System.out.println("Total Spent: ₹" + total);
    }

    // Save expenses to file
    private static void saveExpenses() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Expense exp : expenses) {
                writer.println(exp.title + "," + exp.amount);
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving data: " + e.getMessage());
        }
    }

    // Load expenses from file
    private static void loadExpenses() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String title = parts[0];
                    double amount = Double.parseDouble(parts[1]);
                    expenses.add(new Expense(title, amount));
                }
            }
        } catch (FileNotFoundException e) {
            // Ignore if file doesn't exist yet
        } catch (IOException e) {
            System.out.println("❌ Error loading data: " + e.getMessage());
        }
    }
}
