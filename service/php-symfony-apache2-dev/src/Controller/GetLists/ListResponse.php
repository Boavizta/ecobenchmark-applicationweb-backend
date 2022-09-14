<?php

namespace App\Controller\GetLists;

use App\Entity\Account;
use App\Entity\ListEntity;

class ListResponse
{
    public string $id;
    public string $name;
    public array $tasks;
    public string $creation_date;
    public string $account_id;

    /**
     * @param ListEntity[] $listEntities
     * @return array
     */
    static function fromListEntities(array $listEntities): array
    {
        $listResponses = [];
        foreach ($listEntities as $list) {
            $listResponses[] = self::fromListEntity($list);
        }
        return $listResponses;
    }

    static function fromListEntity(ListEntity $list): ListResponse
    {
        $listResponse = new ListResponse();
        $listResponse->id = $list->getId();
        $listResponse->name = $list->getName();
        $listResponse->creation_date = $list->getCreationDate()->format('c');
        $listResponse->account_id = $list->getAccount()->getId();

        foreach ($list->getTasks() as $task) {
            $listResponse->tasks[] = TaskResponse::fromTaskEntity($task);
        }

        return $listResponse;
    }
}
