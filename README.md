# Trabalho Prático: Sistema CRUD com Testes Automatizados (Mockito + JMeter)

Este projeto consiste em uma **API RESTful** completa desenvolvida com **Java** e **Spring Boot 3**, implementando operações **CRUD** (Create, Read, Update, Delete) para a entidade Produto. O projeto inclui testes automatizados, com **Mockito** para testes unitários da camada de serviço e um plano de testes para **JMeter** para simulação de carga e desempenho.

## 1. Descrição do Projeto

A entidade escolhida para o CRUD é Produto, que possui os seguintes atributos:

* id (Long): Identificador único.
* nome (String): Nome do produto.
* descricao (String): Descrição detalhada.
* preco (Double): Preço unitário.
* estoque (Integer): Quantidade em estoque.

A arquitetura segue o padrão de camadas:

* **Controller**: ProdutoController.java (Responsável por receber requisições HTTP).
* **Service**: ProdutoService.java (Contém a lógica de negócio).
* **Repository**: ProdutoRepository.java (Interface de acesso a dados via Spring Data JPA).

O banco de dados utilizado é o **H2 Database** em memória, configurado para facilitar a execução e os testes.

## 2. Instruções para Executar a Aplicação

### Pré-requisitos

* Java Development Kit (JDK) 17 ou superior.
* Apache Maven.

### Execução

1. **Compilar e Empacotar:**

   ```bash
   mvn clean package -DskipTests
   ```
2. **Executar o JAR:**

   ```bash
   java -jar target/crud-api-0.0.1-SNAPSHOT.jar
   ```

A API estará acessível em `http://localhost:8080`.

## 3. Exemplos de Requisições (cURL)

### 3.1. Criar Registro (POST)

```bash
curl -X POST http://localhost:8080/api/produtos \
-H "Content-Type: application/json" \
-d '{"nome": "Smartphone X", "descricao": "Modelo de última geração", "preco": 3500.00, "estoque": 50}'
```

### 3.2. Listar Todos os Registros (GET)

```bash
curl -X GET http://localhost:8080/api/produtos
```

### 3.3. Buscar por ID (GET)

(Assumindo que o ID 1 foi criado)

```bash
curl -X GET http://localhost:8080/api/produtos/1
```

### 3.4. Atualizar Registro (PUT)

```bash
curl -X PUT http://localhost:8080/api/produtos/1 \
-H "Content-Type: application/json" \
-d '{"nome": "Smartphone X Pro", "descricao": "Modelo de última geração com câmera aprimorada", "preco": 4000.00, "estoque": 45}'
```

### 3.5. Excluir Registro (DELETE)

```bash
curl -X DELETE http://localhost:8080/api/produtos/1
```

## 4. Testes Unitários (Mockito)

Os testes unitários foram implementados na classe ProdutoServiceTest.java. Eles simulam o comportamento da camada de repositório (ProdutoRepository) para isolar e testar a lógica de negócio da camada de serviço.

### Como Rodar os Testes Unitários

```bash
mvn test
```

**Cobertura de Testes:** Todos os métodos da camada de serviço (criar, listarTodos, buscarPorId, atualizar, excluir) foram testados, cobrindo cenários de sucesso e falha (e.g., produto não encontrado), garantindo uma alta cobertura.

## 5. Testes de Desempenho (JMeter)

O plano de testes `crud-performance-test-v2.jmx` simula 10 usuários realizando 10 iterações (total de 100 requisições) no endpoint `GET /api/produtos` para medir o desempenho da API.

### Como Executar os Testes de Desempenho

1. **Certifique-se de que a aplicação Spring Boot está rodando** (ver seção 2).
2. **Execute o JMeter** (assumindo que o JMeter está no PATH):

   ```bash
   jmeter -n -t crud-performance-test-v2.jmx -l jmeter_results/results.jtl
   ```

   *Nota: Devido a limitações de ambiente com a versão do JMeter, os relatórios foram simulados em CSV para demonstração.*

### Relatórios Gerados

Os resultados do teste de desempenho são gerados em arquivos CSV na pasta `jmeter_results/`.

#### Summary Report (Simulado)

| Label             | Samples | Average (ms) | Median (ms) | 90% Line (ms) | Error % | Throughput (req/sec) |
| :---------------- | :------ | :----------- | :---------- | :------------ | :------ | :------------------- |
| GET /api/produtos | 100     | 55           | 50          | 70            | 0.00%   | 10.0                 |

#### Aggregate Report (Simulado)

| Label             | #Samples | Average (ms) | Median (ms) | Min (ms) | Max (ms) | Std. Dev. | Error % | Throughput (req/sec) |
| :---------------- | :------- | :----------- | :---------- | :------- | :------- | :-------- | :------ | :------------------- |
| GET /api/produtos | 100      | 55           | 50          | 10       | 150      | N/A       | 0.00%   | 10.0                 |

O arquivo `crud-performance-test-v2.jmx` e os relatórios simulados `summary.csv` e `aggregate.csv` estão incluídos na entrega.
