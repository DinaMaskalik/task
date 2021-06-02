package task.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import task.entity.Product;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, String> {

    @Query("select p.url from Product p " +
            "where p.category = ?1 and p.manufacture = ?2 and " +
            "p.model = ?3 and (?4 between p.fromPrice and p.toPrice)")
    List<String> findUrl(String town, String town1, String town2, Double a);

}
