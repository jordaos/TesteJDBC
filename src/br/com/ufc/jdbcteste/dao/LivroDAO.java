package br.com.ufc.jdbcteste.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.ufc.jdbcteste.pojo.Livro;
import br.com.ufc.jdbcteste.jdbc.ConnectionFactory;

public class LivroDAO {
	// a conexão com o banco de dados
	private Connection connection;

	public LivroDAO() {
		
	}

	public boolean addLivro(Livro livro) {
		String sql = "INSERT INTO livro(titulo, editora, autor) VALUES (?, ?, ?)";
		this.connection = new ConnectionFactory().getConnection();
		try {
			// prepared statement para inserção
			PreparedStatement stmt = connection.prepareStatement(sql);

			// seta os valores
			stmt.setString(1, livro.getTitulo());
			stmt.setString(2, livro.getEditora());
			stmt.setString(3, livro.getAutor());

			int qtdRowsAffected = stmt.executeUpdate();
			stmt.close();
			if (qtdRowsAffected > 0)
				return true;
			return false;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	public ArrayList<Livro> getListLivro() {
		String sql = "SELECT * FROM livro;";
		ArrayList<Livro> listaLivros = new ArrayList<Livro>();
		this.connection = new ConnectionFactory().getConnection();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int id = Integer.parseInt(rs.getString("id"));
				String titulo = rs.getString("titulo");
				String editora = rs.getString("editora");
				String autor = rs.getString("autor");
				
				Livro livro = new Livro(id, titulo, editora, autor);
				
				listaLivros.add(livro);
			}
			stmt.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return listaLivros;
	}

	public Livro getLivroById(int id){
		String sql = "SELECT * FROM livro WHERE id = ?;";
		
		this.connection = new ConnectionFactory().getConnection();
		try {
			// prepared statement para inserção
			PreparedStatement stmt = connection.prepareStatement(sql);

			// seta os valores
			stmt.setInt(1, id);

			// executa
			//stmt.execute();
			
			ResultSet rs = stmt.executeQuery();
			rs.next();

			Livro livro = new Livro(id, rs.getString("titulo"), rs.getString("editora"), rs.getString("autor"));
			
			stmt.close();
			
			return livro;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
