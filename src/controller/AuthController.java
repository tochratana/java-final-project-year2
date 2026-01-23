package controller;

import dto.UserLoginDTO;
import dto.UserRegistrationDTO;
import model.User;
import service.AuthService;

public class AuthController {
    private AuthService authService;
    private User currentUser;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public void register(UserRegistrationDTO dto) {
        try {
            User user = authService.register(dto);
            System.out.println("✓ Registration successful! Welcome " + user.getFullName());
        } catch (Exception e) {
            System.out.println("✗ Registration failed: " + e.getMessage());
        }
    }

    public boolean login(UserLoginDTO dto) {
        try {
            currentUser = authService.login(dto);
            System.out.println("✓ Login successful! Welcome back " + currentUser.getFullName());
            return true;
        } catch (Exception e) {
            System.out.println("✗ Login failed: " + e.getMessage());
            return false;
        }
    }

    public void logout() {
        currentUser = null;
        System.out.println("✓ Logged out successfully!");
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }
}