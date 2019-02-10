package cn.xkx.bookstore.statistical.domain;
import java.util.Date;
public class Salesituation {
    private Date ordertime;
    private double total;

    public Salesituation() {
    }

    public Salesituation(Date ordertime, double total) {
        this.ordertime = ordertime;
        this.total = total;
    }

    public Salesituation(Salesituation salesituation) {
        this.ordertime = salesituation.ordertime;
        this.total = salesituation.total;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Salesituation{" +
                "ordertime=" + ordertime +
                ", total=" + total +
                '}';
    }
}
