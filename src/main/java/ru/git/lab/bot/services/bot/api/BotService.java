package ru.git.lab.bot.services.bot.api;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotService {

    public void handleReceivedUpdate(Update update);
}
