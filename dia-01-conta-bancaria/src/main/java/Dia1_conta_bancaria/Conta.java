package Dia1_conta_bancaria;


import Dia1_conta_bancaria.exceptions.SaldoInsuficienteException;
import Dia1_conta_bancaria.exceptions.ValorInvalidoException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Conta {
    private String titular;
    private BigDecimal limiteCheque;
    private BigDecimal saldo = BigDecimal.ZERO;
    private ArrayList<Movimentacao> listaMovimentação = new ArrayList<>();

    public Conta(String titular, BigDecimal limiteCheque) {
        this.titular = titular;
        this.limiteCheque = limiteCheque;
    }

    public void depositar(BigDecimal valor){
        creditar(valor, TipoMovimentacao.DEPOSITO, "Deposito");
    }

    public void imprimirExtrato(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.println();
        System.out.println("===== Extrato de " + titular + " =====");
        for (Movimentacao movimentacao1 : listaMovimentação){
            System.out.println(movimentacao1.getDataHora().format(formatter) + " | " + movimentacao1.getTipoMovimentacao() + " | " + "valor : " + movimentacao1.getValor() + " | " + " saldo : " +movimentacao1.getSaldoPosterior() );
        }
    }

    public void sacar(BigDecimal valor){
        debitar(valor, TipoMovimentacao.SAQUE, "Saque");
    }

    private void debitar(BigDecimal valor, TipoMovimentacao tipo, String descricao){
        if (valor.compareTo(BigDecimal.ZERO) <= 0  ){
            throw new ValorInvalidoException("Valor invalido inserido, possivelmente um valor negativo");
        }else{
            BigDecimal disponivel = saldo.add(limiteCheque);
            if (valor.compareTo(disponivel) > 0){
                throw new SaldoInsuficienteException("Valor inserido é maior que seu disponivel");
            }else {

                saldo = saldo.subtract(valor);

                Movimentacao movimentacao = new Movimentacao(LocalDateTime.now(), tipo, valor, saldo, descricao);
                listaMovimentação.add(movimentacao);

            }
        }
    }

    private void creditar(BigDecimal valor, TipoMovimentacao tipo, String descricao){
        if (valor.compareTo(BigDecimal.ZERO) <= 0){
            throw new ValorInvalidoException("Valor invalido inserido");
        }else {
            saldo = saldo.add(valor);

            Movimentacao movimentacao = new Movimentacao(LocalDateTime.now(), tipo, valor, saldo, descricao);
            listaMovimentação.add(movimentacao);

        }
    }

    public void transferir(Conta contaAlvo, BigDecimal valor){
        debitar(valor, TipoMovimentacao.TRANSFERENCIA, "Pix para : " + contaAlvo.titular);
        contaAlvo.creditar(valor, TipoMovimentacao.TRANSFERENCIA, "Transferencia de : " + titular );

    }

}
