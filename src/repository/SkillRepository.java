package repository;

import model.Skill;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkillRepository {
    private Connection conn;

    public SkillRepository(Connection conn) {
        this.conn = conn;
    }

    public Skill save(Skill skill) throws SQLException {
        String sql = "INSERT INTO skills (user_id, skill_name, skill_level, experience_years, description, availability, status, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, skill.getUserId());
            stmt.setString(2, skill.getSkillName());
            stmt.setString(3, skill.getSkillLevel());
            stmt.setInt(4, skill.getExperienceYears());
            stmt.setString(5, skill.getDescription());
            stmt.setBoolean(6, skill.getAvailability());
            stmt.setString(7, skill.getStatus());
            stmt.setTimestamp(8, Timestamp.valueOf(skill.getCreatedAt()));
            stmt.setTimestamp(9, Timestamp.valueOf(skill.getUpdatedAt()));

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                skill.setId(rs.getLong(1));
            }
            return skill;
        }
    }

    public Skill update(Skill skill) throws SQLException {
        String sql = "UPDATE skills SET skill_name = ?, skill_level = ?, experience_years = ?, " +
                "description = ?, availability = ?, status = ?, updated_at = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, skill.getSkillName());
            stmt.setString(2, skill.getSkillLevel());
            stmt.setInt(3, skill.getExperienceYears());
            stmt.setString(4, skill.getDescription());
            stmt.setBoolean(5, skill.getAvailability());
            stmt.setString(6, skill.getStatus());
            stmt.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setLong(8, skill.getId());

            stmt.executeUpdate();
            return skill;
        }
    }

    public List<Skill> findByUserId(Long userId) throws SQLException {
        String sql = "SELECT * FROM skills WHERE user_id = ? ORDER BY created_at DESC";
        List<Skill> skills = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                skills.add(mapResultSetToSkill(rs));
            }
            return skills;
        }
    }

    public List<Skill> findAvailableSkills(Long excludeUserId) throws SQLException {
        String sql = "SELECT * FROM skills WHERE availability = true AND status = 'ACTIVE' AND user_id != ? ORDER BY created_at DESC";
        List<Skill> skills = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, excludeUserId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                skills.add(mapResultSetToSkill(rs));
            }
            return skills;
        }
    }

    public Optional<Skill> findById(Long id) throws SQLException {
        String sql = "SELECT * FROM skills WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToSkill(rs));
            }
            return Optional.empty();
        }
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM skills WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private Skill mapResultSetToSkill(ResultSet rs) throws SQLException {
        return Skill.builder()
                .id(rs.getLong("id"))
                .userId(rs.getLong("user_id"))
                .skillName(rs.getString("skill_name"))
                .skillLevel(rs.getString("skill_level"))
                .experienceYears(rs.getInt("experience_years"))
                .description(rs.getString("description"))
                .availability(rs.getBoolean("availability"))
                .status(rs.getString("status"))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                .build();
    }
}
