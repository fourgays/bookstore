package cn.xkx.bookstore.cart.web.servlet;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;
import cn.xkx.bookstore.book.domain.Book;
import cn.xkx.bookstore.book.service.BookService;
import cn.xkx.bookstore.cart.domain.Cart;
import cn.xkx.bookstore.cart.domain.CartItem;

@WebServlet("/CartServlet")
public class CartServlet extends BaseServlet {

	/**
	 * 添加购物车条目
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		/*
		 * 先得到车，然后得到条目，然后把条目放到车中
		 */
		/*
		 * 表单传递只有bid和数量
		 * 先得到条目
		 *   >得到图书和数量
		 *   >先得到图书的bid，然后我们需要通过bid查询数据库得到Book
		 *   >数量表单中有
		 */
		String bid = request.getParameter("bid");
		Book book = new BookService().load(bid);
		int count = Integer.parseInt(request.getParameter("count"));
		CartItem cartItem = new CartItem();
		cartItem.setBook(book);
		cartItem.setCount(count);
		
		/*
		 * 把条目添加到车中
		 */
		cart.add(cartItem);
		return "f:/jsps/cart/list.jsp"; 
	}

	/**
	 * 清空购物车条目
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */

	public String clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 *1、 得到车
		 *2、清空车
		 */
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		cart.clear();
		return "f:/jsps/cart/list.jsp";
	}

	/**
	 * 删除购物车条目
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 1.得到车
		 * 2.得到要删除的bid
		 */
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		String bid = request.getParameter("bid");
		cart.delete(bid);
		return "f:/jsps/cart/list.jsp";
	}
}
