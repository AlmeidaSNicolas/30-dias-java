package Dia1_conta_bancaria;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
public class Movimentacao {
    private final LocalDateTime dataHora;
    private final TipoMovimentacao tipoMovimentacao;
    private final BigDecimal valor;
    private final BigDecimal saldoPosterior;
    private final String descricao;

    public Movimentacao(LocalDateTime dataHora, TipoMovimentacao tipoMovimentacao, BigDecimal valor, BigDecimal saldoPosterior, String descricao) {
        this.dataHora = dataHora;
        this.tipoMovimentacao = tipoMovimentacao;
        this.valor = valor;
        this.saldoPosterior = saldoPosterior;
        this.descricao = descricao;
    }
}
