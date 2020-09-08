package conexaojdbc;

import org.junit.Test;

import dao.UserDAO;
import model.User;

public class SingleConnectionTest {
	
	@Test
	public void initBanco() {
		UserDAO dao = new UserDAO();
		User user = new User();
		user.setId(4L);
		user.setNome("Matheus");
		user.setEmail("matheus@email.com");
		
		dao.salvar(user);
	}

}
