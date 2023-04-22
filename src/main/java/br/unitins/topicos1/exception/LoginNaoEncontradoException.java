package br.unitins.topicos1.exception;

import javax.ws.rs.core.Response;

public class LoginNaoEncontradoException extends ServiceException {
    public LoginNaoEncontradoException(String message) {
        super(message, Response.Status.NOT_FOUND);
    }
}
