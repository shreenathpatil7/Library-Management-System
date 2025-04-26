import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet("/AddBookServlet")
public class AddBookServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String isbn = request.getParameter("isbn");
        int year = Integer.parseInt(request.getParameter("publication_year"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/library_db", "root", "dbms945@#$");

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO books (title, author, isbn, publication_year, quantity) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, isbn);
            ps.setInt(4, year);
            ps.setInt(5, quantity);

            int rows = ps.executeUpdate();
            con.close();

            if (rows > 0) {
                response.sendRedirect("viewBooks.jsp");
            } else {
                response.getWriter().println("Failed to add book.");
            }
        } catch (Exception e) {
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
