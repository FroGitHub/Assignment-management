package ma.student.task.management.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import ma.student.task.management.dto.project.ProjectCreateRequestDto;
import ma.student.task.management.dto.project.ProjectDto;
import ma.student.task.management.model.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "test", roles = {"USER", "ADMIN"})
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Get project")
    @Sql(scripts = "classpath:database/project/create-project.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/project/delete-project-with-id-1.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getProjectTest_getProjectRequest_responseOk() throws Exception {
        MvcResult result = mockMvc.perform(get("/projects/1"))
                .andExpect(status().isOk())
                .andReturn();

        ProjectDto actual = objectMapper.readValue(
                result.getResponse().getContentAsString(), ProjectDto.class);

        assertProject(actual,1,
                "Project Alpha",
                "This is a sample project description.",
                "2025-04-01",
                "2025-06-30",
                "IN_PROGRESS");
    }

    @Test
    @DisplayName("Create project")
    @Sql(scripts = "classpath:database/project/delete-project-with-id-1.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void createProjectTest_postRequest_responseCreated() throws Exception {
        ProjectCreateRequestDto expected = new ProjectCreateRequestDto();
        expected.setName("New Project");
        expected.setDescription("Description of new project");
        expected.setStartDate(LocalDate.of(2025, 7, 1));
        expected.setEndDate(LocalDate.of(2025, 9, 30));
        expected.setStatus(Status.IN_PROGRESS);

        String json = objectMapper.writeValueAsString(expected);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/projects")
                        .content(json)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        ProjectDto actual = objectMapper.readValue(result.getResponse().getContentAsString(), ProjectDto.class);
        assertProject(actual,1,
                expected.getName(),
                expected.getDescription(),
                expected.getStartDate().toString(),
                expected.getEndDate().toString(),
                expected.getStatus().toString());
    }

    @Test
    @DisplayName("Update project")
    @Sql(scripts = "classpath:database/project/create-project.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/project/delete-project-with-id-1.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateProjectTest_putRequest_responseOk() throws Exception {
        ProjectCreateRequestDto expected = new ProjectCreateRequestDto();
        expected.setName("Updated Project");
        expected.setDescription("Updated description");
        expected.setStartDate(LocalDate.of(2025, 5, 1));
        expected.setEndDate(LocalDate.of(2025, 8, 1));
        expected.setStatus(Status.COMPLETED);

        String json = objectMapper.writeValueAsString(expected);

        MvcResult result = mockMvc.perform(put("/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn();

        ProjectDto actual = objectMapper.readValue(result.getResponse().getContentAsString(), ProjectDto.class);

        assertProject(actual,1,
                expected.getName(),
                expected.getDescription(),
                expected.getStartDate().toString(),
                expected.getEndDate().toString(),
                expected.getStatus().toString());
    }

    @Test
    @DisplayName("Delete project")
    @Sql(scripts = "classpath:database/project/create-project.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deleteProjectTest_deleteRequest_responseNoContent() throws Exception {
        mockMvc.perform(delete("/projects/1"))
                .andExpect(status().isNoContent());
    }

    private void assertProject(ProjectDto projectDto,
                               int id, String name,
                               String description,
                               String startDate,
                               String endDate,
                               String status) {
        assertEquals(projectDto.id(), id);
        assertEquals(projectDto.name(), name);
        assertEquals(projectDto.description(), description);
        assertEquals(projectDto.startDate().toString(), startDate);
        assertEquals(projectDto.endDate().toString(), endDate);
        assertEquals(projectDto.status().toString(), status);
    }
}
