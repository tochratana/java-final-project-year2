package controller;

import dto.SkillDTO;
import model.Skill;
import service.SkillService;
import java.util.List;

public class SkillController {
    private SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    public void addSkill(Long userId, SkillDTO dto) {
        try {
            Skill skill = skillService.addSkill(userId, dto);
            System.out.println("✓ Skill added successfully: " + skill.getSkillName());
        } catch (Exception e) {
            System.out.println("✗ Failed to add skill: " + e.getMessage());
        }
    }

    public void updateSkill(Long skillId, Long userId, SkillDTO dto) {
        try {
            Skill skill = skillService.updateSkill(skillId, userId, dto);
            System.out.println("✓ Skill updated successfully: " + skill.getSkillName());
        } catch (Exception e) {
            System.out.println("✗ Failed to update skill: " + e.getMessage());
        }
    }

    public void viewMySkills(Long userId) {
        try {
            List<SkillDTO> skills = skillService.getUserSkills(userId);
            System.out.println("\n========== MY SKILLS ==========");

            if (skills.isEmpty()) {
                System.out.println("No skills added yet.");
            } else {
                for (SkillDTO skill : skills) {
                    System.out.println("ID: " + skill.getId());
                    System.out.println("Skill: " + skill.getSkillName());
                    System.out.println("Level: " + skill.getSkillLevel());
                    System.out.println("Experience: " + skill.getExperienceYears() + " years");
                    System.out.println("Available: " + (skill.getAvailability() ? "Yes" : "No"));
                    System.out.println("Description: " + skill.getDescription());
                    System.out.println("-------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("✗ Failed to fetch skills: " + e.getMessage());
        }
    }

    public void viewAvailableSkills(Long currentUserId) {
        try {
            List<SkillDTO> skills = skillService.getAvailableSkills(currentUserId);
            System.out.println("\n========== AVAILABLE SKILLS FOR EXCHANGE ==========");

            if (skills.isEmpty()) {
                System.out.println("No available skills at the moment.");
            } else {
                for (SkillDTO skill : skills) {
                    System.out.println("ID: " + skill.getId());
                    System.out.println("Skill: " + skill.getSkillName());
                    System.out.println("Owner: " + skill.getOwnerName());
                    System.out.println("Level: " + skill.getSkillLevel());
                    System.out.println("Experience: " + skill.getExperienceYears() + " years");
                    System.out.println("Description: " + skill.getDescription());
                    System.out.println("---------------------------------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("✗ Failed to fetch available skills: " + e.getMessage());
        }
    }

    public void deleteSkill(Long skillId, Long userId) {
        try {
            skillService.deleteSkill(skillId, userId);
            System.out.println("✓ Skill deleted successfully!");
        } catch (Exception e) {
            System.out.println("✗ Failed to delete skill: " + e.getMessage());
        }
    }
}