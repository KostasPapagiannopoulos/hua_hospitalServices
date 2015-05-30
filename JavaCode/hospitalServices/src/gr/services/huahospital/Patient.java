package gr.services.huahospital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class Patient 
{
	private int patientID;
	private String patientName;
	private String patientSurname;
	private char patientGender;
	private String insuranceFund;
	private int AMKA;
	private String bloodType;
	private String address;
	private String country;

	public Patient() 
	{
	}
	
	@WebMethod
	public String insertPatient()
	{
		Connection conn = null;
		Statement statement = null;
		String SQLStr;
		String retString = "";
		
			try
			{
				Class.forName("com.mysql.jdbc.Driver").newInstance();
					
				conn = DriverManager.getConnection("jdbc:mysql://" + "62.217.125.30:3306/itp14105", "itp14105", "$12345$");
					
				SQLStr = "INSERT INTO `itp14105`.`patient` " + "VALUES ('" + getPatientID() + "', '" + getPatientName() + "', '" + getPatientSurname() + "', '" + getPatientGender() + "',  '" + getInsuranceFund() + "', '" + getAMKA() + "', '" + getBloodType() + " ', '" + getAddress() + "', '" + getCountry() + "');";
								
				statement = conn.createStatement();
				statement.executeUpdate(SQLStr);
					
				retString = "� ���������� ��� ���������������� �� ��������! �� ����� ������ ��� ����� "+ String.valueOf(getPatientID());
			}
			catch (SQLException sqlEx)
			{
					sqlEx.printStackTrace();
			}
			catch (ClassNotFoundException classNotFound)
			{
					classNotFound.printStackTrace();
			}
			catch (Exception ex)
			{
					ex.printStackTrace();
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
				catch (final SQLException sqlEx)
				{
					sqlEx.printStackTrace();
				}
			}
			return retString;
		
	}
	
	@WebMethod
	public ArrayList<Patient> findPatient()
	{
		Connection conn = null;
		Statement statement = null;
		String SQLStr = "";
		ResultSet rs = null;
		ArrayList<Patient> arrList = new ArrayList<Patient>();
		
			SQLStr = "SELECT * FROM `itp14105`.`patient`;";
			try
			{
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection("jdbc:mysql://" + "62.217.125.30:3306/itp14105", "itp14105", "$12345$");
				statement = conn.createStatement();
				rs = statement.executeQuery(SQLStr);
				while(rs.next())
				{
					Patient patientInstance = new Patient();
					patientInstance.setPatientID(rs.getInt("patientId"));
					patientInstance.setPatientName(rs.getString("patientName"));
					patientInstance.setPatientSurname(rs.getString("patientSurname"));
					patientInstance.setPatientGender(rs.getString("patientGender").charAt(0));
					patientInstance.setInsuranceFund(rs.getString("insuranceFund"));
					patientInstance.setAMKA(rs.getInt("AMKA"));
					patientInstance.setBloodType(rs.getString("bloodType"));
					patientInstance.setAddress(rs.getString("address"));
					patientInstance.setCountry(rs.getString("country"));
					
					
					arrList.add(patientInstance);
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
		
			return arrList;
	} 
	
	public int getPatientID() 
	{
		return patientID;
	}

	public void setPatientID(int patientID) 
	{
		this.patientID = patientID;
	}

	public String getPatientName() 
	{
		return patientName;
	}

	public void setPatientName(String patientName) 
	{
		this.patientName = patientName;
	}

	public String getPatientSurname() 
	{
		return patientSurname;
	}

	public void setPatientSurname(String patientSurname) 
	{
		this.patientSurname = patientSurname;
	}

	public char getPatientGender() 
	{
		return patientGender;
	}

	public void setPatientGender(char patientGender) 
	{
		this.patientGender = patientGender;
	}

	public String getInsuranceFund() 
	{
		return insuranceFund;
	}

	public void setInsuranceFund(String insuranceFund) 
	{
		this.insuranceFund = insuranceFund;
	}

	public int getAMKA() 
	{
		return AMKA;
	}

	public void setAMKA(int AMKA) 
	{
		this.AMKA = AMKA;
	}

	public String getBloodType() 
	{
		return bloodType;
	}

	public void setBloodType(String bloodType) 
	{
		this.bloodType = bloodType;
	}

	public String getAddress()
	{

		return address;
	}

	public void setAddress(String address) 
	{
		this.address = address;
	}

	public String getCountry() 
	{
		return country;
	}

	public void setCountry(String country) 
	{
		this.country = country;
	}
	
}