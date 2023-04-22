package br.unitins.topicos1.exception;

import javax.ws.rs.core.Response;

public class BatePapoNaoEncontradoException extends ServiceException {
    public BatePapoNaoEncontradoException(String message) {
        super(message, Response.Status.NOT_FOUND);
    }
}
