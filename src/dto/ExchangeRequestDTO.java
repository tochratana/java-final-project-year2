package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeRequestDTO {
    private Long id;
    private String requesterName;
    private String providerName;
    private String requestedSkill;
    private String offeredSkill;
    private String requestMessage;
    private String status;
    private LocalDateTime createdAt;
}
