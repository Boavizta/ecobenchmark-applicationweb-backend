<?php

namespace App\Entity;

use App\Repository\ListEntityRepository;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Uid\UuidV4;

#[ORM\Entity(repositoryClass: ListEntityRepository::class)]
#[ORM\Table(name: 'list')]
class ListEntity
{
    #[ORM\Id]
    #[ORM\Column(type: 'uuid', unique: true)]
    #[ORM\GeneratedValue(strategy: "CUSTOM")]
    #[ORM\CustomIdGenerator(class: 'doctrine.uuid_generator')]
    private $id;

    #[ORM\ManyToOne(targetEntity: Account::class, inversedBy: 'lists')]
    private $Account;

    #[ORM\Column(type: 'text', nullable: true)]
    private $name;

    #[ORM\Column(type: 'datetimetz', nullable: true)]
    private $creation_date;

    public function getId(): ?UuidV4
    {
        return $this->id;
    }

    public function getAccount(): ?Account
    {
        return $this->Account;
    }

    public function setAccount(?Account $Account): self
    {
        $this->Account = $Account;

        return $this;
    }

    public function getName(): ?string
    {
        return $this->name;
    }

    public function setName(?string $name): self
    {
        $this->name = $name;

        return $this;
    }

    public function getCreationDate(): ?\DateTimeInterface
    {
        return $this->creation_date;
    }

    public function setCreationDate(?\DateTimeInterface $creation_date): self
    {
        $this->creation_date = $creation_date;

        return $this;
    }
}
