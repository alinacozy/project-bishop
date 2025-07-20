# Разработка Synthetic Human Core Starter ("Project Bishop")

Проект содержит стартер synthetic-human-core-starter, который используется для
реализации логики работы всех будущих моделей искусственных людей, 
а также сервис-эмулятор синтетика bishop-prototype.

## Запуск проекта

1. Клонируйте репозиторий
```bash
git clone https://github.com/alinacozy/project-bishop.git
```

2. Перед использованием стартера в bishop-prototype, его нужно собрать и установить локально:

```bash
cd synthetic-core-starter
mvn clean install 
```

3. Запустите bishop-prototype


### На данный момент в проекте есть модуль приема и исполнения команд.

Отправить команду можно по URL: ```localhost:8080/api/commands```

Пример тела POST-запроса: 
```
{
    "description": "Проверить состояние энергоблока космического корабля",
    "priority": "CRITICAL",
    "author": "Лейтенант Эллен Рипли",
    "time": "2025-01-01T12:00:00Z"
}
```