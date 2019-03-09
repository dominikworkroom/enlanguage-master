package com.enlanguage.system.server.service;

import com.enlanguage.system.server.model.BoxWord;
import com.enlanguage.system.server.model.User;
import com.enlanguage.system.server.model.Vocabulary;
import com.enlanguage.system.server.model.dto.BoxWordDTO;
import com.enlanguage.system.server.model.dto.VocabularyPackageDTO;
import com.enlanguage.system.server.repository.BoxWordRepository;
import com.enlanguage.system.server.repository.UserRepository;
import com.enlanguage.system.server.utils.LanguageList;
import com.enlanguage.system.server.repository.VocabularyRepository;
import com.enlanguage.system.server.utils.TextFieldClear;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Dominik on 27.04.2017.
 */

@Service
@Transactional
public class ManageVocabularyServiceImpl implements ManageVocabularyService {

  private static final String SUCCESSFUL_ADDING_WORD_MESSAGE = "You added remember word to RememberBox!";

  @Autowired
  private VocabularyRepository vocabularyRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private BoxWordRepository boxWordRepository;

  TextFieldClear textFieldClearImpl = null;
  private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

  @Override
  public void saveVocabulary(VocabularyPackageDTO vocabularyPackageDTO,
      TextFieldClear textFieldClear) {
    textFieldClearImpl = textFieldClear;
    User user = userService.getCurrentUser();

    if (vocabularyPackageDTO == null) {
      Notification.show("Nie można zapisać danych", Notification.Type.ERROR_MESSAGE);
      return;
    }

    Vocabulary vocabulary = new Vocabulary();
    vocabulary.setBaseWord(vocabularyPackageDTO.getBaseWord().toUpperCase());
    vocabulary.setMeaningWord(vocabularyPackageDTO.getMeaningWord().toUpperCase());
    vocabulary.setBaseWordLang(LanguageList.EN);
    vocabulary.setMeaningWordLang(LanguageList.PL);
    vocabulary.setUser(user);

    vocabularyRepository.save(vocabulary);
    textFieldClear.clearFields();
  }

  @Override
  public List<VocabularyPackageDTO> loadVocabulary() {
    User user = userService.getCurrentUser();
    try {
      List<VocabularyPackageDTO> words = new ArrayList<>();
      List<Vocabulary> all = user.getVocabularies();

      all.forEach(e -> words.add(transferEntityToDto(e)));

      return words;

    } catch (Exception e) {
      Notification.show("Loading data error", Notification.Type.ERROR_MESSAGE);
    }

    return Collections.emptyList();
  }


  @Override
  public void deleteVocabulary(VocabularyPackageDTO vocabularyPackageDTO) {
    vocabularyRepository.delete(vocabularyPackageDTO.getId());
  }

  @Override
  public List<VocabularyPackageDTO> searchVocabulary(String searchValue) {
    User user = userService.getCurrentUser();
    List<VocabularyPackageDTO> words = new ArrayList<>();

    List<Vocabulary> vocabulariesBySearchValue = vocabularyRepository
        .findVocabulariesBySearchValue(user, searchValue.toUpperCase());

    if (!validateResult(vocabulariesBySearchValue)) {
      return this.loadVocabulary();
    }

    vocabulariesBySearchValue.forEach(v -> words.add(transferEntityToDto(v)));

    return words;
  }

  @Override
  public void saveNumberOfRememberWords(String value) {
    User user = userService.getCurrentUser();

    int numberOfRememberWords = Integer.valueOf(value);
    user.setNumberOfRememberWords(numberOfRememberWords);
    userRepository.save(user);
  }

  @Override
  public int getNumberOfRememberWords() {
    User user = userService.getCurrentUser();
    return user.getNumberOfRememberWords();
  }

  @Override
  public void addRememberBoxWords(final Set<VocabularyPackageDTO> selectedItems) {
    final User currentUser = userService.getCurrentUser();
    int numberOfRememberWords = currentUser.getNumberOfRememberWords();
    int freeSlots = calculateFreeSlotsInBoxWord(numberOfRememberWords, currentUser.getBoxwords());
    if (selectedItems.size() > freeSlots) {
      Notification.show("You can't add " + selectedItems.size()
          + " words, your free slots in Remember Box : "
          + freeSlots + " - delete some words so that add another ones", Type.WARNING_MESSAGE);
    } else {
      if (currentUser.getBoxwords() != null && currentUser.getBoxwords().size() > 0) {
        currentUser.getBoxwords().forEach(boxWord -> {
          if (!checkIfWordExistInRememberBox(selectedItems, boxWord)) {
            saveRememberWords(selectedItems, currentUser);
            Notification.show(SUCCESSFUL_ADDING_WORD_MESSAGE);
          } else {
            Notification.show("Word with name: " + boxWord.getBaseBoXword()
                + " / " + boxWord.getMeaningBoxWord() + " exists in remember box.");
          }
        });
      } else {
        saveRememberWords(selectedItems, currentUser);
        Notification.show(SUCCESSFUL_ADDING_WORD_MESSAGE);
      }


    }
  }

  private boolean checkIfWordExistInRememberBox(Set<VocabularyPackageDTO> selectedItems,
      BoxWord boxWord) {
    return selectedItems.stream().anyMatch(wordExists(boxWord));

  }

  private Predicate<VocabularyPackageDTO> wordExists(BoxWord boxWord) {

    return p -> p.getBaseWord().equals(boxWord.getBaseBoXword())
        && p.getMeaningWord().equals(boxWord.getMeaningBoxWord());
  }

  @Override
  public List<BoxWordDTO> loadRememberWords() {
    final User currentUser = userService.getCurrentUser();
    final List<BoxWord> rememberWords = currentUser.getBoxwords();
    return rememberWords.stream()
        .map(this::transferRememberWordsToDTO).collect(Collectors.toList());
  }

  @Override
  public void deleteRememberWord(Set<BoxWordDTO> selectedItems) {
    User currentUser = userService.getCurrentUser();
    selectedItems.forEach(selectedItem -> {
      boxWordRepository
          .deleteBoxWordByBaseBoXwordAndMeaningBoxWordAndUser(selectedItem.getBaseBoXword(),
              selectedItem.getMeaningBoxWord(), currentUser);
    });

  }

  private BoxWordDTO transferRememberWordsToDTO(BoxWord rememberWord) {
    BoxWordDTO boxWordDTO = new BoxWordDTO();
    boxWordDTO.setBaseBoXword(rememberWord.getBaseBoXword());
    boxWordDTO.setBaseBoxWordLang(rememberWord.getBaseBoxWordLang());
    boxWordDTO.setMeaningBoxWord(rememberWord.getMeaningBoxWord());
    boxWordDTO.setMeaningBoxWordLang(rememberWord.getMeaningBoxWordLang());

    return boxWordDTO;
  }

  private void saveRememberWords(Set<VocabularyPackageDTO> selectedItems, User currentUser) {
    final List<BoxWord> boxwords = selectedItems.stream()
        .map(selectedItem -> transferVocabulariesDTOToWordBoxEntity(selectedItem, currentUser))
        .collect(Collectors.toList());
    currentUser.setBoxwords(boxwords);
    boxWordRepository.save(boxwords);
  }

  private BoxWord transferVocabulariesDTOToWordBoxEntity(VocabularyPackageDTO selectedItem,
      User currentUser) {
    final BoxWord boxWord = new BoxWord();
    boxWord.setUser(currentUser);
    boxWord.setBaseBoXword(selectedItem.getBaseWord());
    boxWord.setBaseBoxWordLang(selectedItem.getBaseWordLang());
    boxWord.setMeaningBoxWord(selectedItem.getMeaningWord());
    boxWord.setMeaningBoxWordLang(selectedItem.getMeaningWordLang());
    return boxWord;
  }

  private int calculateFreeSlotsInBoxWord(int numberOfRememberWords, List<BoxWord> boxwords) {
    return numberOfRememberWords - boxwords.size();
  }

  private boolean validateResult(List<Vocabulary> vocabulariesBySearchValue) {
    if (vocabulariesBySearchValue.isEmpty()) {
      Notification.show("Found 0 elements.");
      return false;
    }

    return true;
  }

  private VocabularyPackageDTO transferEntityToDto(Vocabulary vocabulary) {
    VocabularyPackageDTO vocabularyPackageDTO = new VocabularyPackageDTO();
    vocabularyPackageDTO.setId(vocabulary.getId());
    vocabularyPackageDTO.setBaseWord(vocabulary.getBaseWord());
    vocabularyPackageDTO.setMeaningWord(vocabulary.getMeaningWord());
    vocabularyPackageDTO.setBaseWordLang(vocabulary.getBaseWordLang());
    vocabularyPackageDTO.setMeaningWordLang(vocabulary.getMeaningWordLang());
    vocabularyPackageDTO.setDate(simpleDateFormat.format(vocabulary.getCreatedAt()));

    return vocabularyPackageDTO;
  }

}
