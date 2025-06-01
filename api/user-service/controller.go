package sample

import (
	"github.com/gin-gonic/gin"
	"github.com/fergusk96/wso2-user-service/api/sample/dto"
	coredto "github.com/unusualcodeorg/goserve/arch/dto"
	"github.com/unusualcodeorg/goserve/arch/network"
	"github.com/fergusk96/wso2-user-service/utils"
)

type controller struct {
	network.BaseController
	service Service
}

func NewController(
	authProvider network.AuthenticationProvider,
	authorizeMFunc network.AuthorizationProvider,
	service Service,
) network.Controller {
	return &controller{
		BaseController: network.NewBaseController("/users", authProvider, authorizeMFunc),
		service:        service,
	}
}

func (c *controller) MountRoutes(group *gin.RouterGroup) {
group.GET("/ping", c.getPingHandler)
group.GET("/id/:id", c.getSampleHandler)
}

func (c *controller) getPingHandler(ctx *gin.Context) {
	c.Send(ctx).SuccessMsgResponse("pong!")
}

func (c *controller) getSampleHandler(ctx *gin.Context) {
	mongoId, err := network.ReqParams(ctx, coredto.EmptyMongoId())
	if err != nil {
		c.Send(ctx).BadRequestError(err.Error(), err)
		return
	}

	sample, err := c.service.FindSample(mongoId.ID)
	if err != nil {
		c.Send(ctx).NotFoundError("sample not found", err)
		return
	}

	data, err := utils.MapTo[dto.InfoSample](sample)
	if err != nil {
		c.Send(ctx).InternalServerError("something went wrong", err)
		return
	}

	c.Send(ctx).SuccessDataResponse("success", data)
}
