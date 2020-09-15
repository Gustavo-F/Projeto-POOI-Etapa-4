package Kindle.ihc;

import Kindle.db.LivroDAO;
import Kindle.entidades.Escritor;
import Kindle.entidades.Livro;
import Kindle.entidades.Usuario;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

@SuppressWarnings("unused")
public class LivroFX extends Application{

	private Usuario usuarioLogado;
	private Stage stage;
	private Pane pane;
	private TextField txtBusca;
	private static TableView<Livro> tabela;
	private TableColumn<Livro, Integer> clmID;
	private TableColumn<Livro, String> clmTitulo;
	private TableColumn<Livro, Integer> clmPaginas;
	private TableColumn<Livro, ArrayList<String>> clmEscritores;
	private TableColumn<Livro, String> clmGeneros;
	private TableColumn<Livro, String> clmEditora;
 	private Button btnBusca;
	private Button btnCadastrar;
	private Button btnAtualizar;
	private Button btnRemover;
	private Button btnVoltar;
	
	public LivroFX(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		initComponentes();
		configLayout();
		
		Scene scene = new Scene(pane);
		
		stage.setScene(scene);
		stage.setTitle("Livros");
		stage.setResizable(false);
		stage.show();
	}

	@SuppressWarnings("unchecked")
	private void initComponentes() {
		
		pane = new AnchorPane();
		
		// Entradas de texto
		txtBusca = new TextField();
		txtBusca.setPromptText("Buscar livro");
		
		pane.getChildren().add(txtBusca);

		// tabela
		tabela = new TableView<Livro>();
		tabela.setItems(geraListaTabela());
		
		clmID = new TableColumn<Livro, Integer>("ID");
		clmTitulo = new TableColumn<Livro, String>("Título");
		clmPaginas = new TableColumn<Livro, Integer>("Páginas");
		clmEscritores = new TableColumn<Livro, ArrayList<String>>("Escritor(es)");
		clmGeneros = new TableColumn<Livro, String>("Genero(s)");
		clmEditora = new TableColumn<Livro, String>("Editora");
		
		
		clmID.setCellValueFactory(new PropertyValueFactory<>("idLivro"));
		clmTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
		clmPaginas.setCellValueFactory(new PropertyValueFactory<>("paginas"));
		
		clmEditora.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Livro,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Livro, String> param) {
				return new SimpleStringProperty(param.getValue().getEditora().getNome());
			}
		});
		
		
		tabela.getColumns().addAll(clmID,clmTitulo, clmPaginas, clmEscritores, clmGeneros, clmEditora);
		pane.getChildren().add(tabela);
		
		// botões
		btnBusca = new Button("Buscar");
	
		btnCadastrar = new Button("Cadastrar");
		btnCadastrar.setOnAction(cadastrar());
		
		btnAtualizar = new Button("Atualizar");
		btnAtualizar.setOnAction(atualizar());
		
		btnRemover = new Button("Remover");
		btnRemover.setOnAction(remover());
		
		btnVoltar = new Button("Voltar");
		btnVoltar.setOnAction(voltar());
		
		pane.getChildren().addAll(btnBusca, btnCadastrar, btnAtualizar, btnRemover, btnVoltar);
		
	}
	
	private void configLayout() {
		pane.setPrefSize(800, 600);
		
		// Busca
		txtBusca.setLayoutX(20);
		txtBusca.setLayoutY(20);
		txtBusca.setPrefHeight(30);
		txtBusca.setPrefWidth(pane.getPrefWidth() - 220);
		
		btnBusca.setLayoutX(txtBusca.getPrefWidth() + 30);
		btnBusca.setLayoutY(20);
		btnBusca.setPrefHeight(30);
		btnBusca.setPrefWidth((pane.getPrefWidth() / 4) - 30);
		
		// tabela
		tabela.setLayoutX(20);
		tabela.setLayoutY(txtBusca.getLayoutY() + txtBusca.getPrefHeight() + 20);
		tabela.setPrefHeight(pane.getPrefHeight() - tabela.getLayoutY() - 60);
		tabela.setPrefWidth(pane.getPrefWidth() - 40);
		
		// Cadastrar
		btnCadastrar.setLayoutX(20);
		btnCadastrar.setLayoutY(pane.getPrefHeight() - 40);
		btnCadastrar.setPrefHeight(30);
		btnCadastrar.setPrefWidth((pane.getPrefWidth() / 4) - 20);
		
		// Atualizar
		btnAtualizar.setLayoutX(btnCadastrar.getPrefWidth() + 35);
		btnAtualizar.setLayoutY(pane.getPrefHeight() - 40);
		btnAtualizar.setPrefHeight(30);
		btnAtualizar.setPrefWidth((pane.getPrefWidth() / 4) - 20);
		
		// Remover
		btnRemover.setLayoutX(btnAtualizar.getPrefWidth() + btnCadastrar.getPrefWidth() + 55);
		btnRemover.setLayoutY(pane.getPrefHeight() - 40);
		btnRemover.setPrefHeight(30);
		btnRemover.setPrefWidth((pane.getPrefWidth() / 4) - 20);
		
		// Voltar
		btnVoltar.setLayoutX(pane.getPrefWidth() - 190);
		btnVoltar.setLayoutY(pane.getPrefHeight() - 40);
		btnVoltar.setPrefHeight(30);
		btnVoltar.setPrefWidth((pane.getPrefWidth() / 4) - 30);
		
	}
	
	private static ObservableList<Livro> geraListaTabela() {
		return FXCollections.observableArrayList(new LivroDAO().lista());
	}
	
	public static void atualizaTabela() {
		tabela.setItems(geraListaTabela());
	}
	
	private EventHandler<ActionEvent> cadastrar(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					new CadastrarLivroFX(usuarioLogado).start(stage);
				}catch (Exception e) {
					AlertaFX.alerta("Erro ao iniciar tela de cadastro de livro!");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> atualizar(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				TableViewSelectionModel<Livro> selectionModel = tabela.getSelectionModel();
				selectionModel.setSelectionMode(SelectionMode.SINGLE);
				Livro livroSelecionado = selectionModel.getSelectedItem();
				
				try {
					
					if(selectionModel.isEmpty()) {
						AlertaFX.alerta("Selecione um livro!");
						return;
					}
					
					new AtualizarLivroFX(livroSelecionado, usuarioLogado).start(new Stage());;
					
				}catch(Exception e) {
					AlertaFX.erro("Erro ao abrir tela de atualização de livro!");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> remover(){
		return new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				TableViewSelectionModel<Livro> selectionModel = tabela.getSelectionModel();
				selectionModel.setSelectionMode(SelectionMode.SINGLE);
				Livro livroSelecionado  = selectionModel.getSelectedItem();
				
				try {
					
					if(selectionModel.isEmpty()) {
						AlertaFX.alerta("Selecione um livro!");
						return;
					}
					
					new LivroDAO().remove(livroSelecionado);
					AlertaFX.info("Livro removido com sucesso!");
					atualizaTabela();	
					
				}catch(Exception e) {
					AlertaFX.erro("Erro ao remover livro!");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> voltar(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					new MainAdmFX(usuarioLogado).start(stage);
				}catch(Exception e) {
					AlertaFX.alerta("Erro ao voltar ao menu inicial!");
				}
			}
		};
	}
	
}
