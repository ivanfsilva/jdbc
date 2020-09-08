package conexaojdbc;

import org.junit.Test;

import dao.UserDAO;
import model.User;

public class SingleConnectionTest {
	
	@Test
	public void initBanco() {
		UserDAO dao = new UserDAO();
		User user = new User();
		user.setNome("José");
		user.setEmail("jose@email.com");
		
		dao.salvar(user);
	}

}
