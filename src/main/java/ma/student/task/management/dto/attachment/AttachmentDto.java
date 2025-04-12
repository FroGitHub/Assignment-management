package ma.student.task.management.dto.attachment;

import java.time.LocalDateTime;

public record AttachmentDto(
        Long id,
        Long taskId,
        String dropboxFileId,
        String filename,
        LocalDateTime uploadDate) {
}
