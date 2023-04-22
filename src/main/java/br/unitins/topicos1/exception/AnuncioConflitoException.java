package br.unitins.topicos1.exception;

import javax.ws.rs.core.Response;

public class AnuncioConflitoException extends ServiceException {
    public AnuncioConflitoException(String message) {
        super(message, Response.Status.CONFLICT);
    }
}
