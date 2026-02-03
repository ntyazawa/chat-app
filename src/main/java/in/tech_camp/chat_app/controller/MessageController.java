package in.tech_camp.chat_app.controller; //  パッケージ（住所）

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller  //  アノテーション（クラスへの看板）
public class MessageController {  // クラス（設計図の名前）
  @GetMapping("/")  //  アノテーション（メソッドへの看板)
  // 「住所の最後が /（トップページ）でアクセスが来たら、このメソッドを動かしてね」
  public String showMessages(){  //  メソッド（動作・処理）
    //メッセージを表示しろ（showMessages）」という命令が来たら、中括弧 { } の中の処理を実行します。
return "messages/index";   //  戻り値（結果）

  }
}
