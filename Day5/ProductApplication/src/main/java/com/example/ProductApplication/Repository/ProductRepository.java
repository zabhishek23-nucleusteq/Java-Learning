package Day5.ProductApplication.src.main.java.com.example.ProductApplication.Repository;

import com.example.ProductApplication.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
}
