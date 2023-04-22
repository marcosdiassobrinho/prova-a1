package br.unitins.topicos1.domain.repository;

import br.unitins.topicos1.domain.model.*;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Join;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CarrinhoVariacaoRepository implements PanacheRepository<CarrinhoVariacao> {
    @Inject
    EntityManager em;

    public Optional<CarrinhoVariacao> buscarVariacaoPorCarrinho(Carrinho carrinho, Variacao variacao) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(CarrinhoVariacao.class);
        var root = cq.from(CarrinhoVariacao.class);

        cq.where(cb.equal(root.get(CarrinhoVariacao_.carrinho), carrinho),
                cb.equal(root.get(CarrinhoVariacao_.variacao), variacao));

        List<CarrinhoVariacao> resultList = em.createQuery(cq).getResultList();

        return resultList.isEmpty() ? Optional.empty() : Optional.of(resultList.get(0));
    }


}
