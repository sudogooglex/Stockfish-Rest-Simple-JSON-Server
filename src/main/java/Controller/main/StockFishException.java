package Controller.main;

public class StockFishException extends Exception {
    String exceptionString;
    
    public StockFishException(String es) {
        exceptionString = es;
    }
    
    @Override
    public String toString() { 
    return exceptionString;
} 
}
