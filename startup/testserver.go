package startup

import (
	"net/http/httptest"

	"github.com/fergusk96/wso2-user-service/config"
	"github.com/unusualcodeorg/goserve/arch/network"
)

type Teardown = func()

func TestServer() (network.Router, Module, Teardown) {
	env := config.NewEnv("../.test.env", false)
	router, module, shutdown := create(env)
	ts := httptest.NewServer(router.GetEngine())
	teardown := func() {
		ts.Close()
		shutdown()
	}
	return router, module, teardown
}
