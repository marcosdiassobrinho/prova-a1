package br.unitins.topicos1.exception;

import javax.ws.rs.core.Response;

public class PerfilConflitoException extends ServiceException {
    public PerfilConflitoException(String message) {
        super(message, Response.Status.CONFLICT);
    }
}
