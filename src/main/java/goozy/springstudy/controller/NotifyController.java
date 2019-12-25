package goozy.springstudy.controller;

import goozy.springstudy.service.NotifyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class NotifyController {

    private NotifyService notifyService;

    public NotifyController(NotifyService notifyService) {
        this.notifyService = notifyService;
    }

    @GetMapping("/call/notify/{userId}")
    public String callNotify(@PathVariable("userId") int userId) {
        notifyService.sendMessage(userId);
        return userId + "이 호출함";
    }

    @GetMapping("/receive/notify/{userId}")
    public SseEmitter receiveNotify(@PathVariable("userId") int userId) {
        SseEmitter sseEmitter = new SseEmitter(1000L * 60L * 15L);

        sseEmitter.onCompletion(() -> {
            notifyService.removeEmitter(userId);
        });

        sseEmitter.onTimeout(() -> {
            sseEmitter.complete();
            notifyService.removeEmitter(userId);
        });

        notifyService.setUserEmitter(userId, sseEmitter);

        return sseEmitter;
    }
}
