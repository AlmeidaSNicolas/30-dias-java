package Dia_03_Agenda_Consultas;

import Dia_03_Agenda_Consultas.Exceptions.AgendamentoInvalidoException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class Agenda {
    private final List<Consulta> consultas = new ArrayList<>();
    private static final LocalTime ABERTURA = LocalTime.of(8,0);
    private static final LocalTime FECHAMENTO = LocalTime.of(18,0);

    public void cancelar(Consulta consulta){
        if (!consultas.contains(consulta)) {
            throw new AgendamentoInvalidoException("Essa consulta nao existe");
        }
        consultas.remove(consulta);
    }

    public Consulta agendar(Medico medico, String paciente, LocalDateTime inicio, int minutos) {
        Consulta nova = new Consulta( medico, paciente, inicio, minutos);

        if (inicio.isBefore(LocalDateTime.now())) {
            throw new AgendamentoInvalidoException("Recusado por estar no passado");
        }
        if (inicio.getDayOfWeek() == DayOfWeek.SUNDAY || inicio.getDayOfWeek() == DayOfWeek.SATURDAY){
            throw new AgendamentoInvalidoException("Nesses dias estamos fechados (Sabados e Domingos) ");
        }

        if (inicio.toLocalTime().isBefore(ABERTURA)){
            throw new AgendamentoInvalidoException("Inicio antes do horario em que estamos abertos");
        }
        if (nova.fim().toLocalTime().isAfter(FECHAMENTO)){
            throw new AgendamentoInvalidoException("Seu agendamento termina apos o fechamento da empresa");
        }

        for (Consulta consulta : consultas){
            if (consulta.getMedico() == (medico) && nova.sobrepor(consulta)){
                throw new AgendamentoInvalidoException("Esse medico ja tem consulta nesse momento");
            }
        }

        consultas.add(nova);

        return nova;
    }

    public List<Consulta> consultasDoDia(Medico medico, LocalDate dia) {
        List<Consulta> consultasDoDia = new ArrayList<>();

        for (Consulta consulta : consultas){
            if ( consulta.getMedico() == medico && consulta.getInicio().toLocalDate().equals(dia)){
                consultasDoDia.add(consulta);
            }
        }
        return consultasDoDia;
    }
}
