package br.unitins.topicos1.exception;

import javax.ws.rs.core.Response;

public class MarcaNaoEncontradaException extends ServiceException {
    public MarcaNaoEncontradaException(String message) {
        super(message, Response.Status.NOT_FOUND);
    }
}
