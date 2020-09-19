package Kindle.ihc;

import Kindle.db.GeneroDAO;
import Kindle.entidades.Genero;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class AdicionaGeneroLivroFX extends Application{

	private static int atualizar;
	private Stage stage;
	private Pane pane;
	private TableView<Genero> tabela;
	private TableColumn<Genero, String> clmGenero;
	private Button btnAdicionar;
	private Button btnSair;
	
	@SuppressWarnings("static-access")
	public AdicionaGeneroLivroFX(int atualizar) {
		this.atualizar = atualizar;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		initComponentes();
		configLayout();
		
		Scene scene = new Scene(pane);
		
		stage.setScene(scene);
		stage.setTitle("Adicionar Gênero(s)");
		stage.setResizable(false);
		stage.show();
	}

	private void initComponentes() {
		pane = new AnchorPane();
		
		// tabela
		tabela = new TableView<Genero>();
		tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tabela.setItems(geraListaGeneros());
		
		clmGenero = new TableColumn<Genero, String>("Gênero");
		clmGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
		
		tabela.getColumns().add(clmGenero);
		pane.getChildren().add(tabela);
		
		// botões
		btnAdicionar = new Button("Adicionar");
		btnAdicionar.setOnAction(adicionar());
		
		btnSair = new Button("Sair");
		btnSair.setOnAction(sair());
		
		pane.getChildren().addAll(btnAdicionar, btnSair);
	}
	
	private void configLayout() {
		pane.setPrefSize(400, 440);

		// Tabela
		tabela.setLayoutX(20);
		tabela.setLayoutY(20);
		tabela.setPrefHeight(pane.getPrefHeight() - 70);
		tabela.setPrefWidth(pane.getPrefWidth() - 40);
		
		// Adicionar
		btnAdicionar.setLayoutX(20);
		btnAdicionar.setLayoutY(pane.getPrefHeight() - 40);
		btnAdicionar.setPrefHeight(30);
		btnAdicionar.setPrefWidth((pane.getPrefWidth() / 2) - 30);
		
		// Sair
		btnSair.setLayoutX(btnAdicionar.getPrefWidth() + btnAdicionar.getLayoutX() + 10);
		btnSair.setLayoutY(pane.getPrefHeight() - 40);
		btnSair.setPrefHeight(30);
		btnSair.setPrefWidth((pane.getPrefWidth() / 2) - btnAdicionar.getLayoutX());
	}
	
	private ObservableList<Genero> geraListaGeneros(){
		return FXCollections.observableArrayList(new GeneroDAO().lista());
	}
	
	private EventHandler<ActionEvent> adicionar(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				TableViewSelectionModel<Genero> selectionModel = tabela.getSelectionModel();
				selectionModel.setSelectionMode(SelectionMode.SINGLE);
				Genero genero = selectionModel.getSelectedItem();
				
				try {
					
					if(selectionModel.isEmpty()) {
						AlertaFX.alerta("Selecione um gênero.");
						return;
					}
					
					if(atualizar == 0) {
						CadastrarLivroFX.atualizaListaGeneros(genero);
					}else if(atualizar == 1) {
						AtualizarLivroFX.atualizaListaGeneros(genero);
					}
					
				}catch(Exception e) {
					AlertaFX.erro("Erro ao adicionar gênero!");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> sair() {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				stage.close();
			}
		};
	}
	
}


















