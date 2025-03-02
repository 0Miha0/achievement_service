package faang.school.achievement.handler;

import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.service.AchievementCache;
import faang.school.achievement.service.AchievementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractAchievementHandler<T> implements EventHandler<T>{

    private final AchievementService achievementService;
    private final AchievementCache achievementCache;

    @Async("eventListenerTaskExecutor")
    public void handleAchievement(long userId, String achievementTitle) {
        Achievement achievement = achievementCache.get(achievementTitle);
        handleAchievementProgress(userId, achievement);
    }

    public void handleAchievementProgress(long userId, Achievement achievement) {
        if(achievementService.hasAchievement(userId, achievement.getId())) {
            log.info("User with id: {} already has achievement with id: {}", userId, achievement.getId());
        }

        achievementService.createProgressIfNecessary(userId, achievement.getId());
        AchievementProgress progress = achievementService.getProgress(userId, achievement.getId());
        progress.increment();

        if(progress.getCurrentPoints() >= achievement.getPoints()) {
            achievementService.giveAchievement(userId, achievement.getId());
            log.info("User with id: {} received achievement with id: {}", userId, achievement.getId());
        }

        achievementService.saveProgress(progress);
    }
}
