package net.sourceforge.jfox.framework.example;

import javax.ejb.EJB;

import net.sourceforge.jfox.framework.example.AddressDAO;

/**
 * @author <a href="mailto:jfox.young@gmail.com">Young Yang</a>
 */
public class AddressBOImpl implements AddressBO{

    @EJB
    AddressDAO addressDAO;

    public static void main(String[] args) {

    }
}