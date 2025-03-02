package faang.school.achievement.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.achievement.event.ProjectCreateEvent;
import faang.school.achievement.handler.EventHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectAchievementListener extends AbstractAchievementListener<ProjectCreateEvent>{

    @Value("${spring.data.redis.channel.project_create}")
    private String channel;

    public ProjectAchievementListener(ObjectMapper objectMapper, List<EventHandler<ProjectCreateEvent>> eventHandlers) {
        super(objectMapper, eventHandlers);
    }

    @Override
    public ChannelTopic getChannelTopic() {
        return new ChannelTopic(channel);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        processEvent(message, ProjectCreateEvent.class);
    }
}
