# Debug Doctors 🏥

API REST para gestão de agendamento de consultas médicas com validações robustas e testes automatizados.

## 📋 Objetivo

Desenvolver uma API REST para gestão de horários médicos, com foco em garantir a **integridade das agendas** através de testes unitários automatizados e entrega contínua via pipeline.

---

## 🏗️ Tecnologias

- **Java 25**
- **Spring Boot 4.0.3**
- **Maven**
- **JUnit** (testes unitários)
- **Lombok** (redução de boilerplate)

---

## 📦 Principais Entidades

### Médico
- Nome
- Especialidade
- CRM
- Horário de Início do Expediente
- Horário de Fim do Expediente

### Paciente
- Nome
- CPF
- E-mail

### Agendamento
- Médico (referência)
- Paciente (referência)
- Data/Hora de Início
- Data/Hora de Fim
- Status (Agendado, Cancelado, Realizado)

---

## ⚙️ Regras de Negócio

As seguintes validações são **obrigatórias** e devem ser testadas:

| Regra | Descrição | Status |
|-------|-----------|--------|
| ⏰ **Conflito de Horário** | Um médico não pode ter dois agendamentos sobrepostos | Em desenvolvimento |
| 📅 **Fora do Expediente** | Não permitir agendamentos antes do início ou após o fim da jornada do médico | Em desenvolvimento |
| ⏳ **Antecedência Mínima** | Consultas só podem ser agendadas com pelo menos 30 minutos de antecedência | Em desenvolvimento |
| ❌ **Regra de Cancelamento** | O cancelamento só é permitido se faltarem mais de 24h para a consulta | Em desenvolvimento |
| ⏱️ **Duração Padrão** | Validar se a consulta tem o tempo mínimo/máximo permitido (ex: 30 min) | Em desenvolvimento |

---

## 🚀 Como Executar

### Pré-requisitos
- Java 25+
- Maven 3.6+

### Clonar e Instalar

```bash
git clone https://github.com/seu-usuario/debug-doctors.git
cd debug-doctors
```

### Executar a Aplicação

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em: `http://localhost:3000`

### Executar Testes

```bash
./mvnw test
```

---

## 📝 Endpoints (Em desenvolvimento)

A API fornecerá os seguintes endpoints:

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

## 🧪 Estratégia de Testes

Cada regra de negócio possui testes unitários específicos:

```
src/test/java/br/inatel/debug_doctors/
├── validation/
│   ├── ConflitaHorarioTest.java
│   ├── ForaDoExpedienteTest.java
│   ├── AntecedenciaMinmaTest.java
│   ├── RegracancelamentoTest.java
│   └── DuracaoPadraoTest.java
└── controller/
    ├── MedicoControllerTest.java
    ├── PacienteControllerTest.java
    └── AgendamentoControllerTest.java
```

---

## 📊 Status do Projeto

- [ ] Setup inicial do projeto
- [ ] Criar entidades (Médico, Paciente, Agendamento)
- [ ] Implementar validações de negócio
- [ ] Criar testes unitários
- [ ] Implementar controllers REST
- [ ] Documentação API (Swagger/OpenAPI)
- [ ] Pipeline CI/CD
- [ ] Deploy em produção

---

## 📖 Estrutura do Projeto

```
debug-doctors/
├── src/
│   ├── main/
│   │   ├── java/br/inatel/debug_doctors/
│   │   │   ├── Application.java
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── repository/
│   │   │   ├── model/
│   │   │   └── validation/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/br/inatel/debug_doctors/
├── pom.xml
├── mvnw
└── README.md
```

---

## 📝 Notas de Desenvolvimento

- Usar **Lombok** para reduzir boilerplate de getters/setters
- Implementar validações como **serviços separados** ou **validators**
- Usar **try-catch** com `RuntimeException` ou `IllegalArgumentException` para violações de regra
- Manter **100% de cobertura de testes** para todas as regras de negócio
- Documentar cada regra com exemplos de entrada/saída

---

## 🤝 Contribuindo

1. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
2. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
3. Push para a branch (`git push origin feature/nova-feature`)
4. Abra um Pull Request

---

**Desenvolvido para garantir a qualidade de agendamentos médicos**
