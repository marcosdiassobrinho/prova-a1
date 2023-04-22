package br.unitins.topicos1.domain.service;

import br.unitins.topicos1.domain.model.*;
import br.unitins.topicos1.domain.repository.*;
import br.unitins.topicos1.dto.*;
import br.unitins.topicos1.exception.AnuncioNaoEncontradoException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class AnuncioService {
    @Inject
    AnuncioRepository anuncioRepository;
    @Inject
    PerfilService perfilService;
    @Inject
    MarcaService marcaService;

    @Inject
    ProdutoService produtoService;

    public List<AnunciosResponseDto> buscarAnuncios() {
        return anuncioRepository.buscarAnuncios();
    }

    public AnuncioResponseDto buscarDtoPorID(Long id) {
        try {
            return anuncioRepository.buscarDtoPorId(id);
        } catch (NoResultException e) {
            throw new AnuncioNaoEncontradoException(String.format("Anuncio de id: %s não foi encontrado", id));
        }
    }

    @Transactional
    public AnuncioResponseDto salvar(AnuncioDto dto) {
        try {
            Perfil perfil = perfilService.buscarPerfilCompleto(dto.getPerfilId());
            Marca marca = marcaService.criarNovaMarcaCasoNaoExista(dto.getMarca());
            Produto produto = produtoService.criar(dto.getVariacoes(), marca, dto.getNomeProduto());

            Anuncio anuncio = new Anuncio();
            anuncio.setPerfil(perfil);
            anuncio.setTitulo(dto.getTitulo());
            anuncio.setAtivo(true);
            anuncio.setProduto(produto);
            anuncioRepository.persist(anuncio);
            return buscarDtoPorID(anuncio.getId());
        } catch (NotFoundException e) {
            throw new AnuncioNaoEncontradoException("Anuncio não foi encontrado");
        }
    }

    @Transactional
    public AnuncioResponseDto atualizar(Long anuncioId, AtualizarAnuncioDto dto) {
        try {
            Anuncio anuncio = buscarPorId(anuncioId);
            if (dto.getTitulo() != null) {
                anuncio.setTitulo(dto.getTitulo());
            }
            anuncio.setAtivo(dto.isAtivo());
            anuncioRepository.persist(anuncio);
            return buscarDtoPorID(anuncio.getId());
        } catch (NotFoundException e) {
            throw new AnuncioNaoEncontradoException("Anuncio não foi encontrado.");
        }
    }

    @Transactional
    public void deletar(Long anuncioId) {
        try {
            Anuncio anuncio = anuncioRepository.buscarPorId(anuncioId);
            anuncioRepository.delete(anuncio);
        } catch (NoResultException e) {
            throw new AnuncioNaoEncontradoException(String.format("Anuncio de id: %s não foi encontrado", anuncioId));

        }

    }

    public Anuncio buscarPorProduto(Produto produto) {
        try {
            return anuncioRepository.buscarAnuncioPorProduto(produto);
        } catch (NoResultException e) {
            throw new AnuncioNaoEncontradoException(String.format("Anuncio do produto: %s não foi encontrado", produto.getNome()));
        }
    }

    public Anuncio buscarPorId(Long id) {
        try {
            return anuncioRepository.buscarPorId(id);
        } catch (NoResultException e) {
            throw new AnuncioNaoEncontradoException(String.format("Anuncio de id: %s não foi encontrado", id));
        }
    }


}
