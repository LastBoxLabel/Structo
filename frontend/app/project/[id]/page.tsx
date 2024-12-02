"use client"

import { useState, useEffect } from 'react'
import { useParams } from 'next/navigation'
import { Card, CardContent, CardHeader, CardTitle } from '../../../components/ui/card'
import { Input } from '../../../components/ui/input'
import { Button } from '../../../components/ui/button'
import { ScrollArea } from '../../../components/ui/scroll-area'
import { Tabs, TabsContent, TabsList, TabsTrigger } from '../../../components/ui/tabs'
import { Mermaid } from '../../../components/mermaid'
import { FileStructure } from '../../../components/file-structure'
import { TaskList } from '../../../components/task-list'
import { ChatFab } from '../../../components/chat-fab'
import cytoscape from 'cytoscape'

interface Message {
  id: number
  content: string
  role: 'user' | 'assistant'
  timestamp: string
}

interface Task {
  Name: string
  Description: string
  ProjectPath: string
}

type FileStructureNode = string | { [key: string]: FileStructureNode[] }

interface Diagram {
  Name: string
  Type: string
  Description: string
  'Mermaid Code': string
  ProjectPath: string
}

interface ProjectEntity {
  id: number
  name: string
  description: string
  tasks: Task[]
  fileStructure: FileStructureNode
  diagrams: Diagram[]
}

interface Project {
  id: number
  name: string
  description: string
  projectEntities: ProjectEntity[]
}

export default function ProjectPage() {
  const { id } = useParams()
  const [project, setProject] = useState<ProjectEntity | null>(null)
  const [messages, setMessages] = useState<Message[]>([])
  const [newMessage, setNewMessage] = useState('')
  const [isLoading, setIsLoading] = useState(false)
  const [isChatVisible, setIsChatVisible] = useState(false)
  const [historyId, setHistoryId] = useState<number | null>(null)

  useEffect(() => {
    const fetchProjectAndMessages = async () => {
      const token = localStorage.getItem('Authorization')
      if (!token) {
        console.error('No authorization token found')
        return
      }

      let historyId;
      try {
        const projectResponse = await fetch(`http://localhost:8080/user`, {
          method: 'GET',
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`
          },
        })

        if (!projectResponse.ok) {
          console.error('Failed to fetch user data')
          return
        }

        const userData = await projectResponse.json()


        const selectedProject = userData.projectEntities.find((p: ProjectEntity) => p.id === parseInt(Array.isArray(id) ? id[0] : id))

        if (selectedProject) {
          setProject({
            ...selectedProject,
            tasks: JSON.parse(selectedProject.tasks).tasks,
            fileStructure: JSON.parse(selectedProject.fileStructure),
            diagrams: JSON.parse(selectedProject.diagram).diagrams
          })
            setHistoryId(selectedProject.chatHistory.id)
        }


        const messagesResponse = await fetch(`http://localhost:8080/user/project/chat/${selectedProject.chatHistory.id}`, {
          method: 'GET',
          headers: {
            'Authorization': token,
            'Content-Type': 'application/json'
          }
        })

        if (!messagesResponse.ok) {
          throw new Error('Failed to fetch messages')
        }

        const messagesData = await messagesResponse.json()
        console.log(messagesData)

        setMessages(messagesData.map((msg: any) => ({
          id: msg.timestamp,
          content: msg.role === 'MODEL' ? JSON.parse(msg.message).message : msg.message,
          role: msg.role === 'USER' ? 'user' : 'assistant',
          timestamp: msg.timestamp
        })))
      } catch (error) {
        console.error('Error fetching data:', error)
      }
    }

    if (id) {
      fetchProjectAndMessages()
    }
  }, [id])

  const handleSendMessage = async () => {
    if (newMessage.trim() === '' || isLoading) return

    const token = localStorage.getItem('Authorization')
    if (!token) {
      console.error('No authorization token found')
      return
    }

    setIsLoading(true)
    try {
      const response = await fetch(`http://localhost:8080/user/project/chat/${historyId}`, {
        method: 'POST',
        headers: {
          'Authorization': token,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ message: newMessage }),
      })

      if (!response.ok) {
        throw new Error('Failed to send message')
      }

      const data = await response.json()

      setMessages([...messages, {
        id: Date.now(),
        content: newMessage,
        role: 'user',
        timestamp: new Date().toISOString()
      }, {
        id: data.timestamp,
        content: typeof data === 'string' ? data : JSON.parse(data.message).message,
        role: 'assistant',
        timestamp: data.timestamp
      }])

      setNewMessage('')
    } catch (error) {
      console.error('Error sending message:', error)
    } finally {
      setIsLoading(false)
    }
  }

  const toggleChat = () => {
    setIsChatVisible(!isChatVisible)
  }

  if (!project) return <div>Loading...</div>

    return (
      <div className="container mx-auto px-4 py-8">
        <h1 className="text-3xl font-bold mb-6">{project.name}</h1>
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
          <Card className={`order-2 lg:order-1 chat-transition ${
            isChatVisible ? 'chat-visible' : 'chat-hidden lg:chat-visible'
          }`}>
            <CardHeader>
              <CardTitle>Chat</CardTitle>
            </CardHeader>
            <CardContent>
              <ScrollArea className="h-[300px] lg:h-[400px] mb-4">
                {messages.map((message) => (
                  <div
                    key={message.id}
                    className={`mb-2 p-2 rounded-lg ${
                      message.role === 'user' ? 'bg-blue-100 ml-auto' : 'bg-gray-100'
                    }`}
                  >
                    {message.content}
                  </div>
                ))}
              </ScrollArea>
              <div className="flex flex-col sm:flex-row gap-2">
                <Input
                  value={newMessage}
                  onChange={(e) => setNewMessage(e.target.value)}
                  placeholder="Type your message..."
                  disabled={isLoading}
                  className="flex-grow"
                />
                <Button onClick={handleSendMessage} disabled={isLoading} className="w-full sm:w-auto">
                  Send
                </Button>
              </div>
            </CardContent>
          </Card>
          <Card className="order-1 lg:order-2">
            <CardHeader>
              <CardTitle>Project Details</CardTitle>
            </CardHeader>
            <CardContent>
              <Tabs defaultValue="diagram">
                <TabsList>
                  <TabsTrigger value="diagram">Diagram</TabsTrigger>
                  <TabsTrigger value="structure">File Structure</TabsTrigger>
                  <TabsTrigger value="tasks">Tasks</TabsTrigger>
                </TabsList>
                <TabsContent value="diagram">

                  <Mermaid chart={project.diagrams[0]['Mermaid Code']} />
                </TabsContent>
                <TabsContent value="structure">

                  <FileStructure structure={project.fileStructure} />
                </TabsContent>
                <TabsContent value="tasks">
                  <TaskList tasks={project.tasks} />
                </TabsContent>
              </Tabs>
            </CardContent>
          </Card>
        </div>
        <ChatFab onClick={toggleChat} />
      </div>
    )
  }