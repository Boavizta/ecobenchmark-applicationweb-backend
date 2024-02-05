<?php

namespace App\Controller\AddTask;

use App\Entity\ListEntity;
use App\Entity\Task;

class TaskRequest
{
    public string $name;
    public string $description;

    function toTaskEntity(ListEntity $listEntity): Task
    {
        $task = new Task();
        $task->setList($listEntity);
        $task->setName($this->name);
        $task->setDescription($this->description);
        $task->setCreationDate(new \DateTime());

        return $task;
    }
}
