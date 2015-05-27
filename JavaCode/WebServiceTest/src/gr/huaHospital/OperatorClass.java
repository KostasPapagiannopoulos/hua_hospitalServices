package gr.huaHospital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.jws.WebService;
import javax.jws.WebMethod;
//import javax.jws.WebParam;

@WebService
public class OperatorClass 
{
	
	
		int userId;
		String password;
		
		public OperatorClass()
		{
		}
		
		@WebMethod
		//public OperatorClass[] findRow()
		public ArrayList<OperatorClass> findRow()
		{
			Connection conn = null;
			Statement statement = null;
			String SQLStr = "";
			ResultSet rs = null;
			ArrayList<OperatorClass> al = new ArrayList<OperatorClass>();
			
				SQLStr = "SELECT * FROM `itp14105`.`test_user`;";
				try
				{
					Class.forName("com.mysql.jdbc.Driver").newInstance();
					conn = DriverManager.getConnection("jdbc:mysql://" + "62.217.125.30:3306/itp14105", "itp14105", "$12345$");
					statement = conn.createStatement();
					rs = statement.executeQuery(SQLStr);
					while(rs.next())
					{
						OperatorClass row = new OperatorClass();
						row.setUserId(rs.getInt("userId"));
						row.setPassword(rs.getString("password"));
						al.add(row);
					}
				}
				catch (SQLException sqlEx)
				{
					System.err.println(sqlEx.getMessage());
				}
				catch (ClassNotFoundException classNotFound)
				{
					System.err.println(classNotFound.getMessage());	
				}
				catch (Exception ex)
				{
					System.err.println(ex.getMessage());
				}
				finally
				{
					try
					{
						if (statement != null)
						{
							statement.close();
						}
					}
					catch(final SQLException sqlEx)
					{
						System.err.println(sqlEx.getMessage());
					}
				}
			
				//return (OperatorClass[])al.toArray();
				return al;
		}
		public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}			
}
