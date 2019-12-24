package goozy.springstudy.service;

import goozy.springstudy.repository.NotifyRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotifyService {

    private Map<Integer, SseEmitter> connectedUser = new HashMap<>();
    private NotifyRepository notifyRepository;

    public NotifyService(NotifyRepository notifyRepository) {
        this.notifyRepository = notifyRepository;
    }

    public void setUserEmitter(int userId, SseEmitter sseEmitter) {
        connectedUser.put(userId, sseEmitter);
    }

    public void sendMessage(int userId) {
        List<Integer> calledUsers = notifyRepository.findByUserId(userId);

        for (Integer calledUser : calledUsers) {
            if (!connectedUser.containsKey(calledUser)) {
                continue;
            }
            SseEmitter sseEmitter = connectedUser.get(calledUser);
            try {
                sseEmitter.send(userId + "가 당신을 호출 했습니다.");
                sseEmitter.complete();
            } catch (Exception ex) {
                sseEmitter.completeWithError(ex);
            }
        }
    }

    public void removeEmitter(int userId) {
        connectedUser.remove(userId);
    }
}
