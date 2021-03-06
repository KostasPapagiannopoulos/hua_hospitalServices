package gr.services.huahospital;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class ClinicMethods extends BaseWebMethods {

	@WebMethod
	public ArrayList<Clinic> returnAllClinics() {

		PreparedStatement preparedStmnt = null;
		try {
			ArrayList<Clinic> arrList = new ArrayList<Clinic>();

			String SQLStr = "SELECT * FROM `itp14105`.`clinics`;";
			preparedStmnt = db.getConn().prepareStatement(SQLStr);
			ResultSet rs = db.Query(preparedStmnt);
			while (rs.next()) {
				Clinic temp = new Clinic();
				temp.setClinicid(rs.getInt("clinicid"));
				temp.setClinicName(rs.getString("clinicName"));
				temp.setClinicType(rs.getString("clinicType"));
				arrList.add(temp);
			}
			return arrList;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Clinic>();
		} finally {
			try {
				if (preparedStmnt != null) {
					preparedStmnt.close();
				}
			} catch (final SQLException sqlEx) {
				sqlEx.printStackTrace();
			}
		}
	}

	@WebMethod
	public ArrayList<ClinicDuty> returnAllClinicDuty(int doctorid) {

		PreparedStatement preparedStmnt = null;
		try {
			ArrayList<ClinicDuty> arrList = new ArrayList<ClinicDuty>();

			String SQLStr = ""
					+ "SELECT * "
					+ "FROM itp14105.clinicDoctor "
					+ "JOIN itp14105.hospitalstaff ON clinicDoctor.doctorid = hospitalstaff.emp_no "
					+ "JOIN itp14105.clinics   ON clinicDoctor.clinicid = clinics.clinicid "
					+ "WHERE itp14105.hospitalstaff.emp_no = ? " + ";";
			preparedStmnt = db.getConn().prepareStatement(SQLStr);
			preparedStmnt.setString(1, String.valueOf(doctorid));

			ResultSet rs = db.Query(preparedStmnt);
			while (rs.next()) {
				ClinicDuty temp = new ClinicDuty();
				temp.setClinicid(rs.getInt("clinicid"));
				temp.setClinicName(rs.getString("clinicName"));
				temp.setClinicType(rs.getString("clinicType"));
				temp.setDateFrom(rs.getDate("dateFrom"));
				temp.setDateTo(rs.getDate("dateTo"));
				temp.setDoctorid(rs.getInt("emp_no"));
				arrList.add(temp);
			}
			return arrList;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<ClinicDuty>();
		} finally {
			try {
				if (preparedStmnt != null) {
					preparedStmnt.close();
				}
			} catch (final SQLException sqlEx) {
				sqlEx.printStackTrace();
			}
		}
	}

	@WebMethod
	public ArrayList<Doctor> returnAllDoctors() {

		PreparedStatement preparedStmnt = null;
		try {
			ArrayList<Doctor> arrList = new ArrayList<Doctor>();

			String SQLStr = "SELECT * FROM `itp14105`.`hospitalstaff` WHERE staffType = 3;";
			preparedStmnt = db.getConn().prepareStatement(SQLStr);
			ResultSet rs = db.Query(preparedStmnt);
			while (rs.next()) {
				Doctor temp = new Doctor();
				temp.setDoctorid(rs.getInt("emp_no"));
				temp.setName(rs.getString("firstName"));
				temp.setSurname(rs.getString("lastName"));
				temp.setSpecialty(rs.getString("specialty"));
				arrList.add(temp);
			}
			return arrList;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Doctor>();
		} finally {
			try {
				if (preparedStmnt != null) {
					preparedStmnt.close();
				}
			} catch (final SQLException sqlEx) {
				sqlEx.printStackTrace();
			}
		}
	}

	@WebMethod
	public ArrayList<DoctorAppointments> returnDoctorAppointments(int doctorid) {

		PreparedStatement preparedStmnt = null;
		try {
			ArrayList<DoctorAppointments> arrList = new ArrayList<DoctorAppointments>();

			String SQLStr = "SELECT * "
					+ " FROM appointment "
					+ "JOIN `clinicDoctor` ON clinicDoctor.`clinicid` = appointment.clinicid AND appointment.appointmentDate >= clinicDoctor.`dateFrom` AND appointment.appointmentDate <= clinicDoctor.`dateTo` "
					+ "JOIN clinics ON clinicDoctor.`clinicid` = clinics.clinicid "
					+ "WHERE doctorid = ?";
			preparedStmnt = db.getConn().prepareStatement(SQLStr);
			preparedStmnt.setInt(1, doctorid);

			ResultSet rs = db.Query(preparedStmnt);
			while (rs.next()) {
				DoctorAppointments temp = new DoctorAppointments();

				temp.setAppointmentID(rs.getInt("appointmentID"));
				temp.setPatientName(rs.getString("patientName"));
				temp.setPatientSurname(rs.getString("patientSurname"));
				temp.setAMKA(rs.getInt("AMKA"));
				temp.setDiseaseDetails(rs.getString("diseaseDetails"));
				temp.setAppointmentDate(rs.getDate("appointmentDate"));
				temp.setAppointmentTime(rs.getTime("appointmentTime"));
				temp.setAppointmentEmergency(rs.getInt("appointmentEmergency"));
				temp.setClinicId(rs.getInt("clinicId"));
				temp.setClinicName(rs.getString("clinicName"));
				temp.setClinicType(rs.getString("clinicType"));
				temp.setAppointmentState(rs.getInt("appointmentState"));
				
				arrList.add(temp);
			}
			return arrList;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<DoctorAppointments>();
		} finally {
			try {
				if (preparedStmnt != null) {
					preparedStmnt.close();
				}
			} catch (final SQLException sqlEx) {
				sqlEx.printStackTrace();
			}
		}
	}

	@WebMethod
	public boolean doctorInsertAppointment(Assessment assessment) {
		try {

			String SQLStr = "INSERT INTO `itp14105`.`doctorAssessment`"
					+ "(appointmentId, doctorId, problem, subjective, objective, assessment, plan)"
					+ "VALUES ( ?, ?, ?, ?, ?, ?, ? )  "
					;
			PreparedStatement preparedStmnt = db.getConn().prepareStatement(
					SQLStr);

			
			preparedStmnt.setInt(1, assessment.getAppointmentId());
			preparedStmnt.setInt(2, assessment.getDoctorId());
			preparedStmnt.setString(3, assessment.getProblem());
			preparedStmnt.setString(4, assessment.getSubjective());
			preparedStmnt.setString(5, assessment.getObjective());
			preparedStmnt.setString(6, assessment.getAssessment());
			preparedStmnt.setString(7, assessment.getPlan());
			

			db.Update(preparedStmnt); // commit
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;

	}
	
	@WebMethod
	public ArrayList<Assessment> getAssessmentsByPatientAMKA(int patientAMKA) {
		try {

			String SQLStr = "SELECT "
					+ "doctorAssessment.*, appointment .appointmentDate, appointment .appointmentTime "
					+ " FROM `doctorAssessment`"
					+ " LEFT JOIN appointment ON appointment.`appointmentId` = doctorAssessment.appointmentId"
					+ " WHERE AMKA = ?  "
					;
			PreparedStatement preparedStmnt = db.getConn().prepareStatement(
					SQLStr);
			
			preparedStmnt.setInt(1, patientAMKA);

			ArrayList<Assessment> arrList = new ArrayList<Assessment> ();
			ResultSet rs = db.Query(preparedStmnt);
			while (rs.next()) {
				Assessment temp = new Assessment();

				
				temp.setAssessmentId(rs.getInt("assessmentId"));
				temp.setAppointmentId(rs.getInt("appointmentId"));
				temp.setDoctorId(rs.getInt("doctorId"));
				temp.setProblem(rs.getString("problem"));
				temp.setSubjective(rs.getString("subjective"));
				temp.setObjective(rs.getString("objective"));
				temp.setAssessment(rs.getString("assessment"));
				temp.setPlan(rs.getString("plan"));
				temp.setAppointmentDate(rs.getDate("appointmentDate"));				
				temp.setAppointmentTime(rs.getTime("appointmentTime"));

				arrList.add(temp);
			}
			return arrList;
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
