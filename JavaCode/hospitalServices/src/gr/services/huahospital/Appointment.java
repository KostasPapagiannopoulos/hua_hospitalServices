package gr.services.huahospital;

import java.sql.Time;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Appointment 
{
	private int appointmentID;
	private String patientName;
	private String patientSurname;
	private int AMKA;
	private String insuranceFund;
	private Integer clinicid;
	private String diseaseDetails;
	private Date appointmentDate;
	private String strAppointmentDate;
	private Time appointmentTime;
	private String strAppointmentTime;	
	private int appointmentEmergency;
	private String rejectReasons;
	private int appointmentState;
	
	
	
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

	public Integer getClinicid() 
	{
		return clinicid;
	}

	public void setClinicid(Integer clinicid) 
	{
		this.clinicid = clinicid;
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

	public Time getAppointmentTime() 
	{
		return appointmentTime;
	}

	public void setAppointmentTime(Time appointmentTime) 
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

	public int getAppointmentState() 
	{
		return appointmentState;
	}

	public void setAppointmentState(int appointmentState) 
	{
		this.appointmentState = appointmentState;
	}
	
	static DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    public String getStrAppointmentDate() {    	 
         return df.format(this.appointmentDate);        
    }

    public void setStrAppointmentDate(String strappointmentDate) throws ParseException {
        this.appointmentDate = new java.sql.Date( df.parse(strappointmentDate).getTime());
    }
    
    public String getStrAppointmentTime() 
	{
		return appointmentTime.toString();
	}

	public void setStrAppointmentTime(String appointmentTime) 
	{
		this.appointmentTime = java.sql.Time.valueOf(appointmentTime);
	}
}
