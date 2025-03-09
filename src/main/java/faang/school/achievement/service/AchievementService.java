package faang.school.achievement.service;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.event.AchievementEvent;
import faang.school.achievement.mapper.AchievementMapper;
import faang.school.achievement.model.Achievement;
import faang.school.achievement.model.AchievementProgress;
import faang.school.achievement.model.UserAchievement;
import faang.school.achievement.publisher.AchievementEventPublisher;
import faang.school.achievement.repository.AchievementProgressRepository;
import faang.school.achievement.repository.AchievementRepository;
import faang.school.achievement.repository.UserAchievementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final AchievementProgressRepository achievementProgressRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final AchievementMapper achievementMapper;
    private final AchievementEventPublisher achievementEventPublisher;

    @Transactional
    public boolean hasAchievement(long userId, long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Transactional
    public void createProgressIfNecessary(long userId, long achievementId) {
        achievementProgressRepository.createProgressIfNecessary(userId, achievementId);
    }

    public AchievementProgress getProgress(long userId, long achievementId) {
        return achievementProgressRepository.findByUserIdAndAchievementId(userId, achievementId)
                .orElseThrow(() -> new EntityNotFoundException("Achievement progress with id: " + " not found"));
    }

    @Transactional
    public void giveAchievement(long userId, long achievementId){
        Achievement achievement = findAchievementById(achievementId);
        UserAchievement userAchievement = UserAchievement.builder()
                .userId(userId)
                .achievement(achievement)
                .createdAt(LocalDateTime.now())
                .build();
        saveUserAchievement(userAchievement);
        achievementEventPublisher.publish(
                AchievementEvent.builder()
                        .title(achievement.getTitle())
                        .userId(userId)
                        .description(achievement.getDescription())
                        .build()
        );
    }

    public List<AchievementDto> getAllAchievement() {
        return achievementMapper.toDtoList(findAll());
    }

    public List<AchievementDto> getAllAchievementByUserId(Long userId) {
        return achievementMapper.toDtoList(
                userAchievementRepository.findByUserId(userId).stream()
                        .map(UserAchievement::getAchievement)
                        .toList()
        );
    }

    public AchievementDto getAchievement(Long achievementId) {
        return achievementMapper.toDto(findAchievementById(achievementId));
    }

    public Achievement findAchievementById(long achievementId){
        return achievementRepository.findById(achievementId)
                .orElseThrow(() -> new EntityNotFoundException("Achievement with id: " + achievementId + " not found"));
    }

    public Achievement findByTitle(String title) {
        return achievementRepository.findByTitle(title)
                .orElseThrow(() -> new EntityNotFoundException("Achievement with title" + title + " not found"));
    }
    public AchievementDto findByTitleDto(String title) {
        return achievementMapper.toDto(achievementRepository.findByTitle(title)
                        .orElseThrow(() -> new EntityNotFoundException("Achievement with title" + title + " not found")));
    }

    public void saveUserAchievement(UserAchievement userAchievement) {
        userAchievementRepository.save(userAchievement);
    }

    public void saveProgress(AchievementProgress achievementProgress) {
        achievementProgressRepository.save(achievementProgress);
    }

    public List<Achievement> findAll() {
        return achievementRepository.findAll();
    }

    public List<AchievementDto> findAllDto() {
        return achievementMapper.toDtoList(findAll());
    }
}
