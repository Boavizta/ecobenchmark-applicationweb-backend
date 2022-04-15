package add_account

type Storage interface {
	AddAccount(account *AddAccountResponse) error
}
