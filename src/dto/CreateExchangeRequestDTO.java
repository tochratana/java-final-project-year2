package dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CreateExchangeRequestDTO {
    private Long providerId;
    private String requestedSkill;
    private String offeredSkill;
    private String requestMessage;
}