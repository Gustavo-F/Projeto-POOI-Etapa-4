package Kindle;

import Kindle.db.UtilDB;
import Kindle.ihc.LoginFX;
import javafx.application.Application;

public class Main {

	public static void main(String[] args) {
		
		UtilDB.iniDB();
		
		Application.launch(LoginFX.class);
		
	}

}
