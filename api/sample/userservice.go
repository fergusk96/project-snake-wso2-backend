package sample

import (
    "github.com/fergusk96/wso2-user-service/utils"
)

type UserService interface {
    FindUser(id string) (string, error)
}

type userService struct {
    wso2Client *utils.WSO2Client
}

func NewUserService(wso2Client *utils.WSO2Client) UserService {
    return &userService{
        wso2Client: wso2Client,
    }
}

func (s *userService) FindUser(id string) (string, error) {
    // Use the WSO2 client to make a request
    resp, err := s.wso2Client.GetUser(id)
    if err != nil {
        return "", err
    }
    defer resp.Body.Close()

    // Process the response (example)
    return "User found", nil
}