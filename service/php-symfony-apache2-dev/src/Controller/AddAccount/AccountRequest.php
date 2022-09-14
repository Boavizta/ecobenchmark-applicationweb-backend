<?php

namespace App\Controller\AddAccount;

use App\Entity\Account;

class AccountRequest
{
    public string $login;

    function toAccountEntity(): Account {
        $account = new Account();
        $account->setLogin($this->login);
        $account->setCreationDate(new \DateTime());

        return $account;
    }
}
