package Dia_03_Agenda_Consultas;

import Dia_03_Agenda_Consultas.Exceptions.AgendamentoInvalidoException;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class Consulta {
    private final Medico medico;
    private final String paciente;
    private final LocalDateTime inicio;
    private final Duration duracao;

    public Consulta(Medico medico, String paciente, LocalDateTime inicio, int minutos) {
        this.medico = medico;
        this.paciente = paciente;
        this.inicio = inicio;

       if (minutos != 30 && minutos != 60) {
           throw new AgendamentoInvalidoException("Duração do agendamento deve ser ou 30 ou 60 minutos");
       }

       this.duracao = Duration.ofMinutes(minutos);
    }

    public LocalDateTime fim(){
       LocalDateTime fim = inicio.plus(duracao);

       return fim;

    }

    public boolean sobrepor(Consulta outra){
        boolean estaTerminaDepoisDaOutraComecar = fim().isAfter(outra.inicio);
        boolean SituacaoB = outra.fim().isAfter(inicio);
        return estaTerminaDepoisDaOutraComecar && SituacaoB;
    }
}
