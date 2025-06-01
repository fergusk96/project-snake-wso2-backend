FROM golang:1.22.5-alpine

RUN adduser --disabled-password --gecos '' gouser

RUN mkdir -p /home/gouser/wso2-user-service-go

WORKDIR /home/gouser/wso2-user-service-go

COPY . .

RUN chown -R gouser:gouser /home/gouser/wso2-user-service-go

USER gouser

RUN go mod tidy
RUN go build -o build/server cmd/main.go

EXPOSE 8080

CMD ["./build/server"]
 