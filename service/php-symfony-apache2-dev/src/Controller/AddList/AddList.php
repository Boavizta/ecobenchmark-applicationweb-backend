<?php

namespace App\Controller\AddList;

use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\SerializerInterface;
use Symfony\Component\Uid\UuidV4;

class AddList extends AbstractController
{
    #[Route('/api/accounts/{id}/lists', name: 'addList', methods: 'POST')]
    public function addList(string $id, Request $request, SerializerInterface $serializer, ManagerRegistry $doctrine): Response
    {
        $entityManager = $doctrine->getManager();

        /** @var ListRequest $listRequest */
        $listRequest = $serializer->deserialize($request->getContent(), ListRequest::class, 'json');
        $list = $listRequest->toListEntity($entityManager->getReference('App\Entity\Account', new UuidV4($id)));
        $entityManager->persist($list);
        $entityManager->flush();

        return $this->json(ListResponse::fromListEntity($list), 201);
    }
}
