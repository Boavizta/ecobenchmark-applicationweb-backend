<?php

namespace App\Controller\GetLists;

use App\Entity\Account;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class GetLists extends AbstractController
{
    #[Route('/api/accounts/{id}/lists', name: 'getLists', methods: 'GET')]
    public function getLists(string $id, ManagerRegistry $doctrine): Response
    {
        $accountRepository = $doctrine->getManager()->getRepository(Account::class);
        $account = $accountRepository->findAllJoinListsAndTasks($id);

        return $this->json(ListResponse::fromAccountEntity($account));
    }
}
