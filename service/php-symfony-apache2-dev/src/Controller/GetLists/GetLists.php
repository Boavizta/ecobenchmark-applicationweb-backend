<?php

namespace App\Controller\GetLists;

use App\Entity\Account;
use App\Entity\ListEntity;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class GetLists extends AbstractController
{
    #[Route('/api/accounts/{account_id}/lists', name: 'getLists', methods: 'GET')]
    public function getLists(Request $request, string $account_id, ManagerRegistry $doctrine): Response
    {
        $listRepository = $doctrine->getManager()->getRepository(ListEntity::class);
        $lists = $listRepository->findAllByAccountJoinTasks($account_id, $request->query->getInt('page'));

        return $this->json(ListResponse::fromListEntities($lists));
    }
}
