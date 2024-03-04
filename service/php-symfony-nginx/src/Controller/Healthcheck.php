<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class Healthcheck extends AbstractController
{
    #[Route('/healthcheck', name: 'healthcheck')]
    public function healthcheck(): Response
    {
        return new Response('', 204);
    }
}