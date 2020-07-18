package com.gol.golbackend.repository;

import com.gol.golbackend.entity.GameState;
import org.springframework.data.repository.CrudRepository;

public interface GameTableRepository extends CrudRepository<GameState, Long> {

}
