# Game of life

### How to run it?

An entire application can be ran with a single command in a terminal:

```
$ docker-compose up -d
```

If you want to stop it use following command:

```
$ docker-compose down
```

#### URLs
- Frontend app: http://localhost:4200/
- Swagger: http://localhost:8080/swagger-ui.html

#### Database

PostgreSQL database contains only single schema with three tables:
- game_state
- row
- field


After running the docker compose it can be accessible using this connectors:

- Host: _localhost_
- Database: _game-of-life_
- User: _gol-postgres_
- Password: _gol-postgres_
