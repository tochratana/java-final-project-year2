package dto;

import lombok.*;
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