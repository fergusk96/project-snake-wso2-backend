package startup

import (
	"github.com/unusualcodeorg/goserve/arch/mongo"
	sampleModel "github.com/fergusk96/wso2-user-service/api/sample/model"
)

func EnsureDbIndexes(db mongo.Database) {
	go mongo.Document[sampleModel.Sample](&sampleModel.Sample{}).EnsureIndexes(db)
}
