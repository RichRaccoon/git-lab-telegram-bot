package ru.git.lab.bot.services.impl;

import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.api.mr.Project;
import ru.git.lab.bot.api.mr.Reviewer;
import ru.git.lab.bot.model.entities.ApproveEntity;
import ru.git.lab.bot.model.entities.UserEntity;
import ru.git.lab.bot.services.MrTextMessageService;
import ru.git.lab.bot.services.UserService;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MrTextMessageServiceImpl implements MrTextMessageService {

    private static final String likeEmoji = EmojiParser.parseToUnicode(":thumbsup:");
    private static final String DEFAULT_PREFIX = "Тут могло быть ";
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private final UserService userService;

    @Override
    public String createMergeRequestTextMessage(MergeRequestEvent event) {
        String projectName = Optional.ofNullable(event.getProject())
                .map(Project::getName)
                .orElse(DEFAULT_PREFIX + "название проекта.");
        Optional<ObjectAttributes> objectAttributes = Optional.ofNullable(event.getObjectAttributes());
        String title = objectAttributes.map(ObjectAttributes::getTitle)
                .orElse(DEFAULT_PREFIX + "название МРа.");
        String description = objectAttributes.map(ObjectAttributes::getDescription)
                .orElse(DEFAULT_PREFIX + "описание МРа");
        //TODO Fix date deserializer
//        OffsetDateTime createdAt = objectAttributes
//                .map(ObjectAttributes::getCreatedAt)
//                .orElse(OffsetDateTime.now());
        String createdAt = OffsetDateTime.now()
                .format(DATE_TIME_FORMATTER);
        String mrUrl = objectAttributes.map(ObjectAttributes::getUrl)
                .orElse("");
        String sourceBranch = event.getObjectAttributes()
                .getSourceBranch();
        String targetBranch = event.getObjectAttributes()
                .getTargetBranch();

        //TODO fix to user from db by authorId from objectAttributes
        Long authorId = objectAttributes.map(ObjectAttributes::getAuthorId).orElseThrow(() -> new RuntimeException("Author in not present"));

        UserEntity userEntity = userService.getByAuthorId(authorId);
        String name = userEntity.getName();

        List<Reviewer> reviewers = Optional.ofNullable(event.getReviewers())
                .orElse(Collections.emptyList());
        Reviewer reviewer = reviewers.stream()
                .findFirst()
                .orElse(null);
        String reviewerName = Optional.ofNullable(reviewer)
                .map(Reviewer::getName)
                .orElse("");

        return "<b>Project:</b> " + projectName + "\n\n" +
                "<b>Title:</b> " + title + "\n" +
                "<b>Description:</b> " + description + "\n\n" +
                "<b>Author:</b> " + name + "\n" +
                "<b>Reviewer:</b> " + reviewerName + "\n\n" +
                "<b>Source:</b> " + sourceBranch + " ==> " +
                "<b>Target:</b> " + targetBranch + "\n\n" +
                "<b>Create date:</b> " + createdAt + "\n\n" +
                "<a href='" + mrUrl + "'><b><u>MR Hyperlink</u></b></a>";
    }

    @Override
    public String createMergeRequestTextMessageWithApprove(MergeRequestEvent event, List<ApproveEntity> approves) {
        StringBuilder stringBuilder = new StringBuilder(createMergeRequestTextMessage(event));
        approves.forEach(a -> {
            String approveMessage = "\n\n" + likeEmoji + " " + "<b>" + a.getAuthorName() + " approved </b>";
            stringBuilder.append(approveMessage);
        });
        return stringBuilder.toString();
    }
}
