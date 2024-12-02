'use client';

import { useState, useEffect } from 'react';
import { useParams, useRouter } from 'next/navigation';
import axios from 'axios';
import { Card, CardContent, CardHeader, CardTitle } from '../../../components/ui/card';
import { Input } from '../../../components/ui/input';
import { Button } from '../../../components/ui/button';
import { ScrollArea } from '../../../components/ui/scroll-area';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '../../../components/ui/tabs';
import { Mermaid } from '../../../components/mermaid';
import { FileStructure } from '../../../components/file-structure';
import { TaskList } from '../../../components/task-list';
import { ChatFab } from '../../../components/chat-fab';
import { Copy, Check } from 'lucide-react';
import { Toaster, toast } from 'sonner';

interface Message {
  id: number;
  content: string;
  role: 'user' | 'assistant';
  timestamp: string;
}

interface Task {
  Name: string;
  Description: string;
  ProjectPath: string;
}

type FileStructureNode = string | { [key: string]: FileStructureNode[] };

interface Diagram {
  Name: string;
  Type: string;
  Description: string;
  'Mermaid Code': string;
  ProjectPath: string;
}

interface ProjectEntity {
  id: number;
  name: string;
  description: string;
  tasks: Task[];
  fileStructure: FileStructureNode;
  diagrams: Diagram[];
  chatHistory: {
    id: number;
  };
}

export default function ProjectPage() {
  const { id } = useParams();
  const router = useRouter();
  const [project, setProject] = useState<ProjectEntity | null>(null);
  const [messages, setMessages] = useState<Message[]>([]);
  const [newMessage, setNewMessage] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [isChatVisible, setIsChatVisible] = useState(false);
  const [historyId, setHistoryId] = useState<number | null>(null);
  const [copiedDiagram, setCopiedDiagram] = useState<string | null>(null);

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (!token) {
      router.push('/login');
      return;
    }

    const fetchProjectAndMessages = async () => {
      try {
        const userResponse = await axios.get('http://localhost:8080/user', {
          headers: { Authorization: `Bearer ${token}` },
        });

        const userData = userResponse.data;
        const selectedProject = userData.projectEntities.find(
          (p: ProjectEntity) => p.id === parseInt(Array.isArray(id) ? id[0] : id),
        );

        if (selectedProject) {
          setProject({
            ...selectedProject,
            tasks: JSON.parse(selectedProject.tasks).tasks,
            fileStructure: JSON.parse(selectedProject.fileStructure),
            diagrams: JSON.parse(selectedProject.diagram).diagrams,
          });
          setHistoryId(selectedProject.chatHistory.id);
        }

        const messagesResponse = await axios.get(
          `http://localhost:8080/user/project/chat/${selectedProject.chatHistory.id}`,
          {
            headers: { Authorization: `Bearer ${token}` },
          },
        );

        setMessages(
          messagesResponse.data.map((msg: any) => ({
            id: msg.timestamp,
            content: msg.message,
            role: msg.role === 'USER' ? 'user' : 'assistant',
            timestamp: msg.timestamp,
          })),
        );
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    if (id) {
      fetchProjectAndMessages();
    }
  }, [id, router]);

  const handleSendMessage = async () => {
    if (newMessage.trim() === '' || isLoading) return;

    const token = localStorage.getItem('token');
    if (!token) {
      console.error('No authorization token found');
      return;
    }

    setIsLoading(true);
    try {
      const response = await axios.post(
        `http://localhost:8080/user/project/chat/${historyId}`,
        { message: newMessage },
        {
          headers: { Authorization: `Bearer ${token}` },
        },
      );

      const data = response.data;

      setMessages([
        ...messages,
        {
          id: Date.now(),
          content: newMessage,
          role: 'user',
          timestamp: new Date().toISOString(),
        },
        {
          id: data.timestamp,
          content: data.message,
          role: 'assistant',
          timestamp: data.timestamp,
        },
      ]);

      setNewMessage('');
    } catch (error) {
      console.error('Error sending message:', error);
    } finally {
      setIsLoading(false);
    }
  };

  const toggleChat = () => {
    setIsChatVisible(!isChatVisible);
  };

  const handleCopyDiagramCode = (diagramCode: string) => {
    navigator.clipboard.writeText(
      diagramCode.replace(/```mermaid\s*/, '').replace(/```$/, '').trim()
    ).then(() => {
      setCopiedDiagram(diagramCode);
      toast.success('Código do diagrama copiado!');

      setTimeout(() => {
        setCopiedDiagram(null);
      }, 2000);
    }).catch(err => {
      console.error('Erro ao copiar:', err);
      toast.error('Erro ao copiar o código');
    });
  };

  if (!project) return <div>Loading...</div>;

  return (
    <div className="container mx-auto px-4 py-8">
      <Toaster />
      <h1 className="text-3xl font-bold mb-6">{project.name}</h1>
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <Card className="order-2 lg:order-1 chat-transition h-full flex flex-col">
          <CardHeader>
            <CardTitle>Chat</CardTitle>
          </CardHeader>
          <CardContent className="flex flex-col flex-grow justify-between">
            <ScrollArea className="flex-grow mb-4 overflow-y-auto h-0">
              {messages.map((message) => (
                <div
                  key={message.id}
                  className={`mb-2 p-2 rounded-lg ${message.role === 'user' ? 'bg-blue-100 ml-auto' : 'bg-gray-100'
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
        <Card className="order-1 lg:order-2 flex-row">
          <CardHeader>
            <CardTitle>Project Details</CardTitle>
          </CardHeader>
          <CardContent>
            <Tabs defaultValue="diagram">
              <TabsList>
                <TabsTrigger value="diagram">Diagramas</TabsTrigger>
                <TabsTrigger value="structure">Estrutura de Arquivos</TabsTrigger>
                <TabsTrigger value="tasks">Tarefas</TabsTrigger>
              </TabsList>
              <TabsContent value="diagram">
                <h1><strong>Select one of these Diagrams</strong></h1>
                <Tabs orientation="horizontal">
                  <TabsList className="w-full mb-4 flex-wrap h-min">
                    {project.diagrams.map((diagram, index) => (
                      <TabsTrigger
                        key={diagram.Name}
                        value={`diagram-${index}`}
                        className="flex-grow"
                      >
                        {diagram.Name}
                      </TabsTrigger>
                    ))}
                  </TabsList>

                  {project.diagrams.map((diagram, index) => (
                    <TabsContent
                      key={diagram.Name}
                      value={`diagram-${index}`}
                      className="relative"
                    >
                      <div className="absolute top-2 right-2 z-10">
                        <Button
                          size="icon"
                          onClick={() => handleCopyDiagramCode(diagram['Mermaid Code'])}
                          className="w-8 h-8"
                        >
                          {copiedDiagram === diagram['Mermaid Code'] ? (
                            <Check className="w-4 h-4 text-green-500" />
                          ) : (
                            <Copy className="w-4 h-4" />
                          )}
                        </Button>
                      </div>
                      <Card className="mt-2">
                        <CardHeader>
                          <CardTitle>{diagram.Name}</CardTitle>
                        </CardHeader>
                        <CardContent>
                          <p className="text-sm text-muted-foreground mb-4">
                            {diagram.Description}
                          </p>
                          <Mermaid chart={diagram['Mermaid Code']} />
                        </CardContent>
                      </Card>
                    </TabsContent>
                  ))}
                </Tabs>
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
  );
}