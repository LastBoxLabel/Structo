"use client"

import { useState, useEffect } from 'react';
import Link from 'next/link';
import { Card, CardHeader, CardTitle, CardDescription } from '../../components/ui/card';

interface Project {
  id: number;
  name: string;
  description: string;
}

interface User {
  id: number;
  name: string;
  username: string;
  email: string;
  projectEntities: Project[];
}

export default function UserPage() {
  const [projects, setProjects] = useState<Project[]>([]);

  useEffect(() => {
    const fetchProjects = async () => {
      const token = localStorage.getItem('Authorization');
      if (!token) {
        console.error('No authorization token found');
        return;
      }

      try {
        const response = await fetch('http://localhost:8080/user', {
          method: 'GET',
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`
          },
        });

        if (!response.ok) {
          console.error('Failed to fetch user data');
          return;
        }

        const user: User = await response.json();
        setProjects(user.projectEntities);
      } catch (error) {
        console.error('Failed to fetch projects:', error);
      }
    };

    fetchProjects();
  }, []);

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-6">Your Projects</h1>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {projects.length > 0 ? (
          projects.map((project) => (
            <Link href={`/project/${project.id}`} key={project.id}>
              <Card className="hover:shadow-lg transition-shadow">
                <CardHeader>
                  <CardTitle>{project.name}</CardTitle>
                  <CardDescription>{project.description}</CardDescription>
                </CardHeader>
              </Card>
            </Link>
          ))
        ) : (
          <p>No projects found</p>
        )}
      </div>
    </div>
  );
}
