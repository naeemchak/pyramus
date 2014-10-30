package fi.pyramus.json.students;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;

import fi.internetix.smvc.controllers.JSONRequestContext;
import fi.pyramus.I18N.Messages;
import fi.pyramus.dao.DAOFactory;
import fi.pyramus.dao.base.PersonDAO;
import fi.pyramus.dao.base.StudyProgrammeDAO;
import fi.pyramus.dao.students.StudentGroupDAO;
import fi.pyramus.domainmodel.base.Person;
import fi.pyramus.domainmodel.base.StudyProgramme;
import fi.pyramus.domainmodel.students.Student;
import fi.pyramus.domainmodel.students.StudentGroup;
import fi.pyramus.framework.JSONRequestController;
import fi.pyramus.framework.UserRole;
import fi.pyramus.persistence.search.SearchResult;
import fi.pyramus.persistence.search.StudentFilter;

/**
 * Request handler for searching students.
 * 
 * @author antti.viljakainen
 */
public class SearchStudentsDialogJSONRequestContoller extends JSONRequestController {

  public void process(JSONRequestContext jsonRequestContext) {
    StudentGroupDAO studentGroupDAO = DAOFactory.getInstance().getStudentGroupDAO();
    PersonDAO personDAO = DAOFactory.getInstance().getPersonDAO();
    StudyProgrammeDAO studyProgrammeDAO = DAOFactory.getInstance().getStudyProgrammeDAO();

    Integer resultsPerPage = NumberUtils.createInteger(jsonRequestContext.getRequest().getParameter("maxResults"));
    if (resultsPerPage == null) {
      resultsPerPage = 10;
    }

    Integer page = NumberUtils.createInteger(jsonRequestContext.getRequest().getParameter("page"));
    if (page == null) {
      page = 0;
    }
    
    SearchResult<Person> searchResult = null;
    
    String query = jsonRequestContext.getRequest().getParameter("query");
    StudentFilter studentFilter = (StudentFilter) jsonRequestContext.getEnum("studentFilter", StudentFilter.class);
    StudyProgramme studyProgramme = null;
    StudentGroup studentGroup = null;
    
    Long studyProgrammeId = jsonRequestContext.getLong("studyProgrammeId");
    Long studentGroupId = jsonRequestContext.getLong("studentGroupId");
    
    if (studyProgrammeId.intValue() != -1)
      studyProgramme = studyProgrammeDAO.findById(studyProgrammeId);
    if (studentGroupId.intValue() != -1)
      studentGroup = studentGroupDAO.findById(studentGroupId);

    searchResult = personDAO.searchPersonsBasic(resultsPerPage, page, query, studentFilter, studyProgramme, studentGroup);
    
    List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
    List<Person> persons = searchResult.getResults();
    for (Person person : persons) {
    	Student student = person.getLatestStudent();
    	if (student != null) {
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("personId", person.getId());
        info.put("id", student.getId());
        info.put("firstName", student.getFirstName());
        info.put("lastName", student.getLastName());
        info.put("archived", student.getArchived());
        info.put("lodging", Boolean.toString(student.getLodging()));
        results.add(info);
    	}
    }
    
    String statusMessage = "";
    Locale locale = jsonRequestContext.getRequest().getLocale();
    if (searchResult.getTotalHitCount() > 0) {
      statusMessage = Messages.getInstance().getText(
          locale,
          "students.searchStudents.searchStatus",
          new Object[] { searchResult.getFirstResult() + 1, searchResult.getLastResult() + 1,
              searchResult.getTotalHitCount() });
    }
    else {
      statusMessage = Messages.getInstance().getText(locale, "students.searchStudents.searchStatusNoMatches");
    }
    jsonRequestContext.addResponseParameter("results", results);
    jsonRequestContext.addResponseParameter("statusMessage", statusMessage);
    jsonRequestContext.addResponseParameter("pages", searchResult.getPages());
    jsonRequestContext.addResponseParameter("page", searchResult.getPage());
  }

  public UserRole[] getAllowedRoles() {
    return new UserRole[] { UserRole.MANAGER, UserRole.ADMINISTRATOR };
  }

}
