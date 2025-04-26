import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Read form data
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");  // new field

        // JDBC setup
        String url = "jdbc:mysql://localhost:3306/library_db";
        String dbUsername = "root";
        String dbPassword = "dbms945@#$";

        try {
            // Load JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);

            // Prepare SQL insert
            String query = "INSERT INTO users(username, email, password, role) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, email);
            pst.setString(3, password);  // Note: use hashed password for security!
            pst.setString(4, role);

            int row = pst.executeUpdate();

            if (row > 0) {
                // Registration successful, redirect to login
                response.sendRedirect("login.jsp");
            } else {
                out.println("<h3 style='color:red;'>Registration failed. Try again!</h3>");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
        }
    }
}
