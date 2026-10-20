package dia_04_validadorCadastro;

public class ValidadorCadastro {
    ResultadoValidacao validar (Cadastro cadastro){
        ResultadoValidacao resultadoValidacao = new ResultadoValidacao();

       validarNome(cadastro, resultadoValidacao);
       validarTelefone(cadastro, resultadoValidacao);
       validarEmail(cadastro, resultadoValidacao);
       validarSenha(cadastro, resultadoValidacao);

        return resultadoValidacao;
    }

    private void validarNome(Cadastro cadastro, ResultadoValidacao resultadoValidacao){

       String[] palavras = cadastro.getNome().split(" ");

       if (palavras.length < 2){
           resultadoValidacao.adicionarErro("Nome precisa ter nome e sobrenome");
       }

    }

    private void validarTelefone(Cadastro cadastro, ResultadoValidacao resultadoValidacao){
        if (cadastro.getTelefone().length() != 11){
            resultadoValidacao.adicionarErro("Telefone precisa ter 11 digitos");
        }
    }

    private void validarEmail(Cadastro cadastro, ResultadoValidacao resultadoValidacao){

        String regex = "[^@\\s]+@[^@\\s]+\\.[^@\\s]+";

        if (!cadastro.getEmail().matches(regex)){
            resultadoValidacao.adicionarErro("Email nao se encaixa no padrão exigido");
        }


    }

    private void validarSenha(Cadastro cadastro, ResultadoValidacao resultadoValidacao){
        String contemNumero = ".*\\d.*";
        String contemMaiscula = ".*[A-Z].*";

        if (cadastro.getSenha().length() < 8){
            resultadoValidacao.adicionarErro("Sua senha precisa ter 8 digitos ou mais");
        }
        if (!cadastro.getSenha().matches(contemNumero)) {
            resultadoValidacao.adicionarErro("Sua senha precisa conter pelo menos Um numero");
        }
        if (!cadastro.getSenha().matches(contemMaiscula)) {
            resultadoValidacao.adicionarErro("Sua senha precisa conter pelo menos uma Letra maiscula");
        }

    }

}
