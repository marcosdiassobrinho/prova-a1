package br.unitins.topicos1.domain.repository;

import br.unitins.topicos1.domain.model.ComentarioPerfil;
import br.unitins.topicos1.domain.model.Perfil;
import br.unitins.topicos1.domain.model.Usuario;
import br.unitins.topicos1.dto.ComentariosResponseDto;
import br.unitins.topicos1.exception.ComentarioNaoEncontradoException;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.Join;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ComentarioPerfilRepository implements PanacheRepository<ComentarioPerfil> {
    @Inject
    EntityManager em;
    public Optional<ComentarioPerfil> buscarPorIdComentarioId(Long id) {
        return find("id", id).firstResultOptional();
    }

    public ComentarioPerfil buscarComentarioPorId(Long id) {
        return find("id", id).firstResultOptional()
                .orElseThrow(() -> new ComentarioNaoEncontradoException("Comentário não encontrado."));
    }


    public List<ComentariosResponseDto> buscarComentariosPorIdPerfil(Perfil perfil) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(ComentariosResponseDto.class);
        var perfilRoot = cq.from(Perfil.class);

        Join<Perfil, Usuario> usuario = perfilRoot.join("usuario");
        Join<Perfil, ComentarioPerfil> comentario = perfilRoot.join("comentarios");

        cq.multiselect(comentario.get("comentario"), usuario.get("nome"), comentario.get("dataComentario"));

        cq.where(cb.equal(perfilRoot, perfil));
        cq.groupBy(comentario.get("id"), usuario.get("nome"));
        cq.orderBy(cb.asc(comentario.get("dataComentario")));


        return em.createQuery(cq).getResultList();
    }
}
