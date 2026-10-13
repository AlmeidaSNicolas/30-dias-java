# Tickets dos 30 dias

Um ticket por dia, como no trabalho: contexto, requisitos, critérios de aceite e uma pergunta de design. Não tem passo a passo. Regras e progresso no [README](README.md).

Antes de abrir a IDE, copie o ticket para o README da pasta do dia e escreva lá o entendimento, as classes, as responsabilidades e a resposta da pergunta de design.

> Reconstruído em 13/10/2026 depois da formatação do Mac. O Dia 01 é o ticket original. Os outros foram reescritos a partir do plano do README.

---

## Semana 1: Consolidação (POO, exceções e tempo)

### Dia 01: Conta bancária com extrato ✅

Uma fintech precisa do núcleo de contas correntes. Ainda não há banco de dados nem tela: só o domínio, testado por uma classe com `main`.

**Requisitos**
1. Abrir conta com titular e limite de cheque especial.
2. Depositar, sacar e transferir entre contas.
3. Toda movimentação gera um lançamento no extrato, com data e hora.
4. Imprimir o extrato no formato `dd/MM/yyyy HH:mm`, com o saldo após cada lançamento.

**Critérios de aceite**
- [ ] Saque acima de saldo + limite lança uma exceção de domínio específica e o saldo não muda.
- [ ] Se a transferência falhar, nenhuma das duas contas é alterada.
- [ ] Depósito ou saque com valor zero ou negativo é rejeitado.
- [ ] Não existe nenhuma forma de alterar o saldo de fora da classe sem passar pelas regras.

**Restrição:** sem setter de saldo.

---

### Dia 02: Calculadora de frete extensível

Um e-commerce calcula o frete com um `if` gigante por transportadora. Toda transportadora nova exige mexer nesse `if`, e da última vez quebrou o cálculo das outras.

**Requisitos**
1. Calcular o frete de um pedido (peso em kg, distância em km, CEP de destino) em três modalidades:
   - **Econômica:** R$ 5,00 + R$ 0,50/kg. Prazo: 10 dias úteis.
   - **Expressa:** R$ 15,00 + R$ 1,20/kg + R$ 0,10/km. Prazo: 3 dias úteis.
   - **Retirada na loja:** grátis. Prazo: 1 dia útil.
2. Cada cálculo devolve o valor e a **data prevista de entrega**, contando só dias úteis (sábado e domingo não contam).
3. Listar todas as modalidades disponíveis para um pedido, da mais barata para a mais cara.

**Critérios de aceite**
- [ ] Adicionar uma quarta modalidade (ex.: "Drone": R$ 30,00 fixo, só até 5 kg e 20 km, entrega no mesmo dia) não exige alterar nenhuma classe existente.
- [ ] Pedido feito na sexta com prazo de 1 dia útil tem entrega prevista na segunda.
- [ ] Modalidade que não atende o pedido (ex.: Drone com 8 kg) não aparece na lista, e não lança exceção.
- [ ] Peso ou distância zero ou negativos são rejeitados antes de qualquer cálculo.

**Pergunta de design:** quem sabe se uma modalidade atende um pedido: o pedido, a modalidade ou a calculadora? Por quê?

---

### Dia 03: Agenda de consultas

Uma clínica marca consultas num caderno e vive tendo dois pacientes no mesmo horário.

**Requisitos**
1. Cadastrar médicos com especialidade.
2. Agendar uma consulta: médico, paciente, data/hora de início e duração (30 ou 60 minutos).
3. A clínica funciona de segunda a sexta, das 8h às 18h, com almoço das 12h às 13h.
4. Cancelar uma consulta.
5. Listar a agenda de um médico em um dia, em ordem de horário, e os horários livres desse dia.

**Critérios de aceite**
- [ ] Consulta que se sobrepõe a outra do mesmo médico é rejeitada (inclusive sobreposição parcial: 9h00–10h00 contra 9h30–10h00).
- [ ] Consulta que termina exatamente quando a outra começa é aceita.
- [ ] Consulta no fim de semana, fora do expediente ou invadindo o almoço é rejeitada com uma mensagem que diz o motivo.
- [ ] Consulta no passado é rejeitada.
- [ ] Cancelamento com menos de 24h de antecedência é recusado.

**Pergunta de design:** a regra "não pode sobrepor" é responsabilidade da `Consulta`, do `Medico` ou da `Agenda`? E a regra do expediente?

---

### Dia 04: Validador de cadastro

O formulário de cadastro de um site devolve só "dados inválidos" e o usuário não sabe o que corrigir.

**Requisitos**
1. Validar um cadastro com: nome, e-mail, CPF, telefone, data de nascimento e senha.
2. Regras:
   - **Nome:** pelo menos duas palavras, só letras (com acento) e espaços.
   - **E-mail:** formato válido (`algo@dominio.ext`).
   - **CPF:** aceita com ou sem pontuação; os dígitos verificadores têm que bater.
   - **Telefone:** `(11) 91234-5678` ou só os 11 dígitos.
   - **Nascimento:** `dd/MM/yyyy`; maior de 18 anos.
   - **Senha:** 8+ caracteres, com maiúscula, minúscula, número e símbolo.
3. A validação devolve **todos** os erros de uma vez, não só o primeiro.

**Critérios de aceite**
- [ ] Cadastro com três campos errados devolve três mensagens, uma por campo.
- [ ] CPF `111.111.111-11` (todos os dígitos iguais) é rejeitado, mesmo passando no cálculo.
- [ ] Quem faz 18 anos hoje é aceito; quem faz amanhã é rejeitado.
- [ ] Os dados são normalizados depois de validados: CPF e telefone guardados só com dígitos, nome sem espaços sobrando.
- [ ] Data impossível (`31/02/2000`) é erro de validação, não exceção estourando.

**Restrição:** a validação não usa exceção para controlar o fluxo. Erro de validação é resultado, não exceção.

**Pergunta de design:** como representar o resultado de uma validação que pode ter vários erros? Que classe guarda isso, e por que não `List<String>` solto?

---

### Dia 05: Relatório de vendas a partir de CSV

O financeiro exporta as vendas do mês num CSV e monta o relatório na mão no Excel.

**Requisitos**
1. Ler um arquivo `vendas.csv` com: `data;vendedor;produto;quantidade;precoUnitario` (data `dd/MM/yyyy`, preço com vírgula decimal, ex.: `19,90`).
2. Gerar um relatório com:
   - total vendido no mês;
   - total por vendedor;
   - produto mais vendido em quantidade;
   - ticket médio (total ÷ número de vendas).
3. Gravar o relatório em `relatorio.txt`.

**Critérios de aceite**
- [ ] Linhas inválidas (campo faltando, número malformado, data impossível) são puladas e listadas no fim do relatório com número da linha e motivo. O resto do arquivo é processado.
- [ ] Arquivo inexistente gera uma mensagem clara, não um stack trace.
- [ ] Valores monetários com `BigDecimal`, arredondados com duas casas e `HALF_EVEN`.
- [ ] O arquivo é fechado mesmo quando dá erro no meio da leitura.
- [ ] A linha de cabeçalho é ignorada.

**Restrição:** sem `Map` (ele vem na semana 4; o Dia 24 refatora este dia com Map).

**Pergunta de design:** separe leitura, parsing, cálculo e escrita. Quem conhece o formato do CSV? Se amanhã vier um JSON, quantas classes mudam?

---

### Dia 06: Estacionamento

Um estacionamento cobra na calculadora do celular e erra o troco toda semana.

**Requisitos**
1. Registrar entrada de veículo: placa, tipo (MOTO, CARRO, CAMINHONETE) e horário.
2. Registrar saída e calcular o valor:
   - até 15 minutos: grátis;
   - primeira hora: valor do tipo (moto R$ 5, carro R$ 10, caminhonete R$ 15);
   - cada hora seguinte (ou fração): metade do valor do tipo;
   - diária máxima: 8× o valor do tipo.
3. O pátio tem número limitado de vagas por tipo.
4. Listar os veículos no pátio, com o tempo de permanência de cada um.

**Critérios de aceite**
- [ ] Placa validada no formato antigo (`ABC-1234`) e no Mercosul (`ABC1D23`), com regex.
- [ ] O mesmo veículo não entra duas vezes sem ter saído.
- [ ] Entrada com o pátio cheio para aquele tipo é rejeitada.
- [ ] Carro que fica 1h01 paga 1h30 (R$ 15); que fica 14 minutos paga zero; que fica 20 horas paga a diária máxima.
- [ ] Preço e quantidade de vagas moram no enum, não em `if`/`switch` espalhado.

**Pergunta de design:** o cálculo do valor fica no enum, no `Ticket` ou numa classe de tarifa? O que acontece com a sua escolha se o estacionamento criar preço de fim de semana?

---

### Dia 07: Mini-banco no terminal (fim de semana)

Juntar a semana: o domínio do Dia 01 ganha um sistema de verdade, com menu e dados que sobrevivem ao fechar o programa.

**Requisitos**
1. Menu no terminal (`Scanner`): abrir conta, depositar, sacar, transferir, ver extrato, listar contas, sair.
2. Cada conta tem um número gerado pelo sistema.
3. Os dados são salvos em arquivo e carregados ao abrir o programa.
4. Separar em camadas: interface (menu), serviço (regras de uso) e repositório (arquivo).

**Critérios de aceite**
- [ ] Fechar e abrir o programa mantém contas, saldos e extratos.
- [ ] Entrada inválida no menu (texto onde se espera número, opção inexistente) mostra mensagem e volta ao menu, sem derrubar o programa.
- [ ] Erros de domínio (saldo insuficiente, valor inválido) aparecem como mensagem amigável.
- [ ] A classe `Conta` não sabe que existe arquivo nem terminal.
- [ ] Trocar o arquivo por outra persistência mudaria só o repositório.

**Pergunta de design:** quem converte a exceção de domínio em mensagem para o usuário? E se o arquivo estiver corrompido ao abrir?

---

## Semana 2: Arquivos, rede e revisão do BotTelegram

### Dia 08: Organizador da pasta Downloads

A pasta Downloads tem 2 mil arquivos misturados.

**Requisitos**
1. Varrer uma pasta (passada como argumento) e mover cada arquivo para uma subpasta pela extensão: `Imagens` (jpg, png, gif, webp), `Documentos` (pdf, docx, txt, xlsx), `Compactados` (zip, rar, 7z), `Instaladores` (dmg, pkg, exe), `Outros`.
2. As regras ficam num arquivo de configuração, não no código.
3. Gravar um log em arquivo com data/hora, origem e destino de cada movimentação.
4. Modo simulação (`--dry-run`): mostra o que faria sem mover nada.

**Critérios de aceite**
- [ ] Arquivo com nome igual no destino não é sobrescrito: vira `foto (1).jpg`.
- [ ] Subpastas existentes e arquivos ocultos são ignorados.
- [ ] Extensão é comparada sem diferenciar maiúscula (`FOTO.JPG` vai para Imagens).
- [ ] Falha ao mover um arquivo (permissão, arquivo em uso) é registrada no log e o programa continua com os outros.
- [ ] Rodar duas vezes seguidas não move nada na segunda.

**Pergunta de design:** como testar isso sem arriscar a sua pasta Downloads de verdade?

---

### Dia 09: Backup incremental compactado

Você quer fazer backup da pasta do projeto sem copiar tudo toda vez.

**Requisitos**
1. Gerar um `.zip` com data e hora no nome (`backup-2026-10-20-2130.zip`) a partir de uma pasta de origem, incluindo subpastas.
2. **Incremental:** a partir do segundo backup, entra só o que mudou desde o último (pela data de modificação).
3. Ignorar padrões configuráveis (`target/`, `.git/`, `*.class`).
4. Restaurar um backup para uma pasta escolhida.

**Critérios de aceite**
- [ ] Backup sem nenhuma alteração desde o último não gera zip vazio: avisa e não cria o arquivo.
- [ ] A estrutura de pastas é preservada dentro do zip e na restauração.
- [ ] Restaurar recusa entradas do zip com `..` no caminho (zip slip).
- [ ] O programa sabe quando foi o último backup mesmo depois de reiniciado.
- [ ] Ao final, imprime quantos arquivos e quantos bytes entraram.

**Pergunta de design:** onde guardar a data do último backup, e o que acontece se o backup falhar no meio? A data deve ser atualizada?

---

### Dia 10: Auditoria do BotTelegram: recursos abertos

O [BotTelegram](https://github.com/AlmeidaSNicolas/BotTelegram) precisa rodar 24/7. Recurso que não fecha vira vazamento, e vazamento derruba o bot dias depois.

**Requisitos**
1. Listar todos os recursos que o bot abre: conexões JDBC, `Statement`/`ResultSet`, streams, arquivos, clientes HTTP, executors.
2. Para cada um, registrar no README: onde abre, onde fecha e o que acontece se der exceção no meio.
3. Corrigir os que não fecham em todos os caminhos.

**Critérios de aceite**
- [ ] Todo recurso `AutoCloseable` está em try-with-resources ou tem justificativa escrita para não estar.
- [ ] Uma classe sua que implementa `AutoCloseable` prova no `main`, com mensagens, a ordem de fechamento quando há dois recursos no mesmo try.
- [ ] Nenhuma conexão JDBC fica aberta depois de uma consulta que lança exceção.
- [ ] A tabela de auditoria (recurso, onde abre, onde fecha, situação, correção) está no README do dia.

**Pergunta de design:** conexão por consulta ou conexão única aberta o tempo todo? Qual o custo de cada uma para um bot que roda 24/7?

---

### Dia 11: Auditoria do BotTelegram: imutabilidade e exceções

Segunda rodada de auditoria: estado compartilhado e exceções engolidas.

**Requisitos**
1. Encontrar todo `catch` que engole exceção (vazio ou só `printStackTrace`) e decidir para cada um: tratar, registrar com contexto ou propagar.
2. Encontrar objetos que mudam depois de criados e não deveriam (config, mensagens, dados do usuário) e torná-los imutáveis.
3. Revisar as tarefas agendadas: uma exceção dentro de uma tarefa do `ScheduledExecutorService` não pode matar as execuções seguintes.

**Critérios de aceite**
- [ ] Nenhum `catch` vazio sobrou.
- [ ] Prova no `main`: uma tarefa agendada que lança exceção na 2ª execução continua rodando na 3ª.
- [ ] Classes de configuração são imutáveis (`final`, sem setters, coleções defensivas).
- [ ] O bot encerra os executors ao ser desligado (shutdown hook).
- [ ] O README do dia lista cada mudança com o motivo.

**Pergunta de design:** quando uma exceção deve subir e derrubar a operação, e quando deve ser registrada para o bot seguir em frente?

---

### Dia 12: Consulta de CEP com cache local

Um sistema de cadastro consulta CEP numa API pública, que às vezes demora ou cai.

**Requisitos**
1. Consultar um CEP na ViaCEP (`https://viacep.com.br/ws/{cep}/json/`) com `HttpClient`.
2. Converter a resposta num objeto `Endereco` (logradouro, bairro, cidade, UF).
3. Guardar as respostas num cache em arquivo; consultas repetidas não vão à rede.
4. Cache expira em 30 dias.

**Critérios de aceite**
- [ ] CEP com formato inválido é rejeitado antes de chamar a API.
- [ ] CEP inexistente (a API devolve `{"erro": true}`) gera uma exceção de domínio específica, não um `Endereco` com campos nulos.
- [ ] Timeout de 5 segundos. Se a API não responde e o CEP está no cache, mesmo vencido, usa o cache e avisa.
- [ ] A segunda consulta do mesmo CEP não faz requisição (provar com log).
- [ ] Status HTTP diferente de 200 é tratado.

**Pergunta de design:** o serviço de CEP deve saber que existe cache? Como trocar a ViaCEP por outra API sem mexer em quem usa o serviço?

---

### Dia 13: API de tarefas com HttpServer

Um app precisa de uma API simples de tarefas, sem Spring.

**Requisitos**
1. Com o `HttpServer` nativo, expor:
   - `GET /tarefas`: lista;
   - `GET /tarefas/{id}`: uma;
   - `POST /tarefas`: cria (título, descrição);
   - `PUT /tarefas/{id}/concluir`: marca como concluída;
   - `DELETE /tarefas/{id}`: remove.
2. Respostas em JSON.
3. Separar handler (HTTP), serviço (regras) e repositório (dados).

**Critérios de aceite**
- [ ] Status certos: 200, 201 na criação, 204 no delete, 400 para corpo inválido, 404 para id inexistente, 405 para método não suportado.
- [ ] Título vazio retorna 400 com mensagem explicando.
- [ ] Concluir uma tarefa já concluída retorna 409.
- [ ] Uma exceção não tratada retorna 500 e o servidor continua de pé.
- [ ] O serviço não importa nada de `com.sun.net.httpserver`.

**Pergunta de design:** quem converte exceção de domínio em status HTTP? E onde mora a regra "não pode concluir duas vezes"?

---

### Dia 14: Monitor de sites (fim de semana)

Você quer saber quando um site seu cai antes de o usuário reclamar.

**Requisitos**
1. Ler uma lista de URLs de um arquivo.
2. A cada N minutos (configurável), checar cada URL: status e tempo de resposta.
3. Avisar (console e arquivo de log) só quando o estado **muda**: de no ar para fora e de fora para no ar, com quanto tempo ficou fora.
4. Guardar o histórico e gerar um resumo: disponibilidade (%) e tempo médio de resposta por site.

**Critérios de aceite**
- [ ] Site lento (acima de 10 s) conta como fora.
- [ ] Um site travado não atrasa a checagem dos outros.
- [ ] Reiniciar o programa não perde o histórico nem gera aviso falso de "voltou".
- [ ] Erro de DNS e conexão recusada são tratados como site fora, sem derrubar o monitor.
- [ ] Ctrl+C encerra limpo, salvando o estado.

**Pergunta de design:** como guardar o estado entre execuções? O que é estado e o que é histórico?

---

## Semana 3: Coleções parte 1 (igualdade, listas e ordenação)

Daqui em diante, cada dia só depois das aulas do pré-requisito. Se a aula ainda não chegou, faça um desafio reserva (R1 a R4 no README).

### Dia 15: O contrato do equals, na prática

**Pré-requisito:** aulas de equals.

Um sistema de RH acha funcionários duplicados onde não tem e deixa passar os que têm.

**Requisitos**
1. Modelar `Pessoa` (nome, CPF, nascimento) e `Funcionario extends Pessoa` (matrícula, cargo).
2. Implementar `equals` nas duas e decidir o que define a igualdade em cada uma.
3. Escrever um `main` que testa as cinco propriedades do contrato: reflexiva, simétrica, transitiva, consistente e `x.equals(null) == false`.

**Critérios de aceite**
- [ ] Todas as cinco propriedades passam, com pares Pessoa/Pessoa, Funcionario/Funcionario e **Pessoa/Funcionario**.
- [ ] `equals` com objeto de outro tipo devolve `false` sem exceção.
- [ ] O README explica `getClass()` x `instanceof` e mostra qual propriedade quebra com a escolha errada.
- [ ] `list.contains` e `list.indexOf` funcionam com um objeto novo igual ao da lista.

**Pergunta de design:** um `Funcionario` e uma `Pessoa` com o mesmo CPF são iguais?

---

### Dia 16: Clientes sem duplicados

**Pré-requisito:** aula de hashCode.

A base de clientes de uma loja tem o mesmo cliente cadastrado três vezes.

**Requisitos**
1. `Cliente` com CPF, nome e e-mail. Igualdade pelo CPF.
2. Importar uma lista de clientes (com duplicados) para um `HashSet`.
3. Relatório: quantos foram lidos, quantos ficaram, quais foram descartados.

**Critérios de aceite**
- [ ] `equals` e `hashCode` coerentes: iguais pelo `equals` têm o mesmo `hashCode`.
- [ ] Prova no `main`: com `equals` sem `hashCode`, o `HashSet` aceita duplicado.
- [ ] Prova no `main`: mudar o CPF de um cliente depois de inserido faz o `contains` falhar. O README explica por quê.
- [ ] Depois da prova, o campo usado no `hashCode` fica imutável.
- [ ] CPF com e sem pontuação conta como o mesmo cliente.

**Pergunta de design:** o que pode e o que não pode entrar no `hashCode`?

---

### Dia 17: Playlist

**Pré-requisito:** aulas de List.

Um player de música precisa de playlists.

**Requisitos**
1. `Musica` (título, artista, duração) e `Playlist` (nome, músicas).
2. Adicionar no fim e numa posição, remover por posição e por música, mover de uma posição para outra.
3. Modo aleatório que não altera a ordem original.
4. Duração total formatada (`1h 23min 45s`).
5. "Tocar próxima": volta ao início no fim, se o repetir estiver ligado.

**Critérios de aceite**
- [ ] Posição inválida gera exceção de domínio com a posição e o tamanho na mensagem.
- [ ] Desligar o aleatório volta à ordem original.
- [ ] Quem chama `getMusicas()` não consegue alterar a playlist por fora.
- [ ] O README compara `ArrayList` e `LinkedList` para estas operações e justifica a escolha.
- [ ] A mesma música pode aparecer duas vezes; remover por música remove só a primeira.

**Pergunta de design:** quem guarda a posição atual de reprodução: a `Playlist` ou um `Player`?

---

### Dia 18: Ranking de jogadores

**Pré-requisito:** aula de Comparable.

Um campeonato de e-sports precisa do ranking.

**Requisitos**
1. `Jogador` com nick, pontos, vitórias e data de cadastro.
2. Ordem natural: pontos (maior primeiro); empate por vitórias; depois mais antigo primeiro.
3. Imprimir o ranking com a posição. Empatados em tudo dividem a posição (1º, 2º, 2º, 4º).

**Critérios de aceite**
- [ ] `compareTo` coerente com `equals` (ou a incoerência documentada e justificada no README).
- [ ] `Collections.sort` ordena certo uma lista com os empates.
- [ ] O `compareTo` não usa subtração (`a - b`); o README explica o risco de overflow.
- [ ] Atualizar os pontos de um jogador e reordenar reflete a nova posição.

**Pergunta de design:** a ordem do ranking é natural do `Jogador` ou é uma regra do campeonato?

---

### Dia 19: Relatórios de RH com várias ordenações

**Pré-requisito:** aula de Comparator.

O RH quer a mesma lista de funcionários ordenada de jeitos diferentes, conforme o relatório.

**Requisitos**
1. `Funcionario` com nome, departamento, salário, admissão e gestor (pode ser nulo).
2. Relatórios:
   - por nome (sem diferenciar maiúscula e acento);
   - por departamento e, dentro dele, salário decrescente;
   - por tempo de casa (mais antigo primeiro);
   - por gestor, com os sem gestor no fim.
3. O usuário escolhe o relatório no menu.

**Critérios de aceite**
- [ ] Usa `Comparator.comparing`, `thenComparing`, `reversed` e `nullsLast`, sem `compare` escrito na mão.
- [ ] "Álvaro" vem antes de "bruno" na ordenação por nome.
- [ ] Gestor nulo não lança `NullPointerException`.
- [ ] Ordenar não altera a lista original.

**Pergunta de design:** onde ficam os comparators: dentro de `Funcionario`, numa classe própria ou no relatório?

---

### Dia 20: Estoque com validade

**Pré-requisito:** aulas de binarySearch e Iterator.

Um mercado joga produto fora porque não vê a validade a tempo.

**Requisitos**
1. `Lote` (produto, código, quantidade, validade).
2. Remover os lotes vencidos percorrendo a lista com `Iterator`.
3. Buscar um lote pelo código com `Collections.binarySearch`.
4. Listar os que vencem nos próximos 7 dias.
5. Dar baixa numa quantidade de um produto consumindo primeiro o lote que vence antes (FEFO).

**Critérios de aceite**
- [ ] Prova no `main`: remover dentro de um for-each lança `ConcurrentModificationException`; com `iterator.remove()` funciona.
- [ ] `binarySearch` só é chamado com a lista ordenada pelo mesmo critério; o README explica o que acontece se não estiver.
- [ ] Código inexistente: o README explica o valor negativo devolvido e o código o trata.
- [ ] Baixa maior que o estoque total é rejeitada sem alterar nenhum lote.
- [ ] O README compara a complexidade da busca linear e da binária.

**Pergunta de design:** é melhor manter a lista sempre ordenada ou ordenar antes de cada busca?

---

### Dia 21: Biblioteca v2 (fim de semana)

**Pré-requisito:** Dias 15 a 20.

Uma biblioteca de bairro quer sair do caderno.

**Requisitos**
1. Livros (ISBN, título, autor, ano), cada um com vários exemplares.
2. Leitores (CPF, nome) com no máximo 3 empréstimos ao mesmo tempo.
3. Empréstimo por 14 dias. Multa de R$ 1,00 por dia de atraso.
4. Leitor com multa em aberto não pega livro.
5. Fila de espera simples quando não há exemplar disponível.
6. Relatórios: livros mais emprestados, leitores com atraso (ordenados por dias de atraso) e acervo por autor.
7. Dados salvos em arquivo.

**Critérios de aceite**
- [ ] Livro e leitor sem duplicados (`equals`/`hashCode` pelo ISBN e pelo CPF).
- [ ] O 4º empréstimo simultâneo é rejeitado.
- [ ] Devolução com atraso calcula a multa certa, inclusive na virada de mês.
- [ ] Na devolução, o exemplar vai para o primeiro da fila de espera.
- [ ] Relatórios usam `Comparator`, não ordenação manual.
- [ ] Fechar e abrir mantém tudo.

**Pergunta de design:** empréstimo é uma classe própria ou um atributo do exemplar? O que muda no histórico com cada escolha?

---

## Semana 4: Coleções parte 2 (Set, Map, Queue) e projeto final

### Dia 22: Analisador de logs de acesso

**Pré-requisito:** aulas de Set e NavigableSet.

Um servidor gera um log de acessos e ninguém lê.

**Requisitos**
1. Ler um log com linhas `IP - [dd/MM/yyyy:HH:mm:ss] "GET /caminho" status`.
2. Responder:
   - IPs únicos (`HashSet`);
   - páginas na ordem do primeiro acesso, sem repetir (`LinkedHashSet`);
   - IPs em ordem (`TreeSet`);
   - acessos entre dois horários (`subSet` num `TreeSet` de horários);
   - IPs que só deram erro 4xx/5xx.

**Critérios de aceite**
- [ ] Cada pergunta usa o `Set` certo, e o README justifica cada escolha.
- [ ] O intervalo de horários inclui o início e exclui o fim; o README mostra como inverter isso.
- [ ] Linhas fora do formato são contadas e reportadas, não derrubam a leitura.
- [ ] IPs ordenados numericamente (`10.0.0.2` antes de `10.0.0.10`), não como texto.

**Pergunta de design:** como fazer o `TreeSet` ordenar IP como número? Um `Comparator` ou uma classe `Ip` com `Comparable`?

---

### Dia 23: Frequência de palavras em um livro

**Pré-requisito:** aulas de Map.

Uma editora quer saber quais palavras um autor mais usa.

**Requisitos**
1. Ler um livro em `.txt` (ex.: *Dom Casmurro*, domínio público).
2. Contar a frequência de cada palavra, sem diferenciar maiúscula, ignorando pontuação.
3. Ignorar palavras de parada (a, o, de, que, e…) listadas num arquivo.
4. Mostrar as 20 mais frequentes e as que aparecem só uma vez.

**Critérios de aceite**
- [ ] A contagem usa `merge` ou `getOrDefault`, sem o `if (containsKey)` na mão.
- [ ] "Capitu", "capitu" e "Capitu," contam como a mesma palavra.
- [ ] Palavras com acento e hífen (`guarda-chuva`) não são quebradas.
- [ ] O top 20 vem ordenado por frequência e, no empate, alfabeticamente.
- [ ] O README registra o tempo de processamento do livro inteiro.

**Pergunta de design:** `HashMap` ou `TreeMap` para a contagem? E para o resultado?

---

### Dia 24: Refatorar o Dia 5 com Map

**Pré-requisito:** aulas de Map.

O relatório de vendas do Dia 05 foi feito sem `Map`. Agora o financeiro quer mais visões.

**Requisitos**
1. Refatorar o Dia 05 usando `Map` onde fizer sentido.
2. Novos relatórios: total por vendedor e por produto (Map aninhado) e total por dia do mês.
3. Manter o comportamento antigo: mesmas linhas inválidas, mesmos totais.

**Critérios de aceite**
- [ ] Com o mesmo CSV, o relatório antigo sai igual, número por número (comparar com a saída do Dia 05).
- [ ] O README compara antes e depois: linhas de código e complexidade (Big O) de cada cálculo.
- [ ] Commits separados: um `refactor` sem mudar comportamento, depois um `feat` com os relatórios novos.
- [ ] Dia sem venda aparece com zero no relatório por dia.

**Pergunta de design:** quando um `Map<String, Map<String, BigDecimal>>` deve virar uma classe com nome?

---

### Dia 25: Cache com limite de tamanho

**Pré-requisito:** aulas de LinkedHashMap.

O cache de CEP do Dia 12 cresce sem limite.

**Requisitos**
1. Um cache LRU genérico (`Cache<K, V>`) com capacidade máxima.
2. Ao passar da capacidade, sai o item usado há mais tempo.
3. Estatísticas: acertos, falhas e taxa de acerto.
4. Plugar o cache no Dia 12.

**Critérios de aceite**
- [ ] Usa `LinkedHashMap` com `accessOrder = true` e `removeEldestEntry`.
- [ ] Prova no `main`: com capacidade 3, inserir A, B, C, ler A e inserir D remove B, não A.
- [ ] Capacidade zero ou negativa é rejeitada.
- [ ] O cache é genérico: funciona com `String`→`Endereco` e com `Integer`→`String` sem cast.
- [ ] O README explica por que `get` muda a ordem e qual o custo disso.

**Pergunta de design:** herdar de `LinkedHashMap` ou ter um `LinkedHashMap` dentro? O que vaza para quem usa em cada opção?

---

### Dia 26: Fila de pronto-socorro

**Pré-requisito:** aulas de Queue e PriorityQueue.

Um pronto-socorro atende por ordem de chegada e um infarto esperou atrás de uma unha encravada.

**Requisitos**
1. Triagem com a classificação de Manchester: VERMELHO (emergência), LARANJA, AMARELO, VERDE, AZUL.
2. Chamar o próximo paciente: maior gravidade primeiro; na mesma cor, quem chegou antes.
3. Ver a fila sem chamar ninguém, na ordem real de atendimento.
4. Reclassificar um paciente que piorou.
5. Avisar quem esperou mais que o limite da sua cor (ex.: AMARELO, 60 min).

**Critérios de aceite**
- [ ] Usa `PriorityQueue` com `Comparator`, com prioridade e limite de espera no enum.
- [ ] Dois AMARELOS são chamados por ordem de chegada (prova no `main`).
- [ ] O README explica por que imprimir a `PriorityQueue` direto não mostra a ordem de atendimento, e o código mostra a ordem certa.
- [ ] Reclassificar reposiciona o paciente; o README explica por que mudar o campo dentro da fila não basta.
- [ ] Chamar com a fila vazia não lança exceção.

**Pergunta de design:** o desempate por chegada usa horário ou um contador de senha? O que pode dar errado com horário?

---

### Dia 27: Boletim escolar

**Pré-requisito:** aulas de Map e NavigableMap.

Uma escola calcula as notas na planilha e erra a situação dos alunos.

**Requisitos**
1. Alunos com notas por disciplina (`Map<Disciplina, List<BigDecimal>>`).
2. Média por disciplina e média geral.
3. Conceito por faixa com `TreeMap.floorEntry`: 9 a 10 → A, 7 a 9 → B, 5 a 7 → C, abaixo de 5 → D.
4. Situação: aprovado (todas ≥ 6), recuperação (até 2 abaixo de 6), reprovado.
5. Boletim por aluno e ranking da turma por disciplina.

**Critérios de aceite**
- [ ] As faixas de conceito ficam num `TreeMap`, não em `if`/`else`.
- [ ] Média exatamente 7,0 dá B, não C.
- [ ] Nota fora de 0 a 10 é rejeitada.
- [ ] Disciplina sem nota aparece como "sem nota" e não entra na média.
- [ ] Agrupar os alunos por situação devolve um `Map<Situacao, List<Aluno>>`.

**Pergunta de design:** as faixas de conceito são configuração ou código? Como mudar a régua sem recompilar?

---

### Dia 28: Projeto final (1/3): Pedidos de restaurante: design

**Pré-requisito:** semanas 1 a 4.

Um restaurante quer sair da comanda de papel. Os três últimos dias são um projeto só, pensado para ser vitrine no GitHub. **Hoje não tem código.**

**O que o dono do restaurante disse:**
> "Tenho 15 mesas. O garçom anota o pedido e leva na cozinha. A cozinha faz na ordem que chega, mas prato de criança passa na frente. Às vezes acaba um ingrediente e o garçom só descobre depois. No fim, a conta vem com 10% opcional e a mesa quer dividir. No fim do dia quero saber o que mais vendeu e quanto entrou."

**Requisitos de hoje**
1. Transformar a fala acima em requisitos numerados e critérios de aceite, no formato dos tickets.
2. Listar as dúvidas que você faria ao dono e escolher uma resposta para cada uma (premissas).
3. Modelagem: classes, responsabilidades, enums, exceções e qual coleção guarda o quê, e por quê.
4. Quebrar a implementação em tarefas para os Dias 29 e 30.

**Critérios de aceite**
- [ ] Todo trecho da fala do dono vira pelo menos um requisito ou uma premissa.
- [ ] Cada requisito tem pelo menos um critério de aceite testável no `main`.
- [ ] Cada coleção escolhida tem justificativa (por que `PriorityQueue` e não `List`, etc.).
- [ ] As tarefas dos Dias 29 e 30 cabem em 1h30 cada.

**Pergunta de design:** quais são os estados de um pedido e quem pode mudar cada um?

---

### Dia 29: Projeto final (2/3): Núcleo do sistema

**Pré-requisito:** Dia 28.

**Requisitos**
1. Implementar o domínio desenhado no Dia 28: cardápio, mesas, pedidos, fila da cozinha, estoque de ingredientes e conta.
2. Provar todos os critérios do núcleo no `main`.

**Critérios de aceite** (complementam os que você escreveu no Dia 28)
- [ ] Pedido com item sem ingrediente é rejeitado no momento do pedido, não na cozinha.
- [ ] A fila da cozinha respeita a prioridade e a ordem de chegada.
- [ ] Os valores usam `BigDecimal`; a conta dividida em 3 de R$ 100,00 fecha exatamente R$ 100,00 (33,34 + 33,33 + 33,33).
- [ ] Não dá para pular estados do pedido (ex.: de "recebido" direto para "entregue").
- [ ] Mesa com conta aberta não recebe outro grupo.
- [ ] Mudanças em relação ao design do Dia 28 estão registradas no README, com o motivo.

**Pergunta de design:** o que você mudou do design de ontem ao codar, e o que isso diz sobre o design?

---

### Dia 30: Projeto final (3/3): Persistência, relatórios e entrega

**Pré-requisito:** Dia 29.

**Requisitos**
1. Persistir cardápio, estoque e pedidos do dia em arquivo.
2. Relatório de fechamento: total do dia, total dos 10%, itens mais vendidos, ticket médio por mesa e horário de pico.
3. Menu no terminal para operar o restaurante.
4. README de vitrine: o que é, como rodar, decisões de design, o que ficou de fora e próximos passos.

**Critérios de aceite**
- [ ] Fechar e abrir no meio do expediente mantém mesas abertas e a fila da cozinha.
- [ ] O relatório de fechamento bate com a soma das contas fechadas.
- [ ] Nenhuma entrada inválida no menu derruba o programa.
- [ ] Alguém que nunca viu o projeto consegue rodar seguindo só o README.
- [ ] O README da raiz tem a tabela de progresso completa e uma retrospectiva dos 30 dias: o que ficou fácil, o que ainda trava e o que estudar em seguida.

**Pergunta de design:** se esse sistema virasse uma API web amanhã, quais classes mudariam e quais ficariam iguais?
