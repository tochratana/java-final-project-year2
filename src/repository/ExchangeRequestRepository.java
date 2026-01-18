package repository;

import model.ExchangeRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExchangeRequestRepository {
    private Connection conn;

    public ExchangeRequestRepository(Connection conn) {
        this.conn = conn;
    }

    public ExchangeRequest save(ExchangeRequest request) throws SQLException {
        String sql = "INSERT INTO exchange_requests (requester_id, provider_id, requested_skill, offered_skill, " +
                "request_message, status, requester_confirmed, provider_confirmed, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, request.getRequesterId());
            stmt.setLong(2, request.getProviderId());
            stmt.setString(3, request.getRequestedSkill());
            stmt.setString(4, request.getOfferedSkill());
            stmt.setString(5, request.getRequestMessage());
            stmt.setString(6, request.getStatus());
            stmt.setBoolean(7, request.getRequesterConfirmed());
            stmt.setBoolean(8, request.getProviderConfirmed());
            stmt.setTimestamp(9, Timestamp.valueOf(request.getCreatedAt()));

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                request.setId(rs.getLong(1));
            }
            return request;
        }
    }

    public ExchangeRequest update(ExchangeRequest request) throws SQLException {
        String sql = "UPDATE exchange_requests SET status = ?, requester_confirmed = ?, provider_confirmed = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, request.getStatus());
            stmt.setBoolean(2, request.getRequesterConfirmed());
            stmt.setBoolean(3, request.getProviderConfirmed());
            stmt.setLong(4, request.getId());

            stmt.executeUpdate();
            return request;
        }
    }

    public List<ExchangeRequest> findByRequesterId(Long requesterId) throws SQLException {
        String sql = "SELECT * FROM exchange_requests WHERE requester_id = ? ORDER BY created_at DESC";
        return findByQuery(sql, requesterId);
    }

    public List<ExchangeRequest> findByProviderId(Long providerId) throws SQLException {
        String sql = "SELECT * FROM exchange_requests WHERE provider_id = ? ORDER BY created_at DESC";
        return findByQuery(sql, providerId);
    }

    public Optional<ExchangeRequest> findById(Long id) throws SQLException {
        String sql = "SELECT * FROM exchange_requests WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapResultSetToRequest(rs));
            }
            return Optional.empty();
        }
    }

    private List<ExchangeRequest> findByQuery(String sql, Long userId) throws SQLException {
        List<ExchangeRequest> requests = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                requests.add(mapResultSetToRequest(rs));
            }
            return requests;
        }
    }

    private ExchangeRequest mapResultSetToRequest(ResultSet rs) throws SQLException {
        return ExchangeRequest.builder()
                .id(rs.getLong("id"))
                .requesterId(rs.getLong("requester_id"))
                .providerId(rs.getLong("provider_id"))
                .requestedSkill(rs.getString("requested_skill"))
                .offeredSkill(rs.getString("offered_skill"))
                .requestMessage(rs.getString("request_message"))
                .status(rs.getString("status"))
                .requesterConfirmed(rs.getBoolean("requester_confirmed"))
                .providerConfirmed(rs.getBoolean("provider_confirmed"))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .build();
    }
}
