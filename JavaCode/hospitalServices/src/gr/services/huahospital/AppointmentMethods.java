package gr.services.huahospital;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class AppointmentMethods extends BaseWebMethods {

	@WebMethod
	public Boolean insertAppointment(Appointment appointment) {
		try {

			String SQLStr = "INSERT INTO `itp14105`.`appointment` "
					+ "(patientName, patientSurname, AMKA, insuranceFund, clinicid, diseaseDetails,"
					+ " appointmentDate, appointmentTime, appointmentEmergency, appointmentState)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement preparedStmnt = db.getConn().prepareStatement(
					SQLStr);

			preparedStmnt.setString(1, appointment.getPatientName());
			preparedStmnt.setString(2, appointment.getPatientSurname());
			preparedStmnt.setInt(3, appointment.getAMKA());
			preparedStmnt.setString(4, appointment.getInsuranceFund());
			preparedStmnt.setInt(5, appointment.getClinicid());
			preparedStmnt.setString(6, appointment.getDiseaseDetails());
			preparedStmnt.setDate(7, new java.sql.Date(appointment
					.getAppointmentDate().getTime()));
			preparedStmnt.setTime(8, appointment.getAppointmentTime());
			preparedStmnt.setInt(9, appointment.getAppointmentEmergency());
			preparedStmnt.setInt(10, 1);
			// preparedStmnt.setString(11, appointment.getRejectReasons());

			db.Update(preparedStmnt); // commit

			return true;
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
		return false;
	}

	@WebMethod
	public String HeadUpdatesAppointment(Appointment appointment) {
		try {

			String SQLStr = "UPDATE `itp14105`.`appointment` "
					+ " SET clinicid=?, appointmentDate=?, appointmentTime=?, appointmentState=? , rejectReasons = ? , appointmentEmergency = ? "
					+ " WHERE appointmentID = ?;";
			PreparedStatement preparedStmnt = db.getConn().prepareStatement(
					SQLStr);

			preparedStmnt.setInt(1, appointment.getClinicid());
			preparedStmnt.setDate(2, appointment.getAppointmentDate());
			preparedStmnt.setTime(3, appointment.getAppointmentTime());
			preparedStmnt.setInt(4, 10);
			preparedStmnt.setString(5, appointment.getRejectReasons());
			preparedStmnt.setInt(6, appointment.getAppointmentEmergency());
			
			preparedStmnt.setInt(7, appointment.getAppointmentID());
			

			db.Update(preparedStmnt); // commit
			return "Το ραντεβού σας ενημερώθηκε επιτυχώς!";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Σφάλμα στην ενημέρωση του ραντεβού!";

	}

	@WebMethod
	public boolean staffUpdatesAppointment(Appointment appointment) {
		try {

			String SQLStr = "UPDATE `itp14105`.`appointment` "
					+ "SET clinicid=?, appointmentDate=?, appointmentTime=?, appointmentState=? "
					+ "WHERE appointmentID = ?;";
			PreparedStatement preparedStmnt = db.getConn().prepareStatement(
					SQLStr);

			preparedStmnt.setInt(1, appointment.getClinicid());
			preparedStmnt.setDate(2, appointment.getAppointmentDate());
			preparedStmnt.setTime(3, appointment.getAppointmentTime());
			preparedStmnt.setInt(4, appointment.getAppointmentEmergency() == 1 ? 10 : 2);
			preparedStmnt.setInt(5, appointment.getAppointmentID());
			

			db.Update(preparedStmnt); // commit
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;

	}

	@WebMethod
	public ArrayList<Appointment> returnAppointmentByAMKA(int AMKA) {

		PreparedStatement preparedStmnt = null;
		try {
			ArrayList<Appointment> arrList = new ArrayList<Appointment>();

			String SQLStr = "SELECT * FROM `itp14105`.`appointment` where AMKA = ?;";

			preparedStmnt = db.getConn().prepareStatement(SQLStr);

			preparedStmnt.setInt(1, AMKA);
			// Excecute the query
			ResultSet rs = db.Query(preparedStmnt);
			// we get only one

			while (rs.next()) {
				Appointment appointmentInstance = getAppointment(rs);

				arrList.add(appointmentInstance);
			}
			return arrList;

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (preparedStmnt != null) {
					preparedStmnt.close();
				}
			} catch (final SQLException sqlEx) {
				sqlEx.printStackTrace();
			}

		}
		// we didnt find anyone with this Staff id
		return null;

	}

	@WebMethod
	public Appointment returnAppointmentById(int appointmentID) {

		PreparedStatement preparedStmnt = null;
		try {
			ArrayList<Appointment> arrList = new ArrayList<Appointment>();

			String SQLStr = "SELECT * FROM `itp14105`.`appointment` where appointmentID = ?;";

			preparedStmnt = db.getConn().prepareStatement(SQLStr);

			preparedStmnt.setInt(1, appointmentID);
			// Excecute the query
			ResultSet rs = db.Query(preparedStmnt);
			// we get only one

			if (rs.next()) {
				return getAppointment(rs);

			}
			

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (preparedStmnt != null) {
					preparedStmnt.close();
				}
			} catch (final SQLException sqlEx) {
				sqlEx.printStackTrace();
			}

		}
		// we didnt find anyone with this Staff id
		return null;

	}

	@WebMethod
	public ArrayList<Appointment> returnAllAppointments() {
		try {
			ArrayList<Appointment> arrList = new ArrayList<Appointment>();

			String SQLStr = "SELECT * FROM `itp14105`.`appointment`;";
			ResultSet rs = db.Query(SQLStr);
			while (rs.next()) {
				Appointment appointmentInstance = getAppointment(rs);

				arrList.add(appointmentInstance);
			}
			return arrList;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Appointment>();
		}
	}
	
	@WebMethod
	public ArrayList<Appointment> returnAllAppointments_Director() {
		try {
			ArrayList<Appointment> arrList = new ArrayList<Appointment>();

			String SQLStr = "SELECT * FROM `itp14105`.`appointment`"
					+ "where appointmentEmergency = 2;";
			
			ResultSet rs = db.Query(SQLStr);
			while (rs.next()) {
				Appointment appointmentInstance = getAppointment(rs);

				arrList.add(appointmentInstance);
			}
			return arrList;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Appointment>();
		}
	}

	private Appointment getAppointment(ResultSet rs) throws SQLException {
		Appointment appointmentInstance = new Appointment();
		appointmentInstance.setAppointmentID(rs.getInt("appointmentID"));
		appointmentInstance.setPatientName(rs.getString("patientName"));
		appointmentInstance.setPatientSurname(rs.getString("patientSurname"));
		appointmentInstance.setAMKA(rs.getInt("AMKA"));
		appointmentInstance.setInsuranceFund(rs.getString("insuranceFund"));
		appointmentInstance.setClinicid(rs.getInt("clinicid"));
		appointmentInstance.setDiseaseDetails(rs.getString("diseaseDetails"));
		appointmentInstance.setAppointmentDate(rs.getDate("appointmentDate"));
		appointmentInstance.setAppointmentTime(rs.getTime("appointmentTime"));
		appointmentInstance.setAppointmentEmergency(rs
				.getInt("appointmentEmergency"));
		appointmentInstance.setRejectReasons(rs.getString("rejectReasons"));
		appointmentInstance.setAppointmentState(rs.getInt("appointmentState"));
		return appointmentInstance;
	}
}
