package br.unitins.topicos1.exception;

import javax.ws.rs.core.Response;

public class AvaliacaoCompraNaoEncontradaException extends ServiceException {
    public AvaliacaoCompraNaoEncontradaException(String message) {
        super(message, Response.Status.NOT_FOUND);
    }
}
