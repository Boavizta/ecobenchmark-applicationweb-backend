<?php

namespace App\Controller\GetLists;

use App\Entity\Account;
use App\Entity\ListEntity;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Uid\UuidV4;

class GetLists extends AbstractController
{
    #[Route('/api/accounts/{account_id}/lists', name: 'getLists', methods: 'GET')]
    public function getLists(Request $request, string $account_id, ManagerRegistry $doctrine): Response
    {
        $entityManager = $doctrine->getManager();
        $listRepository = $doctrine->getManager()->getRepository(ListEntity::class);
        $lists = $listRepository->findBy(
            array('account' => $entityManager->getReference('App\Entity\Account', new UuidV4($account_id))),
            array(),
            10,
            $request->query->getInt('page') * 10
        );

        return $this->json(ListResponse::fromListEntities($lists));
    }
}
