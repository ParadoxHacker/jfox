package net.sourceforge.jfox.framework.example;

import java.sql.SQLException;
import java.util.List;

import net.sourceforge.jfox.entity.EntityObject;

/**
 * @author <a href="mailto:jfox.young@gmail.com">Young Yang</a>
 */
public interface AccountDAO {

    Account getAccountById(long id) throws SQLException;

    Account getAccountByIdSQL(long id) throws SQLException;

    EntityObject getAccountByIdSQLEntityObject(long id) throws SQLException;

    List<Account> getAllAccounts() throws SQLException;

    Account createAccount(String name, String lastname, String mail) throws SQLException;
}