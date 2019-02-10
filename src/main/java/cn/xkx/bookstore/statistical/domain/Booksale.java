package cn.xkx.bookstore.statistical.domain;

public class Booksale {
    private String bname;
    private int num;

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Booksale{" +
                "bname='" + bname + '\'' +
                ", num=" + num +
                '}';
    }
}