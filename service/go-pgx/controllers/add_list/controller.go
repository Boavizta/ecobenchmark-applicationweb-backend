package add_list

import (
	echo "github.com/labstack/echo/v4"
	"github.com/pkg/errors"
	"go_pgx/usecases/add_list"
	"log"
	"net/http"
)

func Controller(storage add_list.Storage) func(c echo.Context) error {
	useCase := add_list.AddList{Storage: storage}

	return func(c echo.Context) error {
		request := requestBody{}
		if err := c.Bind(&request); err != nil {
			return c.NoContent(http.StatusBadRequest)
		}

		createdList, err := useCase.Execute(request.Name)

		if err != nil {
			log.Printf("%+v", errors.Wrap(err, "failed to handle the request to add the list"))
			return c.String(http.StatusInternalServerError, "failed to add the list")
		}

		return c.JSON(
			http.StatusCreated,
			responseBody{
				Id:           createdList.Id.String(),
				Name:         createdList.Name,
				CreationDate: createdList.CreationDate.String(),
			},
		)
	}

}
