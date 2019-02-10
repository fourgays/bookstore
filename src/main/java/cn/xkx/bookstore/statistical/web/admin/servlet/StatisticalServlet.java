package cn.xkx.bookstore.statistical.web.admin.servlet;

import cn.xkx.bookstore.statistical.domain.Booksale;
import cn.xkx.bookstore.statistical.domain.Salesituation;
import cn.xkx.bookstore.statistical.service.StatisticalService;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/admin/StatisticalServlet")
public class StatisticalServlet extends HttpServlet {
    StatisticalService statisticalService = new StatisticalService();
    public void doGet (HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        String methodname = request.getParameter("method");
        if("RankSaleBook".equals(methodname)) {
            List<Booksale> result = statisticalService.getBookRank();
            response.setContentType("application/json; charset=utf-8");
            String resutljson = JSON.toJSONString(result);
            PrintWriter out = response.getWriter();
            out.print(resutljson);
        }
        else if("salesituation".equals(methodname)){
            List<Salesituation> result = statisticalService.getSalesituation();
            response.setContentType("application/json; charset=utf-8");
            String resutljson = JSON.toJSONString(result);
            PrintWriter out = response.getWriter();
            out.print(resutljson);
        }
    }
}
