package br.unitins.topicos1.domain.service;

import br.unitins.topicos1.domain.model.Compra;
import br.unitins.topicos1.domain.model.enums.StatusPagamento;
import br.unitins.topicos1.domain.model.enums.StatusParcela;
import br.unitins.topicos1.domain.model.Pagamento;
import br.unitins.topicos1.domain.model.Parcela;
import br.unitins.topicos1.domain.repository.ParcelaRepository;
import br.unitins.topicos1.exception.ParcelaNaoEncontradoException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

@ApplicationScoped
public class ParcelaService {
    @Inject
    ParcelaRepository parcelaRepository;
    @Inject
    PagamentoService pagamentoService;

    public Parcela buscarPorId(Long id) {
        return parcelaRepository.buscarParcelaPorId(id)
                .orElseThrow(() -> new ParcelaNaoEncontradoException("Parcela não encontrada."));
    }

    public void compraParcelada(Compra compra, Pagamento pagamento, int parcelasQuant) {
        double valorBruto = compra.getValor();
        double totalParcelas = criarParcelas(parcelasQuant, pagamento, valorBruto);

        pagamentoService.validarPagamentoParcelado(compra);
        if (parcelasQuant > 3) {
            valorBruto = totalParcelas;
        }
        compra.setValor(valorBruto);
    }

    private double criarParcelas(int parcelasQuant, Pagamento pagamento, double valorTotal) {
        double parcelaValor = valorTotal / parcelasQuant;
        final double juros = 1.02;

        List<Parcela> parcelas = IntStream.range(0, parcelasQuant)
                .mapToObj(i -> {
                    Parcela parcela = new Parcela();
                    LocalDateTime dataParcela = LocalDateTime.now().plusMonths(i + 1);
                    parcela.setDataVencimento(dataParcela);
                    parcela.setStatus(StatusParcela.PENDENTE);
                    parcela.setNumero(i + 1);
                    double valorParcela;
                    if (i >= 3) {
                        valorParcela = parcelaValor * Math.pow(juros, i - 2);
                    } else {
                        valorParcela = parcelaValor;
                    }
                    parcela.setValor(valorParcela);
                    parcela.setPagamento(pagamento);
                    parcelaRepository.persist(parcela);
                    return parcela;
                })
                .toList();

        double totalParcelas = parcelas.stream()
                .mapToDouble(Parcela::getValor)
                .sum();

        parcelas.forEach(parcelaRepository::persist);

        return totalParcelas;
    }

    public Parcela buscarPrimeiraParcelaPendente(Pagamento pagamento) {
        return parcelaRepository.buscarPrimeiraParcelaPendente(pagamento);
    }

    public void salvar(Parcela parcela) {
        parcelaRepository.persist(parcela);
    }

    private Long quantidadeParcelasPendentes(Pagamento pagamento) {
        return parcelaRepository.quantidadeParcelasPendentes(pagamento);
    }

    public void atualizarStatusPagamentoDeAcordoComParcelasPendentes(Pagamento pagamento) {
        if (quantidadeParcelasPendentes(pagamento) == 0) {
            pagamento.setStatusPagamento(StatusPagamento.APROVADO);
        } else {
            pagamento.setStatusPagamento(StatusPagamento.PAGAMENTO_PARCIAL);
        }

    }
}
