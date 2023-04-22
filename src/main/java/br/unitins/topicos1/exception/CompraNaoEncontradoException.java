package br.unitins.topicos1.exception;

import javax.ws.rs.core.Response;

public class CompraNaoEncontradoException extends ServiceException {
    public CompraNaoEncontradoException(String message) {
        super(message, Response.Status.NOT_FOUND);
    }
}
