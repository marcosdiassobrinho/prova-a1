package br.unitins.topicos1.domain.repository;

import br.unitins.topicos1.domain.model.BatePapo;
import br.unitins.topicos1.domain.model.ComentarioBatePapo;
import br.unitins.topicos1.domain.model.Perfil;
import br.unitins.topicos1.domain.model.Usuario;
import br.unitins.topicos1.dto.ComentarioBatePapoResponseDto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Join;
import javax.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class ComentarioBatePapoRepository implements PanacheRepository<ComentarioBatePapo> {

    @Inject
    EntityManager em;

    public List<ComentarioBatePapoResponseDto> buscarComentariosBatePapoDtos(Long batePapoId) {

        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(ComentarioBatePapoResponseDto.class);
        var root = cq.from(BatePapo.class);

        Join<BatePapo, ComentarioBatePapo> comentario = root.join("comentarios");
        Join<ComentarioBatePapo, Perfil> perfil = comentario.join("perfil");
        Join<Perfil, Usuario> usuario = perfil.join("usuario");
        cq.select(
                cb.construct(ComentarioBatePapoResponseDto.class,
                        comentario.get("id"),
                        usuario.get("nome"),
                        comentario.get("comentario"),
                        comentario.get("dataCriacao"),
                        comentario.get("vendedor")
                ));

        cq.where(cb.equal(root.get("id"), batePapoId));
        cq.orderBy(cb.desc(comentario.get("dataCriacao")));
        return em.createQuery(cq).getResultList();
    }


    public ComentarioBatePapoResponseDto buscarComentariosBatePapoDto(Long comentarioId) {

        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(ComentarioBatePapoResponseDto.class);
        var root = cq.from(ComentarioBatePapo.class);

        Join<ComentarioBatePapo, Perfil> perfil = root.join("perfil");
        Join<Perfil, Usuario> usuario = perfil.join("usuario");
        cq.select(
                cb.construct(ComentarioBatePapoResponseDto.class,
                        root.get("id"),
                        usuario.get("nome"),
                        root.get("comentario"),
                        root.get("dataCriacao"),
                        root.get("vendedor")
                ));

        cq.where(cb.equal(root.get("id"), comentarioId));
        ComentarioBatePapoResponseDto ComentarioBatePapoResponseDto = em.createQuery(cq).getSingleResult();
        if (ComentarioBatePapoResponseDto == null) {
            throw new NotFoundException();
        }
        return ComentarioBatePapoResponseDto;
    }

}
