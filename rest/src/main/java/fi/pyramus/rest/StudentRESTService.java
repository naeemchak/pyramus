package fi.pyramus.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;

import fi.pyramus.domainmodel.students.AbstractStudent;
import fi.pyramus.domainmodel.students.Sex;
import fi.pyramus.domainmodel.students.StudentStudyEndReason;
import fi.pyramus.rest.controller.AbstractStudentController;
import fi.pyramus.rest.controller.StudentSubResourceController;
import fi.pyramus.rest.tranquil.students.AbstractStudentEntity;
import fi.pyramus.rest.tranquil.students.StudentStudyEndReasonEntity;
import fi.tranquil.TranquilityBuilderFactory;

@Path("/students")
@Produces("application/json")
@Consumes("application/json")
@Stateful
@RequestScoped
public class StudentRESTService extends AbstractRESTService {
  @Inject
  private TranquilityBuilderFactory tranquilityBuilderFactory;
  @Inject
  AbstractStudentController abstractStudentController;
  @Inject
  StudentSubResourceController studentSubController;
  
  @Path("/endReasons")
  @POST
  public Response createStudentStudyEndReason(StudentStudyEndReasonEntity endReasonEntity) {
      Long parentId = endReasonEntity.getParentReason_id();
      if (parentId != null) {
        StudentStudyEndReason parentReason = studentSubController.findStudentStudyEndReasonById(parentId);
        String name = endReasonEntity.getName();
        if (parentReason != null && !StringUtils.isBlank(name)) {
          return Response.ok()
              .entity(tranqualise(studentSubController.createStudentStudyEndReason(parentReason, name)))
              .build();
        } else {
          return Response.status(500).build();
        }
      } else {
        String name = endReasonEntity.getName();
        if (!StringUtils.isBlank(name)) {
          return Response.ok()
              .entity(tranqualise(studentSubController.createStudentStudyEndReason(null, name)))
              .build();
        } else {
          return Response.status(500).build();
        }
      }
  }
  
  @Path("/abstractStudents")
  @POST
  public Response createAbstractStudent(AbstractStudentEntity abstractStudentEntity) {
    Date birthday = abstractStudentEntity.getBirthday();
    String socialSecurityNumber = abstractStudentEntity.getSocialSecurityNumber();
    Sex sex = abstractStudentEntity.getSex();
    String basicInfo = abstractStudentEntity.getBasicInfo();
    boolean secureInfo = abstractStudentEntity.getSecureInfo();
    
    if (birthday != null && !StringUtils.isBlank(socialSecurityNumber) && sex != null && !StringUtils.isBlank(basicInfo)) {
      return Response.ok()
          .entity(tranqualise(abstractStudentController.createAbstractStudent(birthday, socialSecurityNumber, sex, basicInfo, secureInfo)))
          .build();
    } else  {
      return Response.status(500).build();
    }
  }
  
  @Path("/endReasons")
  @GET
  public Response findStudentStudyEndReasons(@DefaultValue("false") @QueryParam("filterArchived") boolean filterArchived) {
    List<StudentStudyEndReason> endReasons;
    if (filterArchived) {
      endReasons = studentSubController.findUnarchivedStudentStudyEndReasons();
    } else {
      endReasons = studentSubController.findStudentStudyEndReasons();
    }
    if (!endReasons.isEmpty()) {
      return Response.ok()
          .entity(tranqualise(endReasons))
          .build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }

  @Path("/endReasons/{ID:[0-9]*}")
  @GET
  public Response findStudentStudyEndReasonById(@PathParam("ID") Long id) {
    StudentStudyEndReason endReason = studentSubController.findStudentStudyEndReasonById(id);
    if (endReason != null) {
      return Response.ok()
          .entity(tranqualise(endReason))
          .build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }
  
  @Path("/abstractStudents")
  @GET
  public Response findAbstractStudents(@DefaultValue("false") @QueryParam("filterArchived") boolean filterArchived) {
    List<AbstractStudent> abstractStudents;
    if (filterArchived) {
      abstractStudents = abstractStudentController.findUnarchivedAbstractStudents();
    } else {
      abstractStudents = abstractStudentController.findAbstractStudents();
    }
    if (!abstractStudents.isEmpty()) {
      return Response.ok()
          .entity(tranqualise(abstractStudents))
          .build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }
  
  @Path("/abstractStudents/{ID:[0-9]*}")
  @GET
  public Response findAbstractStudentById(@PathParam("ID") Long id) {
    AbstractStudent abstractStudent = abstractStudentController.findAbstractStudentById(id);
    if (abstractStudent != null) {
      return Response.ok()
          .entity(tranqualise(abstractStudent))
          .build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }
  
  @Path("/endReasons/{ID:[0-9]*}")
  @PUT
  public Response updateAbstractStudent(@PathParam("ID") Long id, StudentStudyEndReasonEntity endReasonEntity) {
    StudentStudyEndReason endReason = studentSubController.findStudentStudyEndReasonById(id);
    if (endReason != null) {
      String name = endReasonEntity.getName();
      Long parentReasonId = endReasonEntity.getParentReason_id();
      if (!StringUtils.isBlank(name)) {
        return Response.ok()
            .entity(tranqualise(studentSubController.updateStudentStudyEndReason(endReason, name)))
            .build();
      } else if (parentReasonId != null) {
        StudentStudyEndReason parentReason = studentSubController.findStudentStudyEndReasonById(parentReasonId);
        return Response.ok()
            .entity(tranqualise(studentSubController.updateStudentStudyEndReasonParent(endReason, parentReason)))
            .build();
      } else {
        return Response.status(500).build();
      }
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }
  
  @Path("/abstractStudents/{ID:[0-9]*}")
  @PUT
  public Response updateAbstractStudent(@PathParam("ID") Long id, AbstractStudentEntity abstractStudentEntity) {
    AbstractStudent abstractStudent = abstractStudentController.findAbstractStudentById(id);
    Date birthday = abstractStudentEntity.getBirthday();
    String socialSecurityNumber = abstractStudentEntity.getSocialSecurityNumber();
    Sex sex = abstractStudentEntity.getSex();
    String basicInfo = abstractStudentEntity.getBasicInfo();
    boolean secureInfo = abstractStudentEntity.getSecureInfo();
    
    if (abstractStudent != null &&birthday != null && !StringUtils.isBlank(socialSecurityNumber) && sex != null && !StringUtils.isBlank(basicInfo)) {
      return Response.ok()
          .entity(tranqualise(abstractStudentController.updateAbstractStudent(abstractStudent, birthday, socialSecurityNumber, sex, basicInfo, secureInfo)))
          .build();
    } else  {
      return Response.status(500).build();
    }
  }
  
  @Override
  protected TranquilityBuilderFactory getTranquilityBuilderFactory() {
    return tranquilityBuilderFactory;
  }

}