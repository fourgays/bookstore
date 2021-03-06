package cn.xkx.bookstore.book.dao;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cn.xkx.bookstore.book.domain.Book;
import cn.xkx.bookstore.book.domain.PageBean;
import cn.xkx.bookstore.category.domain.Category;
import cn.xkx.bookstore.order.domain.OrderItem;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class BookDao {
	private QueryRunner qr = new TxQueryRunner();

	// 查询所有图书
	public PageBean<Book> findAll(Integer pc, Integer ps) {
		 String sql = "SELECT * FROM book LIMIT ?,?";

		try {

			PageBean pb = new PageBean();
			pb.setPc(pc);
			pb.setPs(ps);

			// 查询总记录数tr
			String tr_sql = "SELECT COUNT(*) FROM book ";
			Number number = (Number) qr.query(tr_sql, new ScalarHandler());
			int tr = number.intValue();
			pb.setTr(tr);
			Object[] params = { ps * (pc - 1), ps };

			System.out.println("1==="+ps*pc);
			System.out.println("2==="+ps * (pc - 1));
			List<Book> beanList = qr.query(sql, new BeanListHandler<Book>(Book.class), params);

			pb.setBeanList(beanList);

			return pb;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * 按分类查询图书
	 */
	public PageBean<Book> findByCategory(String cid, Integer pc, Integer ps) {
		 String sql = "SELECT * FROM book where cid = ? LIMIT ?,?";
		try {
			PageBean pb = new PageBean();
			pb.setPc(pc);
			pb.setPs(ps);

			// 查询总记录数tr
			String tr_sql = "SELECT COUNT(*) FROM book where cid = ?";
			Number number = (Number) qr.query(tr_sql, new ScalarHandler(), cid);
			int tr = number.intValue();
			pb.setTr(tr);

			Object[] params = { cid, ps * (pc-1), ps * pc  };
			List<Book> beanList = qr.query(sql, new BeanListHandler<Book>(Book.class), params);

			pb.setBeanList(beanList);

			return pb;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 加载方法、根据bid查找图书
	 * 
	 * @param bid
	 * @return
	 */

	public Book findByBid(String bid) {
		String sql = "select * from book where bid=?";
		try {
			Map<String, Object> map = qr.query(sql, new MapHandler(), bid);
			Category category = CommonUtils.toBean(map, Category.class);
			Book book = CommonUtils.toBean(map, Book.class);
			book.setCategory(category);
			return book;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查询指定分类下的图书的数量
	 * 
	 * @param cid
	 * @return
	 */
	public int getCountByCid(String cid) {
		String sql = "select count(*) from book where cid=?";
		try {
			Number num = (Number) qr.query(sql, new ScalarHandler(), cid);
			return num.intValue();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 添加图书
	 * 
	 * @param book
	 */
	public void add(Book book) {
		String sql = "insert into book VALUES(?,?,?,?,?,?)";
		try {
			Object[] params = { book.getBid(), book.getBname(), book.getPrice(), book.getAuthor(), book.getImage(),
					book.getCategory().getCid() };
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 删除图书
	 * 
	 * @param bid
	 */
	public boolean delete(String bid) {
		String check_sql = "select * from orderItem where bid = ?";
		String sql = "delete from book where bid = ?";
		try {
			List<OrderItem> orderItemList = qr.query(check_sql, new BeanListHandler<OrderItem>(OrderItem.class), bid);
			if (orderItemList.size() > 0) {
				return false;
			} else {
				qr.update(sql, bid);
				return true;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查找图书
	 * 
	 * @param bookName
	 * @param pc
	 * @param ps
	 * @return
	 */
	public PageBean<Book> searchBooks(String bookName, int pc, int ps) {
		StringBuilder sql = new StringBuilder("SELECT * FROM book where");
		sql.append(" bname LIKE ?");
		sql.append(" LIMIT ?,?");
		try {

			PageBean pb = new PageBean();
			pb.setPc(pc);
			pb.setPs(ps);

			// 查询总记录数tr
			StringBuilder tr_sql = new StringBuilder("SELECT COUNT(*) FROM book where");
			String s = "%" + bookName + "%";
			tr_sql.append(" bname LIKE ?");
			Number number = (Number) qr.query(tr_sql.toString(), new ScalarHandler(), s);
			int tr = number.intValue();
			pb.setTr(tr);

			Object[] params = { s, ps * (pc - 1), ps };
			List<Book> beanList = qr.query(sql.toString(), new BeanListHandler<Book>(Book.class), params);

			pb.setBeanList(beanList);

			return pb;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
