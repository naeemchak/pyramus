package fi.pyramus.rest.tranquil.grading;

import fi.tranquil.TranquilModel;
import fi.tranquil.TranquilModelType;

@TranquilModel  (entityClass = fi.pyramus.domainmodel.grading.GradingScale.class, entityType = TranquilModelType.COMPACT)
public class GradingScaleEntity implements fi.tranquil.TranquilModelEntity {

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Boolean getArchived() {
    return archived;
  }

  public void setArchived(Boolean archived) {
    this.archived = archived;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public java.util.List<Long> getGrades_ids() {
    return grades_ids;
  }

  public void setGrades_ids(java.util.List<Long> grades_ids) {
    this.grades_ids = grades_ids;
  }

  private Long id;

  private String name;

  private String description;

  private Boolean archived;

  private Long version;

  private java.util.List<Long> grades_ids;

  public final static String[] properties = {"id","name","description","archived","version","grades"};
}
