package task.management.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import task.management.dto.label.LabelCreateRequestDto;
import task.management.dto.label.LabelDto;
import task.management.exception.EntityNotFoundException;
import task.management.mapper.LabelMapper;
import task.management.model.Label;
import task.management.model.Task;
import task.management.repository.LabelRepository;
import task.management.repository.TaskRepository;
import task.management.service.LabelService;

@Service
@RequiredArgsConstructor
@Transactional
public class LabelServiceIml implements LabelService {

    private final LabelRepository labelRepository;
    private final TaskRepository taskRepository;
    private final LabelMapper labelMapper;

    @Override
    public Page<LabelDto> getLabels(Pageable pageable) {
        return labelRepository.findAll(pageable).map(labelMapper::toDto);
    }

    @Override
    public LabelDto createLabels(LabelCreateRequestDto createRequestDto) {
        Task task = taskRepository.findById(createRequestDto.getTaskId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "There is no task with id: " + createRequestDto.getTaskId()));
        Label label = labelMapper.toModel(createRequestDto);
        task.setLabel(label);
        taskRepository.flush();
        return labelMapper.toDto(label);
    }

    @Override
    public LabelDto updateLabel(Long id, LabelCreateRequestDto createRequestDto) {
        Label label = labelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "There is no labels with id: " + id));

        labelMapper.updateLabel(label, createRequestDto);

        return labelMapper.toDto(labelRepository.save(label));
    }

    @Override
    public void deleteLabel(Long id) {
        labelRepository.deleteById(id);
    }

}
