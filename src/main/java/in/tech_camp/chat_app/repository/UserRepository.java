package in.tech_camp.chat_app.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import in.tech_camp.chat_app.entity.UserEntity;

@Mapper
public  interface UserRepository {

  //保存する命令

  @Select("SELECT * FROM users WHERE email = #{email}")
    UserEntity findByEmail(String email);
  
  @Insert 
//翻訳: 「この仕事（insert）が呼ばれたら、カッコ内のSQL文 を実行してください！」

("INSERT INTO users (name,email,password) VALUES (#{name},#{email},#{password})")
//usersテーブルの『name』『email』『password』の列に、データを保存しろ！
//保存する値（VALUES）は、さっき渡された 『users』箱の中身 を使ってください！

@Options(useGeneratedKeys = true, keyProperty = "id")
//データベース側で勝手に生成されたキー（AUTO_INCREMENTやSERIALでできたID）を使いますか？」 
// 指示: 「はい（true）！その生成されたIDを取得したいです！」

//取得したIDを、Javaの箱（Entity）のどの項目に入れますか？」 
// 指示: 「id という名前の変数に入れてください！
  
  void insert (UserEntity users);
  //MyBatisさん、『insert』 という仕事を頼みます！ 
  // 材料として、データが詰まった 『users（UserEntity）』という箱 を渡しますね！

   @Select ("SELECT * FROM users WHERE id = #{id}")
  UserEntity findById(Integer id);

  @Update ("UPDATE  users SET name = #{name}, email = #{email} WHERE id = #{id}")
void update(UserEntity user);
}




