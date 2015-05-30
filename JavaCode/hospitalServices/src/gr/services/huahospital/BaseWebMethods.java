package gr.services.huahospital;

import javax.jws.WebService;


@WebService
public class BaseWebMethods {
	Database db = new Database();

	public Database getDb() {
		return db;
	}

}
