package br.unitins.topicos1.exception;

import javax.ws.rs.core.Response;

public class PerfiNaoEncontradoException extends ServiceException {
    public PerfiNaoEncontradoException(String message) {
        super(message, Response.Status.NOT_FOUND);
    }
}
