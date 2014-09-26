package fi.pyramus.rest.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import fi.pyramus.dao.users.UserVariableDAO;
import fi.pyramus.dao.users.UserVariableKeyDAO;
import fi.pyramus.domainmodel.base.VariableType;
import fi.pyramus.domainmodel.users.User;
import fi.pyramus.domainmodel.users.UserVariable;
import fi.pyramus.domainmodel.users.UserVariableKey;

@Dependent
@Stateless
public class UserController {
  
  @Inject
  private UserVariableDAO userVariableDAO;

  @Inject
  private UserVariableKeyDAO userVariableKeyDAO;
  
  /* Variables */

  public synchronized User updateUserVariables(User user, Map<String, String> variables) {
    Set<String> newKeys = new HashSet<>(variables.keySet());
    Set<String> oldKeys = new HashSet<>();
    Set<String> updateKeys = new HashSet<>();
    
    List<UserVariable> userVariables = userVariableDAO.listByUser(user);
    
    for (UserVariable variable : userVariables) {
      oldKeys.add(variable.getKey().getVariableKey());
    }

    for (String oldKey : oldKeys) {
      if (!newKeys.contains(oldKey)) {
        UserVariableKey key = findUserVariableKeyByVariableKey(oldKey);
        UserVariable userVariable = findUserVariableByStudentAndKey(user, key);
        deleteUserVariable(userVariable);
      } else {
        updateKeys.add(oldKey);
      }
      
      newKeys.remove(oldKey);
    }
    
    for (String newKey : newKeys) {
      String value = variables.get(newKey);
      UserVariableKey key = findUserVariableKeyByVariableKey(newKey);
      createUserVariable(user, key, value);
    }
    
    for (String updateKey : updateKeys) {
      String value = variables.get(updateKey);
      UserVariableKey key = findUserVariableKeyByVariableKey(updateKey);
      UserVariable userVariable = findUserVariableByStudentAndKey(user, key);
      updateUserVariable(userVariable, value);
    }
    
    return user;
  }
  
  public UserVariable createUserVariable(User user, UserVariableKey key, String value) {
    return userVariableDAO.create(user, key, value);
  }

  public UserVariable findUserVariableById(Long id) {
    UserVariable userVariable = userVariableDAO.findById(id);
    return userVariable;
  }

  public UserVariable findUserVariableByStudentAndKey(User user, UserVariableKey key) {
    return userVariableDAO.findByUserAndVariableKey(user, key);
  }

  public void deleteUserVariable(UserVariable variable) {
    userVariableDAO.delete(variable);
  }

  public UserVariable updateUserVariable(UserVariable userVariable, String value) {
    return userVariableDAO.updateValue(userVariable, value);
  }
  
  public UserVariableKey findUserVariableKeyByVariableKey(String variableKey) {
    return userVariableKeyDAO.findByVariableKey(variableKey);
  }

  public List<UserVariable> listUserVariablesByUser(User user) {
    return userVariableDAO.listByUser(user);
  }
  
  /* Variable Keys */

  public UserVariableKey createUserVariableKey(String key, String name, VariableType variableType, Boolean userEditable) {
    return userVariableKeyDAO.create(key, name, variableType, userEditable);
  }

  public List<UserVariableKey> listUserVariableKeys() {
    return userVariableKeyDAO.listAll();
  }

  public UserVariableKey updateUserVariableKey(UserVariableKey userVariableKey, String name, VariableType variableType, Boolean userEditable) {
    userVariableKeyDAO.updateVariableName(userVariableKey, name);
    userVariableKeyDAO.updateVariableType(userVariableKey, variableType);
    userVariableKeyDAO.updateUserEditable(userVariableKey, userEditable);
    return userVariableKey;
  }

  public void deleteUserVariableKey(UserVariableKey userVariableKey) {
    userVariableKeyDAO.delete(userVariableKey);
  }
}
