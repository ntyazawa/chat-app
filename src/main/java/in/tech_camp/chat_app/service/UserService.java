package in.tech_camp.chat_app.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.tech_camp.chat_app.entity.UserEntity;
import in.tech_camp.chat_app.repository.UserRepository;
import lombok.AllArgsConstructor; 
  


@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  
  public void createUserWithEncryptedPassword(UserEntity userEntity) {
    
  // 1. パスワードを暗号化する
    String encodedPassword = encodePassword(userEntity.getPassword());
    
    // 2. 暗号化したパスワードをEntityに戻す（上書き）
    userEntity.setPassword(encodedPassword);
    
    // 3. 保存する
    userRepository.insert(userEntity);
}

// 下請けのメソッド（外部からは見えない private）
private String encodePassword(String password) {
  //[材料を受け取る]ここには、ユーザーが入力した**
  // 「生のパスワード」**（例: "123456"）が入ってきます。
    return passwordEncoder.encode(password);
//「加工する」(passwordEncoder.encode(...)):
//Spring Securityが用意した**「シュレッダー（暗号化マシーン）」**にパスワードを通します。
//納品する (return):
//出来上がった「謎の文字列」を、呼び出し元（上司メソッド）に返します。


}
}