package br.unitins.topicos1.domain.service;

import br.unitins.topicos1.domain.model.*;
import br.unitins.topicos1.domain.repository.CarrinhoDto;
import br.unitins.topicos1.domain.repository.CarrinhoRepository;
import br.unitins.topicos1.domain.repository.CarrinhoVariacaoRepository;
import br.unitins.topicos1.dto.CarrinhoResponseDto;
import br.unitins.topicos1.exception.CarrinhoNaoEncontradoException;
import br.unitins.topicos1.exception.PerfiNaoEncontradoException;
import br.unitins.topicos1.exception.PerfilConflitoException;
import br.unitins.topicos1.exception.VariacaoConflitoException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class CarrinhoService {
    @Inject
    CarrinhoRepository carrinhoRepository;
    @Inject
    ProdutoService produtoService;
    @Inject
    CarrinhoVariacaoService carrinhoVariacaoService;
    @Inject
    VariacaoService variacaoService;

    @Inject
    PerfilService perfilService;

    @Inject
    AnuncioService anuncioService;

    public Carrinho buscar(Perfil perfil) {
        try {
            return carrinhoRepository.buscarCarrinhoPorPerfil(perfil);
        } catch (NoResultException e) {
            Carrinho carrinho = new Carrinho();
            carrinho.setPerfil(perfil);
            carrinhoRepository.persist(carrinho);
            throw new CarrinhoNaoEncontradoException(String.format("O perfil de %s não possui carrinho.", perfil.getUsuario().getNome()));
        }
    }

    public List<CarrinhoResponseDto> buscarResponse(Long perfilId) {
        Perfil perfil = perfilService.buscarPorId(perfilId);
        Carrinho carrinho = buscar(perfil);
        List<CarrinhoResponseDto> itensCarrinho = carrinhoRepository.buscarItensCarrinhoPorPerfil(perfil);
        List<CarrinhoVariacao> carrinhoVariacoes = carrinho.getCarrinhoVariacoes();

        for (CarrinhoResponseDto dto : itensCarrinho) {
            List<Variacao> variacoes = new ArrayList<>();
            Anuncio anuncio = anuncioService.buscarPorId(dto.getIdAnuncio());
            for (CarrinhoVariacao cv : carrinhoVariacoes) {
                if (cv.getAnuncio().equals(anuncio)) {
                    variacoes.add(cv.getVariacao());
                    dto.setQuantidade(dto.getQuantidade() + cv.getQuantidade());
                    dto.setPreco(dto.getPreco() + cv.getVariacao().getValorBruto());
                }
            }
            dto.setVariacoes(variacoes);
        }

        return itensCarrinho;
    }


    public void adicionar(CarrinhoDto dto) {
        Variacao variacao = variacaoService.buscarPorId(dto.getVariacaoId());
        Perfil perfil = perfilService.buscarPorId(dto.getPerfilId());
        Carrinho carrinho = buscar(perfil);
        Produto produto = produtoService.buscarPorVariacao(variacao);
        Anuncio anuncio = anuncioService.buscarPorProduto(produto);

        verificarPerfilEAnuncio(perfil, anuncio, produto);

        CarrinhoVariacao carrinhoVariacao = carrinhoVariacaoService.adicionarVariacaoAoCarrinho(carrinho, variacao, dto.getQuantidade(), anuncio);

        carrinhoRepository.persist(carrinho);
    }

    private void verificarPerfilEAnuncio(Perfil perfil, Anuncio anuncio, Produto produto) {
        if (anuncio.getPerfil().equals(perfil)) {
            throw new PerfilConflitoException(String.format("O %s é o dono do anuncio do %s.", perfil.getUsuario().getNome(), produto.getNome()));
        }
    }
}
