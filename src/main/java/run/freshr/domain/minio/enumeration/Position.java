package run.freshr.domain.minio.enumeration;

import static java.util.Arrays.stream;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.geometry.Positions;

@Slf4j
public enum Position {

  TOP_LEFT(Positions.TOP_LEFT),
  TOP_CENTER(Positions.TOP_CENTER),
  TOP_RIGHT(Positions.TOP_RIGHT),
  CENTER_LEFT(Positions.CENTER_LEFT),
  CENTER(Positions.CENTER),
  CENTER_RIGHT(Positions.CENTER_RIGHT),
  BOTTOM_LEFT(Positions.BOTTOM_LEFT),
  BOTTOM_CENTER(Positions.BOTTOM_CENTER),
  BOTTOM_RIGHT(Positions.BOTTOM_RIGHT);

  private final Positions positions;

  Position(Positions positions) {
    this.positions = positions;
  }

  public Positions getPositions() {
    return positions;
  }

  public static Position find(String key) {
    log.info("SizeType.find");

    return stream(Position.values())
        .filter(item -> item.name().equals(key))
        .findFirst()
        .orElse(CENTER);
  }

}
