package goozy.springstudy.repository;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class NotifyRepository {
    private static Map<Integer, List<Integer>> notifies = new HashMap<>();

    static {
        notifies.put(1, Arrays.asList(2, 3, 5, 7));
        notifies.put(2, Arrays.asList(1, 3, 4, 5));
        notifies.put(3, Arrays.asList(2, 4, 6, 7));
        notifies.put(4, Arrays.asList(1, 2, 5, 6));
        notifies.put(5, Arrays.asList(2, 3, 6, 7));
        notifies.put(6, Arrays.asList(3, 4, 5, 6));
        notifies.put(7, Arrays.asList(1, 3, 5, 6));
    }

    public List<Integer> findByUserId(int userId) {
        return Optional.of(notifies.get(userId)).orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
    }
}
