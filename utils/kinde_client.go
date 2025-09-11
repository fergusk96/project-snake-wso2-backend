package utils

import (
	"net/http"
	 "bytes"
    "encoding/json"
    "io"
    "net/url"
)



func NewKindeClient(host, clientId, clientSecret string) *UserManagerClient {
	return &UserManagerClient{
		Client:       &http.Client{}, 
		Host:         host,
		ClientID:     clientId,
		ClientSecret: clientSecret,
	}
}


func (c *UserManagerClient) GetKindeUser(userId string) (*http.Response, error) {
    token, err := c.getBearerToken("https://projectsnake.kinde.com" + "/api")
    if err != nil {
        return nil, err
    }

    endpoint := c.Host + "/api/v1/user?id=" + url.QueryEscape(userId)

    req, err := http.NewRequest("GET", endpoint, nil)
    if err != nil {
        return nil, err
    }

    req.Header.Set("Authorization", "Bearer "+token)

    return c.Client.Do(req)
}

// getBearerToken retrieves a bearer token using client credentials (private function)
func (c *UserManagerClient) getBearerToken(audience string) (string, error) {
    form := url.Values{}
    form.Set("grant_type", "client_credentials")
    form.Set("client_id", c.ClientID)
    form.Set("client_secret", c.ClientSecret)
    form.Set("audience", audience)

    req, err := http.NewRequest("POST", c.Host+"/oauth2/token", bytes.NewBufferString(form.Encode()))
    if err != nil {
        return "", err
    }
    req.Header.Set("Content-Type", "application/x-www-form-urlencoded")

    resp, err := c.Client.Do(req)
    if err != nil {
        return "", err
    }
    defer resp.Body.Close()

    body, err := io.ReadAll(resp.Body)
    if err != nil {
        return "", err
    }

    if resp.StatusCode != http.StatusOK {
        return "", &url.Error{
            Op:  "POST",
            URL: c.Host + "/oauth2/token",
            Err: err,
        }
    }

    var tokenResp tokenResponse
    if err := json.Unmarshal(body, &tokenResp); err != nil {
        return "", err
    }

    return tokenResp.AccessToken, nil
}

