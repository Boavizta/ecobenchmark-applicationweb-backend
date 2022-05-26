package get_stats

import (
	"github.com/pkg/errors"
	"go_pgx/endpoints"
	"go_pgx/usecases/get_stats"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
	"log"
)

func Controller(storage get_stats.Storage) (*endpoints.GetStatsOutput, error) {
	useCase := get_stats.GetStatsByAccounts{Storage: storage}

	stats, err := useCase.Execute()

	if err != nil {
		log.Printf("%+v", errors.Wrap(err, "failed to handle the request to get the stats"))
		return nil, status.Error(codes.Aborted, "failed to get the stats")
	}

	return &endpoints.GetStatsOutput{
		Stats: statsToResponse(stats),
	}, nil
}

func statsToResponse(stats []get_stats.GetStatsByAccountsResponse) []*endpoints.Stats {
	response := make([]*endpoints.Stats, len(stats))
	for i, s := range stats {
		response[i] = &endpoints.Stats{
			AccountId:    s.AccountId.String(),
			AccountLogin: s.AccountLogin,
			ListCount:    s.ListCount,
			TaskAvg:      s.TaskAvg,
		}
	}
	return response
}
