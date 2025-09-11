package utils

import (
	"net/http"
)

type UserManager interface {
    GetUser(userId string) (*http.Response, error)
}

type UserManagerClient struct {
    Client       *http.Client
    Host         string
    ClientID     string
    ClientSecret string
}

type tokenResponse struct {
    AccessToken string `json:"access_token"`
    TokenType   string `json:"token_type"`
    ExpiresIn   int    `json:"expires_in"`
}