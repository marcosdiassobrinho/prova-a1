package br.unitins.topicos1.exception;

import javax.ws.rs.core.Response;

public class AnuncioNaoEncontradoException extends ServiceException {
    public AnuncioNaoEncontradoException(String message) {
        super(message, Response.Status.NOT_FOUND);
    }
}
