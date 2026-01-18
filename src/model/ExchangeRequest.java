package model;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeRequest {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
}
