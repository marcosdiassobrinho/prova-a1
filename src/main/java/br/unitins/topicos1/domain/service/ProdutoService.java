package br.unitins.topicos1.domain.service;

import br.unitins.topicos1.domain.model.Anuncio;
import br.unitins.topicos1.domain.model.Marca;
import br.unitins.topicos1.domain.model.Produto;
import br.unitins.topicos1.domain.model.Variacao;
import br.unitins.topicos1.domain.repository.ProdutoRepository;
import br.unitins.topicos1.dto.ProdutoDto;
import br.unitins.topicos1.dto.VariacaoRequestDto;
import br.unitins.topicos1.exception.ProdutoNaoEncontradoException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ProdutoService {
    @Inject
    ProdutoRepository produtoRepository;
    @Inject
    VariacaoService variacaoService;
    @Inject
    AnuncioService anuncioService;
    @Inject
    MarcaService marcaService;

    public Produto buscarPorId(Long id) {
        try {
            return produtoRepository.buscarProdutoPorId(id);
        } catch (NoResultException e) {
            throw new ProdutoNaoEncontradoException("Produto não encontrado.");
        }
    }

    public Produto buscarPorVariacao(Variacao variacao) {
        try {
            return produtoRepository.buscarProdutoPorVariacao(variacao);
        } catch (NoResultException e) {
            throw new ProdutoNaoEncontradoException("Produto não encontrado.");
        }

    }

    @Transactional
    public Produto criar(List<VariacaoRequestDto> variacoesDto, Marca marca, String nomeProduto) {
        Produto produto = new Produto();
        List<Variacao> variacoes = variacaoService.criarVariacoes(variacoesDto);
        produto.setVariacao(variacoes);
        produto.setMarca(marca);
        produto.setNome(nomeProduto);
        produtoRepository.persist(produto);
        return produto;
    }

    @Transactional
    public Produto adicionarVariacaoProduto(VariacaoRequestDto dto, Long produtoId) {
        Produto produto = produtoRepository.buscarProdutoPorId(produtoId);
        variacaoService.criarVariacao(dto, produto);
        return produto;
    }

    @Transactional
    public Produto atualizar(ProdutoDto dto, Long anuncioId) {

        Anuncio anuncio = anuncioService.buscarPorId(anuncioId);
        Produto produto = buscarPorId(anuncio.getProduto().getId());
        Marca marca = marcaService.criarNovaMarcaCasoNaoExista(dto.getNomeMarca());
        if (dto.getNome() != null) {
            produto.setNome(dto.getNome());
        }
        if (dto.getNomeMarca() != null) {
            produto.setMarca(marca);
        }
        produtoRepository.persist(produto);
        return produto;
    }


}
