package Kindle.ihc;

import java.util.List;

import Kindle.db.EscritorDAO;
import Kindle.entidades.Escritor;
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
public class AdicionaEscritorLivroFX extends Application{

	private static int atualizar;
	private Stage stage;
	private Pane pane;
	private TableView<Escritor> tabela;
	private TableColumn<Escritor, Integer> clmID;
	private TableColumn<Escritor, String> clmNome;
	private TableColumn<Escritor, String> clmSobrenome;
	private Button btnAdicionar;
	private Button btnSair;
	
	@SuppressWarnings("static-access")
	public AdicionaEscritorLivroFX(int atualizar) {
		this.atualizar = atualizar;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		initComponentes();
		configLayout();
		
		Scene scene = new Scene(pane);
		
		stage.setScene(scene);
		stage.setTitle("Adicionar Escritor(es)");
		stage.setResizable(false);
		stage.show();
	}
	
	@SuppressWarnings("unchecked")
	private void initComponentes() {
		pane = new AnchorPane();
		
		// tabela
		tabela = new TableView<Escritor>();
		tabela.setItems(geraListaEscritores());
		
		clmID= new TableColumn<Escritor, Integer>("ID Escritor");
		clmID.setCellValueFactory(new PropertyValueFactory<>("idEscritor"));
		
		clmNome = new TableColumn<Escritor, String>("Nome");
		clmNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		
		clmSobrenome = new TableColumn<Escritor, String>("Sobrenome");
		clmSobrenome.setCellValueFactory(new PropertyValueFactory<>("sobrenome"));
		
		tabela.getColumns().addAll(clmID, clmNome, clmSobrenome);
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

	private ObservableList<Escritor> geraListaEscritores(){
		return FXCollections.observableArrayList(new EscritorDAO().lista());
	}
	
	private EventHandler<ActionEvent> adicionar() {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				TableViewSelectionModel<Escritor> selectionModel = tabela.getSelectionModel();
				selectionModel.setSelectionMode(SelectionMode.SINGLE);
				Escritor escritor = selectionModel.getSelectedItem();
				
				try {
					
					if(selectionModel.isEmpty()) {
						AlertaFX.alerta("Selecione um escritor.");
						return;
					}
					
					if(atualizar == 0) {
						CadastrarLivroFX.atualizaListaEscritores(escritor);
					}else if(atualizar == 1){
						AtualizarLivroFX.atualizaListEscritores(escritor);
					}
					
				}catch(Exception e) {
					AlertaFX.erro("Erro ao adicionar escritor!");
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
