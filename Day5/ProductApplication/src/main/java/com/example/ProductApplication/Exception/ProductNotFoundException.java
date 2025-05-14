package Day5.ProductApplication.src.main.java.com.example.ProductApplication.Exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message)
    {
        super(message);
    }
}
