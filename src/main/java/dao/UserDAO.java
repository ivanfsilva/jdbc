package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexaojdbc.SingleConnection;
import model.BeanUserFone;
import model.Telefone;
import model.User;

public class UserDAO {
	
	private Connection connection;

	public UserDAO() {
		connection = SingleConnection.getConnection();
	}
	
	public void salvar (User user) {
		String sql = "INSERT INTO userJdbc (nome, email) VALUES (?, ?);";
		
		try {
			PreparedStatement insert = connection.prepareStatement(sql);
			
			insert.setString(1, user.getNome());
			insert.setString(2, user.getEmail());
			insert.execute();
			
			connection.commit();
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	public void salvarTelefone(Telefone telefone) {
		try {
			
			String sql = "INSERT INTO public.telefoneuser(numero, tipo, usuariopessoa) VALUES (?, ?, ?);";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, telefone.getNumero());
			statement.setString(2, telefone.getTipo());
			statement.setLong(3, telefone.getUsuario());
			
			statement.execute();
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public List<User> listar() throws SQLException {
		List<User> list = new ArrayList<User>();
		
		String sql = "SELECT * FROM userJdbc";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) {
			User user = new User();
			user.setId(resultado.getLong("id"));
			user.setNome(resultado.getString("nome"));
			user.setEmail(resultado.getString("email"));
			
			list.add(user);
		}
		
		return list;
		
	}
	
	public List<BeanUserFone> listaUserFone (Long idUser) {
		List<BeanUserFone> beanUserFones = new ArrayList<>();
		
		String sql = " SELECT nome, numero, email FROM telefoneuser AS fone ";
		sql += " INNER JOIN userJdbc as U";
		sql += " ON fone.usuariopessoa = u.id ";
		sql += " WHERE u.id = " + idUser;
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				BeanUserFone userFone = new BeanUserFone();
				
				userFone.setEmail(resultSet.getString("email"));
				userFone.setNome(resultSet.getString("nome"));
				userFone.setNumero(resultSet.getString("numero"));
				
				beanUserFones.add(userFone);
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		
		return beanUserFones;
	}
	
	public User buscar(Long id) throws SQLException {
		User retorno = new User();
		
		String sql = "SELECT * FROM userJdbc WHERE id = " + id;
		
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		
		while (resultado.next()) {
			retorno.setId(resultado.getLong("id"));
			retorno.setNome(resultado.getString("nome"));
			retorno.setEmail(resultado.getString("email"));
		}
		
		return retorno;
	}
	
	public void atualizar (User user) {
		String sql = "UPDATE userJdbc SET nome = ? WHERE Id = " + user.getId();
		
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, user.getNome());
			
			statement.execute();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	public void deletar(Long id) {
		try {
			
			String sql = "DELETE FROM userJdbc WHERE id = " + id;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.execute();
			
			connection.commit();
		} catch (Exception e) {
			try {
				connection.rollback();
				e.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public void deleteFonesPorUser(Long idUser) {
		try {
			String sqlFone = "DELETE FROM telefoneuser WHERE usuariopessoa = " + idUser;
			String sqlUser = "DELETE FROM userJdbc WHERE id = " + idUser;
			
			PreparedStatement statement = connection.prepareStatement(sqlFone);
			statement.executeUpdate();
			connection.commit();
			
			statement = connection.prepareStatement(sqlUser);
			statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

}
