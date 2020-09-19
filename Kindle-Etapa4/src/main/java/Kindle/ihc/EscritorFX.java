package Kindle.ihc;

import Kindle.entidades.Usuario;
import Kindle.entidades.Escritor;
import Kindle.db.EscritorDAO;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

@SuppressWarnings("unused")
public class EscritorFX extends Application{

	private Usuario usuarioLogado;
	private Stage stage;
	private Pane pane;
	private static TableView<Escritor> tabela;
	private TableColumn<Escritor, Integer> clmID;
	private TableColumn<Escritor, String> clmNome;
	private TableColumn<Escritor, String> clmSobrenome;
	private TableColumn<Escritor, String> clmCpf;
	private TableColumn<Escritor, String> clmTelefone;
	private TableColumn<Escritor, String> clmEmail;
	private Button btnCadastrar;
	private Button btnAtualizar;
	private Button btnRemover;
	private Button btnVoltar;
	
	public EscritorFX(Usuario usuarioLogado) {
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
		stage.setTitle("Escritores");
		stage.setResizable(false);
		stage.show();
	}
	
	@SuppressWarnings("unchecked")
	private void initComponentes() {
		
		pane = new AnchorPane();
		
		// Tableview
		tabela = new TableView<Escritor>();
		tabela.setItems(geraListaTabela());
		
		clmID = new TableColumn<Escritor, Integer>("ID");
		clmNome = new TableColumn<Escritor, String>("Nome");
		clmSobrenome = new TableColumn<Escritor, String>("Sobrenome");
		clmCpf = new TableColumn<Escritor, String>("CPF");
		clmTelefone = new TableColumn<Escritor, String>("Telefone");
		clmEmail = new TableColumn<Escritor, String>("Email");
		
		for(int i = 0; i < tabela.getItems().size(); i++) {
			
			String teste = tabela.getItems().get(i).getCPF();
			
			clmID.setCellValueFactory(new PropertyValueFactory<>("idEscritor"));
			clmNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
			clmSobrenome.setCellValueFactory(new PropertyValueFactory<>("sobrenome"));
			
			clmCpf.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Escritor,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Escritor, String> param) {
					return new SimpleStringProperty(param.getValue().getCPF());
				}
			});
			
			clmTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
			clmEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		}
		
		tabela.getColumns().addAll(clmID, clmNome, clmSobrenome, clmCpf, clmTelefone, clmEmail);
		
		pane.getChildren().addAll(tabela);
		
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
		btnAtualizar.setLayoutX(btnCadastrar.getPrefWidth()  + 35);
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
	
	private static ObservableList<Escritor> geraListaTabela(){
		return FXCollections.observableArrayList(new EscritorDAO().lista());
	}
	
	public static void atualizaTabela() {
		tabela.setItems(geraListaTabela());
	}
	
	private EventHandler<ActionEvent> cadastrar(){
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					new CadastrarEscritorFX().start(stage);
				}catch(Exception e) {
					AlertaFX.alerta("Erro ao iniciar tela de cadastro de escritores!");
				}
			}
		};
	}
	
	
	
	private EventHandler<ActionEvent> atualizar(){
		return new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				TableViewSelectionModel<Escritor> selectionModel = tabela.getSelectionModel();
				selectionModel.setSelectionMode(SelectionMode.SINGLE);
				Escritor escritor = selectionModel.getSelectedItem();
				
				try {
					
					if(selectionModel.isEmpty()) {
						AlertaFX.alerta("Selecione um escritor");
						return;
					}
					
					new AtualizarEscritorFX(escritor).start(new Stage());
					
				}catch(Exception e) {
					AlertaFX.alerta("Erro ao abrir tela de atualiação de escritor!");
				}
			}
		};
	}
	
	private EventHandler<ActionEvent> remover(){
		return new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				TableViewSelectionModel<Escritor> selectionModel = tabela.getSelectionModel();
				selectionModel.setSelectionMode(SelectionMode.SINGLE);
				ObservableList<Escritor> selectedItems = selectionModel.getSelectedItems();
						
				try {
					if(selectedItems.isEmpty()) {
						AlertaFX.alerta("Nenhum escritor selecionado!");
						return;
					}
					
					Escritor escritor = new Escritor();
					escritor.setIdEscritor(selectedItems.get(0).getIdEscritor());
					escritor.setIdPessoa(selectedItems.get(0).getIdPessoa());
	
					new EscritorDAO().remove(escritor);
					AlertaFX.info("Escritor removido com sucesso!");	
					atualizaTabela();
					
					return;
				}catch(Exception e) {
					AlertaFX.alerta("Erro ao deletar escritor!");
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
					AlertaFX.alerta("Erro ao iniciar menu inicial!");
				}
			}
			
		};
	}

}
