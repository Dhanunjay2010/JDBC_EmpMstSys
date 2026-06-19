import java.util.Scanner;
import java.sql.*;

public class Operations {
    static Connection m;
    static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args){
        String url ="jdbc:mysql://localhost:3306/mits";
        String user = "root";
        String password = "root";
        try{
            m = DriverManager.getConnection(url, user, password);
            System.out.println("Database Connected Successfully");
             while (true) {
                System.out.println("\n Employee Management System ");
                System.out.println("1. Insert Employee");
                System.out.println("2. Display Employees");
                System.out.println("3. Update Salary");
                System.out.println("4. Delete Employee");
                System.out.println("5. Column Details");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();
                switch(choice){
                    case 1:
                        insertEmployee();
                        break;
                    case 2:
                        displayEmployees();
                        break;
                    case 3:
                        updateSalary();
                        break;
                    case 4:
                        deleteEmployee();
                        break;
                    case 5:
                        displayColumnDetails();
                        break;
                    case 6:
                        System.out.println("Thank you! Exiting...");
                        m.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice!");
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static void insertEmployee(){
        try{
            System.out.println("\n--- Insert Employee ---");
            System.out.print("Enter Employee ID: ");
            int empId = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Employee Name: ");
            String empName = sc.nextLine();
            System.out.print("Enter Employee Job Role: ");
            String empJobRole = sc.nextLine();
            System.out.print("Enter Hire Date (YYYY-MM-DD): ");
            String hireDate = sc.nextLine();
            System.out.print("Enter Salary: ");
            int salary = sc.nextInt();
            String sql = "INSERT INTO emp (emp_id, emp_name, emp_jobroll, emp_hire, salary) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = m.prepareStatement(sql);
            pstmt.setInt(1, empId);
            pstmt.setString(2, empName);
            pstmt.setString(3, empJobRole);
            pstmt.setDate(4, Date.valueOf(hireDate));
            pstmt.setInt(5, salary);
            int result = pstmt.executeUpdate();
            if(result > 0){
                System.out.println("✓ Employee inserted successfully!");
            }
            pstmt.close();
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }catch(IllegalArgumentException e){
            System.out.println("Invalid date format! Use YYYY-MM-DD");
        }
    }
    public static void displayEmployees(){
        try{
            String sql = "SELECT * FROM emp";
            Statement stmt = m.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("\n--- All Employees ---");
            System.out.println("════════════════════════════════════════════════════════");
            System.out.printf("%-8s %-15s %-15s %-12s %-10s%n", 
                            "ID", "Name", "Job Role", "Hire Date", "Salary");
            System.out.println("════════════════════════════════════════════════════════");
             boolean hasRecords = false;
            while(rs.next()){
                hasRecords = true;
                int empId = rs.getInt("emp_id");
                String empName = rs.getString("emp_name");
                String empJobRole = rs.getString("emp_jobroll");
                Date hireDate = rs.getDate("emp_hire");
                int salary = rs.getInt("salary");
                System.out.printf("%-8d %-15s %-15s %-12s %-10d%n", empId, empName, empJobRole, hireDate, salary);
            }
            if(!hasRecords){
                System.out.println("No employees found!");
            }
            System.out.println("════════════════════════════════════════════════════════");
            rs.close();
            stmt.close();
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static void updateSalary(){
        try{
            System.out.println("\n--- Update Employee Salary ---");
            System.out.print("Enter Employee ID: ");
            int empId = sc.nextInt();
            System.out.print("Enter New Salary: ");
            int newSalary = sc.nextInt();
            String sql = "UPDATE emp SET salary = ? WHERE emp_id = ?";
            PreparedStatement pstmt = m.prepareStatement(sql);
            pstmt.setInt(1, newSalary);
            pstmt.setInt(2, empId);
            int result = pstmt.executeUpdate();
            if(result > 0){
                System.out.println("✓ Salary updated successfully!");
            }else{
                System.out.println("Employee ID not found!");
            }
            pstmt.close();
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static void deleteEmployee(){
        try{
             System.out.println("\n--- Delete Employee ---");
            System.out.print("Enter Employee ID to delete: ");
            int empId = sc.nextInt();
            sc.nextLine();
            System.out.print("Are you sure? (yes/no): ");
            String confirm = sc.nextLine();
             if(confirm.equalsIgnoreCase("yes")){
                String sql = "DELETE FROM emp WHERE emp_id = ?";
                PreparedStatement pstmt = m.prepareStatement(sql);
                pstmt.setInt(1, empId);
                
                int result = pstmt.executeUpdate();
                if(result > 0){
                    System.out.println("✓ Employee deleted successfully!");
                }else{
                    System.out.println("Employee ID not found!");
                }
                pstmt.close();
            }else{
                System.out.println("Delete operation cancelled.");
            }
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    public static void displayColumnDetails() {
    try {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter column name: ");
        String columnName = sc.nextLine();
        String sql = "SELECT " + columnName + " FROM emp";
        Statement stmt = m.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println("\n--- Values for Column: " + columnName + " ---");
        System.out.println("════════════════════════════════════");
        System.out.printf("%-30s%n", columnName);
        System.out.println("════════════════════════════════════");
        while (rs.next()) {
            System.out.printf("%-30s%n", rs.getObject(1));
        }
        System.out.println("════════════════════════════════════");
        rs.close();
        stmt.close();
    } catch (SQLException e) {
        System.out.println("Error: " + e.getMessage());
    }
}
}




    
    
   
