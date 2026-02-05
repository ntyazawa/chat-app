package in.tech_camp.chat_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.tech_camp.chat_app.entity.UserEntity;
import in.tech_camp.chat_app.form.LoginForm;
import in.tech_camp.chat_app.form.UserEditForm;
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

  @GetMapping("/users/{userId}/edit")
 public String editUserForm(@PathVariable("userId") Integer userId, Model model) {
    UserEntity user = userRepository.findById(userId);

    UserEditForm userForm = new UserEditForm();
    userForm.setId(user.getId());
    userForm.setName(user.getName());
    userForm.setEmail(user.getEmail());

    model.addAttribute("user", userForm);
    return "users/edit";
  }

 @PostMapping("/users/{userId}")
  public String updateUser(@PathVariable("userId") Integer userId, @ModelAttribute("user") UserEditForm userEditForm, Model model) {
    
    // 1. まず、IDを使って現在のユーザー情報をDBから取得する
    UserEntity user = userRepository.findById(userId);

    // 2. 取得した情報の「名前」と「メアド」を、フォームの内容で上書きする
    user.setName(userEditForm.getName());
    user.setEmail(userEditForm.getEmail());

    // 3. 命綱をつけて更新処理を実行する
    try {
      userRepository.update(user);
    } catch (Exception e) {

      // 失敗したらログを出して、編集画面に戻る
      System.out.println("エラー：" + e);
      model.addAttribute("user", userEditForm);
      return "users/edit";
    }

    // 成功したらトップページへ
    return "redirect:/";
  }
  }
  
  
  
  

