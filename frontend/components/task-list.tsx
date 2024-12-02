interface Task {
  Name: string
  Description: string
  ProjectPath: string
}

interface TaskListProps {
  tasks: Task[]
}

export function TaskList({ tasks }: TaskListProps) {
  const parsedTasks = typeof tasks === 'string'
    ? JSON.parse(tasks).tasks
    : tasks;

  return (
    <div className="space-y-2">
      {parsedTasks.map((task: Task, index: number) => (
        <div key={index} className="space-y-1">
          <div className="font-medium">{task.Name}</div>
          <div className="text-sm text-gray-600">{task.Description}</div>
          <div className="text-xs text-gray-500">Path: {task.ProjectPath}</div>
        </div>
      ))}
    </div>
  )
}