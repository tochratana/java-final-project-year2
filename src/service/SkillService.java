package service;

import dto.SkillDTO;
import model.Skill;
import java.util.List;

public interface SkillService {
    Skill addSkill(Long userId, SkillDTO dto);
    Skill updateSkill(Long skillId, Long userId, SkillDTO dto);
    List<SkillDTO> getUserSkills(Long userId);
    List<SkillDTO> getAvailableSkills(Long currentUserId);
    void deleteSkill(Long skillId, Long userId);
}
