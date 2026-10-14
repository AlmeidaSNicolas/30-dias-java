package Dia_02_Carrinho;


import Dia_02_Carrinho.Exceptions.ValorInvalido;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class itemCarrinho {
    private final Produto produto;
    private final int quantidade;

    public itemCarrinho(Produto produto, int quantidade) {
        this.produto = produto;
        if (quantidade <= 0){
            throw new ValorInvalido("Quantidade de itens invalida");
        }
        this.quantidade = quantidade;
    }

    public BigDecimal subtotal(){
        BigDecimal bigquantidade = BigDecimal.valueOf(quantidade);

        return produto.getPreco().multiply(bigquantidade);

    }


}
