package com.TeamMaker.demo.repository.teamBoard;

import com.TeamMaker.demo.entity.TeamBoard;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamBoardRepository extends JpaRepository<TeamBoard, UUID>, TeamBoardRepositoryCustom {

}
