package Dia_02_Carrinho;

import Dia_02_Carrinho.Exceptions.ValorInvalido;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    private final List<itemCarrinho> itens = new ArrayList<>();


    void adicionarItens(Produto produto, int quantidade) {
        itemCarrinho itemCarrinho = new itemCarrinho(produto, quantidade);
        itens.add(itemCarrinho);
    }

    public BigDecimal total() {

        BigDecimal total = BigDecimal.ZERO;

        for (itemCarrinho item: itens) {
             total = total.add(item.subtotal());
        }

        return total;
    }

    BigDecimal totalComCupom(int percentual){
        if (percentual < 0 || percentual > 50) {
            throw new ValorInvalido("Valor de percentual de cupom invalido");
        }

        BigDecimal totalMultiplicacao = total().multiply(BigDecimal.valueOf(100 - percentual));
        BigDecimal totalDivisao = totalMultiplicacao.divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP);

        return totalDivisao;
    }

    void imprimirResumo(){
        for (itemCarrinho item: itens) {
            System.out.println("Nome: " + item.getProduto().getNome());
            System.out.println("Quantidade: " +item.getQuantidade());
            System.out.println("SubTotal: " +item.subtotal());
        }
        System.out.println("Total: " +total());
    }

}
