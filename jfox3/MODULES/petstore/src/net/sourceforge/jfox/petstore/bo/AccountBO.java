package net.sourceforge.jfox.petstore.bo;

import java.util.List;

import net.sourceforge.jfox.petstore.entity.Account;

/**
 * @author <a href="mailto:jfox.young@gmail.com">Young Yang</a>
 */
public interface AccountBO {
    Account getAccount(String username);

    Account getAccount(String username, String password);

    void updateAccount(Account account);

    void insertAccount(Account account);

    List<String> getUsernameList();
}
