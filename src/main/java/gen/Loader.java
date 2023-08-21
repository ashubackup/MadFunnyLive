package gen;

import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Servlet implementation class Loader
 */
@WebServlet("/Loader")
public class Loader extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static Connection Conn = null;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Loader() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init() throws ServletException {
        DBConnection db = new DBConnection();
        Conn = db.getDatabse();
        System.out.println("Db Connected in init first time");
    }
}
