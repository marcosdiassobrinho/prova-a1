package br.unitins.topicos1.domain.repository;

import br.unitins.topicos1.domain.model.*;
import br.unitins.topicos1.dto.CarrinhoResponseDto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.Join;
import java.util.List;

@ApplicationScoped
public class CarrinhoRepository implements PanacheRepository<Carrinho> {
    @Inject
    EntityManager em;

    public Carrinho buscarCarrinhoPorPerfil(Perfil perfil) {
        return find("perfil", perfil).firstResultOptional()
                .orElseThrow(NoResultException::new);
    }

    public List<CarrinhoResponseDto> buscarItensCarrinhoPorPerfil(Perfil perfil) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(CarrinhoResponseDto.class);
        var root = cq.from(Carrinho.class);

        Join<Carrinho, Perfil> joinPerfil = root.join(Carrinho_.perfil);
        Join<Carrinho, CarrinhoVariacao> joinCarrinhoVariacoes = root.join(Carrinho_.carrinhoVariacoes);
        Join<CarrinhoVariacao, Anuncio> joinAnuncio = joinCarrinhoVariacoes.join(CarrinhoVariacao_.anuncio);
        Join<Anuncio, Produto> joinProduto = joinAnuncio.join(Anuncio_.produto);

        cq.select(cb.construct(CarrinhoResponseDto.class,
                joinAnuncio.get(Anuncio_.id),
                joinAnuncio.get(Anuncio_.titulo),
                joinProduto.get(Produto_.nome)
        )).where(cb.equal(joinPerfil, perfil)).distinct(true);

        return em.createQuery(cq).getResultList();
    }
}
