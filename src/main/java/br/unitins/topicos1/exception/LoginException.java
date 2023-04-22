package br.unitins.topicos1.exception;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

public class LoginException extends ServiceException {
    public LoginException(String message, Response.Status status) {
        super(message, status);
    }
}
