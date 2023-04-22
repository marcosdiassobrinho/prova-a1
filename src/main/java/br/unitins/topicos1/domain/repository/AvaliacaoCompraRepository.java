package br.unitins.topicos1.domain.repository;

import br.unitins.topicos1.domain.model.*;
import br.unitins.topicos1.exception.AvaliacaoCompraNaoEncontradaException;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.*;

@ApplicationScoped
public class AvaliacaoCompraRepository implements PanacheRepository<AvaliacaoCompra> {
    @Inject
    EntityManager em;

    public AvaliacaoCompra buscarPagamentoPorId(Long id) {
        return find("id", id).firstResultOptional().orElseThrow(
                () -> new AvaliacaoCompraNaoEncontradaException("Avaliacao não encontrada"));
    }

    public boolean avaliacaoCompraExiste(Long idCompra, boolean vendedor) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Long.class);
        var avaliacao = cq.from(AvaliacaoCompra.class);

        Join<AvaliacaoCompra, Compra> compraJoin = avaliacao.join("compra");
        Join<Compra, Anuncio> anuncioJoin = compraJoin.join("anuncio");
        cq.select(cb.count(avaliacao));

        cq.where(cb.equal(compraJoin.get("id"), idCompra),
                cb.equal(avaliacao.get("vendedor"), vendedor)
        );

        return em.createQuery(cq).getSingleResult() > 0;
    }

    public AvaliacaoCompra buscarAvaliacaoPorCompra(Long compraId, boolean vendedor) {
        try {
            var cb = em.getCriteriaBuilder();
            var cq = cb.createQuery(AvaliacaoCompra.class);
            var avaliacaoCompra = cq.from(AvaliacaoCompra.class);
            Join<AvaliacaoCompra, Compra> compra = avaliacaoCompra.join("compra");

            cq.where(
                    cb.equal(compra.get("id"), compraId),
                    cb.equal(avaliacaoCompra.get("vendedor"), vendedor)
            );

            return em.createQuery(cq).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Long buscarReputacao(Perfil perfil, boolean vendedor) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Long.class);
        var avaliacaoCompra = cq.from(AvaliacaoCompra.class);
        Join<AvaliacaoCompra, Compra> compra = avaliacaoCompra.join("compra");
        Join<Compra, Anuncio> paginaProduto = compra.join("anuncio");

        Expression<Long> caseRecomenda = cb.selectCase()
                .when(cb.isTrue(avaliacaoCompra.get("recomenda")), cb.literal(1L))
                .otherwise(cb.literal(-1L))
                .as(Long.class);

        cq.select(cb.sum(caseRecomenda));

        if (!vendedor) {
            cq.where(
                    cb.equal(compra.get("perfil"), perfil),
                    cb.equal(avaliacaoCompra.get("vendedor"), vendedor)
            );
        } else {
            cq.where(
                    cb.equal(paginaProduto.get("perfil"), perfil),
                    cb.equal(avaliacaoCompra.get("vendedor"), vendedor)
            );
        }
        Long reputacao = em.createQuery(cq).getSingleResult();
        return reputacao != null ? reputacao : 0;
    }


    public Subquery<AvaliacaoCompra> criarSubqueryAvaliacao(CriteriaBuilder cb, CriteriaQuery<?> cq, Path<Compra> compra, boolean vendedor) {
        var avaliacaoSubquery = cq.subquery(AvaliacaoCompra.class);
        var avaliacaoRoot = avaliacaoSubquery.from(AvaliacaoCompra.class);
        avaliacaoSubquery.select(avaliacaoRoot)
                .where(cb.equal(avaliacaoRoot.get("compra"), compra),
                        cb.equal(avaliacaoRoot.get("vendedor"), vendedor));
        return avaliacaoSubquery;
    }
}