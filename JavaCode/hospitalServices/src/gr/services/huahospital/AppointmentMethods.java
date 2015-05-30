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
	public String insertAppointment(Appointment appointment) {
		try {

			String SQLStr = "INSERT INTO `itp14105`.`appointment` "
					+ "VALUES (?, ?, ?, ?,  ?, ?, ?);";
			PreparedStatement preparedStmnt = db.getConn().prepareStatement(
					SQLStr);

			preparedStmnt.setInt(1, appointment.getAppointmentID());
			preparedStmnt.setString(2, appointment.getPatientName());
			preparedStmnt.setString(3, appointment.getPatientSurname());
			preparedStmnt.setInt(4, appointment.getAMKA());
			preparedStmnt.setString(5, appointment.getInsuranceFund());
			//preparedStmnt.setString(6, appointment.getInfirmary());
			preparedStmnt.setString(6, appointment.getDiseaseDetails());
			//preparedStmnt.setDate(8, new java.sql.Date(appointment.getAppointmentDate().getTime()));
			//preparedStmnt.setString(9, appointment.getAppointmentTime());
			preparedStmnt.setInt(7, appointment.getAppointmentEmergency());
			//preparedStmnt.setString(11, appointment.getRejectReasons());
			preparedStmnt.setInt(8, 1);


			db.Update(preparedStmnt); // commit

			return "Το ραντεβού σας καταχωρήθηκε επιτυχώς!";
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();

		} 
		catch (InstantiationException e) 
		{

			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Σφάλμα στη καταχώριση του ραντεβού!";
	}

	@WebMethod

	public String HeadUpdatesAppointment(Appointment appointment) 
	{
		try {

				String SQLStr = "UPDATE `itp14105`.`appointment` "
						+ "SET infirmary=?, appointmentDate=?, appointmentTime=?, appointmentState=?);";
				PreparedStatement preparedStmnt = db.getConn().prepareStatement(SQLStr);
				
				preparedStmnt.setString(1, appointment.getInfirmary());
				preparedStmnt.setDate(2, appointment.getAppointmentDate());
				preparedStmnt.setTime(3, appointment.getAppointmentTime());
				preparedStmnt.setInt(4, 2);
				
				db.Update(preparedStmnt); //commit
				return "Το ραντεβού σας ενημερώθηκε επιτυχώς!";
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			} 
			catch (InstantiationException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (IllegalAccessException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "Σφάλμα στην ενημέρωση του ραντεβού!";
		
	}
	
	@WebMethod
	public String staffUpdatesAppointment(Appointment appointment) 
	{
		try {

				String SQLStr = "UPDATE `itp14105`.`appointment` "
						+ "SET infirmary=?, appointmentDate=?, appointmentTime=?, appointmentState=?);";
				PreparedStatement preparedStmnt = db.getConn().prepareStatement(SQLStr);
				
				preparedStmnt.setString(1, appointment.getInfirmary());
				preparedStmnt.setDate(2, appointment.getAppointmentDate());
				preparedStmnt.setTime(3, appointment.getAppointmentTime());
				preparedStmnt.setInt(4, 2);
				
				db.Update(preparedStmnt); //commit
				return "Το ραντεβού σας ενημερώθηκε επιτυχώς!";
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			} 
			catch (InstantiationException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (IllegalAccessException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "Σφάλμα στην ενημέρωση του ραντεβού!";
		
	}

	@WebMethod
	public Appointment returnAppointmentByAMKA(int AMKA) 
	{

		PreparedStatement preparedStmnt = null;
		try 
		{
			String SQLStr = "SELECT * FROM `itp14105`.`appointment` where AMKA = ?;";

			preparedStmnt = db.getConn().prepareStatement(SQLStr);

			preparedStmnt.setInt(1, AMKA);
			// Excecute the query
			ResultSet rs = db.Query(preparedStmnt);
			// we get only one

			if (rs.next()) 
			{
				Appointment appointmentInstance = new Appointment();
				appointmentInstance.setAppointmentID(rs.getInt("appointmentID"));
				appointmentInstance.setPatientName(rs.getString("patientName"));
				appointmentInstance.setPatientSurname(rs.getString("patientSurname"));
				appointmentInstance.setAMKA(rs.getInt("AMKA"));
				appointmentInstance.setInsuranceFund(rs.getString("insuranceFund"));
				appointmentInstance.setInfirmary(rs.getString("infirmary"));
				appointmentInstance.setDiseaseDetails(rs.getString("diseaseDetails"));
				appointmentInstance.setAppointmentDate(rs.getDate("appointmentDate"));
				appointmentInstance.setAppointmentTime(rs.getTime("appointmentTime"));
				appointmentInstance.setAppointmentEmergency(rs.getInt("appointmentEmergency"));
				appointmentInstance.setRejectReasons(rs.getString("rejectReasons"));
				appointmentInstance.setAppointmentState(rs.getInt("appointmentState"));

				return appointmentInstance;
			}

		} 
		catch (Exception e) 
		{
			e.printStackTrace();

		} 
		 finally {
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
	public ArrayList<Appointment> returnAllAppointments() 
	{
		try {
				ArrayList<Appointment> arrList = new ArrayList<Appointment>();
	
				String SQLStr = "SELECT * FROM `itp14105`.`appointment`;";
				ResultSet rs = db.Query(SQLStr);
				while (rs.next()) 
				{
					Appointment appointmentInstance = new Appointment();
					appointmentInstance.setAppointmentID(rs.getInt("appointmentID"));
					appointmentInstance.setPatientName(rs.getString("patientName"));
					appointmentInstance.setPatientSurname(rs.getString("patientSurname"));
					appointmentInstance.setAMKA(rs.getInt("AMKA"));
					appointmentInstance.setInsuranceFund(rs.getString("insuranceFund"));
					appointmentInstance.setInfirmary(rs.getString("infirmary"));
					appointmentInstance.setDiseaseDetails(rs.getString("diseaseDetails"));
					appointmentInstance.setAppointmentDate(rs.getDate("appointmentDate"));
					appointmentInstance.setAppointmentTime(rs.getTime("appointmentTime"));
					appointmentInstance.setAppointmentEmergency(rs.getInt("appointmentEmergency"));
					appointmentInstance.setRejectReasons(rs.getString("rejectReasons"));
					appointmentInstance.setAppointmentState(rs.getInt("appointmentState"));
	
					arrList.add(appointmentInstance);
				}
				return arrList;
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
				return new ArrayList<Appointment>();
			}
	}
}
