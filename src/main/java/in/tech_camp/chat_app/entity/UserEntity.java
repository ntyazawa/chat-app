package in.tech_camp.chat_app.entity;

import lombok.Data;

@Data
public class UserEntity {
  private Integer id;       // usersテーブルの id
    private String name;      // usersテーブルの name
    private String email;     // usersテーブルの email
    private String password;  // usersテーブルの password
  
}
