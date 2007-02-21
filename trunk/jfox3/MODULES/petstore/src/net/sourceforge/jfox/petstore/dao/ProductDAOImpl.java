package net.sourceforge.jfox.petstore.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;

import net.sourceforge.jfox.petstore.entity.Product;
import net.sourceforge.jfox.entity.dao.DAOSupport;
import net.sourceforge.jfox.entity.EntityManagerExt;
import net.sourceforge.jfox.util.VelocityUtils;

/**
 * @author <a href="mailto:yang_y@sysnet.com.cn">Young Yang</a>
 */
@NamedNativeQueries(
        {
        @NamedNativeQuery(
                name = ProductDAOImpl.GET_PRODUCT,
                query = "select productid, name, descn, category from product where productid = $id",
                resultClass = Product.class
        ),
        @NamedNativeQuery(
                name = ProductDAOImpl.GET_PRODUCT_LIST_BY_CATEGORY,
                query = "select productid, name, descn, category from product where category = $categoryId",
                resultClass = Product.class
        ),
        @NamedNativeQuery(
                name = ProductDAOImpl.SEARCH_PRODUCT,
                query = "select productid, name, descn, category from product " +
                        "#if($keywords.size() > 0)" +
                        "where 0!=0 " +
                        "#foreach($word in $keywords)" +
                        " OR lower(name) like $word " +
                        " OR lower(category) like $word " +
                        " OR lower(descn) like $word " +
                        "#end" +
                        "#end", // $p1 is keyword array
                resultClass = Product.class
        )

                }
)
@Stateless
@Local
public class ProductDAOImpl extends DAOSupport implements ProductDAO {

    public final static String GET_PRODUCT = "getProduct";
    public final static String GET_PRODUCT_LIST_BY_CATEGORY = "getProductListByCategory";
    public final static String SEARCH_PRODUCT = "searchProduct";


    @PersistenceContext(unitName = "JPetstoreMysqlDS")
    EntityManager em;

    protected EntityManagerExt getEntityManager() {
        return (EntityManagerExt)em;
    }

// 容器外测试的时候使用
/*
    protected EntityManagerExt getEntityManager() {
        return (EntityManagerExt)Persistence.createEntityManagerFactory("JPetstoreMysqlDS").createEntityManager();
    }
*/

    public Product getProduct(String productId) throws SQLException {
        return (Product)createNamedNativeQuery(GET_PRODUCT).setParameter("id", productId).getSingleResult();
    }

    public List<Product> getProductListByCategory(String categoryId) throws SQLException {
        return (List<Product>)createNamedNativeQuery(GET_PRODUCT_LIST_BY_CATEGORY).setParameter("categoryId", categoryId).getResultList();
    }

    public List<Product> searchProductList(String[] keywords) throws SQLException {
        // 将 keywords 作为一个参数对象，否则，JDK默认会使用变参(varargs)
        return (List<Product>)createNamedNativeQuery(SEARCH_PRODUCT).setParameter("keywords", Arrays.asList(keywords)).getResultList();
    }

    public static void main(String[] args) {
                String query = "select productid, name, descn, category from product " +
                        "#if(!$keywords.isEmpty())" +
                        "where 0!=0 " +
                        "#foreach($word in $keywords)" +
                        " OR lower(name) like \"%$word%\" " +
                        " OR lower(category) like \"%$word%\" " +
                        " OR lower(descn) like \"%$word%\"" +
                        "#end" +
                        "#end"; // $p1 is keyword array
        Map<String,Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("keywords",new String[]{"a","b"});
        String result = VelocityUtils.evaluate(query, parameterMap);
        System.out.println(result);
    }
}
