package faang.school.achievement.handler;

import faang.school.achievement.event.ProjectCreateEvent;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import org.springframework.stereotype.Component;

@Component
public class BusinessmanAchievementHandler extends AbstractAchievementHandler<ProjectCreateEvent>{

    private static final String ACHIEVEMENT_NAME = "BUSINESSMAN";

    public BusinessmanAchievementHandler(AchievementService achievementService, AchievementCache achievementCache) {
        super(achievementService, achievementCache);
    }

    @Override
    public boolean supportsEvent(Class<?> event) {
        return ProjectCreateEvent.class.equals(event);
    }

    @Override
    public void handleEvent(ProjectCreateEvent event) {
        handleAchievement(event.getUserId(), ACHIEVEMENT_NAME);
    }
}
