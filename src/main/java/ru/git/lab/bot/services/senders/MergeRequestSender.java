package ru.git.lab.bot.services.senders;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface MergeRequestSender {
    Message sendMessage(String text, Long chatId);
    boolean deleteMessage(Long chatId, Integer messageId);
    Message sendSticker(Long chatId, Integer messageId);
}
