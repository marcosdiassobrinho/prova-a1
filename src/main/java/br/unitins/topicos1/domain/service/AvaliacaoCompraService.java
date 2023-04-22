package br.unitins.topicos1.domain.service;

import br.unitins.topicos1.domain.model.AvaliacaoCompra;
import br.unitins.topicos1.domain.model.Compra;
import br.unitins.topicos1.domain.model.Anuncio;
import br.unitins.topicos1.domain.model.Perfil;
import br.unitins.topicos1.domain.repository.AvaliacaoCompraRepository;
import br.unitins.topicos1.dto.AvaliacaoCompraDto;
import br.unitins.topicos1.dto.CompraResponseDto;
import br.unitins.topicos1.exception.AvaliacaoCompraConflitoException;
import br.unitins.topicos1.exception.PerfilConflitoException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import static java.time.LocalDateTime.now;

@ApplicationScoped
public class AvaliacaoCompraService {
    @Inject
    AvaliacaoCompraRepository avaliacaoCompraRepository;
    @Inject
    PerfilService perfilService;
    @Inject
    CompraService compraService;
    @Inject
    AnuncioService anuncioService;

    @Transactional
    public void criarAvaliacaoCompra(AvaliacaoCompraDto dto, Long idCompra) {
        Perfil perfil = perfilService.buscarPorId(dto.getPerfilId());
        Compra compra = compraService.buscarPorId(idCompra);
        Anuncio anuncio = anuncioService.buscarPorId(compra.getAnuncio().getId());
        boolean vendedor = anuncio.getPerfil() == perfil;

        if (avaliacaoCompraRepository.avaliacaoCompraExiste(idCompra, vendedor)) {
            throw new AvaliacaoCompraConflitoException("Já existe uma avaliação.");
        }

        AvaliacaoCompra avaliacaoCompra = new AvaliacaoCompra();
        avaliacaoCompra.setCompra(compra);
        avaliacaoCompra.setDataAvaliacao(now());
        avaliacaoCompra.setComentario(dto.getComentario());
        avaliacaoCompra.setRecomenda(dto.isRecomenda());
        avaliacaoCompra.setVendedor(vendedor);
        salvar(avaliacaoCompra);

        compraService.salvar(compra);
    }

    public void salvar(AvaliacaoCompra avaliacaoCompra) {
        avaliacaoCompraRepository.persist(avaliacaoCompra);
    }

    public AvaliacaoCompra buscarAvaliacaoPorCompra(Long idCompra, boolean vendedor) {
        return avaliacaoCompraRepository.buscarAvaliacaoPorCompra(idCompra, vendedor);
    }

    public Long buscarReputacaoPorPerfil(Perfil perfil, boolean vendedor) {
        return avaliacaoCompraRepository.buscarReputacao(perfil, vendedor);
    }

//    public void atribuirAvaliacoes(CompraResponseDto response) {
//        AvaliacaoCompra avaliacaoComprador = buscarAvaliacaoPorCompra(response.getId(), false);
//        AvaliacaoCompra avaliacaoVendedor = buscarAvaliacaoPorCompra(response.getId(), true);
//        response.setAvaliacaoComprador(avaliacaoComprador);
//        response.setAvaliacaoVendendor(avaliacaoVendedor);
//    }
}
