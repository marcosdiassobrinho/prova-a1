package br.unitins.topicos1.exception;

import javax.ws.rs.core.Response;

public class PagamentoConflitoException extends ServiceException {
    public PagamentoConflitoException(String message) {
        super(message, Response.Status.CONFLICT);
    }
}
