package br.unitins.topicos1.exception;

import javax.ws.rs.core.Response;

public class VariacaoConflitoException extends ServiceException {
    public VariacaoConflitoException(String message) {
        super(message, Response.Status.CONFLICT);
    }
}
