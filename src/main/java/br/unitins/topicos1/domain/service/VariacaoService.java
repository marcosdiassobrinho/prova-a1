package br.unitins.topicos1.domain.service;

import br.unitins.topicos1.domain.model.Produto;
import br.unitins.topicos1.domain.model.Variacao;
import br.unitins.topicos1.domain.repository.VariacaoRepository;
import br.unitins.topicos1.dto.AtualizarVariacaoRequestDto;
import br.unitins.topicos1.dto.CompraDto;
import br.unitins.topicos1.dto.VariacaoRequestDto;
import br.unitins.topicos1.exception.VariacaoConflitoException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class VariacaoService {

    @Inject
    VariacaoRepository variacaoRepository;

    @Inject
    ProdutoService produtoService;

    public List<Variacao> processarVariacoes(CompraDto dto) {

        List<Variacao> listaVariacoesDaPaginaProduto = variacaoRepository.buscarVariacoesPorAnuncio(dto.getIdAnuncio());

        List<Variacao> variacoesCompradas = dto.getIdVariacoes().stream()
                .map(idVariacao -> listaVariacoesDaPaginaProduto.stream()
                        .filter(variacao -> variacao.getId().equals(idVariacao))
                        .findFirst()
                        .orElse(null))
                .filter(Objects::nonNull)
                .filter(variacao -> variacao.getQuantidadeEstoque() >= 1)
                .peek(variacao -> {
                    var quantidadeEstoque = variacao.getQuantidadeEstoque();
                    variacao.setQuantidadeEstoque(quantidadeEstoque - 1);
                })
                .collect(Collectors.toList());

        if (variacoesCompradas.isEmpty()) {
            throw new VariacaoConflitoException("Produto sem estoque!");
        }

        variacoesCompradas.forEach(variacaoRepository::persist);
        return variacoesCompradas;
    }

    public void salvar(Variacao variacao) {
        variacaoRepository.persist(variacao);
    }

    public Variacao buscarPorId(Long id) {
        return variacaoRepository.buscarVariacaoPorId(id);
    }

    public List<Variacao> buscarVariacoesPorCompraId(Long idCompra) {
        return variacaoRepository.buscarVariacoesPorCompraId(idCompra);
    }

    @Transactional
    public void criarVariacao(VariacaoRequestDto dto, Produto produto) {
        Variacao variacao = new Variacao();
        variacao.setPeso(dto.getPeso());
        variacao.setQuantidadeEstoque(dto.getQuantidadeEstoque());
        variacao.setValorBruto(dto.getValorBruto());
        variacao.setValorLiquido(deduzirTaxa(dto.getValorBruto()));
        variacao.setUsado(dto.isUsado());
        variacao.setDescricao(dto.getDescricao());
        salvar(variacao);
    }

    public List<Variacao> criarVariacoes(List<VariacaoRequestDto> dto) {
        List<Variacao> variacoes = new ArrayList<>();
        for (VariacaoRequestDto var : dto) {
            Variacao variacao = new Variacao();
            variacao.setPeso(var.getPeso());
            variacao.setDescricao(var.getDescricao());
            variacao.setUsado(var.isUsado());
            variacao.setValorBruto(var.getValorBruto());
            variacao.setValorLiquido(deduzirTaxa(var.getValorBruto()));
            variacao.setQuantidadeEstoque(var.getQuantidadeEstoque());
            salvar(variacao);
            variacoes.add(variacao);
        }
        return variacoes;
    }

    public void atualizarVariacao(Long VariacadoId, AtualizarVariacaoRequestDto dto) {
        Variacao variacao = buscarPorId(VariacadoId);

        if (dto.getDecricao() != null) {
            variacao.setDescricao(dto.getDecricao());
        }

        if (dto.getQuantidadeEstoque() != null) {
            variacao.setQuantidadeEstoque(dto.getQuantidadeEstoque());
        }

        if (dto.getValorBruto() != null) {
            variacao.setValorBruto(dto.getValorBruto());
            variacao.setValorLiquido(deduzirTaxa(dto.getValorBruto()));
        }
        salvar(variacao);
    }

    private Double deduzirTaxa(Double valor) {
        return valor * 0.95;
    }

    @Transactional
    public void deletarVariacao(Long idVariacao, Long idProduto) {
        Produto produto = produtoService.buscarPorId(idProduto);
        Variacao variacao = buscarPorId(idVariacao);
        if (!produto.getVariacao().contains(variacao)) {
            throw new VariacaoConflitoException("Produto não possui a variacao!");
        }
        produto.getVariacao().remove(variacao);
        delete(variacao);
    }

    public void delete(Variacao variacao) {
        variacaoRepository.delete(variacao);
    }

}
