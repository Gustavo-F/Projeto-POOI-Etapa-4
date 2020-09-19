package Kindle.ihc;

import Kindle.entidades.Usuario;
import Kindle.db.GeneroDAO;
import Kindle.entidades.Genero;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class CadastraGeneroFX extends Application{

	private Stage stage;
	private Pane pane;
	private Label lblGenero;
	private TextField txtGenero;
	private Button btnCadastrar;
	private Button btnSair;
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		initComponentes();
		configLayout();
		
		Scene scene = new Scene(pane);
		btnCadastrar.requestFocus();
		
		stage.setScene(scene);
		stage.setTitle("Cadastrar genero");
		stage.setResizable(false);
		stage.show();
	}
	
	private void initComponentes() {
		pane = new AnchorPane();
		
		lblGenero = new Label("Gênero:");
		
		txtGenero = new TextField();
		txtGenero.setPromptText("Gênero");
		
		btnCadastrar = new Button("Cadastrar");
		btnCadastrar.setOnAction(cadastrar());
		
		btnSair = new Button("Sair");
		btnSair.setOnAction(sair());
		
		pane.getChildren().addAll(lblGenero, txtGenero, btnCadastrar, btnSair);
	}

	private void configLayout() {
		pane.setPrefSize(320, 110);

		// GENERO
		lblGenero.setLayoutX(20);
		lblGenero.setLayoutY(10);

		txtGenero.setLayoutX(20);
		txtGenero.setLayoutY(30);
		txtGenero.setPrefHeight(30);
		txtGenero.setPrefWidth(pane.getPrefWidth() - 40);
		
		// CADASTRAR
		btnCadastrar.setLayoutX(20);
		btnCadastrar.setLayoutY(txtGenero.getPrefHeight() + 40);
		btnCadastrar.setPrefHeight(30);
		btnCadastrar.setPrefWidth((pane.getPrefWidth() / 2) - 30);

		// SAIR
		btnSair.setLayoutX(btnCadastrar.getPrefWidth() + 40);
		btnSair.setLayoutY(txtGenero.getPrefHeight() + 40);
		btnSair.setPrefHeight(30);
		btnSair.setPrefWidth((pane.getPrefWidth() / 2) - 30);
		
	}
	
	private EventHandler<ActionEvent> cadastrar(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					
					if(txtGenero.getText().isBlank()) {
						AlertaFX.alerta("Gênero em branco!");
						return;
					}
					
					Genero novoGenero = new Genero(txtGenero.getText());
					new GeneroDAO().adiciona(novoGenero);
					
					AlertaFX.info("Gênero cadastrado com sucesso!");
					stage.close();
					GeneroFX.atualizaListaGeneros();
					
				}catch(Exception e) {
					AlertaFX.alerta("Erro ao cadastrar gênero!");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> sair(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					stage.close();
				}catch(Exception e) {
					AlertaFX.alerta("Erro ao iniciar tela de gêneros!");
				}
			}
		};
	}
	
}























