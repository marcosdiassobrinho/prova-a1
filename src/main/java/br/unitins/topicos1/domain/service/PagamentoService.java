package br.unitins.topicos1.domain.service;

import br.unitins.topicos1.domain.model.enums.FormaPagamento;
import br.unitins.topicos1.domain.model.enums.StatusPagamento;
import br.unitins.topicos1.domain.model.Compra;
import br.unitins.topicos1.domain.model.Pagamento;
import br.unitins.topicos1.domain.model.Perfil;
import br.unitins.topicos1.domain.repository.PagamentoRepository;
import br.unitins.topicos1.exception.PagamentoConflitoException;
import br.unitins.topicos1.exception.PagamentoNaoEncontradoException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

import static java.time.LocalDateTime.now;

@ApplicationScoped
public class PagamentoService {
    @Inject
    PagamentoRepository pagamentoRepository;

    public Pagamento buscarPorId(Long id) {
        try {
            return pagamentoRepository.buscarPagamentoPorId(id);
        } catch (NotFoundException e) {
            throw new PagamentoNaoEncontradoException("Pagamento não encontrado.");
        }
    }

    public boolean pagamentoAtrasadoExiste(Perfil perfil) {
        return pagamentoRepository.pagamentoEmAtraso(perfil);
    }

    public Pagamento salvar(FormaPagamento formaPagamento, Compra compra) {
        Pagamento pagamento = new Pagamento();
        pagamento.setDataEmissao(now());
        pagamento.setFormaPagamento(formaPagamento);
        pagamentoRepository.persist(pagamento);

        definirStatusPagamento(pagamento);

        compra.setPagamento(pagamento);
        return pagamento;
    }

    private void definirStatusPagamento(Pagamento pagamento) {
        switch (pagamento.getFormaPagamento()) {
            case BOLETO, TRANSFERENCIA, PIX -> pagamento.setStatusPagamento(StatusPagamento.PENDENTE);
            case CARTAO_CREDITO, CARTAO_DEBITO -> pagamento.setStatusPagamento(StatusPagamento.EM_ANALISE);
            default ->
                    throw new PagamentoConflitoException("Forma de pagamento inválida: " + pagamento.getFormaPagamento());
        }
    }

    public Pagamento validarPagamentoParcelado(Compra compra) {
        Pagamento pagamento = buscarPorId(compra.getPagamento().getId());

        if (pagamento.getFormaPagamento() != FormaPagamento.CARTAO_CREDITO) {
            throw new PagamentoConflitoException("Forma de pagamento inválida:");
        }
        return pagamento;
    }
}
