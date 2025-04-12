package ma.student.task.management.controller;

import lombok.RequiredArgsConstructor;
import ma.student.task.management.dto.attachment.AttachmentDto;
import ma.student.task.management.service.AttachmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/attachments")
@RequiredArgsConstructor
public class AttachmentController {
    private final AttachmentService attachmentService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('USER')")
    public AttachmentDto upload(@RequestParam(value = "file") MultipartFile file,
                                @RequestParam("taskId") Long taskId) throws Exception {
        return attachmentService.uploadAttachment(file, taskId);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public Page<AttachmentDto> getAttachments(Pageable pageable,
                                              @RequestParam Long taskId) {
        return attachmentService.getAttachmentsByTask(pageable, taskId);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAttachment(@PathVariable Long id) throws Exception {
        attachmentService.deleteAttachment(id);
    }
}
