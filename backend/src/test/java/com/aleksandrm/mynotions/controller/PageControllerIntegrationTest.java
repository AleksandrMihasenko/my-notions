package com.aleksandrm.mynotions.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import com.aleksandrm.mynotions.support.IntegrationTestBase;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PageControllerIntegrationTest extends IntegrationTestBase {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Create page: user sends valid data -> returns 201")
    void createPageValidDataReturnsCreated() {
        String token = registerAndGetToken("page_create@email.com", "password");
        Long workspaceId = createWorkspaceAndGetId(token, "Pages Workspace");

        ResponseEntity<Map<String, Object>> response = createPage(token, workspaceId, "Page 1", "Initial content");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Page 1", response.getBody().get("title"));
        assertNotNull(response.getBody().get("id"));
    }

    @Test
    @DisplayName("Get page by id: owner requests existing page -> returns 200")
    void getPageByIdOwnerRequestsExistingPageReturnsOk() {
        String token = registerAndGetToken("page_get@email.com", "password");
        Long workspaceId = createWorkspaceAndGetId(token, "Pages Workspace");
        Long pageId = createPageAndGetId(token, workspaceId, "Page 1", "Initial content");

        ResponseEntity<Map<String, Object>> response = getPageById(token, pageId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Page 1", response.getBody().get("title"));
    }

    @Test
    @DisplayName("Update page: owner sends valid data -> returns 200")
    void updatePageOwnerSendsValidDataReturnsOk() {
        String token = registerAndGetToken("page_update@email.com", "password");
        Long workspaceId = createWorkspaceAndGetId(token, "Pages Workspace");
        Long pageId = createPageAndGetId(token, workspaceId, "Page 1", "Initial content");

        ResponseEntity<Map<String, Object>> response = updatePage(token, pageId, "Updated title", "Updated content");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated title", response.getBody().get("title"));
    }

    @Test
    @DisplayName("Delete page: owner deletes existing page -> returns 204 and page not found later")
    void deletePageOwnerDeletesExistingPageReturnsNoContent() {
        String token = registerAndGetToken("page_delete@email.com", "password");
        Long workspaceId = createWorkspaceAndGetId(token, "Pages Workspace");
        Long pageId = createPageAndGetId(token, workspaceId, "Page 1", "Initial content");

        ResponseEntity<Void> deleteResponse = deletePage(token, pageId);
        ResponseEntity<String> getAfterDeleteResponse = getPageByIdAsString(token, pageId);

        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, getAfterDeleteResponse.getStatusCode());
    }

    @Test
    @DisplayName("Create page: user sends blank title -> returns 400")
    void createPageBlankTitleReturnsBadRequest() {
        String token = registerAndGetToken("validation@email.com", "password");
        Long workspaceId = createWorkspaceAndGetId(token, "Validation Workspace");

        HttpHeaders headers = authJsonHeaders(token);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(
                Map.of("title", "", "content", "content"),
                headers
        );

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                "/api/workspaces/" + workspaceId + "/pages",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().toString().contains("title"));
    }

    @Test
    @DisplayName("Get page by id: another user requests foreign page -> returns 404")
    void anotherUserCannotReadForeignPage() {
        String ownerToken = registerAndGetToken("owner@email.com", "password");
        String anotherToken = registerAndGetToken("another@email.com", "password");
        Long workspaceId = createWorkspaceAndGetId(ownerToken, "Owner Workspace");
        Long pageId = createPageAndGetId(ownerToken, workspaceId, "Private page", "secret");

        ResponseEntity<String> response = getPageByIdAsString(anotherToken, pageId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Get workspace pages: user lists pages after delete -> deleted page not returned")
    void deletedPageIsNotReturnedInWorkspaceList() {
        String token = registerAndGetToken("list@email.com", "password");
        Long workspaceId = createWorkspaceAndGetId(token, "List Workspace");
        Long pageId = createPageAndGetId(token, workspaceId, "To delete", "content");

        ResponseEntity<Void> deleteResponse = deletePage(token, pageId);
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

        HttpEntity<Void> entity = new HttpEntity<>(authHeaders(token));
        ResponseEntity<List<Map<String, Object>>> listResponse = restTemplate.exchange(
                "/api/workspaces/" + workspaceId + "/pages",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        assertEquals(HttpStatus.OK, listResponse.getStatusCode());
        assertNotNull(listResponse.getBody());
        assertEquals(0, listResponse.getBody().size());
    }

    @Test
    @DisplayName("Update page: owner updates non-existent page -> returns 404")
    void updatePageOwnerUpdatesNonExistentPageReturnsNotFound() {
        String token = registerAndGetToken("page_update_404@email.com", "password");
        long nonExistentPageId = 999_999L;

        ResponseEntity<String> response = updatePageAsString(token, nonExistentPageId, "Updated title", "Updated content");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("Page not found"));
    }

    @Test
    @DisplayName("Delete page: owner deletes non-existent page -> returns 404")
    void deletePageOwnerDeletesNonExistentPageReturnsNotFound() {
        String token = registerAndGetToken("page_delete_404@email.com", "password");
        long nonExistentPageId = 999_999L;

        ResponseEntity<String> response = deletePageAsString(token, nonExistentPageId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("Page not found"));
    }

    private Long createWorkspaceAndGetId(String token, String workspaceName) {
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(
                Map.of("name", workspaceName),
                authJsonHeaders(token)
        );

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                "/api/workspaces",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        return ((Number) response.getBody().get("id")).longValue();
    }

    private Long createPageAndGetId(String token, Long workspaceId, String title, String content) {
        ResponseEntity<Map<String, Object>> response = createPage(token, workspaceId, title, content);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        return ((Number) response.getBody().get("id")).longValue();
    }

    private ResponseEntity<Map<String, Object>> createPage(String token, Long workspaceId, String title, String content) {
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(
                Map.of("title", title, "content", content),
                authJsonHeaders(token)
        );

        return restTemplate.exchange(
                "/api/workspaces/" + workspaceId + "/pages",
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<>() {}
        );
    }

    private ResponseEntity<Map<String, Object>> getPageById(String token, Long pageId) {
        HttpEntity<Void> entity = new HttpEntity<>(authHeaders(token));
        return restTemplate.exchange(
                "/api/pages/" + pageId,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );
    }

    private ResponseEntity<String> getPageByIdAsString(String token, Long pageId) {
        HttpEntity<Void> entity = new HttpEntity<>(authHeaders(token));
        return restTemplate.exchange(
                "/api/pages/" + pageId,
                HttpMethod.GET,
                entity,
                String.class
        );
    }

    private ResponseEntity<Map<String, Object>> updatePage(String token, Long pageId, String title, String content) {
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(
                Map.of("title", title, "content", content),
                authJsonHeaders(token)
        );

        return restTemplate.exchange(
                "/api/pages/" + pageId,
                HttpMethod.PUT,
                entity,
                new ParameterizedTypeReference<>() {}
        );
    }

    private ResponseEntity<String> updatePageAsString(String token, Long pageId, String title, String content) {
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(
                Map.of("title", title, "content", content),
                authJsonHeaders(token)
        );

        return restTemplate.exchange(
                "/api/pages/" + pageId,
                HttpMethod.PUT,
                entity,
                String.class
        );
    }

    private ResponseEntity<Void> deletePage(String token, Long pageId) {
        HttpEntity<Void> entity = new HttpEntity<>(authHeaders(token));
        return restTemplate.exchange(
                "/api/pages/" + pageId,
                HttpMethod.DELETE,
                entity,
                Void.class
        );
    }

    private ResponseEntity<String> deletePageAsString(String token, Long pageId) {
        HttpEntity<Void> entity = new HttpEntity<>(authHeaders(token));
        return restTemplate.exchange(
                "/api/pages/" + pageId,
                HttpMethod.DELETE,
                entity,
                String.class
        );
    }
}
