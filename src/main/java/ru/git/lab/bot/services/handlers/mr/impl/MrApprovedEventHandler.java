package ru.git.lab.bot.services.handlers.mr.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.api.mr.User;
import ru.git.lab.bot.model.entities.ApproveEntity;
import ru.git.lab.bot.model.entities.MessageEntity;
import ru.git.lab.bot.services.ApproveService;
import ru.git.lab.bot.services.MessageService;
import ru.git.lab.bot.services.handlers.mr.MrEventHandler;
import ru.git.lab.bot.services.senders.MessageSender;

import java.util.List;

import static ru.git.lab.bot.api.mr.Action.APPROVED;
import static ru.git.lab.bot.utils.MergeRequestUtils.createMergeRequestMessageWithApprove;
import static ru.git.lab.bot.utils.ObjectAttributesUtils.getObjectAttributes;
import static ru.git.lab.bot.utils.UserUtils.getUser;

@Slf4j
@Service
@RequiredArgsConstructor
public class MrApprovedEventHandler implements MrEventHandler {

    private final MessageSender sender;
    private final MessageService messageService;
    private final ApproveService approveService;

    @Override
    public void handleEvent(MergeRequestEvent event) {
        ObjectAttributes objectAttributes = getObjectAttributes(event);
        Long mrId = objectAttributes.getId();
        Long authorId = objectAttributes.getAuthorId();
        User user = getUser(event);

        approveService.save(mrId, user);

        List<ApproveEntity> approvalsForMr = approveService.findAllByMrId(mrId);
        String text = createMergeRequestMessageWithApprove(event, approvalsForMr);

        MessageEntity messageEntity = messageService.getMessageByMrIdAndAuthorId(mrId, authorId);
        sender.updateMessage(text, messageEntity.getChatId(), messageEntity.getMessageId());

        log.debug("Merge request action " + getAction() + ". MR id: " + mrId);
    }

    @Override
    public Action getAction() {
        return APPROVED;
    }
}
