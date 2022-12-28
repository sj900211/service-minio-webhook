package run.freshr.domain.minio.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MinioRequest {

  @JsonProperty("EventName")
  private String event;

  @JsonProperty("Records")
  private List<HashMap<String, Object>> records;

  @JsonProperty("Key")
  private String key;

}
