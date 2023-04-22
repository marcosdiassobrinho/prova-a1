package br.unitins.topicos1.domain.service;

import br.unitins.topicos1.domain.model.ComentarioPerfil;
import br.unitins.topicos1.domain.model.Perfil;
import br.unitins.topicos1.domain.repository.ComentarioPerfilRepository;
import br.unitins.topicos1.domain.repository.PerfilRepository;
import br.unitins.topicos1.domain.repository.UsuarioRepository;
import br.unitins.topicos1.domain.model.enums.StatusPerfil;
import br.unitins.topicos1.domain.model.Usuario;
import br.unitins.topicos1.dto.AdicionarComentarioPerfilRequestDto;
import br.unitins.topicos1.dto.ComentariosResponseDto;
import br.unitins.topicos1.dto.CriaUsuarioDePerfilDto;
import br.unitins.topicos1.exception.PerfiNaoEncontradoException;
import br.unitins.topicos1.exception.PerfilConflitoException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;


@ApplicationScoped
public class PerfilService {
    @Inject
    PerfilRepository perfilRepository;
    @Inject
    ComentarioPerfilRepository comentarioPerfilRepository;
    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    ComentarioPerfilService comentarioPerfilService;

    @Inject
    AvaliacaoCompraService avaliacaoCompraService;

    @Transactional
    public void atualizarUsuarioPerfil(CriaUsuarioDePerfilDto dto) {
        Perfil perfil = perfilRepository.findById(dto.getLoginId());
        Usuario usuario = perfil.getUsuario();

        if (perfil.getUsuario() == null) {
            usuario = new Usuario();
            usuarioRepository.persist(usuario);
        }

        if (dto.getNome() != null) {
            usuario.setNome(dto.getNome());
        }

        if (dto.getCpf() != null) {
            usuario.setCpf(dto.getCpf());
        }

        if (dto.getDataDeNascimento() != null) {
            usuario.setDataDeNascimento(dto.getDataDeNascimento());
        }

        perfil.setStatusPerfil(StatusPerfil.COMPLETO);
        perfilRepository.persist(perfil);
    }

    @Transactional
    public void ativarPerfil(Long id) {
        Perfil perfil = perfilRepository.findById(id);
        if (perfil.getStatusPerfil() != StatusPerfil.COMPLETO) {
            perfil.setStatusPerfil(StatusPerfil.INCOMPLETO);
        }
    }

    public List<ComentariosResponseDto> buscarComentarios(Long id) {
        Perfil perfil = buscarPorId(id);
        return comentarioPerfilService.buscarComentariosPorPerfil(perfil);
    }

    @Transactional
    public void adicionarComentario(AdicionarComentarioPerfilRequestDto dto, Long id) {
        Perfil perfilDestinatario = buscarPorId(id);

        Perfil perfilRemetente = buscarPorId(id);

        if (perfilRemetente.getStatusPerfil() != StatusPerfil.COMPLETO) {
            throw new PerfilConflitoException("Complete seu perfil antes de comentar!");
        }
        ComentarioPerfil comentarioPerfil = new ComentarioPerfil();
        comentarioPerfil.setComentario(dto.getComentario());
        comentarioPerfil.setPerfil(perfilDestinatario);
        comentarioPerfilRepository.persist(comentarioPerfil);
    }

    @Transactional
    public void removerComentario(Long id) {
        ComentarioPerfil comentarioPerfil = comentarioPerfilService.buscarComentarioPerfilPorId(id);
        comentarioPerfilRepository.delete(comentarioPerfil);
    }

    public void denunciarComentario(Long id) {
        ComentarioPerfil comentarioPerfil = comentarioPerfilService.buscarComentarioPerfilPorId(id);
        comentarioPerfil.setDenunciado(true);
        comentarioPerfilRepository.persist(comentarioPerfil);
    }

    public Perfil buscarPorId(Long id) {
        try {
            Perfil perfil = perfilRepository.buscarPerfilPorId(id);
            Long reputacao = avaliacaoCompraService.buscarReputacaoPorPerfil(perfil, true) + avaliacaoCompraService.buscarReputacaoPorPerfil(perfil, false);
            perfil.setReputacao(reputacao);
            return perfil;
        } catch (NotFoundException e) {
            throw new PerfiNaoEncontradoException("Perfil não encontrado.");
        }
    }

    public Perfil buscarPerfilCompleto(Long idPerfil) {
        try {
            Perfil perfil = perfilRepository.buscarPerfilPorId(idPerfil);

            if (perfil.getStatusPerfil() != StatusPerfil.COMPLETO) {
                throw new PerfilConflitoException("Perfil não está completo.");
            }
            return perfil;
        } catch (NotFoundException e) {
            throw new PerfiNaoEncontradoException("Perfil não encontrado.");
        }
    }
}
