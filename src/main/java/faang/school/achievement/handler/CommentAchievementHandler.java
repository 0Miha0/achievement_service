package faang.school.achievement.handler;

import faang.school.achievement.event.CommentEvent;
import faang.school.achievement.event.FollowerEvent;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import org.springframework.stereotype.Component;

@Component
public class CommentAchievementHandler extends AbstractAchievementHandler<CommentEvent>{

    private static final String ACHIEVEMENT_NAME = "EVIL COMMENTATOR";

    public CommentAchievementHandler(
            AchievementService achievementService,
            AchievementCache achievementCache) {
        super(achievementService, achievementCache);
    }

    @Override
    public boolean supportsEvent(Class<?> event) {
        return CommentEvent.class.equals(event);
    }

    @Override
    public void handleEvent(CommentEvent event) {
        handleAchievement(event.getActorId(), ACHIEVEMENT_NAME);
    }
}
