package add_list

import (
	"github.com/gofrs/uuid"
	"github.com/pkg/errors"
	"go_pgx/endpoints"
	"go_pgx/usecases/add_list"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
	"google.golang.org/protobuf/types/known/timestamppb"
	"log"
)

func Controller(req *endpoints.AddListInput, storage add_list.Storage) (*endpoints.AddListOutput, error) {
	useCase := add_list.AddList{Storage: storage}

	accountid, err := uuid.FromString(req.AccountId)
	if err != nil {
		return nil, status.Error(codes.Aborted, "failed to handle the account id")

	}

	requestList := add_list.AddListRequest{
		Name:      req.Name,
		AccountId: accountid,
	}

	createdList, err := useCase.Execute(requestList)

	if err != nil {
		log.Printf("%+v", errors.Wrap(err, "failed to handle the request to add the list"))
		return nil, status.Error(codes.Aborted, "failed to add the list")
	}

	return &endpoints.AddListOutput{
		Id:           createdList.Id.String(),
		AccountId:    createdList.AccountId.String(),
		Name:         createdList.Name,
		CreationDate: timestamppb.New(createdList.CreationDate),
	}, nil
}
