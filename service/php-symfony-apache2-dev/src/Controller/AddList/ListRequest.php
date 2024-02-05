<?php

namespace App\Controller\AddList;

use App\Entity\Account;
use App\Entity\ListEntity;

class ListRequest
{
    public string $name;

    function toListEntity(Account $account): ListEntity
    {
        $list = new ListEntity();
        $list->setName($this->name);
        $list->setAccount($account);
        $list->setCreationDate(new \DateTime());

        return $list;
    }
}
