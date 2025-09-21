package project.snake.backend.game.repository;

import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.CrudRepository;
import java.util.List;
import org.bson.types.ObjectId;
import project.snake.backend.game.entity.GameMongoEntity;

@MongoRepository(databaseName = "project-snake")
public interface GameMongoRepository extends CrudRepository<GameMongoEntity, ObjectId> {
  List<GameMongoEntity> findByWinnerId(String winnerId);
  List<GameMongoEntity> findByPlayerOneId(String playerOneId);
}
