package org.boris.bot.controller;

import lombok.extern.slf4j.Slf4j;
import org.boris.bot.api.MergeRequest;
import org.boris.bot.services.MergeRequestService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/merges/requests")
public class MergeRequestController {

    private final MergeRequestService mergeRequestService;

    public MergeRequestController(MergeRequestService mergeRequestService) {
        this.mergeRequestService = mergeRequestService;
    }

    @PostMapping
    public void mergeRequestEvent(@RequestBody MergeRequest request) {
        log.debug(request.toString());
        if ("open".equalsIgnoreCase(request.getObjectAttributes().getAction())) {
            mergeRequestService.sendMergeRequest(request);
        } else if("update".equalsIgnoreCase(request.getObjectAttributes().getAction())) {
            log.debug("Get MR with action status update");
        }
    }
}
