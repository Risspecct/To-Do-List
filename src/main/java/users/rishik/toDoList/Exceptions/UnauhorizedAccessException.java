package users.rishik.toDoList.Exceptions;

public class UnauhorizedAccessException extends RuntimeException{
    public UnauhorizedAccessException(String message){
        super(message);
    }
}
