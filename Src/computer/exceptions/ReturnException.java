package computer.exceptions;

//return exception will pass an instance of our generic object class out of the function
public class ReturnException extends Exception {

    public Object value;

    public ReturnException(Object value) {
        this.value = value;
    }
    
}
