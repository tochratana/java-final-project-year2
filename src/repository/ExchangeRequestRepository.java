package repository;

import model.ExchangeRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ExchangeRequestRepository {
    ExchangeRequest save(ExchangeRequest request) throws SQLException;
    ExchangeRequest update(ExchangeRequest request) throws SQLException;
    List<ExchangeRequest> findByRequesterId(Long requesterId) throws SQLException;
    List<ExchangeRequest> findByProviderId(Long providerId) throws SQLException;
    Optional<ExchangeRequest> findById(Long id) throws SQLException;
}