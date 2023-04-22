package br.unitins.topicos1.domain.service;

import br.unitins.topicos1.domain.model.Anuncio;
import br.unitins.topicos1.domain.model.Carrinho;
import br.unitins.topicos1.domain.model.CarrinhoVariacao;
import br.unitins.topicos1.domain.model.Variacao;
import br.unitins.topicos1.domain.repository.CarrinhoVariacaoRepository;
import br.unitins.topicos1.exception.VariacaoConflitoException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;

@ApplicationScoped
public class CarrinhoVariacaoService {
    @Inject
    CarrinhoVariacaoRepository carrinhoVariacaoRepository;

    private Optional<CarrinhoVariacao> buscarVariacaoCarrinhoExistente(Carrinho carrinho, Variacao variacao) {
        return carrinhoVariacaoRepository.buscarVariacaoPorCarrinho(carrinho, variacao);
    }

    public void validarQuantidadeSolicitada(CarrinhoVariacao carrinhoVariacao, Variacao variacao) {
        if (carrinhoVariacao.getQuantidade() > variacao.getQuantidadeEstoque()) {
            throw new VariacaoConflitoException(String.format("A quantidade solicitada do %s é maior que a quantidade disponível em estoque (%s)", variacao.getDescricao(), variacao.getQuantidadeEstoque()));
        }
    }

    public void salvar(CarrinhoVariacao carrinhoVariacao) {
        carrinhoVariacaoRepository.persist(carrinhoVariacao);
    }

    @Transactional
    public CarrinhoVariacao adicionarVariacaoAoCarrinho(Carrinho carrinho, Variacao variacao, int quantidade, Anuncio anuncio) {
        CarrinhoVariacao carrinhoVariacao = buscarVariacaoCarrinhoExistente(carrinho, variacao).orElseGet(() -> criarCarrinhoVariacao(carrinho, variacao, quantidade, anuncio));

        carrinhoVariacao.setQuantidade(carrinhoVariacao.getQuantidade() + quantidade);
        validarQuantidadeSolicitada(carrinhoVariacao, variacao);

        salvar(carrinhoVariacao);

        return carrinhoVariacao;
    }

    private CarrinhoVariacao criarCarrinhoVariacao(Carrinho carrinho, Variacao variacao, int quantidade, Anuncio anuncio) {
        CarrinhoVariacao carrinhoVariacao = new CarrinhoVariacao(carrinho, variacao, quantidade, anuncio);
        carrinho.getCarrinhoVariacoes().add(carrinhoVariacao);
        return carrinhoVariacao;
    }


}
