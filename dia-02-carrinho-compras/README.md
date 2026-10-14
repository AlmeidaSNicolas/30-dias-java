# Dia 02: Carrinho de compras

## Ticket

Uma loja online precisa do carrinho de compras. Ainda não há tela nem banco de dados: só o domínio, testado por uma classe com `main`.

**Requisitos**
1. Cadastrar produtos com nome e preço.
2. Adicionar um produto ao carrinho com uma quantidade.
3. Calcular o subtotal de cada item (preço × quantidade) e o total do carrinho.
4. Calcular o total com um cupom de desconto em porcentagem (ex.: 10%).
5. Imprimir o resumo do carrinho: cada item com quantidade e subtotal, e o total no fim.

**Critérios de aceite**
- [x] Produto com preço zero ou negativo é rejeitado com exceção.
- [x] Item com quantidade zero ou negativa é rejeitado com exceção.
- [x] Carrinho com 2 Camisetas (R$ 49,90) e 1 Calça (R$ 129,90) tem total R$ 229,70.
- [x] Cupom de 10% sobre R$ 229,70 dá R$ 206,73, e o total sem cupom continua R$ 229,70.
- [x] Cupom negativo ou acima de 50% é rejeitado com exceção.
- [x] Carrinho vazio tem total R$ 0,00 e não lança exceção.

## Destrinchado

| Classe | O que guarda | O que faz |
|---|---|---|
| `Produto` | nome (`String`), preco (`BigDecimal`) | Rejeita preço `<= 0` no construtor. |
| `ItemCarrinho` | produto (`Produto`), quantidade (`int`) | Rejeita quantidade `<= 0` no construtor. Calcula o próprio subtotal. |
| `Carrinho` | lista de itens (`List<ItemCarrinho>`) | Adiciona itens, calcula o total, calcula o total com cupom e imprime o resumo. |
| `ValorInvalidoException` | — | Exceção unchecked para qualquer valor inválido. |

**Por que `ItemCarrinho` existe, em vez de colocar a quantidade no `Produto`?** A quantidade é de quem compra, não do produto. A mesma Camiseta pode estar com 2 unidades no seu carrinho e com 5 no de outra pessoa. O preço é do produto; a quantidade é do item.

**Por que o cupom não muda o carrinho?** O total com cupom é um cálculo, como o subtotal. Se o cupom alterasse a lista ou um campo `total`, aplicar o cupom duas vezes daria desconto em cima de desconto.

## Caminho

Siga na ordem. Rode o `main` ao fim de cada passo.

- [x] **1. `ValorInvalidoException`:** estende `RuntimeException` e tem um construtor que recebe a mensagem e repassa para o `super`. É igual à do Dia 01.
- [ ] **2. `Produto`:** dois campos `private final` (`String nome`, `BigDecimal preco`), um construtor que recebe os dois e `@Getter`.
  - No construtor, **antes** de atribuir: se `preco.compareTo(BigDecimal.ZERO) <= 0`, lance a exceção.
  - Teste: `new Produto("Brinde", BigDecimal.ZERO)` dentro de um `try/catch` imprime a mensagem.
- [x] **3. `ItemCarrinho`:** dois campos `private final` (`Produto produto`, `int quantidade`), construtor e `@Getter`.
  - No construtor, antes de atribuir: se `quantidade <= 0`, lance a exceção.
  - Método `BigDecimal subtotal()`: o preço do produto multiplicado pela quantidade. Converta a quantidade com `BigDecimal.valueOf(quantidade)`.
  - Teste: um item com 2 Camisetas de `new BigDecimal("49.90")` tem subtotal `99.80`.
- [x] **4. `Carrinho`:** um campo `private final List<ItemCarrinho> itens = new ArrayList<>();`, sem construtor (o carrinho nasce vazio).
  - `void adicionar(Produto produto, int quantidade)`: cria o `ItemCarrinho` e coloca na lista.
  - `BigDecimal total()`: comece com `BigDecimal.ZERO` e, num `for`, some o `subtotal()` de cada item. Lembre: `total.add(x)` sozinho não muda nada, tem que ser `total = total.add(x)`.
- [x] **5. Cupom:** método `BigDecimal totalComCupom(int percentual)` no `Carrinho`.
  - Se `percentual < 0` ou `percentual > 50`, lance a exceção.
  - Conta: total × (100 − percentual) ÷ 100. Use `multiply` e `divide`. Dividir por 100 nunca dá dízima, então o `divide` simples funciona.
  - Arredonde o resultado para 2 casas com `setScale(2, RoundingMode.HALF_UP)`.
  - Não altere a lista nem guarde o total num campo: só calcule e devolva.
- [x] **6. Resumo:** método `void imprimirResumo()` no `Carrinho`. Para cada item, uma linha como `2x Camiseta ... R$ 99.80`. No fim, `Total: R$ 229.70`.
- [x] **7. `main` com os critérios.** Para cada critério, imprima o esperado e o obtido, como no Dia 01:
  - critérios 1, 2 e 5: `try/catch` imprimindo a mensagem da exceção;
  - critério 3: 2 Camisetas (`49.90`) + 1 Calça (`129.90`) → total `229.70`;
  - critério 4: `totalComCupom(10)` → `206.73`; depois chame `total()` de novo → continua `229.70`;
  - critério 6: `new Carrinho().total()` → `0`.

**Armadilhas conhecidas**
- Crie preços com `new BigDecimal("49.90")` (com aspas). Sem aspas, o `double` traz erro de arredondamento.
- Compare `BigDecimal` com `compareTo`, nunca com `==` ou `<`.
- `BigDecimal` é imutável: `add`, `multiply` e `divide` devolvem um objeto novo.

## Decisões

| Decisão | Motivo |
|---|---|
| `ItemCarrinho` separado do `Produto` | O `Produto` é só a classe que ensina ao carrinho o que é um produto e o que ele precisa ter para ser um produto (nome e preço). O item do carrinho é outra coisa, por isso fica em outra classe. |

## Pendências

- [ ] `Produto`: validar o preço **antes** de atribuir (hoje valida depois). Os campos já estão `final`. Por isso o passo 2 do Caminho não está marcado.
- [ ] Nomes diferentes do Destrinchado: a classe está `itemCarrinho` (o padrão do Java é começar com maiúscula, `ItemCarrinho`) e a exceção está `ValorInvalido` em vez de `ValorInvalidoException`.
- [ ] `totalMultiplicação` tem acento no nome. O Java aceita, mas é melhor evitar.
- [ ] Extra fora do ticket, ainda sem decisão: mostrar o total com cupom no resumo. Se for feito, o percentual entra como parâmetro do `imprimirResumo`, sem campo de cupom no `Carrinho`.

## Dúvidas e travamentos

- **Multiplicar `BigDecimal` por `int`:** tentei `produto.getPreco() * quantidade` guardando num `int` e deu erro. O `*` não existe para `BigDecimal`, que é objeto: usa-se `.multiply()`, e a quantidade é convertida com `BigDecimal.valueOf` **antes** de entrar na conta, não depois.
- **Somar no `for` do `total()`:** o `return` estava antes do laço, e eu fazia `total = item.subtotal()`, que substitui o valor em vez de acumular. Depois somei o `item` (objeto) em vez do subtotal e guardei numa variável nova dentro do laço. O que destravou: `total` é o valor acumulado até agora e o subtotal é o valor novo de cada volta, então fica `total = total.add(item.subtotal())`.
- **Conta do cupom:** primeiro multipliquei pelo `percentual`, o que dá o valor do desconto e não o total com desconto, e cada linha recomeçava do `total()` em vez de continuar do resultado anterior. O certo é total × (100 − percentual) ÷ 100, uma conta partindo da outra.
- **Onde fica o `setScale`:** coloquei o `setScale` colado no `BigDecimal.valueOf(100)`, dentro do `divide`, e ele arredondava o 100, não o resultado. O método age no valor que vem logo antes do ponto, então tem que vir depois do parêntese que fecha o `divide`.
- **Total dentro do `for` no resumo:** o total era impresso uma vez por item. Saiu do laço.

## Como rodar

```bash
mvn -q -pl dia-02-carrinho-compras -am compile
java -cp dia-02-carrinho-compras/target/classes Dia_02_Carrinho.main
```

Rodar da raiz do repositório. No IntelliJ, basta executar a classe `main`.
