package get_stats

import (
	echo "github.com/labstack/echo/v4"
	"github.com/pkg/errors"
	"go_pgx/usecases/get_stats"
	"log"
	"net/http"
)

func Controller(storage get_stats.Storage) func(c echo.Context) error {
	useCase := get_stats.GetStatsByAccounts{Storage: storage}

	return func(c echo.Context) error {

		stats, err := useCase.Execute()

		if err != nil {
			log.Printf("%+v", errors.Wrap(err, "failed to handle the request to get the stats"))
			return c.String(http.StatusInternalServerError, "failed to add the stats")
		}

		return c.JSON(
			http.StatusCreated,
			statsToResponse(stats),
		)
	}

}

func statsToResponse(stats []get_stats.GetStatsByAccountsResponse) []responseBody {
	response := make([]responseBody, len(stats))
	for i, s := range stats {
		response[i] = responseBody{
			AccountId:    s.AccountId.String(),
			AccountLogin: s.AccountLogin,
			ListCount:    s.ListCount,
			TaskAvg:      s.TaskAvg,
		}
	}
	return response
}
