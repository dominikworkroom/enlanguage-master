package com.enlanguage.system.server.repository;

import com.enlanguage.system.server.model.BoxWord;
import com.enlanguage.system.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxWordRepository extends JpaRepository<BoxWord, Long> {

  void deleteBoxWordByBaseBoXwordAndMeaningBoxWordAndUser(String base, String meaning, User user);

}
