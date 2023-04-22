package br.unitins.topicos1.domain.repository;

import br.unitins.topicos1.domain.model.Anuncio;
import br.unitins.topicos1.domain.model.BatePapo;
import br.unitins.topicos1.domain.model.Perfil;
import br.unitins.topicos1.dto.BatePapoResponseDto;
import br.unitins.topicos1.exception.AnuncioNaoEncontradoException;
import br.unitins.topicos1.exception.BatePapoNaoEncontradoException;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Join;
import javax.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class BatePapoRepository implements PanacheRepository<BatePapo> {
    @Inject
    EntityManager em;

    public boolean batePapoAnuncioExiste(Perfil perfil, Anuncio anuncio) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Long.class);
        var batePapo = cq.from(BatePapo.class);

        Join<BatePapo, Anuncio> anunciojoin = batePapo.join("anuncio");
        Join<BatePapo, Perfil> perfilJoin = batePapo.join("perfilComprador");

        cq.select(cb.count(batePapo));

        cq.where(cb.equal(anunciojoin, anuncio), cb.equal(perfilJoin, perfil));

        return em.createQuery(cq).getSingleResult() > 0;
    }

    public BatePapoResponseDto buscarBatePapo(Perfil perfil, Anuncio anuncio) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(BatePapoResponseDto.class);
        var batePapo = cq.from(BatePapo.class);

        Join<BatePapo, Anuncio> anunciojoin = batePapo.join("anuncio");
        Join<BatePapo, Perfil> comprador = batePapo.join("perfilComprador");
        Join<Anuncio, Perfil> vendedor = anunciojoin.join("perfil");

        cq.select(
                cb.construct(BatePapoResponseDto.class,
                        batePapo.get("id"),
                        vendedor.get("usuario").get("nome"),
                        comprador.get("usuario").get("nome"),
                        anunciojoin.get("titulo")
                ));

        cq.where(
                cb.equal(anunciojoin, anuncio),
                cb.or(cb.equal(comprador, perfil), cb.equal(vendedor, perfil))
        );

        BatePapoResponseDto batePapoDto = em.createQuery(cq).getSingleResult();
        if (batePapoDto == null) {
            throw new NotFoundException();
        }
        return batePapoDto;
    }

    public List<BatePapoResponseDto> buscarBatePapos(Perfil perfil) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(BatePapoResponseDto.class);
        var batePapo = cq.from(BatePapo.class);

        Join<BatePapo, Anuncio> anunciojoin = batePapo.join("anuncio");
        Join<BatePapo, Perfil> comprador = batePapo.join("perfilComprador");
        Join<Anuncio, Perfil> vendedor = anunciojoin.join("perfil");

        cq.select(
                cb.construct(BatePapoResponseDto.class,
                        batePapo.get("id"),
                        vendedor.get("usuario").get("nome"),
                        comprador.get("usuario").get("nome"),
                        anunciojoin.get("titulo")
                ));

        cq.where(
                cb.or(cb.equal(comprador, perfil), cb.equal(vendedor, perfil))
        );

        return em.createQuery(cq).getResultList();

    }


    public BatePapo buscarBatePapoPorId(Long id) {
        return find("id", id).firstResultOptional()
                .orElseThrow(NotFoundException::new);
    }

}
