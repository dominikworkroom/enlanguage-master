package com.enlanguage.system.server.repository;

import com.enlanguage.system.server.model.User;
import com.enlanguage.system.server.model.Vocabulary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Dominik on 27.04.2017.
 */

@Repository
public interface VocabularyRepository extends JpaRepository<Vocabulary, Long> {


  @Query("SELECT v FROM Vocabulary v WHERE v.meaningWord LIKE CONCAT('%',:searchValue,'%') " +
      "AND v.user=:user OR v.baseWord LIKE CONCAT('%',:searchValue,'%') AND v.user=:user")
  List<Vocabulary> findVocabulariesBySearchValue(@Param("user") User user,
      @Param("searchValue") String searchValue);
}
