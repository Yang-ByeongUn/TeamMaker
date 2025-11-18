package com.TeamMaker.demo.repository.nickname;

import com.TeamMaker.demo.entity.Nickname;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NicknameRepository extends JpaRepository<Nickname, UUID>, NicknameRepositoryCustom  {

}
