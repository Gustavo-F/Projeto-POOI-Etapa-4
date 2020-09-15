package Kindle.ihc;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertaFX {

	public static void info(String mensagem) {
		Alert alerta = new Alert(AlertType.INFORMATION);
		alerta.setTitle("Informação");
		alerta.setHeaderText(null);
		alerta.setContentText(mensagem);
		alerta.showAndWait();
	}
	
	public static void alerta(String mensagem) {
		Alert alerta = new Alert(AlertType.WARNING);
		alerta.setTitle("Aviso");
		alerta.setHeaderText(null);
		alerta.setContentText(mensagem);
		alerta.showAndWait();
	}
	
	public static void erro(String mensagem) {
		Alert alerta = new Alert(AlertType.ERROR);
		alerta.setTitle("Erro!");
		alerta.setHeaderText(null);
		alerta.setContentText(mensagem);
		alerta.showAndWait();
	}

}
