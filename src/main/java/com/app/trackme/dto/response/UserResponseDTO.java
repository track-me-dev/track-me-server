package com.app.trackme.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private String username;
    private String email;
    private String phoneNumber;
}
