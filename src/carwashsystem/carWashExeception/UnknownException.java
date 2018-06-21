
package carwashsystem.carWashExeception;

/**
 *
 * @author Joseph
 */
public class UnknownException extends Exception {

  
    public UnknownException() {
    }

    /**
    
     * @param msg the detail message.
     */
    public UnknownException(String msg) {
        super(msg);
    }
}
