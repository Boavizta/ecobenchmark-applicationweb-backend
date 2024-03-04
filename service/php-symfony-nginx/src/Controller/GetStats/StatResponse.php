<?php

namespace App\Controller\GetStats;

class StatResponse
{
    public string $account_id;
    public string $account_login;
    public int $list_count;
    public float $task_avg;

    /**
     * @return StatResponse[]
     */
    static function fromStats(array $stats): array
    {
        $statsResponse = [];
        foreach ($stats as $stat) {
            $statResponse = new StatResponse();
            $statResponse->account_id = $stat['id'];
            $statResponse->account_login = $stat['login'];
            $statResponse->list_count = $stat['nb_list'];
            $statResponse->task_avg = $stat['avg_tasks'];
            $statsResponse[] = $statResponse;
        }
        return $statsResponse;
    }
}