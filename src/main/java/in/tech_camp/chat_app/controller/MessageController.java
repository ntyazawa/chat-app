package in.tech_camp.chat_app.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import in.tech_camp.chat_app.custom_user.CustomUserDetail;
import in.tech_camp.chat_app.entity.UserEntity;
import in.tech_camp.chat_app.repository.UserRepository;
import lombok.AllArgsConstructor;

@Controller  //  アノテーション（クラスへの看板）
@AllArgsConstructor

public class MessageController {  // クラス（設計図の名前）
  private final UserRepository userRepository;


  @GetMapping("/")  //  アノテーション（メソッドへの看板)
  // 「住所の最後が /（トップページ）でアクセスが来たら、このメソッドを動かしてね」

  // 現在ログインしているユーザーの「認証情報」を受け取る
  public String showMessages(@AuthenticationPrincipal CustomUserDetail currentUser, Model model){
    
    // 認証情報のIDを使って、DBから「最新のユーザーEntity」を取得
    UserEntity user = userRepository.findById(currentUser.getId());
    
  // 最新情報を "user" という名前でViewに渡す
    model.addAttribute("user", user); 
    
    //  メソッド（動作・処理）
    //メッセージを表示しろ（showMessages）」という命令が来たら、中括弧 { } の中の処理を実行します。
return "messages/index";   //  戻り値（結果）

  }
}
