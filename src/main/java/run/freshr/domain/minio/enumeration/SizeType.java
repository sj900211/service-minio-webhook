package run.freshr.domain.minio.enumeration;

import static java.util.Arrays.stream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum SizeType {

  THOUSAND(1000, "-w-1000", "-h-1000"),
  EIGHT_HUNDRED(800, "-w-800", "-h-800"),
  SIX_HUNDRED(600, "-w-600", "-h-600"),
  FOUR_HUNDRED(400, "-w-400", "-h-400"),
  TWO_HUNDRED(200, "-w-200", "-h-200"),
  DEFAULT(0, "", "");

  private final Integer size;
  private final String width;
  private final String height;

  SizeType(Integer size, String width, String height) {
    this.size = size;
    this.width = width;
    this.height = height;
  }

  public Integer getSize() {
    return size;
  }

  public String getWidth() {
    return width;
  }

  public String getHeight() {
    return height;
  }

  public static SizeType find(int key) {
    log.info("SizeType.find");

    return stream(SizeType.values())
        .filter(item -> item.getSize() < key)
        .findFirst()
        .orElse(null);
  }

}
