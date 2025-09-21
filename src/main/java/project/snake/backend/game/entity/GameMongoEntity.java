package project.snake.backend.game.entity;


import io.micronaut.core.annotation.Introspected;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.Index;
import io.micronaut.data.annotation.MappedEntity;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import org.bson.types.ObjectId;

@NotNull
@Introspected
@MappedEntity(value = "game")
@Getter
@Data
public class GameMongoEntity {

  @Id
  @Generated
  private ObjectId id;

  @Index(columns = {"userOneId"})
  private String playerOneId;

  @Index(columns = {"userTwoId"})
  private String playerTwoId;

  @Index(columns = {"winnerId"})
  private String winnerId;

  private Instant gameFinishedAt;
}
