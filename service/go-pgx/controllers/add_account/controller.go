package add_account

import (
	"github.com/pkg/errors"
	"go_pgx/endpoints"
	"go_pgx/usecases/add_account"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
	"google.golang.org/protobuf/types/known/timestamppb"
	"log"
)

func Controller(req *endpoints.AddAccountInput, storage add_account.Storage) (*endpoints.AddAccountOutput, error) {
	useCase := add_account.AddAccount{Storage: storage}

	createdAccount, err := useCase.Execute(req.Login)

	if err != nil {
		log.Printf("%+v", errors.Wrap(err, "failed to handle the request to add the account"))
		return nil, status.Error(codes.Aborted, "failed to handle the request to add the account")
	}

	return &endpoints.AddAccountOutput{
		Id:           createdAccount.Id.String(),
		Login:        createdAccount.Login,
		CreationDate: timestamppb.New(createdAccount.CreationDate),
	}, nil
}
