# Debug Doctors 🏥


<p align="center">
  <img src="https://img.shields.io/badge/Java-25-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 25">
  <img src="https://img.shields.io/badge/Spring_Boot-4.0.3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" alt="Spring Boot 4.0.3">
  <img src="https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white" alt="Maven">
  <img src="https://img.shields.io/badge/JUnit_5-Tested-25A162?style=for-the-badge&logo=junit5&logoColor=white" alt="JUnit 5">
  <img src="https://img.shields.io/badge/Lombok-Active-5d3c85?style=for-the-badge&logo=lombok&logoColor=white" alt="Lombok">
  <img src="https://img.shields.io/badge/JaCoCo-Coverage-008080?style=for-the-badge" alt="JaCoCo">
</p>
API REST para gestão de agendamento de consultas médicas com validações robustas e testes automatizados.

## 📋 Objetivo

Desenvolver uma API REST para gestão de horários médicos, com foco em garantir a **integridade das agendas** através de testes unitários automatizados e entrega contínua via pipeline.

---

## 🛠️ Funcionalidades

A aplicação engloba as seguintes funcionalidades centrais:
- **Gestão de Médicos:** Cadastro, atualização, listagem e remoção de profissionais da saúde, incluindo dados de expediente.
- **Gestão de Pacientes:** Cadastro, atualização, listagem e remoção de pacientes, com validações integradas de CPF e e-mail.
- **Agendamento de Consultas:** Marcação de horários associando um médico e um paciente, validando restrições automaticamente.
- **Cancelamento com Motivo:** Permite o cancelamento de uma consulta agendada mediante registro obrigatório do motivo do cancelamento.
- **Confirmação de Consulta:** Atualização do status do agendamento para confirmar a realização da consulta médica.

---

## 📊 Diagrama de Entidade-Relacionamento (DER)

![Diagrama ER - Debug Doctors](./docs/img/img.png)

---

## ⚙️ Regras de Negócio

As seguintes validações são **obrigatórias** e devem ser testadas:

| Regra | Descrição |
|-------|-----------|
| ⏰ **Conflito de Horário** | Um médico não pode ter dois agendamentos sobrepostos |
| 📅 **Fora do Expediente** | Não permitir agendamentos antes do início ou após o fim da jornada do médico |
| ⏳ **Antecedência Mínima** | Consultas só podem ser agendadas com pelo menos 30 minutos de antecedência |
| ❌ **Regra de Cancelamento** | O cancelamento só é permitido se faltarem mais de 24h para a consulta |
| ⏱️ **Duração Padrão** | Validar se a consulta tem o tempo mínimo/máximo permitido (ex: 30 min) |

---

## 👥 Histórias de Usuário

As histórias de usuário que guiaram o desenvolvimento e suas respectivas coberturas de testes estão detalhadas abaixo:

---

### **US01 - Cadastro de Médicos**
* **Como** Gestor do Hospital, **eu quero** cadastrar médicos com suas especialidades, CRM e horários de expediente **para que** eles fiquem disponíveis para novos agendamentos.
* **Prioridade:** Alta
* **Status Final:** Entregue
* **Critérios de Aceitação:**
  * **Cenário 1: Cadastro realizado com sucesso**
    * **Given** que informei dados válidos (Nome, Especialidade, CRM, Shift Start, Shift End),
    * **When** eu solicitar o cadastro do médico,
    * **Then** o médico deve ser cadastrado com sucesso.
  * **Cenário 2: Cadastro rejeitado por CRM em branco**
    * **Given** que o CRM não foi informado ou está vazio,
    * **When** eu solicitar o cadastro do médico,
    * **Then** o sistema deve lançar uma exceção de argumento inválido.
  * **Cenário 3: Cadastro rejeitado por expediente inválido**
    * **Given** que o horário de início do expediente é posterior ao horário de término,
    * **When** eu solicitar o cadastro do médico,
    * **Then** o sistema deve lançar uma exceção de argumento inválido.
* **Rastreabilidade:** US01 -> Issue #1 / PR #1 -> [DoctorTest.java](file:///Users/schulzdimitrii/Documents/GitHub-Projects/debug-doctors/src/test/java/br/inatel/debug_doctors/domain/DoctorTest.java) (`shouldCreateDoctorSuccessfully`, `shouldValidateDoctorWithValidCrm`, `shouldThrowWhenDoctorHasInvalidShift`)

---

### **US02 - Cadastro de Pacientes**
* **Como** Gestor do Hospital, **eu quero** cadastrar pacientes com nome, e-mail e CPF **para que** seja possível vinculá-los a consultas.
* **Prioridade:** Alta
* **Status Final:** Entregue
* **Critérios de Aceitação:**
  * **Cenário 1: Cadastro realizado com sucesso**
    * **Given** que informei dados válidos (Nome, CPF com 14 caracteres e e-mail contendo "@"),
    * **When** eu solicitar o cadastro do paciente,
    * **Then** o paciente deve ser cadastrado com sucesso.
  * **Cenário 2: Cadastro rejeitado por e-mail inválido**
    * **Given** que o e-mail informado não contém o caractere "@",
    * **When** eu solicitar o cadastro do paciente,
    * **Then** o sistema deve lançar uma exceção de argumento inválido.
  * **Cenário 3: Cadastro rejeitado por CPF inválido**
    * **Given** que o CPF informado não contém exatamente 14 caracteres (incluindo pontuação),
    * **When** eu solicitar o cadastro do paciente,
    * **Then** o sistema deve lançar uma exceção de argumento inválido.
* **Rastreabilidade:** US02 -> Issue #2 / PR #2 -> [PatientTest.java](file:///Users/schulzdimitrii/Documents/GitHub-Projects/debug-doctors/src/test/java/br/inatel/debug_doctors/domain/PatientTest.java) (`shouldCreatePatientWithCorrectData`, `shouldValidatePatientWithValidData`)

---

### **US03 - Criação de Agendamentos**
* **Como** Atendente do Hospital, **eu quero** agendar uma consulta vinculando um paciente e um médico em uma data futura **para que** o horário seja reservado.
* **Prioridade:** Alta
* **Status Final:** Entregue
* **Critérios de Aceitação:**
  * **Cenário 1: Agendamento criado com sucesso**
    * **Given** que informei um paciente válido, um médico válido, e uma data/hora no futuro,
    * **When** eu solicitar o agendamento da consulta,
    * **Then** o agendamento deve ser criado com status não confirmado.
  * **Cenário 2: Impedir agendamento no passado**
    * **Given** que a data e hora informadas para o agendamento estão no passado,
    * **When** eu solicitar o agendamento da consulta,
    * **Then** o sistema deve lançar uma exceção de argumento inválido.
  * **Cenário 3: Impedir agendamento sem paciente ou sem médico**
    * **Given** que não informei o paciente ou o médico,
    * **When** eu solicitar o agendamento da consulta,
    * **Then** o sistema deve lançar uma exceção de argumento inválido.
* **Rastreabilidade:** US03 -> Issue #3 / PR #3 -> [ScheduleTest.java](file:///Users/schulzdimitrii/Documents/GitHub-Projects/debug-doctors/src/test/java/br/inatel/debug_doctors/domain/ScheduleTest.java) (`testNewSchedule`, `cannotAllowScheduleInThePast`, `cannotCreateScheduleWithoutPatient`, `cannotCreateScheduleWithoutDoctor`)

---

### **US04 - Bloqueio de Agendamentos Conflitantes**
* **Como** Atendente do Hospital, **eu quero** que o sistema impeça agendamentos sobrepostos para o mesmo médico **para que** não ocorram conflitos na agenda do médico.
* **Prioridade:** Alta
* **Status Final:** Entregue
* **Critérios de Aceitação:**
  * **Cenário 1: Bloqueio de agendamento sobreposto**
    * **Given** que o médico já possui um agendamento na data/hora desejada,
    * **When** eu solicitar um novo agendamento para este mesmo médico nesta mesma data/hora,
    * **Then** o sistema deve lançar uma exceção de conflito de horário.
* **Rastreabilidade:** US04 -> Issue #4 / PR #4 -> [ScheduleTest.java](file:///Users/schulzdimitrii/Documents/GitHub-Projects/debug-doctors/src/test/java/br/inatel/debug_doctors/domain/ScheduleTest.java) (`cannotAllowOverlappingSchedules`, `shouldThrowWhenConflictDetectedWithMockedExistingSchedule`)

---

### **US05 - Cancelamento de Consultas**
* **Como** Paciente ou Atendente, **eu quero** poder cancelar um agendamento informando um motivo **para que** o horário seja liberado na agenda.
* **Prioridade:** Média
* **Status Final:** Entregue
* **Critérios de Aceitação:**
  * **Cenário 1: Cancelamento com sucesso**
    * **Given** que o agendamento selecionado está ativo,
    * **When** eu solicitar o cancelamento e informar um motivo válido,
    * **Then** o agendamento deve ser marcado como cancelado e o motivo deve ser armazenado.
  * **Cenário 2: Impedir cancelamento duplo**
    * **Given** que o agendamento selecionado já se encontra cancelado,
    * **When** eu tentar cancelar novamente,
    * **Then** o sistema deve lançar uma exceção de estado inválido.
* **Rastreabilidade:** US05 -> Issue #5 / PR #5 -> [ScheduleTest.java](file:///Users/schulzdimitrii/Documents/GitHub-Projects/debug-doctors/src/test/java/br/inatel/debug_doctors/domain/ScheduleTest.java) (`shouldCancelScheduleSuccessfully`, `cannotCancelAlreadyCanceledSchedule`)

---

## ⚙️ Metodologias de Desenvolvimento

Fizemos uma reunião inicial para planejar qual o papel de cada um e qual metodologia(s) de desenvolvimento utilizar, e por fim, optamos por
utilizar somente o **Kanban** dentro do **GitHub Projects**. 
- **Cadência:** Nós nos comunicamos principalmente pelo **WhatsApp** e nos reunimos em reuniões curtas e objetivas para alinhar as atividades do dia.
Optamos por não utilizar sprints pois o periodo de desenvolvimento foi bem curto e a cadência de comunicação era suficiente para mantermos o projeto organizado e alinhado.
- **Papeis do grupo:** 
    - `Dimitri:` PO / DevOps
    - `Wagner:` DevOps
    - `Esthefano:` QA
    - `Matheus:` QA
    - `Luiz:` Desenvolvedor
    - `Yan:` Desenvolvedor

- **Definition of Done (DoD):**
  * Todas as histórias de usuários foram concluídas;
  * Todos os testes foram aprovados;
  * Todo o código foi revisado pelos integrantes do grupo.

---

## 📋 Dinâmica de Desenvolvimento

* **Divisão de Tarefas e Decisões Técnicas:**
  A divisão de tarefas foi baseada nos papéis acordados. Os desenvolvedores (`Luiz` e `Yan`) codificaram os endpoints e a lógica de negócio. Os QAs (`Esthefano` e `Matheus`) criaram a suíte completa de testes unitários e de integração. O time de DevOps (`Dimitri` e `Wagner`) desenhou as entidades, a estrutura dockerizada local. Todo mundo configurou uma parte do pipeline contínuo no Jenkins. Decisões técnicas, como a escolha de banco de dados, versões de framework e modelagem de validação, foram tomadas coletivamente via reunião no Discord.

* **Fluxo de Trabalho (Branches, Commits e Code Review):**
  * **Branches:** Adotamos branches específicas baseadas em tarefas de desenvolvimento (ex: `feature/cadastro-medicos`, `refactor/update-readme-file` e `fix/cancellation-bug`), ramificadas a partir da branch principal.
  * **Commits:** Aplicamos o padrão de *Conventional Commits* (usando prefixos como `feat:`, `fix:`, `refactor:` e `docs:`) para facilitar a leitura e o rastreamento do histórico de alterações.
  * **Code Review:** O merge para a branch `main` dependia da abertura de um Pull Request (PR), passando obrigatoriamente pela validação automática da esteira de CI/CD (linting com Checkstyle e testes de unidade) e pela revisão com aprovação por parte de outro membro do grupo.

* **Conflitos, Bloqueios e Reorganização:**
  * **Resolução de Conflitos de Mesclagem:** Pelo fato de as classes [Schedule.java](file:///Users/schulzdimitrii/Documents/GitHub-Projects/debug-doctors/src/main/java/br/inatel/debug_doctors/domain/schedule/Schedule.java) e `ScheduleService` serem centrais para as validações de regras de negócio, ocorreram conflitos de merge durante o desenvolvimento paralelo. O time resolveu os conflitos fazendo sessões rápidas de pareamento para garantir que nenhuma lógica fosse sobrescrita indevidamente.

* **Lições Aprendidas:**
  * **Padrões:** Em projetos futuros, definiremos padrões de branches e commits de forma mais rigorosa.
  * **Comunicação:** A comunicação assíncrona e as reuniões curtas e objetivas foram quase suficientes para mantermos o projeto organizado e alinhado, tivemos alguns conflitos de merge, mas sentimos que poderia ter sido mais produtivo se tivessemos uma comunicação mais frequente e estruturada.

---

## 🤖 Uso de IA

Este projeto utilizou as LLMs `Gemini Flash 3.5` e `Claude Opus 4.6` como ferramentas de apoio e co-criação. 

O uso destas ferramentas foi aplicado nas seguintes frentes:
- Planejamento do projeto;
- Planejamento de testes;
- Geração de código;
- Documentação.

> Todos os artefatos gerados por IA foram revisados, testados e validados pelos integrantes do grupo.

---

## 🚀 Como Executar

### Pré-requisitos
- **Docker** e **Docker Compose** instalados

### Clonar e Instalar
```bash
git clone https://github.com/C14-INATEL/debug-doctors.git
cd debug-doctors
```

### Executar a Aplicação (via Docker)
Suba a infraestrutura completa contendo a API Spring Boot, Banco de Dados PostgreSQL e Jenkins com o seguinte comando:
```bash
docker-compose up -d --build
```

Os serviços estarão disponíveis em:
- **API (Spring Boot):** `http://localhost:8000`
- **Banco de Dados (PostgreSQL):** `localhost:5432`
- **Jenkins CI/CD:** `http://localhost:8080`

Para parar e remover todos os containers ativos:
```bash
docker-compose down
```

### Executar Testes
Você pode rodar a suíte de testes de forma nativa (caso possua Maven instalado localmente):
```bash
mvn test
```
Ou diretamente dentro de um container Maven temporário:
```bash
docker-compose run --rm api mvn test
```

---

## 📝 Endpoints

A API fornece os seguintes endpoints:

### Médicos
- `GET /api/medicos` - Listar todos os médicos
- `POST /api/medicos` - Criar novo médico
- `GET /api/medicos/{id}` - Obter dados de um médico
- `PUT /api/medicos/{id}` - Atualizar dados de um médico
- `DELETE /api/medicos/{id}` - Remover um médico

### Pacientes
- `GET /api/pacientes` - Listar todos os pacientes
- `POST /api/pacientes` - Criar novo paciente
- `GET /api/pacientes/{id}` - Obter dados de um paciente
- `PUT /api/pacientes/{id}` - Atualizar dados de um paciente
- `DELETE /api/pacientes/{id}` - Remover um paciente

### Agendamentos
- `GET /api/agendamentos` - Listar todos os agendamentos
- `POST /api/agendamentos` - Criar novo agendamento (com validações)
- `GET /api/agendamentos/{id}` - Obter dados de um agendamento
- `PUT /api/agendamentos/{id}/cancelar` - Cancelar um agendamento
- `PUT /api/agendamentos/{id}/confirmar` - Confirmar realização

---

**Desenvolvido por alunos do curso de Engenharia de Software e Computação do INATEL - 2026**
