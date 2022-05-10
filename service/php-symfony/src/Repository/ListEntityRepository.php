<?php

namespace App\Repository;

use App\Entity\ListEntity;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\ORM\OptimisticLockException;
use Doctrine\ORM\ORMException;
use Doctrine\ORM\Query\ResultSetMappingBuilder;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<ListEntity>
 *
 * @method ListEntity|null find($id, $lockMode = null, $lockVersion = null)
 * @method ListEntity|null findOneBy(array $criteria, array $orderBy = null)
 * @method ListEntity[]    findAll()
 * @method ListEntity[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class ListEntityRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, ListEntity::class);
    }

    /**
     * @throws ORMException
     * @throws OptimisticLockException
     */
    public function add(ListEntity $entity, bool $flush = true): void
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
    public function remove(ListEntity $entity, bool $flush = true): void
    {
        $this->_em->remove($entity);
        if ($flush) {
            $this->_em->flush();
        }
    }

    /**
     * @param string $account_id
     * @param int $page
     * @param int $pageSize
     * @return ListEntity[]
     */
    public function findAllByAccountJoinTasks(string $account_id, int $page, int $pageSize = 10): array
    {
        $entityManager = $this->getEntityManager();

        $rsm = new ResultSetMappingBuilder($entityManager);
        $rsm->addRootEntityFromClassMetadata('App\Entity\ListEntity', 'l');
        $rsm->addJoinedEntityFromClassMetadata(
            'App\Entity\Task',
            't',
            'l',
            'tasks',
            ['id' => 'task_id', 'name' => 'task_name', 'creation_date' => 'task_creation_date']
        );

        $query = $entityManager->createNativeQuery(
            'SELECT
                l.id,
                l.name,
                l.creation_date,
                l.account_id,
                t.id AS task_id,
                t.name AS task_name,
                t.description,
                t.creation_date AS task_creation_date
            FROM list l
                LEFT JOIN task t ON l.id = t.list_id
            WHERE
                l.account_id = :id
                AND l.id IN (SELECT id FROM list WHERE account_id = :id LIMIT :limit OFFSET :offset)',
            $rsm
        )
            ->setParameter('id', $account_id)
            ->setParameter('limit', $pageSize)
            ->setParameter('offset', $page * $pageSize);

        return $query->getResult();
    }
}
