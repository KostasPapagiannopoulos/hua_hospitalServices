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
					+ "FROM itp14105.doctors "
					+ "JOIN itp14105.clinicDoctor ON clinicDoctor.doctorid = doctors.doctorid "
					+ "JOIN itp14105.clinics   ON clinicDoctor.clinicid = clinics.clinicid "
					+ "WHERE doctors.doctorid = ? " + ";";
			preparedStmnt = db.getConn().prepareStatement(SQLStr);
			preparedStmnt.setString(1, String.valueOf(doctorid));

			ResultSet rs = db.Query(preparedStmnt);
			while (rs.next()) {
				ClinicDuty temp = new ClinicDuty();
				temp.setClinicid(rs.getInt("clinicid"));
				temp.setClinicName(rs.getString("clinicName"));
				temp.setClinicType(rs.getString("clinicType"));
				temp.setDateFrom(rs.getDate("dateFrom"));
				temp.setDateFrom(rs.getDate("dateTo"));
				temp.setDoctorid(rs.getInt("doctorid"));
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

			String SQLStr = "SELECT * FROM `itp14105`.`doctors`;";
			preparedStmnt = db.getConn().prepareStatement(SQLStr);
			ResultSet rs = db.Query(preparedStmnt);
			while (rs.next()) {
				Doctor temp = new Doctor();
				temp.setDoctorid(rs.getInt("doctorid"));
				temp.setName(rs.getString("name"));
				temp.setSurname(rs.getString("surname"));
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

			String SQLStr = "SELECT * FROM appointment "
					+ "JOIN `clinicDoctor` ON clinicDoctor.`clinicid` = appointment.clinicid AND appointment.appointmentDate > clinicDoctor.`dateFrom` AND appointment.appointmentDate < clinicDoctor.`dateTo` "
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

}
