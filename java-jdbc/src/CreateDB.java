import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.swing.JOptionPane;

// ? use PreparedStatement for
// ? executing parameterized SQL queries
// ? and CreateStatement for executing non-parameterized SQL queries.

public class CreateDB {

    private static Connection connection = null;
    private static Scanner scan = null;
    private static PreparedStatement pst;
    private static Statement stmt = null;
    private static ResultSet res;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/";
                String userName = "root";
                String password = "root";

                connection = DriverManager.getConnection(url, userName, password);
                System.out.println("Connection Established !");
            } catch (Exception e) {
                System.out.print("Error while establishing connection to the Sql Database !!!");
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Select The Operation :");
        System.out.println(" 1. Display \n 2. Insert \n 3. Update \n 4. Delete \n 5. Exit \n ");
        scan = new Scanner(System.in);
        switch (scan.nextInt()) {
            case 1:
                displayData();
                main(args);
                break;

            case 2:
                insertData();
                main(args);
                break;

            case 3:
                updateData();
                main(args);
                break;

            case 4:
                deleteData();
                main(args);
                break;

            case 5:
                System.exit(0);

            default:
                System.out.println("Please Enter a Valid Input");
                main(args);
        }
        // String sql = "CREATE DATABASE " + databaseName;

        // Statement statement = connection.createStatement();
        // statement.executeUpdate(sql);
        // statement.close();
        // JOptionPane.showMessageDialog(null, databaseName + " Database has been
        // created successfully",
        // "System Message", JOptionPane.INFORMATION_MESSAGE);

        // } catch (Exception e) {
        // e.printStackTrace();
        // }
    }

    private static void insertData() {
        Connection conn = getConnection();
        if (conn != null) {
            String statement = "insert into `inventory`.`inventory` (`productId`,`productName`, `productPrice`, `supplierId`, `companyName`) values(?,?,?,?,?)";

            try {
                pst = conn.prepareStatement(statement);
                System.out.println("Enter ID");
                int productId = scan.nextInt();
                System.out.println("Enter Product Name");
                String productName = scan.next();
                System.out.println("Enter Product Price");
                int productPrice = scan.nextInt();
                System.out.println("Enter Supplier ID");
                String supllierId = scan.next();
                System.out.println("Enter Company Name");
                String companyName = scan.next();
                pst.setInt(1, productId);
                pst.setString(2, productName);
                pst.setInt(3, productPrice);
                pst.setString(4, supllierId);
                pst.setString(5, companyName);

                int executeStmt = pst.executeUpdate();
                if (executeStmt == 1) {
                    System.out.println("Record has been inserted !");
                    // JOptionPane.showMessageDialog(null, "Record has been inserted !", "Inserted",
                    //         JOptionPane.ERROR_MESSAGE);
                } else {
                    System.out.println("Failed to insert Record !");
                    // JOptionPane.showMessageDialog(null, "Failed to insert Record !", "Failed",
                    //         JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException e) {
                // JOptionPane.showMessageDialog(null, "Connection Error", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private static void updateData() {
        Connection conn = getConnection();
        if (conn != null) {
            String statement = "update `inventory`.`inventory` set `productName`= ? where `productID` = ?";

            try {
                pst = conn.prepareStatement(statement);
                System.out.println("Enter ID");
                int productId = scan.nextInt();
                System.out.println("Enter Product Name");
                String productName = scan.next();
                pst.setString(1, productName);
                pst.setInt(2, productId);

                int executeStmt = pst.executeUpdate();
                if (executeStmt == 1) {
                    System.out.println("Record has been updated !");
                    // JOptionPane.showMessageDialog(null, "Record has been updated !", "Updated",
                    //         JOptionPane.ERROR_MESSAGE);
                } else {
                    System.out.println("Failed to update Record !");
                    // JOptionPane.showMessageDialog(null, "Failed to update Record !", "Failed",
                    //         JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException e) {
                // JOptionPane.showMessageDialog(null, "Connection Error", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private static void deleteData() {
        Connection conn = getConnection();
        if (conn != null) {
            String statement = "delete from `inventory`.`inventory` where `productID` = ?";

            try {
                pst = conn.prepareStatement(statement);
                System.out.println("Enter ID");
                int productId = scan.nextInt();
                pst.setInt(1, productId);

                int executeStmt = pst.executeUpdate();
                if (executeStmt == 1) {
                    System.out.println("Record has been Deleted !");
                    // JOptionPane.showMessageDialog(null, "Record has been Deleted !", "Deleted",
                    //         JOptionPane.ERROR_MESSAGE);
                } else {
                    System.out.println("Failed to delete Record !");
                    // JOptionPane.showMessageDialog(null, "Failed to update Record !", "Failed",
                    //         JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException e) {
                // JOptionPane.showMessageDialog(null, "Connection Error", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private static void displayData() {
        Connection conn = getConnection();
        if (conn != null) {
            String statement = "select * from `inventory`.`inventory`";
            try {
                stmt = conn.createStatement();
                res = stmt.executeQuery(statement);
                System.out.printf(String.format("%-20s%-20s%-20s%-20s%-20s\n", "ProductId", "ProductName",
                        "ProductPrice", "SupplierId", "CompanyName"));
                while (res.next()) {
                    System.out.printf(String.format("%-20s%-20s%-20s%-20s%-20s\n", res.getInt(1), res.getString(2),
                            res.getInt(3), res.getInt(4), res.getString(5)));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}