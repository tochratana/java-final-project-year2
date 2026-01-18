package dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkillDTO {
    private Long id;
    private String skillName;
    private String skillLevel;
    private Integer experienceYears;
    private String description;
    private Boolean availability;
    private String ownerName; // for displaying
}
