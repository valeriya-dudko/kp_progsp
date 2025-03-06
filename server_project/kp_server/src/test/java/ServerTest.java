import kp.dao.CompanyDao;
import kp.model.Company;
import kp.model.Good;
import kp.service.GoodService;
import kp.util.ConnectionFactory;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServerTest {

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    int st;

    CompanyDao compDao = new CompanyDao();
    static Company company = new Company();

    @Test
    @Order(1)
    public void testInsert() {
        company.setName("testt");
        company.setAddress("test");

        try
        {
            compDao.insert(company);
        }
        catch (Exception e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    @Order(2)
    public void testUpdate()
    {
        company = compDao.searchByName(company.getName());
        company.setName("test");

        try
        {
            compDao.update(company);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Order(3)
    public void testSearch()
    {
        String address = compDao.searchByName(company.getName()).getAddress();

        Assertions.assertEquals("test", address);
    }

    @Test
    @Order(4)
    public void testDelete()
    {
        company = compDao.searchByName(company.getName());

        try
        {
            compDao.delete(company.getId());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Order(5)
    public void testCalcTax()
    {
        Good good = new Good("test",10, 10, 10);
        good.setIsImport(true);
        good.setRateType(1);
        good.setRate(10);
        good.setVAT(10);
        good.setExcise(10);

        double tax = GoodService.calcTax(good);

        Assertions.assertEquals(tax, 176, 1);
    }
}
