package dto;

import lombok.*;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public @interface StudentDTO {
    private Long id;
    private String name;
    private String email;
    private int age;
}
