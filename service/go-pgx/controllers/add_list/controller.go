package add_list

import (
	"github.com/gofrs/uuid"
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

		accountid, err := uuid.FromString(request.AccountId)
		if err != nil {
			return c.NoContent(http.StatusBadRequest)
		}

		requestList := add_list.AddListRequest{
			Name:      request.Name,
			AccountId: accountid,
		}

		createdList, err := useCase.Execute(requestList)

		if err != nil {
			log.Printf("%+v", errors.Wrap(err, "failed to handle the request to add the list"))
			return c.String(http.StatusInternalServerError, "failed to add the list")
		}

		return c.JSON(
			http.StatusCreated,
			responseBody{
				Id:           createdList.Id.String(),
				AccountId:    createdList.AccountId.String(),
				Name:         createdList.Name,
				CreationDate: createdList.CreationDate.String(),
			},
		)
	}

}
