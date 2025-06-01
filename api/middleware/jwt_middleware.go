package middleware

import (
	"net/http"
	"strings"
	"time"

	"github.com/MicahParks/keyfunc"
	"github.com/fergusk96/wso2-user-service/config"
	"github.com/gin-gonic/gin"
	"github.com/golang-jwt/jwt/v4"
	"github.com/unusualcodeorg/goserve/arch/network"
)

type jwtMiddleware struct {
	network.BaseMiddleware
	env *config.Env
}

func NewJwtMiddleware() network.RootMiddleware {
	return &jwtMiddleware{
		BaseMiddleware: network.NewBaseMiddleware(),
		env:            config.NewEnv("../.env", true),
	}
}

func (m *jwtMiddleware) Attach(engine *gin.Engine) {
	engine.Use(m.Handler)
}

func (m *jwtMiddleware) Handler(ctx *gin.Context) {
	jwksURL := m.env.JWKSURL

	// Step 1: Create a JWKS keyfunc to fetch keys from the .jwks endpoint
	jwks, err := keyfunc.Get(jwksURL, keyfunc.Options{
		RefreshInterval: time.Hour, // Periodically refresh the JWKS
	})
	if err != nil {
		panic("Failed to create JWKS from URL: " + err.Error())
	}

	// Step 2: Extract the Authorization header
	authHeader := ctx.GetHeader("Authorization")
	if authHeader == "" || !strings.HasPrefix(authHeader, "Bearer ") {
		ctx.JSON(http.StatusUnauthorized, gin.H{"error": "missing or invalid Authorization header"})
		ctx.Abort()
		return
	}

	// Step 3: Extract the token
	tokenString := strings.TrimPrefix(authHeader, "Bearer ")

	// Step 4: Parse and validate the token using the JWKS
	token, err := jwt.Parse(tokenString, jwks.Keyfunc)
	if err != nil || !token.Valid {
		ctx.JSON(http.StatusUnauthorized, gin.H{"error": "invalid token"})
		ctx.Abort()
		return
	}

	if claims, ok := token.Claims.(jwt.MapClaims); ok {
		if sub, ok := claims["sub"].(string); ok {
			ctx.Set("sub", sub) // Store the `sub` claim in the context
		} else {
			ctx.JSON(http.StatusUnauthorized, gin.H{"error": "invalid token claims"})
			ctx.Abort()
			return
		}
	}

	// Step 5: Proceed to the next handler
	ctx.Next()
}
