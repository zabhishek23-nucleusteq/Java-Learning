package Day5.ProductApplication.src.main.java.com.example.ProductApplication.Controller;
import com.example.ProductApplication.DTO.ProductDto;
import com.example.ProductApplication.Exception.InvalidProductException;
import com.example.ProductApplication.Exception.ProductNotFoundException;
import com.example.ProductApplication.Service.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final IProductService iProductService;

    public ProductController(IProductService iProductService) {
        this.iProductService = iProductService;
    }
    public ResponseEntity<?> saveProduct(ProductDto productDto)
    {
        try{
            String message = iProductService.saveProduct(productDto);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        catch (InvalidProductException e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllProducts()
    {
        {
            try{
                return new ResponseEntity<>(iProductService.getAllProducts(), HttpStatus.CREATED);
            }
            catch (InvalidProductException e)
            {
                return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@RequestParam int id)
    {
        {
            try{
                return new ResponseEntity<>(iProductService.getProductById(id), HttpStatus.CREATED);
            }
            catch (ProductNotFoundException e)
            {
                return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfile(@PathVariable int productId,@RequestBody ProductDto productDto)
    {
        try {
            ProductDto updated = iProductService.updateProduct(productId, productDto);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (ProductNotFoundException | InvalidProductException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>("Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        try {
            String msg = iProductService.deleteProduct(id);
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } catch (ProductNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("Unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
