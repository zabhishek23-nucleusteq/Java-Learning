package Day5.ProductApplication.src.main.java.com.example.ProductApplication.Repository;

import com.example.ProductApplication.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
}
