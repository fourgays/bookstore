package cn.xkx.bookstore.statistical.service;

import cn.itcast.servlet.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/admin/StatisticalServlet")
public class StatisticalService extends BaseServlet {
    public String RankSaleBook(HttpServletRequest request, HttpServletResponse response) {

        return "f:/jsps/left.jsp";
    }
}
