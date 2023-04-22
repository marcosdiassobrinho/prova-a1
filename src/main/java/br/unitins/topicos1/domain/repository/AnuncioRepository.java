package br.unitins.topicos1.domain.repository;

import br.unitins.topicos1.domain.model.*;
import br.unitins.topicos1.dto.AnuncioResponseDto;
import br.unitins.topicos1.dto.AnunciosResponseDto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import javax.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class AnuncioRepository implements PanacheRepository<Anuncio> {
    @Inject
    EntityManager em;

    public Anuncio buscarPorId(Long id) {
        return find("id", id).firstResultOptional()
                .orElseThrow(NoResultException::new);
    }

    public List<AnunciosResponseDto> buscarAnuncios() {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(AnunciosResponseDto.class);
        var root = cq.from(Anuncio.class);

        Join<Anuncio, Perfil> perfil = root.join("perfil");
        Join<Perfil, Usuario> usuario = perfil.join("usuario");

        Join<Anuncio, Produto> produto = root.join("produto");
        Join<Produto, Variacao> variacao = produto.join("variacao");

        Expression<Integer> somaEstoque = cb.sum(variacao.get("quantidadeEstoque"));
        Expression<Double> valorMin = cb.min(variacao.get("valorLiquido"));
        Expression<Double> valorMax = cb.max(variacao.get("valorLiquido"));
        cq.multiselect(root.get("id"), usuario.get("nome"), root.get("titulo"), valorMin, valorMax, somaEstoque);
        cq.groupBy(root.get("id"), usuario.get("nome"), root.get("titulo"));
        return em.createQuery(cq).getResultList();
    }

    public AnuncioResponseDto buscarDtoPorId(Long id) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(AnuncioResponseDto.class);
        var root = cq.from(Anuncio.class);

        Join<Anuncio, Perfil> perfil = root.join("perfil");
        Join<Perfil, Usuario> usuario = perfil.join("usuario");

        Join<Anuncio, Produto> produto = root.join("produto");

        cq.select(cb.construct(AnuncioResponseDto.class, produto, root.get("titulo"), usuario.get("nome")));
        cq.where(cb.equal(root.get("id"), id));
        cq.groupBy(produto, root.get("titulo"), usuario.get("nome"));
        return em.createQuery(cq).getSingleResult();
    }

    public Anuncio buscarAnuncioPorProduto(Produto produto) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Anuncio.class);
        var root = cq.from(Anuncio.class);

        cq.where(cb.equal(root.get(Anuncio_.produto), produto));
        return em.createQuery(cq).getSingleResult();
    }


}
