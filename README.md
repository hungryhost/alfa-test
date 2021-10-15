# Сервис для получения информации о курсе валют (сравнение текущего и вчерашнего)


### Задеплоен на https://projects.alfabank.yuryborodin.ru/api/v1/currencies/<ticker, напр. USD>/

#### *Для тестирования можно воспользоваться скриптом test_api.py, который проходит по всем валютам и проверяет возможность нахождения broke, rich и error статусов (чтобы не делать это руками)*
```python3 -m test_api```
## Стек
- Java Spring Boot 2.5.5
- Gradle 7.2
- Feign 11.6
- WireMock 2.31.0

## Описание работы
В данном проекте существует 1 endpoint для получения гифки в зависимости от
изменения курса валют к базовой валюте (рублю).

```https://projects.alfabank.yuryborodin.ru/api/v1/currencies/<ticker i.e. USD>/```

Данный endpoint возвращает стандартный ответ:
```json
{
  "result":"https://media0.giphy.com/media/l2YWoQjalZCEDHfUs/giphy.gif?cid=80009fdd192ztqbhubhy669pdh8n7vvfdopbhcxyssoamgsf&rid=giphy.gif&ct=g",
  "success":true,
  "type":"broke"
}
```
где result - ссылка на гифку; success - маркер успеха операции;
type - тип изменения (broke, rich) или error.

При попытке извлечения информации по несуществующему тикеру
выводится следующая информация:
```json
{
  "result":"fail",
  "success":false,
  "type":"error"
}
```

## Запуск

Запуск осуществляется через Docker-контейнер. 
На верхнем уровне находится Dockerfile с параметрами билда.

Процесс сборки и запуска стандартный для докеризированных приложений.


Сначала необходимо воспользоваться командой  ```docker build ```.
Далее запустить контейнер командой ```docker run```
(команды могут отличаться в зависимости от конфигурации прокси и переменных).
### Для запуска необходимы следующие переменные среды:
```
currency.base_currency=${BASE_CURR} // Базовый тикер валюты (RUB)
currency.api_key=${CURR_API_KEY} // API-ключ для openexchangerates
currency.base_url=${CURR_BASE_URL} // API base url для openexchangerates
images.base_url=${IMAGES_BASE_URL} // API base url для гифок
images.api_key=${IMAGES_API_KEY} // API-ключ для giphy
```



