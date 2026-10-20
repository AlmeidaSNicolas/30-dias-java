# Dia 04: Validador de cadastro

## Ticket

O formulário de cadastro de um site responde só "dados inválidos", e o usuário não sabe o que corrigir. Ainda não há tela nem banco de dados: só o domínio, testado por uma classe com `main`.

**Requisitos**
1. Um cadastro tem nome, e-mail, telefone e senha, digitados pelo usuário.
2. O cadastro guarda os dados arrumados (normalizados):
   - **Nome:** sem espaços nas pontas e sem espaços repetidos no meio (`"  Ana   Souza "` vira `"Ana Souza"`).
   - **E-mail:** sem espaços nas pontas e em minúsculas (`" Ana@Email.com "` vira `"ana@email.com"`).
   - **Telefone:** só os dígitos (`"(11) 91234-5678"` vira `"11912345678"`).
   - **Senha:** guardada como veio.
3. Regras de validação:
   - **Nome:** pelo menos duas palavras (nome e sobrenome).
   - **E-mail:** formato `algo@dominio.ext`.
   - **Telefone:** exatamente 11 dígitos.
   - **Senha:** pelo menos 8 caracteres, pelo menos um número e pelo menos uma letra maiúscula.
4. A validação devolve **todos** os erros de uma vez, não só o primeiro. Cada regra quebrada gera a sua mensagem.

**Critérios de aceite** (todos provados no `main` em 20/10/2026)
- [x] Cadastro todo certo não tem nenhum erro e é válido.
- [x] Cadastro com nome, e-mail e telefone errados (e senha certa) devolve três mensagens, uma por campo, e não é válido.
- [x] Nome `"Ana"` é rejeitado; `"  Ana   Souza  "` é aceito e guardado como `"Ana Souza"`.
- [x] Telefone `"(11) 91234-5678"` e `"11912345678"` são aceitos e guardados como `"11912345678"`; `"1234-5678"` é rejeitado.
- [x] E-mail `"ana.email.com"` (sem `@`) e `"ana@email"` (sem ponto depois do `@`) são rejeitados; `" Ana@Email.com "` é guardado como `"ana@email.com"`.
- [x] Senha `"abc"` devolve três mensagens: curta, sem número e sem maiúscula.

**Restrição:** a validação **não lança exceção**. Erro de validação é resultado, não exceção.

## Destrinchado

| Classe | O que guarda | O que faz |
|---|---|---|
| `Cadastro` | nome, email, telefone, senha (todos `String`) | No construtor, recebe o que o usuário digitou e guarda já normalizado. Não valida nada. |
| `ResultadoValidacao` | lista de erros (`List<String>`) | Recebe erros, diz se é válido (lista vazia) e imprime os erros. |
| `ValidadorCadastro` | nada | Recebe um `Cadastro`, confere cada regra e devolve um `ResultadoValidacao` com tudo o que deu errado. |

Não tem classe de exceção hoje: nenhuma regra deste ticket lança exceção.

**A ideia nova do dia: erro como resultado.** Nos dias 01 a 03, regra quebrada lançava exceção, e a exceção para tudo no primeiro problema. Isso serve para um saque sem saldo: não tem o que continuar. Num formulário, o usuário errou o e-mail **e** o telefone e quer saber dos dois de uma vez. Por isso o validador não para: ele anota cada erro num objeto e devolve esse objeto no fim.

**Por que `ResultadoValidacao`, e não devolver uma `List<String>` solta?** Quem recebe uma lista solta tem que saber que "lista vazia significa válido". Com a classe, quem usa pergunta `resultado.valido()` e pronto: a regra de "o que é ser válido" fica num lugar só. E se amanhã o resultado precisar dizer **qual campo** errou, muda só essa classe.

**Por que a normalização fica no `Cadastro` e a validação no `ValidadorCadastro`?** Normalizar é arrumar o dado e não depende de regra nenhuma: telefone com ou sem parênteses é o mesmo telefone. Validar é decidir se o dado serve, e essa regra é do site (outro site pode aceitar telefone fixo de 10 dígitos). Normalizando antes, o validador já recebe tudo limpo e não precisa se preocupar com espaço sobrando nem com parênteses.

## Caminho

Siga na ordem. Rode o `main` ao fim de cada passo.

- [x] **0. Módulo e pacote:** módulo `dia-04-validadorCadastro` (registrado no `pom.xml` da raiz), pacote `dia_04_validadorCadastro`.
- [x] **1. `Cadastro`:** quatro campos `private final String` (`nome`, `email`, `telefone`, `senha`) e `@Getter`.
  - Construtor: `Cadastro(String nome, String email, String telefone, String senha)`.
  - Cada campo recebe o valor já normalizado (requisito 2). Os métodos de `String` que resolvem: `trim()`, `toLowerCase()` e `replaceAll(regex, troca)`.
  - Teste: `new Cadastro("  Ana   Souza ", " Ana@Email.com ", "(11) 91234-5678", "Senha123")` e imprima os getters.
- [x] **2. `ResultadoValidacao`:** um campo `private final List<String> erros = new ArrayList<>();`, sem construtor (nasce sem erros), e `@Getter`.
  - `void adicionarErro(String mensagem)`: coloca na lista.
  - `boolean valido()`: `true` se a lista estiver vazia (`isEmpty()`).
  - `void imprimir()`: se for válido, imprime `Cadastro válido`; senão, uma linha por erro.
- [x] **3. `ValidadorCadastro`:** sem campos e sem construtor.
  - `ResultadoValidacao validar(Cadastro cadastro)`: cria um `ResultadoValidacao`, chama os quatro métodos abaixo passando o cadastro e o resultado, e devolve o resultado.
  - Um método `private` por campo, cada um com a assinatura `void validarNome(Cadastro cadastro, ResultadoValidacao resultado)` (e `validarEmail`, `validarTelefone`, `validarSenha`). Cada um só adiciona erro; nenhum devolve nada nem lança exceção.
- [x] **4. `validarNome`:** o nome já vem sem espaço sobrando, então separar pelas palavras é `split(" ")`, que devolve um array. Menos de 2 posições: erro.
- [x] **5. `validarTelefone`:** o telefone já vem só com dígitos. Tamanho diferente de 11 (`length()`): erro.
- [x] **6. `validarEmail`:** use `matches(regex)`, que devolve `true` se a String **inteira** casa com o padrão. O padrão do e-mail é `"[^@\\s]+@[^@\\s]+\\.[^@\\s]+"` (lido na tabela abaixo: "um ou mais caracteres que não são `@` nem espaço, um `@`, de novo, um ponto, de novo").
- [x] **7. `validarSenha`:** três `if` separados, **sem `else`**, cada um com a sua mensagem. Com `else if`, a senha `"abc"` daria uma mensagem só e o critério 6 falha.
  - Tamanho: `length()`.
  - Número e maiúscula: `matches` com um padrão montado com a tabela abaixo. Lembre que o `matches` olha a String inteira, então o padrão precisa aceitar "qualquer coisa antes e depois".
- [x] **8. `main` com os critérios:** escrito pelo Claude a pedido do Nicolas (como nos dias 02 e 03). Prova os 6 critérios a partir de um cadastro válido, trocando só o campo que cada critério testa.

**Regex que você vai usar hoje**

| Pedaço | Significa |
|---|---|
| `\\d` | um dígito (0 a 9) |
| `\\D` | um caractere que **não** é dígito |
| `\\s` | um espaço (ou tab, quebra de linha) |
| `[A-Z]` | uma letra maiúscula |
| `[^@\\s]` | um caractere que não é `@` nem espaço |
| `.` | qualquer caractere |
| `\\.` | um ponto de verdade |
| `+` | o anterior, uma ou mais vezes |
| `*` | o anterior, zero ou mais vezes |

As barras são duplas porque, dentro de uma String Java, `\` sozinha é caractere especial: `"\\d"` vira `\d` para a regex.

**Armadilhas conhecidas**
- `String` é imutável: `nome.trim()` sozinho não muda nada, tem que guardar o resultado. É a mesma armadilha do `BigDecimal.add`.
- `matches` olha a String inteira: `"abc1".matches("\\d")` é `false`, porque a String não é **só** um dígito.
- `trim()` antes de trocar espaços repetidos, senão sobra um espaço em cada ponta.
- Não use exceção para nenhuma regra deste ticket. Se aparecer um `throw` no validador, algo saiu do design.

## Decisões

Até agora, nenhuma decisão fora do Destrinchado.

## Pendências

Nenhuma. Resolvidas antes de fechar o dia (20/10/2026):

- [x] Classe renomeada de `ValidarCadastro` para `ValidadorCadastro`, como no Destrinchado.
- [x] Limpeza no `validarEmail`: saíram a linha `matches` solta antes do `if` e os imports de `Pattern` e `Matcher`.
- [x] Classe `teste` apagada; quem prova os critérios é o `main`.

## Dúvidas e travamentos

- **`NullPointerException` no construtor do `Cadastro`:** usei `this.getNome()` como segundo argumento do `replaceAll`. Dentro do construtor, o campo ainda não tem valor enquanto a linha que atribui está rodando, então o getter devolvia `null`. Quem tem o valor nessa hora é o parâmetro.
- **O que vai no segundo argumento do `replaceAll`:** é uma `String` comum que entra no lugar de cada trecho achado pela regex. Primeiro troquei por `""` e o nome virou `"AnaSouza"`. O que destravou: sem o `+`, a regex casa um espaço por vez; com `\\s+`, cada sequência de espaços é um trecho só, trocado uma vez por `" "`. Não precisa de `for`: o `replaceAll` já percorre a `String` inteira.
- **Ternário no `valido()`:** fiz `erros.isEmpty() ? true : false`. O `isEmpty()` já devolve `boolean`, então o `return` pode devolver ele direto.
- **De onde vem a `String` do erro no `imprimir()`:** não entendia de onde vinha a mensagem, porque escrevi quem recebe os erros antes de quem manda. Quem escreve a mensagem é o validador, chamando `adicionarErro`; o `imprimir` só percorre a lista e imprime o que já está lá. Testei fazendo o papel do validador: adicionei dois erros na mão e chamei `imprimir()`.
- **`validarX` com `sout` em vez de `adicionarErro`:** o `sout` só mostra o texto e esquece; a lista continuava vazia e o cadastro errado passaria como válido. O erro tem que ir para o resultado com `adicionarErro`, e só dentro de um `if` que confere a regra. A mensagem é uma frase escrita por mim, dizendo o que corrigir.
- **Teste sem resposta:** fiz `System.out.println(validador.validar(cadastro))` e saiu `ResultadoValidacao@7e9e5f8a`. Imprimir um objeto mostra o nome da classe e um código, não a lista de dentro. O certo é guardar o resultado numa variável e chamar `imprimir()` nele. Testei com `"Nicola"` (saiu o erro do nome) e com `"Nicolas Sales"` (saiu `Cadastro valido`).
- **Telefone com `< 11`:** pegava o curto, mas deixava passar telefone com 12 dígitos. A regra é exatamente 11, então é `!= 11`.
- **E-mail: `while` ou `for` com o `Matcher`?** Nenhum dos dois. `find()` procura vários pedaços dentro de um texto e por isso vem num `while`; `matches()` responde se o texto **inteiro** casa, uma vez só. Para validar um campo é `matches()`. Usei o atalho `cadastro.getEmail().matches(regex)` direto no `if`, com `!` na frente: o erro entra quando o e-mail **não** casa, sem precisar comparar com `true`.
- **Senha com `contains`:** o `contains` procura o texto ao pé da letra, então `contains("\\d")` procurava uma barra e um `d`, e o erro de número saía para qualquer senha. Quem entende regex é o `matches`. Também estava com `sout` e `else if`, os mesmos problemas do começo: troquei para `adicionarErro` e três `if` separados. O teste com `"678"` me enganou, porque deu três erros quando o certo eram dois (`"678"` tem número): testar só senha errada não pega o bug, tem que testar a certa também.
- **Onde vai o `.*` no padrão da senha:** como o `matches` precisa cobrir a senha inteira, `"\\d.*"` quer dizer "começa com dígito" e `".*\\d"` quer dizer "termina com dígito". Para "tem um dígito em algum lugar", o pedaço que eu procuro aparece uma vez e o `.*` vai dos dois lados: um consome o que vem antes, o outro o que vem depois. Mesma coisa para `[A-Z]`. Testado com `"Senha123"`, `"senha123"`, `"abc"`, `"Senha123a"` e `"senhA123"`.

## Como rodar

```bash
mvn -q -pl dia-04-validadorCadastro -am compile
java -cp dia-04-validadorCadastro/target/classes dia_04_validadorCadastro.main
```

Rodar da raiz do repositório. No IntelliJ, basta executar a classe `main`.
