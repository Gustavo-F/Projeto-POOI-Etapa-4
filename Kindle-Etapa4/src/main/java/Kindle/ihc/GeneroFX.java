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
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.control.skin.ListViewSkin;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class GeneroFX extends Application{

	private Usuario usuarioLogado;
	private Stage stage;
	private Pane pane;
	private static ListView<String> listaGeneros;
	private Button btnVoltar;
	private Button btnRemover;
	private Button btnAtualizar;
	private Button btnCadastrar;
	
	public GeneroFX(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		initComponentes();
		configLayout();
		
		Scene scene = new Scene(pane);

		stage.setScene(scene);
		stage.setTitle("Generos");
		stage.setResizable(false);
		stage.show();
	}
	
	private void initComponentes() {
		pane = new AnchorPane();
		
		// lista
		listaGeneros = new ListView<String>();
		listaGeneros.setEditable(true);
		ObservableList<String> items = FXCollections.observableArrayList(geraListaGeneros());
		listaGeneros.setCellFactory(TextFieldListCell.forListView());
		listaGeneros.setItems(items);
		
		pane.getChildren().add(listaGeneros);
		
		// botões
		btnCadastrar = new Button("Cadastrar");
		btnCadastrar.setOnAction(cadastrar());
		
		btnAtualizar = new Button("Atualizar");
		btnAtualizar.setOnAction(atualizar());
		
		btnRemover = new Button("Remover");
		btnRemover.setOnAction(remover());
		
		btnVoltar = new Button("Voltar");
		btnVoltar.setOnAction(voltar());
		
		pane.getChildren().addAll(btnCadastrar, btnAtualizar, btnRemover, btnVoltar);
		
	}
	
	private void configLayout() {
		pane.setPrefSize(800, 600);
		
		// LISTA
		listaGeneros.setLayoutX(20);
		listaGeneros.setLayoutY(20);
		listaGeneros.setPrefHeight(pane.getPrefHeight() - 80);
		listaGeneros.setPrefWidth(pane.getPrefWidth() - 40);
		
		// CADASTRAR 
		btnCadastrar.setLayoutX(20);
		btnCadastrar.setLayoutY(pane.getPrefHeight() - 40);
		btnCadastrar.setPrefHeight(30);
		btnCadastrar.setPrefWidth((pane.getPrefWidth() / 4) - 20);
		
		// ATUALIZAR
		btnAtualizar.setLayoutX(btnCadastrar.getPrefWidth() + 35);
		btnAtualizar.setLayoutY(pane.getPrefHeight() - 40);
		btnAtualizar.setPrefHeight(30);
		btnAtualizar.setPrefWidth((pane.getPrefWidth() / 4) - 20);
		
		// REMOVER
		btnRemover.setLayoutX(btnAtualizar.getPrefWidth() + btnCadastrar.getPrefWidth() + 55);
		btnRemover.setLayoutY(pane.getPrefHeight() - 40);
		btnRemover.setPrefHeight(30);
		btnRemover.setPrefWidth((pane.getPrefWidth() / 4) - 20);
		
		// VOLTAR
		btnVoltar.setLayoutX(pane.getPrefWidth() - 190);
		btnVoltar.setLayoutY(pane.getPrefHeight() - 40);
		btnVoltar.setPrefHeight(30);
		btnVoltar.setPrefWidth((pane.getPrefWidth() / 4) - 30);
		
	}

	private static List<String> geraListaGeneros(){
		
		List<String> retorno = new ArrayList<String>();
		List<Genero> generos = new GeneroDAO().lista();
		
		for(Genero genero : generos) 
			retorno.add(genero.getGenero());
		
		return retorno;
	}
	
	public static void atualizaListaGeneros() {
		ObservableList<String> items = FXCollections.observableArrayList(geraListaGeneros());
		listaGeneros.setItems(items);
	}
	
	private EventHandler<ActionEvent> voltar(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					new MainAdmFX(usuarioLogado).start(stage);
				}catch(Exception e) {
					AlertaFX.alerta("Erro ao iniciar menu inicial!");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> cadastrar(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					new CadastraGeneroFX().start(new Stage());
				}catch(Exception e) {
					AlertaFX.alerta("Erro ao iniciar tela de cadastro de genero!");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> atualizar(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					
					List<String> generos = geraListaGeneros();
					
					for(int i = 0; i < listaGeneros.getItems().size(); i++) {
						if(!listaGeneros.getItems().get(i).equals(generos.get(i))) {
							GeneroDAO.atualizaGenero(listaGeneros.getItems().get(i), generos.get(i));
						}
					}
					
					atualizaListaGeneros();
					AlertaFX.info("Gênero(s) atualizado(s) com sucesso!");
					
				}catch(Exception e) {
					AlertaFX.erro("Erro ao atualizar gênero(s)!");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> remover(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				if(listaGeneros.getSelectionModel().isEmpty()) {
					AlertaFX.alerta("Selecione um genero para ser excluído.");
					return;
				}
			
				GeneroDAO generoDAO = new GeneroDAO();
				Genero genero = generoDAO.buscaGenero(listaGeneros.getSelectionModel().getSelectedItem());
				generoDAO.remove(genero);
				atualizaListaGeneros();
			}
		};
	}
	
}



























