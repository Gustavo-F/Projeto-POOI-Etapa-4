package Kindle.ihc;

import Kindle.db.UsuarioDAO;
import Kindle.entidades.Usuario;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class CadastrarUsuarioFX extends Application{

	private Stage stage;
	private Pane pane;
	private Button btnCadastrar;
	private Button btnCancelar;
	private Label lblNome;
	private Label lblSobrenome;
	private Label lblCpf;
	private Label lblEmail;
	private Label lblLogin;
	private Label lblSenha;
	private Label lblTelefone;
	private Label lblADM;
	private TextField txtNome;
	private TextField txtSobrenome;
	private TextField txtCpf;
	private TextField txtEmail;
	private TextField txtLogin;
	private PasswordField txtSenha;
	private TextField txtTelefone;
	private CheckBox checkADM;
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		initComponentes();
		configLayout();
		
		Scene scene = new Scene(pane);
		btnCadastrar.requestFocus();
		
		stage.setScene(scene);
		stage.setTitle("Cadastrar Usuário");
		stage.setResizable(false);
		stage.show();
		
	}
	
	private void initComponentes() {
		
		pane = new AnchorPane();
		
		// botões 
		btnCadastrar = new Button("Cadastrar");
		btnCadastrar.setOnAction(cadastrar());
		
		btnCancelar = new Button("Cancelar");
		btnCancelar.setOnAction(cancelar());
		
		pane.getChildren().addAll(btnCadastrar, btnCancelar);
		
		// rótulos
		lblNome = new Label("Nome:");
		lblSobrenome = new Label("Sobrenome:");
		lblCpf = new Label("CPF: (Apenas valores numéricos)");
		lblEmail = new Label("Email:");
		lblLogin = new Label("Nome de usuário:");
		lblSenha = new Label("Senha:");
		lblTelefone = new Label("Telefone:");
		lblADM = new Label("Conta administrativa: ");
		
		pane.getChildren().addAll(lblNome, lblSobrenome, lblCpf, lblEmail, lblLogin, lblSenha, lblTelefone, lblADM);
		
		// caixas de texto
		txtNome = new TextField();
		txtNome.setPromptText("Nome");
		
		txtSobrenome = new TextField();
		txtSobrenome.setPromptText("Sobrenome");
		
		txtCpf = new TextField();
		txtCpf.setPromptText("Ex: 12345678910");
		
		txtEmail = new TextField();
		txtEmail.setPromptText("Email");
		
		txtLogin = new TextField();
		txtLogin.setPromptText("Nome de usuário");
		
		txtSenha = new PasswordField();
		txtSenha.setPromptText("Senha");
		
		txtTelefone = new TextField();
		txtTelefone.setPromptText("Telefone");
		
		pane.getChildren().addAll(txtNome, txtSobrenome, txtCpf, txtEmail, txtLogin, txtSenha, txtTelefone);
		
		// check box
		checkADM = new CheckBox();
		
		pane.getChildren().add(checkADM);
		
	}
	
	private void configLayout() {
		pane.setPrefSize(400, 500);
		
		// NOME
		lblNome.setLayoutX(20);
		lblNome.setLayoutY(10);
		
		txtNome.setLayoutX(20);
		txtNome.setLayoutY(30);
		txtNome.setPrefHeight(30);
		txtNome.setPrefWidth(pane.getPrefWidth() - 40);
		
		// SOBRENOME
		lblSobrenome.setLayoutX(20);
		lblSobrenome.setLayoutY(70);
		
		txtSobrenome.setLayoutX(20);
		txtSobrenome.setLayoutY(90);
		txtSobrenome.setPrefHeight(30);
		txtSobrenome.setPrefWidth(pane.getPrefWidth() - 40);
		
		// CPF
		lblCpf.setLayoutX(20);
		lblCpf.setLayoutY(130);
		
		txtCpf.setLayoutX(20);
		txtCpf.setLayoutY(150);
		txtCpf.setPrefHeight(30);
		txtCpf.setPrefWidth(pane.getPrefWidth() - 40);
		
		// EMAIL
		lblEmail.setLayoutX(20);
		lblEmail.setLayoutY(190);
		
		txtEmail.setLayoutX(20);
		txtEmail.setLayoutY(210);
		txtEmail.setPrefHeight(30);
		txtEmail.setPrefWidth(pane.getPrefWidth() - 40);
		
		// LOGIN
		lblLogin.setLayoutX(20);
		lblLogin.setLayoutY(250);
		
		txtLogin.setLayoutX(20);
		txtLogin.setLayoutY(270);
		txtLogin.setPrefHeight(30);
		txtLogin.setPrefWidth(pane.getPrefWidth() - 40);
		
		// SENHA
		lblSenha.setLayoutX(20);
		lblSenha.setLayoutY(310);
		
		txtSenha.setLayoutX(20);
		txtSenha.setLayoutY(330);
		txtSenha.setPrefHeight(30);
		txtSenha.setPrefWidth(pane.getPrefWidth() - 40);
		
		// TELEFONE
		lblTelefone.setLayoutX(20);
		lblTelefone.setLayoutY(370);
		
		txtTelefone.setLayoutX(20);
		txtTelefone.setLayoutY(390);
		txtTelefone.setPrefHeight(30);
		txtTelefone.setPrefWidth(pane.getPrefWidth() - 40);
		
		// CHECKADM
		
		lblADM.setLayoutX(20);
		lblADM.setLayoutY(430);
		
		checkADM.setLayoutX(140);
		checkADM.setLayoutY(430);
		
		// CADASTRAR
		btnCadastrar.setLayoutX(10);
		btnCadastrar.setLayoutY(pane.getPrefHeight() - 40);
		btnCadastrar.setPrefHeight(30);
		btnCadastrar.setPrefWidth((pane.getPrefWidth() / 2) - 20);
		
		// CANCELAR
		btnCancelar.setLayoutX(btnCadastrar.getPrefWidth() + 20);
		btnCancelar.setLayoutY(pane.getPrefHeight() - 40);
		btnCancelar.setPrefHeight(30);
		btnCancelar.setPrefWidth((pane.getPrefWidth() / 2) - 10);
		
	}
	
	private EventHandler<ActionEvent> cadastrar(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					
					if(txtNome.getText().isBlank()) {
						AlertaFX.alerta("Nome em branco.");
						return;
					}
					
					if(txtSobrenome.getText().isBlank()) {
						AlertaFX.alerta("Sobrenome em branco.");
						return;
					}
					
					if(txtCpf.getText().isBlank()) {
						AlertaFX.alerta("CPF em branco.");
						return;
					}
					
					if(txtEmail.getText().isBlank()) {
						AlertaFX.alerta("Email em branco.");
						return;
					}
					
					if(txtSenha.getText().isBlank()){
						AlertaFX.alerta("Senha em branco.");
						return;
					}
					
					if(txtTelefone.getText().isBlank()) {
						AlertaFX.alerta("Telefone em branco.");
						return;
					}
					
					Usuario novoUsuario = new Usuario();
					novoUsuario.setNome(txtNome.getText());
					novoUsuario.setSobrenome(txtSobrenome.getText());
					novoUsuario.setCPF(txtCpf.getText());
					novoUsuario.setEmail(txtEmail.getText());
					novoUsuario.setTelefone(txtTelefone.getText());
					novoUsuario.setLogin(txtLogin.getText());
					novoUsuario.setSenha(txtSenha.getText());
					
					if(checkADM.isSelected()) {
						novoUsuario.setPermissao("A");
					}else if(!checkADM.isSelected()) {
						novoUsuario.setPermissao("U");
					}
					
					new UsuarioDAO().adiciona(novoUsuario);
					
					AlertaFX.info("Usuário Cadastrado com sucesso!");
					new LoginFX().start(stage);
					
				}catch(Exception e) {
					AlertaFX.erro("Erro ao cadastrar usuário.");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> cancelar(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					new LoginFX().start(stage);
				}catch(Exception e) {
					AlertaFX.erro("Erro ao cancelar cadastro.");
				}
			}
		};
	}
}



















