package run.freshr.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import run.freshr.domain.minio.dto.request.MinioRequest;
import run.freshr.service.WebhookService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WebhookController {

  private final WebhookService service;

  private final String webhook = "/minio";

  @PostMapping(webhook)
  public ResponseEntity<?> webhook(@RequestBody MinioRequest dto) throws Exception {
    log.info("WebhookController.webhookPost");

    return service.createThumbnail(dto);
  }

}
