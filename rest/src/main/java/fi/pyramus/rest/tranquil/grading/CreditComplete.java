package fi.pyramus.rest.tranquil.grading;

import fi.tranquil.TranquilModel;
import fi.tranquil.TranquilModelType;
import fi.tranquil.TranquilModelEntity;

@TranquilModel (entityClass = fi.pyramus.domainmodel.grading.Credit.class, entityType = TranquilModelType.COMPLETE)
public class CreditComplete extends CreditBase {

  public TranquilModelEntity getGrade() {
    return grade;
  }

  public void setGrade(TranquilModelEntity grade) {
    this.grade = grade;
  }

  public TranquilModelEntity getAssessingUser() {
    return assessingUser;
  }

  public void setAssessingUser(TranquilModelEntity assessingUser) {
    this.assessingUser = assessingUser;
  }

  private TranquilModelEntity grade;

  private TranquilModelEntity assessingUser;

  public final static String[] properties = {"grade","assessingUser"};
}