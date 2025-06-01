package sample

import (
	"github.com/unusualcodeorg/goserve/arch/mongo"
	"github.com/unusualcodeorg/goserve/arch/network"
	"github.com/unusualcodeorg/goserve/arch/redis"
)

type UserService interface {
	FindUser(id string) (string, error)
}

type userService struct {
	network.BaseService
}

func NewUserService(db mongo.Database, store redis.Store) UserService {
	return &userService{
		BaseService: network.NewBaseService(),
	}
}

func (s *userService) FindUser(id string) (string, error) {

	return "hello world", nil
}
