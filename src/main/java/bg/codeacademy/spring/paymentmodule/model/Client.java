package bg.codeacademy.spring.paymentmodule.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Client
{
  private String id;
  private String name;
  private String email;
  private String phoneNumber;
}
