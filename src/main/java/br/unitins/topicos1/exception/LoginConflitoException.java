package br.unitins.topicos1.exception;

import javax.ws.rs.core.Response;

public class LoginConflitoException extends ServiceException {
    public LoginConflitoException(String message) {
        super(message, Response.Status.CONFLICT);
    }
}
