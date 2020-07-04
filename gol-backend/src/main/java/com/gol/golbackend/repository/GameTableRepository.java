package com.gol.golbackend.repository;

import com.gol.golbackend.entity.GameTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface GameTableRepository extends CrudRepository<GameTable, Long> {

}
