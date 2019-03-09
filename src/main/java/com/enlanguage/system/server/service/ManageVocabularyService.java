package com.enlanguage.system.server.service;

import com.enlanguage.system.server.model.dto.BoxWordDTO;
import com.enlanguage.system.server.model.dto.VocabularyPackageDTO;
import com.enlanguage.system.server.utils.TextFieldClear;

import java.util.List;
import java.util.Set;

/**
 * Created by Dominik on 03.05.2017.
 */

public interface ManageVocabularyService {

  void saveVocabulary(VocabularyPackageDTO vocabularyPackageDTO, TextFieldClear textFieldClear);

  List<VocabularyPackageDTO> loadVocabulary();

  void deleteVocabulary(VocabularyPackageDTO vocabularyPackageDTO);

  List<VocabularyPackageDTO> searchVocabulary(String searchValue);

  void saveNumberOfRememberWords(String value);

  int getNumberOfRememberWords();

  void addRememberBoxWords(Set<VocabularyPackageDTO> selectedItems);

  List<BoxWordDTO> loadRememberWords();

  void deleteRememberWord(Set<BoxWordDTO> selectedItems);

}
