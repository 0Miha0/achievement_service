package faang.school.achievement.handler;

import faang.school.achievement.event.AchievementEvent;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import org.springframework.stereotype.Component;

@Component
public class RecursionAchievementHandler extends AbstractAchievementHandler<AchievementEvent>  {

    private static final String ACHIEVEMENT_TITLE = "RECURSION RECURSION";

    public RecursionAchievementHandler(AchievementService achievementService, AchievementCache achievementCache) {
        super(achievementService, achievementCache);
    }

    @Override
    public boolean supportsEvent(Class<?> event) {
        return AchievementEvent.class.equals(event);
    }

    @Override
    public void handleEvent(AchievementEvent event) {
        handleAchievement(event.getUserId(), ACHIEVEMENT_TITLE);
    }
}
