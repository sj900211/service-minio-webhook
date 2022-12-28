package run.freshr.service;

import static java.util.Optional.ofNullable;
import static org.springframework.util.StringUtils.hasLength;

import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import run.freshr.config.ThumbnailConfig;
import run.freshr.domain.minio.dto.request.MinioRequest;
import run.freshr.domain.minio.model.ThumbnailModel;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebhookServiceImpl implements WebhookService {

  private final ThumbnailConfig config;
  private final MinioService minioService;

  @Override
  public ResponseEntity<?> createThumbnail(MinioRequest dto) throws Exception {
    log.info("WebhookService.createThumbnail");

    String key = dto.getKey();
    LinkedList<String> keys = Arrays.stream(key.split("/"))
        .collect(Collectors.toCollection(LinkedList::new));

    keys.removeFirst();

    String originPath = String.join("/", keys);

    keys.removeFirst();

    String filename = keys.removeLast();
    String extension = FileNameUtils.getExtension(filename);
    String mimeType = MimeMappings.DEFAULT.get(extension);

    if (!mimeType.contains("image")) {
      return ResponseEntity.ok(null);
    }

    String baseName = FileNameUtils.getBaseName(filename);
    String thumbnailDirectory = String.join("/", keys);
    String separator = hasLength(thumbnailDirectory) ? "/" : "";
    String thumbnailRoot = ofNullable(config.getDirectory()).orElse("thumbnail");
    String thumbnailPath = thumbnailRoot + "/" + thumbnailDirectory + separator;

    InputStream inputStream = minioService.get(originPath);
    BufferedImage bufferedImage = ImageIO.read(inputStream);

    List<ThumbnailModel> thumbnailList = config.getList();

    for (ThumbnailModel item : thumbnailList) {
      createThumbnail(thumbnailPath, baseName, extension, item, bufferedImage);
    }

    return ResponseEntity.ok(null);
  }

  private void createThumbnail(String path, String filename, String extension,
      ThumbnailModel thumbnail, BufferedImage bufferedImage) throws IOException, ServerException,
      InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException,
      InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
    String thumbnailFilename = thumbnail.createFilename(filename);
    String physical = path + thumbnailFilename + "." + extension;

    BufferedImage thumbnailBufferedImage = Thumbnails
        .of(bufferedImage)
        .size(thumbnail.getWidth(), thumbnail.getHeight())
        .crop(thumbnail.getPosition().getPositions())
        .asBufferedImage();
    ByteArrayOutputStream thumbnailOutputStream = new ByteArrayOutputStream();

    ImageIO.write(thumbnailBufferedImage, extension, thumbnailOutputStream);

    byte[] thumbnailBytes = thumbnailOutputStream.toByteArray();
    InputStream thumbnailInputStream = new ByteArrayInputStream(thumbnailBytes);

    log.info("physical = " + physical);

    minioService.put("image/png", physical, thumbnailInputStream);
  }

}
