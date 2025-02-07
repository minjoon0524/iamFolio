package com.inhatc.portfolio.repository;

import com.inhatc.portfolio.dto.BoardResponseDto;
import com.inhatc.portfolio.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query(value = "select board " +
            "from Board board " +
            "where board.title = :keyword OR board.content = :keyword")
    List<Board> findByTitleOrContent(@Param("keyword") String keyword);
}
