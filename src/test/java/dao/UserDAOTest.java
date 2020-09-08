package dao;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import model.Telefone;
import model.User;

public class UserDAOTest {
	
	@Test
	public void userListar() {
		UserDAO dao = new UserDAO();
		
		try {
			List<User> list = dao.listar();
			
			for (User user : list) {
				System.out.println(user);
				System.out.println("---------------------------------------------------------------------");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void userBuscar() {
		UserDAO dao = new UserDAO();
		
		try {
			User user = dao.buscar(4L);
			System.out.println(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void userAtualizar() {
		try {
			UserDAO dao = new UserDAO();
			
			User userBanco = dao.buscar(4L);
			userBanco.setNome("Nome atualizado com o método atualizar");
			dao.atualizar(userBanco);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void userDeletar() {
		
		try {
			
			UserDAO dao = new UserDAO();
			dao.deletar(5L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void insertTelefone() {
		Telefone telefone = new Telefone();
		telefone.setNumero("(87) 4444-5555");
		telefone.setTipo("Casa");
		telefone.setUsuario(3L);
		
		UserDAO dao = new UserDAO();
		dao.salvarTelefone(telefone);
	}

}
