package dia_04_validadorCadastro;


public class main {

    // Dados válidos usados como base: cada critério troca só o campo que está testando
    static final String NOME = "Ana Souza";
    static final String EMAIL = "ana@email.com";
    static final String TELEFONE = "(11) 91234-5678";
    static final String SENHA = "Senha123";

    static final ValidadorCadastro validador = new ValidadorCadastro();

    public static void main(String[] args) {

        //Teste Criterio 1: cadastro todo certo não tem nenhum erro e é válido
        System.out.println("##### CRITERIO 1 #####");
        ResultadoValidacao certo = validador.validar(new Cadastro(NOME, EMAIL, TELEFONE, SENHA));
        System.out.println("Esperado: 0 erros, válido = true | Obtido: " + certo.getErros().size() + " erros, válido = " + certo.valido());

        //Teste Criterio 2: nome, e-mail e telefone errados (senha certa) devolvem três mensagens
        System.out.println();
        System.out.println("##### CRITERIO 2 #####");
        ResultadoValidacao tresErros = validador.validar(new Cadastro("Ana", "ana.email.com", "1234-5678", SENHA));
        System.out.println("Esperado: 3 erros, válido = false | Obtido: " + tresErros.getErros().size() + " erros, válido = " + tresErros.valido());
        tresErros.imprimir();

        //Teste Criterio 3: nome com uma palavra é rejeitado; nome com espaços sobrando é aceito e normalizado
        System.out.println();
        System.out.println("##### CRITERIO 3 #####");
        ResultadoValidacao umaPalavra = validador.validar(new Cadastro("Ana", EMAIL, TELEFONE, SENHA));
        System.out.println("Esperado: \"Ana\" rejeitado | Obtido: " + umaPalavra.getErros());
        Cadastro nomeSujo = new Cadastro("  Ana   Souza  ", EMAIL, TELEFONE, SENHA);
        System.out.println("Esperado: válido = true | Obtido: válido = " + validador.validar(nomeSujo).valido());
        System.out.println("Esperado: [Ana Souza] | Obtido: [" + nomeSujo.getNome() + "]");

        //Teste Criterio 4: telefone com ou sem pontuação é aceito e guardado só com dígitos
        System.out.println();
        System.out.println("##### CRITERIO 4 #####");
        Cadastro comPontuacao = new Cadastro(NOME, EMAIL, "(11) 91234-5678", SENHA);
        Cadastro soDigitos = new Cadastro(NOME, EMAIL, "11912345678", SENHA);
        System.out.println("Esperado: (11) 91234-5678 aceito como 11912345678 | Obtido: válido = "
                + validador.validar(comPontuacao).valido() + ", guardado como " + comPontuacao.getTelefone());
        System.out.println("Esperado: 11912345678 aceito como 11912345678 | Obtido: válido = "
                + validador.validar(soDigitos).valido() + ", guardado como " + soDigitos.getTelefone());
        ResultadoValidacao telefoneCurto = validador.validar(new Cadastro(NOME, EMAIL, "1234-5678", SENHA));
        System.out.println("Esperado: 1234-5678 rejeitado | Obtido: " + telefoneCurto.getErros());

        //Teste Criterio 5: e-mail sem @ ou sem ponto depois do @ é rejeitado; e-mail é normalizado
        System.out.println();
        System.out.println("##### CRITERIO 5 #####");
        ResultadoValidacao semArroba = validador.validar(new Cadastro(NOME, "ana.email.com", TELEFONE, SENHA));
        System.out.println("Esperado: ana.email.com rejeitado | Obtido: " + semArroba.getErros());
        ResultadoValidacao semPonto = validador.validar(new Cadastro(NOME, "ana@email", TELEFONE, SENHA));
        System.out.println("Esperado: ana@email rejeitado | Obtido: " + semPonto.getErros());
        Cadastro emailSujo = new Cadastro(NOME, " Ana@Email.com ", TELEFONE, SENHA);
        System.out.println("Esperado: [ana@email.com] | Obtido: [" + emailSujo.getEmail() + "]");

        //Teste Criterio 6: senha "abc" devolve três mensagens (curta, sem número, sem maiúscula)
        System.out.println();
        System.out.println("##### CRITERIO 6 #####");
        ResultadoValidacao senhaFraca = validador.validar(new Cadastro(NOME, EMAIL, TELEFONE, "abc"));
        System.out.println("Esperado: 3 erros | Obtido: " + senhaFraca.getErros().size() + " erros");
        senhaFraca.imprimir();
    }
}
