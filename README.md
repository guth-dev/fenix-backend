# Fenix Backend

API REST para sistema de gestão de reservas de quadras esportivas.

**Stack:** Java 21 · Spring Boot 3.5 · Spring Security · JWT · Spring Data JPA · MySQL 8

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

---

## Primeiro acesso

O sistema não possui endpoint de registro de admin. Para criar o primeiro admin, execute no MySQL:

```sql
INSERT INTO fenix_db.admin (name, email, password_hash, accepted_terms, accepted_terms_at, created_at, updated_at)
VALUES (
  'Admin',
  'admin@fenix.com',
  '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
  true,
  NOW(),
  NOW(),
  NOW()
);
```

> A senha acima corresponde a: `admin123`

---

## Endpoints principais

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/auth/login` | Login — retorna token JWT |
| GET/POST | `/api/clients` | Listar / Criar clientes |
| GET/PUT | `/api/clients/{id}` | Buscar / Atualizar cliente |
| PATCH | `/api/clients/{id}/status` | Alterar status do cliente |
| GET/POST | `/api/courts` | Listar / Criar quadras |
| GET/PUT | `/api/courts/{id}` | Buscar / Atualizar quadra |
| PATCH | `/api/courts/{id}/status` | Alterar status da quadra |
| GET/POST | `/api/bookings` | Listar / Criar reservas |
| PATCH | `/api/bookings/{id}/cancel` | Cancelar reserva |
| PATCH | `/api/bookings/{id}/complete` | Concluir reserva |

Todos os endpoints `/api/**` exigem o header:
```
Authorization: Bearer <token>
```
