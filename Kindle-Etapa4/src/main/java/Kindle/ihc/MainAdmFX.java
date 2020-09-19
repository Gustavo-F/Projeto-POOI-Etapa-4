package Kindle.ihc;

import Kindle.entidades.Usuario;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
public class MainAdmFX extends Application{

	private Usuario usuarioLogado;
	private Stage stage;
	private Pane pane;
	private Button btnSair;
	private Button btnEscritor;
	private Button btnLivro;
	private Button btnGenero;
	private Button btnEditora;
	
	public MainAdmFX(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		initComponentes();
		configLayout();
		
		Scene scene = new Scene(pane);
		btnLivro.requestFocus();
		
		stage.setScene(scene);
		stage.setTitle("Menu Inicial");
		stage.setResizable(false);
		stage.show();
	}
	
	private void initComponentes() {

		pane = new AnchorPane();
		
		// Botões 
		btnSair = new Button("Sair");
		btnSair.setOnAction(sair());
		
		btnLivro = new Button("Livros");
		btnLivro.setOnAction(livro());
	
		btnEscritor = new Button("Escritores");
		btnEscritor.setOnAction(escritor());
		
		btnGenero = new Button("Generos");
		btnGenero.setOnAction(genero());
		
		btnEditora = new Button("Editoras");
		btnEditora.setOnAction(editora());
		
		pane.getChildren().addAll(btnSair, btnLivro, btnEscritor, btnGenero, btnEditora);

	}

	private void configLayout() {
		pane.setPrefSize(480, 420);
		
		// Livro
		btnLivro.setLayoutX(20);
		btnLivro.setLayoutY(20);
		btnLivro.setPrefHeight((pane.getPrefHeight() / 2) - 45);
		btnLivro.setPrefWidth((pane.getPrefWidth() / 2) - 30);
		
		// Escritor
		btnEscritor.setLayoutX(btnLivro.getPrefWidth() + 40);
		btnEscritor.setLayoutY(20);
		btnEscritor.setPrefHeight((pane.getPrefHeight() / 2) - 45);
		btnEscritor.setPrefWidth((pane.getPrefWidth() / 2) - 30);
		
		// Genero
		btnGenero.setLayoutX(20);
		btnGenero.setLayoutY(btnLivro.getPrefHeight() + 40);
		btnGenero.setPrefHeight((pane.getPrefHeight() / 2) - 45);
		btnGenero.setPrefWidth((pane.getPrefWidth() / 2) - 30);
		
		// Editora
		btnEditora.setLayoutX(btnGenero.getPrefWidth() + 40);
		btnEditora.setLayoutY(btnLivro.getPrefHeight()+ 40);
		btnEditora.setPrefHeight((pane.getPrefHeight() / 2) - 45);
		btnEditora.setPrefWidth((pane.getPrefWidth() / 2) - 30);
		
		// Sair
		btnSair.setLayoutX(pane.getPrefWidth() - 110);
		btnSair.setLayoutY(pane.getPrefHeight() - 35);
		btnSair.setPrefHeight(30);
		btnSair.setPrefWidth(90);
	}

	private EventHandler<ActionEvent> sair(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					new LoginFX().start(stage);
				}catch(Exception e) {
					AlertaFX.alerta("Erro ao iniciar tela de login!");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> livro(){
		return new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent event) {
				try {
					new LivroFX(usuarioLogado).start(stage);
				}catch(Exception e) {
					AlertaFX.alerta("Erro ao iniciar tela de livros!");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> escritor(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					new EscritorFX(usuarioLogado).start(stage);
				}catch(Exception e) {
					AlertaFX.alerta("Erro ao iniciar tela de escritores!");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> genero(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					new GeneroFX(usuarioLogado).start(stage);
				}catch(Exception e) {
					AlertaFX.alerta("Erro ao iniciar tela de generos!");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> editora(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					new EditoraFX(usuarioLogado).start(stage);
				}catch(Exception e) {
					AlertaFX.alerta("Erro ao iniciar tela de editoras!");
				}
			}
		};
	}
	
}
















