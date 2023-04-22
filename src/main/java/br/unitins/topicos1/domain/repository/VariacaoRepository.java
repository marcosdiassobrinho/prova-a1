package br.unitins.topicos1.domain.repository;

import br.unitins.topicos1.domain.model.*;
import br.unitins.topicos1.exception.VariacaoNaoEncontradoException;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Join;
import java.util.List;

@ApplicationScoped
public class VariacaoRepository implements PanacheRepository<Variacao> {
    @Inject
    EntityManager em;
    public Variacao buscarVariacaoPorId(Long id) {
        return find("id", id).firstResultOptional().orElseThrow(
                () -> new VariacaoNaoEncontradoException("Variacao não encontrada"));
    }


    public List<Variacao> buscarVariacoesPorAnuncio(Long anuncioId) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Variacao.class);

        var anuncio = cq.from(Anuncio.class);

        Join<Anuncio, Produto> produto = anuncio.join("produto");
        Join<Produto, Variacao> variacoes = produto.join("variacao");

        cq.select(variacoes).where(cb.equal(anuncio.get("id"), anuncioId));

        return em.createQuery(cq).getResultList();
    }

    public List<Variacao> buscarVariacoesPorCompraId(Long compraId) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Variacao.class);
        var compra = cq.from(Compra.class);
        Join<Compra, Variacao> variacoes = compra.join("variacao");

        cq.select(variacoes).where(cb.equal(compra.get("id"), compraId));

        return em.createQuery(cq).getResultList();
    }
}
