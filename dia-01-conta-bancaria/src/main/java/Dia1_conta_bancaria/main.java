package Dia1_conta_bancaria;

import java.math.BigDecimal;

public class main {
    public static void main(String[] args)  {

        //Teste Criterio 1
        //Conta contaTeste1 = new Conta("Nicolas", BigDecimal.ZERO);
        //contaTeste1.depositar(new BigDecimal(100));
        //contaTeste1.sacar(new BigDecimal(500));
        //Excecao capturada Criterio 1 ok

        //Teste criterio 2
        //Conta contaTeste2 = new Conta("Ana", BigDecimal.ZERO);
        //Conta contaTeste22 = new Conta("Joao", BigDecimal.ZERO);
        //contaTeste2.depositar(new BigDecimal(100));
        //contaTeste22.depositar(new BigDecimal(50));

        //contaTeste2.transferir(contaTeste22, new BigDecimal(300));
        //Excecao capturada Criterio 2 ok

        //Teste criterio 3
        Conta conta4 = new Conta("Nicolas", new BigDecimal(100));
        conta4.depositar(new BigDecimal(-50));
        //Excecao capturada Criterio 3 ok
    }
}
