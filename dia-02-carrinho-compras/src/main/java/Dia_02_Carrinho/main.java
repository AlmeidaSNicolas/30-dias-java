package Dia_02_Carrinho;

import Dia_02_Carrinho.Exceptions.ValorInvalido;

import java.math.BigDecimal;

public class main {
    public static void main(String[] args) {

        //Teste Criterio 1: produto com preço zero ou negativo é rejeitado com exceção
        System.out.println("##### CRITERIO 1 #####");
        try {
            new Produto("Brinde", BigDecimal.ZERO);
            System.out.println("FALHOU: o produto com preço zero deveria ter sido recusado");
        } catch (ValorInvalido e) {
            System.out.println("Esperado: ValorInvalido | Obtido: " + e.getMessage());
        }
        try {
            new Produto("Brinde", new BigDecimal("-10.00"));
            System.out.println("FALHOU: o produto com preço negativo deveria ter sido recusado");
        } catch (ValorInvalido e) {
            System.out.println("Esperado: ValorInvalido | Obtido: " + e.getMessage());
        }

        //Teste Criterio 2: item com quantidade zero ou negativa é rejeitado com exceção
        System.out.println();
        System.out.println("##### CRITERIO 2 #####");
        Produto camiseta = new Produto("Camiseta", new BigDecimal("49.90"));
        try {
            new itemCarrinho(camiseta, 0);
            System.out.println("FALHOU: o item com quantidade zero deveria ter sido recusado");
        } catch (ValorInvalido e) {
            System.out.println("Esperado: ValorInvalido | Obtido: " + e.getMessage());
        }
        try {
            new itemCarrinho(camiseta, -1);
            System.out.println("FALHOU: o item com quantidade negativa deveria ter sido recusado");
        } catch (ValorInvalido e) {
            System.out.println("Esperado: ValorInvalido | Obtido: " + e.getMessage());
        }

        //Teste Criterio 3: 2 Camisetas (49.90) e 1 Calça (129.90) têm total 229.70
        System.out.println();
        System.out.println("##### CRITERIO 3 #####");
        Produto calca = new Produto("Calça", new BigDecimal("129.90"));
        Carrinho carrinho = new Carrinho();
        carrinho.adicionarItens(camiseta, 2);
        carrinho.adicionarItens(calca, 1);
        System.out.println("Esperado: 229.70 | Obtido: " + carrinho.total());
        carrinho.imprimirResumo();

        //Teste Criterio 4: cupom de 10% sobre 229.70 dá 206.73, e o total sem cupom continua 229.70
        System.out.println();
        System.out.println("##### CRITERIO 4 #####");
        System.out.println("Esperado: 206.73 | Obtido: " + carrinho.totalComCupom(10));
        System.out.println("Total sem cupom depois (esperado: 229.70) | Obtido: " + carrinho.total());

        //Teste Criterio 5: cupom negativo ou acima de 50% é rejeitado com exceção
        System.out.println();
        System.out.println("##### CRITERIO 5 #####");
        try {
            carrinho.totalComCupom(-5);
            System.out.println("FALHOU: o cupom negativo deveria ter sido recusado");
        } catch (ValorInvalido e) {
            System.out.println("Esperado: ValorInvalido | Obtido: " + e.getMessage());
        }
        try {
            carrinho.totalComCupom(60);
            System.out.println("FALHOU: o cupom acima de 50% deveria ter sido recusado");
        } catch (ValorInvalido e) {
            System.out.println("Esperado: ValorInvalido | Obtido: " + e.getMessage());
        }

        //Teste Criterio 6: carrinho vazio tem total 0 e não lança exceção
        System.out.println();
        System.out.println("##### CRITERIO 6 #####");
        Carrinho carrinhoVazio = new Carrinho();
        System.out.println("Esperado: 0 | Obtido: " + carrinhoVazio.total());
    }
}
