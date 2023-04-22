package br.unitins.topicos1.domain.repository;

import br.unitins.topicos1.domain.model.*;
import br.unitins.topicos1.exception.AnuncioNaoEncontradoException;
import br.unitins.topicos1.exception.PerfiNaoEncontradoException;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.ws.rs.NotFoundException;
import java.util.Optional;

@ApplicationScoped
public class PerfilRepository implements PanacheRepository<Perfil> {
    @Inject
    EntityManager em;

    public Perfil buscarPerfilPorId(Long id) {
        return find("id", id).firstResultOptional()
                .orElseThrow(NotFoundException::new);
    }

    public Perfil buscarPerfilPorLogin(Login loginUsuario) {
        var cb = em.getCriteriaBuilder();
        var cq = cb.createQuery(Perfil.class);

        var perfil = cq.from(Perfil.class);

        cq.where(cb.equal(perfil.get("login"), loginUsuario));

        try {
            return em.createQuery(cq).getSingleResult();
        } catch (NoResultException e) {
            throw new PerfiNaoEncontradoException("Nenhum úsuario com o login de ID: " + loginUsuario.getId() + " foi encontrado.");
        }
    }

}
