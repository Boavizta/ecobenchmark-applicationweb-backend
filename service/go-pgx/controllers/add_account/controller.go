package add_account

import (
	echo "github.com/labstack/echo/v4"
	"github.com/pkg/errors"
	"go_pgx/usecases/add_account"
	"log"
	"net/http"
)

func Controller(storage add_account.Storage) func(c echo.Context) error {
	useCase := add_account.AddAccount{Storage: storage}

	return func(c echo.Context) error {
		request := requestBody{}
		if err := c.Bind(&request); err != nil {
			return c.NoContent(http.StatusBadRequest)
		}

		createdAccount, err := useCase.Execute(request.Login)

		if err != nil {
			log.Printf("%+v", errors.Wrap(err, "failed to handle the request to add the account"))
			return c.String(http.StatusInternalServerError, "failed to add the account")
		}

		return c.JSON(
			http.StatusCreated,
			responseBody{
				Id:           createdAccount.Id.String(),
				Login:        createdAccount.Login,
				CreationDate: createdAccount.CreationDate.String(),
			},
		)
	}

}
