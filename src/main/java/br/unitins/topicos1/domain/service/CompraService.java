package br.unitins.topicos1.domain.service;

import br.unitins.topicos1.domain.model.*;
import br.unitins.topicos1.domain.model.enums.FormaPagamento;
import br.unitins.topicos1.domain.repository.CompraRepository;
import br.unitins.topicos1.dto.CompraResponseDto;
import br.unitins.topicos1.dto.CompraDto;
import br.unitins.topicos1.exception.AnuncioConflitoException;
import br.unitins.topicos1.exception.CompraNaoEncontradoException;
import br.unitins.topicos1.exception.PagamentoConflitoException;
import br.unitins.topicos1.exception.PerfilConflitoException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;


@ApplicationScoped
public class CompraService {
    @Inject
    VariacaoService variacaoService;
    @Inject
    CompraRepository compraRepository;
    @Inject
    PagamentoService pagamentoService;
    @Inject
    AnuncioService anuncioService;
    @Inject
    ParcelaService parcelaService;
    @Inject
    PerfilService perfilService;

    @Transactional
    public void realizarTransacao(CompraDto dto) {
        Anuncio anuncio = anuncioService.buscarPorId(dto.getIdAnuncio());
        Perfil perfil = perfilService.buscarPerfilCompleto(dto.getIdPerfil());

        Compra compraValida = processarCompra(dto, anuncio, perfil);

        Pagamento pagamento = pagamentoService.salvar(dto.getFormaPagamento(), compraValida);

        if (pagamento.getFormaPagamento() == FormaPagamento.CARTAO_CREDITO && dto.getNumeroParcelas() > 0) {
            parcelaService.compraParcelada(compraValida, pagamento, dto.getNumeroParcelas());
        }
        salvar(compraValida);
    }

    private void validarCompra(Anuncio anuncio, Perfil perfil) {
        if (perfil == anuncio.getPerfil()) {
            throw new PerfilConflitoException("Perfil invalido.");
        }
        if (!anuncio.isAtivo()) {
            throw new AnuncioConflitoException("Anuncio não está ativo.");
        }
        if (pagamentoService.pagamentoAtrasadoExiste(perfil)) {
            throw new PagamentoConflitoException("Pagamento em atraso.");
        }
    }

    public Compra buscarPorId(Long id) {
        try {
            return compraRepository.buscarCompraPorId(id);
        } catch (NoResultException e) {
            throw new CompraNaoEncontradoException("Compra não encontrada.");
        }
    }

    public Compra processarCompra(CompraDto dto, Anuncio anuncio, Perfil perfil) {
        validarCompra(anuncio, perfil);
        Compra compra = new Compra();
        compra.setPerfil(perfil);
        compra.setAnuncio(anuncio);

        List<Variacao> variacoesValidadas = variacaoService.processarVariacoes(dto);

        double valorTotal = variacoesValidadas.stream()
                .mapToDouble(Variacao::getValorBruto)
                .sum();

        compra.setQuantidadeCompra(variacoesValidadas.size());
        compra.setVariacao(variacoesValidadas);
        compra.setValor(valorTotal);
        return compra;
    }

    public List<CompraResponseDto> buscarCompras(Long idPerfil) {
        List<CompraResponseDto> compras = compraRepository.buscarCompras(idPerfil);
        for (CompraResponseDto compraResponseDto : compras) {
            compraResponseDto.setVariacoes(variacaoService.buscarVariacoesPorCompraId(compraResponseDto.getId()));

        }
        return compras;
    }

    public CompraResponseDto buscarCompra(Long id) {
        try {
            CompraResponseDto compra = compraRepository.buscarCompra(id);
            compra.setVariacoes(variacaoService.buscarVariacoesPorCompraId(id));
            return compra;
        } catch (NoResultException e) {
            throw new CompraNaoEncontradoException("Compra não encontrada.");
        }
    }


    public void salvar(Compra compra) {
        compraRepository.persist(compra);
    }
}
