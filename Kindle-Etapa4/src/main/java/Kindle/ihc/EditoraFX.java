package Kindle.ihc;

import Kindle.entidades.Usuario;
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
public class EditoraFX extends Application{

	private Usuario usuarioLogado;
	private Stage stage;
	private Pane pane;
	private static TableView<Editora> tabela;
	private TableColumn<Editora, Integer> clmID;
	private TableColumn<Editora, String> clmNome;
	private TableColumn<Editora, String> clmCNPJ;
	private TableColumn<Editora, String> clmEmail;
	private TableColumn<Editora, String> clmTelefone;
	private Button btnCadastrar;
	private Button btnAtualizar;
	private Button btnRemover;
 	private Button btnVoltar;
	
	public EditoraFX(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		initComponentes();
		configLayout();
		
		Scene scene = new Scene(pane);
		
		stage.setScene(scene);
		stage.setTitle("Editoras");
		stage.setResizable(false);
		stage.show();
	}

	@SuppressWarnings("unchecked")
	private void initComponentes() {
		pane = new AnchorPane();
		
		// tabela
		tabela = new TableView<Editora>();
		tabela.setItems(geraListaTabela());
		
		clmID = new TableColumn<Editora, Integer>("ID");
		clmNome = new TableColumn<Editora, String>("Nome");
		clmCNPJ = new TableColumn<Editora, String>("CNPJ");
		clmEmail = new TableColumn<Editora, String>("Email");
		clmTelefone = new TableColumn<Editora, String>("Telefone");
		
		for(int i = 0; i < tabela.getItems().size(); i++) {
			clmID.setCellValueFactory(new PropertyValueFactory<>("idEditora"));
			clmNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
			clmCNPJ.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
			clmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
			clmTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
		}
		
		tabela.getColumns().addAll(clmID, clmNome, clmCNPJ, clmEmail, clmTelefone);
		
		pane.getChildren().add(tabela);
		
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
		
		// TABELA
		tabela.setLayoutX(20);
		tabela.setLayoutY(20);
		tabela.setPrefHeight(pane.getPrefHeight() - 80);
		tabela.setPrefWidth(pane.getPrefWidth() - 40);
		
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
		btnRemover.setLayoutX((btnAtualizar.getPrefWidth() + btnCadastrar.getPrefWidth()) + 55);
		btnRemover.setLayoutY(pane.getPrefHeight() - 40);
		btnRemover.setPrefHeight(30);
		btnRemover.setPrefWidth((pane.getPrefWidth() / 4) - 20);
		
		// VOLTAR
		btnVoltar.setLayoutX(pane.getPrefWidth() - 190);
		btnVoltar.setLayoutY(pane.getPrefHeight() - 40);
		btnVoltar.setPrefHeight(30);
		btnVoltar.setPrefWidth((pane.getPrefWidth() / 4) - 30);
	}
	
	private static ObservableList<Editora> geraListaTabela(){
		return FXCollections.observableArrayList(new EditoraDAO().lista());
	}
	
	public static void atualizaTabela() {
		tabela.setItems(geraListaTabela());
	}
	
	private EventHandler<ActionEvent> cadastrar(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					new CadastrarEditoraFX().start(new Stage());
				}catch(Exception e) {
					AlertaFX.alerta("Erro ao iniciar tela de cadastro de editora!");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> atualizar(){
		return new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				TableViewSelectionModel<Editora> selectionModel = tabela.getSelectionModel();
				selectionModel.setSelectionMode(SelectionMode.SINGLE);
				Editora selectedItem = selectionModel.getSelectedItem();
				
				try {
					
					if(selectionModel.isEmpty()) {
						AlertaFX.alerta("Nenhuma editora selecionada!");
						return;
					}
					
					Editora editora = selectedItem;
					
					new AtualizarEditoraFX(editora).start(new Stage());
				}catch(Exception e) {
					AlertaFX.alerta("Erro ao abrir tela de atualização!");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> remover(){
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TableViewSelectionModel<Editora> selectionModel = tabela.getSelectionModel();
				selectionModel.setSelectionMode(SelectionMode.SINGLE);
				ObservableList<Editora> selectedItems = selectionModel.getSelectedItems();
				
				try {
					if(selectedItems.isEmpty()) {
						AlertaFX.alerta("Nenhuma editora selecionada!");
						return;
					}

					Editora editora = new Editora();
					editora.setIdPessoa(selectedItems.get(0).getIdPessoa());
					editora.setIdEditora(selectedItems.get(0).getIdEditora());
					editora.setNome(selectedItems.get(0).getNome());
					editora.setCnpj(selectedItems.get(0).getCnpj());
					editora.setEmail(selectedItems.get(0).getEmail());
					editora.setTelefone(selectedItems.get(0).getTelefone());
					
					new EditoraDAO().remove(editora);
					AlertaFX.info("Editora removida com sucesso!");
					atualizaTabela();
					
				}catch(Exception e) {
					AlertaFX.alerta("Erro ao remover editora!");
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
					AlertaFX.alerta("Erro ao voltar para o menu inicial!");
				}
			}
		};
	}
	
}



















