<?php

namespace App\Controller\AddList;

use App\Entity\Account;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\SerializerInterface;

class AddList extends AbstractController
{
    #[Route('/api/accounts/{id}/lists', name: 'addList', methods: 'POST')]
    public function addList(Account $account, Request $request, SerializerInterface $serializer, ManagerRegistry $doctrine): Response
    {
        /** @var ListRequest $listRequest */
        $listRequest = $serializer->deserialize($request->getContent(), ListRequest::class, 'json');
        $list = $listRequest->toListEntity($account);
        $entityManager = $doctrine->getManager();
        $entityManager->persist($list);
        $entityManager->flush();

        return $this->json(ListResponse::fromListEntity($list), 201);
    }
}
