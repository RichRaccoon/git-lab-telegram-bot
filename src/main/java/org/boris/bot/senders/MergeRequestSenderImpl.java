package org.boris.bot.senders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.boris.bot.bot.TelegramBot;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@RequiredArgsConstructor
public class MergeRequestSenderImpl implements MergeRequestSender {

    private final TelegramBot telegramBot;

    @Override
    public void sendMessage(String text, Long chatId) {
        try {
            telegramBot.sendMessage(text, chatId);
        } catch (TelegramApiException e) {
            log.warn("Не удалось отправить сообщение. chat id " + chatId);
        }
    }
}
