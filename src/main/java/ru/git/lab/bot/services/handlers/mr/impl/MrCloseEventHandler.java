package ru.git.lab.bot.services.handlers.mr.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.dto.MessageToDelete;
import ru.git.lab.bot.services.EventOfCloseMrService;
import ru.git.lab.bot.services.MessageService;
import ru.git.lab.bot.services.handlers.mr.MrEventHandler;

import static ru.git.lab.bot.api.mr.Action.CLOSE;
import static ru.git.lab.bot.utils.ObjectAttributesUtils.getObjectAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class MrCloseEventHandler implements MrEventHandler {

    private final EventOfCloseMrService eventOfCloseMrService;

    @Override
    public void handleEvent(MergeRequestEvent event) {
        ObjectAttributes objectAttributes = getObjectAttributes(event);
        Long mrId = objectAttributes.getId();
        Long authorId = objectAttributes.getAuthorId();

        log.debug("Merge event action " + getAction() + ". MR id: " + mrId);

        eventOfCloseMrService.deleteMessage(mrId, authorId);
    }

    @Override
    public Action getAction() {
        return CLOSE;
    }
}
