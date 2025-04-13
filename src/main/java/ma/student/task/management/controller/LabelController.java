package ma.student.task.management.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.student.task.management.dto.label.LabelCreateRequestDto;
import ma.student.task.management.dto.label.LabelDto;
import ma.student.task.management.service.LabelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/labels")
@RequiredArgsConstructor
public class LabelController {

    private final LabelService labelService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public Page<LabelDto> getLabels(Pageable pageable) {
        return labelService.getLabels(pageable);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public LabelDto createLabel(
            @RequestBody @Valid LabelCreateRequestDto createRequestDto) {
        return labelService.createLabels(createRequestDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public LabelDto updateLabel(
            @PathVariable Long id,
            @RequestBody @Valid LabelCreateRequestDto createRequestDto) {
        return labelService.updateLabel(id, createRequestDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabel(@PathVariable Long id) {
        labelService.deleteLabel(id);
    }

}
