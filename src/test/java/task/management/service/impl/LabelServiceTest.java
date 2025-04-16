package task.management.service.impl;

import task.management.dto.label.LabelCreateRequestDto;
import task.management.dto.label.LabelDto;
import task.management.exception.EntityNotFoundException;
import task.management.mapper.LabelMapper;
import task.management.model.Label;
import task.management.model.Task;
import task.management.repository.LabelRepository;
import task.management.repository.TaskRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class LabelServiceTest {

    @Mock
    private LabelRepository labelRepository;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private LabelMapper labelMapper;

    @InjectMocks
    private LabelServiceIml labelService;

    private Task task;
    private Label label;
    private LabelDto labelDto;
    private LabelCreateRequestDto createRequestDto;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(1L);

        label = new Label();
        label.setId(1L);

        labelDto = new LabelDto();
        labelDto.setId(1L);

        createRequestDto = new LabelCreateRequestDto();
        createRequestDto.setTaskId(1L);
        createRequestDto.setName("Urgent");
        createRequestDto.setColor(Label.Color.RED);
    }

    @Test
    @DisplayName("Get labels should return page of labels")
    void getLabelsTest_getLabels_ok() {
        Pageable pageable = PageRequest.of(0, 10);
        when(labelRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(label)));
        when(labelMapper.toDto(label)).thenReturn(labelDto);

        Page<LabelDto> result = labelService.getLabels(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(labelDto, result.getContent().get(0));
    }

    @Test
    @DisplayName("Create label should work correctly")
    void createLabelTest_createLabel_ok() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(labelMapper.toModel(createRequestDto)).thenReturn(label);
        when(labelMapper.toDto(label)).thenReturn(labelDto);

        LabelDto result = labelService.createLabels(createRequestDto);

        assertEquals(labelDto, result);
        verify(taskRepository).flush();
    }

    @Test
    @DisplayName("Create label should throw exception if task not found")
    void createLabelTest_createLabel_notOk() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> labelService.createLabels(createRequestDto));

        assertTrue(exception.getMessage().contains("There is no task with id"));
    }

    @Test
    @DisplayName("Update label should work correctly")
    void updateLabelTest_updateLabel_ok() {
        when(labelRepository.findById(1L)).thenReturn(Optional.of(label));
        when(labelRepository.save(label)).thenReturn(label);
        when(labelMapper.toDto(label)).thenReturn(labelDto);

        LabelDto result = labelService.updateLabel(1L, createRequestDto);

        assertEquals(labelDto, result);
        verify(labelMapper).updateLabel(label, createRequestDto);
    }

    @Test
    @DisplayName("Update label should throw exception if label not found")
    void updateLabelTest_updateLabel_notOk() {
        when(labelRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> labelService.updateLabel(1L, createRequestDto));

        assertTrue(exception.getMessage().contains("There is no labels with id"));
    }

    @Test
    @DisplayName("Delete label should call repository")
    void deleteLabelTest_deleteLabel_ok() {
        labelService.deleteLabel(1L);
        verify(labelRepository).deleteById(1L);
    }
}
