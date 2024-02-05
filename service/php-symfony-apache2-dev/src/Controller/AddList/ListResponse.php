<?php

namespace App\Controller\AddList;

use App\Entity\ListEntity;

class ListResponse
{
    public string $id;
    public string $name;
    public string $creation_date;

    static function fromListEntity(ListEntity $listEntity): ListResponse
    {
        $listResponse = new ListResponse();
        $listResponse->id = $listEntity->getId();
        $listResponse->name = $listEntity->getName();
        $listResponse->creation_date = $listEntity->getCreationDate()->format('c');

        return $listResponse;
    }
}
