package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AchievementCache {

    private final AchievementService achievementService;

    @PostConstruct
    public void fillCache() {
        achievementService.findAllDto().forEach(this::cacheAchievement);
    }

    @Cacheable(value = "achievements", key = "#title", cacheManager = "cacheManager")
    public AchievementDto get(String title) {
        return cacheAchievement(achievementService.findByTitleDto(title));
    }

    @CachePut(value = "achievements", key = "#achievement.title", cacheManager = "cacheManager")
    public AchievementDto cacheAchievement(AchievementDto achievement) {
        return achievement;
    }
}

