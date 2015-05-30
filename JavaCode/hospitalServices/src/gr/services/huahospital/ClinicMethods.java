package gr.services.huahospital;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
					+ "WHERE doctors.doctorid = ? "
					+ ";"
					;
			preparedStmnt = db.getConn().prepareStatement(SQLStr);
			preparedStmnt.setString(1, String.valueOf( doctorid));
			
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
}
