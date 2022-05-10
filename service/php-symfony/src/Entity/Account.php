<?php

namespace App\Entity;

use App\Repository\AccountRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Uid\UuidV4;

#[ORM\Entity(repositoryClass: AccountRepository::class)]
class Account
{
    #[ORM\Id]
    #[ORM\Column(type: 'uuid', unique: true)]
    #[ORM\GeneratedValue(strategy: "CUSTOM")]
    #[ORM\CustomIdGenerator(class: 'doctrine.uuid_generator')]
    private $id;

    #[ORM\Column(type: 'text', nullable: true)]
    private $login;

    #[ORM\Column(type: 'datetimetz', nullable: true)]
    private $creation_date;

    #[ORM\OneToMany(mappedBy: 'account', targetEntity: ListEntity::class)]
    private $lists;

    public function __construct()
    {
        $this->lists = new ArrayCollection();
    }

    public function getId(): ?UuidV4
    {
        return $this->id;
    }

    public function getLogin(): ?string
    {
        return $this->login;
    }

    public function setLogin(?string $login): self
    {
        $this->login = $login;

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

    /**
     * @return Collection<int, ListEntity>
     */
    public function getLists(): Collection
    {
        return $this->lists;
    }

    public function addList(ListEntity $list): self
    {
        if (!$this->lists->contains($list)) {
            $this->lists[] = $list;
            $list->setAccount($this);
        }

        return $this;
    }

    public function removeList(ListEntity $list): self
    {
        if ($this->lists->removeElement($list)) {
            // set the owning side to null (unless already changed)
            if ($list->getAccount() === $this) {
                $list->setAccount(null);
            }
        }

        return $this;
    }
}
