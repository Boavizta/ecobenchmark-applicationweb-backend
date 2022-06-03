<?php

namespace App\Repository;

use App\Entity\Account;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\ORM\NonUniqueResultException;
use Doctrine\ORM\OptimisticLockException;
use Doctrine\ORM\ORMException;
use Doctrine\ORM\Query\ResultSetMapping;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Account>
 *
 * @method Account|null find($id, $lockMode = null, $lockVersion = null)
 * @method Account|null findOneBy(array $criteria, array $orderBy = null)
 * @method Account[]    findAll()
 * @method Account[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class AccountRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Account::class);
    }

    /**
     * @throws ORMException
     * @throws OptimisticLockException
     */
    public function add(Account $entity, bool $flush = true): void
    {
        $this->_em->persist($entity);
        if ($flush) {
            $this->_em->flush();
        }
    }

    /**
     * @throws ORMException
     * @throws OptimisticLockException
     */
    public function remove(Account $entity, bool $flush = true): void
    {
        $this->_em->remove($entity);
        if ($flush) {
            $this->_em->flush();
        }
    }

    public function getStats(): array
    {
        $entityManager = $this->getEntityManager();

        $rsm = new ResultSetMapping();
        $rsm->addScalarResult('id', 'id');
        $rsm->addScalarResult('login', 'login');
        $rsm->addScalarResult('nb_list', 'nb_list');
        $rsm->addScalarResult('avg_tasks', 'avg_tasks', 'float');


        $query = $entityManager->createNativeQuery(
            'SELECT 
				id,  
				login, 
				count(list_id) AS nb_list, 
				round(avg(nb_tasks),2) AS avg_tasks 
			FROM (
				SELECT 
					account.id, 
					account.login, 
					list.id list_id, 
					count(task.id) nb_tasks 
				FROM account
				INNER JOIN list ON (list.account_id=account.id) 
				LEFT JOIN task ON (task.list_id=list.id) 
				GROUP BY account.id, account.login, list.id
			) t 
			GROUP BY id, login',
            $rsm
        );

        return $query->getArrayResult();
    }
}
