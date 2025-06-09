package financemanager.records;

/* 
Example:
type,category,amount,date,notes
Income,Salary,2000.00,2025-06-09,Monthly salary
Expense,Groceries,150.00,2025-06-10,Weekly shopping

*/

public class FinanceRecord {
    private String type;
    private String category;
    private double amount;
    private String date;
    private String notes;

    // Constructor
    public FinanceRecord(String type, String category, double amount, String date, String notes) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.notes = notes;
    }

    // Getters
    public String getType() {return type;}

    public String getCategory() {return category;}

    public double getAmount() {return amount;}

    public String getDate() {return date;}

    public String getNotes() {return notes;}
}
