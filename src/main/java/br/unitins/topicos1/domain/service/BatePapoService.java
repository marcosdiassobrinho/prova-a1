package br.unitins.topicos1.domain.service;

import br.unitins.topicos1.domain.model.Anuncio;
import br.unitins.topicos1.domain.model.BatePapo;
import br.unitins.topicos1.domain.model.Perfil;
import br.unitins.topicos1.domain.repository.BatePapoRepository;
import br.unitins.topicos1.dto.BatePapoResponseDto;
import br.unitins.topicos1.dto.ComentarioBatePapoResponseDto;
import br.unitins.topicos1.exception.BatePapoNaoEncontradoException;
import br.unitins.topicos1.exception.PerfilConflitoException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;

@ApplicationScoped
public class BatePapoService {
    @Inject
    BatePapoRepository batePapoRepository;
    @Inject
    PerfilService perfilService;

    @Inject
    AnuncioService anuncioService;

    @Inject
    ComentarioBatePapoService comentarioBatePapoService;

    public BatePapoResponseDto buscarBatePapo(Long perfilId, Long anuncioId) {
        try {
            Perfil perfil = perfilService.buscarPorId(perfilId);
            Anuncio anuncio = anuncioService.buscarPorId(anuncioId);

            BatePapoResponseDto batePapo = batePapoRepository.buscarBatePapo(perfil, anuncio);

            List<ComentarioBatePapoResponseDto> comentariosBatePapo = comentarioBatePapoService.buscarComentariosBatePapo(batePapo.getId());
            batePapo.setComentarioBatePapoResponse(comentariosBatePapo);
            return batePapo;
        } catch (NotFoundException e) {
            throw new BatePapoNaoEncontradoException("Bate papo não encontrado.");
        }
    }

    public List<BatePapoResponseDto> buscarBatePapos(Long perfilId) {
        try {
            Perfil perfil = perfilService.buscarPorId(perfilId);

            List<BatePapoResponseDto> batePapos = batePapoRepository.buscarBatePapos(perfil);

            for (BatePapoResponseDto batePapo : batePapos) {
                List<ComentarioBatePapoResponseDto> comentariosBatePapo = comentarioBatePapoService.buscarComentariosBatePapo(batePapo.getId());
                batePapo.setComentarioBatePapoResponse(comentariosBatePapo);
            }

            return batePapos;
        } catch (NotFoundException e) {
            throw new BatePapoNaoEncontradoException("Bate papo não encontrado.");
        }
    }


    public boolean batePapoExiste(Perfil perfil, Anuncio anuncio) {
        return batePapoRepository.batePapoAnuncioExiste(perfil, anuncio);
    }

    @Transactional
    public BatePapoResponseDto salvar(Long perfilId, Long anuncioId) {
        Anuncio anuncio = anuncioService.buscarPorId(anuncioId);
        Perfil perfil = perfilService.buscarPerfilCompleto(perfilId);
        if (anuncio.getPerfil() == perfil) {
            throw new PerfilConflitoException("Perfil incorreto.");
        }

        if (!batePapoExiste(perfil, anuncio)) {
            BatePapo batePapo = new BatePapo();
            batePapo.setAnuncio(anuncio);
            batePapo.setPerfilComprador(perfil);
            batePapoRepository.persist(batePapo);
        }
        return buscarBatePapo(perfilId, anuncioId);
    }

    public BatePapo buscarPorId(Long id) {
        try {
            return batePapoRepository.buscarBatePapoPorId(id);
        } catch (NotFoundException e) {
            throw new BatePapoNaoEncontradoException("Bate papo não encontrado.");
        }
    }
}
