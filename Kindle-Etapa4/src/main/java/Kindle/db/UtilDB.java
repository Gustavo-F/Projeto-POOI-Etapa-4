package Kindle.db;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class UtilDB {

	private static Connection conexao;

	private static Connection abrirConexao() {
		
		try {
			Class.forName("org.sqlite.JDBC");
			conexao = DriverManager.getConnection("jdbc:sqlite:Kindle.sqlite");
		}catch(SQLException e) {
			System.err.println("Erro ao abrir conexao.");
		}catch(ClassNotFoundException e2) {
			System.err.println("Biblioteca SQLite não está funcionando corretamente.");
		}
		
		return conexao;
	}
	
	public static Connection getConexao() {
		try {
			if(conexao == null)
				abrirConexao();
			
			if(conexao.isClosed())
				abrirConexao();
			
		}catch(SQLException e) {
			System.err.println("Erro ao obter conexao.");
		}
		
		return conexao;
	}
	
	public static void fecharConexao() {
		if(conexao == null)
			return;
		
		try {
			if(!conexao.isClosed())
				conexao.close();
		}catch(SQLException e) {
			System.err.println("Erro ao fechar conexao.");
		}
	}
	
	public static void alterarDB(String sql) {
		System.out.println("\nÀ ser executado: " + sql);
		
		Connection conexao = getConexao();
		Statement stm = null;
		
		try {
			stm = conexao.createStatement();
			stm.executeUpdate(sql);
			stm.close();
			System.out.println("Executei: " + sql);
		}catch(SQLException e) {
			System.err.println("Erro ao executar query.");
		}
		
	}
	
}
