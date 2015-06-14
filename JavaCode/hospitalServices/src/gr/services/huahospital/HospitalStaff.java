package gr.services.huahospital;

import java.util.Date;

public class HospitalStaff {
	private int staffID;
	private String firstName;
	private String lastSurname;
	private char gender;
	private Date birthDate;
	private int staffType;
	private String emp_no;
	private String specialty;
	

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public HospitalStaff() 
	{
	}

	public int getStaffID() 
	{
		return staffID;
	}

	public void setStaffID(int staffID) 
	{
		this.staffID = staffID;
	}

	public String getFirstName() 
	{
		return firstName;
	}

	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}

	public String getLastSurname() 
	{
		return lastSurname;
	}

	public void setLastSurname(String lastSurname) 
	{
		this.lastSurname = lastSurname;
	}

	public char getGender() 
	{
		return gender;
	}

	public void setGender(char gender) 
	{
		this.gender = gender;
	}

	public Date getBirthDate() 
	{
		return birthDate;
	}

	public void setBirthDate(Date birthDate) 
	{
		this.birthDate = birthDate;
	}

	public int getStaffType() 
	{
		return staffType;
	}

	public void setStaffType(int staffType) 
	{
		this.staffType = staffType;
	}

	public String getEmp_no() 
	{
		return emp_no;
	}

	public void setEmp_no(String emp_no) 
	{
		this.emp_no = emp_no;
	}
}
