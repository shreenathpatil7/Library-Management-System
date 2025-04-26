import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String dbURL = "jdbc:mysql://localhost:3306/library_db";
        String dbUser = "root";
        String dbPass = "dbms945@#$";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(dbURL, dbUser, dbPass);

            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, password); // Note: store and compare hashed passwords in real apps!

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
            	
                String role = rs.getString("role");
                String username = rs.getString("username");
                int userId = rs.getInt("user_id"); // 👈 ADD THIS

                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("role", role);
                session.setAttribute("email", email);
                session.setAttribute("user_id", userId); // 👈 AND THIS

                if ("librarian".equals(role)) {
                    response.sendRedirect("librarianDashboard.jsp");
                } else {
                    response.sendRedirect("userDashboard.jsp");
                }
            } else {
                out.println("<h3 style='color:red;'>Invalid email or password!</h3>");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3 style='color:red;'>Error: " + e.getMessage() + "</h3>");
        }
    }
}
