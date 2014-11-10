package fi.pyramus.webhooks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;

@SessionScoped
@Stateful
public class WebhookSessionData {
  
  @PostConstruct
  public void init() {
    updatedStaffMemberIds = new ArrayList<>();
    updatedStudentIds = new ArrayList<>();
    updatedCourseIds = new ArrayList<>();
    updatedCourseStudentIds = new ArrayList<>();
    courseStudentStudentIdMap = new HashMap<>();
    courseStudentCourseIdMap = new HashMap<>();
  }
  
  /* StaffMemberIds */
  
  public void addUpdatedStaffMemberId(Long updatedStaffMemberId) {
    if (!updatedStaffMemberIds.contains(updatedStaffMemberId)) {
      updatedStaffMemberIds.add(updatedStaffMemberId);
    }
  }
  
  public List<Long> retrieveUpdatedStaffMemberIds() {
    List<Long> result = new ArrayList<Long>(updatedStaffMemberIds);
    updatedStaffMemberIds.clear();
    return result;
  }

  public void clearUpdatedStaffMemberIds() {
    updatedStaffMemberIds.clear();
  }
  
  /* StudentIds */
  
  public void addUpdatedStudentId(Long updatedStudentId) {
    if (!updatedStudentIds.contains(updatedStudentId)) {
      updatedStudentIds.add(updatedStudentId);
    }
  }
  
  public List<Long> retrieveUpdatedStudentIds() {
    List<Long> result = new ArrayList<Long>(updatedStudentIds);
    updatedStudentIds.clear();
    return result;
  }

  public void clearUpdatedStudentIds() {
    updatedStudentIds.clear();
  }
  
  /* CourseIds */
  
  public void addUpdatedCourseId(Long updatedCourseId) {
    if (!updatedCourseIds.contains(updatedCourseId)) {
      updatedCourseIds.add(updatedCourseId);
    }
  }
  
  public List<Long> retrieveUpdatedCourseIds() {
    List<Long> result = new ArrayList<Long>(updatedCourseIds);
    updatedCourseIds.clear();
    return result;
  }

  public void clearUpdatedCourseIds() {
    updatedCourseIds.clear();
  }
  
  /* Course Students */
  
  public void addUpdatedCourseStudent(Long updatedCourseStudentId, Long courseId, Long studentId) {
    if (!updatedCourseStudentIds.contains(updatedCourseStudentId)) {
      updatedCourseStudentIds.add(updatedCourseStudentId);
    }
    
    courseStudentStudentIdMap.put(updatedCourseStudentId, studentId);
    courseStudentCourseIdMap.put(updatedCourseStudentId, courseId);
  }
  
  public List<Long> retrieveUpdatedCourseStudentIds() {
    List<Long> result = new ArrayList<Long>(updatedCourseStudentIds);
    updatedCourseStudentIds.clear();
    return result;
  }

  public void clearUpdatedCourseStudentIds() {
    updatedCourseStudentIds.clear();
    courseStudentStudentIdMap.clear();
    courseStudentCourseIdMap.clear();
  }

  public Long getCourseStudentCourseId(Long courseStudentId) {
    return courseStudentCourseIdMap.get(courseStudentId);
  }

  public Long getCourseStudentStudentId(Long courseStudentId) {
    return courseStudentStudentIdMap.get(courseStudentId);
  }
  
  private List<Long> updatedStaffMemberIds;
  private List<Long> updatedStudentIds;
  private List<Long> updatedCourseIds;
  private List<Long> updatedCourseStudentIds;
  private Map<Long, Long> courseStudentStudentIdMap;
  private Map<Long, Long> courseStudentCourseIdMap;
}