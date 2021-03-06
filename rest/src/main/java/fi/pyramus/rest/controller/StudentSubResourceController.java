package fi.pyramus.rest.controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import fi.pyramus.dao.base.LanguageDAO;
import fi.pyramus.dao.base.MunicipalityDAO;
import fi.pyramus.dao.base.NationalityDAO;
import fi.pyramus.dao.base.StudyProgrammeCategoryDAO;
import fi.pyramus.dao.base.StudyProgrammeDAO;
import fi.pyramus.dao.students.StudentActivityTypeDAO;
import fi.pyramus.dao.students.StudentEducationalLevelDAO;
import fi.pyramus.dao.students.StudentExaminationTypeDAO;
import fi.pyramus.dao.students.StudentStudyEndReasonDAO;
import fi.pyramus.domainmodel.base.EducationType;
import fi.pyramus.domainmodel.base.Language;
import fi.pyramus.domainmodel.base.Municipality;
import fi.pyramus.domainmodel.base.Nationality;
import fi.pyramus.domainmodel.base.StudyProgramme;
import fi.pyramus.domainmodel.base.StudyProgrammeCategory;
import fi.pyramus.domainmodel.students.StudentActivityType;
import fi.pyramus.domainmodel.students.StudentEducationalLevel;
import fi.pyramus.domainmodel.students.StudentExaminationType;
import fi.pyramus.domainmodel.students.StudentStudyEndReason;

@Dependent
@Stateless
public class StudentSubResourceController {
  @Inject
  private StudentStudyEndReasonDAO endReasonDAO;
  @Inject
  private StudyProgrammeCategoryDAO studyProgrammeCategoryDAO;
  @Inject
  private StudyProgrammeDAO studyProgrammeDAO;
  @Inject
  private MunicipalityDAO municipalityDAO;
  @Inject
  private LanguageDAO languageDAO;
  @Inject
  private NationalityDAO nationalityDAO;
  @Inject
  private StudentActivityTypeDAO activityTypeDAO;
  @Inject
  private StudentEducationalLevelDAO educationalLevelDAO;
  @Inject
  private StudentExaminationTypeDAO examinationTypeDAO;
  
  public Language createLanguage(String name, String code) {
    Language language = languageDAO.create(code, name);
    return language;
  }
  
  public Municipality createMunicipality(String name, String code) {
    Municipality municipality = municipalityDAO.create(name, code);
    return municipality;
  }
  
  public Nationality createNationality(String name, String code) {
    Nationality nationality = nationalityDAO.create(name, code);
    return nationality;
  }
  
  public StudentActivityType createStudentActivityType(String name) {
    StudentActivityType activityType = activityTypeDAO.create(name);
    return activityType;
  }
  
  public StudentEducationalLevel createStudentEducationalLevel(String name) {
    StudentEducationalLevel educationalLevel = educationalLevelDAO.create(name);
    return educationalLevel;
  }
  
  public StudentExaminationType createStudentExaminationType(String name) {
    StudentExaminationType examinationType = examinationTypeDAO.create(name);
    return examinationType;
  }
  
  public StudentStudyEndReason createStudentStudyEndReason(StudentStudyEndReason parentReason, String name) {
    StudentStudyEndReason studentStudyEndReason = endReasonDAO.create(parentReason, name);
    return studentStudyEndReason;
  }
  
  public StudyProgrammeCategory createStudyProgrammeCategory(String name, EducationType educationType) {
    StudyProgrammeCategory studyProgrammeCategory = studyProgrammeCategoryDAO.create(name, educationType);
    return studyProgrammeCategory;
  }
  
  public StudyProgramme createStudyProgramme(String name, StudyProgrammeCategory category, String code) {
    StudyProgramme studyProgramme = studyProgrammeDAO.create(name, category, code);
    return studyProgramme;
  }
  
  public List<Language> findLanguages() {
    List<Language> languages = languageDAO.listAll();
    return languages;
  }
  
  public List<Language> findUnarchivedLanguages() {
    List<Language> languages = languageDAO.listUnarchived();
    return languages;
  }
  
  public Language findLanguageById(Long id) {
    Language language = languageDAO.findById(id);
    return language;
  }
  
  public List<Municipality> findMunicipalities() {
    List<Municipality> municipalities = municipalityDAO.listAll();
    return municipalities;
  }

  public List<Municipality> findUnarchivedMunicipalities() {
    List<Municipality> municipalities = municipalityDAO.listUnarchived();
    return municipalities;
  }
  
  public Municipality findMunicipalityById(Long id) {
    Municipality municipality = municipalityDAO.findById(id);
    return municipality;
  }
  
  public List<Nationality> findNationalities() {
    List<Nationality> nationalities = nationalityDAO.listAll();
    return nationalities;
  }
  
  public List<Nationality> findUnarchivedNationalities() {
    List<Nationality> nationalities = nationalityDAO.listUnarchived();
    return nationalities;
  }
  
  public Nationality findNationalityById(Long id) {
    Nationality nationality = nationalityDAO.findById(id);
    return nationality;
  }
  
  public List<StudentActivityType> findStudentActivityTypes() {
    List<StudentActivityType> activityTypes = activityTypeDAO.listAll();
    return activityTypes;
  }

  public List<StudentActivityType> findUnarchivedStudentActivityTypes() {
    List<StudentActivityType> activityTypes = activityTypeDAO.listUnarchived();
    return activityTypes;
  }
  
  public StudentActivityType findStudentActivityTypeById(Long id) {
    StudentActivityType activityType = activityTypeDAO.findById(id);
    return activityType;
  }
  
  public List<StudentEducationalLevel> findStudentEducationalLevels() {
    List<StudentEducationalLevel> educationalLevels = educationalLevelDAO.listAll();
    return educationalLevels;
  }
  
  public List<StudentEducationalLevel> findUnarchivedStudentEducationalLevels() {
    List<StudentEducationalLevel> educationalLevels = educationalLevelDAO.listUnarchived();
    return educationalLevels;
  }
  
  public StudentEducationalLevel findStudentEducationalLevelById(Long id) {
    StudentEducationalLevel educationalLevel = educationalLevelDAO.findById(id);
    return educationalLevel;
  }
  
  public List<StudentExaminationType> findStudentExaminationTypes() {
    List<StudentExaminationType> examinationTypes = examinationTypeDAO.listAll();
    return examinationTypes;
  }
  
  public List<StudentExaminationType> findUnarchivedStudentExaminationTypes() {
    List<StudentExaminationType> examinationTypes = examinationTypeDAO.listUnarchived();
    return examinationTypes;
  }
  
  public StudentExaminationType findStudentExaminationTypeById(Long id) {
    StudentExaminationType examinationType = examinationTypeDAO.findById(id);
    return examinationType;
  }
  
  public List<StudentStudyEndReason> findStudentStudyEndReasons() {
    List<StudentStudyEndReason> endReasons = endReasonDAO.listAll();
    return endReasons;
  }
  
  public List<StudentStudyEndReason> findUnarchivedStudentStudyEndReasons() {
    List<StudentStudyEndReason> endReasons = endReasonDAO.listUnarchived();
    return endReasons;
  }
  
  public StudentStudyEndReason findStudentStudyEndReasonById(Long id) {
    StudentStudyEndReason studentStudyEndReason = endReasonDAO.findById(id);
    return studentStudyEndReason;
  }
  
  public List<StudyProgrammeCategory> findStudyProgrammeCategories() {
    List<StudyProgrammeCategory> studyProgrammeCategories = studyProgrammeCategoryDAO.listAll();
    return studyProgrammeCategories;
  }
  
  public List<StudyProgrammeCategory> findUnarchivedStudyProgrammeCategories() {
    List<StudyProgrammeCategory> studyProgrammeCategories = studyProgrammeCategoryDAO.listUnarchived();
    return studyProgrammeCategories;
  }
  
  public StudyProgrammeCategory findStudyProgrammeCategoryById(Long id) {
    StudyProgrammeCategory studyProgrammeCategory = studyProgrammeCategoryDAO.findById(id);
    return studyProgrammeCategory;
  }
  
  public List<StudyProgramme> findStudyProgrammes() {
    List<StudyProgramme> studyProgrammes = studyProgrammeDAO.listAll();
    return studyProgrammes;
  }

  public List<StudyProgramme> findUnarchivedStudyProgrammes() {
    List<StudyProgramme> studyProgrammes = studyProgrammeDAO.listUnarchived();
    return studyProgrammes;
  }
  
  public StudyProgramme findStudyProgrammeById(Long id) {
    StudyProgramme studyProgramme = studyProgrammeDAO.findById(id);
    return studyProgramme;
  }
  
  public Language updateLanguage(Language language, String name, String code) {
    Language updated = languageDAO.update(language, name, code);
    return updated;
  }
  
  public Municipality updateMunicipality(Municipality municipality, String name, String code) {
    Municipality updated = municipalityDAO.update(municipality, name, code);
    return updated;
  }
  
  public Nationality updateNationality(Nationality nationality, String name, String code) {
    return nationalityDAO.updateCode(nationalityDAO.updateName(nationality, name), code);
  }
  
  public StudentActivityType updateStudentActivityType(StudentActivityType activityType, String name) {
    StudentActivityType updated = activityTypeDAO.updateName(activityType, name);
    return updated;
  }
  
  public StudentEducationalLevel updateStudentEducationalLevel(StudentEducationalLevel educationalLevel, String name) {
    StudentEducationalLevel updated = educationalLevelDAO.updateName(educationalLevel, name);
    return updated;
  }
  
  public StudentExaminationType updateStudentExaminationType(StudentExaminationType examinationType, String name) {
    StudentExaminationType updated = examinationTypeDAO.updateName(examinationType, name);
    return updated;
  }
  
  public StudentStudyEndReason updateStudentStudyEndReason(StudentStudyEndReason endReason, String name) {
    StudentStudyEndReason studentStudyEndReason = endReasonDAO.updateName(endReason, name);
    return studentStudyEndReason;
  }
  
  public StudentStudyEndReason updateStudentStudyEndReasonParent(StudentStudyEndReason endReason, StudentStudyEndReason newParentReason) {
    StudentStudyEndReason studentStudyEndReason = endReasonDAO.updateParentReason(endReason, newParentReason);
    return studentStudyEndReason;
  }
  
  public StudyProgrammeCategory updateStudyProgrammeCategory(StudyProgrammeCategory studyProgrammeCategory, String name, EducationType educationType) {
    studyProgrammeCategoryDAO.update(studyProgrammeCategory, name, educationType);
    return studyProgrammeCategory;
  }
  
  public StudyProgramme updateStudyProgramme(StudyProgramme studyProgramme, String name, StudyProgrammeCategory category, String code) {
    studyProgrammeDAO.update(studyProgramme, name, category, code);
    return studyProgramme;
  }
}
