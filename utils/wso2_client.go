package utils

import (
	"encoding/base64"
	"net/http"
)

type WSOClient interface {
	GetUser(userId string) (*http.Response, error)
}

type WSO2Client struct {
	Client       *http.Client
	Host         string
	ClientID     string
	ClientSecret string
}

// NewWSO2Client initializes a new WSO2Client with the host from the environment
func NewWSO2Client(host, clientId, clientSecret string) *WSO2Client {
	return &WSO2Client{
		Client:       &http.Client{}, // You can configure timeouts or other settings here
		Host:         host,
		ClientID:     clientId,
		ClientSecret: clientSecret,
	}
}

// Get sends a GET request to the specified endpoint
func (c *WSO2Client) GetUser(userId string) (*http.Response, error) {
	// Construct the full URL
	url := c.Host + "/scim2/Users/" + userId

	// Create a GET request
	req, err := http.NewRequest("GET", url, nil)
	if err != nil {
		return nil, err
	}

	// Execute the request
	auth := c.ClientID + ":" + c.ClientSecret
	encodedAuth := base64.StdEncoding.EncodeToString([]byte(auth))
	req.Header.Add("Authorization", "Basic "+encodedAuth)

	// Execute the request
	return c.Client.Do(req)
}
