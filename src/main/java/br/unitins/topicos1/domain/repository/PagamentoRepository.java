package br.unitins.topicos1.domain.repository;

import br.unitins.topicos1.domain.model.enums.StatusPagamento;
import br.unitins.topicos1.domain.model.Compra;
import br.unitins.topicos1.domain.model.Pagamento;
import br.unitins.topicos1.domain.model.Perfil;
import br.unitins.topicos1.exception.PagamentoNaoEncontradoException;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Join;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
public class PagamentoRepository implements PanacheRepository<Pagamento> {
    @Inject
    EntityManager em;

    public Pagamento buscarPagamentoPorId(Long id) {
        return find("id", id).firstResultOptional().orElseThrow(NotFoundException::new);
    }

    public boolean pagamentoEmAtraso(Perfil perfil) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Long.class);
        var compra = cq.from(Compra.class);

        Join<Compra, Pagamento> pagamento = compra.join("pagamento");
        Join<Compra, Perfil> perfilJoin = compra.join("perfil");

        cq.where(cb.equal(perfilJoin, perfil),
                cb.equal(pagamento.get("statusPagamento"), StatusPagamento.ATRASO)
        );

        cq.select(cb.count(pagamento));
        return em.createQuery(cq).getSingleResult() > 0;

    }
}
