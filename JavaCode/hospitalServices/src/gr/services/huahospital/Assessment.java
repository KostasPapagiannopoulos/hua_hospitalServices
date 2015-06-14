package gr.services.huahospital;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Assessment {
	int assessmentId;
	int appointmentId;
	int doctorId;
	String problem;
	String subjective;
	String objective;
	String assessment;
	String plan;
	private Date appointmentDate;
	private String strAppointmentDate;
	private Time appointmentTime;
	private String strAppointmentTime;	
	
	
	public int getAssessmentId() {
		return assessmentId;
	}
	public void setAssessmentId(int assessmentId) {
		this.assessmentId = assessmentId;
	}
	public int getAppointmentId() {
		return appointmentId;
	}
	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}
	public int getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
	public String getProblem() {
		return problem;
	}
	public void setProblem(String problem) {
		this.problem = problem;
	}
	public String getSubjective() {
		return subjective;
	}
	public void setSubjective(String subjective) {
		this.subjective = subjective;
	}
	public String getObjective() {
		return objective;
	}
	public void setObjective(String objective) {
		this.objective = objective;
	}
	public String getAssessment() {
		return assessment;
	}
	public void setAssessment(String assessment) {
		this.assessment = assessment;
	}
	public String getPlan() {
		return plan;
	}
	public void setPlan(String plan) {
		this.plan = plan;
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
