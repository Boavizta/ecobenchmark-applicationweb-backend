<?php

namespace App\Controller\AddTask;

use App\Entity\ListEntity;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\SerializerInterface;

class AddTask extends AbstractController
{
    #[Route('/api/lists/{id}/tasks', name: 'addTask', methods: 'POST')]
    public function addTask(ListEntity $list, Request $request, SerializerInterface $serializer, ManagerRegistry $doctrine): Response
    {
        /** @var TaskRequest $taskRequest */
        $taskRequest = $serializer->deserialize($request->getContent(), TaskRequest::class, 'json');
        $task = $taskRequest->toTaskEntity($list);
        $entityManager = $doctrine->getManager();
        $entityManager->persist($task);
        $entityManager->flush();

        return $this->json(TaskResponse::fromTaskEntity($task), 201);
    }
}
