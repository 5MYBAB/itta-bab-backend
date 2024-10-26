package com.fivemybab.ittabab.user.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDate;

@Entity
@Table(name = "user_info")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE user_info SET user_status = 'WITHDRAWN', sign_out_date = NOW() WHERE user_id=?")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String loginId;
    private String pwd;
    private String email;
    private String phone;
    private LocalDate birth;
    private Long courseId;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus = UserStatus.ACTIVE;
    @Enumerated(EnumType.STRING)
    private UserRole userRole = UserRole.USER;
    private LocalDate signUpDate = LocalDate.now();
    private LocalDate signOutDate;

    public void encryptPwd(String encodedPwd) {
        this.pwd = encodedPwd;
    }

    public void modifyPwd(String pwd) {
        this.pwd = pwd;
    }
}
