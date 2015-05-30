package gr.services.huahospital;

import java.util.Date;

public class Appointment 
{
	private int appointmentID;
	private String patientName;
	private String patientSurname;
	private int AMKA;
	private String insuranceFund;
	private String infirmary;
	private String diseaseDetails;
	private Date appointmentDate;
	private String appointmentTime;
	private int appointmentEmergency;
	private String rejectReasons;
	private String appointmentAccelerate;
	
	public Appointment() 
	{
	}

	public int getAppointmentID() 
	{
		return appointmentID;
	}

	public void setAppointmentID(int appointmentID) 
	{
		this.appointmentID = appointmentID;
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

	public int getAMKA() 
	{
		return AMKA;
	}

	public void setAMKA(int aMKA) 
	{
		AMKA = aMKA;
	}

	public String getInsuranceFund() 
	{
		return insuranceFund;
	}

	public void setInsuranceFund(String insuranceFund) 
	{
		this.insuranceFund = insuranceFund;
	}

	public String getInfirmary() 
	{
		return infirmary;
	}

	public void setInfirmary(String infirmary) 
	{
		this.infirmary = infirmary;
	}

	public String getDiseaseDetails() 
	{
		return diseaseDetails;
	}

	public void setDiseaseDetails(String diseaseDetails) 
	{
		this.diseaseDetails = diseaseDetails;
	}

	public Date getAppointmentDate() 
	{
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) 
	{
		this.appointmentDate = appointmentDate;
	}

	public String getAppointmentTime() 
	{
		return appointmentTime;
	}

	public void setAppointmentTime(String appointmentTime) 
	{
		this.appointmentTime = appointmentTime;
	}

	public int getAppointmentEmergency() 
	{
		return appointmentEmergency;
	}

	public void setAppointmentEmergency(int appointmentEmergency) 
	{
		this.appointmentEmergency = appointmentEmergency;
	}

	public String getRejectReasons() 
	{
		return rejectReasons;
	}

	public void setRejectReasons(String rejectReasons) 
	{
		this.rejectReasons = rejectReasons;
	}

	public String getAppointmentAccelerate() 
	{
		return appointmentAccelerate;
	}

	public void setAppointmentAccelerate(String appointmentAccelerate) 
	{
		this.appointmentAccelerate = appointmentAccelerate;
	}
}