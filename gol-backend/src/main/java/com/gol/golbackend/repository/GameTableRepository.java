package com.gol.golbackend.repository;

import com.gol.golbackend.entity.GameState;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GameTableRepository extends CrudRepository<GameState, Long> {

    @Query(value = """
            select * from game_state g
            join table_row r on g.id = r.game_state_id
            join field f on r.id = f.row_id
            where g.id = ? group by r.id, g.id, f.id;
            """, nativeQuery = true)
    Optional<GameState> findById(Long id);
}
