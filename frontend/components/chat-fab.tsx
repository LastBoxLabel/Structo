import { MessageCircle } from 'lucide-react'
import { Button } from './ui/button'

interface ChatFabProps {
  onClick: () => void
}

export function ChatFab({ onClick }: ChatFabProps) {
  return (
    <Button
      className="fixed bottom-4 right-4 rounded-full p-3 shadow-lg lg:hidden"
      onClick={onClick}
    >
      <MessageCircle className="h-6 w-6" />
      <span className="sr-only">Open Chat</span>
    </Button>
  )
}

