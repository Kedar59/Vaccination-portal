import com.password4j.BcryptFunction;
import com.password4j.Password;
import com.password4j.types.Bcrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {
    public static void bookDose1(String username,String date1,String vaccType,boolean bStatus){
        String sql = "UPDATE employee SET dose1Date = ?, vaccineType = ?, bookingStatus = ? WHERE username = ?";

        try (PreparedStatement pstmt = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {

            pstmt.setString(1, date1);
            pstmt.setString(2, vaccType);
            pstmt.setBoolean(3, bStatus);
            pstmt.setString(4, username);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Dose 1 booked successfully for user: " + username);
            } else {
                System.out.println("Booking failed. User not found: " + username);
            }

        } catch (SQLException e) {
            System.out.println("Error booking dose 1: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void bookDose2(String username,String date2,boolean bStatus){
        String sql = "UPDATE employee SET dose2Date = ?, bookingStatus = ? WHERE username = ?";

        try (PreparedStatement pstmt = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {

            pstmt.setString(1, date2);
            pstmt.setBoolean(2, bStatus);
            pstmt.setString(3, username);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Dose 2 booked successfully for user: " + username);
            } else {
                System.out.println("Booking failed. User not found: " + username);
            }

        } catch (SQLException e) {
            System.out.println("Error booking dose 1: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static Employee login(String username, String password) {
        String sql = "SELECT * FROM employee WHERE username = ?";
        Employee employee = null;

        try (PreparedStatement pstmt = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    employee = new Employee();
                    employee.setE_id((int) rs.getLong("emp_id"));
                    employee.setE_name(rs.getString("username"));
                    employee.setE_pass(rs.getString("password"));
                    employee.setVacc_status(rs.getInt("vaccineTaken"));
                    employee.setDate1(rs.getString("dose1Date"));
                    employee.setDate2(rs.getString("dose2Date"));
                    employee.setVacc_type(rs.getString("vaccineType"));
                    employee.setB_status(rs.getBoolean("bookingStatus"));
                    BcryptFunction bcrypt = BcryptFunction.getInstance(Bcrypt.B, 12);

                    boolean verified = Password.check(password, employee.e_pass)
                            .addPepper("shared-secret")
                            .with(bcrypt);
                    if (verified) {
                        return employee;
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
            e.printStackTrace();
        }

        return employee;
    }

    public static Employee register(Employee newUser) {
        String sql = "INSERT INTO employee (username, password, vaccineTaken, dose1Date, dose2Date, vaccineType, bookingStatus) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = DatabaseConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstmt.setString(1, newUser.getE_name());
            pstmt.setString(2, newUser.getE_pass());  // Ensure this is hashed before storing
            pstmt.setInt(3, newUser.getVacc_status());
            pstmt.setString(4, newUser.getDate1());
            pstmt.setString(5, newUser.getDate2());
            pstmt.setString(6, newUser.getVacc_type());
            pstmt.setBoolean(7, newUser.isB_status());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("User registered successfully.");
                return newUser;
            } else {
                System.out.println("User registration failed.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static boolean userExists(String username) {
        DatabaseConnection db = DatabaseConnection.getInstance();
        String query = "SELECT 1 FROM employee WHERE username = ?";
        boolean exists = false;

        try (PreparedStatement stmt = DatabaseConnection.getInstance().getConnection().prepareStatement(query)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    exists = true; // Username exists in the database
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // You might want to log this exception or rethrow it based on your needs
        }

        return exists;

    }
}
