package in.tech_camp.chat_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.tech_camp.chat_app.entity.UserEntity;
import in.tech_camp.chat_app.form.LoginForm;
import in.tech_camp.chat_app.form.UserForm;
import in.tech_camp.chat_app.repository.UserRepository;
import in.tech_camp.chat_app.service.UserService;
import lombok.AllArgsConstructor;



@Controller
@AllArgsConstructor
public class UserController {

private final UserRepository userRepository;
private final UserService userService;

  @GetMapping("/users/sign_up")
  // サインアップ画面を表示するメソッド
  public String showSignUp(Model model) {
    //「誰でも呼べるよ(public)、最終的にファイル名を教えるよ(String)、名前はshowSignUpだよ、
    // JavaからHTMLにデータを渡すためのお盆(Model)を使うよ！」 という

    model.addAttribute("userForm",new UserForm());
    // 画面に「空っぽのフォーム」を渡す（これがないとエラーになることが多いです）
      return "users/signUp";
  }


//データの詰め替え（Form ➡ Entity）
  @PostMapping("/user")
  public String createUser(@ModelAttribute("userForm") UserForm userForm, Model model) {
   
   //👇 ここから詰め替え作業
    UserEntity userEntity = new UserEntity();
    userEntity.setName(userForm.getName());
    userEntity.setEmail(userForm.getEmail());
    userEntity.setPassword(userForm.getPassword());
    
    
    //Service への丸投げ（Try-Catch）
    //Service）へ。パスワードを暗号化処理した上で、倉庫にしまっておいて
    try {
       userService.createUserWithEncryptedPassword(userEntity);
    } catch (Exception e) {
      System.out.println("エラー：" + e);
      model.addAttribute("userForm", userForm);
      return "users/signUp";
    }

    return "redirect:/";
  }
  
 

   @GetMapping("/users/login")
  // ログイン画面を表示するメソッド 引数に @RequestParam(...) を追加します
public String login(@RequestParam(value = "error", required = false) String error, @ModelAttribute("loginForm") LoginForm loginForm, Model model) {
    
    // 👇 エラーがあるかチェックする処理を追加
   if (error != null) {
      model.addAttribute("loginError", "メールアドレスかパスワードが間違っています。");
    }
      return "users/login";
  }
  }
  
  

