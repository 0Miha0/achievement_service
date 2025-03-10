package faang.school.achievement.handler;

import faang.school.achievement.event.FollowerEvent;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import org.springframework.stereotype.Component;

@Component
public class BloggerAchievementHandler extends AbstractAchievementHandler<FollowerEvent>{

    private static final String ACHIEVEMENT_NAME = "BLOGGER";

    public BloggerAchievementHandler(AchievementService achievementService, AchievementCache achievementCache) {
        super(achievementService, achievementCache);
    }

    @Override
    public boolean supportsEvent(Class<?> event) {
        return FollowerEvent.class.equals(event);
    }

    @Override
    public void handleEvent(FollowerEvent event) {
        handleAchievement(event.getId(), ACHIEVEMENT_NAME);
    }
}
