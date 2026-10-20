package dia_04_validadorCadastro;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ResultadoValidacao {
    private final List<String> erros = new ArrayList<>();

    void adicionarErro(String mensagem){
        erros.add(mensagem);
    }

    boolean valido(){

        return erros.isEmpty();
    }

    void imprimir(){
        if (valido()){
            System.out.println("Cadastro valido");
        }else {
            for (String error : erros){
                System.out.println(error);
            }
        }
    }

}
