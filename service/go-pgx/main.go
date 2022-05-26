package main

import (
	"context"
	"fmt"
	"github.com/pkg/errors"
	"go_pgx/controllers/add_account"
	"go_pgx/controllers/add_list"
	"go_pgx/controllers/add_task_to_list"
	"go_pgx/controllers/get_lists"
	"go_pgx/controllers/get_stats"
	"go_pgx/endpoints"
	"go_pgx/infra/storage"
	"google.golang.org/grpc"
	"google.golang.org/grpc/keepalive"
	emptypb "google.golang.org/protobuf/types/known/emptypb"
	"log"
	"net"
	"os"
	"time"
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
	port := "8080"
	lis, err := net.Listen("tcp", fmt.Sprintf(":%s", port))
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}
	s := grpc.NewServer(
		grpc.ConnectionTimeout(time.Second),
		grpc.KeepaliveParams(keepalive.ServerParameters{
			MaxConnectionIdle: time.Second * 10,
			Timeout:           time.Second * 20,
		}),
		grpc.KeepaliveEnforcementPolicy(
			keepalive.EnforcementPolicy{
				MinTime:             time.Second,
				PermitWithoutStream: true,
			}),
		grpc.MaxConcurrentStreams(5),
	)

	endpoints.RegisterHealthServer(s, &healthServer{})
	endpoints.RegisterTodoListServer(s, &todoListServer{})
	if err := s.Serve(lis); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}
}

type healthServer struct {
	endpoints.UnimplementedHealthServer
}

type todoListServer struct {
	endpoints.UnimplementedTodoListServer
}

func (s *healthServer) GetHealthCheck(ctx context.Context, empty *emptypb.Empty) (*emptypb.Empty, error) {
	return empty, nil
}

func (s *todoListServer) AddAccount(ctx context.Context, msg *endpoints.AddAccountInput) (*endpoints.AddAccountOutput, error) {
	return add_account.Controller(msg, storageService)
}

func (s *todoListServer) AddListToAccount(ctx context.Context, msg *endpoints.AddListInput) (*endpoints.AddListOutput, error) {
	return add_list.Controller(msg, storageService)
}

func (s *todoListServer) AddTaskToList(ctx context.Context, msg *endpoints.AddTaskInput) (*endpoints.AddTaskOutput, error) {
	return add_task_to_list.Controller(msg, storageService)
}

func (s *todoListServer) GetListsWithTasks(ctx context.Context, msg *endpoints.GetListInput) (*endpoints.GetListOutput, error) {
	return get_lists.Controller(msg, storageService)
}

func (s *todoListServer) GetStats(ctx context.Context, msg *emptypb.Empty) (*endpoints.GetStatsOutput, error) {
	return get_stats.Controller(storageService)
}
