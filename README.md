# java-explore-with-me
# Афиша

Свободное время — ценный ресурс. Ежедневно мы планируем, как его потратить — куда и с кем сходить.  
Сложнее всего в таком планировании поиск информации и переговоры. Нужно учесть много деталей: 
какие намечаются мероприятия, свободны ли в этот момент друзья, как всех пригласить и где собраться.

## О приложении

Приложение это — афиша.
В этой афише можно предложить какое-либо событие и собрать компанию для участия в нём.

[![Кликай сюда](picture/risovach.ru.jpg)](https://github.com/ionow-arteom/java-explore-with-me)

[Либо Вы можете воспользоваться этой ссылкой](https://github.com/ionow-arteom/java-explore-with-me)

Основной сервис разделён на три части:
- публичная (будет доступна без регистрации любому пользователю сети);
- закрытая (будет доступна только авторизованным пользователям);
- административная (будет доступна для администраторов сервиса);

Приложение содержит в себе два сервиса:

основной сервис будет содержать всё необходимое для работы продукта;
сервис статистики будет хранить количество просмотров и позволит делать различные выборки для анализа работы приложения;

В разработке приложения мы старались работать на принципах feature-based organization, то есть каждая фича имеет собственную
папку с соответствующими компонентами (контроллерами, сервисами, DTO и так далее), а также есть общая папка для утилит и модулей,
которые используются в нескольких фичах. Реализация данного подхода помогает легко находить и управлять компонентами каждой фичи и
делает код более организованным и модульным.
## Стэк

Проект разработан с использованием следующих технологий:

Java 11 с использованием фреймворка Spring Boot.
REST API.
Hibernate - фреймворк работающий с реляционными базами данных.
Maven - фреймворк для автоматизации сборки проектов и управления зависимостями.
PostgresSQL - реляционная база данных.
Lombok - библиотека, которая сокращает объем стандартного кода в классах.
Docker - платформа, которая предназначена для разработки, развёртывания и запуска приложений в контейнерах.

                                     /\_/\  
                                    ( o.o )
                                     > ^ <




