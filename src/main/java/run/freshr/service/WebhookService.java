package run.freshr.service;

import org.springframework.http.ResponseEntity;
import run.freshr.domain.minio.dto.request.MinioRequest;

public interface WebhookService {

  ResponseEntity<?> createThumbnail(MinioRequest dto) throws Exception;

}
