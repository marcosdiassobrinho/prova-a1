package br.unitins.topicos1.domain.repository;

import br.unitins.topicos1.domain.model.*;
import br.unitins.topicos1.domain.service.VariacaoService;
import br.unitins.topicos1.dto.CompraResponseDto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import java.util.List;

@ApplicationScoped
public class CompraRepository implements PanacheRepository<Compra> {
    @Inject
    EntityManager em;

    @Inject
    AvaliacaoCompraRepository avaliacaoRepository;

    public Compra buscarCompraPorId(Long id) {
        return find("id", id).firstResultOptional()
                .orElseThrow(NoResultException::new);
    }


    public CompraResponseDto buscarCompra(Long compraId) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(CompraResponseDto.class);
        var compra = cq.from(Compra.class);

        Join<Compra, Anuncio> anuncio = compra.join("anuncio");
        Join<Compra, Pagamento> pagamento = compra.join("pagamento");
        Join<Anuncio, Perfil> perfil = anuncio.join("perfil");
        Join<Perfil, Usuario> usuario = perfil.join("usuario");

        Subquery<AvaliacaoCompra> avaliacaoComprador = avaliacaoRepository.criarSubqueryAvaliacao(cb, cq, compra, false);

        Subquery<AvaliacaoCompra> avaliacaoVendedor = avaliacaoRepository.criarSubqueryAvaliacao(cb, cq, compra, true);

        cq.select(cb.construct(CompraResponseDto.class,
                compra.get("id"),
                compra.get("valor"),
                usuario.get("nome"),
                anuncio.get("titulo"),
                pagamento.get("statusPagamento"),
                compra.get("quantidadeCompra"),
                pagamento.get("dataEmissao"),
                pagamento.get("formaPagamento"),
                avaliacaoComprador,
                avaliacaoVendedor
        )).where(
                cb.equal(compra.get("id"), compraId)
        );

        return em.createQuery(cq).getSingleResult();
    }


    public List<CompraResponseDto> buscarCompras(Long idPerfil) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(CompraResponseDto.class);
        var compra = cq.from(Compra.class);

        Join<Compra, Anuncio> anuncio = compra.join("anuncio");
        Join<Compra, Pagamento> pagamento = compra.join("pagamento");
        Join<Compra, Perfil> perfil = anuncio.join("perfil");
        Join<Perfil, Usuario> usuario = perfil.join("usuario");

        Subquery<AvaliacaoCompra> avaliacaoComprador = avaliacaoRepository.criarSubqueryAvaliacao(cb, cq, compra, false);

        Subquery<AvaliacaoCompra> avaliacaoVendedor = avaliacaoRepository.criarSubqueryAvaliacao(cb, cq, compra, true);

        cq.select(cb.construct(CompraResponseDto.class,
                compra.get("id"),
                compra.get("valor"),
                usuario.get("nome"),
                anuncio.get("titulo"),
                pagamento.get("statusPagamento"),
                compra.get("quantidadeCompra"),
                pagamento.get("dataEmissao"),
                pagamento.get("formaPagamento"),
                avaliacaoComprador.getSelection(),
                avaliacaoVendedor.getSelection()
        )).where(cb.or(
                cb.equal(anuncio.get("perfil"), idPerfil),
                cb.equal(compra.get("perfil"), idPerfil))
        ).orderBy(
                cb.desc(pagamento.get("dataEmissao")
                )
        );
        return em.createQuery(cq).getResultList();


    }

}


