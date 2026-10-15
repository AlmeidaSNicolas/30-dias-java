# 30 Dias de Java

Desafios práticos feitos depois das aulas da [Maratona Java do DevDojo](https://www.youtube.com/@DevDojoBrasil), começando na aula 162 (Coleções / equals).

Cada dia é um **ticket**, como no trabalho: contexto, requisitos e critérios de aceite. A dificuldade sobe aos poucos:

- **Dias 02 a 10:** começam concretos e cada dia acrescenta uma ideia nova de design, até chegar em interface no Dia 10. Todos com o ticket **destrinchado** (design pronto) e um **caminho** detalhado (passos na ordem, sem código).
- **Dias 11 a 20:** design pronto, caminho mais curto. Parte das decisões fica comigo.
- **Dias 21 a 30:** só o ticket, sem caminho. Quebrar o problema em tarefas faz parte do desafio.

**Início:** 12/10/2026 · **Dedicação:** 1h30+ por dia

## Regras

1. **Ler antes de codar.** Antes de abrir a IDE, leio o Destrinchado e o Caminho do ticket. Se discordar de alguma decisão, discuto antes de codar e o motivo vai para o README do dia.
2. **Os critérios de aceite são o teste.** O dia só termina quando todos passam, e cada um é provado por uma classe de teste (`main`) que imprime o esperado e o obtido.
3. **Sem IA gerando código.** IA só para revisão depois de terminar ou para tirar dúvida de conceito. O código é meu.
4. **Travou mais de 30 minutos?** A dúvida vai para o README, o requisito é quebrado em partes menores e eu ataco a menor. Se não sair, fica como pendência e eu sigo.
5. **Não pule critério.** Critério não cumprido fica registrado no README. Entrega incompleta e honesta vale mais que fingir que está pronto.
6. **Dia perdido não é desafio perdido.** O número do dia é a ordem, não a data.

## Organização

Um repositório só, com uma pasta por dia e vários commits por dia.

```
30-dias-java/
├── README.md                  # este arquivo: regras + progresso
├── dia-01-conta-bancaria/
│   ├── README.md              # entendimento, design, decisões, pendências
│   └── src/
├── dia-02-carrinho-compras/
└── ...
```

Commits em [Conventional Commits](https://www.conventionalcommits.org/pt-br/), em português:

```
docs(dia-01): descreve design da conta bancária
feat(dia-01): implementa saque com cheque especial
fix(dia-01): impede transferência parcial em caso de falha
refactor(dia-24): reescreve relatório de vendas com Map
```

## Plano

### Semana 1: Consolidação (POO, exceções e tempo)

O foco é modelar bem: quem é responsável por cada regra, onde cada validação mora e que exceção representa cada falha.

| Dia | Desafio | Conceitos |
|:---:|---|---|
| 01 | Conta bancária com extrato | Encapsulamento, exceções customizadas, java.time, List |
| 02 | Carrinho de compras | Classes e objetos, `ArrayList`, `BigDecimal`, exceções |
| 03 | Agenda de consultas | LocalDateTime, Duration, DayOfWeek, exceções |
| 04 | Validador de cadastro | String, regex básico, classe de resultado |
| 05 | Relatório de vendas a partir de CSV | Leitura de arquivo, try-with-resources, parsing, BigDecimal |
| 06 | Estacionamento | Enum com atributos e métodos, Duration, regex |
| 07 | **Fim de semana:** Mini-banco no terminal | Camadas, Scanner, salvar e carregar de arquivo |

### Semana 2: Arquivos, rede e revisão do BotTelegram

O que separa "funciona na minha máquina" de "roda 24/7 sem cair", incluindo um dia auditando o meu próprio [BotTelegram](https://github.com/AlmeidaSNicolas/BotTelegram).

| Dia | Desafio | Conceitos |
|:---:|---|---|
| 08 | Organizador da pasta Downloads | Path, Files, enum com lista de extensões |
| 09 | Backup incremental | Files.walk, data de modificação, cópia mantendo a estrutura |
| 10 | Organizador v2: regras com interface | Interface, polimorfismo, aberto/fechado (reaproveita o Dia 08) |
| 11 | Auditoria do BotTelegram: recursos abertos e exceções | try-with-resources, AutoCloseable, JDBC, propagação de exceções |
| 12 | Consulta de CEP com cache local | HttpClient, HttpRequest, timeouts, cache, desserialização |
| 13 | API de tarefas com HttpServer | HTTP, status codes, separação handler/serviço/repositório |
| 14 | **Fim de semana:** Monitor de sites | Agendamento, HttpClient, Duration, estado entre execuções |

### Semana 3: Coleções parte 1 (igualdade, listas e ordenação)

A partir daqui os desafios acompanham o curso: cada dia só depois das aulas do pré-requisito. Se a aula ainda não chegou, faço um [desafio reserva](#desafios-reserva).

| Dia | Desafio | Conceitos | Pré-requisito |
|:---:|---|---|---|
| 15 | O contrato do equals, na prática | equals, contrato, herança e igualdade | Aulas de equals |
| 16 | Clientes sem duplicados | hashCode, HashSet, mutabilidade de chave | Aula de hashCode |
| 17 | Playlist | List, ArrayList x LinkedList, índices, Collections.shuffle | Aulas de List |
| 18 | Ranking de jogadores | Comparable, compareTo, Collections.sort | Aula de Comparable |
| 19 | Relatórios de RH com várias ordenações | Comparator, encadeamento de critérios, nulls | Aula de Comparator |
| 20 | Estoque com validade | Iterator, remove, Collections.binarySearch, complexidade | Aulas de binarySearch e Iterator |
| 21 | **Fim de semana:** Biblioteca v2 | Modelagem, equals/hashCode, List, Comparator, persistência | Dias 15 a 20 |

### Semana 4: Coleções parte 2 (Set, Map, Queue) e projeto final

Os três últimos dias são um projeto só, pensado para ser vitrine.

| Dia | Desafio | Conceitos | Pré-requisito |
|:---:|---|---|---|
| 22 | Analisador de logs de acesso | HashSet, LinkedHashSet, TreeSet, subSet | Aulas de Set e NavigableSet |
| 23 | Frequência de palavras em um livro | Map, merge/getOrDefault, regex, ordenação de entradas | Aulas de Map |
| 24 | Refatorar o Dia 5 com Map | Map aninhado, refatoração, Big O | Aulas de Map |
| 25 | Cache com limite de tamanho | LinkedHashMap com ordem de acesso, removeEldestEntry, LRU | Aulas de LinkedHashMap |
| 26 | Fila de pronto-socorro | Queue, PriorityQueue, Comparator, desempate | Aulas de Queue e PriorityQueue |
| 27 | Boletim escolar | Map de List, TreeMap.floorEntry, agrupamento | Aulas de Map e NavigableMap |
| 28 | **Projeto final (1/3):** Pedidos de restaurante: design | Modelagem, levantamento de requisitos, quebra de tarefas | Semanas 1 a 4 |
| 29 | **Projeto final (2/3):** Núcleo do sistema | POO, exceções, coleções, java.time, BigDecimal | Dia 28 |
| 30 | **Projeto final (3/3):** Persistência, relatórios e entrega | Persistência, relatórios, documentação | Dia 29 |

### Desafios reserva

Para quando o pré-requisito de um dia ainda não chegou no curso. Requisitos e critérios de aceite completos são escritos por mim no README antes de começar: montar o ticket faz parte do desafio.

| Código | Desafio | Resumo |
|:---:|---|---|
| R1 | Conversor de moedas | Cotações via HttpClient, conversão com BigDecimal, histórico em arquivo. API fora do ar usa a última cotação salva e avisa a data dela. |
| R2 | Gerador e verificador de senhas | Regras configuráveis e classificação de força com regex. A classificação explica o que falta para a senha ficar forte. |
| R3 | Jogo da forca | Palavras de arquivo por categoria, placar salvo entre partidas. Letra repetida não conta como erro e acentos são tratados. |
| R4 | Controle de hábitos | Sequência atual e maior sequência de cada hábito. Dia sem registro quebra a sequência e registro duplo no mesmo dia não conta em dobro. |

## Progresso

| Dia | Desafio | Feito | Critérios ok | Data |
|:---:|---|:---:|:---:|:---:|
| 01 | [Conta bancária com extrato](dia-01-conta-bancaria/) | ✅ | ✅ | 12/10/2026 |
| 02 | [Carrinho de compras](dia-02-carrinho-compras/) | ⬜ | ✅ | |
| 03 | [Agenda de consultas](dia-03-Agenda_Consultas/) | ⬜ | ⬜ | |
| 04 | Validador de cadastro | ⬜ | ⬜ | |
| 05 | Relatório de vendas a partir de CSV | ⬜ | ⬜ | |
| 06 | Estacionamento | ⬜ | ⬜ | |
| 07 | Mini-banco no terminal | ⬜ | ⬜ | |
| 08 | Organizador da pasta Downloads | ⬜ | ⬜ | |
| 09 | Backup incremental | ⬜ | ⬜ | |
| 10 | Organizador v2: regras com interface | ⬜ | ⬜ | |
| 11 | Auditoria do BotTelegram: recursos e exceções | ⬜ | ⬜ | |
| 12 | Consulta de CEP com cache local | ⬜ | ⬜ | |
| 13 | API de tarefas com HttpServer | ⬜ | ⬜ | |
| 14 | Monitor de sites | ⬜ | ⬜ | |
| 15 | O contrato do equals, na prática | ⬜ | ⬜ | |
| 16 | Clientes sem duplicados | ⬜ | ⬜ | |
| 17 | Playlist | ⬜ | ⬜ | |
| 18 | Ranking de jogadores | ⬜ | ⬜ | |
| 19 | Relatórios de RH com várias ordenações | ⬜ | ⬜ | |
| 20 | Estoque com validade | ⬜ | ⬜ | |
| 21 | Biblioteca v2 | ⬜ | ⬜ | |
| 22 | Analisador de logs de acesso | ⬜ | ⬜ | |
| 23 | Frequência de palavras em um livro | ⬜ | ⬜ | |
| 24 | Refatorar o Dia 5 com Map | ⬜ | ⬜ | |
| 25 | Cache com limite de tamanho | ⬜ | ⬜ | |
| 26 | Fila de pronto-socorro | ⬜ | ⬜ | |
| 27 | Boletim escolar | ⬜ | ⬜ | |
| 28 | Projeto final: design | ⬜ | ⬜ | |
| 29 | Projeto final: núcleo | ⬜ | ⬜ | |
| 30 | Projeto final: persistência e entrega | ⬜ | ⬜ | |

Legenda: ⬜ pendente · ✅ feito · ⚠️ feito com pendência (ver README do dia)

## Stack

- Java 17
- Maven
- Lombok
