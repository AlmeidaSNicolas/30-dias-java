package Dia_03_Agenda_Consultas;

import Dia_03_Agenda_Consultas.Exceptions.AgendamentoInvalidoException;

import java.util.ArrayList;
import java.util.List;

public class Agenda {
    private final List<Consulta> consultas = new ArrayList<>();


    public void cancelar(Consulta consulta){
        if (!consultas.contains(consulta)) {
            throw new AgendamentoInvalidoException("Essa consulta nao existe");
        }
        consultas.remove(consulta);
        System.out.println("Consulta cancelada");
    }

}
