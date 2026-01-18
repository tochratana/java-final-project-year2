package service;

import dto.ExchangeRequestDTO;
import model.ExchangeRequest;
import java.util.List;

public interface ExchangeService {
    ExchangeRequest createRequest(Long requesterId, CreateExchangeRequestDTO dto);
    ExchangeRequest acceptRequest(Long requestId, Long userId);
    ExchangeRequest rejectRequest(Long requestId, Long userId);
    ExchangeRequest confirmCompletion(Long requestId, Long userId);
    List<ExchangeRequestDTO> getMyRequests(Long userId);
    List<ExchangeRequestDTO> getRequestsForMe(Long userId);
}