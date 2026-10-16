# Dia 03: Agenda de consultas

## Ticket

Uma clínica marca consultas num caderno e vive tendo dois pacientes no mesmo horário com o mesmo médico. Ainda não há tela nem banco de dados: só o domínio, testado por uma classe com `main`.

**Requisitos**
1. Cadastrar médicos com nome e especialidade.
2. Agendar uma consulta: médico, nome do paciente, data/hora de início e duração (30 ou 60 minutos).
3. A clínica funciona de segunda a sexta, das 8h às 18h. A consulta tem que começar e terminar dentro desse horário.
4. Cancelar uma consulta.
5. Listar as consultas de um médico em um dia.

**Critérios de aceite** (todos provados no `main` em 16/10/2026)
- [x] Consulta que se sobrepõe a outra do mesmo médico é rejeitada, inclusive sobreposição parcial (9h00–10h00 contra 9h30–10h00).
- [x] Consulta que termina exatamente quando a outra começa é aceita.
- [x] Consulta no fim de semana ou fora do expediente é rejeitada com uma mensagem que diz o motivo.
- [x] Consulta no passado é rejeitada.
- [x] Duração diferente de 30 ou 60 minutos é rejeitada.
- [x] Depois de cancelar, o mesmo horário pode ser agendado de novo.

## Destrinchado

| Classe | O que guarda | O que faz |
|---|---|---|
| `Medico` | nome (`String`), especialidade (`String`) | Só guarda os dados. |
| `Consulta` | medico (`Medico`), paciente (`String`), inicio (`LocalDateTime`), duracao (`Duration`) | Rejeita duração diferente de 30 ou 60 minutos no construtor. Calcula o próprio fim. Diz se o horário dela se sobrepõe ao de outra consulta. |
| `Agenda` | lista de consultas (`List<Consulta>`) | Agenda (validando passado, fim de semana, expediente e sobreposição), cancela e lista as consultas de um médico num dia. |
| `AgendamentoInvalidoException` | — | Exceção unchecked para qualquer agendamento recusado. A mensagem diz o motivo. |

**Por que a sobreposição fica dividida entre `Consulta` e `Agenda`?** Comparar dois horários é uma pergunta sobre a consulta: só ela sabe onde começa e onde termina. Mas decidir se pode agendar depende de **todas** as outras consultas, e quem conhece todas é a `Agenda`. Então a `Consulta` responde "eu me sobreponho a essa outra?" e a `Agenda` pergunta isso para cada consulta do mesmo médico.

**Por que o expediente fica na `Agenda`, e não na `Consulta`?** Das 8h às 18h é regra da clínica, não da consulta. Uma consulta às 20h continua sendo uma consulta; quem não aceita é esta clínica.

**Por que `Duration` e não um `int` de minutos?** Com `Duration`, o fim sai direto de `inicio.plus(duracao)`, sem converter nada. O construtor recebe os minutos como `int` (é mais fácil de chamar) e transforma em `Duration` depois de validar.

## Caminho

Siga na ordem. Rode o `main` ao fim de cada passo.

- [x] **0. Pacote:** o pacote do módulo está como `Dia_02`. Renomeie para `Dia_03_Agenda` (botão direito no pacote > Refactor > Rename).
- [x] **1. `AgendamentoInvalidoException`:** estende `RuntimeException` e tem um construtor que recebe a mensagem e repassa para o `super`. Igual à dos dias anteriores.
- [x] **2. `Medico`:** dois campos `private final` (`String nome`, `String especialidade`), construtor que recebe os dois e `@Getter`.
- [x] **3. `Consulta`:** quatro campos `private final` (`Medico medico`, `String paciente`, `LocalDateTime inicio`, `Duration duracao`) e `@Getter`.
  - Construtor: `Consulta(Medico medico, String paciente, LocalDateTime inicio, int minutos)`.
  - Antes de atribuir: se `minutos` não for 30 nem 60, lance a exceção. Depois, `this.duracao = Duration.ofMinutes(minutos)`.
  - Teste: uma consulta às 9h com 30 minutos tem fim às 9h30.
- [x] **4. `LocalDateTime fim()`** na `Consulta`: o início mais a duração.
- [x] **5. `boolean sobrepoe(Consulta outra)`** na `Consulta`. Só compara horários; não olha o médico.
  - Dica: é mais fácil pensar em quando **não** há sobreposição. Ou esta termina antes (ou exatamente quando) a outra começa, ou a outra termina antes (ou exatamente quando) esta começa. Em todo o resto, se sobrepõem.
  - Use `isBefore` e `isAfter`. Os dois são estritos: `a.isBefore(a)` é `false`.
  - Confira com esta tabela antes de seguir:

    | Esta | Outra | Sobrepõe? |
    |---|---|:---:|
    | 9h00–10h00 | 9h30–10h00 | sim |
    | 9h00–10h00 | 8h30–9h30 | sim |
    | 9h00–10h00 | 9h15–9h45 | sim |
    | 9h00–10h00 | 10h00–10h30 | não |
    | 9h00–10h00 | 8h30–9h00 | não |

- [x] **6. `Agenda`:** um campo `private final List<Consulta> consultas = new ArrayList<>();`, sem construtor.
  - `Consulta agendar(Medico medico, String paciente, LocalDateTime inicio, int minutos)`: cria a `Consulta`, valida (passos 7 a 9), coloca na lista e devolve a consulta criada (o teste precisa dela para cancelar depois).
- [x] **7. Passado:** dentro do `agendar`, se o início for antes de `LocalDateTime.now()`, lance a exceção com uma mensagem que diga que é no passado.
- [x] **8. Fim de semana e expediente:** ainda no `agendar`.
  - `inicio.getDayOfWeek()` devolve um `DayOfWeek`. Se for `SATURDAY` ou `SUNDAY`, rejeite. Enum se compara com `==`.
  - Guarde os horários em constantes na `Agenda`: `private static final LocalTime ABERTURA = LocalTime.of(8, 0);` e `FECHAMENTO = LocalTime.of(18, 0)`.
  - `inicio.toLocalTime()` antes da abertura: rejeite. `fim().toLocalTime()` depois do fechamento: rejeite. Terminar às 18h00 em ponto é permitido.
  - Cada caso com a sua mensagem: o critério pede que a mensagem diga o motivo.
- [x] **9. Sobreposição:** ainda no `agendar`, percorra a lista. Para cada consulta **do mesmo médico** (`==` serve aqui, porque é o mesmo objeto `Medico`), se a nova `sobrepoe` a existente, rejeite.
- [x] **10. `void cancelar(Consulta consulta)`:** remove da lista. Se não estava na lista, lance a exceção.
  - `consultas.remove(consulta)` devolve `boolean`: `true` se achou e removeu.
- [x] **11. `List<Consulta> consultasDoDia(Medico medico, LocalDate dia)`:** cria uma lista nova e coloca nela as consultas desse médico cujo `inicio.toLocalDate()` é igual ao `dia` (`equals`, não `==`).
- [x] **12. `main` com os critérios:** escrito pelo Claude a pedido do Nicolas (como no Dia 02). Prova os 6 critérios e o requisito 5 (listar as consultas de um médico em um dia), com datas calculadas a partir de hoje para não virarem passado.

**Armadilhas conhecidas**
- `LocalDateTime` é imutável: `inicio.plus(duracao)` devolve um objeto novo e não muda o `inicio`.
- `LocalDate` e `LocalDateTime` se comparam com `equals`, `isBefore` e `isAfter`, nunca com `==`.
- Datas fixas no teste viram passado com o tempo. O `main` vai calcular a próxima segunda-feira a partir de hoje.
- Ordem das validações no `agendar`: valide **antes** de colocar na lista. Se colocar primeiro, a nova consulta se sobrepõe a ela mesma.

## Decisões

| Decisão | Motivo |
|---|---|
| Método `imprimirDadosConsulta` no `main` | Para não repetir os vários `println` toda vez que precisar mostrar uma consulta. Fica no `main` e não na `Consulta`, porque mostrar na tela é coisa do programa de teste, não do domínio. |
| Tirei o `@Getter` da `Agenda` | O `getConsultas()` entregava a lista original, e qualquer um poderia fazer `add` sem passar pelas validações do `agendar`. Para ver consultas existe o `consultasDoDia`, que devolve uma lista nova. |
| `cancelar` não imprime nada | Mensagem na tela é papel do `main`. Se o `cancelar` termina sem exceção, quem chamou já sabe que deu certo. |

## Pendências

- [ ] No `sobrepor`, a variável `SituacaoB` virou `estaTerminaAntes`, mas o nome não descreve o que ela verifica: `outra.fim().isAfter(inicio)` é "a outra termina depois desta começar". O comportamento está certo (os testes passam), só o nome engana.
- [ ] `cancelar` percorre a lista duas vezes (`contains` e depois `remove`). O `remove` já devolve `true` ou `false`, então dá para usar só ele no `if`.
- [ ] O método se chama `sobrepor` (o Destrinchado previa `sobrepoe`). Funciona igual; é só diferença de nome.
- [ ] Ideia fora do ticket: `consultasDoDia` devolve na ordem em que as consultas foram marcadas, não na ordem do horário.

## Dúvidas e travamentos

- **Validar duração 30 ou 60:** usei `duracao.compareTo(...) != 30`, mas o `compareTo` só devolve negativo, zero ou positivo, nunca os minutos, então a exceção caía sempre. O `&&` ("não é 30 e não é 60") estava certo. Passei a receber `int minutos` e comparar o número direto.
- **`fim()` com 30 fixo:** o teste passou por coincidência porque a consulta tinha 30 minutos. O certo é usar o atributo `duracao`, que já foi validado no construtor.
- **Quem é "esta" consulta no `sobrepor`:** não entendia de onde vinham os dados da consulta atual. Destravou: em `consulta1.sobrepor(consulta2)`, `inicio` e `fim()` são da `consulta1` (o objeto antes do ponto) e `outra` é a `consulta2`, como no `fim()`.
- **`isBefore`/`isAfter` no `sobrepor`:** troquei `isAfter` por `isBefore` no chute e o método passou a acertar só por sorte. A regra: se sobrepõem quando esta termina depois da outra começar **e** a outra termina depois desta começar; como `isAfter` não aceita igual, encostar não conta como choque. Material de estudo: `~/Downloads/30-dias-java-estudo-comparar-horarios.pdf`.
- **Lista criada de novo dentro do `agendar`:** escrevi `List<Consulta> consultas = new ArrayList<>();` dentro do método. Isso cria uma segunda lista com o mesmo nome, que esconde o campo e some quando o método termina, então a agenda continuava vazia. Destravou: a lista já nasce no campo quando faço `new Agenda()`; linha que começa com o tipo cria variável nova, sem o tipo usa a que já existe. O `return consultas.get(0)` também saiu: devolvo a `nova`.
- **Achei que o `agendar` não devolvia nada:** o `println` mostrava `Consulta@...`. Era o `toString` padrão do Java, não um erro. Passei a imprimir os campos com os getters.
- **`getDayOfWeek(DayOfWeek.SUNDAY)`:** o método não recebe parâmetro, ele devolve o dia da semana. Primeiro pergunta o dia, depois compara com `==`, ligando sábado e domingo com `||`.
- **Sobreposição comparando só o início:** usei `getInicio().equals(...)`, que só pega consultas que começam no mesmo minuto e deixa passar 9h30 contra 9h00–10h00. Também tinha um `;` logo depois do `if` (o corpo ficava vazio e a linha de baixo rodava sempre) e um `println` no lugar do `throw`, que não impedia o `add`. O certo é usar o `sobrepor`, que olha início e fim das duas.
- **Regra geral de sobreposição:** vale para qualquer coisa com começo e fim (sala, hotel, turno): A começa antes de B terminar **e** B começa antes de A terminar. Com `isBefore`/`isAfter`, encostar não conta como conflito.
- **`consultasDoDia`:** `consulta.getMedico()` sozinho não é pergunta de sim ou não, então não entra no `&&`; tem que comparar com o médico do parâmetro (`== medico`). E o `return` estava dentro do `for`, o que saía na primeira volta; ele vai depois que o `for` termina.

## Como rodar

```bash
mvn -q -pl dia-03-Agenda_Consultas -am compile
java -cp dia-03-Agenda_Consultas/target/classes Dia_03_Agenda_Consultas.main
```

Rodar da raiz do repositório. No IntelliJ, basta executar a classe `main`.
