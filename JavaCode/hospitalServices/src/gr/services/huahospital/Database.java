package gr.services.huahospital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	private static String db_url = "jdbc:mysql://62.217.125.30:3306/itp14105";
	private static String db_username = "itp14105";
	private static String db_password = "$12345$";

	private Connection conn;

	public Connection getConn() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException 
	{
		if (conn == null) 
		{
			// Create the connection
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager
					.getConnection(db_url, db_username, db_password);
		}
		return conn;
	}

	public int Update(PreparedStatement statement) throws SQLException //query, no response
	{
		try 
		{
			return statement.executeUpdate();
		} 
	
		catch (Exception ex) 
		{
			ex.printStackTrace();
			throw ex;
		} 
		finally 
		{
			try {
					if (statement != null) 
					{
						statement.close();
					}
				} 
			catch (final SQLException sqlEx) 
			{
				sqlEx.printStackTrace();
				throw sqlEx;
			}
		}
	}

	public ResultSet Query(PreparedStatement statement) throws Exception //query+attributes
	{
		try 
		{
			return statement.executeQuery();
		} 		
		catch (Exception ex) 
		{
			ex.printStackTrace();
			throw ex;
		} 
		/*finally 
		{
			try 
			{
				if (statement != null) 
				{
					statement.close();
				}
			} 
			catch (final SQLException sqlEx) 
			{
				sqlEx.printStackTrace();
			}
		}*/
	
	}

	public ResultSet Query(String query) //simpleUse 
	{
		try 
		{
			return Query(conn.createStatement(), query);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return null;
		}
	}

	public ResultSet Query(Statement statement, String query) 
	{
		try 
		{
			return statement.executeQuery(query);
		} 
		catch (SQLException sqlEx) 
		{
			sqlEx.printStackTrace();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			throw ex;
		} 
		finally 
		{
			/*try 
			{
				if (statement != null) 
				{
					statement.close();
				}
			} 
			catch (final SQLException sqlEx) 
			{
				sqlEx.printStackTrace();
			}*/
		}
		return null;
	}
}