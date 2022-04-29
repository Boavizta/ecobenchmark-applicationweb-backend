package add_task_to_list

import (
	"github.com/gofrs/uuid"
	echo "github.com/labstack/echo/v4"
	"github.com/pkg/errors"
	"go_pgx/usecases/add_task_to_list"
	"log"
	"net/http"
)

func Controller(storage add_task_to_list.Storage) func(c echo.Context) error {
	useCase := add_task_to_list.AddTask{Storage: storage}

	return func(c echo.Context) error {
		request := requestBody{}
		if err := c.Bind(&request); err != nil {
			return c.NoContent(http.StatusBadRequest)
		}

		listId, err := uuid.FromString(c.Param("list_id"))
		if err != nil {
			return c.NoContent(http.StatusBadRequest)
		}

		requestTask := add_task_to_list.AddTaskRequest{
			Name:        request.Name,
			Description: request.Description,
			ListId:      listId,
		}

		createdTask, err := useCase.Execute(requestTask)

		if err != nil {
			log.Printf("%+v", errors.Wrap(err, "failed to handle the request to add the task"))
			return c.String(http.StatusInternalServerError, "failed to add the task")
		}

		return c.JSON(
			http.StatusCreated,
			responseBody{
				Id:           createdTask.Id.String(),
				ListId:       createdTask.ListId.String(),
				Name:         createdTask.Name,
				Description:  createdTask.Description,
				CreationDate: createdTask.CreationDate.String(),
			},
		)
	}

}
