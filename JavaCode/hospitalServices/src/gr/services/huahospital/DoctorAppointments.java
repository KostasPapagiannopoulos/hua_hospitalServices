package gr.services.huahospital;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DoctorAppointments {
	private int appointmentID;
	private String patientName;
	private String patientSurname;
	private int AMKA;
	private String diseaseDetails;
	private Date appointmentDate;
	private String strAppointmentDate;
	private Time appointmentTime;
	private String strAppointmentTime;		
	private int appointmentEmergency;
	public int appointmentState;
	private int clinicId;
	private String clinicName;
	private String clinicType;
	
	public int getAppointmentID() {
		return appointmentID;
	}
	public void setAppointmentID(int appointmentID) {
		this.appointmentID = appointmentID;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getPatientSurname() {
		return patientSurname;
	}
	public void setPatientSurname(String patientSurname) {
		this.patientSurname = patientSurname;
	}
	public int getAMKA() {
		return AMKA;
	}
	public void setAMKA(int aMKA) {
		AMKA = aMKA;
	}
	public String getDiseaseDetails() {
		return diseaseDetails;
	}
	public void setDiseaseDetails(String diseaseDetails) {
		this.diseaseDetails = diseaseDetails;
	}
	public Date getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
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
	public Time getAppointmentTime() {
		return appointmentTime;
	}
	public void setAppointmentTime(Time appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
	
	public int getAppointmentEmergency() {
		return appointmentEmergency;
	}
	public void setAppointmentEmergency(int appointmentEmergency) {
		this.appointmentEmergency = appointmentEmergency;
	}
	public int getClinicId() {
		return clinicId;
	}
	public void setClinicId(int clinicId) {
		this.clinicId = clinicId;
	}
	public String getClinicName() {
		return clinicName;
	}
	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}
	public String getClinicType() {
		return clinicType;
	}
	public void setClinicType(String clinicType) {
		this.clinicType = clinicType;
	}
	public int getAppointmentState() {
		return appointmentState;
	}
	public void setAppointmentState(int appointmentState) {
		this.appointmentState = appointmentState;
	}
}
