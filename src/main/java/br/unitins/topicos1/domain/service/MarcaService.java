package br.unitins.topicos1.domain.service;

import br.unitins.topicos1.domain.model.Marca;
import br.unitins.topicos1.domain.repository.MarcaRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

@ApplicationScoped
public class MarcaService {
    @Inject
    MarcaRepository marcaRepository;

    public Marca criarNovaMarcaCasoNaoExista(String nomeMarca) {
        Marca marca;
        try {
            marca = marcaRepository.findByNome(nomeMarca);
        } catch (NotFoundException e) {
            marca = new Marca();
            marca.setNome(nomeMarca);
            marcaRepository.persist(marca);
        }
        return marca;
    }


    public void salvar(Marca marca) {
        marcaRepository.persist(marca);
    }
}
