# git-lab-telegram-bot

java -Dspring.profiles.active=prod -jar git-lab-telegram-bot-1.0-SNAPSHOT.jar

Бизнес процессы для взаимодействия с GitLab.

1. Отправка уведомления об открытии нового МРа
    1. При создании МРа
    2. При восстановлении старого МРа
2. Редактирование сообщения в чате при внесении изменений в МР
3. Удаление сообщения после закрытия МРа
    1. При закрытии МРа
    2. При сливании мра
4. Отправка реакции к сообщению если был поставлен аппрув на МРе
5. Удалении реакции если апрув был убран
6. Запрет на отправку сообщения если МР имеет статус draft

Бизнес процессы управления ботом через телеграмм.

1. Добавление чатов для которых пользователь хочет осуществлять уведомление.
2. Удаление чатов
3. Реализация связи учетных записей в телеграмме и GitLab.
