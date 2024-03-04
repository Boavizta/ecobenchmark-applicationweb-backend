<?php

namespace App\Controller\AddAccount;

use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\SerializerInterface;

class AddAccount extends AbstractController
{
    #[Route('/api/accounts', name: 'addAccount', methods: 'POST')]
    public function addAccount(Request $request, SerializerInterface $serializer, ManagerRegistry $doctrine): Response
    {
        /** @var AccountRequest $accountRequest */
        $accountRequest = $serializer->deserialize($request->getContent(), AccountRequest::class, 'json');
        $account = $accountRequest->toAccountEntity();
        $entityManager = $doctrine->getManager();
        $entityManager->persist($account);
        $entityManager->flush();

        return $this->json(AccountResponse::fromAccountEntity($account), 201);
    }
}
