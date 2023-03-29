package peaksoft.exceptiron;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class BadCredentialException extends RuntimeException{
    public BadCredentialException(){

    }
    public BadCredentialException(String message){
        super(message);
    }
}
