package Kindle.ihc;

import Kindle.entidades.Escritor;
import Kindle.db.EscritorDAO;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class CadastrarEscritorFX extends Application{

	private Stage stage;
	private Pane pane;
	private Label lblNome;
	private Label lblSobrenome;
	private Label lblCpf;
	private Label lblTelefone;
	private Label lblEmail;
	private TextField txtNome;
	private TextField txtSobrenome;
	private TextField txtCpf;
	private TextField txtTelefone;
	private TextField txtEmail;
	private Button btnCadastrar;
	private Button btnSair;
	
	@Override
	public void start(Stage stage2) throws Exception {
		this.stage = new Stage();
		
		initComponentes();
		configLayout();
		
		Scene scene = new Scene(pane);
		btnCadastrar.requestFocus();
		
		stage.setScene(scene);
		stage.setTitle("Cadastrar Escritor");
		stage.setResizable(false);
		stage.show();
	}

	private void initComponentes() {
		pane = new AnchorPane();
		
		// rótulos
		lblNome = new Label("Nome:");
		lblSobrenome = new Label("Sobrenome:");
		lblCpf = new Label("CPF:");
		lblTelefone = new Label("Telefone:");
		lblEmail = new Label("Email");
		
		pane.getChildren().addAll(lblNome, lblSobrenome, lblCpf, lblTelefone, lblEmail);
		
		// caixas de texto
		txtNome = new TextField();
		txtNome.setPromptText("Nome");
		
		txtSobrenome = new TextField();
		txtSobrenome.setPromptText("Sobrenome");
		
		txtCpf = new TextField();
		txtCpf.setPromptText("CPF");
		
		txtTelefone = new TextField();
		txtTelefone.setPromptText("Telefone");
		
		txtEmail = new TextField();
		txtEmail.setPromptText("Email");
		
		pane.getChildren().addAll(txtNome, txtSobrenome, txtCpf, txtTelefone, txtEmail);
		
		// botões
		btnCadastrar = new Button("Cadastrar");
		btnCadastrar.setOnAction(cadastrar());
		
		btnSair = new Button("Sair");
		btnSair.setOnAction(sair());
		
		pane.getChildren().addAll(btnCadastrar, btnSair);
		
	}
	
	private void configLayout() {
		pane.setPrefSize(400, 360);
		
		// NOME
		lblNome.setLayoutX(20);
		lblNome.setLayoutY(10);
		lblNome.setPrefHeight(20);
		
		txtNome.setLayoutX(20);
		txtNome.setLayoutY(30);
		txtNome.setPrefHeight(30);
		txtNome.setPrefWidth(pane.getPrefWidth() - 40);
		
		// SOBRENOME
		lblSobrenome.setLayoutX(20);
		lblSobrenome.setLayoutY(lblNome.getPrefHeight() + lblNome.getLayoutY() + txtNome.getPrefHeight() + 10);
		lblSobrenome.setPrefHeight(20);
		
		txtSobrenome.setLayoutX(20);
		txtSobrenome.setLayoutY(lblSobrenome.getLayoutY() + lblSobrenome.getPrefHeight());
		txtSobrenome.setPrefHeight(30);
		txtSobrenome.setPrefWidth(pane.getPrefWidth() - 40);
		
		// CPF
		lblCpf.setLayoutX(20);
		lblCpf.setLayoutY(lblSobrenome.getPrefHeight() + lblSobrenome.getLayoutY() + txtSobrenome.getPrefHeight() + 10);
		lblCpf.setPrefHeight(20);
		
		txtCpf.setLayoutX(20);
		txtCpf.setLayoutY(lblCpf.getLayoutY() + lblCpf.getPrefHeight());
		txtCpf.setPrefHeight(30);
		txtCpf.setPrefWidth(pane.getPrefWidth() - 40);
		
		// TELEFONE
		lblTelefone.setLayoutX(20);
		lblTelefone.setLayoutY(lblCpf.getLayoutY() + lblCpf.getPrefHeight() + txtCpf.getPrefHeight() + 10);
		lblTelefone.setPrefHeight(20);
		
		txtTelefone.setLayoutX(20);
		txtTelefone.setLayoutY(lblTelefone.getLayoutY() + lblTelefone.getPrefHeight());
		txtTelefone.setPrefHeight(30);
		txtTelefone.setPrefWidth(pane.getPrefWidth() - 40 );
		
		// EMAIL
		lblEmail.setLayoutX(20);
		lblEmail.setLayoutY(lblTelefone.getLayoutY() + lblTelefone.getPrefHeight() + txtTelefone.getPrefHeight() + 10);
		lblEmail.setPrefHeight(20);
		
		txtEmail.setLayoutX(20);
		txtEmail.setLayoutY(lblEmail.getLayoutY() + lblEmail.getPrefHeight());
		txtEmail.setPrefHeight(30);
		txtEmail.setPrefWidth(pane.getPrefWidth() - 40);
		
		// CADASTRAR 
		btnCadastrar.setLayoutX(20);
		btnCadastrar.setLayoutY(pane.getPrefHeight() - 40);
		btnCadastrar.setPrefHeight(30);
		btnCadastrar.setPrefWidth((pane.getPrefWidth() / 2) - 30);
		
		// SAIR 
		btnSair.setLayoutX(pane.getPrefWidth() / 2);
		btnSair.setLayoutY(pane.getPrefHeight() - 40);
		btnSair.setPrefHeight(30);
		btnSair.setPrefWidth((pane.getPrefWidth() / 2) - 20);
		
	}
	
	private EventHandler<ActionEvent> cadastrar(){
		return new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try {
					
					if(txtNome.getText().isBlank()) {
						AlertaFX.alerta("Nome em branco!");
						return;
					}
					
					if(txtSobrenome.getText().isBlank()) {
						AlertaFX.alerta("Sobrenome em branco!");
						return;
					}
					
					if(txtCpf.getText().isBlank()) {
						AlertaFX.alerta("CPF em branco!");
						return;
					}
					
					if(txtCpf.getText().length() != 11) {
						AlertaFX.alerta("CPF deve conter 11 caracteres!");
						return;
					}
					
					if(txtTelefone.getText().isBlank()) {
						AlertaFX.alerta("Telefone em branco!");
						return;
					}
					
					if(txtEmail.getText().isBlank()) {
						AlertaFX.alerta("Email em branco!");
						return;
					}
					
					Escritor escritor = new Escritor();
					escritor.setNome(txtNome.getText());
					escritor.setSobrenome(txtSobrenome.getText());
					escritor.setCPF(txtCpf.getText());
					escritor.setTelefone(txtTelefone.getText());
					escritor.setEmail(txtEmail.getText());
					
					new EscritorDAO().adiciona(escritor);
					
					AlertaFX.info("Escritor cadastrado com sucesso!");
					stage.close();
					EscritorFX.atualizaTabela();
					
				}catch(Exception e) {
					AlertaFX.erro("Erro ao cadastrar escritor!");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> sair(){
		return new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				stage.close();
			}
		};
	}
	
}
























