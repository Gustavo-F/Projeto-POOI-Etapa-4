package Kindle.ihc;

import java.util.ArrayList;
import java.util.List;

import Kindle.db.EditoraDAO;
import Kindle.db.LivroDAO;
import Kindle.entidades.Editora;
import Kindle.entidades.Escritor;
import Kindle.entidades.Genero;
import Kindle.entidades.Livro;
import Kindle.entidades.Usuario;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class AtualizarLivroFX extends Application{
	
	private static Livro livro = null;
	private Usuario usuarioLogado;
	private Stage stage;
	private Pane pane;
	private Label lblTitulo;
	private Label lblPaginas;
	private Label lblEscritores;
	private Label lblGeneros;
	private Label lblEditora;
	private TextField txtTitulo;
	private TextField txtPaginas;
	private static TableView<Escritor> tabelaEscritores;
	private TableColumn<Escritor, Integer> clmIdEscritor;
	private TableColumn<Escritor, String> clmNomeEscritor;
	private TableColumn<Escritor, String> clmSobrenome;
	private static TableView<Genero> tabelaGeneros;
	private TableColumn<Genero, String> clmGenero;
	private ChoiceBox<String> chbEditora;
	private Button btnAdicionarEscritor;
	private Button btnRemoverEscritor;
	private Button btnAdicionarGenero;
	private Button btnRemoverGenero;
	private Button btnAtualizar;
	private Button btnSair;
 
	@SuppressWarnings("static-access")
	public AtualizarLivroFX(Livro livro, Usuario usuarioLogado) {
		this.livro = livro;
		this.usuarioLogado = usuarioLogado;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		initComponentes();
		configLayout();
		
		Scene scene = new Scene(pane);
		
		stage.setScene(scene);
		stage.setTitle("Atualizar Livro");
		stage.setResizable(false);
		stage.show();
	}

	@SuppressWarnings("unchecked")
	private void initComponentes() {
		pane = new AnchorPane();
		
		// Rótulos
		lblTitulo = new Label("Título:");
		lblPaginas = new Label("Páginas:");
		lblEscritores = new Label("Escritor(es):");
		lblGeneros = new Label("Gênero(s):");
		lblEditora = new Label("Editora:");
		
		pane.getChildren().addAll(lblTitulo, lblPaginas, lblEscritores, lblGeneros, lblEditora);
		
		// Titulo e Páginas
		txtTitulo = new TextField(livro.getTitulo());
		txtTitulo.setPromptText("Título");
		
		txtPaginas = new TextField(Integer.toString(livro.getPaginas()));
		txtPaginas.setPromptText("Páginas");
		
		pane.getChildren().addAll(txtTitulo, txtPaginas);
		
		// Tabela de escritores
		tabelaEscritores = new TableView<Escritor>();
		tabelaEscritores.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tabelaEscritores.setItems(geraListaEscritores());
		
		clmIdEscritor = new TableColumn<Escritor, Integer>("ID Escritor");
		clmIdEscritor.setCellValueFactory(new PropertyValueFactory<>("idEscritor"));
		
		clmNomeEscritor = new TableColumn<Escritor, String>("Nome");
		clmNomeEscritor.setCellValueFactory(new PropertyValueFactory<>("nome"));
		
		clmSobrenome = new TableColumn<Escritor, String>("Sobrenome");
		clmSobrenome.setCellValueFactory(new PropertyValueFactory<>("sobrenome"));
		
		tabelaEscritores.getColumns().addAll(clmIdEscritor, clmNomeEscritor, clmSobrenome);
		
		btnAdicionarEscritor = new Button("Adicionar Escritor");
		btnAdicionarEscritor.setOnAction(adicionarEscritor());
		
		btnRemoverEscritor = new Button("Remover Escritor");
		btnRemoverEscritor.setOnAction(removerEscritor());
		
		pane.getChildren().addAll(tabelaEscritores, btnAdicionarEscritor, btnRemoverEscritor);
		
		// Tabela de Gêneros
		tabelaGeneros = new TableView<Genero>();
		tabelaGeneros.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tabelaGeneros.setItems(geraListaGeneros());
		
		clmGenero = new TableColumn<Genero, String>("Gênero");
		clmGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
		
		tabelaGeneros.getColumns().add(clmGenero);
		
		btnAdicionarGenero = new Button("Adicionar Gênero(s)");
		btnAdicionarGenero.setOnAction(adicionarGenero());
		
		btnRemoverGenero = new Button("Remover Gênero");
		btnRemoverGenero.setOnAction(removerGenero());
		
		pane.getChildren().addAll(tabelaGeneros, btnAdicionarGenero, btnRemoverGenero);
		
		// Editora
		chbEditora = new ChoiceBox<String>();
		chbEditora.setItems(geraListaEditoras());
		
		int indice = 0;
		
		for(int i = 0; i <chbEditora.getItems().size(); i++) {
			if(chbEditora.getItems().get(i).equals(livro.getEditora().getNome())) {
				indice = i;
				break;
			}
		}
		
		chbEditora.getSelectionModel().select(indice);
		
		pane.getChildren().add(chbEditora);
		
		// Atualizar e Sair
		btnAtualizar = new Button("Atualizar");
		btnAtualizar.setOnAction(atualizar());
		
		btnSair = new Button("Sair");
		btnSair.setOnAction(sair());
		
		pane.getChildren().addAll(btnAtualizar, btnSair);

	}

	private void configLayout() {
		pane.setPrefSize(400, 580);
		
		// Título
		lblTitulo.setLayoutX(20);
		lblTitulo.setLayoutY(20);
		lblTitulo.setPrefHeight(30);
		
		txtTitulo.setLayoutX(20);
		txtTitulo.setLayoutY(lblTitulo.getLayoutY() + lblTitulo.getPrefHeight());
		txtTitulo.setPrefHeight(30);
		txtTitulo.setPrefWidth(pane.getPrefWidth() - 40);
		
		// Páginas
		lblPaginas.setLayoutX(20);
		lblPaginas.setLayoutY(txtTitulo.getPrefHeight() + txtTitulo.getLayoutY() + 10);
		lblPaginas.setPrefHeight(20);
		
		txtPaginas.setLayoutX(20);
		txtPaginas.setLayoutY(lblPaginas.getPrefHeight() + lblPaginas.getLayoutY());
		txtPaginas.setPrefHeight(30);
		txtPaginas.setPrefWidth(pane.getPrefWidth() - 40);
		
		// Escritores
		lblEscritores.setLayoutX(20);
		lblEscritores.setLayoutY(txtPaginas.getPrefHeight() + txtPaginas.getLayoutY() + 10);
		lblEscritores.setPrefHeight(20);
		
		tabelaEscritores.setLayoutX(20);
		tabelaEscritores.setLayoutY(lblEscritores.getPrefHeight() + lblEscritores.getLayoutY());
		tabelaEscritores.setPrefHeight(100);
		tabelaEscritores.setPrefWidth(pane.getPrefWidth() - 40);
		
		btnAdicionarEscritor.setLayoutX(20);
		btnAdicionarEscritor.setLayoutY(tabelaEscritores.getPrefHeight() + tabelaEscritores.getLayoutY() + 10);
		btnAdicionarEscritor.setPrefHeight(20);
		btnAdicionarEscritor.setPrefWidth((pane.getPrefWidth() / 2) - 30);
		
		btnRemoverEscritor.setLayoutX(btnAdicionarEscritor.getPrefWidth() + 30);
		btnRemoverEscritor.setLayoutY(btnAdicionarEscritor.getLayoutY());
		btnRemoverEscritor.setPrefHeight(20);
		btnRemoverEscritor.setPrefWidth((pane.getPrefWidth() / 2) - 20);
		
		// Gêneros
		lblGeneros.setLayoutX(20);
		lblGeneros.setLayoutY(btnRemoverEscritor.getPrefHeight() + btnRemoverEscritor.getLayoutY() + 15);
		lblGeneros.setPrefHeight(20);
		
		tabelaGeneros.setLayoutX(20);
		tabelaGeneros.setLayoutY(lblGeneros.getPrefHeight() + lblGeneros.getLayoutY());
		tabelaGeneros.setPrefHeight(100);
		tabelaGeneros.setPrefWidth(pane.getPrefWidth() - 40);
		
		btnAdicionarGenero.setLayoutX(20);
		btnAdicionarGenero.setLayoutY(tabelaGeneros.getPrefHeight() + tabelaGeneros.getLayoutY() + 10);
		btnAdicionarGenero.setPrefHeight(20);
		btnAdicionarGenero.setPrefWidth((pane.getPrefWidth() / 2) - 30);
		
		btnRemoverGenero.setLayoutX(btnAdicionarGenero.getPrefWidth() + 30);
		btnRemoverGenero.setLayoutY(btnAdicionarGenero.getLayoutY());
		btnRemoverGenero.setPrefHeight(20);
		btnRemoverGenero.setPrefWidth((pane.getPrefWidth() / 2) - 20);
		
		// Editora
		lblEditora.setLayoutX(20);
		lblEditora.setLayoutY(btnRemoverGenero.getPrefHeight() + btnRemoverGenero.getLayoutY() + 15);
		lblEditora.setPrefHeight(20);
		
		chbEditora.setLayoutX(20);
		chbEditora.setLayoutY(lblEditora.getPrefHeight() + lblEditora.getLayoutY());
		chbEditora.setPrefHeight(30);
		chbEditora.setPrefWidth(pane.getPrefWidth() - 40);
		
		// Atualizar
		btnAtualizar.setLayoutX(20);
		btnAtualizar.setLayoutY(pane.getPrefHeight() - 40);
		btnAtualizar.setPrefHeight(30);
		btnAtualizar.setPrefWidth((pane.getPrefWidth() / 2) - 30);
		
		// Sair
		btnSair.setLayoutX(btnAtualizar.getPrefWidth() + 30);
		btnSair.setLayoutY(pane.getPrefHeight() - 40);
		btnSair.setPrefHeight(30);
		btnSair.setPrefWidth((pane.getPrefWidth() / 2) - 20);
		
	}
	
	private static ObservableList<Escritor> geraListaEscritores() {
		return FXCollections.observableArrayList(livro.getEscritores());
	}
	
	public static void atualizaListEscritores(Escritor escritor) {
		if(escritor != null) {
			for(int i = 0; i < livro.getEscritores().size(); i++) {
				if(livro.getEscritores().get(i).getCPF().equals(escritor.getCPF())) {
					AlertaFX.alerta("Escritor adicionado anteriormente!");
					return;
				}
			}
			
			livro.getEscritores().add(escritor);
		}
		
		tabelaEscritores.setItems(geraListaEscritores());
	}
	
	private static ObservableList<Genero> geraListaGeneros() {
		return FXCollections.observableArrayList(livro.getGeneros());
	}
	
	public static void atualizaListaGeneros(Genero genero) {
		if(genero != null) {
			for(int i = 0; i < livro.getGeneros().size(); i++) {
				if(livro.getGeneros().get(i).getGenero().equals(genero.getGenero())) {
					AlertaFX.alerta("Gênero adicionado anteriormente!");
					return;
				}
			}
			
			livro.getGeneros().add(genero);
		}
		
		tabelaGeneros.setItems(geraListaGeneros());
	}
	
	private ObservableList<String> geraListaEditoras() {
		
		List<String> retorno = new ArrayList<String>();
		List<Editora> editoras = new EditoraDAO().lista();
		
		for(Editora editora : editoras) {
			retorno.add(editora.getNome());
		}
		
		return FXCollections.observableArrayList(retorno);
	}
	
	private EventHandler<ActionEvent> adicionarEscritor() {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					new AdicionaEscritorLivroFX(1).start(new Stage());
				}catch(Exception e) {
					AlertaFX.erro("Erro ao iniciar janela de adição de escritores!");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> removerEscritor() {
		return new EventHandler<ActionEvent>() {

			@SuppressWarnings("static-access")
			@Override
			public void handle(ActionEvent event) {
				TableViewSelectionModel<Escritor> selectionModel = tabelaEscritores.getSelectionModel();
				selectionModel.setSelectionMode(SelectionMode.SINGLE);
				Escritor escritor = selectionModel.getSelectedItem();
				
				try {
					
					if(selectionModel.isEmpty()) {
						AlertaFX.alerta("Selecione um escritor.");
						return;
					}
					
					for(int i = 0; i < livro.getEscritores().size(); i++) {
						if(livro.getEscritores().get(i).equals(escritor)) {
							livro.getEscritores().remove(i);
							atualizaListEscritores(null);
							return;
						}
					}
					
				}catch(Exception e) {
					AlertaFX.erro("Erro ao remover escritor!");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> adicionarGenero() {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					new AdicionaGeneroLivroFX(1).start(new Stage());
				}catch(Exception e) {
					AlertaFX.erro("Erro ao iniciar tela de adição de gêneros!");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> removerGenero() {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				TableViewSelectionModel<Genero> selectionModel = tabelaGeneros.getSelectionModel();
				selectionModel.setSelectionMode(SelectionMode.SINGLE);
				Genero genero = selectionModel.getSelectedItem();
				
				try {
					
					if(selectionModel.isEmpty()) {
						AlertaFX.alerta("Selecione um gênero.");
						return;
					}
					
					for(int i = 0; i < livro.getGeneros().size(); i++) {
						 if(livro.getGeneros().get(i).equals(genero)){
							 livro.getGeneros().remove(i);
							 atualizaListaGeneros(null);
							 return;
						 }
					 }
					
				}catch(Exception e) {
					AlertaFX.erro("Erro ao remover gênero!");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> atualizar(){
		return new EventHandler<ActionEvent>() {

			@SuppressWarnings("static-access")
			@Override
			public void handle(ActionEvent event) {
				List<Escritor> escritores = new LivroDAO().getEscritoresLivro(livro);
				List<Genero> generos = new LivroDAO().getGenerosLivro(livro);
				int confere, i, y;
				
				try {
					
					if(txtTitulo.getText().isBlank()) {
						AlertaFX.alerta("Título em branco!");
						return;
					}
					
					if(!livro.getTitulo().equals(txtTitulo.getText())) {
						LivroDAO.atualizaTitulo(livro, txtTitulo.getText());
					}
					
					if(txtPaginas.getText().isBlank()) {
						AlertaFX.alerta("Páginas em branco!");
						return;
					}
					
					if(livro.getPaginas() != Integer.valueOf(txtPaginas.getText())) {
						LivroDAO.atualizaPaginas(livro, Integer.valueOf(txtPaginas.getText()));
					}
					
					// adiciona escritores
					for(i = 0; i < livro.getEscritores().size(); i++) {
						confere = 0;
						
						for(y = 0; y < escritores.size(); y++) {
							if(livro.getEscritores().get(i).getIdPessoa() == escritores.get(y).getIdPessoa()) {
								confere = 1;
								break;
							}
						}
						
						if(confere == 0) {
							LivroDAO.adicionaEscritorLivro(livro.getEscritores().get(i), livro);
						}
					}
					
					// remove escritores
					for(i = 0; i < escritores.size(); i++) {
						confere = 0;
						
						for(y = 0; y < livro.getEscritores().size(); y++) {
							if(escritores.get(i).getIdPessoa() == livro.getEscritores().get(y).getIdPessoa()) {
								confere = 1;
								break;
							}
						}
						if(confere == 0) {
							new LivroDAO().removeEscritorLivro(escritores.get(i), livro);
							escritores.remove(i);
						}
					}
					
					// adiciona gêneros
					for(i = 0; i < livro.getGeneros().size(); i++) {
						confere = 0;
						
						for(y = 0; y < generos.size(); y++) {
							if(livro.getGeneros().get(i).getGenero().equals(generos.get(y).getGenero())) {
								confere = 1;
								break;
							}
						}
						
						if(confere == 0) {
							LivroDAO.adicionaGeneroLivro(livro.getGeneros().get(i), livro);
						}
					}
					
					// remove gêneros
					for(i = 0; i < generos.size(); i++) {
						confere = 0;
						
						for(y = 0; y < livro.getGeneros().size(); y++) {
							if(generos.get(i).getGenero().equals(livro.getGeneros().get(y).getGenero())) {
								confere = 1;
								break;
							}
						}
						
						if(confere == 0) {
							LivroDAO.removeGeneroLivro(generos.get(i), livro);
							generos.remove(i);
						}
					}
					
					if(chbEditora.getSelectionModel().isEmpty()) {
						AlertaFX.alerta("Selecione uma editora!");
						return;
					}
					
					if(!livro.getEditora().getNome().equals(chbEditora.getSelectionModel().getSelectedItem())) {
						LivroDAO.atualizaEditora(livro, EditoraDAO.buscaEditoraPorNome(chbEditora.getSelectionModel().getSelectedItem()));
					}
					
					AlertaFX.info("Livro atualizado com sucesso!");
					stage.close();
					LivroFX.atualizaTabela();
					
				}catch(Exception e) {
					AlertaFX.erro("Erro ao atualizar livro!");
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


























