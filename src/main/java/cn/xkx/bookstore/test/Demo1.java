package cn.xkx.bookstore.test;

import org.junit.Test;

public class Demo1 {

	@Test
	public void fun1()
	{
		String hmac=PaymentUtil.buildHmac("Buy", "10001126856", "123456", 
				"1", "CNY", "", "", "","http://localhost:8080/bookstore/OrderServlet?method=back", "",
				"", "ICBC-NET-B2C", "1", "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl");
		System.out.println(hmac);
	}
}
//https://www.yeepay.com/app-merchant-proxy/node?p0_Cmd=Buy&p1_MerId=10001126856&p2_Order=123456&p3_Amt=1&p4_Cur=CNY&p5_Pid=&p6_Pcat=&p7_Pdesc=&p8_Url=http://localhost:8080/bookstore/OrderServlet?method=back&p9_SAF=&pa_MP=&pd_FrpId=ICBC-NET-B2C&pr_NeedResponse=1&hmac=76befbe4ab0f4aa7b021d6628efc2d81
