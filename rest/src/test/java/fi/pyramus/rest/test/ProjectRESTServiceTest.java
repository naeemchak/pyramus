package fi.pyramus.rest.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;

import fi.pyramus.rest.ProjectRESTService;
import fi.pyramus.rest.controller.ProjectController;
import fi.pyramus.rest.tranquil.base.TagEntity;
import fi.pyramus.rest.tranquil.projects.ProjectEntity;

@RunWith(Arquillian.class)
public class ProjectRESTServiceTest extends RestfulServiceTest {

  public ProjectRESTServiceTest() throws URISyntaxException {
    super();
  }

  @Deployment
  @OverProtocol("Servlet 3.0")
  public static Archive<?> createTestArchive() {
    Archive<?> archive = createArchive(ProjectController.class, ProjectRESTService.class);
    return archive;
  }

  @Test
  public void testCreateProject() throws ClientProtocolException, IOException {
    StringEntity str = new StringEntity("{\"name\":\"PyramusREST\", \"description\":\"Pyramus RESTService development\"}");

    HttpResponse response = doPostRequest("/projects/projects", str);

    assertEquals(200, response.getStatusLine().getStatusCode());
    HttpEntity entity = response.getEntity();
    try {
      assertNotNull(entity);
      assertEquals("application/json", entity.getContentType().getValue());
      ProjectEntity projectEntity = unserializeEntity(ProjectEntity.class, EntityUtils.toString(entity));
      assertNotNull(projectEntity);
      assertEquals("PyramusREST", projectEntity.getName());
      assertEquals("Pyramus RESTService development", projectEntity.getDescription());
    } finally {
      EntityUtils.consume(entity);
    }
  }
  
  @Test
  public void testCreateTag() throws ClientProtocolException, IOException {
    StringEntity str = new StringEntity("{\"text\":\"Test environment\"}");

    HttpResponse response = doPostRequest("/projects/projects/1/tags", str);

    assertEquals(200, response.getStatusLine().getStatusCode());
    HttpEntity entity = response.getEntity();
    try {
      assertNotNull(entity);
      assertEquals("application/json", entity.getContentType().getValue());
      TagEntity tagEntity = unserializeEntity(TagEntity.class, EntityUtils.toString(entity));
      assertNotNull(tagEntity);
      assertEquals("Test environment", tagEntity.getText());
    } finally {
      EntityUtils.consume(entity);
    }
  }

  @Test
  public void testFindProjects() throws ClientProtocolException, IOException {
    HttpResponse response = doGetRequest("/projects/projects");

    assertEquals(200, response.getStatusLine().getStatusCode());

    HttpEntity entity = response.getEntity();
    try {
      assertNotNull(entity);
      assertEquals("application/json", entity.getContentType().getValue());
      ProjectEntity[] projectEntities = unserializeEntity(ProjectEntity[].class, EntityUtils.toString(entity));
      assertNotNull(projectEntities);
      assertEquals(1, projectEntities.length);
      assertEquals("PyramusREST", projectEntities[0].getName());
      assertEquals("Pyramus RESTService development", projectEntities[0].getDescription());
    } finally {
      EntityUtils.consume(entity);
    }
  }

  @Test
  public void testFindProjectById() throws ClientProtocolException, IOException {
    HttpResponse response = doGetRequest("/projects/projects/1");

    assertEquals(200, response.getStatusLine().getStatusCode());

    HttpEntity entity = response.getEntity();
    try {
      assertNotNull(entity);
      assertEquals("application/json", entity.getContentType().getValue());
      ProjectEntity projectEntity = unserializeEntity(ProjectEntity.class, EntityUtils.toString(entity));
      assertNotNull(projectEntity);
      assertEquals((Long) 1l, projectEntity.getId());
      assertEquals("PyramusREST", projectEntity.getName());
      assertEquals("Pyramus RESTService development", projectEntity.getDescription());
    } finally {
      EntityUtils.consume(entity);
    }
  }
  
  @Test
  public void testSearchProjects() throws ClientProtocolException, IOException {
    HttpResponse response = doGetRequest("/projects/projects?name=PyramusREST&tags=Test%20environment");

    assertEquals(200, response.getStatusLine().getStatusCode());

    HttpEntity entity = response.getEntity();
    try {
      assertNotNull(entity);
      assertEquals("application/json", entity.getContentType().getValue());
      ProjectEntity[] projectEntities = unserializeEntity(ProjectEntity[].class, EntityUtils.toString(entity));
      assertNotNull(projectEntities);
      assertEquals(1, projectEntities.length);
      assertEquals("PyramusREST", projectEntities[0].getName());
      assertEquals("Pyramus RESTService development", projectEntities[0].getDescription());
    } finally {
      EntityUtils.consume(entity);
    }
  }
  
  @Test
  public void testFindTags() throws ClientProtocolException, IOException {
    HttpResponse response = doGetRequest("/projects/projects/1/tags");

    assertEquals(200, response.getStatusLine().getStatusCode());

    HttpEntity entity = response.getEntity();
    try {
      assertNotNull(entity);
      assertEquals("application/json", entity.getContentType().getValue());
      TagEntity[] tagEntities = unserializeEntity(TagEntity[].class, EntityUtils.toString(entity));
      assertNotNull(tagEntities);
      assertEquals(1, tagEntities.length);
      assertEquals("Test environment", tagEntities[0].getText());
    } finally {
      EntityUtils.consume(entity);
    }
  }

  @Test
  public void testUpdateProject() throws ClientProtocolException, IOException {

    String path = "/projects/projects/1";

    StringEntity str = new StringEntity("{\"name\":\"Updating project\",\"description\":\"Testing if project update works\"}");

    HttpResponse response = doPutRequest(path, str);

    assertEquals(200, response.getStatusLine().getStatusCode());

    HttpEntity entity = response.getEntity();
    try {
      assertNotNull(entity);
      assertEquals("application/json", entity.getContentType().getValue());
      ProjectEntity projectEntity = unserializeEntity(ProjectEntity.class, EntityUtils.toString(entity));
      assertNotNull(projectEntity);
      assertEquals((Long) 1l, projectEntity.getId());
      assertEquals("Updating project", projectEntity.getName());
      assertEquals("Testing if project update works", projectEntity.getDescription());
    } finally {
      EntityUtils.consume(entity);
    }
  }

  @Test
  public void testArchiveProject() throws ClientProtocolException, IOException {
    String path = "/projects/projects/1";

    HttpResponse response = doDeleteRequest(path);

    assertEquals(200, response.getStatusLine().getStatusCode());

    HttpEntity entity = response.getEntity();
    try {
      assertNotNull(entity);
      assertEquals("application/json", entity.getContentType().getValue());
      ProjectEntity projectEntity = unserializeEntity(ProjectEntity.class, EntityUtils.toString(entity));
      assertNotNull(projectEntity);
      assertEquals((Long) 1l, projectEntity.getId());
      assertEquals(true, projectEntity.getArchived());
    } finally {
      EntityUtils.consume(entity);
    }
  }

  @Test
  public void testUnarchiveProject() throws ClientProtocolException, IOException {
    String path = "/projects/projects/1";
    StringEntity str = new StringEntity("{\"archived\":true}");

    HttpResponse response = doPutRequest(path, str);

    assertEquals(200, response.getStatusLine().getStatusCode());

    HttpEntity entity = response.getEntity();
    try {
      assertNotNull(entity);
      assertEquals("application/json", entity.getContentType().getValue());
      ProjectEntity projectEntity = unserializeEntity(ProjectEntity.class, EntityUtils.toString(entity));
      assertNotNull(projectEntity);
      assertEquals((Long) 1l, projectEntity.getId());
      assertEquals(false, projectEntity.getArchived());
    } finally {
      EntityUtils.consume(entity);
    }
  }
  
  @Test
  public void testFindUnarchivedProjects() throws ClientProtocolException, IOException {
    HttpResponse response = doGetRequest("/projects/projects?filterArchived=true");

    assertEquals(200, response.getStatusLine().getStatusCode());

    HttpEntity entity = response.getEntity();
    try {
      assertNotNull(entity);
      assertEquals("application/json", entity.getContentType().getValue());
      ProjectEntity[] projectEntities = unserializeEntity(ProjectEntity[].class, EntityUtils.toString(entity));
      assertNotNull(projectEntities);
      assertEquals(1, projectEntities.length);
      assertEquals("Updating project", projectEntities[0].getName());
      assertEquals("Testing if project update works", projectEntities[0].getDescription());
    } finally {
      EntityUtils.consume(entity);
    }
  }
  
  @Test
  public void testDeleteProjectTag() throws ClientProtocolException, IOException {
    String path = "/projects/projects/1/tags/1";

    HttpResponse response = doDeleteRequest(path);

    assertEquals(200, response.getStatusLine().getStatusCode());

    HttpEntity entity = response.getEntity();
    try {
      assertNotNull(entity);
      assertEquals("application/json", entity.getContentType().getValue());
      ProjectEntity projectEntity = unserializeEntity(ProjectEntity.class, EntityUtils.toString(entity));
      assertNotNull(projectEntity);
      assertEquals((Long) 1l, projectEntity.getId());
      assertEquals(0, projectEntity.getTags_ids().size());
    } finally {
      EntityUtils.consume(entity);
    }
  }
}
