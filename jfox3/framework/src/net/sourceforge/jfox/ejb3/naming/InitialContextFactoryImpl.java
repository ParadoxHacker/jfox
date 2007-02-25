package net.sourceforge.jfox.ejb3.naming;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.ObjectFactory;

/**
 * @author <a href="mailto:jfox.young@gmail.com">Young Yang</a>
 */
public class InitialContextFactoryImpl implements InitialContextFactory, ObjectFactory {

    private static Context context = null;

    public static void setInitialContext(Context ctx) {
        context = ctx;
    }

    public Context getInitialContext(Hashtable<?,?> env) throws NamingException {
        if(context == null) {
            throw new NamingException("Naming Service not initialzed.");
        }
        return context;
    }

    public Object getObjectInstance(Object obj, Name name, Context ctx, Hashtable environment) throws Exception {
//        will perform after urlContextFactory failed and if set Context.OBJECT_FACTORY
        throw new UnsupportedOperationException("getObjectInstance");
    }
    
    public static void main(String[] args) {

    }
}
