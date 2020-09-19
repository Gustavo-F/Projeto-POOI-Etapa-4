package Kindle.ihc;

import Kindle.entidades.Usuario;
import Kindle.db.UsuarioDAO;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class LoginFX extends Application{
	
	private Stage stage;
	private Label lblKindle;
	private TextField txtUsuario;
	private PasswordField txtSenha;
	private Button btnEntrar;
	private Button btnSair;
	private Button btnCadastrar;
	private Pane pane;

	@Override
	public void start(Stage stage) throws Exception {
		
		this.stage = stage;
		
		initComponentes();
		configLayout();
		
		Scene scene = new Scene(this.pane);
		btnEntrar.requestFocus();
		
		stage.setScene(scene);
		stage.setTitle("Kindle login");
		stage.setResizable(false);
		stage.show();
		
	}
	
	private void initComponentes() {
		// rótulo
		lblKindle = new Label("Bem-vindo ao Kindle");
		
		// entrada de textos
		txtUsuario = new TextField();
		txtUsuario.setPromptText("Nome de usuário");
		
		// entrada de senha
		txtSenha = new PasswordField();
		txtSenha.setPromptText("Digite sua senha");
		
		// botões
		btnEntrar = new Button("Entrar");
		btnEntrar.setOnAction(entrar());
		
		btnSair = new Button("Sair");
		btnSair.setOnAction(sair());
		
		btnCadastrar = new Button("Cadastrar usuário");
		btnCadastrar.setOnAction(cadastrar());
		
		// painel
		pane = new AnchorPane();
		
		// adiciona componentes ao painel
		pane.getChildren().add(lblKindle);	
		pane.getChildren().addAll(txtUsuario, txtSenha, btnEntrar, btnSair, btnCadastrar);
	}
	
	private void configLayout() {
		pane.setPrefSize(320, 180);
		
		// Configura componentes no 
		lblKindle.setLayoutX(10);
		lblKindle.setLayoutY(10);
		
		txtUsuario.setLayoutX(10);
		txtUsuario.setLayoutY(35);
		txtUsuario.setPrefHeight(30);
		txtUsuario.setPrefWidth(pane.getPrefWidth() - 20);
		
		txtSenha.setLayoutX(10);
		txtSenha.setLayoutY(75);
		txtSenha.setPrefHeight(30);
		txtSenha.setPrefWidth(pane.getPrefWidth() - 20);
		
		btnEntrar.setLayoutX(10);
		btnEntrar.setLayoutY(115);
		btnEntrar.setPrefHeight(20);
		btnEntrar.setPrefWidth((pane.getPrefWidth() - 30) / 2);
		
		btnSair.setLayoutX(btnEntrar.getPrefWidth() + 20);
		btnSair.setLayoutY(115);
		btnSair.setPrefHeight(20);
		btnSair.setPrefWidth((pane.getPrefWidth() - 30) / 2);
		
		btnCadastrar.setLayoutX(10);
		btnCadastrar.setLayoutY(145);
		btnCadastrar.setPrefHeight(20);
		btnCadastrar.setPrefWidth(pane.getPrefWidth() - 20);
		
	}
	
	private EventHandler<ActionEvent> entrar(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					
					if(txtUsuario.getText().isBlank()) {
						AlertaFX.alerta("Usuário em branco!");
						return;
					}
					
					if(txtSenha.getText().isBlank()) {
						AlertaFX.alerta("Senha em branco!");
						return;
					}
					
					Usuario usuario = new UsuarioDAO().buscaUsuario(txtUsuario.getText());
					
					if(usuario == null) {
						AlertaFX.alerta("Usuário inexistente!");
						return;
					}
					
					if(!usuario.getSenha().contentEquals(txtSenha.getText())) {
						AlertaFX.alerta("Senha incorreta!");
						return;
					}
					
					if(usuario.getPermissao().equals("U")) {
						new ClienteFX(usuario).start(stage);
					}else if(usuario.getPermissao().equals("A")) {
						new MainAdmFX(usuario).start(stage);;
					}

				}catch(Exception e) {
					AlertaFX.alerta("Erro ao iniciar tela principal!");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> sair(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
					Platform.exit();
			}
		};
	}
	
	private EventHandler<ActionEvent> cadastrar(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					new CadastrarUsuarioFX().start(stage);;
				}catch(Exception e) {
					System.err.println("Erro ao iniciar tela de cadastro!");
				}
			}
		};
	}
	
}

























