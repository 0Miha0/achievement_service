package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.LikeEvent;
import faang.school.achievement.handler.EventHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LikePostAchievementListener extends AbstractAchievementListener<LikeEvent>{

    @Value("${spring.data.redis.channel.like}")
    private String channel;

    public LikePostAchievementListener(ObjectMapper objectMapper, List<EventHandler<LikeEvent>> eventHandlers) {
        super(objectMapper, eventHandlers);
    }

    @Override
    public ChannelTopic getChannelTopic() {
        return new ChannelTopic(channel);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        processEvent(message, LikeEvent.class);
    }
}
