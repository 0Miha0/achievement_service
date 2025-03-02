package faang.school.achievement.handler;

import faang.school.achievement.event.LikeEvent;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import org.springframework.stereotype.Component;

@Component
public class ILoveEveryoneAchievementHandler extends AbstractAchievementHandler<LikeEvent>{

    private static final String ACHIEVEMENT_NAME = "I LOVE EVERYONE";

    public ILoveEveryoneAchievementHandler(AchievementService achievementService, AchievementCache achievementCache) {
        super(achievementService, achievementCache);
    }

    @Override
    public boolean supportsEvent(Class<?> event) {
        return LikeEvent.class.equals(event);
    }

    @Override
    public void handleEvent(LikeEvent event) {
        handleAchievement(event.getReceiverId(), ACHIEVEMENT_NAME);
    }
}
