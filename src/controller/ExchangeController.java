package controller;

import dto.CreateExchangeRequestDTO;
import dto.ExchangeRequestDTO;
import model.ExchangeRequest;
import service.ExchangeService;
import java.util.List;

public class ExchangeController {
    private ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    public void createExchangeRequest(Long requesterId, CreateExchangeRequestDTO dto) {
        try {
            ExchangeRequest req = exchangeService.createRequest(requesterId, dto);
            System.out.println("✓ Exchange request created successfully!");
        } catch (Exception e) {
            System.out.println("✗ Failed to create request: " + e.getMessage());
        }
    }

    public void acceptRequest(Long requestId, Long userId) {
        try {
            exchangeService.acceptRequest(requestId, userId);
            System.out.println("✓ Request accepted!");
        } catch (Exception e) {
            System.out.println("✗ Failed to accept request: " + e.getMessage());
        }
    }

    public void rejectRequest(Long requestId, Long userId) {
        try {
            exchangeService.rejectRequest(requestId, userId);
            System.out.println("✓ Request rejected!");
        } catch (Exception e) {
            System.out.println("✗ Failed to reject request: " + e.getMessage());
        }
    }

    public void confirmCompletion(Long requestId, Long userId) {
        try {
            ExchangeRequest req = exchangeService.confirmCompletion(requestId, userId);
            if ("COMPLETED".equals(req.getStatus())) {
                System.out.println("✓ Exchange completed! Both parties have confirmed.");
            } else {
                System.out.println("✓ Confirmation recorded. Waiting for other party.");
            }
        } catch (Exception e) {
            System.out.println("✗ Failed to confirm: " + e.getMessage());
        }
    }

    public void viewMyRequests(Long userId) {
        try {
            List<ExchangeRequestDTO> requests = exchangeService.getMyRequests(userId);
            System.out.println("\n========== MY EXCHANGE REQUESTS ==========");

            if (requests.isEmpty()) {
                System.out.println("No requests sent yet.");
            } else {
                for (ExchangeRequestDTO req : requests) {
                    displayRequest(req);
                }
            }
        } catch (Exception e) {
            System.out.println("✗ Failed to fetch requests: " + e.getMessage());
        }
    }

    public void viewRequestsForMe(Long userId) {
        try {
            List<ExchangeRequestDTO> requests = exchangeService.getRequestsForMe(userId);
            System.out.println("\n========== REQUESTS FOR ME ==========");

            if (requests.isEmpty()) {
                System.out.println("No incoming requests.");
            } else {
                for (ExchangeRequestDTO req : requests) {
                    displayRequest(req);
                }
            }
        } catch (Exception e) {
            System.out.println("✗ Failed to fetch requests: " + e.getMessage());
        }
    }

    private void displayRequest(ExchangeRequestDTO req) {
        System.out.println("Request ID: " + req.getId());
        System.out.println("From: " + req.getRequesterName());
        System.out.println("To: " + req.getProviderName());
        System.out.println("Requesting: " + req.getRequestedSkill());
        System.out.println("Offering: " + req.getOfferedSkill());
        System.out.println("Message: " + req.getRequestMessage());
        System.out.println("Status: " + req.getStatus());
        System.out.println("Created: " + req.getCreatedAt());
        System.out.println("------------------------------------------");
    }
}