package model;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Skill {
    private Long id;
    private Long userId;
    private String skillName;
    private String skillLevel; // BEGINNER, INTERMEDIATE, ADVANCED, EXPERT
    private Integer experienceYears;
    private String description;
    private Boolean availability; // true = available for exchange
    private String status; // ACTIVE, INACTIVE
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}