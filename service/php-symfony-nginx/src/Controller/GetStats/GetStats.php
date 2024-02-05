<?php

namespace App\Controller\GetStats;

use App\Entity\Account;
use Doctrine\Persistence\ManagerRegistry;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class GetStats extends AbstractController
{
    #[Route('/api/stats', name: 'getStats', methods: 'GET')]
    public function getStats(ManagerRegistry $doctrine): Response
    {
        $accountRepository = $doctrine->getManager()->getRepository(Account::class);
        $stats = $accountRepository->getStats();

        return $this->json(StatResponse::fromStats($stats));
    }
}
