package com.lti.training.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lti.training.dao.ProductDao;
import com.lti.training.model.Product;

public class ProductControllerServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
				int pageSize = 5;
			
				//storing current position in session
				HttpSession session= request.getSession();
				Integer currentPosition = (Integer) session.getAttribute("cp");
				if(currentPosition == null)
					currentPosition = 1;
				
				String go = request.getParameter("go");
				if(go != null) {
					if(go.equals("next"))
						currentPosition += pageSize;
					else if(go.equals("previous"))
						currentPosition -= pageSize;
				}
				else
					currentPosition = 1;
				
				session.setAttribute("cp", currentPosition);
				
				ProductDao dao = new ProductDao();
				List<Product> products = dao.fetchProducts(currentPosition, currentPosition + pageSize);
				//System.out.println(products.size());
				request.setAttribute("currentPosition", products);
				//response.sendRedirect("viewProducts.jsp");
				RequestDispatcher dispatcher= request.getRequestDispatcher("viewProducts.jsp");
				dispatcher.forward(request, response);
		}
}
