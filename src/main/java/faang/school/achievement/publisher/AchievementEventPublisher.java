package faang.school.achievement.publisher;

import faang.school.achievement.event.AchievementEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AchievementEventPublisher implements EventPublisher<AchievementEvent> {

    @Value("${spring.data.redis.channel.achievement}")
    private String channel;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void publish(AchievementEvent event) {
        redisTemplate.convertAndSend(channel, event);
    }
}
