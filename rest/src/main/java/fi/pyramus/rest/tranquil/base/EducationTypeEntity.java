package fi.pyramus.rest.tranquil.base;

import fi.tranquil.TranquilModel;
import fi.tranquil.TranquilModelType;

@TranquilModel  (entityClass = fi.pyramus.domainmodel.base.EducationType.class, entityType = TranquilModelType.COMPACT)
public class EducationTypeEntity implements fi.tranquil.TranquilModelEntity {

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

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
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

  public java.util.List<Long> getSubtypes_ids() {
    return subtypes_ids;
  }

  public void setSubtypes_ids(java.util.List<Long> subtypes_ids) {
    this.subtypes_ids = subtypes_ids;
  }

  private Long id;

  private String name;

  private String code;

  private Boolean archived;

  private Long version;

  private java.util.List<Long> subtypes_ids;

  public final static String[] properties = {"id","name","code","archived","version","subtypes"};
}
