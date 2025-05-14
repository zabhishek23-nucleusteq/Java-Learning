package Day5.ProductApplication.src.main.java.com.example.ProductApplication.Exception;

public class InvalidProductException extends RuntimeException{
    public InvalidProductException(String message)
    {
        super(message);
    }
}
