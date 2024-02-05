<?php

namespace App\Controller\AddTask;

use App\Entity\Task;

class TaskResponse
{
    public string $id;
    public string $list_id;
    public string $name;
    public string $description;
    public string $creation_date;

    static function fromTaskEntity(Task $task): TaskResponse
    {
        $taskResponse = new TaskResponse();
        $taskResponse->id = $task->getId();
        $taskResponse->list_id = $task->getList()->getId();
        $taskResponse->name = $task->getName();
        $taskResponse->description = $task->getDescription();
        $taskResponse->creation_date = $task->getCreationDate()->format('c');

        return $taskResponse;
    }
}
