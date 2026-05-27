# Fenix Backend

API REST para sistema de gestão de reservas de quadras esportivas.

**Stack:** Java 21 · Spring Boot 3.5 · Spring Security · JWT · Spring Data JPA · MySQL 8

> Repositório companion (frontend): https://github.com/guth-dev/fenix-frontend

---

## Pré-requisitos

- [Java JDK 21](https://adoptium.net/)
- [MySQL 8](https://dev.mysql.com/downloads/mysql/)

> Maven não precisa ser instalado — o projeto inclui o `mvnw` (Maven Wrapper).

---

## Como rodar

### 1. Configure o MySQL

Certifique-se de que o MySQL está rodando na porta `3306`.  
O banco `fenix_db` é criado automaticamente na primeira execução.

Por padrão o sistema usa `root` / `root`. Se suas credenciais forem diferentes, defina as variáveis de ambiente antes de rodar:

**Windows (PowerShell):**
```powershell
$env:DB_USERNAME = "seu_usuario"
$env:DB_PASSWORD = "sua_senha"
```

**Windows (CMD):**
```cmd
set DB_USERNAME=seu_usuario
set DB_PASSWORD=sua_senha
```

### 2. Clone o repositório

```bash
git clone https://github.com/guth-dev/fenix-backend.git
cd fenix-backend
```

### 3. Execute a aplicação

```powershell
.\mvnw.cmd spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

### 4. Rode os testes

```powershell
.\mvnw.cmd test
```

---

## Primeiro acesso

Na primeira inicialização, o sistema cria automaticamente um admin padrão:

| Campo | Valor |
|-------|-------|
| Email | `admin@fenix.com` |
| Senha | `admin123` |

> Não é necessário executar SQL manualmente. O admin é criado apenas se não existir nenhum no banco.

---

## Variáveis de ambiente

| Variável | Padrão | Descrição |
|----------|--------|-----------|
| `DB_USERNAME` | `root` | Usuário do MySQL |
| `DB_PASSWORD` | `root` | Senha do MySQL |
| `JWT_SECRET` | *(definido em application.properties)* | Chave secreta para assinar o JWT |
| `JWT_EXPIRATION_MS` | `86400000` (24h) | Tempo de expiração do token em milissegundos |

---

## Endpoints

### Autenticação

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/auth/login` | Login — retorna token JWT |

### Clientes

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/api/clients` | Listar todos os clientes |
| POST | `/api/clients` | Criar cliente |
| GET | `/api/clients/{id}` | Buscar cliente por ID |
| PUT | `/api/clients/{id}` | Atualizar cliente |
| PATCH | `/api/clients/{id}/status` | Alterar status (ACTIVE/INACTIVE) |

### Quadras

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/api/courts` | Listar todas as quadras |
| POST | `/api/courts` | Criar quadra |
| GET | `/api/courts/{id}` | Buscar quadra por ID |
| PUT | `/api/courts/{id}` | Atualizar quadra |
| PATCH | `/api/courts/{id}/status` | Alterar status (ACTIVE/INACTIVE/MAINTENANCE) |

### Reservas

| Método | Rota | Descrição |
|--------|------|-----------|
| GET | `/api/bookings` | Listar todas as reservas |
| POST | `/api/bookings` | Criar reserva |
| GET | `/api/bookings/{id}` | Buscar reserva por ID |
| GET | `/api/bookings/client/{clientId}` | Listar reservas de um cliente |
| GET | `/api/bookings/court/{courtId}` | Listar reservas de uma quadra |
| PATCH | `/api/bookings/{id}/cancel` | Cancelar reserva |
| PATCH | `/api/bookings/{id}/complete` | Concluir reserva |

> Todos os endpoints `/api/**` exigem o header:
> ```
> Authorization: Bearer <token>
> ```

---

## Regras de negócio

### Reservas
- `startTime` deve ser no futuro
- `endTime` deve ser posterior ao `startTime`
- O cliente deve ter status `ACTIVE`
- A quadra deve ter status `ACTIVE`
- Não pode haver outra reserva `CONFIRMED` no mesmo horário para a mesma quadra
- `totalPrice` é calculado automaticamente: `pricePerHour × duração em horas`
- Transições de status permitidas: `CONFIRMED → CANCELLED` e `CONFIRMED → COMPLETED`

---

## Formato das respostas de erro

**Erro de validação (400):**
```json
{ "errors": ["campo obrigatório", "e-mail inválido"] }
```

**Erro de regra de negócio (400) ou não encontrado (404):**
```json
{ "error": "mensagem descritiva em português" }
```

**Não autenticado (401):**
```json
{ "error": "Token inválido ou expirado" }
```
