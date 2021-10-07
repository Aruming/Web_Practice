package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/member/list")
public class MemberListServlet extends GenericServlet{

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			//1. 사용할 JDBC 드라이버 등록
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		
			//2. 드라이버 사용하여 MySQL 서버와 연결
			con = DriverManager.getConnection("jdbc:mysql://localhost/studydb", "study", "1111");
			
			//3. 커넥션 객체로부터 SQL 던질 객체 준비
			stmt = con.createStatement();
			
			//4. SQL 던지는 객체를 사용하여 서버에 쿼리
			rs = stmt.executeQuery("select MNO,MNAME,EMAIL,CRE_DATE from MEMBERS order by MNO ASC");
			
			//5. 서버에서 가져온 데이터 사용하여 HTML 만들어 웹브라우저로 출력
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>회원목록</title></head><body>");
			out.println("<h1>회원목록</h1>");
			out.println("<p><a href='add'>신규회원</a></p>");
			while(rs.next()) {
				out.println(rs.getInt("MNO")+","+rs.getString("MNAME")+","+rs.getString("EMAIL")
								+","+rs.getDate("CRE_DATE")+"<BR>");
			}
			out.println("</body></html>");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try{rs.close();} catch(Exception e) {}
			try{stmt.close();} catch(Exception e) {}
			try{con.close();} catch(Exception e) {}
		}
	}

}
