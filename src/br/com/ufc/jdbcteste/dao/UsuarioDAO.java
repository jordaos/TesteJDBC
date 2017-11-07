package br.com.ufc.jdbcteste.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.ufc.jdbcteste.jdbc.ConnectionFactory;
import br.com.ufc.jdbcteste.pojo.Usuario;

public class UsuarioDAO {
	private Connection connection;

	public UsuarioDAO() { }
	
	public boolean addUser(Usuario usuario) {
		String sql = "INSERT INTO usuario(nome, email, endereco) VALUES (?, ?, ?)";
		this.connection = new ConnectionFactory().getConnection();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, usuario.getNome());
			stmt.setString(2, usuario.getEmail());
			stmt.setString(3, usuario.getEndereco());
			
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
	
	public ArrayList<Usuario> getListUser() {
		String sql = "SELECT * FROM usuario;";
		ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
		
		this.connection = new ConnectionFactory().getConnection();
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String email = rs.getString("email");
				String endereco = rs.getString("endereco");
				
				Usuario user = new Usuario(id, nome, email, endereco);
				
				listaUsuarios.add(user);
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
		return listaUsuarios;
	}
	
	public boolean deleteUser(int id) {
		String sql = "DELETE FROM usuario WHERE id = ?";
		
		this.connection = new ConnectionFactory().getConnection();

		try {
			PreparedStatement stmt = connection.prepareStatement(sql);

			// seta os valores
			stmt.setInt(1, id);

			int qtdRowsAffected = stmt.executeUpdate();
			stmt.close();
			if (qtdRowsAffected > 0)
				return true;
			return false;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public Usuario getUserById(int id){
		String sql = "SELECT * FROM usuario WHERE id = ?;";

		try {
			// prepared statement para inserção
			PreparedStatement stmt = connection.prepareStatement(sql);

			// seta os valores
			stmt.setInt(1, id);

			// executa
			//stmt.execute();
			
			ResultSet rs = stmt.executeQuery();
			rs.next();

			Usuario usuario = new Usuario(id, rs.getString("email"), rs.getString("nome"), rs.getString("nome"));
			
			stmt.close();
			return usuario;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
}
