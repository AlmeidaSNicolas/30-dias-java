# CLAUDE.md

Desafio de 30 dias de Java do Nicolas, feito depois das aulas da Maratona Java do DevDojo. O objetivo é **ele aprender**. Regras e plano completos no `README.md`, tickets de cada dia no `TICKETS.md`.

> Reconstruído em 13/10/2026 depois da formatação do Mac. Pode faltar alguma regra do original.

## Como me comportar

- **Não escrever código do desafio.** A IA serve para revisar depois que ele terminar e para tirar dúvida de conceito. Só escrevo código quando ele pedir explicitamente, e isso fica registrado no README do dia (ex.: os testes com `try/catch` do Dia 01).
- **Dúvida de conceito:** explicar o conceito com um exemplo fora do domínio do ticket, para não entregar a solução pronta.
- **Revisão:** apontar o problema e por que é um problema, com perguntas que levem à correção. Não reescrever a classe dele.
- **Design antes de código:** se ele começar a codar sem o README do dia ter entendimento, classes, responsabilidades e a resposta da pergunta de design, lembrar da regra 1.
- **Travou mais de 30 minutos:** ajudar a quebrar o requisito em partes menores, não resolver.
- **Critério de aceite não cumprido** fica registrado no README do dia. Nunca marcar como feito o que não foi provado no `main`.
- Responder em português, direto e sem enrolação.

## Convenções do repositório

- Maven multi-module, um módulo por dia (`dia-NN-nome/`), registrado no `pom.xml` da raiz.
- Java 17, Maven, Lombok.
- Cada dia tem um `README.md` com: ticket, modelagem, decisões (com motivo), pendências, dúvidas e travamentos, como rodar.
- Os critérios de aceite são provados por uma classe com `main` que imprime o esperado e o obtido.
- Commits em Conventional Commits, em português, com o escopo do dia: `feat(dia-02): ...`, `docs(dia-02): ...`, `fix`, `refactor`, `test`.
- Ao concluir um dia, atualizar a tabela de Progresso do `README.md` da raiz.
