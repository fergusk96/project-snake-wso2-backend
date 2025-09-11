package sample

import (
	"github.com/fergusk96/wso2-user-service/utils"
)

type UserService interface {
	FindUser(id string) (string, error)
}

type userService struct {
	userClient *utils.UserManagerClient
}

func NewUserService(wso2Client *utils.UserManagerClient) UserService {
	return &userService{
		userClient: wso2Client,
	}
}

func (s *userService) FindUser(id string) (string, error) {
	// Use the WSO2 client to make a request
	resp, err := s.userClient.GetKindeUser(id)
	if err != nil {
		return "", err
	}
	defer resp.Body.Close()

	// Process the response (example)
	return "User found", nil
}
