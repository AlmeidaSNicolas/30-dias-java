package Dia1_conta_bancaria;

import Dia1_conta_bancaria.exceptions.SaldoInsuficienteException;
import Dia1_conta_bancaria.exceptions.ValorInvalidoException;

import java.math.BigDecimal;

public class main {
    public static void main(String[] args)  {

        //Teste Criterio 1: saque acima de saldo + limite lança exceção e o saldo não muda
        System.out.println("##### CRITERIO 1 #####");
        Conta contaTeste1 = new Conta("Nicolas", BigDecimal.ZERO);
        contaTeste1.depositar(new BigDecimal(100));
        contaTeste1.imprimirExtrato();
        try {
            contaTeste1.sacar(new BigDecimal(500));
            System.out.println("FALHOU: o saque deveria ter sido recusado");
        } catch (SaldoInsuficienteException e) {
            System.out.println("Esperado: SaldoInsuficienteException | Obtido: " + e.getMessage());
        }
        System.out.println("Extrato depois (esperado: igual ao de antes, saldo 100)");
        contaTeste1.imprimirExtrato();

        //Teste Criterio 2: se a transferência falhar, nenhuma das duas contas é alterada
        System.out.println();
        System.out.println("##### CRITERIO 2 #####");
        Conta contaAna = new Conta("Ana", BigDecimal.ZERO);
        Conta contaJoao = new Conta("Joao", BigDecimal.ZERO);
        contaAna.depositar(new BigDecimal(100));
        contaJoao.depositar(new BigDecimal(50));
        contaAna.imprimirExtrato();
        contaJoao.imprimirExtrato();
        try {
            contaAna.transferir(contaJoao, new BigDecimal(300));
            System.out.println("FALHOU: a transferência deveria ter sido recusada");
        } catch (SaldoInsuficienteException e) {
            System.out.println("Esperado: SaldoInsuficienteException | Obtido: " + e.getMessage());
        }
        System.out.println("Extratos depois (esperado: Ana 100 e Joao 50, sem nova movimentação)");
        contaAna.imprimirExtrato();
        contaJoao.imprimirExtrato();

        //Teste Criterio 3: depósito ou saque com valor zero ou negativo é rejeitado
        System.out.println();
        System.out.println("##### CRITERIO 3 #####");
        Conta contaTeste3 = new Conta("Nicolas", new BigDecimal(100));
        try {
            contaTeste3.depositar(new BigDecimal(-50));
            System.out.println("FALHOU: o depósito negativo deveria ter sido recusado");
        } catch (ValorInvalidoException e) {
            System.out.println("Esperado: ValorInvalidoException | Obtido: " + e.getMessage());
        }
        try {
            contaTeste3.sacar(BigDecimal.ZERO);
            System.out.println("FALHOU: o saque de zero deveria ter sido recusado");
        } catch (ValorInvalidoException e) {
            System.out.println("Esperado: ValorInvalidoException | Obtido: " + e.getMessage());
        }
        System.out.println("Extrato depois (esperado: vazio)");
        contaTeste3.imprimirExtrato();
    }
}
