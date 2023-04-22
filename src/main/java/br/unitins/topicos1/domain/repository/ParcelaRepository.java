package br.unitins.topicos1.domain.repository;

import br.unitins.topicos1.domain.model.enums.StatusParcela;
import br.unitins.topicos1.domain.model.Pagamento;
import br.unitins.topicos1.domain.model.Parcela;
import br.unitins.topicos1.exception.ParcelaNaoEncontradoException;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.Join;
import java.util.Optional;

@ApplicationScoped
public class ParcelaRepository implements PanacheRepository<Parcela> {
    @Inject
    EntityManager em;

    public Optional<Parcela> buscarParcelaPorId(Long id) {
        return find("id", id).firstResultOptional();
    }

    public Parcela buscarPrimeiraParcelaPendente(Pagamento pagamento) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Parcela.class);
        var parcela = cq.from(Parcela.class);

        Join<Parcela, Pagamento> pagamentoJoin = parcela.join("pagamento");

        cq.where(cb.equal(pagamentoJoin, pagamento),
                cb.or(
                        cb.equal(parcela.get("status"), StatusParcela.PENDENTE),
                        cb.equal(parcela.get("status"), StatusParcela.ATRASADO)
                ));

        cq.orderBy(cb.asc(parcela.get("dataVencimento")));

        try {
            return em.createQuery(cq).setMaxResults(1).getSingleResult();
        } catch (NoResultException e) {
            throw new ParcelaNaoEncontradoException("Nenhuma parcela pendente ou atrasada encontrada para o pagamento com o ID: " + pagamento.getId());
        }
    }

    public Long quantidadeParcelasPendentes(Pagamento pagamento) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Long.class);
        var parcela = cq.from(Parcela.class);

        Join<Parcela, Pagamento> pagamentoJoin = parcela.join("pagamento");

        cq.select(cb.count(parcela));

        cq.where(cb.equal(pagamentoJoin, pagamento),
                cb.or(
                        cb.equal(parcela.get("status"), StatusParcela.PENDENTE),
                        cb.equal(parcela.get("status"), StatusParcela.ATRASADO)
                ));
       return em.createQuery(cq).getSingleResult();
    }
}
