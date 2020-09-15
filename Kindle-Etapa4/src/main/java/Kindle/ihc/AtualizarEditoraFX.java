package Kindle.ihc;

import Kindle.entidades.Editora;
import Kindle.db.EditoraDAO;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class AtualizarEditoraFX extends Application{

	private Editora editora;
	private Stage stage;
	private Pane pane;
	private Label lblNome;
	private Label lblCnpj;
	private Label lblEmail;
	private Label lblTelefone;
	private TextField txtNome;
	private TextField txtCnpj;
	private TextField txtEmail;
	private TextField txtTelefone;
	private Button btnAtualizar;
	private Button btnSair;
	
	public AtualizarEditoraFX(Editora editora) {
		this.editora = editora;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		initComponentes();
		configLayout();
		
		Scene scene = new Scene(pane);
		btnSair.requestFocus();
		
		stage.setScene(scene);
		stage.setTitle("Atualizar Editora");
		stage.setResizable(false);
		stage.show();
	}
	
	private void initComponentes() {
		pane = new AnchorPane();

		// rótulos 
		lblNome = new Label("Nome:");
		lblCnpj = new Label("CNPJ:");
		lblEmail = new Label("Email:");
		lblTelefone = new Label("Telefone:");
		
		pane.getChildren().addAll(lblNome, lblCnpj, lblEmail, lblTelefone);
		
		// caixas de texto
		txtNome = new TextField(this.editora.getNome());
		txtNome.setPromptText("Nome");
		
		txtCnpj = new TextField(this.editora.getCnpj());
		txtCnpj.setPromptText("CNPJ");
		
		txtEmail = new TextField(this.editora.getEmail());
		txtEmail.setPromptText("Email");
		
		txtTelefone = new TextField(this.editora.getTelefone());
		txtTelefone.setPromptText("Telefone");
		
		pane.getChildren().addAll(txtNome, txtCnpj, txtEmail, txtTelefone);
		
		// botões
		btnAtualizar = new Button("Atualizar");
		btnAtualizar.setOnAction(atualizar());
		
		btnSair = new Button("Sair");
		btnSair.setOnAction(sair());
		
		pane.getChildren().addAll(btnAtualizar, btnSair);
	}
	
	private void configLayout() {
		pane.setPrefSize(400, 300);
		
		// NOME
		lblNome.setLayoutX(20);
		lblNome.setLayoutY(10);
		lblNome.setPrefHeight(20);

		txtNome.setLayoutX(20);
		txtNome.setLayoutY(lblNome.getPrefHeight() + lblNome.getLayoutY());
		txtNome.setPrefHeight(30);
		txtNome.setPrefWidth(pane.getPrefWidth() - 40);
		
		// CNPJ 
		lblCnpj.setLayoutX(20);
		lblCnpj.setLayoutY(txtNome.getLayoutY() + txtNome.getPrefHeight() + 10);
		lblCnpj.setPrefHeight(20);
		
		txtCnpj.setLayoutX(20);
		txtCnpj.setLayoutY(lblCnpj.getPrefHeight() + lblCnpj.getLayoutY());
		txtCnpj.setPrefHeight(30);
		txtCnpj.setPrefWidth(pane.getPrefWidth() - 40);
		
		// EMAIL
		lblEmail.setLayoutX(20);
		lblEmail.setLayoutY(txtCnpj.getLayoutY() + txtCnpj.getPrefHeight() + 10);
		lblEmail.setPrefHeight(20);
		
		txtEmail.setLayoutX(20);
		txtEmail.setLayoutY(lblEmail.getPrefHeight() + lblEmail.getLayoutY());
		txtEmail.setPrefHeight(30);
		txtEmail.setPrefWidth(pane.getPrefWidth() - 40);
		
		// TELEFONE
		lblTelefone.setLayoutX(20);
		lblTelefone.setLayoutY(txtEmail.getLayoutY() + txtEmail.getPrefHeight() + 10);
		lblTelefone.setPrefHeight(20);
		
		txtTelefone.setLayoutX(20);
		txtTelefone.setLayoutY(lblTelefone.getPrefHeight() + lblTelefone.getLayoutY());
		txtTelefone.setPrefHeight(30);
		txtTelefone.setPrefWidth(pane.getPrefWidth() - 40);
		
		// ATUALIZAR 
		btnAtualizar.setLayoutX(20);
		btnAtualizar.setLayoutY(pane.getPrefHeight() - 40);
		btnAtualizar.setPrefHeight(30);
		btnAtualizar.setPrefWidth((pane.getPrefWidth() / 2) - 30);
		
		// SAIR 
		btnSair.setLayoutX(btnAtualizar.getPrefWidth() + 40);
		btnSair.setLayoutY(pane.getPrefHeight() - 40);
		btnSair.setPrefHeight(30);
		btnSair.setPrefWidth((pane.getPrefWidth() / 2) - 30);
		
	}

	private EventHandler<ActionEvent> atualizar() {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					
					if(txtNome.getText().isBlank()) {
						AlertaFX.alerta("Nome em branco!");
						return;
					}
					
					if(txtCnpj.getText().isBlank()) {
						AlertaFX.alerta("CNPJ em branco!");
						return;
					}
					
					if(txtEmail.getText().isBlank()) {
						AlertaFX.alerta("Email em branco!");
						return;
					}
					
					if(txtTelefone.getText().isBlank()) {
						AlertaFX.alerta("Telefone em branco!");
						return;
					}
					
					editora.setNome(txtNome.getText());
					editora.setCnpj(txtCnpj.getText());
					editora.setEmail(txtEmail.getText());
					editora.setTelefone(txtTelefone.getText());
					
					new EditoraDAO().atualizaEditora(editora);
					AlertaFX.info("Editora atualizada com sucesso!");
					stage.close();
					EditoraFX.atualizaTabela();
					
				}catch(Exception e) {
					AlertaFX.alerta("Erro ao atualizar editora!");
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





















