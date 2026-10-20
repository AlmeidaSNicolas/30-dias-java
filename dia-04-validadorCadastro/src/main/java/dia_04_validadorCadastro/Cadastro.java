package dia_04_validadorCadastro;

import lombok.Getter;

import java.util.Locale;

@Getter
public class Cadastro {
    private final String nome;
    private final String email;
    private final String telefone;
    private final String senha;

    public Cadastro(String nome, String email, String telefone, String senha) {
        this.nome = nome.trim().replaceAll("\\s+", " ");
        this.email = email.trim().toLowerCase(Locale.ROOT);
        this.telefone = telefone.replaceAll("\\D", "");
        this.senha = senha;
    }
}
