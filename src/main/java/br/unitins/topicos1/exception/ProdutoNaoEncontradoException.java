package br.unitins.topicos1.exception;

import javax.ws.rs.core.Response;

public class ProdutoNaoEncontradoException extends ServiceException {
    public ProdutoNaoEncontradoException(String message) {
        super(message, Response.Status.NOT_FOUND);
    }
}
