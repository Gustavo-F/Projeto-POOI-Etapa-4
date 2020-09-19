package Kindle.ihc;

import Kindle.entidades.Usuario;
import Kindle.entidades.Biblioteca;
import Kindle.entidades.Livro;
import Kindle.db.LivroDAO;
import Kindle.db.BibliotecaDAO;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

@SuppressWarnings("unused")
public class ClienteFX extends Application{

	private Usuario usuarioLogado;
	private Stage stage;
	private Pane pane;
	private static TableView<Livro> tabela;
	private TableColumn<Livro, Integer> clmID;
	private TableColumn<Livro, String> clmTitulo;
	private TableColumn<Livro, Integer> clmPaginas;
	private TableColumn<Livro, String> clmEditora;
	private Button btnBiblioteca;
	private Button btnAddLivroBiblioteca;
	private Button btnSair;
	
	public ClienteFX(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		initComponentes();
		configLayout();
		
		Scene scene = new Scene(pane);
		
		stage.setScene(scene);
		stage.setTitle("Menu inicial");
		stage.setResizable(false);
		stage.show();
	}

	@SuppressWarnings("unchecked")
	private void initComponentes() {
		pane = new AnchorPane();
		
		// tabela
		tabela = new TableView<Livro>();
		tabela.setItems(geraTabela());
		
		clmID = new TableColumn<Livro, Integer>("ID Livro");
		clmTitulo = new TableColumn<Livro, String>("Título");
		clmPaginas = new TableColumn<Livro, Integer>("Páginas");
		clmEditora = new TableColumn<Livro, String>("Editora");
		
		for(int i = 0; i < tabela.getItems().size(); i++) {
			clmID.setCellValueFactory(new PropertyValueFactory<>("idLivro"));
			clmTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
			clmPaginas.setCellValueFactory(new PropertyValueFactory<>("paginas"));
			
			clmEditora.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Livro,String>, ObservableValue<String>>() {
				
				@Override
				public ObservableValue<String> call(CellDataFeatures<Livro, String> param) {
					return new SimpleStringProperty(param.getValue().getEditora().getNome());
				}
			});
		}
		
		tabela.getColumns().addAll(clmID, clmTitulo, clmPaginas, clmEditora);
		pane.getChildren().add(tabela);
		
		// botões
		btnBiblioteca = new Button("Acessar Biblioteca");
		btnBiblioteca.setOnAction(acessarBiblioteaca());
		
		btnAddLivroBiblioteca = new Button("Adicionar à Biblioteca");
		btnAddLivroBiblioteca.setOnAction(adicionarLivroBiblioteca());
		
		btnSair = new Button("Sair");
		btnSair.setOnAction(sair());
		
		pane.getChildren().addAll(btnBiblioteca, btnAddLivroBiblioteca, btnSair);
		
	}
	
	private void configLayout() {
		pane.setPrefSize(800, 600);
		
		// tabela
		tabela.setLayoutX(20);
		tabela.setLayoutY(20);
		tabela.setPrefHeight(pane.getPrefHeight() - 70);
		tabela.setPrefWidth(pane.getPrefWidth() - 40);
		
		// biblioteca
		btnBiblioteca.setLayoutX(20);
		btnBiblioteca.setLayoutY(pane.getPrefHeight() - 40);
		btnBiblioteca.setPrefHeight(30);
		btnBiblioteca.setPrefWidth((pane.getPrefWidth() / 3) - 20);
		
		btnAddLivroBiblioteca.setLayoutX(btnBiblioteca.getPrefWidth() + 30);
		btnAddLivroBiblioteca.setLayoutY(pane.getPrefHeight() - 40);
		btnAddLivroBiblioteca.setPrefHeight(30);
		btnAddLivroBiblioteca.setPrefWidth((pane.getPrefWidth() / 3) - 20);
		
		// sair
		btnSair.setLayoutX(pane.getPrefWidth() - (pane.getPrefWidth() / 3));
		btnSair.setLayoutY(pane.getPrefHeight() - 40);
		btnSair.setPrefHeight(30);
		btnSair.setPrefWidth((pane.getPrefWidth() / 3) - 20);
	}
	
	private static ObservableList<Livro> geraTabela(){
		return FXCollections.observableArrayList(new LivroDAO().lista());
	}
	
	private EventHandler<ActionEvent> acessarBiblioteaca(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					new BibliotecaFX(usuarioLogado).start(stage);
				}catch(Exception e) {
					AlertaFX.alerta("Erro ao abrir biblioteca!");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> adicionarLivroBiblioteca(){
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
					
					Biblioteca biblioteca = new BibliotecaDAO().buscaBiblioteca(usuarioLogado);
					
					for(int i = 0; i < biblioteca.getLivros().size(); i++) {
						if(biblioteca.getLivros().get(i).getTitulo().equals(selectedItem.getTitulo())) {
							AlertaFX.info("Este livro já foi adicionado à sua biblioteca!");
							return;
						}
					}
					
					Livro livro = selectedItem;
					new BibliotecaDAO().adicionaLivro(usuarioLogado, livro);
					AlertaFX.info("Livro adicionado com sucesso!");
					
				}catch(Exception e) {
					AlertaFX.alerta("Erro ao adicionar livro à sua biblioteca!");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> sair(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					new LoginFX().start(stage);
				}catch(Exception e) {
					AlertaFX.alerta("Erro ao sair do menu inicial!");
				}
			}
		};
	}
	
}



















