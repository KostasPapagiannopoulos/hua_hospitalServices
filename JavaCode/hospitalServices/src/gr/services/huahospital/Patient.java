package gr.services.huahospital;

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
	private String email;
	private String username;




	public Patient() 
	{
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



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}

}
