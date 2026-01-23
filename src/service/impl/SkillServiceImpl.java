package service.impl;

import dto.SkillDTO;
import model.Skill;
import model.User;
import repository.SkillRepository;
import repository.UserRepository;
import service.SkillService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SkillServiceImpl implements SkillService {
    private SkillRepository skillRepository;
    private UserRepository userRepository;

    public SkillServiceImpl(SkillRepository skillRepository, UserRepository userRepository) {
        this.skillRepository = skillRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Skill addSkill(Long userId, SkillDTO dto) {
        try {
            Skill skill = Skill.builder()
                    .userId(userId)
                    .skillName(dto.getSkillName())
                    .skillLevel(dto.getSkillLevel())
                    .experienceYears(dto.getExperienceYears())
                    .description(dto.getDescription())
                    .availability(dto.getAvailability() != null ? dto.getAvailability() : true)
                    .status("ACTIVE")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            return skillRepository.save(skill);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add skill: " + e.getMessage(), e);
        }
    }

    @Override
    public Skill updateSkill(Long skillId, Long userId, SkillDTO dto) {
        try {
            Skill skill = skillRepository.findById(skillId)
                    .orElseThrow(() -> new IllegalArgumentException("Skill not found"));

            if (!skill.getUserId().equals(userId)) {
                throw new IllegalArgumentException("Unauthorized to update this skill");
            }

            skill.setSkillName(dto.getSkillName());
            skill.setSkillLevel(dto.getSkillLevel());
            skill.setExperienceYears(dto.getExperienceYears());
            skill.setDescription(dto.getDescription());
            skill.setAvailability(dto.getAvailability());
            skill.setUpdatedAt(LocalDateTime.now());

            return skillRepository.update(skill);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update skill: " + e.getMessage(), e);
        }
    }

    @Override
    public List<SkillDTO> getUserSkills(Long userId) {
        try {
            List<Skill> skills = skillRepository.findByUserId(userId);
            List<SkillDTO> dtos = new ArrayList<>();

            for (Skill skill : skills) {
                dtos.add(convertToDTO(skill, null));
            }
            return dtos;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch user skills: " + e.getMessage(), e);
        }
    }

    @Override
    public List<SkillDTO> getAvailableSkills(Long currentUserId) {
        try {
            List<Skill> skills = skillRepository.findAvailableSkills(currentUserId);
            List<SkillDTO> dtos = new ArrayList<>();

            for (Skill skill : skills) {
                User owner = userRepository.findById(skill.getUserId()).orElse(null);
                String ownerName = owner != null ? owner.getFullName() : "Unknown";
                dtos.add(convertToDTO(skill, ownerName));
            }
            return dtos;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch available skills: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteSkill(Long skillId, Long userId) {
        try {
            Skill skill = skillRepository.findById(skillId)
                    .orElseThrow(() -> new IllegalArgumentException("Skill not found"));

            if (!skill.getUserId().equals(userId)) {
                throw new IllegalArgumentException("Unauthorized to delete this skill");
            }

            skillRepository.delete(skillId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete skill: " + e.getMessage(), e);
        }
    }

    private SkillDTO convertToDTO(Skill skill, String ownerName) {
        return SkillDTO.builder()
                .id(skill.getId())
                .skillName(skill.getSkillName())
                .skillLevel(skill.getSkillLevel())
                .experienceYears(skill.getExperienceYears())
                .description(skill.getDescription())
                .availability(skill.getAvailability())
                .ownerName(ownerName)
                .build();
    }
}