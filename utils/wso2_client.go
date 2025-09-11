package utils

import (
	"encoding/base64"
	"net/http"
)


// NewWSO2Client initializes a new WSO2Client with the host from the environment
func NewWSO2Client(host, clientId, clientSecret string) *UserManagerClient {
	return &UserManagerClient{
		Client:       &http.Client{}, // You can configure timeouts or other settings here
		Host:         host,
		ClientID:     clientId,
		ClientSecret: clientSecret,
	}
}

// Get sends a GET request to the specified endpoint
func (c *UserManagerClient) GetWSO2User(userId string) (*http.Response, error) {
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
