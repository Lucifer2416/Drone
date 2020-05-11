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
 * Servlet implementation class AddProduct
 */
@WebServlet(name = "addproduct", urlPatterns = { "/add-product" })
public class AddProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddProduct() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int action = Integer.parseInt(request.getParameter("a"));
		if (action == 1) {
			ActionItems.getItemById(Integer.parseInt(request.getParameter("product_id")), request, response);
			ActionCategory.getActiveCategories(request, response);
			request.getRequestDispatcher(Const.PATH_PAGE_ADD_PRODUCT).forward(request, response);
		} else if (action == 2) {
			ActionItems.removeItem(Integer.parseInt(request.getParameter("product_id")), request, response);
			ActionItems.getItems(request, response);
			response.sendRedirect("ListProduct?category=1");
//			
		} else {
			ActionCategory.getActiveCategories(request, response);
			request.getRequestDispatcher(Const.PATH_PAGE_ADD_PRODUCT).forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			if(ActionItems.addItem(request, response)) {
				request.getRequestDispatcher(Const.PATH_PAGE_PRODUCT_ADD_COMPLETE).forward(request, response);
			}
			else {
				request.getRequestDispatcher("add-product?a=3");
			}
	}

}
