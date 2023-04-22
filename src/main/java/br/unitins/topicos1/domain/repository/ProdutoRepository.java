package br.unitins.topicos1.domain.repository;

import br.unitins.topicos1.domain.model.*;
import br.unitins.topicos1.exception.ProdutoNaoEncontradoException;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.Join;
import java.util.Optional;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<Produto> {
    @Inject
    EntityManager em;

    public Produto buscarProdutoPorId(Long id) {
        return find("id", id).firstResultOptional()
                .orElseThrow(NoResultException::new);
    }

    public Produto buscarProdutoPorVariacao(Variacao variacao) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Produto.class);
        var root = cq.from(Produto.class);
        Join<Produto, Variacao> joinVariacao = root.join(Produto_.variacao);


        cq.select(root).where(cb.equal(joinVariacao.get(Variacao_.id), variacao.getId()));
        return em.createQuery(cq).getSingleResult();
    }
}
