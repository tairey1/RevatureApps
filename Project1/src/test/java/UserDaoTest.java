import static org.junit.Assert.*;

import org.junit.Test;

import com.revature.dao.UserDao;

public class UserDaoTest {

	public static UserDao uDao = new UserDao();
	
	@Test
	public void test() {
		assertNotNull(uDao.findByID(1));
		assertNotNull(uDao.findAll());
	}

}
