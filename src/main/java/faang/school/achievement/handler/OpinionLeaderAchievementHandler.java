package faang.school.achievement.handler;

import faang.school.achievement.event.PublishPostEvent;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import org.springframework.stereotype.Component;

@Component
public class OpinionLeaderAchievementHandler extends AbstractAchievementHandler<PublishPostEvent>{

    private static final String ACHIEVEMENT_NAME = "OPINION LEADER";

    public OpinionLeaderAchievementHandler(
            AchievementService achievementService,
            AchievementCache achievementCache) {
        super(achievementService, achievementCache);
    }

    @Override
    public boolean supportsEvent(Class<?> event) {
        return PublishPostEvent.class.equals(event);
    }

    @Override
    public void handleEvent(PublishPostEvent event) {
        handleAchievement(event.getUserId(), ACHIEVEMENT_NAME);
    }
}
