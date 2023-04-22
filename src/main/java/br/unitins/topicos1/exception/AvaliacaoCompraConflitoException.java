package br.unitins.topicos1.exception;

import javax.ws.rs.core.Response;

public class AvaliacaoCompraConflitoException extends ServiceException {
    public AvaliacaoCompraConflitoException(String message) {
        super(message, Response.Status.CONFLICT);
    }
}
