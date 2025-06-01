package startup

import (
	sampleModel "github.com/fergusk96/wso2-user-service/api/sample/model"
	"github.com/unusualcodeorg/goserve/arch/mongo"
)

func EnsureDbIndexes(db mongo.Database) {
	go mongo.Document[sampleModel.Sample](&sampleModel.Sample{}).EnsureIndexes(db)
}
