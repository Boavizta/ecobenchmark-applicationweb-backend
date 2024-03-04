<?php

namespace App\Controller\AddAccount;

use App\Entity\Account;

class AccountResponse
{
    public string $id;
    public string $login;
    public string $creation_date;

    static function fromAccountEntity(Account $account): AccountResponse {
        $accountResponse = new AccountResponse();
        $accountResponse->id = $account->getId();
        $accountResponse->login = $account->getLogin();
        $accountResponse->creation_date = $account->getCreationDate()->format('c');

        return $accountResponse;
    }
}