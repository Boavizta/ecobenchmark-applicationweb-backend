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
        $rsm->addScalarResult('account_id', 'account_id');
        $rsm->addScalarResult('login', 'login');
        $rsm->addScalarResult('list_id', 'list_id');
        $rsm->addScalarResult('task_id', 'task_id');


        $query = $entityManager->createNativeQuery(
            'SELECT account.id account_id, account.login, list.id list_id, task.id task_id
            FROM account
                INNER JOIN list ON (list.account_id=account.id)
                LEFT JOIN task ON (task.list_id=list.id)',
            $rsm
        );

        $rawStats = $query->getArrayResult();
        $preparedData = [];
        foreach ($rawStats as $rawLine) {
            $account_id = $rawLine['account_id'];
            $list_id = $rawLine['list_id'];
            if (array_key_exists($account_id, $preparedData)) {
                if (!array_key_exists($list_id, $preparedData[$account_id]['task_id_map'])) {
                    $preparedData[$account_id]['task_id_map'][$list_id] = 0;
                }
            } else {
                $preparedData[$account_id] = [
                    'account_id' => $account_id,
                    'login' => $rawLine['login'],
                    'task_id_map' => [$list_id => 0],
                    'task_count' => 0
                ];
            }
            if (!empty($rawLine['task_id'])) {
                $preparedData[$account_id]['task_id_map'][$list_id]++;
                $preparedData[$account_id]['task_count']++;
            }
        }

        $stats = [];
        foreach ($preparedData as $data) {
            $avgTask = $data['task_count'] / count($data['task_id_map']);
            $stats[] = [
                'id' => $data['account_id'],
                'login' => $data['login'],
                'nb_list' => count($data['task_id_map']),
                'avg_tasks' => round($avgTask, 2),
            ];
        }

        return $stats;
    }
}
