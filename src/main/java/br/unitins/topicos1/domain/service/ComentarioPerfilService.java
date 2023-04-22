package br.unitins.topicos1.domain.service;

import br.unitins.topicos1.domain.model.ComentarioPerfil;
import br.unitins.topicos1.domain.model.Perfil;
import br.unitins.topicos1.domain.repository.ComentarioPerfilRepository;
import br.unitins.topicos1.dto.ComentariosResponseDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class ComentarioPerfilService {
    @Inject
    ComentarioPerfilRepository comentarioPerfilRepository;

    public ComentarioPerfil buscarComentarioPerfilPorId(Long id) {
        return comentarioPerfilRepository.buscarComentarioPorId(id);
    }

    public List<ComentariosResponseDto> buscarComentariosPorPerfil(Perfil perfil) {
        return comentarioPerfilRepository.buscarComentariosPorIdPerfil(perfil);
    }

}
