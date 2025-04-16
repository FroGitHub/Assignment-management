package task.management.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*; // <- оце головне

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import task.management.dto.label.LabelCreateRequestDto;
import task.management.dto.label.LabelDto;
import task.management.model.Label;
import task.management.service.LabelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "ADMIN")
class LabelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LabelService labelService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Create label - success")
    void createLabel_success() throws Exception {
        LabelCreateRequestDto request = new LabelCreateRequestDto();
        request.setTaskId(1L);
        request.setName("Urgent");
        request.setColor(Label.Color.RED);

        LabelDto response = new LabelDto();
        response.setId(1L);
        response.setName("Urgent");
        response.setColor(Label.Color.RED);

        when(labelService.createLabels(any())).thenReturn(response);

        mockMvc.perform(post("/labels")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Urgent"))
                .andExpect(jsonPath("$.color").value("RED"));
    }

    @Test
    @DisplayName("Update label - success")
    void updateLabel_success() throws Exception {
        LabelCreateRequestDto request = new LabelCreateRequestDto();
        request.setTaskId(1L);
        request.setName("Updated");
        request.setColor(Label.Color.GREEN);

        LabelDto response = new LabelDto();
        response.setId(1L);
        response.setName("Updated");
        response.setColor(Label.Color.GREEN);

        when(labelService.updateLabel(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/labels/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"))
                .andExpect(jsonPath("$.color").value("GREEN"));
    }

    @Test
    @DisplayName("Get labels - success")
    @WithMockUser(roles = "USER")
        // для GET-ендпоінту
    void getLabels_success() throws Exception {
        LabelDto label = new LabelDto();
        label.setId(1L);
        label.setName("Bug");
        label.setColor(Label.Color.BLUE);

        Page<LabelDto> page = new PageImpl<>(List.of(label));

        when(labelService.getLabels(any())).thenReturn(page);

        mockMvc.perform(get("/labels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name")
                        .value("Bug"));
    }

    @Test
    @DisplayName("Delete label - success")
    void deleteLabel_success() throws Exception {
        doNothing().when(labelService).deleteLabel(1L);

        mockMvc.perform(delete("/labels/1").with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Create label - validation fail (short name)")
    void createLabel_validationFail_shortName() throws Exception {
        LabelCreateRequestDto request = new LabelCreateRequestDto();
        request.setTaskId(1L);
        request.setName("ab");
        request.setColor(Label.Color.GREEN);

        mockMvc.perform(post("/labels")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}

