package startup

import (
	"context"

	"github.com/fergusk96/wso2-user-service/api/middleware"
	"github.com/fergusk96/wso2-user-service/api/sample"
	"github.com/fergusk96/wso2-user-service/config"
	"github.com/fergusk96/wso2-user-service/utils"
	coreMW "github.com/unusualcodeorg/goserve/arch/middleware"
	"github.com/unusualcodeorg/goserve/arch/mongo"
	"github.com/unusualcodeorg/goserve/arch/network"
	"github.com/unusualcodeorg/goserve/arch/redis"
)

type Module network.Module[module]

type module struct {
	Context context.Context
	Env     *config.Env
	DB      mongo.Database
	Store   redis.Store
}

func (m *module) GetInstance() *module {
	return m
}

func (m *module) Controllers() []network.Controller {
	return []network.Controller{
		sample.NewController(m.AuthenticationProvider(),
			m.AuthorizationProvider(),
			sample.NewService(m.DB, m.Store),
			sample.NewUserService(
				utils.NewWSO2Client(m.Env.KINDEHOST, m.Env.KINDEClientID, m.Env.KINDEClientSecret))),
	}
}

func (m *module) RootMiddlewares() []network.RootMiddleware {
	return []network.RootMiddleware{
		coreMW.NewErrorCatcher(),
		coreMW.NewNotFound(),
		middleware.NewJwtMiddleware(),
	}
}

func (m *module) AuthenticationProvider() network.AuthenticationProvider {
	// TODO
	return nil
}

func (m *module) AuthorizationProvider() network.AuthorizationProvider {
	// TODO
	return nil
}

func NewModule(context context.Context, env *config.Env, db mongo.Database, store redis.Store) Module {
	return &module{
		Context: context,
		Env:     env,
		DB:      db,
		Store:   store,
	}
}
