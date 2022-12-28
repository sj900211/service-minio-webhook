package run.freshr.domain.minio.dto.request;

import java.io.InputStream;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import run.freshr.domain.minio.enumeration.SizeType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThumbnailRequest {

  private MediaType contentType;
  private String path;
  private InputStream inputStream;

}
