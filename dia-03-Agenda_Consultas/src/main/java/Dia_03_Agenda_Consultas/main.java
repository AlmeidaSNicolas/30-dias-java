package Dia_03_Agenda_Consultas;


import Dia_03_Agenda_Consultas.Exceptions.AgendamentoInvalidoException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;


public class main {
    public static void main(String[] args) {

        try {
            Medico medico = new Medico("jOAO", "CARDIO");

            LocalDateTime inicio = LocalDateTime.of(2020, Month.JANUARY, 1, 9, 0, 0);
            LocalDateTime inicioDepois = LocalDateTime.of(2020, Month.JANUARY, 1, 10, 0, 0);

            Consulta consulta = new Consulta(medico, "leandro", inicio, 60);
            Consulta corrida = new Consulta(medico, "corrida", inicioDepois, 30);

            System.out.println(corrida.sobrepor(consulta));

            System.out.println(consulta.sobrepor(corrida));

            System.out.println(consulta.fim());

        }catch (AgendamentoInvalidoException e){
            System.out.println(e);
            e.printStackTrace();
        }

    }
}
