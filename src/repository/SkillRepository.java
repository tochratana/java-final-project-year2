package repository;

import model.Skill;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface SkillRepository {
    Skill save(Skill skill) throws SQLException;
    Skill update(Skill skill) throws SQLException;
    List<Skill> findByUserId(Long userId) throws SQLException;
    List<Skill> findAvailableSkills(Long excludeUserId) throws SQLException;
    Optional<Skill> findById(Long id) throws SQLException;
    void delete(Long id) throws SQLException;
}