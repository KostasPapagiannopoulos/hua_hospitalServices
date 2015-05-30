package gr.services.huahospital;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class PatientMethods extends BaseWebMethods {

	@WebMethod
	public String insertPatient(Patient patient) {
		try {

			String SQLStr = "INSERT INTO `itp14105`.`patient` "
					+ "VALUES (?, ?, ?, ?,  ?, ?, ?, ?, ?, ?);";
			PreparedStatement preparedStmnt = db.getConn().prepareStatement(
					SQLStr);

			preparedStmnt.setInt(1, patient.getPatientID());
			preparedStmnt.setString(2, patient.getPatientName());
			preparedStmnt.setString(3, patient.getPatientSurname());
			preparedStmnt.setString(4,
					Character.toString(patient.getPatientGender()));
			preparedStmnt.setString(5, patient.getInsuranceFund());
			preparedStmnt.setInt(6, patient.getAMKA());
			preparedStmnt.setString(7, patient.getBloodType());
			preparedStmnt.setString(8, patient.getAddress());
			preparedStmnt.setString(9, patient.getCountry());
			preparedStmnt.setString(10, patient.getEmail());

			db.Update(preparedStmnt); // commit

			return "Η καταχώρηση σας πραγματοποιήθηκε με επιτυχία! Το Όνομα Χρήστη σας είναι "
					+ String.valueOf(patient.getPatientID());
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
		return "Error";
	}

	@WebMethod
	public ArrayList<Patient> returnAllPatients() {

		PreparedStatement preparedStmnt = null;
		try {
			ArrayList<Patient> arrList = new ArrayList<Patient>();

			String SQLStr = "SELECT * FROM `itp14105`.`patient`;";
			preparedStmnt = db.getConn().prepareStatement(SQLStr);
			ResultSet rs = db.Query(preparedStmnt);
			while (rs.next()) {
				Patient patientInstance = new Patient();
				patientInstance.setPatientID(rs.getInt("patientId"));
				patientInstance.setPatientName(rs.getString("patientName"));
				patientInstance.setPatientSurname(rs
						.getString("patientSurname"));
				patientInstance.setPatientGender(rs.getString("patientGender")
						.charAt(0));
				patientInstance.setInsuranceFund(rs.getString("insuranceFund"));
				patientInstance.setAMKA(rs.getInt("AMKA"));
				patientInstance.setBloodType(rs.getString("bloodType"));
				patientInstance.setAddress(rs.getString("address"));
				patientInstance.setCountry(rs.getString("country"));

				arrList.add(patientInstance);
			}
			return arrList;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<Patient>();
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
	public Patient returnPatientByAMKA(int AMKA) {
		PreparedStatement preparedStmnt = null;
		try {

			String SQLStr = "SELECT * FROM `itp14105`.`patient` where AMKA = ?;";

			preparedStmnt = db.getConn().prepareStatement(SQLStr);

			preparedStmnt.setInt(1, AMKA);
			// Excecute the query
			ResultSet rs = db.Query(preparedStmnt);
			// we get only one
			if (rs.next()) {
				Patient patientInstance = new Patient();
				patientInstance.setPatientID(rs.getInt("patientId"));
				patientInstance.setPatientName(rs.getString("patientName"));
				patientInstance.setPatientSurname(rs
						.getString("patientSurname"));
				patientInstance.setPatientGender(rs.getString("patientGender")
						.charAt(0));
				patientInstance.setInsuranceFund(rs.getString("insuranceFund"));
				patientInstance.setAMKA(rs.getInt("AMKA"));
				patientInstance.setBloodType(rs.getString("bloodType"));
				patientInstance.setAddress(rs.getString("address"));
				patientInstance.setCountry(rs.getString("country"));

				return patientInstance;
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

}
