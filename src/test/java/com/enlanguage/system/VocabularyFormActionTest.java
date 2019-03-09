package com.enlanguage.system;


import com.enlanguage.system.server.model.dto.VocabularyPackageDTO;
import com.enlanguage.system.server.service.ManageVocabularyService;
import com.enlanguage.system.ui.form.VocabularyForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VocabularyFormActionTest {

  @Mock
  ManageVocabularyService manageVocabularyService;

  @Mock
  VocabularyForm vocabularyForm;

  VocabularyPackageDTO vocabularyPackageDTO;


  @Test
  public void correctSaveVocabularyTest() throws Exception {
    vocabularyPackageDTO = new VocabularyPackageDTO();
    vocabularyPackageDTO.setBaseWord("TEST_ENG");
    vocabularyPackageDTO.setMeaningWord("TEST_PL");

    manageVocabularyService
        .saveVocabulary(vocabularyPackageDTO, () -> vocabularyForm.clearFields());

  }

  @Test
  public void saveNullVocabularyDTO() throws Exception {
    vocabularyPackageDTO = null;
    manageVocabularyService
        .saveVocabulary(vocabularyPackageDTO, () -> vocabularyForm.clearFields());
  }


}
