<h1 align="center">Orchestrator-service</h1>
<p align="center">Serviço que tem como responsabilidade, gerenciar o processo de pagamento de assinaturas, dentro da PetFriends.</p>

<p align="center">
 <a href="#contexto-geral">Contexto geral</a> •
 <a href="#deploy">Deploy</a> •
 <a href="#pre-requisitos">Pré-requisitos</a> •
 <a href="#rodando-a-api">Rodando a aplicação</a> •
</p>

## 📝 [Contexto geral](#-contexto-geral)
Esse serviço, nasceu com a intenção de permitir que a PetFriends disponibilizasse diversas formas de pagamento para seus clientes, e que esses clientes pudessem escolher a forma de pagamento que melhor se encaixasse em suas necessidades.
Sendo assim, eu decidi que o serviço de pagamento seria apartado do serviço de assinatura, e que a comunicação entre as partes, seria feito através de mensageria. Portanto, o desenho da arquitetura ficou da seguinte forma:  

<img width="663" align="center" alt="image" src="https://github.com/dev-rodrigues/arquitetura/blob/main/1.jpeg?raw=true">
<br/>
<br/>
Além disso, service--orchestrator, tem como premissa, implementar o ‘designer’ pattern, chamado Saga, sendo um padrão de projeto de ‘software’ que visa implementar a execução de uma série de ações, de forma distribuída, com o intuito de garantir a consistência das informações, mesmo que ocorra algum erro durante a execução de uma das ações.
Portanto, o service--orchestrator, é responsável por executar as seguintes ações:

- Receber pedidos de pagamento
- Distribuir os pedidos de pagamento para os serviços responsáveis por cada forma de pagamento
- Notificar o serviço de assinatura, quando o pagamento for aprovado, reprovado ou pendente

## :robot: [Deploy](#-deploy)
O deploy da aplicação, foi feito utilizando o Github Actions. 
A premissa do pipeline é disponibilizar uma nova versão da imagem da aplicação no Docker Hub para que com o ArgoCD que é um gerenciador de configurações declarativas, gerenciado pelo Kubernetes, possa fazer o deploy da nova versão da aplicação.

## ✅ [Pré-requisitos](#pre-requisitos)
Nessa aplicação foram utilizadas algumas tecnologias, cada uma delas teve uma importância significativa no projeto, sendo elas:

- [**Java 17**](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html): Linguagem de programação utilizada para escrever toda a aplicação.


- [**Maven**](https://maven.apache.org/download.cgi): Ferramenta de automação de compilação utilizada para gerenciar as dependências do projeto.


- [**Spring Boot**](https://spring.io/projects/spring-boot): Framework utilizado para construir toda a integração da aplicação com o mundo externo, como, por exemplo, integração com o banco de dados, integração com o RabbitMQ e com o serviço de cliente.


- [**Docker**](https://www.docker.com/): Utilizado para dispinibilizar a aplicação em containers, para ela poder ser executada em qualquer ambiente independente do sistema operacional. Além de permitir a
execução dentro do ambiente cloud.


- [**H2**](https://www.mongodb.com/home):O banco H2 foi escolhido por ser um banco de dados em memória, que não precisa de instalação, e é muito rápido para ser utilizado em testes.


- [**RabbitMQ**](https://www.rabbitmq.com/): Foi utilizado para implementar a comunicação entre os serviços, utilizando o protocolo AMQP.

## 🎲 [Rodando a aplicação](#rodando-a-api)

### Rodando localmente
```bash
# Primeiramente é necessário clonar a aplicação no github:
$ git clone git@github.com:pb-microsservicos/payment_orchestrator.git

# Acesse a pasta do projeto no terminal
$ cd payment_orchestrator

# Após isso, é importante ter uma venv dentro da sua pasta da aplicação
$ mvn clean install 

# Rode a aplicação com o seguinte comando
$ java -jar orchestrator-2.1.jar
```

### Docker
```bash
# Para rodar o serviço usando Docker é necessário primeiro realizar o build da imagem com o seguinte comando:
$ mvn jib:dockerBuild

# Após realizado o build, para executar a imagem basta rodar o seguinte comando:
$ docker run -d --name orchestrator -p 8081:8081 httpsantos/payment_orchestrator

# ou então, você baixar a imagem disponibilizada no Docker Hub:
$ docker run --name orchestrator -p 8081:8081 -d httpsantos/payment_orchestrator:2.4
```

### Links uteis
- Serviço de boleto: [URL](https://github.com/dev-rodrigues/payment--slip-service)
- Arquivos de deployment: [URL](https://github.com/dev-rodrigues/infnet-example-deployment)