package com.TeamMaker.demo.common.utils.security;

import com.TeamMaker.demo.entity.Streamer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(
    name = "users",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
    }
)
@Getter
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;   // 암호화된 비밀번호 저장

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  private boolean enabled = true;

  @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Streamer streamer;

  public User(String username, String password, Role role) {
    this.username = username;
    this.password = password;
    this.role = role;
    this.enabled = true;
  }

  public void connectStreamer(Streamer streamer) {
    this.streamer = streamer;
    streamer.setUser(this); // owning side 동시 세팅
  }

  public void encodePassword(PasswordEncoder encoder) {
    this.password = encoder.encode(this.password);
  }
}
