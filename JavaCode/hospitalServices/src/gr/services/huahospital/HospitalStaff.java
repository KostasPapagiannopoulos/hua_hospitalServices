package gr.services.huahospital;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class HospitalStaff {
	private int staffID;
	private String firstName;
	private String lastSurname;
	private char gender;
	private Date birthDate;
	private int staffType;
	private String emp_no;

	public HospitalStaff() {
	}

	Database db = new Database();

	public Database getDb() {
		return db;
	}

	@WebMethod
	public String insertStaff(HospitalStaff hospitalStaff) {

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

			db.Update(preparedStmnt);

			return "� ���������� ��� ���������������� �� ��������! �� ����� ������ ��� ����� "
					+ String.valueOf(getStaffID());
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
	public ArrayList<HospitalStaff> returnAllStaff() {

		try {

			ArrayList<HospitalStaff> arrList = new ArrayList<HospitalStaff>();

			String SQLStr = "SELECT * FROM `itp14105`.`hospitalstaff`;";
			ResultSet rs = db.Query(SQLStr);
			while (rs.next()) {
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
		} catch (SQLException e) {

			e.printStackTrace();
			return new ArrayList<HospitalStaff>();
		}

	}

	@WebMethod
	public HospitalStaff returnStaffByStaffId(int staffID) {
		try {

			String SQLStr = "SELECT * FROM `itp14105`.`hospitalstaff` where staffID = ?;";

			PreparedStatement preparedStmnt = db.getConn().prepareStatement(SQLStr);

			preparedStmnt.setInt(1, getStaffID());
			// Excecute the query
			ResultSet rs = db.Query(preparedStmnt);
			// we get only one
			if (rs.next()) {
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
		// we didnt find anyone with this Staff id
		return null;

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

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
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