package main

import (
	echo "github.com/labstack/echo/v4"
	"github.com/labstack/echo/v4/middleware"
	"github.com/pkg/errors"
	"go_pgx/controllers/add_account"
	"go_pgx/controllers/add_list"
	"go_pgx/controllers/add_task_to_list"
	"go_pgx/controllers/get_lists"
	"go_pgx/controllers/get_stats"
	"go_pgx/infra/storage"
	"net/http"
	"os"
)

var storageService *storage.Storage

func init() {
	var exists bool
	var err error

	postgresqlConnectionUri, exists := os.LookupEnv("DATABASE_URL")
	if !exists {
		panic(errors.New("the environment variable DATABASE_URL is required"))
	}

	storageService, err = storage.New(postgresqlConnectionUri)
	if err != nil {
		panic(err)
	}

}

func main() {
	e := echo.New()

	e.HEAD("/healthcheck", func(c echo.Context) error {
		return c.NoContent(http.StatusNoContent)
	})
	e.POST("/api/accounts", add_account.Controller(storageService))
	e.POST("/api/accounts/:account_id/lists", add_list.Controller(storageService))
	e.POST("/api/lists/:list_id/tasks", add_task_to_list.Controller(storageService))
	e.GET("/api/accounts/:account_id/lists/", get_lists.Controller(storageService))
	e.GET("/api/stats", get_stats.Controller(storageService))

	e.Use(middleware.Logger())
	e.Use(middleware.Recover())
	e.Logger.Fatal(e.Start(":8080"))
}
