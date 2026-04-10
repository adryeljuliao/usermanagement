# User Management Mock API

API Mock para gerenciamento de membros/usuários com dados armazenados em memória.

## 🚀 Tecnologias

- Java 21
- Spring Boot 4.0.5
- Spring Web
- Spring Security
- SpringDoc OpenAPI (Swagger)
- Lombok
- Gradle

## 📋 Características

- ✅ API REST completa (CRUD)
- ✅ Dados armazenados em memória (ConcurrentHashMap)
- ✅ Dados iniciais (seed data) carregados automaticamente
- ✅ Documentação Swagger/OpenAPI
- ✅ Validação de dados
- ✅ Exception handling global
- ✅ Thread-safe
- ✅ Sem necessidade de banco de dados

## 🏃 Como Executar

### Usando Gradle Wrapper (Windows)

```powershell
.\gradlew.bat bootRun
```

### Usando Gradle Wrapper (Linux/Mac)

```bash
./gradlew bootRun
```

A aplicação iniciará na porta `8080`.

## 📚 Documentação da API

Após iniciar a aplicação, acesse:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## 🔌 Endpoints

### Base URL
```
http://localhost:8080/api/members
```

### Endpoints Disponíveis

#### 1. Criar Membro
```http
POST /api/members
Content-Type: application/json

{
  "name": "João Silva",
  "employmentStatus": "MANAGER"
}
```

#### 2. Listar Todos os Membros
```http
GET /api/members
```

#### 3. Buscar Membro por ID
```http
GET /api/members/{id}
```

#### 4. Atualizar Membro
```http
PUT /api/members/{id}
Content-Type: application/json

{
  "name": "João Silva Atualizado",
  "employmentStatus": "FUNCTIONARY"
}
```

#### 5. Deletar Membro
```http
DELETE /api/members/{id}
```

## 📊 Roles Disponíveis

- `MANAGER` - Gerente
- `FUNCTIONARY` - Funcionário
- `TRAINEE` - Estagiário

## 💾 Dados Iniciais

A aplicação já vem com 5 membros pré-cadastrados em memória:

1. João Silva - MANAGER
2. Maria Santos - FUNCTIONARY
3. Pedro Oliveira - TRAINEE
4. Ana Costa - FUNCTIONARY
5. Carlos Pereira - MANAGER

## 🧪 Testando com cURL

### Listar todos os membros
```powershell
curl http://localhost:8080/api/members
```

### Criar um novo membro
```powershell
curl -X POST http://localhost:8080/api/members `
  -H "Content-Type: application/json" `
  -d '{\"name\":\"Teste Usuario\",\"employmentStatus\":\"TRAINEE\"}'
```

### Buscar membro por ID (substitua {id} por um UUID válido)
```powershell
curl http://localhost:8080/api/members/{id}
```

### Atualizar membro
```powershell
curl -X PUT http://localhost:8080/api/members/{id} `
  -H "Content-Type: application/json" `
  -d '{\"name\":\"Nome Atualizado\",\"employmentStatus\":\"MANAGER\"}'
```

### Deletar membro
```powershell
curl -X DELETE http://localhost:8080/api/members/{id}
```

## ⚠️ Importante

- Os dados são armazenados apenas em memória
- Ao reiniciar a aplicação, todos os dados criados/modificados serão perdidos
- A aplicação sempre iniciará com os 5 membros padrão
- Não há autenticação configurada (acesso público)

## 🔧 Estrutura do Projeto

```
src/main/java/com/project/management/
├── config/              # Configurações (Security, OpenAPI)
├── controller/          # Controllers REST
├── domain/              # Entidades de domínio
│   └── enums/          # Enumerações
├── dto/                 # DTOs (Request/Response)
├── exception/           # Exception handlers
└── service/             # Camada de serviço
    └── impl/           # Implementações
```

## 📝 Validações

### MemberRequest
- `name`: Obrigatório (não pode ser vazio)
- `employmentStatus`: Obrigatório (deve ser um dos valores do enum Role)

## 🛠️ Build

Para gerar o JAR da aplicação:

```powershell
.\gradlew.bat build
```

O arquivo JAR será gerado em: `build/libs/`

Para executar o JAR:

```powershell
java -jar build/libs/usermanagement-0.0.1-SNAPSHOT.jar
```

