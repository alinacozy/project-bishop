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

3. В папке bishop-prototype запустите kafka через docker compose:

```bash
cd bishop-prototype
docker compose up
```

4. Запустите bishop-prototype


### На данный момент в проекте есть:
1. Модуль приема и исполнения команд.
2. Модуль аудита с помощью AOP
3. Модуль мониторинга занятости андроида (показ метрик)
4. Модуль обработки ошибок

## Прием и исполнение команд

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

## Аудит

Класс CommandController в bishop-prototype помечен аннотацией @WeylandWatchingYou(kafkaTopic = "audit-topic"), которая позволяет отправлять в топик audit-topic в Kafka информацию о вызове и результате данного метода.

Если убрать параметр kafkaTopic (оставить только @WeylandWatchingYou), то по умолчанию информация аудита выводится в консоль.

## Метрики

Чтобы посмотреть все доступные метрики, необходимо сделать GET запрос по URL: 
`localhost:8080/actuator/metrics`

Метрика количества выполненных задач доступна по URL:
`localhost:8080/actuator/metrics/bishop.completed.tasks`

Количество задач по автору (впишите имя вашего автора вместо "Лейтенант Эллен Рипли"):
`localhost:8080/actuator/metrics/bishop.completed.tasks?tag=author:Лейтенант Эллен Рипли`

Метрика количества задач в очереди доступна по URL:
`localhost:8080/actuator/metrics/bishop.queue.size`

## Обработка ошибок

В starter'е реализован CustomExceptionHandler, который обрабатывает следующие исключения и выдает результат в едином формате:
- ошибки валидации команды (значения полей, не соответствующие необходимому формату, например, время, не соответствующее ISO-8601, либо слишком длинное название или автор команды) - результатом обработки является ошибка 400 Bad Request с сообщением, какие из полей не прошли проверку
- ошибка переполнения очереди - 429 Too Many Requests 
- остальные ошибки - 500 Internal Server Error.