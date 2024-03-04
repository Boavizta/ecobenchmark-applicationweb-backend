<?php

namespace App\Controller\AddTask;

use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\Uid\UuidV4;

class AddTask extends AbstractController
{
    #[Route('/api/lists/{id}/tasks', name: 'addTask', methods: 'POST')]
    public function addTask(string $id, Request $request, SerializerInterface $serializer, ManagerRegistry $doctrine): Response
    {
        $entityManager = $doctrine->getManager();

        /** @var TaskRequest $taskRequest */
        $taskRequest = $serializer->deserialize($request->getContent(), TaskRequest::class, 'json');
        $task = $taskRequest->toTaskEntity($entityManager->getReference('App\Entity\ListEntity', new UuidV4($id)));
        $entityManager->persist($task);
        $entityManager->flush();

        return $this->json(TaskResponse::fromTaskEntity($task), 201);
    }
}
