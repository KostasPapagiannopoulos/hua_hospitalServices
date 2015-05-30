package gr.services.huahospital;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class HospitalStaffMethods 
{
	
	Database db = new Database();

	public Database getDb() 
	{
		return db;
	}
	
	@WebMethod
	public String insertStaff(HospitalStaff hospitalStaff) 
	{
		try {

			String SQLStr = "INSERT INTO `itp14105`.`hospitalstaff` "
					+ "VALUES (?, ?, ?, ?,  ?, ?, ?);";
			PreparedStatement preparedStmnt = db.getConn().prepareStatement(SQLStr);

			preparedStmnt.setInt(1, hospitalStaff.getStaffID());
			preparedStmnt.setString(2, hospitalStaff.getFirstName());
			preparedStmnt.setString(3, hospitalStaff.getLastSurname());
			preparedStmnt.setString(4, Character.toString(hospitalStaff.getGender()));
			preparedStmnt.setDate(5, new java.sql.Date(hospitalStaff.getBirthDate().getTime()));
			preparedStmnt.setInt(6, hospitalStaff.getStaffType());
			preparedStmnt.setString(7, hospitalStaff.getEmp_no());

			db.Update(preparedStmnt); //commit

			return "� ���������� ��� ���������������� �� ��������! �� ����� ������ ��� ����� "
					+ String.valueOf(hospitalStaff.getStaffID());
		} catch (SQLException e) {

			e.printStackTrace();
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Error";
	}
	
	@WebMethod
	public ArrayList<HospitalStaff> returnAllStaff() 
	{
		try {
				ArrayList<HospitalStaff> arrList = new ArrayList<HospitalStaff>();
	
				String SQLStr = "SELECT * FROM `itp14105`.`hospitalstaff`;";
				ResultSet rs = db.Query(SQLStr);
				while (rs.next()) 
				{
					HospitalStaff staffInstance = new HospitalStaff();
					staffInstance.setStaffID(rs.getInt("staffID"));
					staffInstance.setFirstName(rs.getString("firstName"));
					staffInstance.setLastSurname(rs.getString("lastName"));
					staffInstance.setGender(rs.getString("gender").charAt(0));
					staffInstance.setBirthDate(rs.getDate("birthDate"));
					staffInstance.setStaffType(rs.getInt("staffType"));
					staffInstance.setEmp_no(rs.getString("emp_no"));
	
					arrList.add(staffInstance);
				}
				return arrList;
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
				return new ArrayList<HospitalStaff>();
			}
	}

	@WebMethod
	public HospitalStaff returnStaffByStaffId(int staffID) 
	{
		PreparedStatement preparedStmnt = null;
		try {

			String SQLStr = "SELECT * FROM `itp14105`.`hospitalstaff` where staffID = ?;";

			preparedStmnt = db.getConn().prepareStatement(SQLStr);

			preparedStmnt.setInt(1, staffID);
			// Excecute the query
			ResultSet rs = db.Query(preparedStmnt);
			// we get only one
			if (rs.next()) 
			{
				HospitalStaff staffInstance = new HospitalStaff();
				staffInstance.setStaffID(rs.getInt("staffID"));
				staffInstance.setFirstName(rs.getString("firstName"));
				staffInstance.setLastSurname(rs.getString("lastName"));
				staffInstance.setGender(rs.getString("gender").charAt(0));
				staffInstance.setBirthDate(rs.getDate("birthDate"));
				staffInstance.setStaffType(rs.getInt("staffType"));
				staffInstance.setEmp_no(rs.getString("emp_no"));

				return staffInstance;
			}

		} catch (SQLException e) {

			e.printStackTrace();

		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				if (preparedStmnt != null) 
				{
					preparedStmnt.close();
				}
			} 
			catch (final SQLException sqlEx) 
			{
				sqlEx.printStackTrace();
			}
	
		}
		// we didnt find anyone with this Staff id
		return null;

	}	
}