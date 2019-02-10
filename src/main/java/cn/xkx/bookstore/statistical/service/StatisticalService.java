package cn.xkx.bookstore.statistical.service;

import cn.itcast.servlet.BaseServlet;
import cn.xkx.bookstore.book.dao.BookDao;
import cn.xkx.bookstore.book.domain.Book;
import cn.xkx.bookstore.order.dao.OrderDao;
import cn.xkx.bookstore.order.domain.OrderItem;
import cn.xkx.bookstore.statistical.domain.Booksale;
import cn.xkx.bookstore.statistical.domain.Salesituation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StatisticalService extends BaseServlet {
    OrderDao orderDao = new OrderDao();
    BookDao bookDao = new BookDao();
    public List<Booksale> getBookRank() {
        Map<String,Integer> counts = new TreeMap<String, Integer>();
        List<Booksale> result = new ArrayList<Booksale>();
        List<String> oids = orderDao.getAllOrderoid();
        for(String oid:oids){
            List<OrderItem> orderItems = orderDao.load(oid).getOrderItemList();
            for(OrderItem item :orderItems){
                String bid = item.getBook().getBid();
                Integer count = counts.get(bid);
                if(null==count)
                    counts.put(bid,item.getCount());
                else
                    counts.put(bid,count+item.getCount());
                }
        }
        for(int i = 0 ;i<5;i++){
            int max = Integer.MIN_VALUE;
            String bid=null;
            Iterator<String> it = counts.keySet().iterator();
            while(it.hasNext()){
                String temp = it.next();
                int count = counts.get(temp);
                if(count>max){
                    max=count;
                    bid=temp;
                }
            }
            Book book = bookDao.findByBid(bid);
            Booksale booksale = new Booksale();
            booksale.setBname(book.getBname());
            booksale.setNum(max);
            result.add(booksale);
            counts.remove(bid);
        }
        return result;
    }
    public List<Salesituation> getSalesituation(){
        //一天毫秒数86400000
        List<Salesituation> salesituations = orderDao.getAllorderTimeAndDate();
        Map<Long,Double> temp = new HashMap<Long, Double>();
        List<Salesituation> result = new ArrayList<Salesituation>();
        long today = new Date().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        long first = 0;
        long last = 0;
        for(int i = 0;i<7;i++){
            if(i==0) {
                calendar.set(Calendar.HOUR, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                first = calendar.getTime().getTime();
                calendar.set(Calendar.HOUR, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                last = calendar.getTime().getTime();
            }
            else{
                last = new Long(first);
                first-= 86400000;
            }
            temp.put(first,0.0);
            for(Salesituation salesituation:salesituations){
                long time = salesituation.getOrdertime().getTime();
                if(time>=first&&time<=last){
                    temp.put(first,temp.get(first)+salesituation.getTotal());
                }
            }
            Salesituation salesituation = new Salesituation();
            salesituation.setTotal(temp.get(first));
            salesituation.setOrdertime(new Date(first));
            result.add(salesituation);
        }
        for(int i=0;i<3;i++){
            Salesituation tem = new Salesituation(result.get(i));
            result.set(i,new Salesituation(result.get(6-i)));
            result.set(6-i,new Salesituation(tem));
        }
        return result;
    }
}
