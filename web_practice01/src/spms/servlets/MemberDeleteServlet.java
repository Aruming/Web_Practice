package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/member/delete")
public class MemberDeleteServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;
		Statement stmt = null;
		
		try {
			//컨텍스트 초기화 매개변수 사용
			ServletContext ctx = this.getServletContext();
			Class.forName(ctx.getInitParameter("driver"));
			
			//2. 드라이버 사용하여 MySQL 서버와 연결
			con = DriverManager.getConnection(
					ctx.getInitParameter("url"),
					ctx.getInitParameter("username"),
					ctx.getInitParameter("password"));
			
			//3. 커넥션 객체로부터 SQL 던질 객체 준비
			stmt = con.createStatement();
			stmt.executeUpdate("delete from members where MNO="+
					request.getParameter("no"));
			
			response.sendRedirect("list");
			
		} catch (Exception e) {
			throw new ServletException(e);
		}finally {
			try{stmt.close();} catch(Exception e) {}
			try{con.close();} catch(Exception e) {}
		}
	}
}
