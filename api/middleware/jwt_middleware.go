package middleware

import (
    "net/http"
    "strings"
    "time"
    "os"

    "github.com/MicahParks/keyfunc"
    "github.com/gin-gonic/gin"
    "github.com/golang-jwt/jwt/v4"
    "github.com/joho/godotenv"
)

func TokenValidationMiddleware() gin.HandlerFunc {
    err := godotenv.Load()
    jwksURL := os.Getenv("JWKS_URL")
    if err != nil {
        panic("Failed to load .env file: " + err.Error())
    }

    // Step 1: Create a JWKS keyfunc to fetch keys from the .jwks endpoint
    jwks, err := keyfunc.Get(jwksURL, keyfunc.Options{
        RefreshInterval: time.Hour, // Periodically refresh the JWKS
    })
    if err != nil {
        panic("Failed to create JWKS from URL: " + err.Error())
    }

    return func(ctx *gin.Context) {
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

        // Step 5: Proceed to the next handler
        ctx.Next()
    }
}