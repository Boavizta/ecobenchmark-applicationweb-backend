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

	postgresqlConnectionUri, exists := os.LookupEnv("POSTGRESQL_CONNECTION_URI")
	if !exists {
		panic(errors.New("the environment variable POSTGRESQL_CONNECTION_URI is required"))
	}

	storageService, err = storage.New(postgresqlConnectionUri)
	if err != nil {
		panic(err)
	}

}

func main() {
	e := echo.New()

	e.GET("/ping", func(c echo.Context) error {
		return c.String(http.StatusOK, "")
	})
	e.POST("/api/account", add_account.Controller(storageService))
	e.POST("/api/list", add_list.Controller(storageService))
	e.POST("/api/list/:list_id/task", add_task_to_list.Controller(storageService))
	e.GET("/api/account/:account_id/list/", get_lists.Controller(storageService))
	e.GET("/api/stats", get_stats.Controller(storageService))

	e.Use(middleware.Logger())
	e.Use(middleware.Recover())
	e.Logger.Fatal(e.Start(":8080"))
}
