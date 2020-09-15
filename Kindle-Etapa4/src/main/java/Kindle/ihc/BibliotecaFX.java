package Kindle.ihc;

import Kindle.entidades.Usuario;
import Kindle.entidades.Biblioteca;
import Kindle.entidades.Livro;
import Kindle.db.BibliotecaDAO;
import Kindle.db.LivroDAO;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TextFieldSkin;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

@SuppressWarnings("unused")
public class BibliotecaFX extends Application{

	private static Usuario usuarioLogado;
	private Stage stage;
	private Pane pane;
	private static TableView<Livro> tabela;
	private TableColumn<Livro, Integer> clmID;
	private TableColumn<Livro, String> clmTitulo;
	private TableColumn<Livro, String> clmEscritores;
	private TableColumn<Livro, String> clmGeneros;
	private TableColumn<Livro, Integer> clmPaginas;
	private TableColumn<Livro, String> clmEditora;
	private Button btnAddLivro;
	private Button btnRemoverLivro;
	private Button btnVoltar;
	
	@SuppressWarnings("static-access")
	public BibliotecaFX(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		initComponentes();
		configLayout();
		
		Scene scene = new Scene(pane);
		btnAddLivro.requestFocus();
		
		stage.setScene(scene);
		stage.setTitle("Biblioteca de " + usuarioLogado.getNome());
		stage.setResizable(false);
		stage.show();
	}
	
	@SuppressWarnings("unchecked")
	private void initComponentes() {
		pane = new AnchorPane();
		
		// tabela
		tabela = new TableView<Livro>();
		tabela.setItems(geraListaTabela());
		
		clmID = new TableColumn<Livro, Integer>("ID Livro");
		clmTitulo = new TableColumn<Livro, String>("Título");
		clmEscritores = new TableColumn<Livro, String>("Escritor(es)");
		clmGeneros = new TableColumn<Livro, String>("Gênero(s)");
		clmPaginas = new TableColumn<Livro, Integer>("Páginas");
		clmEditora = new TableColumn<Livro, String>("Editora");
		
		for(int indice = 0; indice < tabela.getItems().size(); indice++) {
			clmID.setCellValueFactory(new PropertyValueFactory<>("idLivro"));
			clmTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
			
			clmEditora.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Livro,String>, ObservableValue<String>>() {
				
				@Override
				public ObservableValue<String> call(CellDataFeatures<Livro, String> param) {
					return new SimpleStringProperty(param.getValue().getEditora().getNome());
				}
			});
			
			clmPaginas.setCellValueFactory(new PropertyValueFactory<>("paginas"));
		}
		
		tabela.getColumns().addAll(clmID, clmTitulo, clmEscritores, clmGeneros, clmPaginas, clmEditora);
		pane.getChildren().add(tabela);
		
		// botões
		btnAddLivro = new Button("Adicionar Livro");
		btnAddLivro.setOnAction(adicionarLivro());
		
		btnRemoverLivro = new Button("Remover Livro");
		btnRemoverLivro.setOnAction(removerLivro());
		
		btnVoltar = new Button("Voltar");
		btnVoltar.setOnAction(voltar());
		
		pane.getChildren().addAll(btnAddLivro, btnRemoverLivro, btnVoltar);
	}
	
	private void configLayout() {
		pane.setPrefSize(800, 600);
		
		// Tabela
		tabela.setLayoutX(20);
		tabela.setLayoutY(20);
		tabela.setPrefHeight(pane.getPrefHeight() - 80);
		tabela.setPrefWidth(pane.getPrefWidth() - 40);
		
		// AddLivro
		btnAddLivro.setLayoutX(20);
		btnAddLivro.setLayoutY(pane.getPrefHeight() - 40);
		btnAddLivro.setPrefHeight(30);
		btnAddLivro.setPrefWidth((pane.getPrefWidth() / 3) - 20);
		
		// RemoverLivro
		btnRemoverLivro.setLayoutX(btnAddLivro.getPrefWidth() + 30);
		btnRemoverLivro.setLayoutY(pane.getPrefHeight() - 40);
		btnRemoverLivro.setPrefHeight(30);
		btnRemoverLivro.setPrefWidth((pane.getPrefWidth() / 3) - 20);
		
		// Voltar
		btnVoltar.setLayoutX(btnRemoverLivro.getPrefWidth() + btnAddLivro.getPrefWidth() + 40);
		btnVoltar.setLayoutY(pane.getPrefHeight() - 40);
		btnVoltar.setPrefHeight(30);
		btnVoltar.setPrefWidth((pane.getPrefWidth() / 3) - 20);
	}

	private static ObservableList<Livro> geraListaTabela() {
		return FXCollections.observableArrayList(new BibliotecaDAO().buscaLivrosBiblioteca(usuarioLogado).getLivros());
	}
	
	private static void atualizaTabela() {
		tabela.setItems(geraListaTabela());
	}
	
	private EventHandler<ActionEvent> adicionarLivro(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
			}
		};
	}
	
	private EventHandler<ActionEvent> removerLivro(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				TableViewSelectionModel<Livro> selectionModel = tabela.getSelectionModel();
				selectionModel.setSelectionMode(SelectionMode.SINGLE);
				Livro selectedItem = selectionModel.getSelectedItem();
				
				try {
					if(selectionModel.isEmpty()) {
						AlertaFX.alerta("Selecione um livro!");
						return;
					}
					
					Livro livro = selectedItem;
					new BibliotecaDAO().removeLivro(usuarioLogado, livro);
					AlertaFX.info("Livro removido com sucesso!");
					atualizaTabela();
					
				}catch(Exception e) {
					AlertaFX.alerta("Erro ao remover livro da biblioteca!");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> voltar(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					new ClienteFX(usuarioLogado).start(stage);
				}catch(Exception e) {
					AlertaFX.alerta("Erro ao voltar para o menu inicial!");
				}
			}
		};
	}
	
}



















