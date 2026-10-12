# Dia 01: Conta bancária com extrato

## Ticket

Uma fintech precisa do núcleo de contas correntes. Ainda não há banco de dados nem tela: só o domínio, testado por uma classe com `main`.

**Requisitos**
1. Abrir conta com titular e limite de cheque especial.
2. Depositar, sacar e transferir entre contas.
3. Toda movimentação gera um lançamento no extrato, com data e hora.
4. Imprimir o extrato no formato `dd/MM/yyyy HH:mm`, com o saldo após cada lançamento.

**Critérios de aceite**
- [ ] Saque acima de saldo + limite lança uma exceção de domínio específica e o saldo não muda. *(a exceção foi comprovada no `main`; falta imprimir o extrato antes e depois para provar que o saldo não muda)*
- [ ] Se a transferência falhar, nenhuma das duas contas é alterada. *(a exceção foi comprovada; falta imprimir os dois extratos antes e depois)*
- [x] Depósito ou saque com valor zero ou negativo é rejeitado.
- [x] Não existe nenhuma forma de alterar o saldo de fora da classe sem passar pelas regras.

**Restrição:** sem setter de saldo. ✅

## Modelagem

| Classe | O que ela guarda | O que ela faz |
|---|---|---|
| `Conta` | titular, limiteCheque, saldo, lista de movimentações | depositar, sacar, transferir, imprimirExtrato |
| `Movimentacao` | dataHora, tipo, valor, saldoPosterior, descricao | nada, só guarda dados (imutável) |

| Enum | Valores |
|---|---|
| `TipoMovimentacao` | SAQUE, DEPOSITO, TRANSFERENCIA |

| Exceção | Quando | Tipo |
|---|---|---|
| `ValorInvalidoException` | valor zero ou negativo em qualquer operação | unchecked |
| `SaldoInsuficienteException` | valor maior que saldo + limiteCheque | unchecked |

**Métodos internos da `Conta`:** `debitar` e `creditar` (privados) recebem valor, tipo e descrição. `sacar` e `depositar` chamam esses dois com o tipo fixo. `transferir` chama `debitar` na própria conta e `creditar` na conta de destino.

## Decisões

| Decisão | Motivo |
|---|---|
| `BigDecimal` para saldo, limite e valores | Lida melhor com valores quebrados, por isso é utilizado em bancos. |
| `LocalDateTime` na data | O ticket pede data **e** hora (`dd/MM/yyyy HH:mm`), e `LocalDate` não guarda hora. |
| `DateTimeFormatter` só no extrato | Formatar é papel da impressão, não do dado. O formatter é criado uma vez, antes do `for`. |
| Tipo como enum, não `String` | Com `String`, um erro como `"saqeu"` só aparece em tempo de execução, mesmo validando com regex ou `equals`. Com enum, o erro aparece na compilação. |
| Sem `saldoAnterior` | Dá para calcular com saldoPosterior e valor. Guardar um dado calculado cria o risco de os números não fecharem por causa de um bug. |
| `descricao` em vez de `receptor` | O `receptor` ficaria `null` em saque e depósito. A `descricao` sempre tem conteúdo, o que deixa a impressão simples. O custo é que texto livre não serve para buscar ("todas as transferências para o João"), e o ticket não pede essa busca. |
| `Movimentacao` imutável (`final` + construtor + só getters) | Uma movimentação registrada no extrato não pode mudar depois. |
| `@Getter` do Lombok | Deixa a classe mais limpa e legível, sem a repetição dos getters escritos à mão. |
| Conta nasce com saldo zero, sem `inserirSaldo` | Saldo no construtor ou um `inserirSaldo` seriam um setter disfarçado e quebrariam a restrição. O primeiro dinheiro entra por `depositar`, que gera um lançamento. |
| Sem `@AllArgsConstructor` na `Conta` | Ele gera um construtor com o saldo, que continuaria disponível ao lado do construtor escrito à mão (sobrecarga, não sobrescrita). Regra que só existe na cabeça de quem escreveu o código não é regra. |
| A `Conta` valida o saldo suficiente (no `debitar`) | O `debitar` é privado, então ninguém de fora consegue mexer no saldo sem passar pela validação. |
| `debitar` e `creditar` privados | Se fossem públicos, alguém de fora poderia registrar um débito com o tipo `DEPOSITO`. |
| `ValorInvalidoException` unchecked | Valor inválido é bug de quem chama o método. Não faz sentido obrigar todo mundo a tratar com `try/catch`. |
| `SaldoInsuficienteException` unchecked | *pendente: escrever o motivo* |
| `transferir` recebe uma `Conta`, não o nome | O saldo e o extrato do destino moram no objeto conta. Um nome (`String`) não tem onde creditar. |
| Na transferência, débito antes do crédito | Se o débito falha, a exceção interrompe o método antes do crédito, e o `debitar` valida antes de mexer no saldo. Assim nenhuma conta muda. |
| Projeto Maven multi-module, um módulo por dia | Cada dia compila sozinho, então um dia quebrado não trava os outros. |

## Pendências

- [ ] Testes dos critérios 1 e 2 no `main` com `try/catch`, imprimindo os extratos antes e depois.
- [ ] Motivo da `SaldoInsuficienteException` ser unchecked.
- [x] Tirar os `throws ValorInvalidoException`, que viraram desnecessários com a exceção unchecked.
- [ ] Polimentos: tirar o acento de `listaMovimentação`; imprimir a `descricao` no extrato.

## Dúvidas e travamentos

- **`BigDecimal` é objeto, não primitivo.** Não aceita `int` direto no construtor da `Conta`, não funciona com `<=` (usar `compareTo`) e é imutável: `saldo.add(valor)` sozinho não muda o saldo, tem que ser `saldo = saldo.add(valor)`. Esse último erro apareceu três vezes.
- **Chamar um método passando parâmetros:** repassar o `valor` recebido e fixar o tipo e a descrição (`debitar(valor, TipoMovimentacao.SAQUE, "Saque")`).
- **Quem é a conta "atual":** em `ana.transferir(joao, valor)`, a Ana é o objeto antes do ponto (`this`), e não precisa ser parâmetro.
- **Limite não é saldo:** o segundo parâmetro do construtor é o limite do cheque especial. O `limiteCheque` nunca muda; quem fica negativo é o saldo.
- **Quem recebe não precisa de saldo:** só quem envia é validado.
- **`cannot find symbol` no IntelliJ** depois de mudar o `pom.xml`: resolvido com "Reload All Maven Projects".

## Como rodar

Na raiz do repositório:

```
mvn compile
java -cp dia-01-conta-bancaria/target/classes Dia1_conta_bancaria.main
```
