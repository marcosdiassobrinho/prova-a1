package br.unitins.topicos1.exception;

import javax.ws.rs.core.Response;

public class ParcelaNaoEncontradoException extends ServiceException {
    public ParcelaNaoEncontradoException(String message) {
        super(message, Response.Status.NOT_FOUND);
    }
}
