import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Invalidate the current session
        HttpSession session = request.getSession(false); // false = don't create if it doesn't exist
        if (session != null) {
            session.invalidate();
        }

        // Redirect to login page after logout
        response.sendRedirect("index.html");
    }
}
