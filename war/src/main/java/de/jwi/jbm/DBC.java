package de.jwi.jbm;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class DBC extends HttpServlet {

	ServletContext servletContext = null;
	private DataSource ds;

	@Override
	public void init() throws ServletException {
		super.init();

		servletContext = getServletContext();

		try {
			Context ic = new InitialContext();

			ds = (DataSource) ic.lookup("java:comp/env/jdbc/scuttle");
		} catch (NamingException e) {

			throw new ServletException(e);
		}

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		try {
			Connection c = ds.getConnection();

			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery("select 1");

			int n = 0;

			while (rs.next()) {
				n = rs.getInt(1);
				System.out.println(n);
				response.getWriter().print(n);
			}

			rs.close();

			c.close();
		} catch (SQLException e) {
			throw new ServletException(e);
		}

	}
}