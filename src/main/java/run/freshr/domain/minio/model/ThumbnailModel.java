package run.freshr.domain.minio.model;

import static java.lang.Double.valueOf;
import static java.util.Optional.ofNullable;
import static org.springframework.util.StringUtils.hasLength;

import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import run.freshr.domain.minio.enumeration.Position;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThumbnailModel {

  private String prefix;
  private String suffix;
  private Integer width;
  private Integer height;
  private Position position;

  public Integer getWidth() {
    return ofNullable(width).orElse(height);
  }

  public Integer getHeight() {
    return ofNullable(height).orElse(width);
  }

  public String createFilename(String filename) {
    boolean prefixFlag = hasLength(prefix);
    boolean suffixFlag = hasLength(suffix);
    boolean fixFlag = !(!prefixFlag && !suffixFlag);

    String thumbnailFilename = "";

    if (fixFlag) {
      thumbnailFilename = ofNullable(prefix).orElse("")
          + filename
          + ofNullable(suffix).orElse("");
    } else {
      int width = ofNullable(this.width).orElse(this.height);
      int height = ofNullable(this.height).orElse(this.width);
      String suffix = width + "x" + height;

      thumbnailFilename = filename + suffix;
    }

    return thumbnailFilename;
  }

}
