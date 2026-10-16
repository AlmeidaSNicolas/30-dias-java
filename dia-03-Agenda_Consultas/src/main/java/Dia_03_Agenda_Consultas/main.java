package Dia_03_Agenda_Consultas;


import Dia_03_Agenda_Consultas.Exceptions.AgendamentoInvalidoException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;


public class main {
    public static void main(String[] args) {

        Medico medico = new Medico("Nicolas", "Cardio");
        Agenda agenda = new Agenda();

        // Datas calculadas a partir de hoje, para nunca virarem passado
        LocalDate segunda = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        LocalDate sabado = segunda.plusDays(5);
        LocalDate segundaPassada = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY));

        //Teste Criterio 1: consulta que se sobrepõe a outra do mesmo médico é rejeitada, inclusive parcial
        System.out.println("##### CRITERIO 1 #####");
        Agenda agendaSobreposicao = new Agenda();
        Medico outroMedico = new Medico("Ana", "Pediatra");
        LocalDate terca = segunda.plusDays(1);
        agendaSobreposicao.agendar(medico, "Luan", terca.atTime(9, 0), 60);   // 9h00–10h00 já marcada
        try {
            agendaSobreposicao.agendar(medico, "Pedro", terca.atTime(9, 30), 30);
            System.out.println("FALHOU: 9h30–10h00 deveria ter sido recusada (sobreposição parcial)");
        } catch (AgendamentoInvalidoException e) {
            System.out.println("Esperado: 9h30–10h00 recusada | Obtido: " + e.getMessage());
        }
        try {
            agendaSobreposicao.agendar(medico, "Pedro", terca.atTime(8, 30), 60);
            System.out.println("FALHOU: 8h30–9h30 deveria ter sido recusada (sobreposição parcial)");
        } catch (AgendamentoInvalidoException e) {
            System.out.println("Esperado: 8h30–9h30 recusada | Obtido: " + e.getMessage());
        }
        try {
            agendaSobreposicao.agendar(medico, "Pedro", terca.atTime(9, 0), 30);
            System.out.println("FALHOU: 9h00–9h30 deveria ter sido recusada (mesmo início)");
        } catch (AgendamentoInvalidoException e) {
            System.out.println("Esperado: 9h00–9h30 recusada | Obtido: " + e.getMessage());
        }
        try {
            agendaSobreposicao.agendar(outroMedico, "Pedro", terca.atTime(9, 0), 60);
            System.out.println("Esperado: outro médico às 9h00 aceito | Obtido: aceito");
        } catch (AgendamentoInvalidoException e) {
            System.out.println("FALHOU: outro médico às 9h00 deveria ser aceito. Mensagem: " + e.getMessage());
        }

        //Teste Criterio 2: consulta que termina exatamente quando a outra começa é aceita
        System.out.println();
        System.out.println("##### CRITERIO 2 #####");
        try {
            agendaSobreposicao.agendar(medico, "Pedro", terca.atTime(10, 0), 30);
            System.out.println("Esperado: 10h00–10h30 aceita (encosta no fim) | Obtido: aceita");
        } catch (AgendamentoInvalidoException e) {
            System.out.println("FALHOU: 10h00–10h30 deveria ser aceita. Mensagem: " + e.getMessage());
        }
        try {
            agendaSobreposicao.agendar(medico, "Pedro", terca.atTime(8, 30), 30);
            System.out.println("Esperado: 8h30–9h00 aceita (encosta no início) | Obtido: aceita");
        } catch (AgendamentoInvalidoException e) {
            System.out.println("FALHOU: 8h30–9h00 deveria ser aceita. Mensagem: " + e.getMessage());
        }

        //Teste Criterio 3: fim de semana ou fora do expediente é rejeitado com mensagem que diz o motivo
        System.out.println();
        System.out.println("##### CRITERIO 3 #####");
        try {
            Consulta consulta = agenda.agendar(medico, "Luan", segunda.atTime(9, 0), 30);
            System.out.println("Esperado: segunda às 9h00 aceita | Obtido: aceita");
            imprimirDadosConsulta(consulta);
        } catch (AgendamentoInvalidoException e) {
            System.out.println("FALHOU: segunda às 9h00 deveria ser aceita. Mensagem: " + e.getMessage());
        }
        try {
            agenda.agendar(medico, "Luan", sabado.atTime(9, 0), 30);
            System.out.println("FALHOU: sábado deveria ter sido recusado");
        } catch (AgendamentoInvalidoException e) {
            System.out.println("Esperado: recusa por fim de semana | Obtido: " + e.getMessage());
        }
        try {
            agenda.agendar(medico, "Luan", segunda.atTime(7, 30), 30);
            System.out.println("FALHOU: 7h30 deveria ter sido recusado (antes de abrir)");
        } catch (AgendamentoInvalidoException e) {
            System.out.println("Esperado: recusa por antes da abertura | Obtido: " + e.getMessage());
        }
        try {
            agenda.agendar(medico, "Luan", segunda.atTime(17, 30), 60);
            System.out.println("FALHOU: 17h30 com 60 min deveria ter sido recusado (termina 18h30)");
        } catch (AgendamentoInvalidoException e) {
            System.out.println("Esperado: recusa por terminar depois do fechamento | Obtido: " + e.getMessage());
        }
        try {
            Consulta consulta = agenda.agendar(medico, "Luan", segunda.atTime(17, 30), 30);
            System.out.println("Esperado: 17h30 com 30 min aceita, fim 18:00 | Obtido: aceita, fim " + consulta.fim().toLocalTime());
        } catch (AgendamentoInvalidoException e) {
            System.out.println("FALHOU: 17h30 com 30 min termina 18h00 e deveria ser aceita. Mensagem: " + e.getMessage());
        }

        //Teste Criterio 4: consulta no passado é rejeitada
        System.out.println();
        System.out.println("##### CRITERIO 4 #####");
        try {
            agenda.agendar(medico, "Luan", segundaPassada.atTime(9, 0), 30);
            System.out.println("FALHOU: segunda passada deveria ter sido recusada");
        } catch (AgendamentoInvalidoException e) {
            System.out.println("Esperado: recusa por passado | Obtido: " + e.getMessage());
        }

        //Teste Criterio 5: duração diferente de 30 ou 60 minutos é rejeitada
        System.out.println();
        System.out.println("##### CRITERIO 5 #####");
        try {
            agenda.agendar(medico, "Luan", segunda.atTime(14, 0), 45);
            System.out.println("FALHOU: 45 minutos deveria ter sido recusado");
        } catch (AgendamentoInvalidoException e) {
            System.out.println("Esperado: 45 min recusado | Obtido: " + e.getMessage());
        }
        try {
            agenda.agendar(medico, "Luan", segunda.atTime(14, 0), 0);
            System.out.println("FALHOU: 0 minutos deveria ter sido recusado");
        } catch (AgendamentoInvalidoException e) {
            System.out.println("Esperado: 0 min recusado | Obtido: " + e.getMessage());
        }
        try {
            agenda.agendar(medico, "Luan", segunda.atTime(14, 0), 60);
            System.out.println("Esperado: 60 min aceito | Obtido: aceito");
        } catch (AgendamentoInvalidoException e) {
            System.out.println("FALHOU: 60 minutos deveria ser aceito. Mensagem: " + e.getMessage());
        }

        //Teste Criterio 6: depois de cancelar, o mesmo horário pode ser agendado de novo
        System.out.println();
        System.out.println("##### CRITERIO 6 #####");
        Agenda agendaCancelamento = new Agenda();
        LocalDate quarta = segunda.plusDays(2);
        Consulta marcada = agendaCancelamento.agendar(medico, "Luan", quarta.atTime(14, 0), 30);
        try {
            agendaCancelamento.agendar(medico, "Pedro", quarta.atTime(14, 0), 30);
            System.out.println("FALHOU: antes de cancelar, 14h00 deveria estar ocupado");
        } catch (AgendamentoInvalidoException e) {
            System.out.println("Esperado: antes de cancelar, 14h00 recusado | Obtido: " + e.getMessage());
        }
        agendaCancelamento.cancelar(marcada);
        try {
            agendaCancelamento.agendar(medico, "Pedro", quarta.atTime(14, 0), 30);
            System.out.println("Esperado: depois de cancelar, 14h00 aceito | Obtido: aceito");
        } catch (AgendamentoInvalidoException e) {
            System.out.println("FALHOU: depois de cancelar, 14h00 deveria ser aceito. Mensagem: " + e.getMessage());
        }
        try {
            agendaCancelamento.cancelar(marcada);
            System.out.println("FALHOU: cancelar de novo uma consulta já cancelada deveria dar erro");
        } catch (AgendamentoInvalidoException e) {
            System.out.println("Esperado: cancelar duas vezes recusado | Obtido: " + e.getMessage());
        }

        //Teste Requisito 5: listar as consultas de um médico em um dia
        System.out.println();
        System.out.println("##### REQUISITO 5: CONSULTAS DO DIA #####");
        // Na terça, a agendaSobreposicao tem 3 consultas do Nicolas (8h30, 9h00, 10h00) e 1 da Ana (9h00)
        System.out.println("Esperado: Nicolas na terça = 3 | Obtido: " + agendaSobreposicao.consultasDoDia(medico, terca).size());
        System.out.println("Esperado: Ana na terça = 1 | Obtido: " + agendaSobreposicao.consultasDoDia(outroMedico, terca).size());
        System.out.println("Esperado: Nicolas na quarta = 0 | Obtido: " + agendaSobreposicao.consultasDoDia(medico, quarta).size());
        for (Consulta consulta : agendaSobreposicao.consultasDoDia(medico, terca)) {
            imprimirDadosConsulta(consulta);
        }

    }

    public static void imprimirDadosConsulta(Consulta consulta){
        System.out.println();
        System.out.println("Inicio : " + consulta.getInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        System.out.println("Paciente : " + consulta.getPaciente());
        System.out.println("Medico : " + consulta.getMedico().getNome());
        System.out.println("Especialidade : " + consulta.getMedico().getEspecialidade());
        System.out.println("Duração : " + consulta.getDuracao().toMinutes() + " minutos");
        System.out.println();
    }
}
