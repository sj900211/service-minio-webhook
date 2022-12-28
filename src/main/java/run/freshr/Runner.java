package run.freshr;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static org.springframework.util.StringUtils.hasLength;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import run.freshr.config.ThumbnailConfig;
import run.freshr.domain.minio.model.ThumbnailModel;

@Slf4j
@Component
@RequiredArgsConstructor
public class Runner implements ApplicationRunner {

  private final ApplicationContext context;
  private final ThumbnailConfig config;

  @Override
  public void run(ApplicationArguments args) {
    boolean runFlag = true;
    List<ThumbnailModel> thumbnailList = config.getList();

    try {
      // 썸네일 설정이 있는지 확인
      boolean emptyFlag = thumbnailList.isEmpty();

      if (emptyFlag) {
        log.error("썸네일 설정을 찾을 수 없습니다.");

        throw new Exception();
      }

      // 썸네일 사이즈가 없는 항목이 있는지 확인
      boolean sizeFlag = thumbnailList.stream()
          .anyMatch(item -> isNull(item.getWidth()) && isNull(item.getHeight()));

      if (sizeFlag) {
        log.error("썸네일 크기가 없는 항목이 있습니다.");

        throw new Exception();
      }

      // 중복된 항목이 있는지 확인
      boolean duplicateFixFlag = thumbnailList.stream()
          .filter(item -> !(!hasLength(item.getPrefix()) && !hasLength(item.getSuffix())))
          .map(item -> {
            String prefix = ofNullable(item.getPrefix()).orElse("");
            String suffix = ofNullable(item.getSuffix()).orElse("");

            return prefix + "x" + suffix;
          }).collect(groupingBy(identity(), counting()))
          .entrySet().stream().anyMatch(item -> item.getValue() > 1);

      if (duplicateFixFlag) {
        log.error("결과 파일 이름이 중복된 항목이 있습니다.");

        throw new Exception();
      }

      // 중복된 항목이 있는지 확인
      boolean duplicateAllFlag = thumbnailList.stream()
          .map(item -> {
            String prefix = ofNullable(item.getPrefix()).orElse("");
            String suffix = ofNullable(item.getSuffix()).orElse("");
            int width = ofNullable(item.getWidth()).orElse(item.getHeight());
            int height = ofNullable(item.getHeight()).orElse(item.getWidth());

            return prefix + width + "x" + height + suffix;
          }).collect(groupingBy(identity(), counting()))
          .entrySet().stream().anyMatch(item -> item.getValue() > 1);

      if (duplicateAllFlag) {
        log.error("결과 파일 이름이 중복된 항목이 있습니다.");

        throw new Exception();
      }
    } catch (Exception e) {
      runFlag = false;

      SpringApplication.exit(context);
    } finally {
      if (runFlag) {
        log.info("-------------------------------------------------------------------");
        log.info("_______ .______       _______     _______. __    __  .______");
        log.info("|   ____||   _  \\     |   ____|   /       ||  |  |  | |   _  \\");
        log.info("|  |__   |  |_)  |    |  |__     |   (----`|  |__|  | |  |_)  |");
        log.info("|   __|  |      /     |   __|     \\   \\    |   __   | |      /");
        log.info("|  |     |  |\\  \\----.|  |____.----)   |   |  |  |  | |  |\\  \\----.");
        log.info("|__|     | _| `._____||_______|_______/    |__|  |__| | _| `._____|");
        log.info("-------------------------------------------------------------------");
      } else {
        log.error("-------------------------------------------------------------------");
        log.error(" _______    ___       __   __         .______       __    __  .__   __.");
        log.error("|   ____|  /   \\     |  | |  |        |   _  \\     |  |  |  | |  \\ |  |");
        log.error("|  |__    /  ^  \\    |  | |  |        |  |_)  |    |  |  |  | |   \\|  |");
        log.error("|   __|  /  /_\\  \\   |  | |  |        |      /     |  |  |  | |  . `  |");
        log.error("|  |    /  _____  \\  |  | |  `----.   |  |\\  \\----.|  `--'  | |  |\\   |");
        log.error("|__|   /__/     \\__\\ |__| |_______|   | _| `._____| \\______/  |__| \\__|");
        log.error("-------------------------------------------------------------------");
      }
    }
  }

}
