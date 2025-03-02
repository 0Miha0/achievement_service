package faang.school.achievement.controller;

import faang.school.achievement.dto.AchievementDto;
import faang.school.achievement.service.AchievementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Api for achievement")
@RestController
@RequestMapping("/achievements")
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;

    @Operation(summary = "Get all achievement")
    @GetMapping("/all")
    public ResponseEntity<List<AchievementDto>> getAllAchievement(){
        return ResponseEntity.ok(achievementService.getAllAchievement());
    }

    @Operation(summary = "Get all achievement by userId")
    @GetMapping("/{userId}")
    public ResponseEntity<List<AchievementDto>> getAllAchievementByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(achievementService.getAllAchievementByUserId(userId));
    }

    @Operation(summary = "Get achievement by id")
    @GetMapping("/{achievementId}")
    public ResponseEntity<AchievementDto> getAchievement(@PathVariable Long achievementId) {
        return ResponseEntity.ok(achievementService.getAchievement(achievementId));
    }
}
