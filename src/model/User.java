package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private String role; // USER, ADMIN
    private String status; // ACTIVE, INACTIVE, SUSPENDED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
