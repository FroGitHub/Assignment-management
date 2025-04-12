package ma.student.task.management.service;

import ma.student.task.management.dto.attachment.AttachmentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface AttachmentService {
    AttachmentDto uploadAttachment(MultipartFile file, Long taskId) throws Exception;

    Page<AttachmentDto> getAttachmentsByTask(Pageable pageable, Long taskId);

    void deleteAttachment(Long id) throws Exception;
}
