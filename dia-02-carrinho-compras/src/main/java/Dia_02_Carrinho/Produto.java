package Dia_02_Carrinho;

import Dia_02_Carrinho.Exceptions.ValorInvalido;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Produto {
    private final String nome;
    private final BigDecimal preco;

    public Produto(String nome, BigDecimal preco) {
        this.nome = nome;
        this.preco = preco;

        if (preco.compareTo(BigDecimal.ZERO) <= 0){
            throw new ValorInvalido("Preço Inserido é invalido");
        }
    }
}
