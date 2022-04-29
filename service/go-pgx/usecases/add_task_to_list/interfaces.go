package add_task_to_list

type Storage interface {
	AddTask(task *AddTaskResponse) error
}
