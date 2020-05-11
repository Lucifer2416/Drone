package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.ActionCategory;
import action.ActionItems;
import util.Const;

/**
 * Servlet implementation class AddCategory
 */
@WebServlet("/add-category")
public class AddCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCategory() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int action = Integer.parseInt(request.getParameter("a"));
		if (action == 1) {
			ActionCategory.getCategoryById(request, response);
			ActionCategory.getCategories(request, response);
			request.getRequestDispatcher(Const.PATH_PAGE_ADD_CATEGORY).forward(request, response);
		} else if (action == 2) {
			ActionCategory.removeCategory( request, response);
			ActionCategory.getCategories(request, response);
			response.sendRedirect("ListCategories");
//			
		} else {
			
			request.getRequestDispatcher(Const.PATH_PAGE_ADD_CATEGORY).forward(request, response);
		}
//		request.getRequestDispatcher(Const.PATH_PAGE_ADD_CATEGORY).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
