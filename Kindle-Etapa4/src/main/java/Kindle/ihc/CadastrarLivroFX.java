package Kindle.ihc;

import Kindle.entidades.Usuario;
import Kindle.entidades.Genero;
import Kindle.db.GeneroDAO;
import Kindle.entidades.Livro;
import Kindle.db.LivroDAO;
import Kindle.entidades.Editora;
import Kindle.entidades.Escritor;
import Kindle.db.EscritorDAO;
import Kindle.db.EditoraDAO;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.ComboBoxTreeTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.ChoiceBoxSkin;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

@SuppressWarnings("unused")
public class CadastrarLivroFX extends Application{

	private Usuario usuarioLogado;
	private Stage stage;
	private Pane pane;
	private static Livro livro = null;
	private Label lblTitulo;
	private Label lblPaginas;
	private Label lblEscritor;
	private Label lblGenero;
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
 	private Button btnAddEscritor;
	private Button btnRemoverEscritor;
	private Button btnAddGenero;
	private Button btnRemoverGenero;
 	private Button btnCadastrar;
	private Button btnSair;
	
	public CadastrarLivroFX(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		initComponentes();
		configLayout();
		
		Scene scene = new Scene(pane);
		btnCadastrar.requestFocus();
		
		stage.setScene(scene);
		stage.setTitle("Cadastrar livro");
		stage.setResizable(false);
		stage.show();
	}

	@SuppressWarnings("unchecked")
	private void initComponentes() {
		livro = new Livro();
		pane = new AnchorPane();
		
		// rótulos
		
		lblTitulo = new Label("Título:");
		lblEscritor = new Label("Escritores:");
		lblGenero = new Label("Gêneros:");
		lblPaginas = new Label("Número de páginas:");
		lblEditora = new Label("Editoras:");
		
		pane.getChildren().addAll(lblTitulo,lblEscritor, lblGenero, lblPaginas, lblEditora);
		
		// caixas de textos
		txtTitulo = new TextField();
		txtTitulo.setPromptText("Título");
		
		txtPaginas = new TextField();
		txtPaginas.setPromptText("Páginas");
		
		pane.getChildren().addAll(txtTitulo, txtPaginas);
		
		// tabela, colunas e botões para escritores
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
		
		btnAddEscritor = new Button("Adicionar Escritor(es)");
		btnAddEscritor.setOnAction(adicionarEscritor());
		
		btnRemoverEscritor = new Button("Remover Escritor");
		btnRemoverEscritor.setOnAction(removerEscritor());
		
		pane.getChildren().addAll(tabelaEscritores, btnAddEscritor, btnRemoverEscritor);
		
		//tabela, colunas e botões para gêneros
		tabelaGeneros  = new TableView<Genero>();
		tabelaGeneros.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tabelaGeneros.setItems(geraListaGeneros());
		
		clmGenero = new TableColumn<Genero, String>("Gênero");
		clmGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
		
		tabelaGeneros.getColumns().add(clmGenero);
		
		btnAddGenero= new Button("Adicionar Gêner(os)");
		btnAddGenero.setOnAction(adicionarGenero());
		
		btnRemoverGenero = new Button("Remover Gênero");
		btnRemoverGenero.setOnAction(removerGenero());
		
		pane.getChildren().addAll(tabelaGeneros, btnAddGenero, btnRemoverGenero);
		
		// editora
		chbEditora = new ChoiceBox<>();
		chbEditora.setItems(geraListaEditoras());
		
		pane.getChildren().add(chbEditora);
		
		// botões
		btnCadastrar = new Button("Cadastrar");
		btnCadastrar.setOnAction(cadastrar());
		
		btnSair = new Button("Sair");
		btnSair.setOnAction(sair());
		
		pane.getChildren().addAll(btnCadastrar, btnSair);
		
	}
	
	private void configLayout() {
		pane.setPrefSize(400, 580);
		
		// Titulo
		lblTitulo.setLayoutX(20);
		lblTitulo.setLayoutY(20);
		
		txtTitulo.setLayoutX(20);
		txtTitulo.setLayoutY(lblTitulo.getLayoutY() + 20);
		txtTitulo.setPrefHeight(30);
		txtTitulo.setPrefWidth(pane.getPrefWidth() - 40);
		
		// Páginas
		lblPaginas.setLayoutX(20);
		lblPaginas.setLayoutY(lblTitulo.getLayoutY() + 60);
		
		txtPaginas.setLayoutX(20);
		txtPaginas.setLayoutY(lblPaginas.getLayoutY() + 20);
		txtPaginas.setPrefHeight(30);
		txtPaginas.setPrefWidth(pane.getPrefWidth() - 40);
		
		// Escritores
		lblEscritor.setLayoutX(20);
		lblEscritor.setLayoutY(txtPaginas.getPrefHeight() + txtPaginas.getLayoutY() + 10);
		lblEscritor.setPrefHeight(20);
		
		tabelaEscritores.setLayoutX(20);
		tabelaEscritores.setLayoutY(lblEscritor.getPrefHeight() + lblEscritor.getLayoutY());
		tabelaEscritores.setPrefHeight(100);
		tabelaEscritores.setPrefWidth(pane.getPrefWidth() - 40);
		
		btnAddEscritor.setLayoutX(20);
		btnAddEscritor.setLayoutY(tabelaEscritores.getPrefHeight() + tabelaEscritores.getLayoutY() + 10);
		btnAddEscritor.setPrefHeight(20);
		btnAddEscritor.setPrefWidth((pane.getPrefWidth() / 2) - 30);
		
		btnRemoverEscritor.setLayoutX(btnAddEscritor.getPrefWidth() + 30);
		btnRemoverEscritor.setLayoutY(btnAddEscritor.getLayoutY());
		btnRemoverEscritor.setPrefHeight(20);
		btnRemoverEscritor.setPrefWidth((pane.getPrefWidth() / 2) - 20);
		
		//Generos
		lblGenero.setLayoutX(20);
		lblGenero.setLayoutY(btnRemoverEscritor.getPrefHeight() + btnRemoverEscritor.getLayoutY() + 15);
		lblGenero.setPrefHeight(20);
		
		tabelaGeneros.setLayoutX(20);
		tabelaGeneros.setLayoutY(lblGenero.getPrefHeight() + lblGenero.getLayoutY());
		tabelaGeneros.setPrefHeight(100);
		tabelaGeneros.setPrefWidth(pane.getPrefWidth() - 40);
		
		btnAddGenero.setLayoutX(20);
		btnAddGenero.setLayoutY(tabelaGeneros.getPrefHeight() + tabelaGeneros.getLayoutY() + 10);
		btnAddGenero.setPrefHeight(20);
		btnAddGenero.setPrefWidth((pane.getPrefWidth() / 2) - 30);
		
		btnRemoverGenero.setLayoutX(btnAddGenero.getPrefWidth() + 30);
		btnRemoverGenero.setLayoutY(btnAddGenero.getLayoutY());
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
		
		// Cadastrar
		btnCadastrar.setLayoutX(20);
		btnCadastrar.setLayoutY(pane.getPrefHeight() - 40);
		btnCadastrar.setPrefHeight(30);
		btnCadastrar.setPrefWidth((pane.getPrefWidth() / 2) - 30);
		
		// Sair
		btnSair.setLayoutX(btnCadastrar.getPrefWidth() + 30);
		btnSair.setLayoutY(pane.getPrefHeight() - 40);
		btnSair.setPrefHeight(30);
		btnSair.setPrefWidth((pane.getPrefWidth() / 2) - 20);
		
	}
	
	private static ObservableList<Escritor> geraListaEscritores(){
		return FXCollections.observableArrayList(livro.getEscritores());
	}
	
	public static void atualizaListaEscritores(Escritor escritor) {
		for(int i = 0; i < livro.getEscritores().size(); i++) {
			if(livro.getEscritores().get(i).getCPF().equals(escritor.getCPF())) {
				AlertaFX.alerta("Escritor adicionado anteriormente!");
				return;
			}
		}
		
		livro.getEscritores().add(escritor);
		tabelaEscritores.setItems(geraListaEscritores());
	}
	
	private static ObservableList<Genero> geraListaGeneros(){
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
	
	
	private ObservableList<String> geraListaEditoras(){
		
		List<String> retorno = new ArrayList<String>();
		List<Editora> editoras = new EditoraDAO().lista();
		
		for(Editora editora: editoras) {
			retorno.add(editora.getNome());
		}
		
		return FXCollections.observableArrayList(retorno);
	}
	
	private EventHandler<ActionEvent> adicionarEscritor(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					new AdicionaEscritorLivroFX(0).start(new Stage());
				}catch(Exception e) {
					AlertaFX.erro("Erro ao abrir janela de adição de escritores!");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> removerEscritor() {
		return new EventHandler<ActionEvent>() {

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
							tabelaEscritores.setItems(geraListaEscritores());
							return;
						}
					}
					
				}catch(Exception e) {
					AlertaFX.erro("Erro ao remover escritor!");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> adicionarGenero(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					new AdicionaGeneroLivroFX(0).start(new Stage());
				}catch(Exception e) {
					AlertaFX.erro("Erro ao abrir janela de adição de gêneros!");
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
						 AlertaFX.alerta("Selecione um gênero");
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
	
	private EventHandler<ActionEvent> cadastrar(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				try {
					
					if(txtTitulo.getText().isBlank()) {
						AlertaFX.alerta("Título em branco!");
						return;
					}
					
					if(txtPaginas.getText().isBlank()) {
						AlertaFX.alerta("Número de páginas em branco!");
						return;
					}
					
					if(chbEditora.getSelectionModel().isEmpty()) {
						AlertaFX.alerta("Selecione uma editora!");
						return;
					}
					
					System.out.println(chbEditora.getSelectionModel().getSelectedItem());
					
					livro.setEditora(EditoraDAO.buscaEditoraPorNome(chbEditora.getSelectionModel().getSelectedItem()));
					
					livro.setTitulo(txtTitulo.getText());
					livro.setPaginas(Integer.valueOf(txtPaginas.getText()));
					
					new LivroDAO().adiciona(livro);
					
					if(livro.getEscritores().size() > 0) {
						for(int i = 0; i < livro.getEscritores().size(); i++) {
							LivroDAO.adicionaEscritorLivro(livro.getEscritores().get(i), livro);
						}
					}
					
					if(livro.getGeneros().size() > 0) {
						for(int y = 0; y < livro.getGeneros().size(); y++) {
							LivroDAO.adicionaGeneroLivro(livro.getGeneros().get(y), livro);
						}
					}
					
					AlertaFX.info("Livro cadastrado com sucesso!");
					new LivroFX(usuarioLogado).start(stage);
					
				}catch(Exception e) {
					AlertaFX.alerta("Erro ao cadastrar livro!");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> sair(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					new LivroFX(usuarioLogado).start(stage);
				}catch(Exception e) {
					AlertaFX.erro("Erro ao voltar para o menu anterior!");
				}
			}
		};
	}
	
}



















