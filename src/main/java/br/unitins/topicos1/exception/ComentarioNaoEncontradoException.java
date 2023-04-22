package br.unitins.topicos1.exception;

import javax.ws.rs.core.Response;

public class ComentarioNaoEncontradoException extends ServiceException {
    public ComentarioNaoEncontradoException(String message) {
        super(message, Response.Status.NOT_FOUND);
    }
}
