package gen;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);

		String Url = "landing";
		try {

			System.out.println("ani::"+request.getParameter("number"));
			String ani=new DataCollector().checkAni(request.getParameter("number"));
			//String ani=new DataCollector().checkAni("9878715559");
			String status = new DataCollector().getStatus(ani);
			System.out.println(status);
			if (status.equalsIgnoreCase("1")) {
				session.setAttribute("ani", ani);
				response.sendRedirect("home");
				Url = "home";
			} 
			else {
				response.sendRedirect("http://optin.telkomsdp.co.za/service/248");
				Url = "home";
			}
//			else if (status.equalsIgnoreCase("0")) {
//				request.setAttribute("errorMessage", "Please recharge your account to gain entry to GameOMania");
//			}else if (status.equalsIgnoreCase("2")) {
//				response.sendRedirect("http://optin.telkomsdp.co.za/service/68");
//				Url = "index";
//			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Something Went Wrong");
		}
		if(Url != "home") {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/"+Url+"");
			dispatcher.forward(request, response);
		}
		
	}

}
