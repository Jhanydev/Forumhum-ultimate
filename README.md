# Forumhum-ultimate

FórumHub 🚀
Um mini fórum estilo Alura: API REST com Spring Boot 3 + MySQL + JWT + Frontend estático bonitão.
Com gratidão à Alura e à Oracle pelo incentivo e pela comunidade incrível 💙🧡.

🧰 Stack
☕ Java 17
🌱 Spring Boot 3 (Web, Security, Validation, Data JPA, DevTools)
🐬 MySQL 8 + Flyway
🔐 JWT
🐳 Docker + docker-compose + Makefile
🧪 Tests (JUnit + MockMvc + H2)
📘 Swagger/OpenAPI (/swagger-ui.html)
🩺 Actuator (/actuator)
✅ Funcionalidades
CRUD de tópicos
Registro/Login reais com JWT
Papéis: USER, MODERATOR, ADMIN
Filtros de busca: q, course, authorName, status, from, to
Tags, Respostas, Likes, Uploads (metadados + arquivo em ./uploads)
Rate limiting simples (100 req / 60s por IP)
Seeds (admin/mod/user senha: 123456)
▶️ Rodando rápido
make run        # sobe MySQL + app (http://localhost:8080)
# ou:
mvn spring-boot:run
Configure seu banco no application.properties ou use o docker-compose.yml.

🔑 Auth
POST /api/auth/register → retorna { token }
POST /api/auth/login → retorna { token } Inclua Authorization: Bearer <token> nos endpoints protegidos.
🔎 Tópicos
GET /api/topics (público) — filtros opcionais
POST /api/topics (USER/MOD/ADMIN) — cria com tags
PUT /api/topics/{id} (USER/MOD/ADMIN)
PATCH /api/topics/{id}/status?status=OPEN|CLOSED (MOD/ADMIN)
DELETE /api/topics/{id} (ADMIN)
💬 Respostas
GET /api/topics/{id}/replies (público)
POST /api/topics/{id}/replies (USER/MOD/ADMIN)
👍 Likes
POST /api/topics/{id}/like (curtir)
DELETE /api/topics/{id}/like (descurtir)
📎 Uploads
POST /api/topics/{id}/attachments (multipart file)
GET /api/topics/{id}/attachments
GET /files/{filename} (serve o arquivo)
🧑‍🎨 UI
Arquivos em src/main/resources/static/ — páginas simples no estilo Alura, com JS vanilla consumindo a API.

Feito com carinho. Se este projeto te ajudou, conta pra gente e 🌟 o repositório.
Obrigada, Alura e Oracle — vocês impulsionam sonhos! 🙌
