package sample

import (
	"github.com/fergusk96/wso2-user-service/api/sample/dto"
	"github.com/fergusk96/wso2-user-service/utils"
	"github.com/gin-gonic/gin"
	coredto "github.com/unusualcodeorg/goserve/arch/dto"
	"github.com/unusualcodeorg/goserve/arch/network"
)

type controller struct {
	network.BaseController
	service     Service
	userService UserService
}

func NewController(
	authProvider network.AuthenticationProvider,
	authorizeMFunc network.AuthorizationProvider,
	service Service,
	userService UserService,
) network.Controller {
	return &controller{
		BaseController: network.NewBaseController("/sample", authProvider, authorizeMFunc),
		service:        service,
		userService:    userService,
	}
}

func (c *controller) MountRoutes(group *gin.RouterGroup) {
	group.GET("/ping", c.getPingHandler)
	group.GET("/id/:id", c.getSampleHandler)
	group.GET("getUser/:id", c.getUser)
}

func (c *controller) getPingHandler(ctx *gin.Context) {
	c.Send(ctx).SuccessMsgResponse("pong!")
}

func (c *controller) getUser(ctx *gin.Context) {
	user, err := c.userService.FindUser("12345")
	if err != nil {
		c.Send(ctx).InternalServerError("failed to find user", err)
		return
	}
	c.Send(ctx).SuccessDataResponse("success", user)
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
