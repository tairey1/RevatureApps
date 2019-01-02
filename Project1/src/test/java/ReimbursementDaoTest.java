import static org.junit.Assert.*;

import org.junit.Test;

import com.revature.dao.ReimbursementDao;

public class ReimbursementDaoTest {

	public static ReimbursementDao rDao = new ReimbursementDao();
	
	@Test
	public void test() {
		assertNotNull(rDao.findAll());
		assertNotNull(rDao.findByID(127));
	}

}
