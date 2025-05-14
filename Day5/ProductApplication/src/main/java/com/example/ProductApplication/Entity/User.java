package Day5.ProductApplication.src.main.java.com.example.ProductApplication.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Address> address ;
}
