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
public class Appointment 
{
	private int appointmentID;
	private String patientName;
	private String patientSurname;
	private int AMKA;
	private String insuranceFund;
	private String infirmary;
	private String diseaseDetails;
	private String appointmentDate;
	private String appointmentTime;
	private int appointmentEmergency;
	private String rejectReasons;
	private String appointmentAccelerate;
	
	public Appointment() 
	{
	}
	
	@WebMethod
	public String insertAppointment()
	{
		Connection conn = null;
		Statement statement = null;
		String SQLStr;
		String retString = "";
		
			try
			{
				Class.forName("com.mysql.jdbc.Driver").newInstance();
					
				conn = DriverManager.getConnection("jdbc:mysql://" + "62.217.125.30:3306/itp14105", "itp14105", "$12345$");	
				SQLStr = "INSERT INTO `itp14105`.`hospitalstaff` " + "VALUES ('" + getStaffID() + "', '" + getFirstName() + "', '" + getLastSurname() + "', '" + getGender() + "',  '" + getBirthDate() + "', '" + getStaffType() + "', '" + getEmp_no() + " ');";				
				statement = conn.createStatement();
				statement.executeUpdate(SQLStr);
					
				retString = "Η καταχώρηση σας πραγματοποιήθηκε με επιτυχία! Το Όνομα Χρήστη σας είναι "+ String.valueOf(getStaffID());
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
	public ArrayList<HospitalStaff> returnAllStaff()
	{
		Connection conn = null;
		Statement statement = null;
		String SQLStr = "";
		ResultSet rs = null;
		ArrayList<HospitalStaff> arrList = new ArrayList<HospitalStaff>();
		
			SQLStr = "SELECT * FROM `itp14105`.`hospitalstaff`;";
			try
			{
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				conn = DriverManager.getConnection("jdbc:mysql://" + "62.217.125.30:3306/itp14105", "itp14105", "$12345$");
				statement = conn.createStatement();
				rs = statement.executeQuery(SQLStr);
				while(rs.next())
				{
					HospitalStaff staffInstance = new HospitalStaff();
					staffInstance.setStaffID(rs.getInt("staffID"));
					staffInstance.setFirstName(rs.getString("firstName"));
					staffInstance.setLastSurname(rs.getString("lastName"));
					staffInstance.setGender(rs.getString("gender").charAt(0));
					staffInstance.setBirthDate(rs.getString("birthDate"));
					staffInstance.setStaffType(rs.getInt("staffType"));
					staffInstance.setEmp_no(rs.getString("emp_no"));
					
					arrList.add(staffInstance);
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

	public int getStaffID() {
		return staffID;
	}

	public void setStaffID(int staffID) {
		this.staffID = staffID;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastSurname() {
		return lastSurname;
	}

	public void setLastSurname(String lastSurname) {
		this.lastSurname = lastSurname;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public int getStaffType() {
		return staffType;
	}

	public void setStaffType(int staffType) {
		this.staffType = staffType;
	}

	public String getEmp_no() {
		return emp_no;
	}

	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	} 
}
