package com.TeamMaker.demo.repository.streamer;

import com.TeamMaker.demo.entity.Streamer;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreamerRepository extends JpaRepository<Streamer, UUID>, StreamerRepositoryCustom {

}
