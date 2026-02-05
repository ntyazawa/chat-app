package in.tech_camp.chat_app.custom_user;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import in.tech_camp.chat_app.entity.UserEntity;
import lombok.Data;

@Data
public class CustomUserDetail implements UserDetails {

    // ここに、データベースから取ってきた「本当のユーザーデータ」を持っておく
  private final UserEntity user;

  // コンストラクタ：この会員証を作るときに、UserEntityを受け取って保管する
  public CustomUserDetail(UserEntity user) {
      this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
      return Collections.emptyList();
  }

  // 警備員「パスワードを見せろ」
  // 答え「UserEntityに入っているパスワードです」
  @Override
  public String getPassword() {
      return user.getPassword();
  }

  // 警備員「ユーザーID（ログインに使う名前）は何だ？」
  // 答え「UserEntityの『メールアドレス』をIDとして使います！」★ここ重要
  @Override
  public String getUsername() {
      return user.getEmail();
  }

  // --- 👇 ここからはSpring Securityのルールではなく、アプリで便利に使うための追加機能 ---

  // 「ユーザーのID番号（1とか2とか）を教えて」
  public Integer getId() {
      return user.getId();
  }

// 「ユーザーの表示名（Tomとか）を教えて」
  public String getName() {
      return user.getName();
  }
// --- 👆 追加機能ここまで ---


// --- 👇 ここからは「アカウントの状態」への質問（全部OKと答える） ---

  // 警備員「有効期限は切れてないか？」 → 答え「はい（true）」
  @Override
  public boolean isAccountNonExpired() {
      return true;
  }

  // 警備員「ロック（凍結）されてないか？」 → 答え「はい（true）」
  @Override
  public boolean isAccountNonLocked() {
      return true;
  }

  // 警備員「パスワードの有効期限は切れてないか？」 → 答え「はい（true）」
  @Override
  public boolean isCredentialsNonExpired() {
      return true;
  }

  // 警備員「このアカウントは有効か？」 → 答え「はい（true）」
  @Override
  public boolean isEnabled() {
      return true;
  }
}