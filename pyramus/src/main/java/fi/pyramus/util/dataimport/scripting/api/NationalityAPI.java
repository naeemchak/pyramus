package fi.pyramus.util.dataimport.scripting.api;

import fi.pyramus.dao.DAOFactory;

public class NationalityAPI {
  
  public NationalityAPI(Long loggedUserId) {
    this.loggedUserId = loggedUserId;
  }

  public Long create(String name, String code) {
    return (DAOFactory.getInstance().getNationalityDAO().create(name, code).getId());
  }

  @SuppressWarnings("unused")
  private Long loggedUserId;
}
