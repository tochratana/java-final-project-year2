package service.impl;

import dto.CreateExchangeRequestDTO;
import dto.ExchangeRequestDTO;
import model.ExchangeRequest;
import model.User;
import repository.ExchangeRequestRepository;
import repository.UserRepository;
import service.ExchangeService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExchangeServiceImpl implements ExchangeService {
    private ExchangeRequestRepository exchangeRepository;
    private UserRepository userRepository;

    public ExchangeServiceImpl(ExchangeRequestRepository exchangeRepository, UserRepository userRepository) {
        this.exchangeRepository = exchangeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ExchangeRequest createRequest(Long requesterId, CreateExchangeRequestDTO dto) {
        try {
            ExchangeRequest request = ExchangeRequest.builder()
                    .requesterId(requesterId)
                    .providerId(dto.getProviderId())
                    .requestedSkill(dto.getRequestedSkill())
                    .offeredSkill(dto.getOfferedSkill())
                    .requestMessage(dto.getRequestMessage())
                    .status("PENDING")
                    .requesterConfirmed(false)
                    .providerConfirmed(false)
                    .createdAt(LocalDateTime.now())
                    .build();

            return exchangeRepository.save(request);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create exchange request: " + e.getMessage(), e);
        }
    }

    @Override
    public ExchangeRequest acceptRequest(Long requestId, Long userId) {
        try {
            ExchangeRequest request = exchangeRepository.findById(requestId)
                    .orElseThrow(() -> new IllegalArgumentException("Request not found"));

            if (!request.getProviderId().equals(userId)) {
                throw new IllegalArgumentException("Only provider can accept request");
            }

            if (!"PENDING".equals(request.getStatus())) {
                throw new IllegalArgumentException("Request is not pending");
            }

            request.setStatus("ACCEPTED");
            return exchangeRepository.update(request);
        } catch (Exception e) {
            throw new RuntimeException("Failed to accept request: " + e.getMessage(), e);
        }
    }

    @Override
    public ExchangeRequest rejectRequest(Long requestId, Long userId) {
        try {
            ExchangeRequest request = exchangeRepository.findById(requestId)
                    .orElseThrow(() -> new IllegalArgumentException("Request not found"));

            if (!request.getProviderId().equals(userId)) {
                throw new IllegalArgumentException("Only provider can reject request");
            }

            request.setStatus("REJECTED");
            return exchangeRepository.update(request);
        } catch (Exception e) {
            throw new RuntimeException("Failed to reject request: " + e.getMessage(), e);
        }
    }

    @Override
    public ExchangeRequest confirmCompletion(Long requestId, Long userId) {
        try {
            ExchangeRequest request = exchangeRepository.findById(requestId)
                    .orElseThrow(() -> new IllegalArgumentException("Request not found"));

            if (!"ACCEPTED".equals(request.getStatus())) {
                throw new IllegalArgumentException("Request must be accepted first");
            }

            if (request.getRequesterId().equals(userId)) {
                request.setRequesterConfirmed(true);
            } else if (request.getProviderId().equals(userId)) {
                request.setProviderConfirmed(true);
            } else {
                throw new IllegalArgumentException("Unauthorized");
            }

            // If both confirmed, mark as completed
            if (request.getRequesterConfirmed() && request.getProviderConfirmed()) {
                request.setStatus("COMPLETED");
            }

            return exchangeRepository.update(request);
        } catch (Exception e) {
            throw new RuntimeException("Failed to confirm completion: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ExchangeRequestDTO> getMyRequests(Long userId) {
        try {
            List<ExchangeRequest> requests = exchangeRepository.findByRequesterId(userId);
            return convertToDTOList(requests);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch requests: " + e.getMessage(), e);
        }
    }

    @Override
    public List<ExchangeRequestDTO> getRequestsForMe(Long userId) {
        try {
            List<ExchangeRequest> requests = exchangeRepository.findByProviderId(userId);
            return convertToDTOList(requests);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch requests: " + e.getMessage(), e);
        }
    }

    private List<ExchangeRequestDTO> convertToDTOList(List<ExchangeRequest> requests) {
        List<ExchangeRequestDTO> dtos = new ArrayList<>();

        try {
            for (ExchangeRequest req : requests) {
                User requester = userRepository.findById(req.getRequesterId()).orElse(null);
                User provider = userRepository.findById(req.getProviderId()).orElse(null);

                ExchangeRequestDTO dto = ExchangeRequestDTO.builder()
                        .id(req.getId())
                        .requesterName(requester != null ? requester.getFullName() : "Unknown")
                        .providerName(provider != null ? provider.getFullName() : "Unknown")
                        .requestedSkill(req.getRequestedSkill())
                        .offeredSkill(req.getOfferedSkill())
                        .requestMessage(req.getRequestMessage())
                        .status(req.getStatus())
                        .createdAt(req.getCreatedAt())
                        .build();

                dtos.add(dto);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert requests: " + e.getMessage(), e);
        }

        return dtos;
    }
}