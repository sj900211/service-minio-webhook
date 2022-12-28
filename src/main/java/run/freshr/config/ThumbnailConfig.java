package run.freshr.config;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import run.freshr.domain.minio.model.ThumbnailModel;

@Data
@Configuration
@ConfigurationProperties(prefix = "freshr.thumbnail")
public class ThumbnailConfig {

  private String directory;
  private List<ThumbnailModel> list = new ArrayList<>();

}
