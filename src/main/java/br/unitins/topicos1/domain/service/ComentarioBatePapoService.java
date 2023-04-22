package br.unitins.topicos1.domain.service;

import br.unitins.topicos1.domain.model.BatePapo;
import br.unitins.topicos1.domain.model.ComentarioBatePapo;
import br.unitins.topicos1.domain.model.Perfil;
import br.unitins.topicos1.domain.repository.ComentarioBatePapoRepository;
import br.unitins.topicos1.dto.ComentarioBatePapoResponseDto;
import br.unitins.topicos1.dto.ComentarioBatePapoDto;
import br.unitins.topicos1.exception.ComentarioNaoEncontradoException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;

import static java.time.LocalDateTime.now;


@ApplicationScoped
public class ComentarioBatePapoService {
    @Inject
    ComentarioBatePapoRepository batePapoRepository;
    @Inject
    BatePapoService batePapoService;
    @Inject
    PerfilService perfilService;

    public List<ComentarioBatePapoResponseDto> buscarComentariosBatePapo(Long batePapoId) {
        return batePapoRepository.buscarComentariosBatePapoDtos(batePapoId);
    }

    public ComentarioBatePapoResponseDto buscarComentarioBatePapo(Long ComentarioId) {
        try {
            return batePapoRepository.buscarComentariosBatePapoDto(ComentarioId);
        } catch (NotFoundException e) {
            throw new ComentarioNaoEncontradoException("Comentário não encontrado");
        }
    }

    @Transactional
    public ComentarioBatePapoResponseDto salvar(Long batePapoId, ComentarioBatePapoDto dto) {
        BatePapo batePapo = batePapoService.buscarPorId(batePapoId);
        Perfil perfil = perfilService.buscarPorId(dto.getIdPerfil());
        ComentarioBatePapo comentario = new ComentarioBatePapo();

        comentario.setVendedor(!perfil.equals(batePapo.getPerfilComprador()));
        comentario.setComentario(dto.getComentario());
        comentario.setBatePapo(batePapo);
        comentario.setDataCriacao(now());
        comentario.setPerfil(perfil);
        comentario.setDenunciado(false);
        batePapoRepository.persist(comentario);
        return buscarComentarioBatePapo(comentario.getId());
    }

}
